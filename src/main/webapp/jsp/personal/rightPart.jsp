<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="rightPart" id="RightH">
	<div class="myMessage">
		<a href="<%=request.getContextPath()%>/personal/personalInformation"><img
			src="${userDto.user.portraitUrl}" class="myMessageImg"></a> <a
			style="text-decoration: none;">
			<h4 class="loginUserName userName">${userDto.user.nickname}</h4>
		</a> <br> <a href="<%=request.getContextPath()%>/personal/myFanList"><span
			class="loginFollowFont">粉丝<span style="color: yellow;">${userDto.fanAmount}</span>人
		</span></a> &nbsp;<a
			href="<%=request.getContextPath()%>/personal/myConcernedUserList"><span
			class="loginFollowFont">关注<span style="color: yellow;">${userDto.concernedUserAmount}</span>人
		</span></a> &nbsp;<a href="<%=request.getContextPath()%>/personal/myTwitterList"><span
			class="loginFollowFont">微博<span style="color: yellow;">${userDto.twitterAmount}</span>条
		</span></a>
	</div>




	<ul class="nav nav-pills nav-stacked ">
		<li class="leftThird radiu"><a
			href="<%=request.getContextPath()%>/personal/home" class="topfont "><font
				face="微软雅黑" size="3.5">全部</font></a></li>
		<li class="leftThird radiu"><a
			href="<%=request.getContextPath()%>/personal/concernedUserTwitters"
			class="topfont"><font face="微软雅黑" size="3.5">我的关注</font></a></li>
		<li class="leftSecond radiu"><a
			href="<%=request.getContextPath()%>/personal/collectedTwitters"
			class="topfont"><font face="微软雅黑" size="3.5">我的收藏</font></a></li>
	</ul>

</div>