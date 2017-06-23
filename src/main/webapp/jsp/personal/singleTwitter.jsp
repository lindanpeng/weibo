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
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>查看微博</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/resources/css/bootstrap.min.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/resources/css/singleTwitter.css">
</head>
<body>
	<div class="hide1" onmouseover="closeDownList()">
		<!-- between the topMenu and behind part to make it like a line.   -->
	</div>

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
					href="javascript:"><img
					src="<%=request.getContextPath()%>/resources/img/closeTick.png"
					style="position: relative; left: 5px;" id="closeComment"></a>
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
	<input type="hidden" value="${userDto.user.userId}" id="myUserId">
	<input type="hidden" value="${twitterDto.twitter.twitterId}"
		id="twitterId">
	<%@include file="topMenu.jsp"%>
	<div class="bgcontainer" onmouseover="closeDownList()">

		<div class="CenterMandRP">
			<div class="middlePart" id="MiddleH">

				<div class="content">

					<div class="content1">
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
					<div class="Comment" twitterUserId="${twitterDto.twitter.userId}">
						<ul class="CommentList"
							twitterId="${twitterDto.twitter.twitterId}">
							<li class="Com_Box"><textarea class="Com_width"></textarea>
								<button class="Com_Button"
									toUserId="${twitterDto.twitter.userId}">评论</button></li>
							<c:if test="${pagerResult.count==0}">
								<div
									style="color: #5F5F5F; background-color: white; width: 100%; font-size: 20px; height: 200px; line-height: 200px;">
									暂无评论</div>
							</c:if>
							<c:forEach var="commentDto" items="${pagerResult.resultSet}">
								<li class="CommentLineOfList"><a>${commentDto.fromUser.nickname}</a>@<a>${commentDto.toUser.nickname}</a>：<span>${commentDto.comment.content }</span>
									<br>
								<span class="Com_Time"><fmt:formatDate
											pattern="yyyy-MM-dd hh:mm:ss"
											value="${commentDto.comment.commentTime}" /></span>&nbsp;<a
									href="javascript:" class='reply'
									toUserId="${commentDto.fromUser.userId}">回复</a></li>
							</c:forEach>


							<div class="PageTPart">
								<c:if test="${pagerResult.totalPage>1}">
									<ul class="pagination">
										<li><a href="javascript:void(0)"
											pageNum="${pagerResult.firstPage}" class="page_selector">&laquo;第一页</a></li>
										<c:choose>
											<c:when
												test="${pagerResult.totalPage<=5 || pagerResult.currentPage<=3}">
												<c:forEach var="i" items="${pagerResult.pageList}">
													<li ${i==pagerResult.currentPage?'class="active"':''}><a
														href="javascript:void(0)" class="page_selector"
														pageNum="${i}">${i}</a></li>
												</c:forEach>
											</c:when>
											<c:otherwise>
												<c:forEach var="i"
													begin="${pagerResult.totalPage-pagerResult.currentPage>=2?currentPage-2:pagerResult.totalPage-4}"
													end="${pagerResult.totalPage-pagerResult.currentPage>=2?pagerResult.currentPage+2:pagerResult.currentPage}"
													step="1">
													<li><a href="javascript:void(0)" class="page_selector"
														pageNum="${i}"
														${i==pagerResult.currentPage?'class="active"':''}>${i}</a></li>
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
						</ul>
					</div>
				</div>




			</div>








		</div>



		<%@include file="rightPart.jsp"%>
		<div style="clear: both;"></div>
	</div>
	<form action="<%=request.getContextPath()%>/personal/publishComment"
		id="commentForm" method="post">
		<input type="hidden" name="jsonStr" id="jsonStr">
	</form>
	<script
		src="<%=request.getContextPath()%>/resources/js/jquery-1.11.3.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/resources/interactive/personal/topMenu.js"></script>
	<script
		src="<%=request.getContextPath()%>/resources/interactive/personal/singleTwitter.js"></script>
</body>
</html>