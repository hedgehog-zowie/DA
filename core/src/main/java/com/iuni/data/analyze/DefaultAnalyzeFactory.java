package com.iuni.data.analyze;

import com.google.common.base.Preconditions;
import com.iuni.data.Analyze;
import com.iuni.data.AnalyzeFactory;
import com.iuni.data.conf.analyze.AnalyzeType;
import com.iuni.data.conf.iplib.IpLibType;
import com.iuni.data.exceptions.IuniDAException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class DefaultAnalyzeFactory implements AnalyzeFactory {

    private static final Logger logger = LoggerFactory.getLogger(DefaultAnalyzeFactory.class);

    @Override
    public Analyze create(String name, String type) throws IuniDAException {
        Preconditions.checkNotNull(name, "name");
        Preconditions.checkNotNull(type, "type");
        logger.info("Creating instance of analyze {}, type {}", name, type);
        Class<? extends Analyze> analyzeClass = getClass(type);
        try {
            Analyze analyze = analyzeClass.newInstance();
            analyze.setName(name);
            return analyze;
        } catch (Exception ex) {
            throw new IuniDAException("Unable to setBasicInfoForCreate analyze: " + name + ", type: " + type + ", class: " + analyzeClass.getName(), ex);
        }
    }

    @Override
    public Class<? extends Analyze> getClass(String type) throws IuniDAException {
        String className = type;
        AnalyzeType analyzeType = AnalyzeType.OTHER;
        try {
            analyzeType = AnalyzeType.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException ex) {
            logger.debug("analyze type {} is a custom type", type);
        }
        if (!analyzeType.equals(AnalyzeType.OTHER)) {
            className = analyzeType.getClassName();
        }
        try {
            return (Class<? extends Analyze>) Class.forName(className);
        } catch (Exception ex) {
            throw new IuniDAException("Unable to load analyze type: " + type + ", class: " + className, ex);
        }
    }

}
