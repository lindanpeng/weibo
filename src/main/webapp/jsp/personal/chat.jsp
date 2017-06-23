<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.alphaking.util.TimeUtil"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="nowDay" value="<%=TimeUtil.nowDay()%>" />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>私聊窗口</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/resources/css/chat.css">
</head>
<body>
	<div class="all">
		<div class="null_top"></div>
		<div class="container">
			<div class="contain_find_welcome">
				<div class="find">
					<div class="master">
						<img class="mastersImg" id="myPortrait"
							src="${userDto.user.portraitUrl}"> <b
							style="color: rgb(238, 238, 238);">${userDto.user.nickname}</b>
						<div class="shows">${userDto.user.introduction}</div>
					</div>
					<form>
						<div class="input-to-find">
							<img
								src="<%=request.getContextPath()%>/resources/img/Magnifier.png"
								class="Magnifier"> <input class="findsomone" type="text"
								placeholder="查找联系人或群"
								style="color: rgb(180, 181, 188); border-width: 0px"
								id="autocomplete">
						</div>
					</form>
					<div class="hide_persons">
						<p class="not_find">没有找到相应的联系人</p>
						<ul class="contacts3">
						</ul>
					</div>
					<div class="person">
						<ul class="contacts" id="selectable">
							<c:forEach var="conversationDto" items="${conversationDtos}">
								<li class="people"
									friendUserId="${conversationDto.friend.user.userId}"><img
									class="masterimg"
									src="${conversationDto.friend.user.portraitUrl}"> <c:if
										test="${conversationDto.unReadMessageAmount!=0}">
										<span class="unread">${conversationDto.unReadMessageAmount}</span>
									</c:if> <span class="name">${conversationDto.friend.user.nickname}</span>
									<div class="li_time">
										<fmt:formatDate pattern="hh:mm"
											value="${conversationDto.latestMessage.sendTime}" />
									</div>
									<div class="show">${conversationDto.latestMessage.content}</div>
								</li>
							</c:forEach>

						</ul>
					</div>
					<div class="hide_div" id="hid_div">
						<ul class="contacts2">
							<li class="hide_li">删除对话</li>
						</ul>
					</div>
				</div>
				<div class="welcome">
					<p class="pargraph">您还未选中或发起聊天，快去跟好友聊一聊吧</p>
				</div>

				<div class="containt">
					<div class="head" id="page2">
						<b id="userName"></b>
					</div>
					<div class="body" id="chat_content"></div>
					<div class="textpage">
						<textarea class="textinput" id="txt"></textarea>
						<button id="send" class="sendbutton" type="submit"
							style="position: absolute; bottom: 8px; right: 20px;">发送(S)</button>
					</div>
				</div>
			</div>
			<div class="null_down"></div>
		</div>
	</div>
	<input type="hidden" id="userId" value="${userDto.user.userId}">
	<script
		src="<%=request.getContextPath()%>/resources/js/jquery-1.11.3.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/resources/js/bootstrap.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/resources/interactive/personal/chat.js"></script>
	<script src="<%=request.getContextPath()%>/resources/js/chat.js"></script>
</body>

</html>