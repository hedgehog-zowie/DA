<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<form id="pagerForm" method="post" action="config/holiday">
	<input type="hidden" name="pageNum" value="${page.currentPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>

<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="config/holiday" method="post">
	<div class="searchBar">
		<ul class="searchContent">
		</ul>
		<div class="subBar">
		</div>
	</div>
	</form>
</div>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<%-- <li><a  class="add" target="dialog" href="${pageContext.request.contextPath }/holidayAdd.jsp"><span>添加</span></a></li> 
			<li><a  class="add" target="dialog" href="javascript:window.location.href='holidayAdd.jsp'"><span>添加</span></a></li>
			<li><input type="button" name="btnAdd" onclick="window.location.href='config/holidayAdd.jsp'" id="btnAdd" value="添加员工通讯信息" /></li>--%>
			<li><a  class="add" target="dialog" mask="true"  href="config/holiday/add"><span>添加</span></a></li>
			
		</ul>
	</div>
	<table class="table" width="1200" layoutH="138">
		<thead>
			<tr>
				<th style="display:none">ID</th>
				<th style="width:10%" align="center">起始日期</th>
				<th style="width:10%" align="center">结束日期</th>
				<th style="width:10%" align="center">节日/日期调整</th>
				<th style="width:10%" align="center">节假日/调整日期类型</th>
				<th style="width:5%" >删除</th>
				<th style="width:5%" >编辑</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="v" items="${holidays }">
				<tr>
					<td style="display:none">${v.id }</td>
					<td>${v.startDate }</td>
					<td>${v.endDate }</td>
					<td>${v.name }</td>
					<td>${v.holidayType.name }</td>
					<td><a title="删除" target="ajaxTodo" href="config/holiday/delete?id=${v.id }" class="btnDel">删除</a></td>
					<td><a title="编辑" target="dialog" href="config/holiday/edit?id=${v.id }" class="btnEdit">编辑</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	
	
	<div class="panelBar">
        <div class="pages">
            <span>显示</span>
            <select class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value})">
                <c:if test="${page.pageSize == 20}">
                    <option selected value="${page.pageSize}">${page.pageSize}</option>
                    <option value="50">50</option>
                    <option value="100">100</option>
                </c:if>
                <c:if test="${page.pageSize == 50}">
                    <option value="20">20</option>
                    <option selected value="${page.pageSize}">${page.pageSize}</option>
                    <option value="100">100</option>
                </c:if>
                <c:if test="${page.pageSize == 100}">
                    <option value="20">20</option>
                    <option value="50">50</option>
                    <option selected value="${page.pageSize}">${page.pageSize}</option>
                </c:if>
            </select>
            <span>条，共${page.totalRecord}条</span>
        </div>

        <div class="pagination" targetType="navTab" totalCount="${page.totalRecord}" numPerPage="${page.pageSize}"
             pageNumShown="10" currentPage="${page.currentPage}"></div>

    </div>
</div>