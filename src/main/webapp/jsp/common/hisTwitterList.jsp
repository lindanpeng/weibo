<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="UNCOLLECTED_ICON" value="/weibo/resources/img/collect1.png" />
<c:set var="COLLECTED_ICON" value="/weibo/resources/img/collect2.png" />
<c:set var="UNTHUMBSUPED_ICON"
	value="/weibo/resources/img/thumbsup1.png" />
<c:set var="THUMBSUPED_ICON" value="/weibo/resources/img/thumbsup2.png" />
<c:set var="FOLLOW_BTN_TEXT" value="设为关注" />
<c:set var="CANCEL_FOLLOW_BTN_TEXT" value="取消关注" />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${checkedUser.user.nickname}的微博</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/resources/css/bootstrap.min.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/resources/css/hisTwitter.css">
</head>
<body>
	<div class="hideBackgroud" id="hideBackgroudSwich">
		<!-- this use to view the big img or 转发(use as backgroud) -->
	</div>
	<div class="ShowBigImgPart" id="ShowBigImgPartSwich">
		<!-- 预览大图 -->
		<img src="" class="ShowBigImg">
		<div class="forwardTickCloseTick1">
			<a href="javascript:"><img
				src="<%=request.getContextPath()%>/resources/img/closeTick.png"
				style="position: relative; left: 5px;"></a>
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
							href="/weibo/common/checkUserInformation?userId=${checkedUser.user.userId}"
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
			<c:if test="${pagerResult.count==0}">
				<div
					style="color: #5F5F5F; background-color: white; height: 400px; text-align: center; width: 100%; line-height: 400px; font-size: 20px;">
					暂无内容</div>
			</c:if>
			<c:forEach var="twitterDto" items="${pagerResult.resultSet}"
				varStatus="status">
				<div class="content1 twitter">
					<a
						href="/weibo/common/checkUserInformation?userId=${twitterDto.userDto.user.userId}"><img
						src="${twitterDto.userDto.user.portraitUrl}" class="UserimgBox"></a>
					<div class="WeiboPersonal">
						<a href="javascript:void(0)" class="userName">${twitterDto.userDto.user.nickname}</a><br>
						<span style="color: gray;">发表于 <fmt:formatDate
								pattern="yyyy-MM-dd hh:mm:ss"
								value="${twitterDto.twitter.publicTime}" /></span><br>
					</div>
					<div class="textbox">
						<p>${twitterDto.twitter.textContent}</p>
					</div>
					<div
						class="${fn:length(twitterDto.twitterPictures)<9?'imgWholeBox':'imgWholeBox1'}">
						<c:forEach var="twitterPicture"
							items="${twitterDto.twitterPictures}">
							<img src="${twitterPicture.pictureUrl}"
								class="imgWeiboBoxPicsTop">
						</c:forEach>
					</div>
					<div class="bottomWeibo">
						<!-- 用class来代替 -->
						<input type="hidden" value="${twitterDto.twitter.twitterId}"
							class="twitterId"> <input type="hidden"
							value="${twitterDto.twitter.userId}" class="userId">
						<div class="bottomWeibo1">
							<a href="javascript:" title="转发" class="forward_btn"><img
								src="<%=request.getContextPath()%>/resources/img/forward.png"><span
								class="bottomWeiboNum">0</span></a>
						</div>
						<div class="bottomWeibo2">
							<a href="javascript:void(0)" title="评论" class="comment_btn"
								userId="${twitterDto.userDto.user.userId}"><img
								src="<%=request.getContextPath()%>/resources/img/comment.png"><span
								class="bottomWeiboNum commentsAmount">${twitterDto.commentsAmount}</span></a>
						</div>
						<div class="bottomWeibo3">
							<a href="javascript:" title="收藏" class="collect_btn"
								collected="${twitterDto.collected}"><img
								src="${twitterDto.collected eq true?COLLECTED_ICON:UNCOLLECTED_ICON}"
								class="collect"></a>
						</div>
						<div class="bottomWeibo4">
							<a href="javascript:" title="点赞" class="thumbsup_btn"
								thumbsuped="${twitterDto.thumbsuped}"><img
								src="${twitterDto.thumbsuped eq true?THUMBSUPED_ICON:UNTHUMBSUPED_ICON}"
								class="thumbsup"><span
								class="bottomWeiboNum thumbsupAmount">${twitterDto.thumbsupAmount}</span></a>
						</div>
					</div>
				</div>
				<div class="Comment">
					<ul class="CommentList" twitterId="${twitterDto.twitter.twitterId}">
					</ul>
				</div>
			</c:forEach>
			<div class="PageTPart">
				<c:if test="${pagerResult.totalPage>1}">
					<ul class="pagination">
						<li><a href="javascript:void(0)"
							pageNum="${pagerResult.firstPage}" class="page_selector">&laquo;第一页</a></li>
						<c:choose>
							<c:when
								test="${pagerResult.totalPage<=5 || pagerResult.currentPage<=3}">
								<c:forEach var="i" begin="${pagerResult.firstPage}"
									end="${pagerResult.totalPage>5?5:pagerResult.totalPage}"
									step="1">
									<li ${i==pagerResult.currentPage?'class="active"':''}><a
										href="javascript:void(0)" class="page_selector" pageNum="${i}">${i}</a></li>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<c:forEach var="i"
									begin="${pagerResult.totalPage-pagerResult.currentPage>=2?pagerResult.currentPage-2:pagerResult.totalPage-4}"
									end="${pagerResult.totalPage-pagerResult.currentPage>=2?pagerResult.currentPage+2:pagerResult.totalPage}"
									step="1">
									<li ${i==pagerResult.currentPage?'class="active"':''}><a
										href="javascript:void(0)" class="page_selector" pageNum="${i}">${i}</a></li>
								</c:forEach>
							</c:otherwise>
						</c:choose>
						<li><a href="javascript:void(0)" class="page_selector"
							pageNum="${pagerResult.lastPage}">&raquo;最后一页</a></li>
					</ul>
				</c:if>
				<c:if test="${pagerResult.totalPage<=1}">
					<div id="loadall">已全部加载完</div>
				</c:if>
			</div>
		</div>

	</div>
	<script
		src="<%=request.getContextPath()%>/resources/js/jquery-1.11.3.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/resources/js/bootstrap.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/resources/interactive/common/hisTwitter.js"></script>
</body>
</html>