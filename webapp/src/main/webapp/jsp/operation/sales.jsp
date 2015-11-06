<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <meta charset="UTF-8">
    <title>数据分析平台 | 商品销售报表</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <!-- Bootstrap 3.3.4 -->
    <link href="/resources/plugins/bootstrap/css/bootstrap.css" rel="stylesheet" type="text/css"/>
    <!-- FontAwesome 4.3.0 -->
    <link href="/resources/plugins/font-awesome/css/font-awesome.css" rel="stylesheet" type="text/css"/>

    <link href="/resources/css/components.css" rel="stylesheet" type="text/css"/>

    <link href="/resources/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.css" rel="stylesheet"
          type="text/css"/>

    <link href="/resources/plugins/daterangepicker/daterangepicker.css" rel="stylesheet" type="text/css"/>

    <link href="/resources/plugins/select2/select2.min.css" rel="stylesheet" type="text/css"/>

    <link href="/resources/plugins/echarts/css/carousel.css" rel="stylesheet" type="text/css">
    <link href="/resources/plugins/echarts/css/echartsHome.css" rel="stylesheet" type="text/css">

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

        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>
                <i class="fa fa-dashboard"></i> Home > 运营分析 > 运营报表 >
                <small>商品销售报表</small>
            </h1>
        </section>

        <!-- Main content -->
        <section class="content">

            <div class="portlet box grey">
                <div class="portlet-title">
                    <div class="caption">
                        <i class="fa fa-bookmark"></i> 查询条件
                    </div>
                </div>
                <div class="portlet-body form">
                    <input hidden id="orderSourceStr" value="${queryParam.orderSourceStr}"/>
                    <input hidden id="type" value="${queryParam.type}"/>
                    <input hidden id="model" value="${queryParam.model}"/>
                    <form:form id="form_operation_sales" class="form-horizontal"
                               action="/operation/sales/query"
                               method="post"
                               modelAttribute="queryParam">
                        <div class="form-body">
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="form-group">
                                        <label class="control-label col-md-1 pull-left">订单来源：</label>

                                        <div class="col-md-11">
                                            <form:select id="select2OfOrderSource" class="form-control select2"
                                                         multiple="multiple"
                                                         data-placeholder="请选择订单来源"
                                                         path="orderSourceStr" value="${queryParam.orderSourceStr}">
                                                <form:options items="${orderSources}" itemLabel="sourceName"
                                                              itemValue="sourceCode"/>
                                            </form:select>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label class="control-label col-md-4">商品类型：</label>

                                        <div class="col-md-8">
                                            <form:select id="typeSelect" class="form-control select2" path="type"
                                                         data-placeholder="请选择商品类型" value="${queryParam.type}">
                                                <form:options items="${catsNoPhone}" itemLabel="name"
                                                              itemValue="id"/>
                                                <form:options items="${waresOfPhone}" itemLabel="name"
                                                              itemValue="id"/>
                                            </form:select>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label class="control-label col-md-4">规格型号：</label>

                                        <div class="col-md-8">
                                            <form:select id="modelSelect" class="form-control select2" path="model"
                                                         data-placeholder="请选择规格型号" value="${queryParam.model}">
                                                <form:options items="${waresNoPhone}" itemLabel="name"
                                                              itemValue="id"/>
                                                <form:options items="${skuOfPhone}" itemLabel="name"
                                                              itemValue="id"/>
                                            </form:select>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label class="control-label col-md-2">SKU编码：</label>

                                        <div class="col-md-10">
                                            <form:input type="text" class="form-control" id="skuStr"
                                                        path="skuStr"
                                                        value="${queryParam.skuStr}"
                                                        placeholder="多个SKU编码以英文逗号分隔（若输入此参数，则‘商品类型’与‘规格型号’无效）"/>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label class="control-label col-md-4">统计日期：</label>

                                        <div class="col-md-8">
                                            <form:input type="text" class="form-control" id="daterangepicker"
                                                        value="${queryParam.dateRangeString}" path="dateRangeString"/>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label class="control-label col-md-4">统计方式：</label>

                                        <div class="col-md-8">
                                            <form:select id="dateStyle" class="form-control" path="dateStyle"
                                                         items="${dateStyles}" itemLabel="dateStyleName"
                                                         itemValue="dateStyleStr"
                                                         value="${queryParam.dateStyle}">
                                            </form:select>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-0 pull-right">
                                    <button id="query-button" type="submit" class="btn green">查询</button>
                                    <button id="operation-sales-table-export" class="btn red-flamingo">
                                        导出 <i class="fa fa-file"></i>
                                    </button>
                                </div>
                            </div>
                        </div>
                    </form:form>
                </div>
            </div>
            <!-- /.form group -->

            <!-- Custom tabs (Charts with tabs)-->
            <div class="nav-tabs-custom portlet box grey">

                <!-- Tabs within a box -->
                <ul class="nav nav-tabs pull-right" id="operation-sales-tab">
                    <li class="pull-left header"><i class="fa fa-inbox"></i> 商品销售</li>
                </ul>
                <div class="tab-content no-padding">
                    <div class="portlet-body tab-pane active" id="operation-sales-table-tab"
                         style="position: relative;">
                        <table id="operation-sales-table"
                               class="table table-striped table-bordered table-hover">
                            <thead>
                            <tr>
                                <th>时间区间</th>
                                <th>支付时间</th>
                                <th>销售渠道</th>
                                <th>规格型号</th>
                                <th>商品</th>
                                <th>SKU</th>
                                <th>数量</th>
                                <th>支付金额</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${resultList}" var="result">
                                <tr class="odd gradeX">
                                    <td>${result.range}</td>
                                    <td>${result.payTime}</td>
                                    <td>${result.orderSource}</td>
                                    <td>${result.goodsName}</td>
                                    <td>${result.wareName}</td>
                                    <td>${result.sku}</td>
                                    <td>${result.num}</td>
                                    <td>${result.salePrice}</td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>

                </div>
            </div>
            <!-- /.nav-tabs-custom -->

        </section>

    </div>

    <jsp:include page="../common/footer.jsp" flush="true"/>

</div>
<!-- ./wrapper -->

<!-- jQuery 2.1.4 -->
<%--<script src="plugins/jQuery/jQuery-2.1.4.min.js"></script>--%>
<script src="/resources/scripts/common/jquery.min.js" type="text/javascript"></script>
<!-- Bootstrap 3.3.2 JS -->
<script src="/resources/plugins/bootstrap/scripts/bootstrap.js" type="text/javascript"></script>
<!-- Select2 -->
<script src="/resources/plugins/select2/select2.full.min.js" type="text/javascript"></script>
<!-- FastClick -->
<script src="/resources/plugins/fastclick/fastclick.js" type="text/javascript"></script>
<!-- AdminLTE App -->
<script src="/resources/scripts/app.js" type="text/javascript"></script>

<!-- data range picker -->
<script src="/resources/plugins/daterangepicker/moment.js" type="text/javascript"></script>
<script src="/resources/plugins/daterangepicker/daterangepicker.js" type="text/javascript"></script>

<!-- dataTable -->
<script src="/resources/plugins/datatables/media/js/jquery.dataTables.js" type="text/javascript"></script>
<script src="/resources/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.js" type="text/javascript"></script>

<script src="/resources/scripts/operation/sales.js" type="text/javascript"></script>
<script>
    jQuery(document).ready(function () {
        SalesOfOperation.init();
    });
</script>
</body>
</html>
