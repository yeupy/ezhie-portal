/* iframe 내부 색상변경 */
$('#iframe').load(function(){
	
	var colorArray = new Array("blue", "green", "orange", "red", "purple");

	if($("body").hasClass("blue") == true){
		$("#iframe").contents().find("body").addClass("blue_theme");
	}else if($("body").hasClass("green_theme") == true){
		$("#iframe").contents().find("body").addClass("green_theme");
	}else if($("body").hasClass("orange_theme") === true){
		$("#iframe").contents().find("body").addClass("orange_theme");
	}else if($("body").hasClass("red_theme") === true){
		$("#iframe").contents().find("body").addClass("red_theme");		//하단 else if 추가 - 최명훈(160617)
	}else if($("body").hasClass("purple_theme") === true){
		$("#iframe").contents().find("body").addClass("purple_theme");
	}

	$(".btn_theme li").click(function(){
		var i = $(this).index();
		
		$("body").removeClass();
		$("body").addClass(colorArray[i]+"_theme");
		$("#iframe").contents().find("body").removeClass();
		$("#iframe").contents().find("body").addClass(colorArray[i]+"_theme");

		$(this).siblings().children().css("border","1px solid transparent");
		$("."+colorArray[i]).css("border","1px solid #fff");
		

	})
});