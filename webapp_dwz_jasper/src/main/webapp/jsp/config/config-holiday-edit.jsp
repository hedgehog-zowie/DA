<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class=pageContent>
	<form method="post" action="config/holiday/save" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone)" > 
		<div class="pageFormContent">
			
			<p><input type="hidden" name="holiday.id" value="${holiday.id }"/></p>
	 		<p>
				<name>节日/日期调整：</name>
				<input name="holiday.name" value="${ holiday.name }" type="text" class="required" />
			</p>
			<p>
				<name>起始日期：</name>
				<input type="text"  name="startDate" value="${ holiday.startDate}" class="date textInput readonly required" size="30" /><a class="inputDateButton" href="javascript:;">选择</a>
			</p>
		
			<p>
				<name>结束日期：</name>
				<input type="text" name="endDate" value="${ holiday.endDate}" class="date textInput readonly required" size="30" /><a  class="inputDateButton" href="javascript:;">选择</a>
			</p> 
			<p>
				<name>节假日/调整日期类型：</name>
				<select name="holiday.holidayType.id" class="required" >
					<c:forEach items="${ holidayTypes }" var="type">
						<c:if test="${ holiday.holidayType.id eq type.id}">
							<option value="${type.id }" selected="selected">${type.name }</option>
						</c:if>
						<c:if test="${ holiday.holidayType.id ne type.id}">
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