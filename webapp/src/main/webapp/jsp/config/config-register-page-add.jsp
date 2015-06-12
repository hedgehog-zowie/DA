<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="pageContent">
	<form method="post" action="config/registerPage/addregisterPage" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="56">
			<p>
				<label>入口页名称：</label>
				<input value="${iuniDaRegisterPage.name }" name="name" type="text" class="required" alt="请输入推广渠道" size="30" />
			</p>
			<p>
				<label>入口链接：</label>
				<input value="${iuniDaRegisterPage.url }" name="url" class="required url" type="text" alt="请输入合作形式" size="30"/>
			</p>
			
			<p>
				<label>描        述：</label>
				<input value="${iuniDaRegisterPage.desc }" name="desc" type="text"  alt="请输入描述" size="30"/>
			</p>
			<div class="divider"></div>
			
			
		</div>
		<div class="formBar">
			<ul>
				<!--<li><a class="buttonActive" href="javascript:;"><span>保存</span></a></li>-->
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">保存</button></div></div></li>
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
				</li>
			</ul>
		</div>
	</form>
</div>
