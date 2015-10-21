<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>数据分析平台</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <!-- Bootstrap 3.3.4 -->
    <link href="/resources/plugins/bootstrap/css/bootstrap.css" rel="stylesheet" type="text/css"/>
    <!-- FontAwesome 4.3.0 -->
    <link href="/resources/plugins/font-awesome/css/font-awesome.css" rel="stylesheet" type="text/css"/>
    <!-- uniform -->
    <%--<link href="/resources/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css"/>--%>
    <!-- Theme style -->
    <link href="/resources/css/AdminLTE.css" rel="stylesheet" type="text/css"/>
    <!-- AdminLTE Skins. Choose a skin from the css/skins
         folder instead of downloading all of them to reduce the load. -->
    <link href="/resources/css/skins/_all-skins.css" rel="stylesheet" type="text/css"/>

</head>
<body class="skin-blue sidebar-mini">
<div class="wrapper">

    <jsp:include page="../common/header.jsp" flush="true"/>
    <jsp:include page="../common/menu.jsp" flush="true"/>

    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">

        <!-- Main content -->
        <section class="content">
            <div class="error-page">
                <h2 class="headline text-yellow"> 404</h2>
                <!-- error-content -->
                <div class="error-content">
                    <h3><i class="fa fa-warning text-yellow"></i> 靠！出错了！</h3>
                    <h3>休息一下吧！</h3>
                    <h3>请联系开发那个小子，让他看看是什么鬼问题！</h3>
                </div>
                <!-- /.error-content -->
            </div>
            <!-- /.error-page -->
        </section>
        <!-- /.content -->
    </div>

    <jsp:include page="../common/footer.jsp" flush="true"/>

</div>
<!-- ./wrapper -->

<!-- jQuery 2.1.4 -->
<%--<script src="plugins/jQuery/jQuery-2.1.4.min.js"></script>--%>
<script src="/resources/scripts/common/jquery.min.js" type="text/javascript"></script>
<!-- uniform -->
<%--<script src="/resources/plugins/uniform/jquery.uniform.min.js" type="text/javascript"></script>--%>
<!-- Bootstrap 3.3.2 JS -->
<script src="/resources/plugins/bootstrap/scripts/bootstrap.js" type="text/javascript"></script>
<!-- FastClick -->
<script src="/resources/plugins/fastclick/fastclick.js" type="text/javascript"></script>
<!-- AdminLTE App -->
<script src="/resources/scripts/app.js" type="text/javascript"></script>

</body>
</html>