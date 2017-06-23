$(function(){
	document.getElementById("left_con").style.height = document.getElementById("right_con").offsetHeight + "px";
	var $thumbsup;
	$("#closeComment").click(function(){
	$("#hideBackgroudSwich").toggle();
	$("#commentPartSwich").toggle();
})
    $(".reply").click(function(){
    	$("#commentPartSwich").show();
		$("#hideBackgroudSwich").show();
		$thumbsup=$(this).closest("li").find(".thumbsup");
    })
    $("#reply_btn").click(function(){
    	var object=new Object();
		object.commentTime=Date.parse(new Date());
		object.fromUserId=$("#myUserId").val();
		object.toUserId=$thumbsup.attr("userId");
		object.content=$("#replyContent").val();
		object.twitterId=$thumbsup.attr("twitterId");
		object.twitterUserId=$thumbsup.attr("twitterUserId");
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
	    location.href="/weibo/personal/thumbsupList?pageNum="+pageNum;	
	});
})