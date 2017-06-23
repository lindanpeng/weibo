$(function(){
	document.getElementById("searchHistory").style.height = document.getElementById("userList").offsetHeight-document.getElementById("rightPart1").offsetHeight+ "px";


	$(".page_selector").click(function(){
		var pageNum=$(this).attr("pageNum");
		var searchkey=$("#searchUserForm").find("input[name='searchKey']").val();
	    location.href="/weibo/common/searchUser?pageNum="+pageNum+"&searchKey="+key;	
	})
	$(".user_portrait").click(function(){
		var userId=$(this).closest("li").find(".searchedUser").attr("searchedUserId");
		location.href="/weibo/common/checkUserInformation?userId="+userId;

	})
	$("#searchUser_btn").click(function(){

		if($("#searchUserForm").find("input[name='searchKey']").val()=="")
			alert("请输入搜索内容！");
		else
		$("#searchUserForm").submit();
	})
	$(".allFollow_btn").click(function(){
		$("#popupbox").modal("toggle");
	})
})