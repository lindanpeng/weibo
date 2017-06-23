$(function(){
	$("#toUploadPortrait").click(function(){
		 window.open("/weibo/personal/toUploadPortrait");
	});
	$("#editBtn").click(function(){
		location.href="/weibo/personal/toAmendPersonalInfo";
	});
});
function refreshWin(){
	location.href="/weibo/personal/personalInformation";
}