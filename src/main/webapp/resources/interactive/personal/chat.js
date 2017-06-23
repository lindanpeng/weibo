$(function(){
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
	var masterPortrait=$("#myPortrait").attr("src");
	$(".contacts").on('click','.people',function(){
		if($(".contacts").find(".selectedFriend").length>0)
			$(".contacts").find(".selectedFriend").removeClass("selectedFriend");
		$(this).addClass("selectedFriend");
		var friendUserId=$(this).attr("friendUserId");
		var userPortrait=$(this).find(".masterimg").attr("src");
		var $friend=$(this);
		$.post("/weibo/personal/getMessages",{"friendUserId":friendUserId},function(messages){

			var content="";
			for(var i=0;i<messages.length;i++){
				if(messages[i].fromUserId==friendUserId){
					content+="<div class='friends'><img class='friendpicture' src='"+userPortrait+"'><div class='triangle'></div>"
					+"<div class='friendsword'><b>"+messages[i].content+"</b></div></div>";
				}
				else{
					content+="<div class='masters'><img class='masterpicture' src='"+masterPortrait+"'><div class='triangle2'></div>"
					+"<div class='mastersword'><b>"+messages[i].content+"</b></div></div>";
				}
			}
			
			  $("#chat_content").html(content);
			  changePerson($friend);
		})
	})
	$("#autocomplete").keyup(function(e){
		var nickname=$(this).val();
		if(nickname==""){			  
          $(".hide_per").hide();
          $(".contacts").show();
		}else{
			 $(".hide_persons").addClass("hide_per");
	         $(".hide_persons").removeClass("hide_persons");
	          $(".contacts").hide();
	          $(".hide_per").show();
	   $.get("/weibo/personal/searchFriend",{"nickname":nickname},function(friends){
		  if(friends.length>0){
			  console.log(friends.length);
		   var content="";
		   for(var i=0;i<friends.length;i++){
			   content+="<li friendUserId='"+friends[i].userId+"'><img class='find_masterimg' src='"+friends[i].portraitUrl
			   +"'><span class='name'>"+friends[i].nickname+"</span></li>";
		   }
		   $(".not_find").hide();
		   $(".contacts3").html(content);
		   $(".contacts3").show();
		  }
		  else{
			  console.log("show");
			  $(".contacts3").hide();
			  $(".not_find").show();
		  }
	   });
		}
	});
   $(".contacts3").on("click","li",function(){
	   var flag=false;
	   $friend=$(this);
	   $(".hide_per").hide();
       $(".contacts").show();
	   $(".people").each(function(){
			if($(this).attr("friendUserId")==$friend.attr("friendUserId"))
				{
				$(".contacts").prepend($(this));
				changePerson($(this));
				$(this).click();
				flag=true;
				}
		})
		if(flag)return false;
	   var content="<li class='people' friendUserId='"+$friend.attr("friendUserId")+"'><img class='masterimg'"+
		"src='"+$friend.find(".find_masterimg").attr("src")+"'><span class='unread'>0</span><span class='name'>"+
		$friend.find(".name").text()+"</span><div class='show'></div></li>";
	    $(".contacts").prepend(content);
	    $contact=$(".people:first");
	    $("#chat_content").html("");
	    changePerson($contact);
   });
   /**
    * 切换聊天联系人时调用
    * 
    */
    function changePerson($contact){
    	$(".hide_div").hide();
	    $(".con2").removeClass("con2");
        $contact.addClass("con2");
        $contact.removeClass('wait');    
        $(".welcome").hide();
        $(".containt").show();
        $contact.find(".unread").remove();
        $(".selectedFriend").removeClass("selectedFriend");
        $contact.addClass("selectedFriend");
		$("#userName").text($contact.find(".name").text());
        $("#chat_content").scrollTop($("#chat_content")[0].scrollHeight);
    }
		function refreshConversation(){
	    	 var friendUserId=$(".contacts").find(".selectedFriend").attr("friendUserId");
	      $.post("/weibo/personal/refreshConversation",{"chatingFriendId":friendUserId},function(conversationDtos){
	    	 if(conversationDtos==false)
	    		  return;
		    	for(var i=0;i<conversationDtos.length;i++){
					$(".people").each(function(){
						if($(this).attr("friendUserId")==conversationDtos[i].friend.user.userId)
							{
							  $(this).remove();
							}
					})
				}
				 var content="";
				for(var i=0;i<conversationDtos.length;i++){
				 content+="<li class='people' friendUserId='"+conversationDtos[i].friend.user.userId+"'><img class='masterimg'"+
					"src='"+conversationDtos[i].friend.user.portraitUrl+"'><span class='unread'>"+conversationDtos[i].unReadMessageAmount
					+"</span><span class='name'>"+conversationDtos[i].friend.user.nickname+"</span>"+" <div class='li_time'>"+
					new Date(conversationDtos[i].latestMessage.sendTime).format("hh:mm")+"</div>"+"<div class='show'>"+
					conversationDtos[i].latestMessage.content+"</div></li>";
		    	}
				$(".contacts").prepend(content);
				$(".people").each(function(){
					if($(this).attr("friendUserid")==friendUserId)
						{
						$(this).find(".unread").remove();
						$(this).addClass("selectedFriend");
					    $(this).addClass("con2");
				        $(this).removeClass('wait');    
				        $("#chat_content").append("<div class='friends'><img class='friendpicture' src='"+$(this).find(".masterimg").attr("src")
				        		+"'><div class='triangle'></div>"+"<div class='friendsword'><b>"+$(this).find(".show").text()+"</b></div></div>");
				        $("#chat_content").scrollTop($("#chat_content")[0].scrollHeight);
						}
				})
		});
	}
	
	setInterval(refreshConversation,1000);
/*	setInterval(getUnReadMessages,1000);*/
	function sendMessage(){
	      if ($('#txt').val()=='') {
	            return;
	        }
          var message=$('#txt').val();
          var $selectedFriend=$(".contacts").find(".selectedFriend");
          var toUserId=$selectedFriend.attr("friendUserId");
          var userId=$("#userId").val();
         
          $.post("/weibo/personal/sendMessage",{"toUserId":toUserId,"content":message},function(){
          	var content="<div class='masters'><img class='masterpicture' src='"+masterPortrait+"'><div class='triangle2'></div>"
				+"<div class='mastersword'><b>"+message+"</b></div></div>";
          	  $('#chat_content').append(content);
     	        //清空输入框
     	         $('#txt').val('');
     	       $selectedFriend.find(".show").text(message);
		        $("#chat_content").scrollTop($("#chat_content")[0].scrollHeight);
          })
          
	}
	
	$("#send").click(function(){
	
       sendMessage();
	})
	
$("#txt").keydown(function(e){
		if(e.keyCode == 13 && e.ctrlKey){
      $("#txt").val($("#txt").val()+"\n");
}else if(e.keyCode == 13){
//避免回车键换行
e.preventDefault();
sendMessage();
}
	});
})