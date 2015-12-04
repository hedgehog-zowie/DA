<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <meta charset="UTF-8">
    <title>数据分析平台 | 埋点组配置</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <!-- Bootstrap 3.3.4 -->
    <link href="/resources/plugins/bootstrap/css/bootstrap.css" rel="stylesheet" type="text/css"/>

    <link href="/resources/plugins/select2/select2.css" rel="stylesheet" type="text/css"/>
    <!-- FontAwesome 4.3.0 -->
    <link href="/resources/plugins/font-awesome/css/font-awesome.css" rel="stylesheet" type="text/css"/>
    <!-- uniform -->
    <link href="/resources/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css"/>
    <!-- Theme style -->
    <link href="/resources/css/AdminLTE.css" rel="stylesheet" type="text/css"/>
    <!-- AdminLTE Skins. Choose a skin from the css/skins
         folder instead of downloading all of them to reduce the load. -->
    <link href="/resources/css/skins/_all-skins.css" rel="stylesheet" type="text/css"/>

    <link href="/resources/css/components.css" rel="stylesheet" type="text/css"/>

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
                <i class="fa fa-dashboard"></i> Home > 系统配置 > 来源配置 >
                <small>埋点组配置</small>
            </h1>
        </section>

        <!-- Main content -->
        <section class="content">
            <div class="row">
                <div class="col-md-12">
                    <!-- BEGIN VALIDATION STATES-->
                    <div class="portlet box blue">
                        <div class="portlet-title">
                        </div>
                        <div class="portlet-body form">
                            <!-- BEGIN FORM-->
                            <form:form id="form_buried_group" class="form-horizontal" action="/config/buriedGroup/save"
                                       method="post"
                                       modelAttribute="buriedGroup">
                                <div class="form-body">
                                    <div class="alert alert-danger display-hide">
                                        <button class="close" data-close="alert"></button>
                                        部分配置项校验失败，请检查输入！
                                    </div>
                                    <div class="alert alert-success display-hide">
                                        <button class="close" data-close="alert"></button>
                                        校验通过！
                                    </div>
                                    <form:input type="hidden" id="buried-group-id" name="id" path="id"
                                                value="${buriedGroup.id}"/>

                                    <div class="form-group">
                                        <name class="control-label col-md-3">埋点组名称<span class="required">
										* </span></name>

                                        <div class="col-md-4">
                                            <form:input type="text" name="name" path="name"
                                                        value="${buriedGroup.name}" data-required="1"
                                                        class="form-control"/>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <name class="control-label col-md-3">埋点组设定<span class="required">
										* <br></span>
                                            <span class="required">（请先添加完埋点，最后再检查并拖拽设置顺序）</span></name>
                                        <div class="col-md-4">
                                            <form:select id="selectPointOfGroup"
                                                         class="form-control select2"
                                                         multiple="multiple"
                                                         data-placeholder="请选择埋点，至少选择一个"
                                                         value="${buriedGroup.buriedPoints}" path="buriedPoints">
                                                <form:options items="${buriedPointList}" itemValue="id"/>
                                            </form:select>
                                        </div>
                                    </div>
                                    <div class="form-group" style="display: none">
                                        <name class="control-label col-md-3">埋点选择<span class="required">
										* </span></name>
                                        <div class="col-md-4">
                                            <form:input readonly="true" type="text" class="form-control"
                                                        id="buriedPoints" name="buriedPoints" path="sortedBuriedPoints"
                                                        value="${buriedGroup.sortedBuriedPoints}"/>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <name class="control-label col-md-3">备注</name>

                                        <div class="col-md-4">
                                            <form:input name="desc" path="desc" value="${buriedGroup.desc}"
                                                        type="text"
                                                        class="form-control"/>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-actions">
                                    <div class="row">
                                        <div class="col-md-offset-3 col-md-9">
                                            <button type="submit" class="btn blue">提交</button>
                                            <button id="cancel_form_buried_group" type="button" class="btn default">取消
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </form:form>
                            <!-- END FORM-->
                        </div>
                        <!-- END VALIDATION STATES-->
                    </div>
                </div>
            </div>
        </section>

    </div>

    <jsp:include page="../common/footer.jsp" flush="true"/>

</div>
<!-- ./wrapper -->

<!-- jQuery 2.1.4 -->
<%--<script src="plugins/jQuery/jQuery-2.1.4.min.js"></script>--%>
<script src="/resources/scripts/common/jquery.min.js" type="text/javascript"></script>
<script src="/resources/plugins/jquery-ui/jquery-ui.js" type="text/javascript"></script>
<!-- uniform -->
<script src="/resources/plugins/uniform/jquery.uniform.min.js" type="text/javascript"></script>
<!-- Bootstrap 3.3.2 JS -->
<script src="/resources/plugins/bootstrap/scripts/bootstrap.js" type="text/javascript"></script>
<!-- Select2 -->
<script src="/resources/plugins/select2/select2.full.js" type="text/javascript"></script>
<script src="/resources/plugins/select2/i18n/zh-CN.js" type="text/javascript"></script>
<!-- FastClick -->
<script src="/resources/plugins/fastclick/fastclick.js" type="text/javascript"></script>
<!-- AdminLTE App -->
<script src="/resources/scripts/app.js" type="text/javascript"></script>

<script src="/resources/plugins/jquery-validation/js/jquery.validate.js" type="text/javascript"></script>
<script src="/resources/plugins/input-mask/jquery.inputmask.js" type="text/javascript"></script>
<script src="/resources/plugins/input-mask/jquery.inputmask.extensions.js" type="text/javascript"></script>

<script src="/resources/plugins/sweetalert-master/sweetalert.min.js" type="text/javascript"></script>

<script src="/resources/scripts/common/common.js" type="text/javascript"></script>
<script src="/resources/scripts/config/buried-group.js" type="text/javascript"></script>
<script>
    jQuery(document).ready(function () {
        BuriedGroupFormValidation.init();
    });
</script>
</body>
</html>



