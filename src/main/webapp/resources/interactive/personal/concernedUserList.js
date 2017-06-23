$(function(){
	  function refreshData(){
		  $.post("/weibo/personal/refreshData",function(dataWrapper){
			  if(dataWrapper.unReadCommentAmount==0){
				  $("#unReadCommentAmount").hide();
			  }
			  else{
				  $("#unReadCommentAmount").text(dataWrapper.unReadCommentAmount);
				  $("#unReadCommentAmount").show();
			  }
			  if(dataWrapper.unReadMessageAmount==0){
				  $("#unReadMessageAmount").hide();
			  }
			  else{
				  $("#unReadMessageAmount").text(dataWrapper.unReadMessageAmount);
				  $("#unReadMessageAmount").show();
			  }
			  if(dataWrapper.unReadAtmeAmount==0){
				  $("#unReadAtmeAmount").hide();
			  }
			  else{

				  $("#unReadAtmeAmount").text(dataWrapper.unReadAtmeAmount);
				  $("#unReadAtmeAmount").show();
			  }
		      if(dataWrapper.unReadCommentAmount+dataWrapper.unReadMessageAmount+dataWrapper.unReadAtmeAmount==0){
			      $(".newsNum").hide();
		      }
		      else
		      {
		    	  $(".newsNum").text(dataWrapper.unReadCommentAmount+dataWrapper.unReadMessageAmount+dataWrapper.unReadAtmeAmount);
		    	  $(".newsNum").show();
		      }
		  })
	  }
	 setInterval(refreshData,1500);
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
			  $button.parent().find(".allFollow_text").text("尚未关注");
			})

		}
		else{
			$.post("/weibo/personal/concernUser",{"concernedUserId":userId},function(data){
				$button.val("取消关注");
				$button.attr("isMyConcernedUser",1);
				  if(isMyFan==1)
				  {
					  $button.parent().find(".allFollow_text").text("互相关注");
					  $button.parent().find(".follow_icon").attr("src","/weibo/resources/img/follow_each.png"); 
				  }
				  else
				  {
					  $button.parent().find(".allFollow_text").text("已关注");
					  $button.parent().find(".follow_icon").attr("src","/weibo/resources/img/followed.png");
				  }
			})
		}
	})
	$(".page_selector").click(function(){
		var pageNum=$(this).attr("pageNum");
	    location.href="/weibo/personal/myConcernedUserList?pageNum="+pageNum;	
	})
	$(".user_portrait").click(function(){
		var userId=$(this).parent().find(".allFollow_btn").attr("userId");
		location.href="/weibo/personal/checkUserInformation?userId="+userId;
	})
})