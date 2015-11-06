<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <meta charset="UTF-8">
    <title>数据分析平台 | 收款发货发票金额核对明细报表</title>
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

    <link href="/resources/plugins/daterangepicker/daterangepicker.css" rel="stylesheet" type="text/css"/>

    <link href="/resources/plugins/echarts/css/carousel.css" rel="stylesheet" type="text/css">
    <link href="/resources/plugins/echarts/css/echartsHome.css" rel="stylesheet" type="text/css">
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
                <i class="fa fa-dashboard"></i> Home > 运营分析 > 财务报表 >
                <small>收款发货发票金额核对明细报表</small>
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
                    <form:form id="form_financial_pay_amount_check_details" class="form-horizontal"
                               action="/financial/payAmountCheckDetails/query"
                               method="post"
                               modelAttribute="queryParam">
                        <div class="form-body">
                            <div class="row">
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label class="control-label col-md-3">统计日期:</label>

                                        <div class="col-md-7">
                                            <form:input type="text" class="form-control" id="daterangepicker"
                                                        value="${queryParam.dateRangeString}" path="dateRangeString"/>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-0 pull-right">
                                    <button id="query-button" type="submit" class="btn green">查询</button>
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
                <ul class="nav nav-tabs pull-right" id="financial-pay-amount-check-details-tab">
                    <li>
                        <button id="financial-pay-amount-check-details-table-export" class="btn green">
                            导出 <i class="fa fa-file"></i>
                        </button>
                    </li>
                    <li class="pull-left header"><i class="fa fa-inbox"></i> 收款发货发票金额核对明细</li>
                </ul>
                <div class="tab-content no-padding">
                    <div class="portlet-body tab-pane active" id="financial-pay-amount-check-details-table-tab"
                         style="position: relative;">
                        <table id="financial-pay-amount-check-details-table"
                               class="table table-striped table-bordered table-hover">
                            <thead>
                            <tr>
                                <th>出入库日期</th>
                                <th>子订单号</th>
                                <th>主订单号</th>
                                <th>外部订单号</th>
                                <th>销售渠道/类型</th>
                                <th>商品金额</th>
                                <th>优惠金额</th>
                                <th>代收快递费</th>
                                <th>订单金额</th>
                                <th>支付流水号</th>
                                <th>收款金额</th>
                                <th>收款方式</th>
                                <th>发票金额</th>
                                <th>退货扣款金额</th>
                                <th>退货扣款原因</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${resultList}" var="result">
                                <tr class="odd gradeX">
                                    <td><fmt:formatDate value="${result.stockTime}" pattern="yyyy/MM/dd"/></td>
                                    <td>${result.orderCode}</td>
                                    <td>${result.parentOrderId}</td>
                                    <td>${result.outerOrderCode}</td>
                                    <td>${result.orderSource}</td>
                                    <td>${result.goodsAmount}</td>
                                    <td>${result.bonus}</td>
                                    <td>${result.shippingFee}</td>
                                    <td>${result.orderAmount}</td>
                                    <td>${result.paySerialNo}</td>
                                    <td>${result.paidAmount}</td>
                                    <td>${result.payName}</td>
                                    <td>${result.invoiceAmount}</td>
                                    <td>${result.deductAmount}</td>
                                    <td>${result.deductReason}</td>
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
<!-- FastClick -->
<script src="/resources/plugins/fastclick/fastclick.js" type="text/javascript"></script>
<!-- AdminLTE App -->
<script src="/resources/scripts/app.js" type="text/javascript"></script>

<!-- data range picker -->
<script src="/resources/plugins/daterangepicker/moment.js" type="text/javascript"></script>
<script src="/resources/plugins/daterangepicker/daterangepicker.js" type="text/javascript"></script>

<!-- dataTable -->
<script src="/resources/plugins/select2/select2.js" type="text/javascript"></script>
<script src="/resources/plugins/datatables/media/js/jquery.dataTables.js" type="text/javascript"></script>
<script src="/resources/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.js" type="text/javascript"></script>

<script src="/resources/scripts/financial/pay-amount-check-details.js" type="text/javascript"></script>
<script>
    jQuery(document).ready(function () {
        PayAmountCheckDetailsOfFinancial.init();
    });
</script>
</body>
</html>
