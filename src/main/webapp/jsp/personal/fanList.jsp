<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="MALE_ICON" value="/weibo/resources/img/male.png" />
<c:set var="FEMALE_ICON" value="/weibo/resources/img/female.png" />
<c:set var="FOLLOW_ICON" value="/weibo/resources/img/followed.png" />
<c:set var="UNFOLLOW_ICON" value="/weibo/resources/img/unFollow.png" />
<c:set var="FOLLOWED_TEXT" value="已关注" />
<c:set var="UNFOLLOWED_TEXT" value="尚未关注" />
<c:set var="FOLLOW_BTN_TEXT" value="设为关注" />
<c:set var="CANCEL_FOLLOW_BTN_TEXT" value="取消关注" />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>我的粉丝</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/resources/css/bootstrap.min.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/resources/css/fanList.css">
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
		<!-- 以下是你的内容-->
		<div class="content">
			<ul class="list-group">
				<c:if test="${pagerResult.count==0}">
					<li><div
							style="color: #5F5F5F; background-color: white; height: 400px; text-align: center; width: 100%; line-height: 400px; font-size: 20px;">
							暂无粉丝</div></li>
				</c:if>
				<c:forEach var="relativeUser" items="${pagerResult.resultSet}">
					<li class="list-group-item setHeight"><a
						href="javaScript:void(0)" class="user_portrait"><img
							src="${relativeUser.user.portraitUrl}" class="userPics"></a>
					<h4 class="userName">${relativeUser.user.nickname}<img
								src="${relativeUser.user.gender==0?MALE_ICON:FEMALE_ICON}"
								class="sex_icon">
						</h4>
						<form class="buttonForm">
							<div class="followCancer">
								<img
									src="${relativeUser.isMyConcernedUser==0?UNFOLLOW_ICON:FOLLOW_ICON}"
									class="follow_icon"><span class="allFollow_text">${relativeUser.isMyConcernedUser==0?UNFOLLOWED_TEXT:FOLLOWED_TEXT}</span>
							</div>
							<input type="button"
								value="${relativeUser.isMyConcernedUser==0?FOLLOW_BTN_TEXT:CANCEL_FOLLOW_BTN_TEXT}"
								isMyConcernedUser="${relativeUser.isMyConcernedUser}"
								isMyFan="${relativeUser.isMyFan}"
								userId="${relativeUser.user.userId}" class="allFollow_btn">
						</form> <br>
					<span class="introdution">${relativeUser.user.introduction eq null?"暂无简介":relativeUser.user.introduction}</span>
						<br>
					<span class="followFont">粉丝<span class="number">${relativeUser.fanAmount}</span>人
							&nbsp;关注<span class="number">${relativeUser.concernedUserAmount}</span>人
					</span></li>
				</c:forEach>
				<c:if test="${pagerResult.totalPage>1}">
					<li class="list-group-item" style="height: 45px;">
						<ul class="pagination"
							style="position: relative; top: -25px; left: 35%;">
							<li><a href="javaScript:void(0)"
								pageNum="${pagerResult.firstPage}" class="page_selector">&laquo;第一页</a></li>
							<c:choose>
								<c:when
									test="${pagerResult.totalPage<=5 || pagerResult.currentPage<=3}">
									<c:forEach var="i" begin="${pagerResult.firstPage}"
										end="${pagerResult.totalPage>5?5:pagerResult.totalPage}"
										step="1">
										<li ${i==pagerResult.currentPage?'class="active"':''}><a
											href="javaScript:void(0)" class="page_selector"
											pageNum="${i}">${i}</a></li>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<c:forEach var="i"
										begin="${pagerResult.totalPage-pagerResult.currentPage>=2?pagerResult.currentPage-2:pagerResult.totalPage-4}"
										end="${pagerResult.totalPage-pagerResult.currentPage>=2?pagerResult.currentPage+2:pagerResult.totalPage}"
										step="1">
										<li ${i==pagerResult.currentPage?'class="active"':''}><a
											href="javaScript:void(0)" class="page_selector"
											pageNum="${i}">${i}</a></li>
									</c:forEach>
								</c:otherwise>
							</c:choose>
							<li><a href="javaScript:void(0)" class="page_selector"
								pageNum="${pagerResult.lastPage}">&raquo;最后一页</a></li>
						</ul>
					</li>
				</c:if>
				<c:if test="${pagerResult.totalPage<=1}">
					<div id="loadall">已全部加载完</div>
				</c:if>
			</ul>
		</div>
	</div>
	<script
		src="<%=request.getContextPath()%>/resources/js/jquery-1.11.3.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/resources/js/bootstrap.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/resources/interactive/personal/fanList.js"></script>
	<script
		src="<%=request.getContextPath()%>/resources/interactive/personal/topMenu.js"></script>
</body>
</html>