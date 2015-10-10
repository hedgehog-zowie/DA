<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <meta charset="UTF-8">
    <title>数据分析平台 | 活动-渠道分析</title>
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
                <i class="fa fa-dashboard"></i> Home > 运营分析 > 活动分析 >
                <small>活动-渠道分析</small>
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
                    <form:form id="form_activity_channel" class="form-horizontal" action="/activity/channel/query"
                               method="post"
                               modelAttribute="queryParam">
                        <div class="form-body">
                            <div class="row">
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label class="control-label col-md-3">渠道类型</label>

                                        <div class="col-md-6">
                                            <form:select class="form-control" path="channel.channelType"
                                                         id="channelType"
                                                         value="${queryParam.channel.channelType}">
                                                <form:option value="" label="--全部--"/>
                                                <form:options items="${channelTypes}" itemLabel="name" itemValue="id"/>
                                            </form:select>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label class="control-label col-md-3">渠道AD</label>

                                        <div class="col-md-6">
                                            <form:input type="text" class="form-control" id="channelCode"
                                                        path="channel.code"
                                                        value="${queryParam.channel.code}"
                                                        placeholder="输入渠道AD"/>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label class="control-label col-md-3">统计日期:</label>

                                        <div class="col-md-7">
                                            <form:input type="text" class="form-control" id="daterangepicker"
                                                        value="${queryParam.dateRangeString}" path="dateRangeString"/>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-1">
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
                <ul class="nav nav-tabs pull-right" id="activity-channel-tab">
                    <li>
                        <button id="activity-channel-table-export" class="btn green">
                            导出 <i class="fa fa-file"></i>
                        </button>
                    </li>
                    <li><a href="#activity-channel-chart-tab" data-toggle="tab">图</a></li>
                    <li class="active"><a href="#activity-channel-table-tab" data-toggle="tab">表</a></li>
                    <li class="pull-left header"><i class="fa fa-inbox"></i> 活动-渠道分析</li>
                </ul>
                <div class="tab-content no-padding">
                    <div class="portlet-body tab-pane active" id="activity-channel-table-tab"
                         style="position: relative;">
                        <table id="activity-channel-table" class="table table-striped table-bordered table-hover">
                            <thead>
                            <tr>
                                <th>日期</th>
                                <th>推广渠道</th>
                                <th>推广链接</th>
                                <th>PV</th>
                                <th>UV</th>
                                <th>VV</th>
                                <th>跳出率</th>
                                <th>人均浏览页面</th>
                                <th>平均访问深度</th>
                                <th>平均访问时间</th>
                                <th>注册页UV</th>
                                <th>注册成功数</th>
                                <th>注册转化率</th>
                                <th>注册成功率</th>
                                <th>下单总数量</th>
                                <th>下单总金额</th>
                                <th>下单转化率</th>
                                <th>已支付订单数</th>
                                <th>已支付订单比</th>
                                <th>已支付订单金额</th>
                                <th>客单价</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${resultList}" var="result">
                                <tr class="odd gradeX">
                                    <td><fmt:formatDate value="${result.time}" pattern="yyyy-MM-dd"/></td>
                                    <td>${result.channelName}</td>
                                    <td>${result.channelUrl}</td>
                                    <td>${result.pv}</td>
                                    <td>${result.uv}</td>
                                    <td>${result.vv}</td>
                                    <td><fmt:formatNumber value="${result.jumpRate}" type="percent"
                                                          maxFractionDigits="2"
                                                          minFractionDigits="2"/></td>
                                    <td><fmt:formatNumber value="${result.avrPages}" maxFractionDigits="2"
                                                          minFractionDigits="2"/></td>
                                    <td><fmt:formatNumber value="${result.avrDeeps}" maxFractionDigits="2"
                                                          minFractionDigits="2"/></td>
                                    <td><fmt:formatNumber value="${result.avrTimes}" maxFractionDigits="2"
                                                          minFractionDigits="2"/>s
                                    </td>
                                    <td>${result.ruv}</td>
                                    <td>${result.rsNum}</td>
                                    <td><fmt:formatNumber value="${result.rRate}" type="percent" maxFractionDigits="2"
                                                          minFractionDigits="2"/></td>
                                    <td><fmt:formatNumber value="${result.rsRate}" type="percent" maxFractionDigits="2"
                                                          minFractionDigits="2"/></td>
                                    <td>${result.orderNum}</td>
                                    <td><fmt:formatNumber value="${result.orderAmount}" maxFractionDigits="2"
                                                          minFractionDigits="2"/></td>
                                    <td><fmt:formatNumber value="${result.orderTrans}" type="percent"
                                                          maxFractionDigits="2"
                                                          minFractionDigits="2"/></td>
                                    <td>${result.paidOrderNum}</td>
                                    <td><fmt:formatNumber value="${result.payRate}" type="percent" maxFractionDigits="2"
                                                          minFractionDigits="2"/></td>
                                    <td>${result.paidOrderAmount}</td>
                                    <td><fmt:formatNumber value="${result.avgAmount}" maxFractionDigits="2"
                                                          minFractionDigits="2"/></td>
                                </tr>
                            </c:forEach>
                            </tbody>
                            <%--当前页合计--%>
                            <%--<tfoot>--%>
                            <%--<tr>--%>
                            <%--<td>当前页合计</td>--%>
                            <%--<td></td>--%>
                            <%--<td></td>--%>
                            <%--<td id="pvsum"></td>--%>
                            <%--<td id="uvsum"></td>--%>
                            <%--<td id="vvsum"></td>--%>
                            <%--</tr>--%>
                            <%--</tfoot>--%>
                        </table>
                    </div>
                    <div class="chart tab-pane" id="activity-channel-chart-tab"
                         style="position: relative; height: 516px">
                        <div id="activity-channel-chart-label" class="col-md-2 form-group"
                             style="height: 516px; background-color: whitesmoke">
                            <div><label><i class="fa fa-hand-o-down"></i> 请选择以下一种数据生成饼图</label></div>
                            <div><label><input class="data-type" name="data-type" type="radio" value="pv"> pv</label>
                            </div>
                            <div><label><input class="data-type" name="data-type" type="radio" value="uv"> uv</label>
                            </div>
                            <div><label><input class="data-type" name="data-type" type="radio" value="vv"> vv</label>
                            </div>
                            <div><label><input class="data-type" name="data-type" type="radio" value="on"> 下单总数量</label>
                            </div>
                            <%--<div><label><input class="data-type" name="data-type" type="radio" value="oa"> 下单总金额</label></div>--%>
                            <div><label><input class="data-type" name="data-type" type="radio" value="pn">
                                已支付订单数量</label></div>
                            <%--<div><label><input class="data-type" name="data-type" type="radio" value="pa"> 已支付订单总金额</label></div>--%>
                        </div>
                        <div id="activity-channel-chart" class="col-md-10 form-group" style="height: 516px"/>
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

<!-- dataTable -->
<script src="/resources/plugins/select2/select2.js" type="text/javascript"></script>
<script src="/resources/plugins/datatables/media/js/jquery.dataTables.js" type="text/javascript"></script>
<script src="/resources/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.js" type="text/javascript"></script>

<!-- data range picker -->
<script src="/resources/plugins/daterangepicker/moment.js" type="text/javascript"></script>
<script src="/resources/plugins/daterangepicker/daterangepicker.js" type="text/javascript"></script>

<script src="/resources/scripts/active/active-channel.js" type="text/javascript"></script>
<!-- ECharts单文件引入 -->
<script src="/resources/plugins/echarts/echarts.js" type="text/javascript"></script>
<script>
    jQuery(document).ready(function () {
        ActiveChannelTable.init();
    });
</script>
</body>
</html>


