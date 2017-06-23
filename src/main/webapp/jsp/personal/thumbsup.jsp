<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>我收到的赞</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/resources/css/bootstrap.min.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/resources/css/thumbsup.css">
</head>
<body class="bgBody">
	<div class="hideBackgroud" id="hideBackgroudSwich">
		<!-- this use to view the big img or 转发(use as backgroud) -->
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
					href="javascript:" id="closeComment"><img
					src="<%=request.getContextPath()%>/resources/img/closeTick.png"
					style="position: relative; left: 5px;"></a>
			</div>
		</form>
	</div>

	<%@include file="topMenu.jsp"%>
	<input type="hidden" id="myUserId" value="${userDto.user.userId}">
	<div class="followContent" onmouseover="closeDownList()">
		<div class="bigcon1">
			<!-- <div class="bigcon2"> -->
			<div class="leftCon" id="left_con">
				<ul class="nav nav-pills nav-stacked">
					<li><a
						href="<%=request.getContextPath()%>/personal/commentToMe"
						class="topfont "><font face="微软雅黑" size="3.5"><span><img
									src="<%=request.getContextPath()%>/resources/img/concern.png"></span>评论</font></a></li>
					<li><a
						href="<%=request.getContextPath()%>/personal/thumbsupList"
						class="topfontChosen"><font face="微软雅黑" size="3.5"><span><img
									src="<%=request.getContextPath()%>/resources/img/fan.png"></span>赞</font></a></li>

				</ul>
			</div>




			<div class="rightCon" id="right_con">
				<ul class="list-group">


					<li class="list-group-item-head"><span
						style="top: 0.2em; position: relative; left: 0.4em"> <font
							size="3.5">&nbsp;收到的赞</font></span>
						<div class="triangle"></div></li>
					<c:if test="${pagerResult.count==0}">
						<div
							style="color: #5F5F5F; background-color: white; height: 400px; text-align: center; width: 100%; line-height: 400px; font-size: 20px;">
							未收到赞</div>
					</c:if>
					<c:forEach var="thumbsupDto" items="${pagerResult.resultSet}">
						<li class="list-group-item setHeight"><input type="hidden"
							class="thumbsup" userId="${thumbsupDto.user.userId}"
							twitterUserId="${thumbsupDto.twitter.userId}"
							twitterId="${thumbsupDto.twitter.twitterId}"> <a
							href="<%=request.getContextPath()%>/personal/checkUserInformation?userId=${thumbsupDto.thumbsup.userId}"><img
								src="${thumbsupDto.user.portraitUrl}" class="userPics"></a>
							<div class="user">${thumbsupDto.user.nickname}</div>
							<div class="agree">赞了我的微博</div>
							<div class="content">
								<a class="call"
									href="<%=request.getContextPath()%>/personal/checkUserInformation?userId=${thumbsupDto.twitter.userId}">${userDto.user.nickname}:</a>
								<a class="formal"
									href="<%=request.getContextPath()%>/personal/singleTwitter?twitterId=${thumbsupDto.twitter.twitterId}">${thumbsupDto.twitter.textContent eq ''?'分享图片':thumbsupDto.twitter.textContent}
								</a>

							</div>
							<div class="data">
								<fmt:formatDate pattern="yyyy-MM-dd hh:mm:ss"
									value="${thumbsupDto.thumbsup.thumbsupTime}" />
							</div>
							<div class="call_back reply">回复</div></li>
					</c:forEach>
					<li class="list-group-item Page1"><c:if
							test="${pagerResult.totalPage>1}">
							<ul class="pagination Page0">
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
						</c:if> <c:if test="${pagerResult.totalPage<=1}">
							<div id="loadall">已全部加载完</div>
						</c:if></li>


				</ul>
			</div>
		</div>
		<!-- </div> -->
	</div>
	<script
		src="<%=request.getContextPath()%>/resources/js/jquery-1.11.3.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/resources/js/bootstrap.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/resources/interactive/personal/topMenu.js"></script>
	<script
		src="<%=request.getContextPath()%>/resources/interactive/personal/thumbsup.js"></script>
</body>
</html>