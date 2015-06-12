<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<form id="pagerForm" method="post" action="#rel#">
	<input type="hidden" name="pageNum" value="${page.currentPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
	<input type="hidden" name="orderField" value="${param.orderField}" />
	<input type="hidden" name="orderDirection" value="${param.orderDirection}" />
</form>

<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="config/advertisement" method="post">
	<div class="searchBar">
		<ul class="searchContent">
			<li>
				<label>推广渠道：</label>
				<input type="text" name="name" value="${iuniDaAd.name }"/>
			</li>
			<li>
				<label>合作形式：</label>
				<input type="text" name="type" value="${iuniDaAd.type }"/>
			</li>
		</ul>
		
		<div class="subBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">检索</button></div></div></li>
			</ul>
		</div>
	</div>
	</form>
</div>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" href="config/advertisement/add" mask="true" target="dialog" reloadFlag=1><span>添加</span></a></li>
			<li><a title="确实要禁用这些记录吗?" target="selectedTodo" rel="ids" postType="string" href="config/advertisement/updateBatchZero" class="delete"><span>禁用</span></a></li>
			<li><a title="确实要启用这些记录吗?" target="selectedTodo" rel="ids" postType="string" href="config/advertisement/updateBatchOne" class="icon"><span>启用</span></a></li>
<%-- 			<li><a title="确实要删除记录吗?" target="selectedTodo" rel="ids" postType="string" href="config/advertisement/updateBatch?numPerPage=${page.pageSize}" class="delete"><span>删除</span></a></li> --%>
<!-- 			<li><a class="edit" href="config/advertisement/setBasicInfoForUpdate?id={sid_user}" target="dialog" warn="请选择一行内容" mask="true"><span>编辑</span></a></li> -->
			<li class="line">line</li>
		</ul>
	</div>
	<table class="table" width="1200" layoutH="138">
		<thead>
			<tr align="center" >
				<th width="5%"><input type="checkbox" group="ids" class="checkboxCtrl"></th>
				<th width="20%">推广渠道</th>
				<th width="10%">合作形式</th>
				<th width="10%" >频道</th>
				<th width="10%"  >广告位</th>
				<th width="20%" >链接</th>
				<th width="10%" >添加时间</th>
				<th width="5%" >状态</th>
				<th width="5%" >禁用/启用</th>
				<th width="5%" >编辑</th>
			</tr>
		</thead>
		<tbody>
		   <c:forEach items="${iuniDaAds }" var="ad" varStatus="adstatus">
			<tr target="sid_user" rel="${ad.id }"  align="center" >
				<td><input name="ids" value="${ad.id }" type="checkbox"></td>
				<td>${ad.name }</td>
				<td>${ad.type }</td>
				<td>${ad.channel }</td>
				<td>${ad.position }</td>
				<td>${ad.url }</td>
				<td>
				     <fmt:formatDate value="${ad.createDate }" type="date" pattern="yyyy-MM-dd HH:mm:ss" />
				</td>
				<td>
				    <c:choose>
				       <c:when test="${ad.status == 1 }">
				          <a style="text-decoration: none;">有效</a> 
				       </c:when>
				       <c:otherwise>
				          <a style="color: red;text-decoration: none;">无效</a> 
				       </c:otherwise>
				    </c:choose>
				</td>
				<td>
<%-- 					<c:choose> --%>
<%-- 					   <c:when test="${ad.status ==1 }"> --%>
<%-- 					      <a title="禁用" target="ajaxTodo" href="config/advertisement/updateStatus?id=${ad.id }" class="btnDel" style="float: inherit;">禁用</a> --%>
<%-- 					   </c:when> --%>
<%-- 					   <c:otherwise> --%>
<%-- 					      <a title="启用" target="ajaxTodo" href="config/advertisement/updateStatus?id=${ad.id }" class="btnSelect" style="float: inherit;">启用</a> --%>
<%-- 					   </c:otherwise> --%>
<%-- 					</c:choose> --%>
					<a title="删除" target="ajaxTodo" href="config/advertisement/updateIuniDaAdCancelFlag?id=${ad.id }" class="btnDel" style="float: inherit;">删除</a>
					
				</td>
				<td>
				   <a class="btnEdit" href="config/advertisement/update?id=${ad.id }" target="dialog" title="编辑" rel="page${ad.id }"  mask="true" reloadFlag=1 style="float: inherit;">编辑</a>
				</td>
			</tr>
			</c:forEach>
			
			
		</tbody>
	</table>
	<div class="panelBar">
		<div class="pages">
			<span>显示</span>
			<select class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value})">
				<c:forEach var="i" begin="20" end="100" step="20">
                   <c:choose>
                      <c:when test="${i/workDay == 1.0}">
                          <option value=${i } selected="selected">${i } </option>
                      </c:when>
                      <c:otherwise>
                          <option value=${i } >${i } </option>
                      </c:otherwise>
                   </c:choose>
                 </c:forEach> 
			</select>
			<span>条，共${page.totalRecord}条</span>
		</div>
		
		<div class="pagination" targetType="navTab" totalCount="${page.totalRecord}" numPerPage="${page.pageSize}" pageNumShown="10" currentPage="${page.currentPage}"></div>

	</div>
</div>
