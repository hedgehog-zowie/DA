package com.iuni.data.webapp.controller;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public abstract class ReportController {

    private boolean showChart;

    public boolean isShowChart() {
        return showChart;
    }

    public void setShowChart(boolean showChart) {
        this.showChart = showChart;
    }

}
