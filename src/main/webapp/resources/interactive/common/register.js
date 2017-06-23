$(function(){
	$("#register_btn").click(function(){
		if($("input[name='userOpenId']").val()==""){
			$("#tip").text("请输入账号!");
			return false;
		}
		if($("input[name='password'").val()==""){
			$("#tip").text("请输入密码!");
			return false;
		}
		if($("input[name='password'").val()!=$("input[name='password2'").val()){
			$("#tip").text("两次输入密码不一致!");
			return false;
		}
		if($("input[name='nickname'").val()==""){
			$("#tip").text("请填写昵称!");
			return false;
		}
		if($("input[name='code'").val()==""){
			$("#tip").text("请填写验证码!");
			return false;
		}
		$("register_form").sumbit();
	});
	$("#newcode").click(function(){
		//当一个<img>的src改变时，页面会自动刷新这个img
		var verify=document.getElementById('codeimg');
	    verify.src=verify.src+'?';
	})
})