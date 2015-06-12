<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<div class="pageContent">
	<form method="post" action="config/registerPage/updateIuniDaRegisterPage" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
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
<!-- 			<p> -->
<!-- 			    <label>创建时间：</label> -->
<%-- 				<input value="${iuniDaRegisterPage.createDate}"   --%>
<!-- 				name="createDate"  type="datetime"  size="30" readonly="true"/> -->
				
<!-- 			</p> -->
			<p>
				<input value="${iuniDaRegisterPage.status }" name="status" type="hidden"   size="30" alt="状        态"/>
			</p>
			<p>
				<input value="${iuniDaRegisterPage.cancelFlag }" name="cancelFlag" type="hidden"   size="30" alt="禁用状态"/>
			</p>
			<p>
				<input value="${iuniDaRegisterPage.createBy }" name="createBy" type="hidden"   size="30" alt="创  建  人"/>
			</p>
			
<!-- 			<p> -->
<!-- 				<label>更  新  人：</label> -->
<%-- 				<input value="${iuniDaRegisterPage.updateBy }" name="updateBy" type="hidden"   size="30"/> --%>
<!-- 			</p> -->
<!-- 			<p> -->
<!-- 				<label>更新时间：</label> -->
<%-- 				<input value="${iuniDaRegisterPage.updateDate }" name="updateDate" type="hidden"   size="30"/> --%>
<!-- 			</p> -->
			<p>
				<input value="${iuniDaRegisterPage.id }" name="id" type="hidden"   size="30" alt="ID"/>
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
