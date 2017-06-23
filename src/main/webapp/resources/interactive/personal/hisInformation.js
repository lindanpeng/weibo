$(function(){
	$("#toHisConcernedUserList").click(function(){
		var userId=$("#userId").val();
		location.href="/weibo/personal/hisConcernedUserList?userId="+userId;
	})
    $("#toHisFanList").click(function(){
		var userId=$("#userId").val();
		location.href="/weibo/personal/hisFanList?userId="+userId;
	})
		$("#toHisTwitterList").click(function(){
		var userId=$("#userId").val();
		location.href="/weibo/personal/hisTwitterList?userId="+userId;
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
	});
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