$(function(){
	document.getElementById("searchHistory").style.height = document.getElementById("userList").offsetHeight-document.getElementById("rightPart1").offsetHeight+ "px";

	$(".buttonForm").each(function(){
		var myUserId=$("#myUser").attr("myUserId");
		var userId=$(this).closest("li").find(".searchedUser").attr("searchedUserId");
		if(myUserId==userId){
		$(this).hide();
		}
	});
	$(".allFollow_btn").click(function(){
		var $father=$(this).closest("li");
		var $searchedUser=$father.find(".searchedUser");
		var isMyConcernedUser=$searchedUser.attr("isMyConcernedUser");
	    var isMyFan=$searchedUser.attr("isMyFan");
		var userId=$searchedUser.attr("searchedUserId");
		
		var $button=$(this);
		if(isMyConcernedUser==1){
			$.post("/weibo/personal/cancelConcern",{"concernedUserId":userId},function(){
			  $button.val("设为关注");
			  $searchedUser.attr("isMyConcernedUser",0);
			  $father.find(".follow_icon").attr("src","/weibo/resources/img/unFollow.png");
			  $father.find(".allFollow_text").text("未关注");
			})

		}
		else{
			$.post("/weibo/personal/concernUser",{"concernedUserId":userId},function(){
				$button.val("取消关注");
				$searchedUser.attr("isMyConcernedUser",1);
				$father.find(".follow_icon").attr("src","/weibo/resources/img/followed.png");
				$father.find(".allFollow_text").text("已关注");
			})
		}
	})
	$(".page_selector").click(function(){
		var pageNum=$(this).attr("pageNum");
		var searchkey=$("#searchUserForm").find("input[name='searchKey']").val();
	    location.href="/weibo/personal/searchUser?pageNum="+pageNum+"&searchKey="+key;	
	})
	$(".user_portrait").click(function(){
		var userId=$(this).closest("li").find(".searchedUser").attr("searchedUserId");
		location.href="/weibo/personal/checkUserInformation?userId="+userId;

	})
	$("#searchUser_btn").click(function(){

		if($("#searchUserForm").find("input[name='searchKey']").val()=="")
			alert("请输入搜索内容！");
		else
		$("#searchUserForm").submit();
	})
	$("#clear_searchHistory").click(function(){
		$.get("/weibo/personal/clearSearchRecord",function(){
			$(".searchKey").remove();
		})
	})
})