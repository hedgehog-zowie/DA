<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="pageHeader" style="border:1px #B8D0D6 solid">
    <form id="pagerForm" onsubmit="return divSearch(this, 'dataBox');" action="config/chain/listStepData" method="post">
        <input type="hidden" name="pageNum" value="${page.currentPage}"/>
        <input type="hidden" name="numPerPage" value="${page.pageSize}"/>
        <input type="hidden" name="chain.id" value="${chain.id}"/>
    </form>
</div>
<div class="pageContent" style="border-left:1px #B8D0D6 solid;border-right:1px #B8D0D6 solid">
    <div class="panelBar">
        <ul class="toolBar">
            <li><a class="add" href="config/chain/addStepData?step.chain.id=${chain.id}" target="dialog"
                   mask="true"><span>添加</span></a></li>
            <li><a class="delete" href="config/chain/deleteStepData" rel="ids" target="selectedTodo" title="确实要删除这些记录吗?"><span>删除</span></a>
            </li>
            <%--<li><a class="edit" href="config/chain/editStepData?step.id={step_id}" target="dialog"--%>
                   <%--mask="true"><span>修改</span></a></li>--%>
        </ul>
    </div>
    <table class="table" width="99%" layoutH="260" rel="dataBox">
        <thead>
        <tr>
            <th width="1%"><input type="checkbox" group="ids" class="checkboxCtrl"></th>
            <th width="9%">关键路径</th>
            <th width="10%">步骤编号</th>
            <th width="10%">步骤名称</th>
            <th width="20%">取数定义</th>
            <th width="10%">order_type</th>
            <th width="10%">添加时间</th>
            <th width="10%">编辑</th>
            <th width="10%">删除</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${stepDataList}" var="step">
            <tr target="step_id" rel="${step.id}">
                <td><input name="ids" value="${step.id}" type="checkbox"></td>
                <td>${step.chain.name}</td>
                <td>${step.stepIndex}</td>
                <td>${step.name}</td>
                <td>${step.desc}</td>
                <td>${step.orderType}</td>
                <td><fmt:formatDate value="${step.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                <td><a title="编辑" target="dialog" href="config/chain/editStepData?step.id=${step.id}"
                       class="btnEdit">编辑</a></td>
                <td><a title="删除" target="ajaxTodo" href="config/chain/deleteStepData?ids=${step.id}"
                       class="btnDel">删除</a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <div class="panelBar">
        <div class="pages">
            <span>显示</span>
            <select class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value}, 'dataBox')">
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
        <div class="pagination" rel="dataBox" totalCount="${page.totalRecord}" numPerPage="${page.pageSize}"
             pageNumShown="10" currentPage="${page.currentPage}"></div>
    </div>
</div>