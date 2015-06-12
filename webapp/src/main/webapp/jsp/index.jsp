<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>数据分析平台</title>

    <link href="resources/dwz/themes/default/style.css" rel="stylesheet" type="text/css" media="screen"/>
    <link href="resources/dwz/themes/css/core.css" rel="stylesheet" type="text/css" media="screen"/>
    <link href="resources/dwz/themes/css/print.css" rel="stylesheet" type="text/css" media="print"/>
    <link href="resources/dwz/uploadify/css/uploadify.css" rel="stylesheet" type="text/css" media="screen"/>
    <!--[if IE]>
    <link href="resources/dwz/themes/css/ieHack.css" rel="stylesheet" type="text/css" media="screen"/>
    <![endif]-->

    <!--[if lte IE 9]>
    <script src="resources/dwz/js/speedup.js" type="text/javascript"></script>
    <![endif]-->

    <script src="resources/dwz/js/jquery-1.7.2.js" type="text/javascript"></script>
    <script src="resources/dwz/js/jquery.cookie.js" type="text/javascript"></script>
    <script src="resources/dwz/js/jquery.validate.js" type="text/javascript"></script>
    <script src="resources/dwz/js/jquery.bgiframe.js" type="text/javascript"></script>
    <script src="resources/dwz/xheditor/xheditor-1.2.1.min.js" type="text/javascript"></script>
    <script src="resources/dwz/xheditor/xheditor_lang/zh-cn.js" type="text/javascript"></script>
    <script src="resources/dwz/uploadify/scripts/jquery.uploadify.js" type="text/javascript"></script>

    <!-- svg图表  supports Firefox 3.0+, Safari 3.0+, Chrome 5.0+, Opera 9.5+ and Internet Explorer 6.0+ -->
    <%--<script type="text/javascript" src="resources/dwz/chart/raphael.js"></script>--%>
    <%--<script type="text/javascript" src="resources/dwz/chart/g.raphael.js"></script>--%>
    <%--<script type="text/javascript" src="resources/dwz/chart/g.bar.js"></script>--%>
    <%--<script type="text/javascript" src="resources/dwz/chart/g.line.js"></script>--%>
    <%--<script type="text/javascript" src="resources/dwz/chart/g.pie.js"></script>--%>
    <%--<script type="text/javascript" src="resources/dwz/chart/g.dot.js"></script>--%>

    <script src="resources/dwz/js/dwz.core.js" type="text/javascript"></script>
    <script src="resources/dwz/js/dwz.util.date.js" type="text/javascript"></script>
    <script src="resources/dwz/js/dwz.validate.method.js" type="text/javascript"></script>
    <script src="resources/dwz/js/dwz.barDrag.js" type="text/javascript"></script>
    <script src="resources/dwz/js/dwz.drag.js" type="text/javascript"></script>
    <script src="resources/dwz/js/dwz.tree.js" type="text/javascript"></script>
    <script src="resources/dwz/js/dwz.accordion.js" type="text/javascript"></script>
    <script src="resources/dwz/js/dwz.ui.js" type="text/javascript"></script>
    <script src="resources/dwz/js/dwz.theme.js" type="text/javascript"></script>
    <script src="resources/dwz/js/dwz.switchEnv.js" type="text/javascript"></script>
    <script src="resources/dwz/js/dwz.alertMsg.js" type="text/javascript"></script>
    <script src="resources/dwz/js/dwz.contextmenu.js" type="text/javascript"></script>
    <script src="resources/dwz/js/dwz.navTab.js" type="text/javascript"></script>
    <script src="resources/dwz/js/dwz.tab.js" type="text/javascript"></script>
    <script src="resources/dwz/js/dwz.resize.js" type="text/javascript"></script>
    <script src="resources/dwz/js/dwz.dialog.js" type="text/javascript"></script>
    <script src="resources/dwz/js/dwz.dialogDrag.js" type="text/javascript"></script>
    <script src="resources/dwz/js/dwz.sortDrag.js" type="text/javascript"></script>
    <script src="resources/dwz/js/dwz.cssTable.js" type="text/javascript"></script>
    <script src="resources/dwz/js/dwz.stable.js" type="text/javascript"></script>
    <script src="resources/dwz/js/dwz.taskBar.js" type="text/javascript"></script>
    <script src="resources/dwz/js/dwz.ajax.js" type="text/javascript"></script>
    <script src="resources/dwz/js/dwz.pagination.js" type="text/javascript"></script>
    <script src="resources/dwz/js/dwz.database.js" type="text/javascript"></script>
    <script src="resources/dwz/js/dwz.datepicker.js" type="text/javascript"></script>
    <script src="resources/dwz/js/dwz.effects.js" type="text/javascript"></script>
    <script src="resources/dwz/js/dwz.panel.js" type="text/javascript"></script>
    <script src="resources/dwz/js/dwz.checkbox.js" type="text/javascript"></script>
    <script src="resources/dwz/js/dwz.history.js" type="text/javascript"></script>
    <script src="resources/dwz/js/dwz.combox.js" type="text/javascript"></script>
    <script src="resources/dwz/js/dwz.print.js" type="text/javascript"></script>

    <!-- 可以用dwz.min.js替换前面全部dwz.*.js (注意：替换是下面dwz.regional.zh.js还需要引入)
    <script src="bin/dwz.min.js" type="text/javascript"></script>
    -->
    <script src="resources/dwz/js/dwz.regional.zh.js" type="text/javascript"></script>

    <script type="text/javascript">
        $(function () {
            // DWZ
            DWZ.init("resources/dwz/dwz.frag.xml", {
                // loginUrl: "login.html", loginTitle: "登录",	// 弹出登录对话框
                loginUrl: "login",	// 跳到登录页面
                statusCode: {ok: 200, error: 300, timeout: 301}, //【可选】
                pageInfo: {pageNum: "pageNum", numPerPage: "numPerPage", orderField: "orderField", orderDirection: "orderDirection"}, //【可选】
                keys: {statusCode: "statusCode", message: "message"}, //【可选】
                ui: {hideMode: 'offsets'}, //【可选】hideMode:navTab组件切换的隐藏方式，支持的值有’display’，’offsets’负数偏移位置的值，默认值为’display’
                debug: false,	// 调试模式 【true|false】
                callback: function () {
                    initEnv();
                    $("#themeList").theme({themeBase: "themes"}); // themeBase 相对于index页面的主题base路径
                }
            });
            $("#testbutton").click(function () {
                alert("test button");
            });
        });
    </script>

    <!-- ECharts单文件引入 -->
    <script src="resources/echarts/source/echarts.js"></script>

</head>

<body scroll="no">
<div id="layout">
    <div id="header">
        <div class="headerNav">
            <a href="#">logo</a>
            <ul class="nav">
                <li><a href="changepwd.html" target="dialog" width="600">设置</a></li>
                <li><a href="logout">退出</a></li>
            </ul>
            <ul class="themeList" id="themeList">
                <li theme="default">
                    <div class="selected">蓝色</div>
                </li>
                <li theme="green">
                    <div>绿色</div>
                </li>
                <!--<li theme="red"><div>红色</div></li>-->
                <li theme="purple">
                    <div>紫色</div>
                </li>
                <li theme="silver">
                    <div>银色</div>
                </li>
                <li theme="azure">
                    <div>天蓝</div>
                </li>
            </ul>
        </div>
        <!-- navMenu -->
    </div>

    <div id="leftside">
        <div id="sidebar_s">
            <div class="collapse">
                <div class="toggleCollapse">
                    <div></div>
                </div>
            </div>
        </div>
        <div id="sidebar">
            <div class="toggleCollapse"><h2>数据分析平台</h2>
                <div>收缩</div>
            </div>
            <div class="accordion" fillSpace="sidebar">
                <c:forEach items="${menuList}" var="menu">
                    <div class="accordionHeader">
                        <h2><span></span>${menu.name}</h2>
                    </div>
                    <div class="accordionContent">
                        <c:forEach items="${menu.child}" var="subMenu">
                            <ul class="tree treeFolder">
                                <li>
                                    <a href="javascript:void(0);">${subMenu.name}</a>
                                    <c:forEach items="${subMenu.child}" var="subSubMenu">
                                        <ul>
                                            <li>
                                                <a href="${subSubMenu.path}" target="navTab"
                                                   rel="${subSubMenu.path}">${subSubMenu.name}</a>
                                            </li>
                                        </ul>
                                    </c:forEach>
                                </li>
                            </ul>
                        </c:forEach>
                    </div>
                </c:forEach>
                <div class="accordionHeader">
                    <h2><span></span>用户自定义报表</h2>
                </div>
                <div class="accordionContent">
                    <ul class="tree treeFolder">
                        <li>
                            <a href="javascript:void(0);" rel="userDefined">用户自定义报表</a>
                            <c:forEach items="${userDefinedReports}" var="userDefinedReport">
                                <ul>
                                    <li>
                                        <a href="userDefined?reportId=${userDefinedReport.id}" target="navTab"
                                           rel="userDefined-${userDefinedReport.id}">${userDefinedReport.name}</a>
                                    </li>
                                </ul>
                            </c:forEach>
                            <%--<ul>--%>
                                <%--<li>--%>
                                    <%--<a href="test" target="navTab" rel="test1">test1</a>--%>
                                <%--</li>--%>
                            <%--</ul>--%>
                            <%--<ul>--%>
                                <%--<li>--%>
                                    <%--<a href="userDefined?reportId=1" target="navTab" rel="userDefined1">用户自定义报表1</a>--%>
                                <%--</li>--%>
                            <%--</ul>--%>
                            <%--<ul>--%>
                                <%--<li>--%>
                                    <%--<a href="userDefined?reportId=1" target="navTab" rel="userDefined2">用户自定义报表2</a>--%>
                                <%--</li>--%>
                            <%--</ul>--%>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </div>

    <div id="container">
        <div id="navTab" class="tabsPage">
            <div class="tabsPageHeader">
                <div class="tabsPageHeaderContent"><!-- 显示左右控制时添加 class="tabsPageHeaderMargin" -->
                    <ul class="navTab-tab">
                        <li tabid="main" class="main"><a href="javascript:;"><span><span
                                class="home_icon">欢迎</span></span></a></li>
                    </ul>
                </div>
                <div class="tabsLeft">left</div>
                <!-- 禁用只需要添加一个样式 class="tabsLeft tabsLeftDisabled" -->
                <div class="tabsRight">right</div>
                <!-- 禁用只需要添加一个样式 class="tabsRight tabsRightDisabled" -->
                <div class="tabsMore">more</div>
            </div>
            <ul class="tabsMoreList">
                <li><a href="javascript:;">欢迎</a></li>
            </ul>
            <div class="navTab-panel tabsPageContent layoutBox">
                <div class="page unitBox">
                    <img src="resources/images/iuni_admin_main.jpg"/>
                </div>
            </div>
        </div>
    </div>

</div>
<div id="footer">
    <a href="#">IUNI 数据分析平台</a>
</div>
</body>
</html>
