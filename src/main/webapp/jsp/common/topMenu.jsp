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
	<div class="navbar-left searching">
		<form class="form-horizontal" role="form"
			action="<%=request.getContextPath()%>/common/search"
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
		<li><a href="<%=request.getContextPath()%>/common/index"
			class="topfontChosen"><font face="微软雅黑" size="3.5">首页</font></a></li>
		<li><a href="<%=request.getContextPath()%>/common/login"
			class="topfont"><font face="微软雅黑" size="3.5">登录</font></a></li>

		<li><a href="<%=request.getContextPath()%>/common/register"
			class="topfont" id="openDownListN"><font face="微软雅黑" size="3.5">注册</font></a>
		<li><a href="#bottomPart" class="topfont"><font face="微软雅黑"
				size="3.5">关于我们</font></a></li>
	</ul>
</div>