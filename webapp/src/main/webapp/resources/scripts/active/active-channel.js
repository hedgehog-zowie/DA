var ActiveChannelTable = function () {

    var initTable = function () {

        var table = $('#activity-channel-table');

        // begin table_channel
        table.dataTable({

            // Internationalisation. For more info refer to http://datatables.net/manual/i18n
            "language": {
                "aria": {
                    "sortAscending": ": 升序排列",
                    "sortDescending": ": 降序排列"
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
                [5, 15, 20, -1],
                [5, 15, 20, "全部"] // change per page values here
            ],
            // set the initial value
            "pageLength": 15,
            "pagingType": "bootstrap_full_number",
            "columnDefs": [{  // set default column settings
                'orderable': false,
                'targets': [0]
            }, {
                'font-size': 13,
            }, {
                "searchable": false,
                "targets": [0]
            }],
            "order": [
                [0, "asc"],
                [1, "asc"]
            ],
            //当前页合计
            //"fnRowCallback": function (nRow, aData, iDisplayIndex, iDisplayIndexFull) {
            //    if (iDisplayIndex == 0) {
            //        pvsum = 0;
            //        uvsum = 0;
            //        vvsum = 0;
            //        rnsum = 0;
            //    }
            //    pvsum += parseFloat(aData[3]);
            //    uvsum += parseFloat(aData[4]);
            //    vvsum += parseFloat(aData[5]);
            //    $("#pvsum").html(pvsum);
            //    $("#uvsum").html(uvsum);
            //    $("#vvsum").html(vvsum);
            //    return nRow;
            //}
        });

        var tableWrapper = jQuery('#activity-channel-table_wrapper');

        tableWrapper.find('.dataTables_length select').addClass("form-control input-xsmall input-inline");

        var drawChart = function (dataType) {
            // 路径配置
            require.config({
                paths: {
                    echarts: '/resources/plugins/echarts'
                }
            });
            // 使用
            require(
                [
                    'echarts',
                    'echarts/chart/pie',
                    'echarts/chart/funnel'
                ],
                function (ec) {
                    var dateRangeString = $("#daterangepicker").val() ? $("#daterangepicker").val() : "";
                    var channelTypeId = $("#channelType").val() ? $("#channelType").val() : 0;
                    var channelCode = $("#channelCode").val() ? $("#channelCode").val() : "";
                    //var dataType = $(".data-type[checked]").val();
                    //if (typeof(dataType) == "undefined")
                    //    dataType = $("input[name='data-type'][checked]").val();
                    if (typeof(dataType) == "undefined") {
                        $(".data-type:eq(0)").prop("checked", true);
                        dataType = "pv";
                    }
                    var queryParams = {
                        "dateRangeString": dateRangeString,
                        "channel": {
                            "channelType": {
                                "id": channelTypeId
                            },
                            "code": channelCode
                        },
                        "dataType": dataType
                    };
                    var url = "/activity/channel/data";

                    // 基于准备好的dom，初始化echarts图表
                    var chart = ec.init(document.getElementById('activity-channel-chart'), 'macarons');
                    chart.showLoading({text: "图表数据正在努力加载..."});
                    var option;
                    $.ajax({
                            type: "POST",
                            async: false, //同步执行
                            data: JSON.stringify(queryParams),
                            contentType: "application/json",
                            dataType: "json",
                            url: url,
                            success: function (result) {
                                option = {
                                    title: {
                                        text: result.title,
                                        //subtext: '统计各个渠道的订单数量',
                                        x: 'center'
                                    },
                                    tooltip: {
                                        trigger: 'item',
                                        formatter: "{a} <br/>{b} : {c} ({d}%)"
                                    },
                                    legend: {
                                        orient: 'vertical',
                                        x: 'left',
                                        data: $.makeArray(result.legendData)
                                    },
                                    toolbox: {
                                        show: true,
                                        feature: {
                                            mark: {show: true},
                                            dataView: {show: true, readOnly: false},
                                            magicType: {
                                                show: true,
                                                type: ['pie', 'funnel'],
                                                option: {
                                                    funnel: {
                                                        x: '25%',
                                                        width: '50%',
                                                        funnelAlign: 'left',
                                                        max: 10000,
                                                        itemStyle: {
                                                            normal: {
                                                                label: {
                                                                    position: 'outer',
                                                                    formatter: '{b}\n数量：{c}\n'
                                                                }
                                                            },
                                                            emphasis: {
                                                                label: {
                                                                    position: 'outer',
                                                                    formatter: '{b}\n数量：{c}\n'
                                                                }
                                                            }
                                                        },
                                                    }
                                                }
                                            },
                                            restore: {show: true},
                                            saveAsImage: {show: true}
                                        }
                                    },
                                    calculable: true,
                                    series: [
                                        {
                                            name: '渠道来源',
                                            type: 'pie',
                                            radius: '55%',
                                            center: ['50%', '60%'],
                                            itemStyle: {
                                                normal: {
                                                    label: {
                                                        position: 'outer',
                                                        formatter: '{b}\n数量：{c}\n占比：{d}%'
                                                    }
                                                }
                                            },
                                            data: $.makeArray(result.seriesData)
                                        }
                                    ]
                                };
                            }
                        }
                    );
                    chart.hideLoading();
                    // 为echarts对象加载数据
                    chart.setOption(option);
                    window.onresize = chart.resize;
                }
            );
        }

        $("#activity-channel-chart-tab").css("display", "none");
        $('#activity-channel-tab a[href="#activity-channel-chart-tab"]').click(function (e) {
            e.preventDefault();
            if ($("#activity-channel-chart-tab").css("display") == "none") {
                $("#activity-channel-table-tab").css("display", "none");
                $("#activity-channel-chart-tab").css("display", "block");
                var selected;
                $(".data-type").each(function () {
                    if ($(this).prop("checked") == true) {
                        selected = $(this).val();
                        return;
                    }
                });
                drawChart(selected);
            }
        });
        $('#activity-channel-tab a[href="#activity-channel-table-tab"]').click(function (e) {
            e.preventDefault();
            if ($("#activity-channel-table-tab").css("display") == "none") {
                $("#activity-channel-chart-tab").css("display", "none");
                $("#activity-channel-table-tab").css("display", "block");
            }
        });
        $(".data-type").change(function (e) {
            e.preventDefault();
            drawChart($(this).val());
        });

        $('#daterangepicker').daterangepicker({
            dateLimit: {days: 30},
            opens: 'left',
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

        $('#activity-channel-table-export').click(function (e) {
            e.preventDefault();
            var dateRangeString = $("#daterangepicker").val() ? $("#daterangepicker").val() : "";
            var channelTypeId = $("#channelType").val() ? $("#channelType").val() : 0;
            var channelCode = $("#channelCode").val() ? $("#channelCode").val() : "";
            var queryParams = {
                "dateRangeString": dateRangeString,
                "channel": {
                    "channelType": {
                        "id": channelTypeId
                    },
                    "code": channelCode
                },
            };
            location.href = "/activity/channel/exportExcel?queryParamStr=" + JSON.stringify(queryParams);
        });
    }

    return {

        //main function to initiate the module
        init: function () {
            activeMenu('/activity/channel');
            initTable();
        }

    };

}();

