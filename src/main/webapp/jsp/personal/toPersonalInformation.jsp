<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>温馨提示</title>
</head>
<body>
	保存成功，正在跳转至个人信息页面...
	<script>
function forward(){
	location.href="/weibo/personal/personalInformation";
	}
setTimeout(forward,1500);
</script>
</body>
</html>