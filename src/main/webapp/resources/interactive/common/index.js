$(function(){
	var validateUrl="/weibo/common/asyValidateUser";
	var homeUrl="/weibo/personal/home";
	var registerUrl="/weibo/common/register";
	var loginUrl="/weibo/common/login";
    $("#registerBtn").click(function(){
    	location.href=registerUrl;
    });

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
	    location.href="/weibo/common/index?pageNum="+pageNum;	
	});
	function updateComments(commentDtos,$comment){
	
		   $comment.find(".CommentLineOfList,.CommentButtom").remove();
			if(commentDtos.length==0)return;
		   var content="";
		   for(var i=0;i<commentDtos.length;i++){
				   content+="<li class='CommentLineOfList'> <a href='javascript:' class='checkUser' userId='"+commentDtos[i].fromUser.userId+"'>"+
				   commentDtos[i].fromUser.nickname+"</a>@<a href='javascript:' class='checkUser' userId='"+commentDtos[i].toUser.userId+"'>"+
				   commentDtos[i].toUser.nickname+"</a>：<span>"+commentDtos[i].comment.content+"</span>"+
				   "<br><span class='Com_Time'>"+ new Date(commentDtos[i].comment.commentTime).format('yyyy-MM-dd hh:mm:ss')+
				   "</span>&nbsp;<a href='javascript:void(0)' class='reply'  toUserId='"+commentDtos[i].fromUser.userId+"'>回复</a></li>";   
			   
		   }

		   $comment.find(".CommentList").append(content);
	        var twitterId=commentDtos[0].comment.twitterId;
		   document.getElementById("RightH").style.height = document.getElementById("MiddleH").offsetHeight + "px";
		   if(commentDtos.length>=5){
			   var more="<a href='/weibo/common/singleTwitter?twitterId="+twitterId+"'><li class='CommentButtom'><span class='CommentButtomA'>更多</span></li></a>";
			   $comment.find(".CommentList").append(more);
			   }
	   }
    $("#toLogin_btn").click(function(){
		location.href="/weibo/common/login";
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
     	  document.getElementById("RightH").style.height = document.getElementById("MiddleH").offsetHeight + "px";
     	   return;
			}
		 else
			 $comment.show();
           var twitterId=$(this).parent().parent().find(".twitterId").val();
			var userId=$(this).attr("userId");
			   $.get("/weibo/common/getSomeComments",{"twitterId":twitterId},function(commentDtos){
		    	   updateComments(commentDtos,$comment);
		       });
	   })
			$(".thumbsup_btn,.collect_btn").click(function(){
			event.stopPropagation();
			$("#popupbox").modal("toggle");
		})
	$("body").on("click",".Com_Button,.reply",function(){
		$("#popupbox").modal("toggle");
	});
    $(".forward_btn").click(function(){
    	event.stopPropagation();
    	alert("开发中，敬请期待！");
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
		$(".twitter").click(function(){
			var twitterId=$(this).find(".twitterId").val();
			location.href="/weibo/common/singleTwitter?twitterId="+twitterId;
		})
		$(".atPeople").click(function(){
	event.stopPropagation();
	var nickname=$(this).text().substring(1);
	location.href="/weibo/common/checkUserInformationByNickname?nickname="+nickname;
})
		document.getElementById("RightH").style.height = document.getElementById("MiddleH").offsetHeight + "px";
});