<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>出现错误</title>
<style type="text/css">
.bgBody {
	background-color: #5170ad;
	text-align: center;
	height: 100%;
}

.Img404 {
	width: 600px;
	height: 450px;
	border-radius: 10px;
	position: relative;
	top: 30px;
}

.textArea {
	width: 600px;
	text-align: left;
	height: 100px;
	position: relative;
	top: 35px;
	left: 50%;
	margin-left: -300px;
}

.warningImg {
	display: inline-block;
	width: 95px;
}

.textAreaFont {
	font-size: 21px;
	display: inline-block;
	position: relative;
	top: -20px;
	font-weight: bold;
	color: white;
	left: 10px;
	width: 490px;
}
</style>
</head>
<body class="bgBody">
	<img src="<%=request.getContextPath()%>/resources/img/404.jpg"
		class="Img404">
	<div class="textArea">
		<img src="<%=request.getContextPath()%>/resources/img/Warning.png"
			class="warningImg"><span class="textAreaFont">对不起~您访问的内容不存在或服务器出错！</span>
	</div>
	<script>

 </script>
</body>
</html>