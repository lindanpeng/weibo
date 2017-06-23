<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="FOLLOW_BTN_TEXT" value="设为关注" />
<c:set var="CANCEL_FOLLOW_BTN_TEXT" value="取消关注" />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${checkedUser.user.nickname}的个人中心</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/resources/css/bootstrap.min.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/resources/css/hisInformation.css">
</head>
<body>
	<div class="hideBackgroud" id="hideBackgroudSwich">
		<!-- this use to view the big img or 转发(use as backgroud) -->
	</div>
	<div class="chatPart" id="chatPart">

		<div class="chatTB">
			<h4>发私信</h4>
			<img src="<%=request.getContextPath()%>/resources/img/CloseChat.png"
				class="closeChat">
		</div>
		<div class="chatCB">
			<span class="userTitle">收件人: <span id="receiver_name"></span></span>
			<div class="textareaB">
				<span class="chatPartTextTitle">私信 ： </span>
				<textarea class="chatPartTextArea" id="messageContent"></textarea>
			</div>
			<button type="button" class="btn btn-primary marginSet1"
				id="send_btn">发送</button>
			<button type="button" class="btn btn-primary marginSet2"
				id="toChat_btn">聊天</button>
		</div>
	</div>
	<%@include file="loginPopupBox.jsp"%>
	<%@include file="topMenu.jsp"%>
	<div class="container">
		<div class="head">
			<div class="portrait_name">
				<div class="portrait">
					<a href="javaScript:void(0)" id="toUploadPortrait"><img
						src="${checkedUser.user.portraitUrl}"
						class="img-circle img-thumbnail" id="portrait_img"></a>

				</div>
				<div class="name">
					<input type="hidden" value="${checkedUser.user.userId}"
						id="checkedUserId">
					<p class="nickname" id="checkedUserName">${checkedUser.user.nickname}</p>
					<p>
						<a
							href="/weibo/personal/checkUserInformation?userId=${checkedUser.user.userId}"
							style="color: #F0FFF0;">查看个人资料</a>
					</p>
					<input type="hidden" value="${checkedUser.user.userId}" id="userId">
					<input type="hidden" value="${userDto.user.userId}" id="myUserId">
					<input type="hidden" value="${checkedUser.isMyFan}" id="isMyFan">
					<input type="hidden" value="${checkedUser.isMyConcernedUser}"
						id="isMyConcernedUser">
					<!-- <div class="PI_FollowButton"><button class="PI_FollowButtonDetail"><span class="PI_setFont">取消关注</span></button></div><div class="PI_Chat"><button class="PI_ChatDetail"><span class="PI_setFont">私信</span></button></div>  -->
				</div>
				<div class="button_group">
					<button class="btn btn-default" type="button" id="follow_btn">${checkedUser.isMyConcernedUser==0?FOLLOW_BTN_TEXT:CANCEL_FOLLOW_BTN_TEXT}</button>
					&nbsp;&nbsp;&nbsp;&nbsp;
					<button class="btn btn-warning" type="button" id="chat_btn">私信</button>
				</div>
			</div>
			<div class="profile_data">
				<ul class="nav nav-tabs">
					<li><a
						href="<%=request.getContextPath()%>/common/hisConcernedUserList?userId=${checkedUser.user.userId}">关注<span>${checkedUser.concernedUserAmount}</span></a></li>
					<li><a
						href="<%=request.getContextPath()%>/common/hisFanList?userId=${checkedUser.user.userId}">粉丝<span>${checkedUser.fanAmount}</span></a></li>
					<li><a
						href="<%=request.getContextPath()%>/common/hisTwitterList?userId=${checkedUser.user.userId}">动态<span>${checkedUser.twitterAmount}</span></a></li>
				</ul>
			</div>
		</div>
		<div class="content">
			<div id="content_title">
				<p>基本信息</p>
			</div>
			<div class="table_wrapper">
				<table>
					<tr>
						<td class="label_tip">登录名：</td>
						<td class="information">${checkedUser.user.userOpenId}</td>
					</tr>
					<tr>
						<td class="label_tip">昵称：</td>
						<td class="information">${checkedUser.user.nickname}</td>
					</tr>
					<tr>
						<td class="label_tip">性别：</td>
						<td class="information">${checkedUser.user.gender==0?'男':'女'}</td>
					</tr>
					<tr>
						<td class="label_tip">电子邮箱：</td>
						<td class="information">${checkedUser.user.email eq null||checkedUser.user.email eq ''?"未知":checkedUser.user.email}</td>
					</tr>
					<tr>
						<td class="label_tip">生日：</td>
						<td class="information"><c:choose>
								<c:when test="${checkedUser.user.birthday eq null}">
未知
</c:when>
								<c:otherwise>
									<fmt:formatDate pattern="yyyy-MM-dd"
										value="${checkedUser.user.birthday}" />
								</c:otherwise>
							</c:choose>
					<tr>
						<td class="label_tip">简介：</td>
						<td class="information">${checkedUser.user.introduction eq null?"未知":checkedUser.user.introduction}</td>
					</tr>
					<tr>
						<td class="label_tip">注册时间：</td>
						<td class="information"><fmt:formatDate pattern="yyyy-MM-dd"
								value="${checkedUser.user.registerTime}" /></td>
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
		src="<%=request.getContextPath()%>/resources/interactive/common/hisInformation.js"></script>
</body>
</html>