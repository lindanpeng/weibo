<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编辑个人信息</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/resources/css/bootstrap.min.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/resources/css/amendPersonalInfo.css">
</head>
<body>
	<%@include file="topMenu.jsp"%>
	<div class="container">
		<div class="head">
			<div class="portrait_name">
				<div class="portrait">
					<a href="javaScript:void(0)" id="toUploadPortrait"><img
						src="${userDto.user.portraitUrl}" class="img-circle img-thumbnail"
						id="portrait_img"></a>

				</div>
				<div class="name">
					<p class="nickname">${userDto.user.nickname}</p>
					<p>
						<a href="/weibo/personal/personalInformation"
							style="color: #F0FFF0;">查看个人资料</a>
					</p>
				</div>
			</div>
			<div class="profile_data">
				<ul class="nav nav-tabs">
					<li><a
						href="<%=request.getContextPath()%>/personal/myConcernedUserList">关注<span>${userDto.concernedUserAmount}</span></a></li>
					<li><a href="<%=request.getContextPath()%>/personal/myFanList">粉丝<span>${userDto.fanAmount}</span></a></li>
					<li><a
						href="<%=request.getContextPath()%>/personal/myTwitterList">动态<span>${userDto.twitterAmount}</span></a></li>
				</ul>
			</div>
		</div>
		<div class="content">
			<div id="content_title">
				<p>基本信息</p>
			</div>
			<form
				action="<%=request.getContextPath()%>/personal/savePersonalInfo"
				method="post" id="infoForm">
				<div class="table_wrapper">
					<table>
						<tr>
							<td class="label_tip">性别：</td>
							<td class="information"><label class="radio-inline">
									<input type="radio" value="0" name="gender"
									${userDto.user.gender==0?'checked':''}>男性
							</label> <label class="radio-inline"> <input type="radio"
									value="1" name="gender" ${userDto.user.gender==1?'checked':''}>女性
							</label></td>
						</tr>
						<tr>
							<td class="label_tip">生日：</td>
							<td class="information"><input name="birthday" type="date"
								value="<fmt:formatDate pattern="yyyy-MM-dd"  value ="${userDto.user.registerTime}"/>" /></td>
						</tr>
						<tr>
							<td class="label_tip">电子邮箱：</td>
							<td class="information"><input name="email" type="email"
								value="${userDto.user.email}"></td>
						</tr>
						<tr>
							<td class="label_tip">简介：</td>
							<td class="information"><textarea name="introduction"
									class="form-control" rows="4" cols="40">${userDto.user.introduction}</textarea></td>
						</tr>
					</table>
				</div>
				<div id="button_wrapper">
					<input class="btn" type="submit" id="saveBtn" value="保存">
				</div>
			</form>
		</div>
	</div>
	<script
		src="<%=request.getContextPath()%>/resources/js/jquery-1.11.3.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/resources/interactive/personal/amendPersonalInfo.js"></script>
	<script
		src="<%=request.getContextPath()%>/resources/interactive/personal/topMenu.js"></script>
</body>
</html>