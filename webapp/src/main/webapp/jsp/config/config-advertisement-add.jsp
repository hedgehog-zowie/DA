<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="pageContent">
	<form method="post" action="config/advertisement/add" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="56">
			<p>
				<label>推广渠道：</label>
				<input value="${iuniDaAd.name }" name="name" type="text" class="required" alt="请输入推广渠道" size="30" bs/>
			</p>
			<p>
				<label>合作形式：</label>
				<input value="${iuniDaAd.type }" name="type" class="required" type="text" alt="请输入合作形式" size="30"/>
			</p>
			<p>
				<label>频       道：</label>
				<input value="${iuniDaAd.channel }" type="text" class="required" name="channel" alt="请输入频道"  size="30"/>
			</p>
			<p>
				<label>广  告  位：</label>
				<input value="${iuniDaAd.position }" type="text" name="position"  class="required" alt="请输入广告位" size="30"/>
			</p>
			<p>
				<label>链        接：</label>
				<input value="${iuniDaAd.url }" name="url"  class="required url" type="text"  alt="请输入链接" size="30"/>
			</p>
			<p>
				<label>描        述：</label>
				<input value="${iuniDaAd.desc }" name="desc" type="text"  alt="请输入描述" size="30"/>
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
