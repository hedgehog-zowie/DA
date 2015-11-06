var SalesOfOperation = function () {

    var initSku = function() {
        var skuStr = $("#skuStr").val();
        if (skuStr != null && skuStr != undefined && skuStr.trim() != "") {
            $("#typeSelect").attr("disabled", "disabled");
            $("#modelSelect").attr("disabled", "disabled");
        }
        $("#skuStr").on('input', function () {
            if ($(this).val() != null && $(this).val() != undefined && $(this).val().trim() != "") {
                $("#typeSelect").attr("disabled", "disabled");
                $("#modelSelect").attr("disabled", "disabled");
            } else {
                $("#typeSelect").removeAttr("disabled");
                $("#modelSelect").removeAttr("disabled");
            }
        });
    }

    var initSelect = function () {
        var orderSourceStr = $("#orderSourceStr").val();
        var orderSources = orderSourceStr.split(",");
        $("#select2OfOrderSource").select2();
        $("#select2OfOrderSource").select2("val", orderSources);

        function BindSelect(selectId, url1, url2, t) {
            var select = $('#' + selectId);

            select.empty();//清空下拉框
            // select.append("<option value=''>全选</option>");
            // 绑定Ajax的内容
            $.ajaxSettings.async = false;
            $.getJSON(url1, function (data) {
                $.each(data, function (i, item) {
                    select.append("<option value='" + item.id + "'>" + item.name + "</option>");
                });
            });
            $.getJSON(url2, function (data) {
                $.each(data, function (i, item) {
                    select.append("<option value='" + item.id + "'>" + item.name + "</option>");
                });
            });

            //设置Select2的处理
            select.select2({
                allowClear: true,
            });
            // 赋值
            select.select2("val", t);
        }

        var type = $("#type").val();
        BindSelect("typeSelect", "/operation/sales/selectWareOfPhone", "/operation/sales/selectCategory", type);
        var model = $("#model").val();
        BindSelect("modelSelect", "/operation/sales/selectWare?catId=" + type, "/operation/sales/selectSku?wareId=" + type, model);
        $("#typeSelect").on("change", function (e) {
            var type = $(this).val();
            var model = $("#model").val();
            BindSelect("modelSelect", "/operation/sales/selectWare?catId=" + type, "/operation/sales/selectSku?wareId=" + type, model);
        });

    }

    var initTable = function () {

        var table = $('#operation-sales-table');

        // begin table
        table.dataTable({

            // Internationalisation. For more info refer to http://datatables.net/manual/i18n
            "language": {
                "aria": {
                    "sortAscending": ": activate to sort column ascending",
                    "sortDescending": ": activate to sort column descending"
                },
                "emptyTable": "无数据",
                "info": "显示 _START_ 到 _END_ 条记录，共 _TOTAL_ 条记录",
                "infoEmpty": "未找到相应记录",
                "infoFiltered": "(从 _MAX_ 条记录中过滤)",
                "lengthMenu": "每页显示： _MENU_ ",
                "search": "查找：",
                "zeroRecords": "无匹配的记录",
                "paginate": {
                    "last": "末页",
                    "first": "首页",
                    "previous": "上页",
                    "next": "下页"
                }
            },

            "bStateSave": true, // save datatable state(pagination, sort, etc) in cookie.

            "lengthMenu": [
                [5, 10, 20, -1],
                [5, 10, 20, "全部"] // change per page values here
            ],
            // set the initial value
            "pageLength": 10,
            "pagingType": "bootstrap_full_number",
            "order": [
                [0, 'asc']
            ]
        });

        var tableWrapper = jQuery('#operation-sales-table_wrapper');

        tableWrapper.find('.dataTables_length select').addClass("form-control input-xsmall input-inline");

        $('#daterangepicker').daterangepicker({
            dateLimit: {days: 30},
            opens: 'right',
            autoApply: true,
            locale: {
                format: 'YYYY/MM/DD',
                separator: ' - ',
                applyLabel: '确定',
                cancelLabel: '取消',
                customRangeLabel: '自定义',
                daysOfWeek: ['日', '一', '二', '三', '四', '五', '六'],
                monthNames: ['一月', '二月', '三月', '四月', '五月', '六月',
                    '七月', '八月', '九月', '十月', '十一月', '十二月']
            }
        }, function (start, end, label) {//格式化日期显示框
            $('#daterangepicker span').html(start.format('YYYY/MM/DD') + ' - ' + end.format('YYYY/MM/DD'));
        });

        $('#operation-sales-table-export').click(function (e) {
            e.preventDefault();
            var dateRangeString = $("#daterangepicker").val();
            var dateStyle = $("#dateStyle").val();
            var queryParams = {
                "dateRangeString": dateRangeString,
                "dateStyle": dateStyle
            }
            location.href = "/operation/sales/exportExcel?queryParamStr=" + JSON.stringify(queryParams);
        });
    }

    var activeMenu = function () {
        $(".treeview-menu [href='/operation/sales']").parents("li:eq(0)").parents("li:eq(0)").parents("li:eq(0)").addClass("active");
        $(".treeview-menu [href='/operation/sales']").parents("li:eq(0)").parents("li:eq(0)").addClass("active");
        $(".treeview-menu [href='/operation/sales']").parents("li:eq(0)").addClass("active");
    }

    return {

        //main function to initiate the module
        init: function () {
            // set right height
            $(".content-wrapper, .right-side").css('min-height', 516);

            if (!jQuery().dataTable) {
                return;
            }
            activeMenu();
            initTable();
            initSku();
            initSelect();
        }

    };

}();

