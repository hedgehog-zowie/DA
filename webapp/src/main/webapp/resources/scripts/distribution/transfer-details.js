var TransferDetailsOfDistribution = function () {

    var initTable = function () {

        var tableOfOut = $('#distribution-transfer-details-out-table');
        // begin table
        tableOfOut.dataTable({
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
                    "next": "下页",
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
            ],
        });
        var tableOfOutWrapper = jQuery('#distribution-transfer-details-out-table_wrapper');
        tableOfOutWrapper.find('.dataTables_length select').addClass("form-control input-xsmall input-inline");

        var tableOfIn = $('#distribution-transfer-details-in-table');
        // begin table
        tableOfIn.dataTable({
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
                    "next": "下页",
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
            ],
        });
        var tableOfInWrapper = jQuery('#distribution-transfer-details-in-table_wrapper');
        tableOfInWrapper.find('.dataTables_length select').addClass("form-control input-xsmall input-inline");

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
                    '七月', '八月', '九月', '十月', '十一月', '十二月'],
            }
        }, function (start, end, label) {//格式化日期显示框
            $('#daterangepicker span').html(start.format('YYYY/MM/DD') + ' - ' + end.format('YYYY/MM/DD'));
        });

        $('#distribution-transfer-details-table-export').click(function (e) {
            e.preventDefault();
            var dateRangeString = $("#daterangepicker").val();
            var queryParams = {
                "dateRangeString": dateRangeString,
            }
            location.href = "/distribution/transferDetails/exportExcel?queryParamStr=" + JSON.stringify(queryParams);
        });

        $("#distribution-transfer-details-in-tab").css("display", "none");
        $('#distribution-transfer-tab a[href="#distribution-transfer-details-out-tab"]').click(function (e) {
            e.preventDefault();
            if ($("#distribution-transfer-details-out-tab").css("display") == "none") {
                $("#distribution-transfer-details-in-tab").css("display", "none");
                $("#distribution-transfer-details-out-tab").css("display", "block");
            }
        });
        $('#distribution-transfer-tab a[href="#distribution-transfer-details-in-tab"]').click(function (e) {
            e.preventDefault();
            if ($("#distribution-transfer-details-in-tab").css("display") == "none") {
                $("#distribution-transfer-details-out-tab").css("display", "none");
                $("#distribution-transfer-details-in-tab").css("display", "block");
            }
        });
    }

    return {

        //main function to initiate the module
        init: function () {
            activeMenu();

            // set right height
            $(".content-wrapper, .right-side").css('min-height', 516);

            if (!jQuery().dataTable) {
                return;
            }
            initTable();
        }

    };

}();

var activeMenu = function () {
    $(".treeview-menu [href='/distribution/transferDetails']").parents("li:eq(0)").parents("li:eq(0)").parents("li:eq(0)").addClass("active");
    $(".treeview-menu [href='/distribution/transferDetails']").parents("li:eq(0)").parents("li:eq(0)").addClass("active");
    $(".treeview-menu [href='/distribution/transferDetails']").parents("li:eq(0)").addClass("active");
}
