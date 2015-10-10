<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="pageContent" style="padding:5px">
    <div class="panel" defH="40">
        <h1>关键路径信息</h1>
        <div>
            <name>关键路径：</name><input type="text" value="${chain.name}" readonly/>
            <name>产品：</name><input type="text" value="${chain.product}" readonly/>
        </div>
    </div>
    <div class="divider"></div>
    <div class="tabs">
        <div class="tabsHeader">
            <div class="tabsHeaderContent">
                <ul>
                    <li><a href="config/chain/listStepPage?chain.id=${chain.id}" target="ajax" rel="pageBox"><span>页面配置</span></a></li>
                    <li><a href="config/chain/listStepAction?chain.id=${chain.id}" target="ajax" rel="actionBox"><span>点击按钮配置</span></a></li>
                    <li><a href="config/chain/listStepData?chain.id=${chain.id}" target="ajax" rel="dataBox"><span>数据统计配置</span></a></li>
                </ul>
            </div>
        </div>
        <div class="tabsContent">
            <div>
                <div id="pageBox" class="unitBox">
                    <jsp:include page="config-chain-step-page.jsp"/>
                </div>
            </div>
            <div>
                <div id="actionBox" class="unitBox">
                </div>
            </div>
            <div>
                <div id="dataBox" class="unitBox">
                </div>
            </div>
        </div>
        <div class="tabsFooter">
            <div class="tabsFooterContent"></div>
        </div>
    </div>
</div>





