<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<form id="pagerForm" method="post" action="config/flowSource">
	<input type="hidden" name="pageNum" value="${page.currentPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>

<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="config/flowSource" method="post">
	<div class="searchBar">
		<ul class="searchContent">
			<li>
				<name>名称：</name>
				<input type="text" name="flowSource.name" value="${flowSource.name }" placeholder="请输入名称"/>
			</li>
			<li>
				<name>URL:</name>
				<input type="text" name="flowSource.url" value="${flowSource.url }" placeholder="请输入URL"/>
			</li>
		</ul>
		<div class="subBar">
		<ul>
                    <li>
                        <div class="buttonActive">
                            <div class="buttonContent">
                                <button type="submit">检索</button>
                            </div>
                        </div>
                    </li>
                </ul>
		</div>
	</div>
	</form>
</div>
<div class="pageContent">
	<div class="panelBar">
			<ul class="toolBar">
			 <li><a  class="add" target="dialog"   href="config/flowSource/add"><span>添加</span></a></li> 
		</ul>
	</div>
	<table class="table" width="1200" layoutH="138">
		<thead>
			<tr>
				<th style="display:none">ID</th>
				<!-- <th style="width:5%" align="center">序号</th> -->
				<th style="width:20%" align="center">名称</th>
				<th style="width:40%" align="center">URL</th>
				<th style="width:20%" align="center">分类</th>
				<th style="width:10%" >编辑</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="v" items="${flowSources }">
				<tr>
					<td style="display:none">${v.id }</td>
					<td>${v.name }</td>
					<td>${v.url }</td>
					<td>${v.flowSourceType.name }</td>
					<td><a title="编辑" target="dialog" rel="tab_flowSource" mask="true"  href="config/flowSource/edit?id=${v.id }" class="btnEdit">编辑</a></td>
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