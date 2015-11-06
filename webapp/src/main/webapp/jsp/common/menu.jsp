<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<!-- Left side column. contains the logo and sidebar -->
<aside class="main-sidebar">
    <!-- sidebar: style can be found in sidebar.less -->
    <section class="sidebar">
        <!-- Sidebar user panel -->
        <div class="user-panel">
            <div class="pull-left image">
                <img src="/resources/images/logo-128x128.jpg" class="img-circle" alt="User Image"/>
            </div>
            <div class="pull-left info">
                <p>${user.loginName}</p>
                <a href="#"><i class="fa fa-circle text-success"></i> Online</a>
            </div>
        </div>
        <!-- sidebar menu: : style can be found in sidebar.less -->
        <ul class="sidebar-menu">
            <li class="header">导 航</li>
            <c:forEach items="${menuList}" var="menu">
                <li class="treeview" style="white-space: normal;">
                    <a href="#">
                        <i class="fa fa-tasks"></i>
                        <span> ${menu.name} </span>
                        <i class="fa fa-angle-left pull-right"></i>
                    </a>
                    <ul class="treeview-menu">
                        <c:forEach items="${menu.child}" var="subMenu">
                            <li style="white-space: normal;">
                                <a href="#"><i class="fa fa-circle-o text-yellow"></i> ${subMenu.name} <i
                                        class="fa fa-angle-left pull-right"></i></a>
                                <ul class="treeview-menu">
                                    <c:forEach items="${subMenu.child}" var="subSubMenu">
                                        <li style="white-space: normal;"><a class="partial" href="${subSubMenu.path}"><i
                                                class="fa fa-circle-o text-red"></i> ${subSubMenu.name} </a>
                                        </li>
                                    </c:forEach>
                                </ul>
                            </li>
                        </c:forEach>
                    </ul>
                </li>
            </c:forEach>
            <%--<li class="treeview">--%>
            <%--<a href="#">--%>
            <%--<i class="fa fa-wrench"></i>--%>
            <%--<span>自定义报表</span>--%>
            <%--<i class="fa fa-angle-left pull-right"></i>--%>
            <%--</a>--%>
            <%--<ul class="treeview-menu">--%>
            <%--<c:forEach items="${userDefinedReports}" var="userDefinedReport">--%>
            <%--<li><a class="partial" id="userDefined?reportId=${userDefinedReport.id}" href="#"><i--%>
            <%--class="fa fa-circle-o text-red"></i> ${userDefinedReport.name} </a></li>--%>
            <%--</c:forEach>--%>
            <%--</ul>--%>
            <%--</li>--%>
            <%--<li><a class="partial" href="#" id="test"><i class="fa fa-circle-o text-red"></i> <span>test</span></a>--%>
            <%--</li>--%>
        </ul>
    </section>
    <!-- /.sidebar -->
</aside>