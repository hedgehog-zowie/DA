<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>数据分析平台 | 埋点列表</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <!-- Bootstrap 3.3.4 -->
    <link href="/resources/plugins/bootstrap/css/bootstrap.css" rel="stylesheet" type="text/css"/>
    <!-- FontAwesome 4.3.0 -->
    <link href="/resources/plugins/font-awesome/css/font-awesome.css" rel="stylesheet" type="text/css"/>
    <!-- Theme style -->
    <link href="/resources/css/AdminLTE.css" rel="stylesheet" type="text/css"/>
    <!-- AdminLTE Skins. Choose a skin from the css/skins
         folder instead of downloading all of them to reduce the load. -->
    <link href="/resources/css/skins/_all-skins.css" rel="stylesheet" type="text/css"/>

    <link href="/resources/css/components.css" rel="stylesheet" type="text/css"/>

    <link href="/resources/plugins/select2/select2.css" rel="stylesheet" type="text/css"/>
    <link href="/resources/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.css" rel="stylesheet"
          type="text/css"/>

    <link href="/resources/plugins/sweetalert-master/sweetalert.css" rel="stylesheet" type="text/css"/>
</head>
<body class="skin-blue sidebar-mini">
<div class="wrapper">

    <jsp:include page="../common/header.jsp" flush="true"/>
    <jsp:include page="../common/menu.jsp" flush="true"/>

    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">

        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>
                <i class="fa fa-dashboard"></i> Home > 系统配置 > 页面配置 >
                <small>埋点配置</small>
            </h1>
        </section>

        <!-- Main content -->
        <section class="content">
            <div class="row">
                <div class="col-xs-12">
                    <div class="portlet box grey">
                        <div class="portlet-title">
                            <div class="caption">
                                <i class="fa fa-globe"></i>埋点列表
                            </div>
                        </div>
                        <!-- /.box-header -->
                        <div class="portlet-body">
                            <div class="table-toolbar">
                                <div class="row">
                                    <div class="col-md-6">
                                        <div class="btn-group">
                                            <button id="table_buried_point_new" class="btn green">
                                                添加 <i class="fa fa-plus"></i>
                                            </button>
                                            <button id="table_buried_point_delete" class="btn red">
                                                删除 <i class="fa fa-minus"></i>
                                            </button>
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="btn-group pull-right">
                                            <button id="table_buried_point_export" class="btn yellow">
                                                导出 <i class="fa fa-file"></i>
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <table id="table_buried_point" class="table table-striped table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th class="table-checkbox"><input type="checkbox" class="group-checkable"
                                                                      data-set="#table_buried_point .checkboxes"/></th>
                                    <th>站点编码</th>
                                    <th>站点名称</th>
                                    <th>页面名称</th>
                                    <th>页面位置</th>
                                    <th>埋点编码</th>
                                    <th>添加时间</th>
                                    <th>备 注</th>
                                    <th>操 作</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${buriedPointList}" var="buriedPoint">
                                    <tr class="odd gradeX">
                                        <td><input type="checkbox" class="checkboxes" value="${buriedPoint.id}"/></td>
                                        <td>${buriedPoint.websiteCode}</td>
                                        <td>${buriedPoint.website}</td>
                                        <td>${buriedPoint.pageName}</td>
                                        <td>${buriedPoint.pagePosition}</td>
                                        <td>${buriedPoint.pointFlag}</td>
                                        <td>${buriedPoint.createDate}</td>
                                        <td>${buriedPoint.desc}</td>
                                        <td>
                                            <a class="edit text-blue" id="edit_${buriedPoint.id}" href="javascript:;"><i
                                                    class="fa fa-pencil text-blue"></i> 编辑 </a>
                                            |
                                            <c:if test="${buriedPoint.status == 1}">
                                                <a class="disable text-yellow" id="disable_${buriedPoint.id}" href="javascript:;"><i
                                                        class="fa fa-stop text-yellow"></i> 禁用 </a>
                                            </c:if>
                                            <c:if test="${buriedPoint.status == 0}">
                                                <a class="enable text-green" id="enable_${buriedPoint.id}" href="javascript:;"><i
                                                        class="fa fa-play text-green"></i> 启用 </a>
                                            </c:if>
                                            |
                                            <a class="delete text-red" id="delete_${buriedPoint.id}" href="javascript:;"><i
                                                    class="fa fa-remove text-red"></i> 删除 </a>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                        <!-- /.box-body -->
                    </div>
                    <!-- /.box -->
                </div>
                <!-- /.col -->
            </div>
            <!-- /.row -->
        </section>
        <!-- /.content -->

    </div>

    <jsp:include page="../common/footer.jsp" flush="true"/>

</div>
<!-- ./wrapper -->

<!-- jQuery 2.1.4 -->
<%--<script src="plugins/jQuery/jQuery-2.1.4.min.js"></script>--%>
<script src="/resources/scripts/common/jquery.min.js" type="text/javascript"></script>
<!-- Bootstrap 3.3.2 JS -->
<script src="/resources/plugins/bootstrap/scripts/bootstrap.js" type="text/javascript"></script>
<!-- FastClick -->
<script src="/resources/plugins/fastclick/fastclick.js" type="text/javascript"></script>
<!-- AdminLTE App -->
<script src="/resources/scripts/app.js" type="text/javascript"></script>

<script src="/resources/plugins/select2/select2.js" type="text/javascript"></script>
<script src="/resources/plugins/datatables/media/js/jquery.dataTables.js" type="text/javascript"></script>
<script src="/resources/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.js" type="text/javascript"></script>

<script src="/resources/plugins/sweetalert-master/sweetalert.min.js" type="text/javascript"></script>

<script src="/resources/scripts/common/common.js" type="text/javascript"></script>
<script src="/resources/scripts/config/buried-point.js" type="text/javascript"></script>

<script>
    jQuery(document).ready(function () {
        BuriedPointTable.init();
    });
</script>

</body>
</html>
