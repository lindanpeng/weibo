<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>上传头像</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/resources/css/uploadPortrait.css">
</head>
<body>
<body onload="init();">
	<div id="container">
		<a id="selectBtn" href="javascript:void(0);"
			onclick="document.getElementById('input').click();">选择</a> <a
			id="saveBtn" href="javascript:void(0);" onclick="saveImage();">保存</a>
		<input type="file" id="input" size="10" style="visibility: hidden;"
			onchange="selectImage(this.files)" />

		<div id="wrapper">
			<canvas id="cropper"></canvas>
			<a id="rotateLeftBtn" href="javascript:void(0);"
				onclick="rotateImage(event);">向左旋转</a> <a id="rotateRightBtn"
				href="javascript:void(0);" onclick="rotateImage(event);">向右旋转</a> <span
				id="status"
				style="position: absolute; left: 350px; top: 10px; width: 100px;"></span>
			<div id="previewContainer">
				<canvas id="preview180" width="180" height="180" class="preview"></canvas>
				<span
					style="display: block; width: 100%; padding-top: 5px; text-align: center;">大尺寸图片，180x180像素</span>

				<canvas id="preview100" width="100" height="100"
					style="position: absolute; left: 230px; top: 0px;" class="preview"></canvas>
				<span
					style="position: absolute; left: 230px; top: 110px; width: 100px; text-align: center;">中尺寸图片
					100x100像素</span>

				<canvas id="preview50" width="50" height="50"
					style="position: absolute; left: 255px; top: 150px;"
					class="preview"></canvas>
				<span
					style="position: absolute; left: 245px; top: 210px; width: 70px; text-align: center;">小尺寸图片
					50x50像素</span>
			</div>
		</div>
	</div>
	<script
		src="<%=request.getContextPath()%>/resources/js/jquery-1.11.3.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/resources/js/imagecropper.js"></script>
	<script
		src="<%=request.getContextPath()%>/resources/interactive/personal/uploadPortrait.js"></script>
</body>
</html>