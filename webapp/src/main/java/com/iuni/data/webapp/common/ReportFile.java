package com.iuni.data.webapp.common;

/**
 * 编译后的报表文件
 *
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public enum ReportFile {

    test("test", "test.jasper", "test-chart-html5.jasper"),

    flowOverView("flowOverView", "flow-overview.jasper", ""),
    flowArea("flowArea", "flow-area.jasper", ""),
    flowSource("flowSource", "flow-source.jasper", ""),
    flowTrend("flowTrend", "flow-trend.jasper", "");

    private final String name;
    private final String path;
    private final String chartPath;

    ReportFile(String name, String path, String chartPath) {
        this.name = name;
        this.path = path;
        this.chartPath = chartPath;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public String getChartPath() {
        return chartPath;
    }
}
