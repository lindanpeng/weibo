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
 
	$(".page_selector").click(function(){
		var pageNum=$(this).attr("pageNum");
		var searchkey=$("#searchUserForm").find("input[name='searchKey']").val();
	    location.href="/weibo/common/searchTwitter?pageNum="+pageNum+"&searchKey="+searchKey;	
	});

	function updateComments(commentDtos,$comment){
		   $comment.find(".CommentLineOfList,.CommentButtom").remove();
			if(commentDtos.length==0)return;
		   var content="";
		   var twitterUserId=$comment.attr("twitterUserId");
		   for(var i=0;i<commentDtos.length;i++){
				   content+="<li class='CommentLineOfList'> <a href='javascript:' class='checkUser' userId='"+commentDtos[i].fromUser.userId+"'>"+
				   commentDtos[i].fromUser.nickname+"</a>@<a href='javascript:' class='checkUser' userId='"+commentDtos[i].toUser.userId+"'>"+
				   commentDtos[i].toUser.nickname+"</a>：<span>"+commentDtos[i].comment.content+"</span>"+
				   "<br><span class='Com_Time'>"+ new Date(commentDtos[i].comment.commentTime).format('yyyy-MM-dd hh:mm:ss')+
				   "</span>&nbsp;<a href='javascript:void(0)' class='reply' toUserId='"+commentDtos[i].fromUser.userId+"'>回复</a>"; 
				   if(twitterUserId==myUserId||commentDtos[i].comment.fromUserId==myUserId){
					   content+="&nbsp;<a href='javascript:void(0)' class='comDelete' commentId='"+commentDtos[i].comment.commentId+"'>删除</a>";
				   }
					 content+="</li>";
			   
		   }
        var twitterId=commentDtos[0].comment.twitterId;
		   $.post("/weibo/common/getCommentAmountOfTwitter",{"twitterId":twitterId},function(commentsAmount){
			   $comment.prev(".twitter").find(".commentsAmount").text(commentsAmount);
		   })
		   $comment.find(".CommentList").append(content);
		   if(commentDtos.length>=5){
			   var more="<a href='/weibo/common/singleTwitter?twitterId="+twitterId+"'><li class='CommentButtom'><span class='CommentButtomA'>更多</span></li></a>";
			   $comment.find(".CommentList").append(more);
			   }
	   }

	/**
	 * 获取评论列表
	 */		
	$(".comment_btn").bind("click",function(){
     var $comment=$(this).parent().parent().parent().next(".Comment");
		 if($comment.is(":visible")==true)
			{
  	   $comment.hide();
  	   return;
			}
		 else
			 $comment.show();
        
        var twitterId=$(this).parent().parent().find(".twitterId").val();
			   $.get("/weibo/common/getSomeComments",{"twitterId":twitterId},function(commentDtos){
		    	   updateComments(commentDtos,$comment);			    	  
		       });
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
			$(".thumbsup_btn,.collect_btn").click(function(){
			event.stopPropagation();
			$("#popupbox").modal("toggle");
		})
	$("body").on("click",".Com_Button,.reply",function(){
		$("#popupbox").modal("toggle");
	});
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
	$(".atPeople").click(function(){
	event.stopPropagation();
	var nickname=$(this).text().substring(1);
	location.href="/weibo/common/checkUserInformationByNickname?nickname="+nickname;
})
})



