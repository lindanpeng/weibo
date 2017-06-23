<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>注册</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/resources/css/bootstrap.min.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/resources/css/register.css">
</head>
<body>
	<div class="form-wrapper">
		<img src="<%=request.getContextPath()%>/resources/img/logoText.png">
		<p id="tip">${returnResult.retMsg}</p>
		<form class="form-horizontal" id="register_form"
			action="<%=request.getContextPath()%>/common/registerUser"
			method="post">
			<div class="form-group input-group">
				<span class="input-group-addon"><i
					class="glyphicon glyphicon-user"></i></span> <input type="text"
					name="userOpenId" class="form-control input-lg input_text"
					placeholder="请输入常用邮箱">
			</div>
			<div class="form-group input-group">
				<span class="input-group-addon"><i
					class="glyphicon glyphicon-lock"></i></span> <input type="password"
					name="password" class="form-control input-lg input_text"
					placeholder="请输入密码">
			</div>
			<div class="form-group input-group">
				<span class="input-group-addon"><i
					class="glyphicon glyphicon-lock"></i></span> <input type="password"
					name="password2" class="form-control input-lg input_text"
					placeholder="请确认密码">
			</div>
			<div class="form-group input-group">
				<span class="input-group-addon"><i
					class="glyphicon glyphicon-thumbs-up"></i></span> <input type="text"
					name="nickname" class="form-control input-lg input_text"
					placeholder="填写昵称">
			</div>
			<div class="form-group">
				<label class="radio-inline"> <input type="radio" value="0"
					name="gender" checked>男性
				</label> <label class="radio-inline"> <input type="radio" value="1"
					name="gender">女性
				</label>
			</div>
			<div class="form-group">
				<div class="form-group">
					<input type="text" placeholder="验证码" name="code"
						style="width: 130px; height: 40px;"> <a
						href="javascript:void(0)" id="newcode"><img title="点击换另一张"
						src="<%=request.getContextPath()%>/common/getVerificationCode"
						id="codeimg"></a>
				</div>
				<button class="btn btn-success btn-lg" id="register_btn">注册</button>
				&nbsp; <a href="<%=request.getContextPath()%>/common/login">已有账号?去登录</a>
			</div>
		</form>
	</div>
	<script
		src="<%=request.getContextPath()%>/resources/js/jquery-1.11.3.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/resources/js/bootstrap.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/resources/interactive/common/register.js"></script>
</body>
</html>