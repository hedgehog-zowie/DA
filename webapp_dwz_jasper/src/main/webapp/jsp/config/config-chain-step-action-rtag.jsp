<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<form id="pagerForm" action="config/chain/listRTags">
    <input type="hidden" name="page.currentPage" value="${page.currentPage}"/>
    <input type="hidden" name="page.pageSize" value="${page.pageSize}" />
</form>
<div class="pageHeader">
    <form rel="pagerForm" method="post" action="config/chain/listRTags"
          onsubmit="return dwzSearch(this, 'dialog');">
        <div class="searchBar">
            <ul class="searchContent">
                <li>
                    <name>RTAG编号:</name>
                    <input class="textInput" name="rTag.rtag" value="${rTag.rtag}" type="text">
                </li>
            </ul>
            <div class="subBar">
                <ul>
                    <li>
                        <div class="buttonActive">
                            <div class="buttonContent">
                                <button type="submit">查询</button>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div class="button">
                            <div class="buttonContent">
                                <button type="button" multLookup="ids" warn="请选择RTAG">选择</button>
                            </div>
                        </div>
                    </li>
                </ul>
            </div>
        </div>
    </form>
</div>
<div class="pageContent">
    <table class="table" layoutH="118" targetType="dialog" width="100%">
        <thead>
        <tr>
            <th width="1%"><input type="checkbox" group="ids" class="checkboxCtrl"></th>
            <th width="15%" align="center">功能</th>
            <th width="15%" align="center">功能编号</th>
            <th width="30%" align="center">对应URL</th>
            <th width="20%" align="center">添加日期</th>
            <th width="10%" align="center">选择</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${rTagList}" var="rtag">
            <tr target="rtag_id" rel="${rtag.id}">
                <td><input name="ids" value="${rtag.id}" type="checkbox"></td>
                <td>${rtag.name}</td>
                <td>${rtag.rtag}</td>
                <td>${rtag.info}</td>
                <td><fmt:formatDate value="${rtag.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                <td>
                    <a class="btnSelect" href="javascript:$.bringBack({id:'${rtag.id}', rtag:'${rtag.rtag}', name:'${rtag.name}', info:'${rtag.info}'})" title="查找带回">选择</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <div class="panelBar">
        <div class="pages">
            <span>每页</span>
            <select name="numPerPage" onchange="dwzPageBreak({targetType:'dialog', numPerPage:'10'})">
                <option value="10" selected="selected">10</option>
                <option value="20">20</option>
                <option value="50">50</option>
                <option value="100">100</option>
            </select>
            <span>条，共2条</span>
        </div>
        <div class="pagination" totalCount="${page.totalRecord}" numPerPage="${page.pageSize}"
             pageNumShown="10" currentPage="${page.currentPage}"></div>
    </div>
</div>