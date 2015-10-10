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
        'echarts/chart/map'
    ],
    function (ec) {
        // 基于准备好的dom，初始化echarts图表
        var chart = ec.init(document.getElementById('areaChart'), 'macarons');
        chart.showLoading({text: "图表数据正在努力加载..."});
        var option = {
            tooltip: {
                show: true
            },
            legend: {
                orient: 'vertical',
                x:'left',
                data: []
            },
            dataRange: {
                min: 0,
                max: 100000,
                x: 'left',
                y: 'bottom',
                calculable : true
            },
            series: []
        };

        var beginDate = $("#flow_area_startDate").val();
        var endDate = $("#flow_area_endDate").val();
        var url = "flow/area/data?startDate=" + beginDate + "&endDate=" + endDate;
        $.ajax({
            type: "POST",
            async: false, //同步执行
            dataType: "json",
            url: url,
            success: function (result) {
                if (result && result.legend && result.series) {
                    // 将返回的category和series对象赋值给options对象内的category和series
                    // 因为xAxis是一个数组 这里需要是xAxis[i]的形式
                    //alert(result.category + "\n" + result.series + "\n" + result.legend);
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