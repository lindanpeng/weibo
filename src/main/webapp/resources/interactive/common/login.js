$(function(){
	$("#login_btn").click(function(){
		if($("input[name='accountId']").val()==""){
			$("#tip").text("请输入账号!");
			return false;
		}
		if($("input[name='password']").val()==""){
			$("#tip").text("请输入密码!");
			return false;
		}
		$("#login_form").sumbit();
	});
})