$(function(){
	$(".buttonForm").each(function(){
		var myUserId=$("#myUserId").val();
		var userId=$(this).find(".allFollow_btn").attr("userId");
		if(myUserId==userId){
		$(this).hide();
		}
	})
	$(".allFollow_btn").click(function(){
		var isMyConcernedUser=$(this).attr("isMyConcernedUser");
	    var isMyFan=$(this).attr("isMyFan");
		var userId=$(this).attr("userId");
		
		var $button=$(this);
		if(isMyConcernedUser==1){
			$.post("/weibo/personal/cancelConcern",{"concernedUserId":userId},function(){
			  $button.val("设为关注");
			  $button.attr("isMyConcernedUser",0);
			  $button.parent().find(".follow_icon").attr("src","/weibo/resources/img/unFollow.png");
			  $button.parent().find(".allFollow_text").text("未关注");
			})

		}
		else{
			$.post("/weibo/personal/concernUser",{"concernedUserId":userId},function(){
				$button.val("取消关注");
				$button.attr("isMyConcernedUser",1);
				$button.parent().find(".follow_icon").attr("src","/weibo/resources/img/followed.png");
				$button.parent().find(".allFollow_text").text("已关注");
			})
		}
	})
	$(".page_selector").click(function(){
		var pageNum=$(this).attr("pageNum");
		var userId=$("#userId").val();
	    location.href="/weibo/personal/hisFanList?pageNum="+pageNum+"&userId="+userId;	
	})
	$(".user_portrait").click(function(){
		var userId=$(this).parent().find(".allFollow_btn").attr("userId");
		if(userId==myUserId)
			location.href="/weibo/personal/personalInformation";
		else
		location.href="/weibo/personal/checkUserInformation?userId="+userId;
		location.href="/weibo/personal/checkUserInformation?userId="+userId;
	})
		$("#follow_btn").click(function(){
		var isMyConcernedUser=$("#isMyConcernedUser").val();
		var userId=$("#userId").val();
		
		var $button=$(this);
		if(isMyConcernedUser==1){
			$.post("/weibo/personal/cancelConcern",{"concernedUserId":userId},function(){
			  $button.text("设为关注");
			  $("#isMyConcernedUser").val(0);
			})
		}
		else{
			$.post("/weibo/personal/concernUser",{"concernedUserId":userId},function(){
				$button.text("取消关注");
				$("#isMyConcernedUser").val(1);
			})
		}
	})
	$("#chat_btn").click(function(){
		var nickname=$("#checkedUserName").text();
		$("#receiver_name").text(nickname);
	})
	$("#send_btn").click(function(){	
		var toUserId=$("#userId").val();
		var content=$("#messageContent").val();
		$.post("/weibo/personal/isFriend",{"userId":toUserId},function(isFriend){
			if(isFriend){
				 $.post("/weibo/personal/sendMessage",{"toUserId":toUserId,"content":content},function(){
			         alert("发送成功！");	
				  });
			}
			else{
				alert("你和对方尚未成为好友，不能发送私信！");
			}
		})
		    $("#messageContent").val("");
		  $("#hideBackgroudSwich").toggle();
			$("#chatPart").toggle();
	})
	$("#toChat_btn").click(function(){
		window.open("/weibo/personal/chat");
		$("#hideBackgroudSwich").toggle();
		$("#chatPart").toggle();
	});
	$("#chat_btn,.closeChat").click(function(){
		$("#hideBackgroudSwich").toggle();
		$("#chatPart").toggle();
	})
})