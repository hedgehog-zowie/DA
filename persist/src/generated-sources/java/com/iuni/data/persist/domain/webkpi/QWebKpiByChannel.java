package com.iuni.data.persist.domain.webkpi;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QWebKpiByChannel is a Querydsl query type for WebKpiByChannel
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QWebKpiByChannel extends EntityPathBase<WebKpiByChannel> {

    private static final long serialVersionUID = -1275261660;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWebKpiByChannel webKpiByChannel = new QWebKpiByChannel("webKpiByChannel");

    public final com.iuni.data.persist.domain.config.QChannel channel;

    public final DatePath<java.util.Date> createDate = createDate("createDate", java.util.Date.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> ip = createNumber("ip", Long.class);

    public final NumberPath<Long> pv = createNumber("pv", Long.class);

    public final NumberPath<Long> stayTime = createNumber("stayTime", Long.class);

    public final DatePath<java.util.Date> time = createDate("time", java.util.Date.class);

    public final NumberPath<Long> totalJump = createNumber("totalJump", Long.class);

    public final StringPath ttype = createString("ttype");

    public final NumberPath<Long> uv = createNumber("uv", Long.class);

    public final NumberPath<Long> vv = createNumber("vv", Long.class);

    public QWebKpiByChannel(String variable) {
        this(WebKpiByChannel.class, forVariable(variable), INITS);
    }

    @SuppressWarnings("all")
    public QWebKpiByChannel(Path<? extends WebKpiByChannel> path) {
        this((Class)path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QWebKpiByChannel(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QWebKpiByChannel(PathMetadata<?> metadata, PathInits inits) {
        this(WebKpiByChannel.class, metadata, inits);
    }

    public QWebKpiByChannel(Class<? extends WebKpiByChannel> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.channel = inits.isInitialized("channel") ? new com.iuni.data.persist.domain.config.QChannel(forProperty("channel"), inits.get("channel")) : null;
    }

}

