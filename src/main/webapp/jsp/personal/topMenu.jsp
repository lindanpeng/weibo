<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/resources/css/topMenu.css">
<div class="topMenu">
	<div class="xiaoWeiImg">
		<p class="navbar-text">
			<font color="white">xiaowei</font>
		</p>
	</div>
	<!-- new  searching change-->
	<div class="navbar-left searching">
		<form class="form-horizontal" role="form"
			action="<%=request.getContextPath()%>/personal/search"
			onsubmit="return check()">
			<div class="selectSearching">
				<select class="selectSearching1" id="searchType" name="searchType">
					<option value="1">微博</option>
					<option value="0">用户</option>
				</select>
			</div>
			<div class="input-group">
				<input type="text" name="searchKey" id="searchKey"
					class="form-control" placeholder="有趣的内容..."> <span
					class="input-group-btn"> <input type="submit"
					class="btn btn-default" value="Go">

				</span>
			</div>
		</form>
	</div>
	<ul class="nav nav-pills nav1">
		<li><a href="/weibo/personal/home" class="topfontChosen"><font
				face="微软雅黑" size="3.5">首页</font></a></li>
		<li onmouseover="closeDownList()"><a
			href="<%=request.getContextPath()%>/personal/personalInformation"
			class="topfont"><font face="微软雅黑" size="3.5">我的</font></a></li>
		<!-- /*change add onmouseover="closeDownList()"*/ -->
		<li onmouseover="openDownList()"><a href="javascript:"
			class="topfont" id="openDownListN"><font face="微软雅黑" size="3.5">消息<span
					class="newsNum"></span></font><img
				src="<%=request.getContextPath()%>/resources/img/pull_down.png"
				style="height: 15px; width: 15px;"></a>
		<li><a href="javascript:" class="topfont" id="openDownListY"><font
				face="微软雅黑" size="3.5">消息<span class="newsNum"></span></font><img
				src="<%=request.getContextPath()%>/resources/img/pull_up.png"
				style="height: 15px; width: 15px;"></a>

			<ul class="downList downLittleList" id="downList1"
				onmouseover="openDownList()">
				<a href="<%=request.getContextPath()%>/personal/atMeTwitters"
					class="downList2"><li class="downList1">@我<span
						class="newsNum1" id="unReadAtmeAmount"></span></li></a>
				<a href="<%=request.getContextPath()%>/personal/commentToMe"
					class="downList2"><li class="downList1">评论<span
						class="newsNum1" id="unReadCommentAmount"></span></li></a>
				<a href="<%=request.getContextPath()%>/personal/chat"
					class="downList2"><li class="downList1">私信<span
						class="newsNum1" id="unReadMessageAmount"></span></li></a>
				<a href="<%=request.getContextPath()%>/personal/thumbsupList"
					class="downList2"><li class="downList1">点赞<span
						class="newsNum1" id="unReadThumbsupAmount"></span></li></a>
			</ul></li>
		<!--  /*change add onmouseover="closeDownList()"*/ -->
		<li onmouseover="closeDownList()"><a
			href="<%=request.getContextPath()%>/personal/logout" class="topfont"><font
				face="微软雅黑" size="3.5">注销</font></a></li>
	</ul>

	<form id="searchForm">
		<input type="hidden" name="searchKey">
	</form>
</div>
