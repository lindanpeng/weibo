$(function(){
	document.getElementById("RightH").style.height = document.getElementById("MiddleH").offsetHeight + "px";

	 var THUMBSUPED_ICON="/weibo/resources/img/thumbsup2.png";
	 var UNTHUMBSUPED_ICON="/weibo/resources/img/thumbsup1.png";
	 var COLLECTED_ICON="/weibo/resources/img/collect2.png";
	 var UNCOLLECTED_ICON="/weibo/resources/img/collect1.png";
	$(".imgWeiboBoxPicsTop").bind("click",function(){
		var img=$(this).attr("src")
		$(".ShowBigImg").attr("src",img);
		$("#ShowBigImgPartSwich").toggle();
		$("#hideBackgroudSwich").toggle();
	})
	$(".forwardTickCloseTick1").bind("click",function(){
		$("#ShowBigImgPartSwich").hide();
		$("#hideBackgroudSwich").hide();
	})
	$("#closeComment").click(function(){
		$("#hideBackgroudSwich").toggle();
		$("#commentPartSwich").toggle();
	})
	
	$(".Comment").on("click",".checkUser",function(){
		var userId=$(this).attr("userId");
		window.open("/weibo/common/checkUserInformation?userId="+userId);
	})
		$("#chat_btn,#follow_btn").click(function(){
			$("#popupbox").modal("toggle");
		})
	       $(".forward_btn").click(function(){
	    	   event.stopPropagation();
    	alert("开发中，敬请期待！");
    })
	$(".page_selector").click(function(){
		var pageNum=$(this).attr("pageNum");
		var twitterId=$("#twitterId").val();
	    location.href="/weibo/personal/singleTwitter?pageNum="+pageNum+"&twitterId="+twitterId;	
	});
	var $reply=null;
	$(".Comment").on("click",".reply",function(){
		$("#commentPartSwich").show();
		$("#hideBackgroudSwich").show();
		$reply=$(this);
	})
	/**
	 * 点赞
	 */
	$(".thumbsup_btn").click(function(){
		var thumbsuped=$(this).attr("thumbsuped");
		var twitterId=$(this).parent().parent().find(".twitterId").val();
		var $thumbsup_btn=$(this);
		var thumbsupedUserId=$(this).parent().parent().find(".userId").val();
		var $thumbsupAmount=$thumbsup_btn.find(".thumbsupAmount");
		
		if(thumbsuped=="true"){
			$thumbsup_btn.attr("thumbsuped","false");
			$.post("/weibo/personal/cancelThumbsup",{"twitterId":twitterId,},function(){
				$thumbsup_btn.find(".thumbsup").attr("src",UNTHUMBSUPED_ICON);
				updateThumbsupAmount($thumbsupAmount);
			})
		}
		else{
			$thumbsup_btn.attr("thumbsuped","true");
			$.post("/weibo/personal/thumbsup",{"twitterId":twitterId,"thumbsupedUserId":thumbsupedUserId},function(){
				$thumbsup_btn.find(".thumbsup").attr("src",THUMBSUPED_ICON);
				updateThumbsupAmount($thumbsupAmount);
			})
		}
		event.stopPropagation();
		
	})
	function updateThumbsupAmount($thumbsupAmount){
		var twitterId=$thumbsupAmount.parent().parent().parent().find(".twitterId").val();
		$.get("/weibo/personal/getThumbsupAmountOfTwitter",{"twitterId":twitterId},function(thumbsupAmount){
			$thumbsupAmount.text(thumbsupAmount);
		})
	}
	/**
	 * 收藏
	 */
	$(".collect_btn").click(function(){
		var collected=$(this).attr("collected");
		var twitterId=$(this).parent().parent().find(".twitterId").val();
		$collect_btn=$(this);
		if(collected=="true"){
			$collect_btn.attr("collected","false");
			$.post("/weibo/personal/cancelCollect",{"twitterId":twitterId},function(){
				$collect_btn.find(".collect").attr("src",UNCOLLECTED_ICON);

			})
		}
		else{
			$collect_btn.attr("collected","true");
			$.post("/weibo/personal/collectTwitter",{"twitterId":twitterId},function(){
				$collect_btn.find(".collect").attr("src",COLLECTED_ICON);
			})
		}
		event.stopPropagation();
	})	
/*	$(".Comment").on("click",".comDelete",function(){
		var $comment=$(this).parent().parent().parent();
		var commentId=$(this).attr("commentId");
		var twitterId=$(this).parent().parent().attr("twitterId");
		$.post("/weibo/personal/deleteComment",{"commentId":commentId,"twitterId":twitterId},function(commentDtos){
			updateComments(commentDtos,$comment);
		})
	})*/
	/**
	 *发表评论
	 */
	$(".Com_Button").click(function(){
		var object=new Object();
		object.content=$(this).parent().find(".Com_width").val();
		object.twitterId=$(this).parent().parent().attr("twitterId");
		object.toUserId=$(this).attr("toUserId");
		object.commentTime=Date.parse(new Date());
		object.fromUserId=$("#myUserId").val();
		object.twitterUserId=$(this).attr("toUserId");
		if(object.fromUserId==object.twitterUserId){
			object.isRead=1;
		}
		else{
			object.isRead=0;
		}
		var jsonStr=JSON.stringify(object);
		$("#jsonStr").val(jsonStr);
		$("#commentForm").submit();
	})
	$("#reply_btn").click(function(){
		var object=new Object();
		object.commentTime=Date.parse(new Date());
		object.fromUserId=$("#myUserId").val();
		object.toUserId=$reply.attr("toUserId");
		object.content=$("#replyContent").val();
		object.twitterId=$reply.parent().parent().attr("twitterId");
		object.twitterUserId=$reply.parent().parent().parent().attr("twitterUserId");
		if(object.fromUserId==object.twitterUserId||object.fromUserId==object.toUserId){
			object.isRead=1;
		}
		else{
			object.isRead=0;
		}
		var jsonStr=JSON.stringify(object);
		$("#jsonStr").val(jsonStr);
		$("#commentForm").submit();
	});
	$(".atPeople").click(function(){
		event.stopPropagation();
		var nickname=$(this).text().substring(1);
		location.href="/weibo/personal/checkUserInformationByNickname?nickname="+nickname;
	})
})