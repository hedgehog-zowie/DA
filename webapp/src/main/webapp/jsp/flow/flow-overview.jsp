<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<form id="pagerForm" method="post" action="#rel#">
    <input type="hidden" name="pageNum" value="${page.currentPage}"/>
    <input type="hidden" name="showType" value="${showType}"/>
</form>
<div class="pageHeader">
    <form onsubmit="return navTabSearch(this);" action="flow/overview" method="post" rel="pagerForm"
          id="report_form">
        <div class="searchBar">
            <table class="searchContent">
                <tr>
                    <td>
                        开始日期：<input name="startDate" type="text" class="date" readonly="true" value="${startDate }"
                                    id="flow_overview_startDate"/>
                    </td>
                    <td>
                        结束日期：<input name="endDate" type="text" class="date" readonly="true" value="${endDate }"
                                    id="flow_overview_endDate"/>
                    </td>
                    <%--<td>--%>
                        <%--日期类型：--%>
                    <%--</td>--%>
                    <%--<td>--%>
                        <%--<select class="combox" name="workDay" id="workDay">--%>
                            <%--<c:choose>--%>
                                <%--<c:when test="${'2' == workDay}">--%>
                                    <%--<option value="2" selected="selected">全选</option>--%>
                                <%--</c:when>--%>
                                <%--<c:otherwise>--%>
                                    <%--<option value="2">全选</option>--%>
                                <%--</c:otherwise>--%>
                            <%--</c:choose>--%>
                            <%--<c:choose>--%>
                                <%--<c:when test="${'1' == workDay}">--%>
                                    <%--<option value="1" selected="selected">工作日</option>--%>
                                <%--</c:when>--%>
                                <%--<c:otherwise>--%>
                                    <%--<option value="1">工作日</option>--%>
                                <%--</c:otherwise>--%>
                            <%--</c:choose>--%>
                            <%--<c:choose>--%>
                                <%--<c:when test="${'0' == workDay}">--%>
                                    <%--<option value="0" selected="selected">非工作日</option>--%>
                                <%--</c:when>--%>
                                <%--<c:otherwise>--%>
                                    <%--<option value="0">非工作日</option>--%>
                                <%--</c:otherwise>--%>
                            <%--</c:choose>--%>
                        <%--</select>--%>
                    <%--</td>--%>
                    <%--<td>--%>
                        <%--终端类型：--%>
                    <%--</td>--%>
                    <%--<td>--%>
                        <%--<select class="combox" name="terminal_id">--%>
                            <%--<c:choose>--%>
                                <%--<c:when test="${'2' == terminal_id}">--%>
                                    <%--<option value="2" selected="selected">全选</option>--%>
                                <%--</c:when>--%>
                                <%--<c:otherwise>--%>
                                    <%--<option value="2">全选</option>--%>
                                <%--</c:otherwise>--%>
                            <%--</c:choose>--%>
                            <%--<c:choose>--%>
                                <%--<c:when test="${'1' == terminal_id}">--%>
                                    <%--<option value="1" selected="selected">PC端</option>--%>
                                <%--</c:when>--%>
                                <%--<c:otherwise>--%>
                                    <%--<option value="1">PC端</option>--%>
                                <%--</c:otherwise>--%>
                            <%--</c:choose>--%>
                            <%--<c:choose>--%>
                                <%--<c:when test="${'0' == terminal_id}">--%>
                                    <%--<option value="0" selected="selected">移动端</option>--%>
                                <%--</c:when>--%>
                                <%--<c:otherwise>--%>
                                    <%--<option value="0">移动端</option>--%>
                                <%--</c:otherwise>--%>
                            <%--</c:choose>--%>

                        <%--</select>--%>
                    <%--</td>--%>
                </tr>
            </table>
            <div class="subBar">
                <ul>
                    <li>
                        <div class="buttonActive">
                            <div class="buttonContent">
                                <button id="submit">查询</button>
                            </div>
                        </div>
                    </li>

                </ul>
            </div>
        </div>
    </form>
</div>

<div id="div-test" class="pageContent">
    <div class="panelBar">
        <ul class="toolBar">
            <li><a id="downloadExcel"
                   href="flow/overview/download?type=xlsx&startDate=${startDate }&endDate=${endDate}"
                   class="icon"><span>导出EXCEL</span></a></li>
            <li><a id="downloadCSV"
                   href="flow/overview/download?type=csv&startDate=${startDate }&endDate=${endDate}"
                   class="icon"><span>导出CSV</span></a></li>
            <!-- <li><a id="downloadPdf" href="flow/overview/download?type=pdf"
                   target="_blank" class="icon"><span>导出PDF</span></a></li> -->
            <li><a id="downloadPPT"
                   href="flow/overview/download?type=pptx&startDate=${startDate }&endDate=${endDate}"
                   class="icon"><span>导出PPT</span></a></li>
        </ul>
        <!--         <div  class="pagination" targetType="navTab" totalCount=${page.totalRecord} numPerPage=${page.pageSize} pageNumShown="10" currentPage=${page.currentPage}></div> -->
    </div>

    <div class="tabsContent" id="reportTable" layoutH="115">
        <div id="overViewReport" style="overflow-y:scroll; height:350px">
            <%--<jsp:include page="flow_overview_report.jsp"/>--%>
            ${report}
        </div>
        <div class="panelBar" style="height: 5px"/>
        <!-- 为ECharts准备一个具备大小（宽高）的Dom -->
        <div id="overViewChart" style="overflow-y:scroll; height:350px">
            <%--<jsp:include page="flow_overview_chart.jsp"/>--%>
        </div>
        <script src="resources/js/flow/flow-overview.js"></script>
    </div>

    <div class="panelBar">
        <div class="pagination" targetType="navTab" totalCount="${page.totalRecord}" numPerPage="${page.pageSize}"
             pageNumShown="10" currentPage="${page.currentPage}"></div>
    </div>

</div>
