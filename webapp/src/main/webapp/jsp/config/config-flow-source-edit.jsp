<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class=pageContent>
	<form method="post" action="<c:url value='config/flowSource/save'/>" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone)" > 
		<div><input type="hidden" name="flowSource.id" value="${flowSource.id }"/></div>
		<div class="pageFormContent">
	 		<p>
				<label>名称 ：</label>
				<input type="text" name="flowSource.name" value="${ flowSource.name }"  class="required" />
			</p>
			<p>
				<label>URL：</label>
				<input type="text"  name="flowSource.url" value="${ flowSource.url}" class="required url"/>
			</p>
		
			<p>
				<label>分类：</label>
				<select name="flowSource.flowSourceType.id" class="required" >
					<c:forEach items="${ flowSourceTypes }" var="type">
						<c:if test="${ flowSource.flowSourceType.id eq type.id}">
							<option value="${type.id }" selected="selected">${type.name }</option>
						</c:if>
						<c:if test="${ flowSource.flowSourceType.id ne type.id}">
							<option value="${type.id }">${type.name }</option>
						</c:if>
					</c:forEach>
				</select>
			</p> 
		
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">保存</button></div></div></li>
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
				</li>
			</ul>
		</div>
	</form>
</div>