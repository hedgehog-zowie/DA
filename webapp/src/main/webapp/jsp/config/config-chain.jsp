<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<form id="pagerForm" method="post" action="config/chain">
    <input type="hidden" name="page.currentPage" value="${page.currentPage}"/>
    <input type="hidden" name="page.pageSize" value="${page.pageSize}"/>
</form>
<div class="pageHeader">
    <form rel="pagerForm" onsubmit="return navTabSearch(this);" action="config/chain" method="post">
        <div class="searchBar">
            <ul class="searchContent">
                <li>
                    <label>关键路径：</label>
                    <input type="text" name="chain.name" value="${chain.name}" placeholder="请输入关键路径名称"/>
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
            <li><a class="add" rel="addChain" href="config/chain/add" target="dialog"><span>添加</span></a></li>
            <%--<li><a class="edit" rel="editChain" href="config/chain/edit?id={chain_id}" target="dialog" warn="请选择一个关键路径"><span>修改</span></a>--%>
            <%--</li>--%>
            <li><a title="确实要禁用这些记录吗?" target="selectedTodo" rel="ids" href="config/chain/disable" class="delete"><span>禁用</span></a>
            </li>
            <li><a title="确实要启用这些记录吗?" target="selectedTodo" rel="ids" href="config/chain/enable" class="icon"><span>启用</span></a>
            </li>
            <li><a class="edit" rel="editStep" href="config/chain/listStep?chain.id={chain_id}" target="navTab"
                   warn="请选择一个关键路径"><span>查看/修改步骤</span></a>
        </ul>
    </div>
    <table class="table" layoutH="138">
        <thead>
        <tr>
            <th width="1%"><input type="checkbox" group="ids" class="checkboxCtrl"></th>
            <th width="19%" align="center" orderField="accountName">关键路径</th>
            <th width="50%" align="center" orderField="accountType">产品</th>
            <th width="10%" align="center" orderField="accountType">状态</th>
            <th width="10%" align="center" orderField="accountCert">编辑</th>
            <th width="10%" align="center" orderField="accountCert">删除</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${chainList}" var="chain">
            <tr target="chain_id" rel="${chain.id}">
                <td><input name="ids" value="${chain.id}" type="checkbox"></td>
                <td>${chain.name}</td>
                <td>${chain.product}</td>
                <td>
                    <c:if test="${chain.status == 1}">
                        有效
                    </c:if>
                    <c:if test="${chain.status == 0}">
                        无效
                    </c:if>
                </td>
                <td><a target="dialog" href="config/chain/edit?id=${chain.id}" class="btnEdit">编辑</a></td>
                <td><a title="删除" target="ajaxTodo" href="config/chain/delete?ids=${chain.id}" class="btnDel">删除</a></td>
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
