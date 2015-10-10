package com.iuni.data.webapp.service.webkpi.impl;

import com.iuni.data.persist.domain.config.FlowSource;
import com.iuni.data.persist.domain.config.FlowSourceType;
import com.iuni.data.persist.domain.webkpi.QWebKpi;
import com.iuni.data.persist.domain.webkpi.WebKpi;
import com.iuni.data.persist.repository.webkpi.WebKpiRepository;
import com.iuni.data.webapp.service.webkpi.WebKpiService;
import com.mysema.query.Tuple;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.expr.BooleanExpression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("webKpiService")
public class WebKpiServiceImpl implements WebKpiService {

    private static final Logger logger = LoggerFactory.getLogger(WebKpiService.class);

    @Autowired
    private WebKpiRepository webKpiRepository;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Override
    public List<WebKpi> getWebKpi(Date startDate, Date endDate) {
        QWebKpi qWebKpi = QWebKpi.webKpi;
        BooleanExpression expression = generateExpression(startDate, endDate);

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        JPAQuery query = new JPAQuery(entityManager);
        List<Tuple> s = query.from(qWebKpi).where(expression).groupBy(qWebKpi.time).orderBy(qWebKpi.time.asc())
                .list(qWebKpi.time, qWebKpi.pv.sum(), qWebKpi.uv.sum(), qWebKpi.newUv.sum(), qWebKpi.vv.sum());

        List<WebKpi> webKpiList = new ArrayList<>();
        if (s != null && s.size() > 0) {
            int size = s.size();
            for (int i = 0; i < size; i++) {
                WebKpi webKpi = new WebKpi();
                webKpi.setTime(s.get(i).get(0, Date.class));
                webKpi.setPv(s.get(i).get(1, Integer.class));
                webKpi.setUv(s.get(i).get(2, Integer.class));
                webKpi.setNewUv(s.get(i).get(3, Integer.class));
                webKpi.setVv(s.get(i).get(4, Integer.class));
                webKpiList.add(webKpi);
            }
        }

        return webKpiList;
    }

    @Override
    public List<WebKpi> getWebKpiGroupByArea(Date startDate, Date endDate){
        QWebKpi qWebKpi = QWebKpi.webKpi;
        BooleanExpression expression = generateExpression(startDate, endDate);

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        JPAQuery query = new JPAQuery(entityManager);
        List<Tuple> s = query.from(qWebKpi).where(expression.and(qWebKpi.country.eq("中国"))).groupBy(qWebKpi.province).orderBy(qWebKpi.province.asc())
                .list(qWebKpi.province, qWebKpi.pv.sum(), qWebKpi.uv.sum(), qWebKpi.newUv.sum(), qWebKpi.vv.sum());

        List<WebKpi> webKpiList = new ArrayList<>();
        if (s != null && s.size() > 0) {
            int size = s.size();
            for (int i = 0; i < size; i++) {
                WebKpi webKpi = new WebKpi();
                webKpi.setProvince(s.get(i).get(0, String.class));
                webKpi.setPv(s.get(i).get(1, Integer.class));
                webKpi.setUv(s.get(i).get(2, Integer.class));
                webKpi.setNewUv(s.get(i).get(3, Integer.class));
                webKpi.setVv(s.get(i).get(4, Integer.class));
                webKpiList.add(webKpi);
            }
        }

        return webKpiList;
    }

    @Override
    public List<WebKpi> getWebKpiGroupBySource(Date startDate, Date endDate){
        QWebKpi qWebKpi = QWebKpi.webKpi;
        BooleanExpression expression = generateExpression(startDate, endDate);

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        JPAQuery query = new JPAQuery(entityManager);
        List<Tuple> s = query.from(qWebKpi).where(expression).groupBy(qWebKpi.flowSource.flowSourceType.name).orderBy(qWebKpi.flowSource.flowSourceType.name.asc())
                .list(qWebKpi.flowSource.flowSourceType.name, qWebKpi.pv.sum(), qWebKpi.uv.sum(), qWebKpi.newUv.sum(), qWebKpi.vv.sum());

        List<WebKpi> webKpiList = new ArrayList<>();
        if (s != null && s.size() > 0) {
            int size = s.size();
            for (int i = 0; i < size; i++) {
                WebKpi webKpi = new WebKpi();
                FlowSourceType flowSourceType = new FlowSourceType();
                flowSourceType.setName(s.get(i).get(0, String.class));
                FlowSource flowSource = new FlowSource();
                flowSource.setFlowSourceType(flowSourceType);
                webKpi.setFlowSource(flowSource);
                webKpi.setPv(s.get(i).get(1, Integer.class));
                webKpi.setUv(s.get(i).get(2, Integer.class));
                webKpi.setNewUv(s.get(i).get(3, Integer.class));
                webKpi.setVv(s.get(i).get(4, Integer.class));
                webKpiList.add(webKpi);
            }
        }

        return webKpiList;
    }

    private BooleanExpression generateExpression(Date startDate, Date endDate) {
        QWebKpi qWebKpi = QWebKpi.webKpi;
        BooleanExpression booleanExpression = qWebKpi.ttype.eq("dd");

        if (startDate != null) {
            BooleanExpression startDateExpression = qWebKpi.time.goe(startDate);
            booleanExpression = (booleanExpression == null) ? startDateExpression : booleanExpression.and(startDateExpression);
        }
        if (endDate != null) {
            BooleanExpression endDateExpression = qWebKpi.time.loe(endDate);
            booleanExpression = (booleanExpression == null) ? endDateExpression : booleanExpression.and(endDateExpression);
        }

        return booleanExpression;
    }
}
