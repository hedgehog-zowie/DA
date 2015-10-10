<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<header class="main-header">
    <!-- Logo -->
    <a href="#" class="logo">
        <!-- mini logo for sidebar mini 50x50 pixels -->
        <span class="logo-mini"><b>DATA</b></span>
        <!-- logo for regular state and mobile devices -->
        <span class="logo-lg"><b>数据分析平台</b></span>
    </a>
    <!-- Header Navbar: style can be found in header.less -->
    <nav class="navbar navbar-static-top" role="navigation">
        <!-- Sidebar toggle button-->
        <a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button">
            <span class="sr-only">Toggle navigation</span>
        </a>

        <div class="navbar-custom-menu">
            <ul class="nav navbar-nav">
                <!-- User Account: style can be found in dropdown.less -->
                <li class="user user-menu">
                    <a href="#">
                        <img src="/resources/images/logo-128x128.jpg" class="user-image"
                             alt="User Image"/>
                        <span class="hidden-xs">${user.loginName}</span>
                    </a>
                </li>
                <li class="user user-menu">
                    <a href="/logout">退出</a>
                </li>
            </ul>
        </div>
    </nav>
</header>