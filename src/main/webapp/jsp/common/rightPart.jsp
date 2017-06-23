<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/resources/css/rightPart2.css">
<div class="rightPart" id="RightH">



	<div class="loginInto">
		<form>
			<span class="errorC" id="wranTip">&nbsp;</span>

			<div class="UserLoginImg">
				<img src="<%=request.getContextPath()%>/resources/img/UserId.png">
			</div>
			<div class="col-sm-10 enterUserMessage">
				<input type="text" name="idName" placeholder="用户名/邮箱"
					class="form-control" id="accountId">
			</div>
			<div class="UserLoginImg1">
				<img
					src="<%=request.getContextPath()%>/resources/img/UserPassword.png"
					class="SetUserLoginImg">
			</div>
			<div class="col-sm-10">
				<input type="password" name="idpassword" placeholder="密码"
					class="form-control  enterUserMessage UserLoginPassword"
					id="password">
			</div>
			<div class="col-sm-4 enterCode">
				<input type="text" name="check" placeholder="验证码"
					class="form-control" id="code">
			</div>
			<img src="<%=request.getContextPath()%>/common/getVerificationCode"
				class="enterCodeImg" id="codeImg" title="点击切换">


			<div class="form-group setButtonCss">
				<div class="col-sm-offset-2 col-sm-10" style="top: -18px;">
					<button type="button" class="btn btn-primary" id="loginBtn">登录</button>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<button type="button" class="btn btn-info" id="registerBtn">注册</button>
				</div>
			</div>




		</form>

	</div>


</div>
