$(function(){

	$(".buttonForm").each(function(){
		var myUserId=$("#myUserId").val();
		var userId=$(this).find(".allFollow_btn").attr("userId");
		if(myUserId==userId){
		$(this).hide();
		}
	})
	$(".page_selector").click(function(){
		var pageNum=$(this).attr("pageNum");
		var userId=$("#userId").val();
	    location.href="/weibo/common/hisFanList?pageNum="+pageNum+"&userId="+userId;	
	})
	$(".user_portrait").click(function(){
		var userId=$(this).parent().find(".allFollow_btn").attr("userId");
		location.href="/weibo/common/checkUserInformation?userId="+userId;
	})
		$("#toLogin_btn").click(function(){
		location.href="/weibo/common/login";
	})
	$(".allFollow_btn,#chat_btn,#follow_btn").click(function(){
		$("#popupbox").modal("toggle");
	})
})