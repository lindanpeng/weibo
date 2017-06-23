<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- 模态弹出窗内容 -->
<div class="modal" id="popupbox" tabindex="-1" role="dialog"
	aria-labelledby="mySmallModalLabel" aria-hidden="true"
	style="margin-top: 100px;">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
				</button>
				<h4 class="modal-title">提示</h4>
			</div>
			<div class="modal-body">
				<p>请登录后进行操作！</p>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-primary" id="toLogin_btn">去登录</button>
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
			</div>
		</div>
	</div>
</div>
