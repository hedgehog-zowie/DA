// 路径配置
require.config({
    paths: {
        echarts: 'resources/echarts/dist'
    }
});

// 使用
require(
    [
        'echarts',
        'echarts/chart/line'
    ],
    function (ec) {
        // 基于准备好的dom，初始化echarts图表
        var chart = ec.init(document.getElementById('trendChart'), 'macarons');
        chart.showLoading({text: "图表数据正在努力加载..."});
        var option = {
            tooltip: {
                show: true
            },
            legend: {
                data: []
            },
            xAxis: [
                {
                    axisLabel: {
                        interval: 0,
                        rotate: 90,
                        textStyle: {
                            fontSize: 10
                        }
                    },
                    type: 'category',
                    data: []
                }
            ],
            yAxis: [
                {
                    type: 'value'
                }
            ],
            series: []
        };

        var beginDate = $("#flow_trend_startDate").val();
        var endDate = $("#flow_trend_endDate").val();
        var url = "flow/trend/data?startDate=" + beginDate + "&endDate=" + endDate;
        $.ajax({
            type: "POST",
            async: false, //同步执行
            dataType: "json",
            url: url,
            success: function (result) {
                if (result && result.legend && result.category && result.series) {
                    // 将返回的category和series对象赋值给options对象内的category和series
                    // 因为xAxis是一个数组 这里需要是xAxis[i]的形式
                    //alert(result.category + "\n" + result.series + "\n" + result.legend);
                    option.xAxis[0].data = result.category;
                    option.series = result.series;
                    option.legend.data = result.legend;
                    chart.hideLoading();
                    // 为echarts对象加载数据
                    chart.setOption(option);
                }else {
                    chart.showLoading({text: "无数据！"});
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                chart.showLoading({text: "图表请求数据失败！"});
            }
        });

    }
)
;