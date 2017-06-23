$(function(){
	document.getElementById("searchHistory").style.height = document.getElementById("userList").offsetHeight-document.getElementById("rightPart1").offsetHeight+ "px";

	Date.prototype.format = function(format) {
	    var o = {
	            "M+" : this.getMonth() + 1, //month
	            "d+" : this.getDate(), //day
	            "h+" : this.getHours(), //hour
	            "m+" : this.getMinutes(), //minute
	            "s+" : this.getSeconds(), //second
	            "q+" : Math.floor((this.getMonth()+3)/3), //quarter
	            "S"  : this.getMilliseconds() //millisecond
	    }
	    if (/(y+)/.test(format)) {
	        format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	        }
	    for ( var k in o) if (new RegExp("(" + k + ")").test(format)) {
	        format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]: ("00" + o[k]).substr(("" + o[k]).length));
	    }
	        return format;
	}
 var THUMBSUPED_ICON="/weibo/resources/img/thumbsup2.png";
 var UNTHUMBSUPED_ICON="/weibo/resources/img/thumbsup1.png";
 var COLLECTED_ICON="/weibo/resources/img/collect2.png";
 var UNCOLLECTED_ICON="/weibo/resources/img/collect1.png";
 
	$(".page_selector").click(function(){
		var pageNum=$(this).attr("pageNum");
		var searchkey=$("#searchUserForm").find("input[name='searchKey']").val();
	    location.href="/weibo/personal/searchTwitter?pageNum="+pageNum+"&searchKey="+searchKey;	
	});
	/**
	 * 点赞
	 */
	$(".thumbsup_btn").click(function(){
		event.stopPropagation();
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
		event.stopPropagation();
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
	})	
	function updateComments(commentDtos,$comment){
		   $comment.find(".CommentLineOfList,.CommentButtom").remove();
			if(commentDtos.length==0)return;
		   var content="";
		   var twitterUserId=$comment.attr("twitterUserId");
		   var myUserId=$("#myUserId").val();
		   for(var i=0;i<commentDtos.length;i++){
				   content+="<li class='CommentLineOfList'> <a href='javascript:' class='checkUser' userId='"+commentDtos[i].fromUser.userId+"'>"+
				   commentDtos[i].fromUser.nickname+"</a>@<a href='javascript:' class='checkUser' userId='"+commentDtos[i].toUser.userId+"'>"+
				   commentDtos[i].toUser.nickname+"</a>：<span>"+commentDtos[i].comment.content+"</span>"+
				   "<br><span class='Com_Time'>"+ new Date(commentDtos[i].comment.commentTime).format('yyyy-MM-dd hh:mm:ss')+
				   "</span>&nbsp;<a href='javascript:void(0)' class='reply' toUserId='"+commentDtos[i].fromUser.userId+"'>回复</a>"; 
				   if(twitterUserId==myUserId||commentDtos[i].comment.fromUserId==myUserId){
					   content+="&nbsp;<a href='javascript:void(0)' class='comDelete' commentId='"+commentDtos[i].comment.commentId+"'>删除</a>";
					 content+="</li>";
				   }
			   
		   }
           var twitterId=commentDtos[0].comment.twitterId;
		   $.post("/weibo/personal/getCommentAmountOfTwitter",{"twitterId":twitterId},function(commentsAmount){
			   $comment.prev(".twitter").find(".commentsAmount").text(commentsAmount);
		   })
		   $comment.find(".CommentList").append(content);
			document.getElementById("searchHistory").style.height = document.getElementById("userList").offsetHeight-document.getElementById("rightPart1").offsetHeight+ "px";

		   if(commentDtos.length>=5){
			   var more="<a href='/weibo/personal/singleTwitter?twitterId="+twitterId+"'><li class='CommentButtom'><span class='CommentButtomA'>更多</span></li></a>";
			   $comment.find(".CommentList").append(more);
			   }
	   }
	var $reply=null;
	$(".Comment").on("click",".checkUser",function(){
		var userId=$(this).attr("userId");
		window.open("/weibo/personal/checkUserInformation?userId="+userId);
	})
	$(".Comment").on("click",".reply",function(){
		$("#commentPartSwich").show();
		$("#hideBackgroudSwich").show();
		$reply=$(this);
	})
	$(".Comment").on("click",".comDelete",function(){
		var $comment=$(this).parent().parent().parent();
		var commentId=$(this).attr("commentId");
		var twitterId=$(this).parent().parent().attr("twitterId");
		$.post("/weibo/personal/deleteComment",{"commentId":commentId,"twitterId":twitterId},function(commentDtos){
			updateComments(commentDtos,$comment);
			document.getElementById("searchHistory").style.height = document.getElementById("userList").offsetHeight-document.getElementById("rightPart1").offsetHeight+ "px";

		})
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
		var $comment=$reply.parent().parent().parent();
		var jsonStr=JSON.stringify(object);
	       $.post("/weibo/personal/comment",{"jsonStr":jsonStr},function(commentDtos){
	    	   $("#replyContent").val("");
	    	   $("#commentPartSwich").hide();
	   		   $("#hideBackgroudSwich").hide();
	    	   updateComments(commentDtos,$comment);
	    		document.getElementById("searchHistory").style.height = document.getElementById("userList").offsetHeight-document.getElementById("rightPart1").offsetHeight+ "px";

	       });
	})
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
		var $comment=$(this).parent().parent().parent();
		$board=$(this).parent().find(".Com_width");
       $.post("/weibo/personal/comment",{"jsonStr":jsonStr},function(commentDtos){
    	   $board.val("");
    	   updateComments(commentDtos,$comment);
    		document.getElementById("searchHistory").style.height = document.getElementById("userList").offsetHeight-document.getElementById("rightPart1").offsetHeight+ "px";

       });
		
	})
	/**
	 * 获取评论列表
	 */	
	$(".comment_btn").bind("click",function(){
		event.stopPropagation();
        var $comment=$(this).parent().parent().parent().next(".Comment");
		 if($comment.is(":visible")==true)
			{
     	   $comment.hide();
     		document.getElementById("searchHistory").style.height = document.getElementById("userList").offsetHeight-document.getElementById("rightPart1").offsetHeight+ "px";

     	   return;
			}
		 else
			 $comment.show();
           
           var twitterId=$(this).parent().parent().find(".twitterId").val();
			var userId=$(this).attr("userId");
			var myUserId=$("#myUserId").val();
			   $.get("/weibo/personal/getSomeComments",{"twitterId":twitterId},function(commentDtos){
		    	   updateComments(commentDtos,$comment);
		    		document.getElementById("searchHistory").style.height = document.getElementById("userList").offsetHeight-document.getElementById("rightPart1").offsetHeight+ "px";

		    	   if(userId==myUserId){
		    		   $.post("/weibo/personal/readCommentOfTwitter",{"twitterId":twitterId});
		    	   }
		       });
	   })
	$(".delete_twitter").click(function(){
		event.stopPropagation();
		var twitterId=$(this).parent().find(".twitterId").val();
		var $twitter=$(this).parent();
		$.post("/weibo/personal/deleteTwitter",{"twitterId":twitterId},function(){
			$twitter.remove();
			document.getElementById("searchHistory").style.height = document.getElementById("userList").offsetHeight-document.getElementById("rightPart1").offsetHeight+ "px";
		})
	})
    $(".forward_btn").click(function(){
    	event.stopPropagation();
    	alert("开发中，敬请期待！");
    })
    $("#searchTwitter_btn").click(function(){

		if($("#searchTwitterForm").find("input[name='searchKey']").val()=="")
			alert("请输入搜索内容！");
		else
		$("#searchTwitterForm").submit();
	})
		$("#clear_searchHistory").click(function(){
		$.get("/weibo/personal/clearSearchRecord",function(){
			$(".searchKey").remove();
		})
	})	
		$(".twitter").click(function(){
		var twitterId=$(this).find(".twitterId").val();
		location.href="/weibo/personal/singleTwitter?twitterId="+twitterId;
	})
	$(".imgWeiboBoxPicsTop").bind("click",function(){
		event.stopPropagation();
	var img=$(this).attr("src")
	$(".ShowBigImg").attr("src",img);
	$("#ShowBigImgPartSwich").toggle();
	$("#hideBackgroudSwich").toggle();
})
$(".forwardTickCloseTick1").bind("click",function(){
	$("#ShowBigImgPartSwich").hide();
	$("#hideBackgroudSwich").hide();
})
$("#postImg_btn").click(function(){
	$("#PostWBPicBox1").toggle();
})
$("#closeComment").click(function(){
	$("#hideBackgroudSwich").toggle();
	$("#commentPartSwich").toggle();
})
$(".atPeople").click(function(){
	event.stopPropagation();
	var nickname=$(this).text().substring(1);
	location.href="/weibo/personal/checkUserInformationByNickname?nickname="+nickname;
})
})
