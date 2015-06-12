package com.iuni.data.persist.domain.webkpi;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QWebKpi is a Querydsl query type for WebKpi
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QWebKpi extends EntityPathBase<WebKpi> {

    private static final long serialVersionUID = 1211748456;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWebKpi webKpi = new QWebKpi("webKpi");

    public final StringPath area = createString("area");

    public final com.iuni.data.persist.domain.config.QChannel channel;

    public final StringPath city = createString("city");

    public final StringPath country = createString("country");

    public final StringPath county = createString("county");

    public final DatePath<java.util.Date> createDate = createDate("createDate", java.util.Date.class);

    public final com.iuni.data.persist.domain.config.QFlowSource flowSource;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> ip = createNumber("ip", Integer.class);

    public final StringPath isp = createString("isp");

    public final NumberPath<Integer> newUv = createNumber("newUv", Integer.class);

    public final StringPath province = createString("province");

    public final NumberPath<Integer> pv = createNumber("pv", Integer.class);

    public final NumberPath<Long> stayTime = createNumber("stayTime", Long.class);

    public final DatePath<java.util.Date> time = createDate("time", java.util.Date.class);

    public final NumberPath<Integer> totalJump = createNumber("totalJump", Integer.class);

    public final NumberPath<Long> totalSize = createNumber("totalSize", Long.class);

    public final NumberPath<Long> totalTime = createNumber("totalTime", Long.class);

    public final StringPath ttype = createString("ttype");

    public final NumberPath<Integer> uv = createNumber("uv", Integer.class);

    public final NumberPath<Integer> vv = createNumber("vv", Integer.class);

    public final NumberPath<Integer> workday = createNumber("workday", Integer.class);

    public QWebKpi(String variable) {
        this(WebKpi.class, forVariable(variable), INITS);
    }

    @SuppressWarnings("all")
    public QWebKpi(Path<? extends WebKpi> path) {
        this((Class)path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QWebKpi(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QWebKpi(PathMetadata<?> metadata, PathInits inits) {
        this(WebKpi.class, metadata, inits);
    }

    public QWebKpi(Class<? extends WebKpi> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.channel = inits.isInitialized("channel") ? new com.iuni.data.persist.domain.config.QChannel(forProperty("channel")) : null;
        this.flowSource = inits.isInitialized("flowSource") ? new com.iuni.data.persist.domain.config.QFlowSource(forProperty("flowSource"), inits.get("flowSource")) : null;
    }

}

