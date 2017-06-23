<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/resources/css/bootstrap.min.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/resources/css/login.css">
</head>
<body>
	<div class="form-wrapper">
		<img src="<%=request.getContextPath()%>/resources/img/logoText.png">
		<p id="tip">${returnResult.retMsg}</p>
		<form class="form-horizontal" id="login_form"
			action="<%=request.getContextPath()%>/common/validateUser"
			method="post">
			<div class="form-group input-group">
				<span class="input-group-addon"><i
					class="glyphicon glyphicon-user"></i></span> <input type="text"
					name="accountId" value="" class="form-control input-lg input_text"
					placeholder="请输入用户名">
			</div>
			<div class="form-group input-group">
				<span class="input-group-addon"><i
					class="glyphicon glyphicon-lock"></i></span> <input type="password"
					name="password" value="" class="form-control input-lg input_text"
					placeholder="请输入密码">
			</div>
			<div class="form-group">
				<button class="btn btn-success btn-lg" id="login_btn">登录</button>
				&nbsp; <a href="<%=request.getContextPath()%>/common/register">没有账号?去注册</a>
			</div>
		</form>
	</div>
	<script
		src="<%=request.getContextPath()%>/resources/js/jquery-1.11.3.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/resources/js/bootstrap.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/resources/interactive/common/login.js"></script>
</body>
</html>