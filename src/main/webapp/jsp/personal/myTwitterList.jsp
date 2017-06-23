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
<title>我发布的微博</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/resources/css/bootstrap.min.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/resources/css/myTwitter.css">
</head>
<body>
	<div class="hide1" onmouseover="closeDownList()">
		<!-- between the topMenu and behind part to make it like a line.   -->
	</div>
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
	<div class="commentReplyPart" id="commentPartSwich">
		<!-- 评论框 new-->
		<h4 class="LittleTitleFont">回复</h4>
		<hr>
		<form>
			<textarea class="commentPartTextArea" placeholder="评论"
				id="replyContent"></textarea>
			<div class="forwardTickCloseTick">
				<a href="javascript:" id="reply_btn"><img
					src="<%=request.getContextPath()%>/resources/img/forwardTick.png"></a><a
					href="javascript:"><img
					src="<%=request.getContextPath()%>/resources/img/closeTick.png"
					class="forwardTickCloseTick1"
					style="position: relative; left: 5px;"></a>
			</div>
		</form>
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
					<input type="hidden" value="${userDto.user.userId}" id="myUserId">
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
			<c:if test="${pagerResult.count==0}">
				<div
					style="color: #5F5F5F; background-color: white; height: 400px; text-align: center; width: 100%; line-height: 400px; font-size: 20px;">
					暂无内容</div>
			</c:if>
			<c:forEach var="twitterDto" items="${pagerResult.resultSet}"
				varStatus="status">
				<div class="content1 twitter">
					<a
						href="/weibo/personal/checkUserInformation?userId=${twitterDto.userDto.user.userId}"><img
						src="${twitterDto.userDto.user.portraitUrl}" class="UserimgBox"></a>
					<div class="WeiboPersonal">
						<a href="javascript:void(0)" class="userName">${twitterDto.userDto.user.nickname}</a><br>
						<span style="color: gray;">发表于 <fmt:formatDate
								pattern="yyyy-MM-dd hh:mm:ss"
								value="${twitterDto.twitter.publicTime}" /></span><br>
					</div>
					<c:if test="${userDto.user.userId==twitterDto.twitter.userId}">
						<div class="detele_l delete_twitter">
							<a href="javascript:" class="detele_Font">删除</a>
						</div>
					</c:if>
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
						<li class="Com_Box"><textarea class="Com_width"></textarea>
							<button class="Com_Button"
								toUserId="${twitterDto.twitter.userId}">评论</button></li>
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
		src="<%=request.getContextPath()%>/resources/interactive/personal/myTwitter.js"></script>
	<script
		src="<%=request.getContextPath()%>/resources/interactive/personal/topMenu.js"></script>
</body>
</html>