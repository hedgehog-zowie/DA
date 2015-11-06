package com.iuni.data.conf.analyze;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public enum AnalyzeType {
    OTHER(null),
    IMPALA("com.iuni.data.analyze.ImpalaAnalyze"),
    HIVE("com.iuni.data.analyze.HiveAnalyze"),
    ACTIVE("com.iuni.data.analyze.ActivityAnalyze"),
    PAGE("com.iuni.data.analyze.PageAnalyze"),
    CLICK("com.iuni.data.analyze.ClickAnalyze"),
    PAGE_FOR_WHOLE_SITE("com.iuni.data.analyze.PageAnalyzeForWholeSite"),
    WHOLE_SITE_BY_CHANNEL("com.iuni.data.analyze.WholeSiteAnalyzeByChannel"),
    ;

    private final String className;

    private AnalyzeType(String className) {
        this.className = className;
    }

    public String getClassName() {
        return className;
    }

}
