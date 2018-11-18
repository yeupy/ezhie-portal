/*
 * dom에 데이터 로딩중 마크.
 * 
 * 마크 해제 예)
 * 
	var loadingDiv = setLoading(dom);
	this.ajax = $.get(url, function(data) {
		loadingDiv.remove();
	});
 */
function setLoading(dom) {
	var width = dom.width();
	var height = dom.height();
	var offset = dom.offset();
	var loading = $('<div class="loading">Loading...<div>')
		.css({
			'background-color': 'white',
			border: '1px solid black',
			color: 'black',
			top: offset.top,
			left: offset.left,
			position: 'absolute',
			'z-index': 1001,
			padding: '2px'
		});
	var div = $('<div class="loading"/>')
		.css({
			width: width,
			height: height,
			top: offset.top,
			left: offset.left,
			position: 'absolute',
			'z-index': 1000,
			'background-color': 'gray',
			opacity: 0.2
		});
	dom.after(div).after(loading);
	return div.add(loading);
}

/*
 * 데이터 렌더링을 위한 data-ui-control 값을 해석.
 * 
 * 예)
	parseUiData('{C_MDINS_NM}', jsonData);
 */
function parseUiData(string, data) {
	if(string == undefined) { return ""; }
	var result = string;
    var len = result.length;
    var keys = [];
    var i = 0;
	var left = result.indexOf("{", i);
    while(left > -1) {
		i = result.indexOf("}",left);
		keys.push(result.substring(left+1, i));
		left = result.indexOf("{", i);
    }
	keys.forEach(function(key) {
		if(data.hasOwnProperty(key)) {
			result = result.replace("{"+key+"}", data[key] ? data[key] : '').trim();
		}
	});
    return result;
}

/*
 * 콜백 배열 추가
 */
function callbacks(args) {
	var cb = [];
	if(typeof args === 'function') {
		cb.push(args);
	} else if(typeof args === 'object' && args.length !== undefined) {
		for(var x in args) {
			var arg = args[x];
			if(typeof arg === 'function') {
				cb.push(arg);
			} else if(typeof arg === 'object' && arg.length !== undefined) {
				for(var z in arg) {
					if(typeof arg[z] === 'function') {
						cb.push(arg[z]);
					}
				}
			}
		}
	}
	return cb;
}

/*
 * 콜백 배열 실행
 */
function callbacksDo(callbacks) {
	callbacks.forEach(function(cb) {
		cb();
	});
}

/*
 * 현재 url에서 key 파라미터 값 조회.
 */
function getParam(key) {
	var value = null;
	var search = location.search;
	if(search != "") {
		var params = search.replace('?','').split('&');
		var pair = params.find(function(param) {
			var _pair = param.split('=');
			return _pair[0] == key;
		});
		var _value = pair.split('=')[1];
		value = _value ? _value : null;
	}
	return value;
}

/*
 * 현재 url에 key 파라미터 값 지정하여 request 호출.
 */
function setParam(key, value) {
	var search = location.search;
	var params = search.replace('?','').split('&');
	var pairIdx = params.findIndex(function(param) {
		var _pair = param.split('=');
		return _pair[0] === key;
	});
	var param = key + '=' + value;
	if(pairIdx == -1) {
		params.push(param);
	} else {
		params[pairIdx] = param; 
	}
	var serialized = '?';
	params.forEach(function(pair, i, arr) {
		serialized += pair + (i < arr - 1 ? '&' : '');
	});
	location.search = serialized;
}

/*
 * Texter
 * 
 * Texter는 CRUD가 가능한 폼에서 데이터를 추가, 수정, 삭제 할 때 필요한 UI 이벤트 validation을 지원하며,
 * DOM의 타입에 따라 알맞는 JSON 렌더링을 지원한다. 또는 Read 할 JSON 데이터의 특정 요소를 사용자가 원하는 포멧으로 지정할 수도 있다.
 * 
 * DOM 오브젝트 데이터 적용은 아래와 같다.
 * 
 	<script>
 	window.texter = new Texter('${context}/portal/system/getHproviderDetail.do?id=%s', $('#content'));
 	</script>
 	<body>
 	<button onclick="texter.load(108)">상세조회</button>
 	<div id="content">
 	<h6 class="ui-control" data-ui-control="{C_MDINS_NM}"></h6>
 	
 	<input type="text" class="input_text ui-control" data-ui-control="{C_TEL_NO_1} {C_TEL_NO_2} {C_TEL_NO_3}" name="C_TEL_NO_1" placeholder="전화번호" />
 	
 	<select class="select_text ui-control HOSTYPE" data-ui-control="{C_MDINS_CLS_CD}" name="C_MDINS_CLS_CD">
		<option value="">-- 선택 --</option>
		<option value="C001">상급종합병원</option>
		<option value="C002">병원</option>
	</select>
	...
 */

/*
 * Texter 초기화.
 */
function Texter(url, dom, clazz) {
	this.clazz = clazz ? clazz : "ui-control";
	this.dom = dom.find('.' + this.clazz).change(function(e) {
		this.isEdited = true;
	}.bind(this));
	this.isEdited = false;
	this.data = null;
	var loadDo = function(dom, clazz, data) {
		dom.each(function() {
			var parsed = parseUiData($(this).data(clazz), data);
			if(this.tagName === 'INPUT' || this.tagName === 'SELECT' || this.tagName === 'TEXTAREA') {
				$(this).val(parsed);
			} else {
				$(this).text(parsed);
			}
		});
	};
	/*
	 * id를 지정하고 정보를 렌더링.
	 */
	this.load = function(id, arguments) {
		if(this.isEdited && !confirm('Continue?\n\nUnsaved data will be lost.')) {
			return;
		}
		if(this.ajax != null) {
			this.ajax.abort();
			this.ajax = null;
		}
		var cb = callbacks(arguments);
		var loadingDiv = setLoading(dom);
		this.ajax = $.get(url.replace("%s", id), function(data) {
			this.data = data;
			this.isEdited = false;
			loadDo(this.dom, this.clazz, data);
			loadingDiv.remove();
		}.bind(this), 'json');
		
		$.when(this.ajax).done(function() {
			callbacksDo(cb);
		}).fail(function(xhr, status, err) {
			loadingDiv.remove();
			alert(status);
		});
	};
	/*
	 * 폼을 리셋.
	 */
	this.reset = function() {
		var force = (arguments[0] && typeof arguments[0] === 'boolean') ? arguments[0] : false;
		var cb = callbacks(arguments);
		var resetDo = function() {
			this.isEdited = false;
			$.Deferred().done(function() {
				if(this.data) {
					loadDo(this.dom, this.clazz, this.data);
				} else {
					loadDo(this.dom, "", this.data);
				}
			}.bind(this)).done(function() {
				callbacksDo(cb);
			}).resolve();
		}.bind(this);
		if(!force && this.isEdited) {
			if(confirm('Reset?\n\nUnsaved data will be lost.')) {
				resetDo();
			}
		} else if(force) {
			resetDo();
		}
	};
	/*
	 * 폼에 새로운 데이터 입력.
	 */
	this.add = function() {
		if(!this.data && !this.isEdited) { return; }
		this.data = null;
		this.isEdited = true;
		if(confirm('Add New?\n\nUnsaved data will be lost.')) {
			this.reset(true, arguments);
		}
	};
	/*
	 * 폼에 입력한 데이터 저장.
	 */
	this.save = function() {
		if(!this.isEdited) {
			alert('Nothing to save.');
			return false;
		}
		if(confirm('Save?\n\nData will be saved.')) {
			setLoading(dom);
			return true;
		}
		return false;
	};
	/*
	 * 폼에 조회한 데이터 삭제.
	 */
	this.del = function() {
		if(!this.data) {
			alert('Nothing to delete.');
			return false;
		}
		if(confirm('Delete?\n\nData will be deleted.')) {
			setLoading(dom);
			return true;
		}
		return false;
	};
}

/*
 * Lister
 * 
 * Lister는 검색조건이나 폼 작성 시 선택하여 입력하는 공통코드 리스트를 렌더링한다.
 * 사용 예)
 * 
	<script>
	new Lister('${context}/portal/com/codes.do?id=HOSTYPE', $('.HOSTYPE'));
	</script>
 */

/*
 * Lister 초기화.
 */
function Lister(url, selectDom) {
	this.dom = selectDom.filter('select');
	this.selectedIndex = null;
	this.selectedValue = null;
	/*
	 * SELECT 리스트 렌더링.
	 */
	this.load = function() {
		$.get(url, function(data) {
			this.dom.empty().append('<option value="">-- 선택 --</option>');
			data.forEach(function(code) {
				this.dom.append('<option value="'+code.key+'">'+code.value+'</option>');
			}.bind(this));
			this.selectIndex(this.selectedIndex).selectValue(this.selectedValue);
		}.bind(this), 'json')
		.fail(function() {
			this.dom.empty().append('<option value="">## ERROR ##</option>');
		}.bind(this));
	};
	/*
	 * index로 OPTION 오브젝트 선택.
	 */
	this.selectIndex = function(index) {
		if(!index) { return this; }
		this.selectedIndex = index;
		this.selectedValue = null;
		this.dom.each(function() {
			$(this).find('option').eq(index).prop('selected', true);
		});
		return this;
	};
	/*
	 * value로 OPTION 오브젝트 선택.
	 */
	this.selectValue = function(value) {
		if(!value) { return this; }
		this.selectedIndex = null;
		this.selectedValue = value;
		this.dom.each(function() {
			$(this).val(value);
		});
		return this;
	};
	this.load();
}

/*
 * Paginator
 * 
 * Paginator는 오직 주어진 파라미터 값에 의존하여 테이블의 페이지 리스트를 렌더링 하며 서버와 분리되어 작동한다.
 * 단 파라미터에 콜백함수를 주어 클릭 이벤트 시 GET 또는 AJAX 호출을 할 수 있다.
 * 
 * 예)
	var page = getParam('page');
	window.p = new Paginator(page ? page : 0, 10, 10, 114, $('#hproviderListPagination'), function(_page) {
		setParam('page', _page);
	}).configImg({
		path: '${context}/images/portal/main/'
	});
 */

/*
 * Paginator 초기화
 */
function Paginator(pageIndex, recordSize, pageSize, totalCount, dom, callback) {
	this.pageIndex = Number(pageIndex);
	this.img = {
		path: '/images/portal/main/',
		first: 'btn_first.gif',
		prev: 'btn_pre.gif',
		next: 'btn_next.gif',
		last: 'btn_last.gif'
	};
	/*
	 * 이미지 URL 설정.
	 */
	this.configImg = function(json) {
		this.img.path = json.path ? json.path : this.img.path;
		this.img.first = json.first ? json.first : this.img.first;
		this.img.prev = json.prev ? json.prev : this.img.prev;
		this.img.next = json.next ? json.next : this.img.next;
		this.img.last = json.last ? json.last : this.img.last;
		this.loadButtons().load();
		return this;
	};
	var totalPageCount = Math.ceil(totalCount / recordSize);
	var getPageArray = function(rangeIndex) {
		var pages = [];
		var init = rangeIndex * pageSize;
		for(var i = 1; i <= pageSize; i++) {
			var page = init + i;
			if(page > totalPageCount) {
				break;
			}
			pages.push(page);
		}
		return pages;
	};
	/*
	 * 버튼 이미지 렌더링.
	 */
	this.loadButtons = function() {
		var img = this.img;
		var a_first = $('<a class="btn_first btn_page" style="cursor:pointer"><img src="' + img.path + img.first + '" title="첫 페이지" /></a>')
			.click(function() {
				this.first();
			}.bind(this));
		var a_prev = $('<a class="btn_pre btn_page" style="cursor:pointer"><img src="' + img.path + img.prev + '" title="이전 페이지" /></a>')
			.click(function() {
				this.prev();
			}.bind(this));
		var a_next = $('<a class="btn_pre btn_page" style="cursor:pointer"><img src="' + img.path + img.next + '" title="다음 페이지" /></a>')
			.click(function() {
				this.next();
			}.bind(this));
		var a_last = $('<a class="btn_pre btn_page" style="cursor:pointer"><img src="' + img.path + img.last + '" title="마지막 페이지" /></a>')
			.click(function() {
				this.last();
			}.bind(this));
		var ol_page = $('<ol class="page_num"></ol>');

		dom.empty()
			.append(a_first)
			.append(a_prev)
			.append(ol_page)
			.append(a_next)
			.append(a_last);
		
		return this;
	};
	/*
	 * 페이지 UI 렌더링.
	 */
	this.load = function() {
		var ol_page = dom.find('ol').empty();
		var li_tmp = $('<li style="cursor:pointer"></li>');
		var rangeIndex = Math.floor(this.pageIndex / pageSize);
		getPageArray(rangeIndex).forEach(function(page) {
			var li_page = li_tmp.clone().text(page).click(function(e) {
				ol_page.find('li.on').removeClass('on');
				$(e.target).addClass('on');
				this.gotoPage(page - 1);
			}.bind(this));
			if((page - 1) == this.pageIndex) {
				li_page.addClass('on');
			}
			ol_page.append(li_page);
		}.bind(this));
	};
	/*
	 * 처음으로 이동.
	 */
	this.first = function() {
		this.gotoPage(0);
	};
	/*
	 * 이전으로 이동.
	 */
	this.prev = function() {
		var firstIdx = 0;
		var idx = this.pageIndex;
		this.gotoPage(idx > firstIdx ? idx - 1 : firstIdx);
	};
	/*
	 * 다음으로 이동.
	 */
	this.next = function() {
		var lastIdx = totalPageCount - 1;
		var idx = this.pageIndex;
		this.gotoPage(idx < lastIdx ? idx + 1 : lastIdx);
	};
	/*
	 * 마지막으로 이동.
	 */
	this.last = function() {
		this.gotoPage(totalPageCount - 1);
	};
	/*
	 * pageIndex 페이지로 이동.
	 */
	this.gotoPage = function(pageIndex) {
		if(callback) {
			callback(pageIndex);
		}
		this.pageIndex = pageIndex;
		this.load();
	};
	/*
	 * articleIndex 글이 있는 페이지로 이동.
	 */
	this.gotoArticle = function(articleIndex) {
		var _articleIndex = Number(articleIndex);
		var page = totalPageCount;
		var rangeCount = Math.ceil(totalPageCount / pageSize);
		var rangeIndex = null;
		for(var i = 0; i < rangeCount; i++) {
			if(recordSize * pageSize * i <= _articleIndex && _articleIndex < recordSize * pageSize * (i + 1)) {
				rangeIndex = i;
				break;
			}
		}
		for(var j = 0; j < pageSize; j++) {
			var _page = (rangeIndex * pageSize) + j;
			if(recordSize * _page <= _articleIndex && _articleIndex < recordSize * (_page + 1)) {
				page = _page;
				break;
			}
		}
		this.gotoPage(page);
	}
	try {
		this.loadButtons();
	} catch(e) {}
	this.load();
}