<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
<title>搜索用户结果</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/resources/css/bootstrap.min.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/resources/css/searchUser.css">
</head>
<body class="bgBody">
	<%@include file="topMenu.jsp"%>
	<div class="hide1" onmouseover="closeDownList()">
		<!-- between the topMenu and behind part to make it like a line.   -->
	</div>
	<input type="hidden" id="myUser" myUserId="${userDto.user.userId}">
	<div class="contentPart">

		<div class="middlePart">
			<div class="serchingPart">
				<div class="serchingChosen">
					<a class="serchingChosenFont1"
						href="<%=request.getContextPath()%>/personal/searchTwitter">微博</a>
				</div>
				<div class="serchingChosen1">
					<span class="serchingChosenFont" href="javascript:">用户</span>
				</div>
				<form class="input-group serchingBox"
					action="<%=request.getContextPath()%>/personal/searchUser"
					id="searchUserForm">

					<input type="text" name="searchKey" class="form-control"
						placeholder="有趣的内容..." value="${searchKey}"> <span
						class="input-group-btn">
						<button class="btn btn-default" type="button" id="searchUser_btn">
							搜索</button>
					</span>
				</form>
			</div>
			<ul class="userList" id="userList">
				<li class="myFirstLine">用户搜索-${searchKey}</li>
				<c:if test="${fn:length(pagerResult.resultSet)==0}">
					<li class="styleUse1"><div class="style1Use">
							<img
								src="<%=request.getContextPath()%>/resources/img/notFound.png"
								class="cantFindImg">对不起，搜索不到相关用户~
						</div></li>
				</c:if>
				<c:if test="${fn:length(pagerResult.resultSet)>0}">
					<c:forEach var="searchedUser" items="${pagerResult.resultSet}">
						<li class="list-group-item setHeight"><input type="hidden"
							class="searchedUser" searchedUserId="${searchedUser.user.userId}"
							sMyConcernedUser="${relativeUser.isMyConcernedUser}"
							isMyFan="${relativeUser.isMyFan}"> <a
							href="javaScript:void(0)" class="user_portrait"><img
								src="${searchedUser.user.portraitUrl}" class="userPics"></a>
							<h4 class="userName" style="position: relative; left: 1em;">${searchedUser.user.nickname}<img
									src="${searchedUser.user.gender==0?MALE_ICON:FEMALE_ICON}"
									style="height: 15px; width: 15px;">
							</h4>
							<form class="buttonForm">
								<div class="followCancer">
									<img
										src="${searchedUser.isMyConcernedUser==0?UNFOLLOW_ICON:FOLLOW_ICON}"
										class="follow_icon" style="height: 20px; width: 20px;"><span
										class="allFollow_text">${searchedUser.isMyConcernedUser==0?UNFOLLOWED_TEXT:FOLLOWED_TEXT}</span>
								</div>
								<input type="button"
									value="${searchedUser.isMyConcernedUser==0?FOLLOW_BTN_TEXT:CANCEL_FOLLOW_BTN_TEXT}"
									name="unFollow" class="allFollow allFollow_btn">
							</form> <br>
						<span class="introdution">${searchedUser.user.introduction eq null?"暂无简介":searchedUser.user.introduction}</span>
							<br>
						<span class="followFont">粉丝<span style="color: blue;">${searchedUser.fanAmount}</span>人
								&nbsp;关注<span style="color: blue;">${searchedUser.concernedUserAmount}</span>人
						</span></li>
					</c:forEach>
				</c:if>
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
				<c:forEach var="searchRecord" items="${searchRecords}">
					<li class="searchKey"><a
						href="<%=request.getContextPath()%>/personal/searchUser?searchKey=${searchRecord.searchKey}">${searchRecord.searchKey}</a>
					</li>
				</c:forEach>
				<li class="clearSerchingHis"><a href="javascript:void(0)"
					id="clear_searchHistory">清除浏览记录</a></li>
			</ul>
		</div>
	</div>
	<script
		src="<%=request.getContextPath()%>/resources/js/jquery-1.11.3.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/resources/interactive/personal/topMenu.js"></script>
	<script
		src="<%=request.getContextPath()%>/resources/interactive/personal/searchUser.js"></script>

</body>
</html>