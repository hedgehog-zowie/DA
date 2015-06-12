<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<form id="pagerForm" method="post" action="config/userDefinedReport">
    <input type="hidden" name="pageNum" value="${page.currentPage}"/>
    <input type="hidden" name="numPerPage" value="${page.pageSize}"/>
</form>
<div class="pageHeader">
    <form rel="pagerForm" onsubmit="return navTabSearch(this);" action="config/userDefinedReport" method="post">
        <div class="searchBar">
            <ul class="searchContent">
                <li>
                    <label>报表名称：</label>
                    <input type="text" name="userDefinedReport.name" value="${userDefinedReport.name}"
                           placeholder="请输入自定义报表名称"/>
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
            <li><a class="add" rel="addRTag" href="config/userDefinedReport/add" target="dialog"><span>添加</span></a>
            </li>
            <%--<li><a class="edit" rel="editRTag" href="config/userDefinedReport/edit?id={rtag_id}" target="dialog" warn="请选择一个RTAG"><span>编辑</span></a>--%>
            <%--</li>--%>
            <li><a title="确实要删除这些记录吗?" target="selectedTodo" rel="ids" href="config/userDefinedReport/delete"
                   class="delete"><span>删除</span></a></li>
        </ul>
    </div>
    <table class="table" layoutH="138">
        <thead>
        <tr>
            <th width="1%"><input type="checkbox" group="ids" class="checkboxCtrl"></th>
            <th width="15%" align="center">名称</th>
            <th width="15%" align="center">用户</th>
            <th width="30%" align="center">报表文件</th>
            <th width="20%" align="center">描述</th>
            <th width="20%" align="center">创建时间</th>
            <th width="10%" align="center">编辑</th>
            <th width="10%" align="center">删除</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${userDefinedReportList}" var="userDefinedReport">
            <tr target="userDefinedReport_id" rel="${userDefinedReport.id}">
                <td width="1%"><input name="ids" value="${userDefinedReport.id}" type="checkbox"></td>
                <td width="15%">
                    <div style="overflow: hidden; text-overflow: ellipsis;"> ${userDefinedReport.name}
                    </div>
                </td>
                <td width="15%">
                    <div style="overflow: hidden; text-overflow: ellipsis;"> ${userDefinedReport.user}
                    </div>
                </td>
                <td width="15%">
                    <div style="overflow: hidden; text-overflow: ellipsis;">${userDefinedReport.path}
                    </div>
                </td>
                <td width="15%">
                    <div style="overflow: hidden; text-overflow: ellipsis;">${userDefinedReport.desc}
                    </div>
                </td>
                <td width="20%"><fmt:formatDate value="${userDefinedReport.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                <td width="10%"><a title="编辑" target="dialog" href="config/userDefinedReport/edit?id=${userDefinedReport.id}" class="btnEdit">编辑</a>
                </td>
                <td width="10%"><a title="删除" target="ajaxTodo" href="config/userDefinedReport/delete?ids=${userDefinedReport.id}"
                                   class="btnDel">删除</a></td>
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
