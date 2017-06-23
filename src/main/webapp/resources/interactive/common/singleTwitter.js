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
 $("#codeImg").click(function(){
 	$(this).attr("src",$(this).attr("src")+'?');
 })
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

	function updateThumbsupAmount($thumbsupAmount){
		var twitterId=$thumbsupAmount.parent().parent().parent().find(".twitterId").val();
		$.get("/weibo/personal/getThumbsupAmountOfTwitter",{"twitterId":twitterId},function(thumbsupAmount){
			$thumbsupAmount.text(thumbsupAmount);
		})
	}
	
	$(".thumbsup_btn,.collect_btn").click(function(){
		event.stopPropagation();
		$("#popupbox").modal("toggle");
	})
$("body").on("click",".Com_Button,.reply",function(){
	$("#popupbox").modal("toggle");
});
	$(".Comment").on("click",".checkUser",function(){
		var userId=$(this).attr("userId");
		window.open("/weibo/common/checkUserInformation?userId="+userId);
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
	$(".atPeople").click(function(){
		event.stopPropagation();
		var nickname=$(this).text().substring(1);
		location.href="/weibo/common/checkUserInformationByNickname?nickname="+nickname;
	})
})