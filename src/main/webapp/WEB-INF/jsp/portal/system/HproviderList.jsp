<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui"     uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<c:set var="context" scope="session" value="${pageContext.servletContext.contextPath}" />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko" lang="ko">
<head>
<title>:: HI-SEA 진료정보교류 통합 관리 포털 ::</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Content-Script-Type" content="text/javascript" />
<meta http-equiv="Content-Style-Type" content="text/css" />
<link rel="stylesheet" type="text/css" src="${context}/css/portal/default_frame.css" />
<script type="text/javascript" src="${context}/scripts/portal/jquery-3.2.1.min.js"></script>
<script type="text/javascript" src="${context}/scripts/portal/design.js"></script>
<script type="text/javascript" src="${context}/scripts/portal/jquery.nicescroll.js"></script>
<script type="text/javascript" src="${context}/scripts/portal/HIEQuery.Controller.js"></script>
<script type="text/javascript" src="${context}/scripts/portal/jquery.li18n.min.js"></script>
<script type="text/javascript" src="${context}/scripts/portal/ui-control.js"></script>
<script type="text/javascript">
	function OnShowMsg() {
		MsgBox("메시지 박스 출력");
	}

	function OnShowConfirm() {
		ConfirmBox("컨펌 하시겠습니까?", ExecFunction, "변수");
	}

	function ExecFunction(param) {
		MsgBox("OnShowConfirm 으로 ExecFunction 를 실행하여 " + param + " 값이 전달 되었습니다.");
	}

	function addNew() {
		$('.detail_title').find('h6').html('새 의료기관 추가');
	}
	
	function focus() {
		$('.rgt_tile').find('input[name="C_MDINS_NM"]').focus();
	}
	
	function reset() {
		if(texter.data == null) {
			texter.reset(addNew, focus);
		} else {
			texter.reset(focus);
		}
	}
	
	function save() {
		if(texter.save()) {
			$('#urlParams').val(location.search);
			$('#formHprovider').prop('action', '${context}/portal/system/' + (texter.data ? 'update' : 'save') + 'Hprovider.do').submit();
		}
	}
	
	function del() {
		if(texter.del()) {
			$('#urlParams').val(location.search);
			$('#formHprovider').prop('action', '${context}/portal/system/deleteHprovider.do').submit();
		}
	}
	
	function postAlert() {
		var msg = '${postAlert.msg}';
		var id = '${postAlert.id}';
		var _alert = function(msg) {
			setTimeout(function() {
				alert(msg);
			}, 100);
		};
		if(id.length > 0 && texter) {
			texter.load(id, function() {
				if(msg.length > 0) {
					_alert(msg);
				}
			});
		} else {
			$.when(document).done(function() {
				if(msg.length > 0) {
					_alert(msg);
				}
			});
		}
	}
	
	$(document).ready(function() {
		window.texter = new Texter('${context}/portal/system/getHproviderDetail.do?id=%s', $('.rgt_tile'));
		texter.isEdited = true;
		texter.reset(true, addNew, focus);
		
		new Lister('${context}/portal/com/codes.do?id=HOSTYPE', $('.HOSTYPE'));
		new Lister('${context}/portal/com/codes.do?id=LANGTYPE', $('.LANGTYPE'));
		new Lister('${context}/portal/com/codes.do?id=ACTIVETYPE', $('.ACTIVETYPE'));
		
		var page = getParam('page');
		window.p = new Paginator(page ? page : 0, ${paginationInfo.recordCountPerPage}, ${paginationInfo.pageSize}, ${paginationInfo.totalRecordCount}, $('#hproviderListPagination'), function(_page) {
			setParam('page', _page);
		}).configImg({
			path: '${context}/images/portal/main/'
		});
		postAlert();
		
	});
	
	
</script>
</head>
<body id="sub_body" style="overflow:hidden;">
	<!-- contents_form -->
	<div class="contents_form">
		<!-- 상단 타이틀, 로케이션 -->
		<div class="location_section">
			<!-- 상단 로케이션 -->
			<div class="top_location fix">
				<h4 class="location_tit">의료기관 관리</h4>

				<ul class="brc fix">
					<li>시스템 관리</li>
					<li><strong>의료기관 관리</strong></li>
				</ul>
			</div>
			<!-- /상단 로케이션 -->

			<!-- 하단 로케이션 -->
			<div class="btm_location fix">
				<strong class="location_sub_tit">의료기관 유형</strong>
				<form action="#">
					<fieldset>
						<legend>의료기관 유형 검색</legend>
						<select style="width:130px;" title="의료기관 유형 선택" class="select_text HOSTYPE">
							<option value="">상급종합병원</option>
							<option value="">병원</option>
						</select>

						<input class="input_text" type="text" placeholder="의료기관명" />
						<input type="image" src="${context}/images/portal/main/btn_search.png" alt="검색하기" />
					</fieldset>
				</form>

				<ul class="btn_location">
					<li><a href="#" class="btn_notice" onclick="OnShowMsg();"><span>알림</span></a></li>
					<li><a href="#" class="btn_check" onclick="OnShowConfirm();"><span>확인</span></a></li>
					<li><a href="#" class="btn_new"><span>신규</span></a></li>
				</ul>
			</div>
			<!-- /하단 로케이션 -->
		</div>
		<!-- 상단 타이틀, 로케이션 -->
		
		<div class="main_section fix">
			<!-- left_tile -->
			<div class="lft_tile">
				<div class="title_tile">
					<h5>의료기관 목록</h5>
				</div>
	 			
				<div class="content_tile tile_type1">
					<table class="master_table sort_table"  cellpadding="0" cellspacing="0" border="0" summary="순번, 의료기관 명, 의료기관 유형, 주 사용언어, 등록일시, 활성상태 순의 표 입니다.">
						<caption>의료기관 목록</caption>
						<colgroup>
							<col width="6%"/>
							<col width="34%"/>
							<col width="20%"/>
							<col width="15%"/>
							<col width="15%"/>
						</colgroup>

						<thead>
							<tr>
								<th>순번</th>

								<th>
									<div class="th_wrap">의료기관 명
										<p>
											<a href="#"><img src="${context}/images/portal/main/btn_up.gif" alt="오름차순" /></a>
											<a href="#"><img src="${context}/images/portal/main/btn_down.gif" alt="내림차순" /></a>
										</p>
									</div>
								</th>

								<th>
									<div class="th_wrap">의료기관 유형
										<p>
											<a href="#"><img src="${context}/images/portal/main/btn_up.gif" alt="오름차순" /></a>
											<a href="#"><img src="${context}/images/portal/main/btn_down.gif" alt="내림차순" /></a>
										</p>
									</div>
								</th>

								<th>
									<div class="th_wrap">주 사용언어
										<p>
											<a href="#"><img src="${context}/images/portal/main/btn_up.gif" alt="오름차순" /></a>
											<a href="#"><img src="${context}/images/portal/main/btn_down.gif" alt="내림차순" /></a>
										</p>
									</div>
								</th>

								<th>
									<div class="th_wrap">등록일시
										<p>
											<a href="#"><img src="${context}/images/portal/main/btn_up.gif" alt="오름차순" /></a>
											<a href="#"><img src="${context}/images/portal/main/btn_down.gif" alt="내림차순" /></a>
										</p>
									</div>
								</th>

								<th>
									<div class="th_wrap">활성상태
										<p>
											<a href="#"><img src="${context}/images/portal/main/btn_up.gif" alt="오름차순" /></a>
											<a href="#"><img src="${context}/images/portal/main/btn_down.gif" alt="내림차순" /></a>
										</p>
									</div>
								</th>
							</tr>
						</thead>

						<tbody>
						<c:forEach items="${hproviderList}" var="el" varStatus="status">
							<tr>
								<td>${(paginationInfo.recordCountPerPage * paginationInfo.currentPageNo) + status.count }</td>
								<td class="left"><a href="javascript: texter.load(${el.C_MDINS_ID});">${el.C_MDINS_NM}</a></td>
								<td>${el.C_MDINS_CLS_NM}</td>
								<td></td>
								<td>${el.C_REG_DTM}</td>
								<td>${el.C_ENTY_STS_NM}</td>
							</tr>		
						</c:forEach>
							<!-- <tr>
								<td>1</td>
								<td class="left"><a href="#">두리가자의원</a></td>
								<td>병원</td>
								<td>한국어</td>
								<td>2015-09-21</td>
								<td>가입</td>
							</tr>

							<tr>
								<td>2</td>
								<td class="left"><a href="#">원곡하늘의원</a></td>
								<td>상급종합병원</td>
								<td>한국어</td>
								<td>2015-09-21</td>
								<td>가입</td>
							</tr>

							<tr>
								<td>3</td>
								<td class="left"><a href="#">두리가자의원</a></td>
								<td>병원</td>
								<td>한국어</td>
								<td>2015-09-21</td>
								<td>가입</td>
							</tr>

							<tr>
								<td>4</td>
								<td class="left"><a href="#">원곡하늘의원</a></td>
								<td>상급종합병원</td>
								<td>한국어</td>
								<td>2015-09-21</td>
								<td>가입</td>
							</tr>

							<tr>
								<td>5</td>
								<td class="left"><a href="#">두리가자의원</a></td>
								<td>병원</td>
								<td>한국어</td>
								<td>2015-09-21</td>
								<td>가입</td>
							</tr>

							<tr>
								<td>6</td>
								<td class="left"><a href="#">원곡하늘의원</a></td>
								<td>상급종합병원</td>
								<td>한국어</td>
								<td>2015-09-21</td>
								<td>가입</td>
							</tr>

							<tr>
								<td>7</td>
								<td class="left"><a href="#">두리가자의원</a></td>
								<td>병원</td>
								<td>한국어</td>
								<td>2015-09-21</td>
								<td>가입</td>
							</tr>

							<tr>
								<td>8</td>
								<td class="left"><a href="#">원곡하늘의원</a></td>
								<td>상급종합병원</td>
								<td>한국어</td>
								<td>2015-09-21</td>
								<td>가입</td>
							</tr>

							<tr>
								<td>9</td>
								<td class="left"><a href="#">두리가자의원</a></td>
								<td>병원</td>
								<td>한국어</td>
								<td>2015-09-21</td>
								<td>가입</td>
							</tr>

							<tr>
								<td>10</td>
								<td class="left"><a href="#">원곡하늘의원</a></td>
								<td>상급종합병원</td>
								<td>한국어</td>
								<td>2015-09-21</td>
								<td>가입</td>
							</tr> -->
						</tbody>								
					</table>

					<!-- 페이징 -->
					<div id="hproviderListPagination" class="pageList">
						<!-- <a class="btn_first btn_page" href="#"><img src="${context}/images/portal/main/btn_first.gif" title="목록 첫 페이지" /></a>
						<a class="btn_pre btn_page" href="#"><img src="${context}/images/portal/main/btn_pre.gif" title="이전 페이지" /></a>
						<ol class="page_num">
							<li class="on"><a href="#">1</a></li>
							<li><a href="#">2</a></li>
							<li><a href="#">3</a></li>
							<li><a href="#">4</a></li>
							<li><a href="#">5</a></li>
							<li><a href="#">6</a></li>
							<li><a href="#">7</a></li>
							<li><a href="#">8</a></li>
							<li><a href="#">9</a></li>
							<li><a href="#">10</a></li>
						</ol>
						<a class="btn_next btn_page" href="#"><img src="${context}/images/portal/main/btn_next.gif" title="마지막 페이지" /></a>
						<a class="btn_last btn_page" href="#"><img src="${context}/images/portal/main/btn_last.gif" title="목록 마지막 페이지" /></a> -->
					</div>
					<!-- /페이징 -->
				</div>
				<img src="${context}/images/portal/main/bg_split.png" alt="" class="split" />
			</div>
			<!-- /left_tile -->
			
			<!-- right_tile-->
			<div class="rgt_tile">
				<div class="detail_title">
					<h6 class="ui-control" data-ui-control="{C_MDINS_NM}">새 의료기관 추가</h6>
				</div>

				<div class="content_tile tile_type1">
					<ul class="detail_tab">
						<li id="info_tab1" class="on"><a href="javascript:move_tab(1);">의료기관</a></li>
						<li id="info_tab2"><a href="javascript:move_tab(2);">사용자 목록</a></li>
						<li id="info_tab3"><a href="javascript:move_tab(3);">사용자 로그인 내역</a></li>
					</ul>

					<div class="fix" id="info_area1">
					<form:form commandName="hprovider" id="formHprovider" action="" method="post" target="_self">
						<input type="hidden" id="urlParams" name="urlParams" />
						<div class="detail_wrap">
							<table class="detail_table_type1" cellpadding="0" cellspacing="0" border="0">
								<caption>의료기관 상세</caption>
								<!--<colgroup>
									<col width="18%" />
									<col width="32%" />
									<col width="18%" />
									<col width="32%" />
								</colgroup>-->

								<tbody>
									<tr>
										<th style="width:79px;">의료기관 명</th>
										<td>
											<input type="hidden" class="ui-control" data-ui-control="{C_MDINS_ID}" name="C_MDINS_ID" />
											<input type="text" class="input_text ui-control" data-ui-control="{C_MDINS_NM}" name="C_MDINS_NM" placeholder="의료기관 명" />
										</td>
										<th>OID 코드</th>
										<td><input readonly="readonly" type="text" class="input_text ui-control" data-ui-control="{C_MDINS_OID}" name="C_MDINS_OID" style="background:#eee;"
										 placeholder="OOO.OO.OO.OOOOOOO……" /></td>
									</tr>

									<tr>
										<th>의료기관 주소</th>
										<td colspan="3">
											<div class="medical_addr">
												<p class="push_btn">
													<input type="text" readonly="readonly" class="input_text ui-control" data-ui-control="{C_MDINS_POST_NO}" name="C_MDINS_POST_NO" placeholder="우편번호" />
													<a href="#" onmousedown="javascript:OpenTreeForParent();">
														<img src="${context}/images/portal/main/btn_search.png" alt="주소 검색" />
													</a>
													<script type="text/javascript">
												 /// <summary>주소를 검색하기 위한 다이얼로그 창 을 오픈 합니다.</summary>
													function OpenTreeForParent() {
															var page = "addr_search.html";

															$dialog = $('<div></div>')
															 .html('<iframe id="iframe1" border="0" scrolling="no" src="' + page + '" width="100%" height="440px"></iframe>')
															 .dialog({
																	 autoOpen: false,
																	 modal: true,
																	 //height: 400,
																	 width:600,
																	 closeOnEscape: false,
																	 draggable: true,
																	 resizable: false,
																	 title: "주소검색"
															 });
															$dialog.dialog('open');
													}

													/// <summary>다이얼로그 창 에서 주소가 선택되었을 때 수행할 메소드 입니다.</summary>
													/// <param name="obj" type="array">우편번호와 상세주소가 포함된 array 입니다.</param>
													<%-- function TreeSelected(obj) {
															if (obj) {
																	$("#<%= Html.IdFor(m => m.DefaultDTO.C_PL_LCAL_NM) %>").val(obj[0]);
																	$("#<%= Html.IdFor(m => m.DefaultDTO.C_PRNS_ARC_ID) %>").val(obj[1]);
															}
															if ($dialog) {
																	$dialog.dialog('close');
															}
													} --%>
												</script>
												</p>
												
												<p class="addr_content">
													<input type="text" readonly="readonly" class="input_text ui-control" data-ui-control="{C_MDINS_ADDR}" name="C_MDINS_ADDR" placeholder="주소" />
												</p>
											</div>
											
											<input type="text" class="input_text ui-control" data-ui-control="{C_MDINS_ADDR_DTL}" name="C_MDINS_ADDR_DTL" placeholder="상세주소" />
										</td>
									</tr>

									<tr>
										<th>요양기관 번호</th>
										<td style="border-right:none;">
											<input type="text" class="input_text ui-control" data-ui-control="{C_RCORG_NO}" name="C_RCORG_NO" placeholder="요양기관 번호" />
										</td>

										<td colspan="2" style="border-left:none; padding-left:0;">
											<a href="#" class="btn_check"><span>요양기관 확인</span></a>
										</td>
									</tr>

									<tr>
										<th>대표자 명</th>
										<td><input type="text" class="input_text ui-control" data-ui-control="{C_RPST_NM}" name="C_RPST_NM" placeholder="대표자 명" /></td>
										<th>전화번호</th>
										<td><input type="text" class="input_text ui-control" data-ui-control="{C_TEL_NO_1} {C_TEL_NO_2} {C_TEL_NO_3}" name="C_TEL_NO_1" placeholder="전화번호" /></td>
									</tr>

									<tr>
										<th>의료기관 유형</th>
										<td>
											<select class="select_text ui-control HOSTYPE" data-ui-control="{C_MDINS_CLS_CD}" name="C_MDINS_CLS_CD">
												<option value="">-- 선택 --</option>
												<option value="">상급종합병원</option>
												<option value="">병원</option>
											</select>
										</td>
										<th>리파지토리 ID</th>
										<td><input type="text" class="input_text ui-control" data-ui-control="{C_RPG_OID}" name="C_RPG_OID" placeholder="OID 를 포함한 UUID" /></td>
									</tr>

									<tr>
										<th class="type2_th">주 사용 언어</th>
										<td class="type2_td">
											<select class="select_text LANGTYPE" title="주 사용 언어">
												<option value="">-- 선택 --</option>
												<option value="korean">한국어</option>
												<option value="english">영어</option>
												<option value="japanese">일본어</option>
											</select>
										</td>

										<th class="type2_th">상&nbsp;&nbsp;&nbsp;&nbsp;태</th>
										<td class="type2_td">
											<select class="select_text ui-control ACTIVETYPE" title="상태" name="C_ENTY_STS_CD" data-ui-control="{C_ENTY_STS_CD}">
												<option value="">-- 선택 --</option>
												<option value="join">가입</option>
												<option value="unjoin">미가입</option>
											</select>
										</td>
									</tr>

									<tr>
										<th>전문 의료 분야</th>
										<td><input type="text" class="input_text ui-control" data-ui-control="{C_RGSY_OID}" name="C_RGSY_OID" placeholder="OID 를 포함한 UUID" /></td>
									</tr>
								</tbody>
							</table>
						</div>
						
						<ul class="btn_submit">
							<li>
								<a class="btn_new_user" href="javascript: texter.add(addNew, focus);"><span>신규사용자</span></a>
							</li>

							<li>
								<a class="btn_save" href="javascript: save();"><span>저장</span></a>
							</li>

							<li>
								<a class="btn_del" href="javascript: del();"><span>삭제</span></a>
							</li>

							<li class="mgr_x">
								<a class="btn_reset" href="javascript: reset();"><span>초기화</span></a>
							</li>
						</ul>
					</form:form>
					</div>

					<div id="info_area2" style="display:none;">
						<div class="detail_wrap">
							<table class="detail_table_type2 sort_table" cellpadding="0" cellspacing="0" border="0" summary="등록일, 로그인 ID, 사용자 명, 이메일 주소, 전화번호, 사용자 유형 순의 표 입니다.">
								<caption>사용자 목록</caption>
								<colgroup>
									<col width="15%"/>
									<col width="16%"/>
									<col width="16%"/>
									<col width="20%"/>
									<col width="18%"/>
								</colgroup>

								<thead>
									<tr>
										<th>등록일</th>

										<th>
											<div class="th_wrap">로그인 ID
												<p>
													<a href="#"><img src="${context}/images/portal/main/btn_up.gif" alt="오름차순" /></a>
													<a href="#"><img src="${context}/images/portal/main/btn_down.gif" alt="내림차순" /></a>
												</p>
											</div>
										</th>

										<th>
											<div class="th_wrap">사용자 명
												<p>
													<a href="#"><img src="${context}/images/portal/main/btn_up.gif" alt="오름차순" /></a>
													<a href="#"><img src="${context}/images/portal/main/btn_down.gif" alt="내림차순" /></a>
												</p>
											</div>
										</th>

										<th>
											<div class="th_wrap">이메일 주소
												<p>
													<a href="#"><img src="${context}/images/portal/main/btn_up.gif" alt="오름차순" /></a>
													<a href="#"><img src="${context}/images/portal/main/btn_down.gif" alt="내림차순" /></a>
												</p>
											</div>
										</th>

										<th>
											<div class="th_wrap">전화번호
												<p>
													<a href="#"><img src="${context}/images/portal/main/btn_up.gif" alt="오름차순" /></a>
													<a href="#"><img src="${context}/images/portal/main/btn_down.gif" alt="내림차순" /></a>
												</p>
											</div>
										</th>

										<th>
											<div class="th_wrap">사용자 유형
												<p>
													<a href="#"><img src="${context}/images/portal/main/btn_up.gif" alt="오름차순" /></a>
													<a href="#"><img src="${context}/images/portal/main/btn_down.gif" alt="내림차순" /></a>
												</p>
											</div>
										</th>
									</tr>
								</thead>

								<tbody>
									<tr>
										<td>2016-02-17</td>
										<td>test</td>
										<td>테스트</td>
										<td>test@test.com</td>
										<td>010-1234-5678</td>
										<td>STAFF</td>
									</tr>

									<tr>
										<td>2016-02-17</td>
										<td>test</td>
										<td>테스트</td>
										<td>test@test.com</td>
										<td>010-1234-5678</td>
										<td>STAFF</td>
									</tr>

									<tr>
										<td>2016-02-17</td>
										<td>test</td>
										<td>테스트</td>
										<td>test@test.com</td>
										<td>010-1234-5678</td>
										<td>STAFF</td>
									</tr>

									<tr>
										<td>2016-02-17</td>
										<td>test</td>
										<td>테스트</td>
										<td>test@test.com</td>
										<td>010-1234-5678</td>
										<td>STAFF</td>
									</tr>

									<tr>
										<td>2016-02-17</td>
										<td>test</td>
										<td>테스트</td>
										<td>test@test.com</td>
										<td>010-1234-5678</td>
										<td>STAFF</td>
									</tr>

									<tr>
										<td>2016-02-17</td>
										<td>test</td>
										<td>테스트</td>
										<td>test@test.com</td>
										<td>010-1234-5678</td>
										<td>STAFF</td>
									</tr>

									<tr>
										<td>2016-02-17</td>
										<td>test</td>
										<td>테스트</td>
										<td>test@test.com</td>
										<td>010-1234-5678</td>
										<td>STAFF</td>
									</tr>

									<tr>
										<td>2016-02-17</td>
										<td>test</td>
										<td>테스트</td>
										<td>test@test.com</td>
										<td>010-1234-5678</td>
										<td>STAFF</td>
									</tr>

									<tr>
										<td>2016-02-17</td>
										<td>test</td>
										<td>테스트</td>
										<td>test@test.com</td>
										<td>010-1234-5678</td>
										<td>STAFF</td>
									</tr>

									<tr>
										<td>2016-02-17</td>
										<td>test</td>
										<td>테스트</td>
										<td>test@test.com</td>
										<td>010-1234-5678</td>
										<td>STAFF</td>
									</tr>
								</tbody>								
							</table>

							<!-- 페이징 -->
							<div class="pageList">
								<a class="btn_first btn_page" href="#"><img src="${context}/images/portal/main/btn_first.gif" alt="목록 첫 페이지" /></a>
								<a class="btn_pre btn_page" href="#"><img src="${context}/images/portal/main/btn_pre.gif" alt="이전 페이지" /></a>
								<ol class="page_num">
									<li class="on"><a href="#">1</a></li>
									<li><a href="#">2</a></li>
									<li><a href="#">3</a></li>
									<li><a href="#">4</a></li>
									<li><a href="#">5</a></li>
									<li><a href="#">6</a></li>
									<li><a href="#">7</a></li>
									<li><a href="#">8</a></li>
									<li><a href="#">9</a></li>
									<li><a href="#">10</a></li>
								</ol>
								<a class="btn_next btn_page" href="#"><img src="${context}/images/portal/main/btn_next.gif" alt="마지막 페이지" /></a>
								<a class="btn_last btn_page" href="#"><img src="${context}/images/portal/main/btn_last.gif" alt="목록 마지막 페이지" /></a>
							</div>
							<!-- /페이징 -->
						</div>
					</div>

					<div id="info_area3" style="display:none;">
						<div class="detail_wrap">
							<table class="detail_table_type2 sort_table" cellpadding="0" cellspacing="0" border="0" summary="로그인 일시, 로그인 ID, 사용자 명, 이메일, 전화번호 순의 표 입니다.">
								<caption>사용자 로그인 내역</caption>
								<colgroup>
									<col width="27%"/>
									<col width="13%"/>
									<col width="13%"/>
									<col width="27%"/>
								</colgroup>
								
								<thead>
									<tr>
										<th>
											<div class="th_wrap">로그인 유형
												<p>
													<a href="#"><img src="${context}/images/portal/main/btn_up.gif" alt="오름차순" /></a>
													<a href="#"><img src="${context}/images/portal/main/btn_down.gif" alt="내림차순" /></a>
												</p>
											</div>
										</th>

										<th>
											<div class="th_wrap">로그인 ID
												<p>
													<a href="#"><img src="${context}/images/portal/main/btn_up.gif" alt="오름차순" /></a>
													<a href="#"><img src="${context}/images/portal/main/btn_down.gif" alt="내림차순" /></a>
												</p>
											</div>
										</th>

										<th>
											<div class="th_wrap">사용자 명
												<p>
													<a href="#"><img src="${context}/images/portal/main/btn_up.gif" alt="오름차순" /></a>
													<a href="#"><img src="${context}/images/portal/main/btn_down.gif" alt="내림차순" /></a>
												</p>
											</div>
										</th>

										<th>
											<div class="th_wrap">이메일
												<p>
													<a href="#"><img src="${context}/images/portal/main/btn_up.gif" alt="오름차순" /></a>
													<a href="#"><img src="${context}/images/portal/main/btn_down.gif" alt="내림차순" /></a>
												</p>
											</div>
										</th>

										<th>
											<div class="th_wrap">전화번호
												<p>
													<a href="#"><img src="${context}/images/portal/main/btn_up.gif" alt="오름차순" /></a>
													<a href="#"><img src="${context}/images/portal/main/btn_down.gif" alt="내림차순" /></a>
												</p>
											</div>
										</th>
									</tr>
								</thead>
								
								<tbody>
									<tr>
										<td>2015-10-01 AM 08:32:28</td>
										<td>Abcdefg....</td>
										<td>홍길동</td>
										<td>emailAddr@domain.com</td>
										<td>031-0000-0000</td>
									</tr>

									<tr>
										<td>2015-10-26 PM 02:23:44</td>
										<td>xxxxxxxx</td>
										<td>xxxxx</td>
										<td>xxxxxxxxxxx@xxxxxx.xxx</td>
										<td>Xxx-xxxx-xxxx</td>
									</tr>
								</tbody>
							</table>

							<!-- 페이징 -->
							<div class="pageList">
								<a class="btn_first btn_page" href="#"><img src="${context}/images/portal/main/btn_first.gif" title="목록 첫 페이지" /></a>
								<a class="btn_pre btn_page" href="#"><img src="${context}/images/portal/main/btn_pre.gif" title="이전 페이지" /></a>
								<ol class="page_num">
									<li class="on"><a href="#">1</a></li>
									<li><a href="#">2</a></li>
									<li><a href="#">3</a></li>
									<li><a href="#">4</a></li>
									<li><a href="#">5</a></li>
									<li><a href="#">6</a></li>
									<li><a href="#">7</a></li>
									<li><a href="#">8</a></li>
									<li><a href="#">9</a></li>
									<li><a href="#">10</a></li>
								</ol>
								<a class="btn_next btn_page" href="#"><img src="${context}/images/portal/main/btn_next.gif" title="마지막 페이지" /></a>
								<a class="btn_last btn_page" href="#"><img src="${context}/images/portal/main/btn_last.gif" title="목록 마지막 페이지" /></a>
							</div>
							<!-- /페이징 -->
						</div>
					</div>
				</div>
			</div>
			<!-- /right_tile-->
		</div>

	</div>
	<!-- /contents_form -->
</body>
</html>