<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<form id="pagerForm" method="post" action="#rel#">
	<input type="hidden" name="pageNum" value="${page.currentPage}"/>
	<input type="hidden" name="showType" value="${showType}"/>
</form>
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="flow/source" method="post" rel="pagerForm"
		  id="report_form">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						开始日期：<input name="startDate" type="text" class="date" readonly="true" value="${startDate }"
									id="flow_source_startDate"/>
					</td>
					<td>
						结束日期：<input name="endDate" type="text" class="date" readonly="true" value="${endDate }"
									id="flow_source_endDate"/>
					</td>
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
				   href="flow/source/download?type=xlsx&startDate=${startDate }&endDate=${endDate}"
				   class="icon"><span>导出EXCEL</span></a></li>
			<li><a id="downloadCSV"
				   href="flow/source/download?type=csv&startDate=${startDate }&endDate=${endDate}"
				   class="icon"><span>导出CSV</span></a></li>
			<li><a id="downloadPPT"
				   href="flow/source/download?type=pptx&startDate=${startDate }&endDate=${endDate}"
				   class="icon"><span>导出PPT</span></a></li>
		</ul>
	</div>

	<div class="tabsContent" id="reportTable" layoutH="115">
		<div id="sourceReport" style="overflow-y:scroll; height:350px">
			${report}
		</div>
		<div class="panelBar" style="height: 5px"/>
		<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
		<div id="sourceChart" style="overflow-y:scroll; height:350px" />
		<script src="resources/js/flow/flow-source.js"></script>
	</div>

	<div class="panelBar">
		<div class="pagination" targetType="navTab" totalCount="${page.totalRecord}" numPerPage="${page.pageSize}"
			 pageNumShown="10" currentPage="${page.currentPage}"></div>
	</div>

</div>
