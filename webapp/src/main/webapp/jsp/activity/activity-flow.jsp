<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<form id="pagerForm" method="post" action="active/flow">
    <input type="hidden" name="pageNum" value="${page.currentPage}"/>
    <input type="hidden" name="numPerPage" value="${page.pageSize}"/>
</form>
<div class="pageHeader">
    <form rel="pagerForm" onsubmit="return navTabSearch(this);" action="activity/flow" method="post">
        <div class="searchBar">
            <ul class="searchContent">
                <li>
                    <label>页面地址：</label>
                    <input type="text" name="url" value="${url}" placeholder="请输入页面地址"/>
                </li>
                <li>
                    <label>渠道号：</label>
                    <input type="text" name="channelCode" value="${channelCode}" placeholder="请输入渠道号"/>
                </li>
                <li>
                    <label>时间：</label>
                    <input type="text" name="startDateStr" value="${startDate}" class="date" minDate="2015-01-01"
                           maxDate="2020-01-01" readonly="true"/>
                    <a class="inputDateButton" href="javascript:;">选择</a>
                </li>
                <li>
                    <label> -- </label>
                    <input type="text" name="endDateStr" value="${endDate}" class="date" minDate="2015-01-01"
                           maxDate="2020-01-01" readonly="true"/>
                    <a class="inputDateButton" href="javascript:;">选择</a>
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
    </div>
    <table class="table" width="100%" layoutH="115">
        <thead>
        <tr>
            <c:forEach items="${columnList}" var="column">
                <th width="${rate}%" align="center">${column}</th>
            </c:forEach>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${activeKpiList}" var="activeKpi">
            <tr>
                <td width="${rate}%" align="center">${activeKpi.date}</td>
                <td width="${rate}%" align="center">${activeKpi.pv}</td>
                <td width="${rate}%" align="center">${activeKpi.uv}</td>
                <c:forEach items="${activeKpi.countMap}" var="count">
                    <td width="${rate}%" align="center">${count.value}</th>
                </c:forEach>
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


