<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>个人中心</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/resources/css/bootstrap.min.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/resources/css/personalInformation.css">
</head>
<body>
	<div class="hide1" onmouseover="closeDownList()">
		<!-- between the topMenu and behind part to make it like a line.   -->
	</div>
	<%@include file="topMenu.jsp"%>

	<div class="container">
		<div class="head">
			<div class="portrait_name">
				<div class="portrait">
					<a href="javaScript:void(0)" id="toUploadPortrait"><img
						src="${userDto.user.portraitUrl}" class="img-circle img-thumbnail"
						id="portrait_img" title="上传新头像"></a>

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
				<p>
					基本信息
					<button class="btn" type="button" id="editBtn">编辑</button>
				</p>
			</div>
			<div class="table_wrapper">
				<table>
					<tr>
						<td class="label_tip">登录名：</td>
						<td class="information">${userDto.user.userOpenId}</td>
					</tr>
					<tr>
						<td class="label_tip">昵称：</td>
						<td class="information">${userDto.user.nickname}</td>
					</tr>
					<tr>
						<td class="label_tip">性别：</td>
						<td class="information">${userDto.user.gender==0?'男':'女'}</td>
					</tr>
					<tr>
						<td class="label_tip">电子邮箱：</td>
						<td class="information">${userDto.user.email eq null||userDto.user.email eq ''?"未知":userDto.user.email}</td>
					</tr>
					<tr>
						<td class="label_tip">生日：</td>
						<td class="information"><c:choose>
								<c:when test="${userDto.user.birthday eq null}">
未知
</c:when>
								<c:otherwise>
									<fmt:formatDate pattern="yyyy-MM-dd"
										value="${userDto.user.birthday}" />
								</c:otherwise>
							</c:choose>
					<tr>
						<td class="label_tip">简介：</td>
						<td class="information">${userDto.user.introduction eq null?"未知":userDto.user.introduction}</td>
					</tr>
					<tr>
						<td class="label_tip">注册时间：</td>
						<td class="information"><fmt:formatDate pattern="yyyy-MM-dd"
								value="${userDto.user.registerTime}" /></td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	<script
		src="<%=request.getContextPath()%>/resources/js/jquery-1.11.3.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/resources/js/bootstrap.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/resources/interactive/personal/personalInformation.js"></script>
	<script
		src="<%=request.getContextPath()%>/resources/interactive/personal/topMenu.js"></script>
</body>
</html>