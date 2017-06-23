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
<title>搜索微博-${searchKey}的结果</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/resources/css/searchTwitter.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/resources/css/bootstrap.min.css">
</head>
<body class="bgBody">

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
	<div class="contentPart">
		<div class="serchingPart">
			<div class="serchingChosen">
				<span class="serchingChosenFont" href="javascript:">微博</span>
			</div>
			<div class="serchingChosen1">
				<a class="serchingChosenFont1"
					href="<%=request.getContextPath()%>/common/searchUser">用户</a>
			</div>
			<form class="input-group serchingBox"
				action="<%=request.getContextPath()%>/common/searchTwitter"
				id="searchTwitterForm">

				<input type="text" name="searchKey" class="form-control"
					placeholder="有趣的内容..." value="${searchKey}"> <span
					class="input-group-btn">
					<button class="btn btn-default" type="button"
						id="searchTwitter_btn">搜索</button>
				</span>
			</form>
		</div>
		<input type="hidden" value="${userDto.user.userId}" id="myUserId">
		<div class="middlePart">

			<ul class="userList" id="userList">
				<li class="myFirstLine">微博搜索-${searchKey}</li>

				<c:if test="${fn:length(pagerResult.resultSet)==0}">
					<li class="styleUse1"><div class="style1Use">
							<img
								src="<%=request.getContextPath()%>/resources/img/notFound.png"
								class="cantFindImg">对不起，搜索不到相关微博~~
						</div></li>
				</c:if>
				<c:if test="${fn:length(pagerResult.resultSet)>0}">
					<c:forEach var="twitterDto" items="${pagerResult.resultSet}">
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
						<div class="Comment" twitterUserId="${twitterDto.twitter.userId}">
							<ul class="CommentList"
								twitterId="${twitterDto.twitter.twitterId}">
							</ul>
						</div>
					</c:forEach>
				</c:if>
				</li>
				<li class="styleUse3"><c:if test="${pagerResult.totalPage>1}">
						<ul class="pagination">
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

		<div class="rightPart1" id="rightPart1">
			<div class="myFirstLine"></div>
		</div>
		<div class="rightPart2" id="searchHistory">
			搜索历史
			<ul class="serchingHistory">

			</ul>
		</div>
	</div>
	<script
		src="<%=request.getContextPath()%>/resources/js/jquery-1.11.3.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/resources/interactive/personal/topMenu.js"></script>
	<script
		src="<%=request.getContextPath()%>/resources/interactive/common/searchTwitter.js"></script>
</body>
</html>