<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<form id="pagerForm" method="post" action="config/pagetag">
    <input type="hidden" name="pageNum" value="${page.currentPage}"/>
    <input type="hidden" name="numPerPage" value="${page.pageSize}"/>
</form>
<div class="pageHeader">
    <form rel="pagerForm" onsubmit="return navTabSearch(this);" action="config/pagetag" method="post">
        <div class="searchBar">
            <ul class="searchContent">
                <li>
                    <name>功能编号：</name>
                    <input type="text" name="rTag.rtag" value="${rTag.rtag}" placeholder="请输入功能编号"/>
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
            <li><a class="add" rel="addRTag" href="config/pagetag/add" target="dialog"><span>添加</span></a></li>
            <%--<li><a class="edit" rel="editRTag" href="config/pagetag/edit?id={rtag_id}" target="dialog" warn="请选择一个RTAG"><span>编辑</span></a>--%>
            <%--</li>--%>
            <li><a title="确实要删除这些记录吗?" target="selectedTodo" rel="ids" href="config/pagetag/delete"
                   class="delete"><span>删除</span></a></li>
        </ul>
    </div>
    <table class="table" layoutH="138">
        <thead>
        <tr>
            <th width="1%"><input type="checkbox" group="ids" class="checkboxCtrl"></th>
            <th width="15%" align="center">功能</th>
            <th width="15%" align="center">功能编号</th>
            <th width="30%" align="center">对应URL</th>
            <th width="20%" align="center">添加日期</th>
            <th width="10%" align="center">编辑</th>
            <th width="10%" align="center">删除</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${rTagList}" var="rtag">
            <tr target="rtag_id" rel="${rtag.id}">
                <td width="1%"><input name="ids" value="${rtag.id}" type="checkbox"></td>
                <td width="15%">${rtag.name}</td>
                <td width="15%">
                    <div style="overflow: hidden; text-overflow: ellipsis;"> ${rtag.rtag}
                    </div>
                </td>
                <td width="30%">
                    <div style="overflow: hidden; text-overflow: ellipsis;">${rtag.info}
                    </div>
                </td>
                <td width="20%"><fmt:formatDate value="${rtag.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                <td width="10%"><a title="编辑" target="dialog" href="config/pagetag/edit?id=${rtag.id}" class="btnEdit">编辑</a></td>
                <td width="10%"><a title="删除" target="ajaxTodo" href="config/pagetag/delete?ids=${rtag.id}" class="btnDel">删除</a></td>
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
