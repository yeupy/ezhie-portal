/***** contents size *****/
$(window).resize(function () {
	var d_h = $(window).height();
	
	set_h = d_h - 94;
	$("#iframe").css("height", set_h);
	
	set_h = d_h - 26;
	$(".contents_form").css("height", set_h);

	set_h = d_h/1.3  - 10;
	$(".tile_type1").css("height", set_h);

	set_h = d_h/1.3  + 34;
	$(".tile_type2").css("height", set_h);

	set_h = d_h/2.6 - 16;
	$(".tile_type3").css("height", set_h);

	set_h = d_h/2.6 - 18;
	$(".tile_type4").css("height", set_h);

	set_h = d_h/2.6 - 65.5;
	$(".tile_type5").css("height", set_h);
// 최명훈 (160718)

	set_h = d_h/ 1.3 + 68;
	$(".regiWrap").css("height", set_h);	

	/*set_h = d_h/1.4 - 26;
	$(".tile_type6").css("height", set_h);

	set_h = d_h/2.7 - 60;
	$(".tile_type7").css("height", set_h);*/
// 최명훈 (160718)
}).resize();

$(document).ready(function () {
	var d_h = $(window).height();
	
	set_h = d_h - 94;
	$("#iframe").css("height", set_h);
	
	set_h = d_h - 26;
	$(".contents_form").css("height", set_h);
	
	set_h = d_h/1.3  - 10;
	$(".tile_type1").css("height", set_h);

	set_h = d_h/1.3  + 34;
	$(".tile_type2").css("height", set_h);

	set_h = d_h/2.6 - 16;
	$(".tile_type3").css("height", set_h);

	set_h = d_h/2.6 - 18;
	$(".tile_type4").css("height", set_h);

	set_h = d_h/2.6 - 65.5;
	$(".tile_type5").css("height", set_h);
// 최명훈 (160718)
	set_h = d_h/ 1.3 + 68;
	$(".regiWrap").css("height", set_h);

	set_h = d_h/1.4 - 26;
	$(".tile_type6").css("height", set_h);

	set_h = d_h/2.7 - 60;
	$(".tile_type7").css("height", set_h);
// /최명훈 (160718) -->
});
// 최명훈 (160718) -->
/* 레지스트리1 */
$(window).resize(function () {
	var c_h = $('.regiWrap').height();

	set_h = c_h - 28;
	$(".one_tile").css("height", set_h);
}).resize();

$(document).ready(function() {
	var c_h = $('.regiWrap').height();

	set_h = c_h - 28;
	$(".one_tile").css("height", set_h);
})
/* 레지스트리2 */
$(window).resize(function () {
	var cs_h = $('.one_tile').height();

	set_h = cs_h - 47;
	$(".section_type1").css("height", set_h);
}).resize();

$(document).ready(function() {
	var cs_h = $('.one_tile').height();

	set_h = cs_h - 47;
	$(".section_type1").css("height", set_h);
});

/* 레지스트리 주 컨텐츠 */
$(window).resize(function () {
	var css_h = $('.section_type1').height();

	set_h = css_h - 68;
	$(".tile_type6").css("height", set_h);

	set_h = css_h/2 - 69;
	$(".tile_type7").css("height", set_h);
}).resize();

$(document).ready(function() {
	var css_h = $('.section_type1').height();

	set_h = css_h - 68;
	$(".tile_type6").css("height", set_h);

	set_h = css_h/2 - 69;
	$(".tile_type7").css("height", set_h);
});
// /최명훈 (160718) -->
/***** scroll *****/
$(function(){
	$("#sub_body").niceScroll();
	$("textarea").niceScroll();
	$(".content_tile").niceScroll();
	$(".addr_list").niceScroll();
});

/***** tab *****/
var info_idx = 1;

function move_tab( idx ){
	
	$(" #info_tab" + info_idx ).removeClass( "on" );
	$(" #info_tab" + idx ).addClass( "on" );
	$(" #info_area" + info_idx ).css( "display", "none" );
	$(" #info_area" + idx ).css( "display", "" );

	info_idx = idx;
}

/***** GNB *****/
/* 최명훈(160615) */
/* 최명훈(160628) */
$(function(){
	$(".gnb li:last-child").find("div").addClass("last_div");

	$(".gnb > li > a").click(function(){
		idx = $(this).parent().index();
		$(".gnb > li").not(idx).removeClass("on");
		$(this).parent().addClass("on");
		$(".snb li").removeClass("on");
		$(this).siblings().find(".snb > li").first().addClass("on")
		$(".full_depth1_li li").removeClass("on");
		$(".full_depth1_li > a").parent().eq(idx).find("ul li").first().addClass("on");
	})
	
	$(".gnb > li").mouseover(function(){
		$(".gnb div").show();
		$(".bg_gnb_wrap").show();
	})

	$(".dep_1").mouseleave(function(){
		$(".gnb div").hide();
		$(".bg_gnb_wrap").hide();
	})

	$(".snb li").click(function(){
		p_idx = $(this).parent().parent().parent().index();
		idx = $(this).index();
		$(".gnb li").removeClass("on");
		$(this).parents(".gnb li").addClass("on");
		$(".snb li").removeClass("on");
		$(this).addClass("on");
		$(".full_depth1_li").find("li").removeClass("on");
		$(".full_depth1_li").eq(p_idx).find("ul li").eq(idx).addClass("on");
		
	})
/* /최명훈(160628) */
/* /최명훈(160615) */

/***** Profile *****/
	$(".btn_pro").click(function(){
		$(".profile_wrap").css("display","block");
	})
	$(".btn_close a").click(function(){
		$(".profile_wrap").css("display","none");
	})


/***** full_menu *****/
	$(".open_menu").click(function(){
		$(".total_menu_section").css("display","block");
	})

	$(".full_close").click(function(){
		$(".total_menu_section").css("display","none");
	})

	$(".full_depth1_li > a").click(function(){
		idx = $(this).parent().index();
		$(".dep_1").removeClass("on");
		$(".dep_1").eq(idx).addClass("on");
		$(".full_depth1_li li").removeClass("on");
		$(this).siblings().find("li").first().addClass("on");
		$(".snb li").removeClass("on");
		$(".gnb > li > a").parent().eq(idx).find("ul li").first().addClass("on");
		$(".total_menu_section").css("display","none");
	})

	$(".full_depth1_li li").click(function(){
		idx = $(this).index();
		p_idx = $(this).parent().parent().index();
		$(".full_depth1_li li").removeClass("on");
		$(this).addClass("on");
		$(".dep_1").removeClass("on");
		$(".dep_1").eq(p_idx).addClass("on")
		$(".snb li").removeClass("on");
		$(".dep_1").eq(p_idx).find(".snb li").eq(idx).addClass("on");
		$(".total_menu_section").css("display","none");
	})

/***** input[type=text], textarea 미입력 *****/
	$(".input_text, .textarea_text").blur(function(){
		if(this.value==""){
			$(this).css("border","1px solid red");
		}else{
			$(this).css("border","1px solid #e4e5e7");
		}
	});

/***** select 미선택 *****/
    $(".select_text").blur(function(){
		if($(this).find("option:selected").index()==0){
			$(this).css("border","1px solid red");
		}else{
			$(this).css("border","1px solid #e4e5e7");
		}
	})

/***** 오름차순, 내림차순 *****/
	$(".sort_table th").click(function(){
		if($(this).find("p a:first-child").css("display")=="none"){
			$(this).find("p a:first-child").show().siblings("p a:last-child").hide();
		}else{
			$(this).find("p a:first-child").hide().siblings("p a:last-child").show();
		}
	})

/***** file_upload *****/
	var file = $("#up_file");

	file.on("change",function(){
		if(window.FileReader){
			var filename = $(this)[0].files[0].name;
		}

		$(this).siblings("#upload_list").val(filename);
	})

	var btn_click = $("#btn_upload");

	btn_click.on("click",function(){
		if($(".upload_chk").css("visibility")=="hidden"){
			$(".upload_chk").css("visibility","visible");
		}
	})

/***** 체크박스 체크여부 *****/
	$('.choice_list').click(function(){
		if($(this).is(':checked')){
			$(this).parent().parent().addClass('color');
		}else{
			$(this).parent().parent().removeClass('color');
		}
	})
/***** 탭 메뉴 *****/
	$('.sub_tab li a').click(function(){
		var i = $(this).parent().index();
		
		$(this).parent().siblings().removeClass('on');
		$(this).parent().addClass('on');
		$('.detail_wrap').hide();
		$('.detail_wrap:eq('+ i +')').show();
	})
})