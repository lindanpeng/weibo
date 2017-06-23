$(function(){
	var validateUrl="/weibo/common/asyValidateUser";
	var homeUrl="/weibo/personal/home";
	var registerUrl="/weibo/common/register";
    $("#loginBtn").click(function(){
    	if($("#accountId").val()==""){
    		$("#wranTip").text("账号不能为空！")
    		return;
    	}
    	if($("#password").val()==""){
    		$("#wranTip").text("密码不能为空！")
    		return;
    	}
    	if($("#code").val()==""){
    		$("#wranTip").text("验证码不能为空！")
    		return;
    	}
    	var object=new Object();
    	object.accountId=$("#accountId").val();
    	object.password=$("#password").val();
    	object.code=$("#code").val();
    	var jsonStr=JSON.stringify(object);
    	$.post(validateUrl,{"jsonStr":jsonStr},function(data){
    		if(data.code==404){
    			$("#wranTip").text(data.retMsg);
    		}
    		else{
    			location.href=homeUrl;
    		}
    	});
    });
    $("#codeImg").click(function(){
    	$(this).attr("src",$(this).attr("src")+'?');
    })
})