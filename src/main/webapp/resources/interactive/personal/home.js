$(function(){
	document.getElementById("RightH").style.height = document.getElementById("MiddleH").offsetHeight + "px";
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
	    location.href="/weibo/personal/home?pageNum="+pageNum;	
	});
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
				   }
				   content+="</li>";
			   
		   }
           var twitterId=commentDtos[0].comment.twitterId;
		   $.post("/weibo/personal/getCommentAmountOfTwitter",{"twitterId":twitterId},function(commentsAmount){
			   $comment.prev(".twitter").find(".commentsAmount").text(commentsAmount);
		   })
		   $comment.find(".CommentList").append(content);

		   if(commentDtos.length>=5){
			   var more="<a href='javascript:'><li class='CommentButtom'><span class='CommentButtomA'>更多</span></li></a>";
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
			document.getElementById("RightH").style.height = document.getElementById("MiddleH").offsetHeight + "px";
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
	    		document.getElementById("RightH").style.height = document.getElementById("MiddleH").offsetHeight + "px";
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
    		document.getElementById("RightH").style.height = document.getElementById("MiddleH").offsetHeight + "px";
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
     		document.getElementById("RightH").style.height = document.getElementById("MiddleH").offsetHeight + "px";

     	   return;
			}
		 else
			 $comment.show();
           
           var twitterId=$(this).parent().parent().find(".twitterId").val();
			var userId=$(this).attr("userId");
			var myUserId=$("#myUserId").val();
			   $.get("/weibo/personal/getSomeComments",{"twitterId":twitterId},function(commentDtos){
		    	   updateComments(commentDtos,$comment);
		    		document.getElementById("RightH").style.height = document.getElementById("MiddleH").offsetHeight + "px";
		    	   if(userId==myUserId){
		    		   $.post("/weibo/personal/readCommentOfTwitter",{"twitterId":twitterId});
		    	   }
		       });

	   })

	 /**
	 * 查看更多评论
	 */
	$(".moveComment_btn").click(function(){
		var twitterId=$(this).parent().find(".twitterDto").attr("twitterId");
		window.open="/weibo/personal/singleTwitter?twitterId="+twitterId;
	})
	$(".delete_twitter").click(function(){
		event.stopPropagation();
		var twitterId=$(this).parent().find(".twitterId").val();
		var $twitter=$(this).parent();
		$.post("/weibo/personal/deleteTwitter",{"twitterId":twitterId},function(){
			$twitter.remove();
			document.getElementById("RightH").style.height = document.getElementById("MiddleH").offsetHeight + "px";

		})
	})
    $(".forward_btn").click(function(){
    	event.stopPropagation();
    	alert("开发中，敬请期待！");
    })
	/**
	 * 发表微博
	 */
	$("#publish_btn").click(function(){
		var twitterDto=new Object();
        var twitterPictures=new Array();
        $(".PostWBPicDetailL").each(function(){
        	var twitterPicture=new Object();
        	twitterPicture.pictureUrl=$(this).attr("src");
        	twitterPictures.push(twitterPicture);
        });
        var twitter=new Object();
        twitter.textContent=$("#textContent").val();
        twitter.userNickname=$("#nickname").val();
        var date=new Date();
        twitter.publicTime=Date.parse(date);
        twitter.userId=$("#userId").val();
        twitterDto.twitter=twitter;
        twitterDto.twitterPictures=twitterPictures;
        var jsonStr=JSON.stringify(twitterDto);
        $("#jsonStr").val(jsonStr);
		$("#publishForm").submit();
		
	})
	$("body").on("click",".imgDelete",function(){
		$(this).parent().remove();
		if($(".PostWBPicDetailL").length>=8)
		{
		 $("#post_btn").parent().show();
		}
		
		
	})
	$(".twitter").click(function(){
		var twitterId=$(this).find(".twitterId").val();
		location.href="/weibo/personal/singleTwitter?twitterId="+twitterId;
	})
	  $("#textContent").keydown(function(event){
		
    	if($("#textContent").val().length>=140&&event.keyCode!=8)
    	 {
    		return false;
    	 }
    })
    		var regx=/^.*@[^\s]*$/;
	$("#textContent").keyup(function(){
		var textContent=$("#textContent").val();

		  if(regx.test(textContent)){
			  $("#atPeopleTip").show();
		  }
		  else {
			  $("#atPeopleTip").hide();
		  }
	var amount=140;
	var writedcount=textContent.length;
	var surplus=amount-writedcount;
	$("#textTip").text(surplus);
})

	var uploader2=null;
	$.get("/weibo/common/getUpToken",function(token){
		 var uploader2= Qiniu.uploader({
				runtimes : 'html5,flash,html4', // 上传模式，依次退化
				browse_button : 'post_btn', // 上传选择的点选按钮，必需
				// 在初始化时，uptoken，uptoken_url，uptoken_func三个参数中必须有一个被设置
				// 切如果提供了多个，其优先级为uptoken > uptoken_url > uptoken_func
				// 其中uptoken是直接提供上传凭证，uptoken_url是提供了获取上传凭证的地址，如果需要定制获取uptoken的过程则可以设置uptoken_func
				uptoken:token, // uptoken是上传凭证，由其他程序生成
				get_new_uptoken : false, // 设置上传文件的时候是否每次都重新获取新的uptoken
				// downtoken_url: '/downtoken',
				// Ajax请求downToken的Url，私有空间时使用，JS-SDK将向该地址POST文件的key和domain，服务端返回的JSON必须包含url字段，url值为该文件的下载地址
				 unique_names: true,              // 默认false，key为文件名。若开启该选项，JS-SDK会为每个文件自动生成key（文件名）
				// save_key: true,                  // 默认false。若在服务端生成uptoken的上传策略中指定了sava_key，则开启，SDK在前端将不对key进行任何处理
				domain : 'http://odhh6aem6.bkt.clouddn.com/', // bucket域名，下载资源时用到，必需
				//container : 'uploadarea', // 上传区域DOM ID，默认是browser_button的父元素
				max_file_size : '100mb', // 最大文件体积限制
				flash_swf_url : 'path/of/plupload/Moxie.swf', //引入flash，相对路径
				max_retries : 3, // 上传失败最大重试次数
				dragdrop : true, // 开启可拖曳上传
				//drop_element : 'uploadarea', // 拖曳上传区域元素的ID，拖曳文件或文件夹后可触发上传
				chunk_size : '4mb', // 分块上传时，每块的体积
				auto_start:true,
				init : {
					'FileUploaded' : function(up, file, info) {
						
						// 每个文件上传成功后，处理相关的事情
						// 其中info是文件上传成功后，服务端返回的json，形式如：
						// {
						//    "hash": "Fh8xVqod2MQ1mocfI4S4KpRL6D98",
						//    "key": "gogopher.jpg"
						//  }
						// 查看简单反馈
					
						 var domain = up.getOption('domain');
						 var res =$.parseJSON(info);
						 var sourceLink = domain + res.key; //获取上传成功后的文件的Url	
											 
						 var ImgHtml="<li><img src='"+sourceLink+"' class='PostWBPicDetailL'><a href='javascript:' class='imgDelete'><span class='littleDetele'>一</span></a></li>";
						 if($(".PostWBPicDetailL").length>=8)
								{
							      $("#post_btn").parent().hide();
								}
						 $(".PostWBPicList").append(ImgHtml);	
							
					},
					'Error' : function(up, err, errTip) {
						alert("上传图片失败!");
						//上传出错时，处理相关的事情
					},
					'UploadComplete' : function() {
						
					},
				}
		});
	})	
	
	$(".imgWeiboBoxPicsTop").bind("click",function(){
	var img=$(this).attr("src")
	$(".ShowBigImg").attr("src",img);
	$("#ShowBigImgPartSwich").toggle();
	$("#hideBackgroudSwich").toggle();
	event.stopPropagation();
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

