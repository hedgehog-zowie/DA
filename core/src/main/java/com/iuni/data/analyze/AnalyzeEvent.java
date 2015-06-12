package com.iuni.data.analyze;

import com.iuni.data.Analyze;
import com.iuni.data.analyze.cube.Result;
import com.iuni.data.common.TType;

import java.util.Date;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
@Deprecated
public class AnalyzeEvent {

    private Analyze analyze;
    private Date startDate;
    private Date endDate;
    private TType tType;
    private boolean createPartition;
    private Result result;

    public AnalyzeEvent(Analyze analyze, Date startDate, Date endDate, TType tType, boolean createPartition) {
        this.analyze = analyze;
        this.startDate = startDate;
        this.endDate = endDate;
        this.tType = tType;
        this.createPartition = createPartition;
    }

    public Analyze getAnalyze() {
        return analyze;
    }

    public void setAnalyze(Analyze analyze) {
        this.analyze = analyze;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public TType gettType() {
        return tType;
    }

    public void settType(TType tType) {
        this.tType = tType;
    }

    public boolean isCreatePartition() {
        return createPartition;
    }

    public void setCreatePartition(boolean createPartition) {
        this.createPartition = createPartition;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "AnalyzeEvent{" +
                "analyze=" + analyze +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", tType=" + tType +
                ", createPartition=" + createPartition +
                ", analyzeResult=" + result +
                '}';
    }
}

