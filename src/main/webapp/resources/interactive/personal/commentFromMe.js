$(function(){
	document.getElementById("left_con").style.height = document.getElementById("right_con").offsetHeight + "px";
	var $comment;
	$("#closeComment").click(function(){
	$("#hideBackgroudSwich").toggle();
	$("#commentPartSwich").toggle();
})
    $(".rep").click(function(){
    	$("#commentPartSwich").show();
		$("#hideBackgroudSwich").show();
		$comment=$(this).closest("li").find(".comment");
    })
    $("#reply_btn").click(function(){
    	var object=new Object();
		object.commentTime=Date.parse(new Date());
		object.fromUserId=$("#myUserId").val();
		object.toUserId=$comment.attr("userId");
		object.content=$("#replyContent").val();
		object.twitterId=$comment.attr("twitterId");
		object.twitterUserId=$comment.attr("twitterUserId");
		if(object.fromUserId==object.twitterUserId||object.fromUserId==object.toUserId){
			object.isRead=1;
		}
		else{
			object.isRead=0;
		}
		var jsonStr=JSON.stringify(object);
	       $.post("/weibo/personal/comment",{"jsonStr":jsonStr},function(commentDtos){
	    	   $("#replyContent").val("");
	    	   $("#commentPartSwich").hide();
	   		   $("#hideBackgroudSwich").hide();
	       });
    })
	$(".page_selector").click(function(){
		var pageNum=$(this).attr("pageNum");
	    location.href="/weibo/personal/commentFromMe?pageNum="+pageNum;	
	});
})