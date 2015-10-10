<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="pageContent">
	<form method="post" action="config/advertisement/updateIuniDaAd" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="56">
			<p>
				<name>推广渠道：</name>
				<input value="${iuniDaAd.name }" name="name" type="text" class="required" alt="请输入推广渠道" size="30" />
			</p>
			<p>
				<name>合作形式：</name>
				<input value="${iuniDaAd.type }" name="type" class="required" type="text" alt="请输入合作形式" size="30"/>
			</p>
			<p>
				<name>频       道：</name>
				<input value="${iuniDaAd.channel }" type="text" class="required" name="channel" alt="请输入频道"  size="30"/>
			</p>
			<p>
				<name>广  告  位：</name>
				<input value="${iuniDaAd.position }" type="text" name="position"  class="required" alt="请输入广告位" size="30"/>
			</p>
			<p>
				<name>链        接：</name>
				<input value="${iuniDaAd.url }" name="url"  class="required url" type="text"  alt="请输入链接" size="30"/>
			</p>
			<p>
				<name>描        述：</name>
				<input value="${iuniDaAd.desc }" name="desc" type="text"  alt="请输入描述" size="30"/>
			</p>
			
			<p>
				<input value="${iuniDaAd.status }" name="status" type="hidden"   size="30" alt="状        态"/>
			</p>
			<p>
				<input value="${iuniDaAd.cancelFlag }" name="cancelFlag" type="hidden"   size="30" alt="禁用状态"/>
			</p>
			<p>
				<input value="${iuniDaAd.createBy }" name="createBy" type="hidden"   size="30" alt="创  建  人"/>
			</p>
			
<!-- 			<p> -->
<!-- 				<name>更  新  人：</name> -->
<%-- 				<input value="${iuniDaAd.updateBy }" name="updateBy" type="hidden"   size="30"/> --%>
<!-- 			</p> -->
<!-- 			<p> -->
<!-- 				<name>更新时间：</name> -->
<%-- 				<input value="${iuniDaAd.updateDate }" name="updateDate" type="hidden"   size="30"/> --%>
<!-- 			</p> -->
			<p>
				<input value="${iuniDaAd.id }" name="id" type="hidden"   size="30" alt="ID"/>
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
