<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<form id="pagerForm" method="post" action="userDefined">
    <input type="hidden" name="pageNum" value="${page.currentPage}"/>
    <input type="hidden" name="reportId" value="${reportId}"/>
</form>
<div class="pageContent">
    <div class="panelBar">
        <ul class="toolBar">
            <li><a id="downloadExcel" href="userDefined/download?type=xlsx&reportId=${reportId}"
                   class="icon"><span>导出EXCEL</span></a></li>
            <li><a id="downloadCSV" href="userDefined/download?type=csv&reportId=${reportId}"
                   class="icon"><span>导出CSV</span></a></li>
            <li><a id="downloadPdf" href="userDefined/download?type=pdf&reportId=${reportId}"
                   target="_blank" class="icon"><span>导出PDF</span></a></li>
            <li><a id="downloadPPT" href="userDefined/download?type=pptx&reportId=${reportId}"
                   class="icon"><span>导出PPT</span></a></li>
        </ul>
    </div>
    <div layoutH="50">
        ${report}
    </div>

    <div class="panelBar">
        <div ref="userDefined" class="pagination" targetType="navTab"
             totalCount=${page.totalRecord} numPerPage=${page.pageSize} pageNumShown="10"
             currentPage=${page.currentPage}></div>
    </div>
</div>