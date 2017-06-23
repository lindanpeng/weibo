$(function(){

   	$("#toLogin_btn").click(function(){
		location.href="/weibo/common/login";
	})
	$("#chat_btn,#follow_btn").click(function(){
		$("#popupbox").modal("toggle");
	})
})