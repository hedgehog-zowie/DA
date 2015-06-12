package com.iuni.data.persist.domain.webkpi;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QClickWebKpi is a Querydsl query type for ClickWebKpi
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QClickWebKpi extends EntityPathBase<ClickWebKpi> {

    private static final long serialVersionUID = 1250305440;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QClickWebKpi clickWebKpi = new QClickWebKpi("clickWebKpi");

    public final com.iuni.data.persist.domain.config.QChannel channel;

    public final NumberPath<Integer> count = createNumber("count", Integer.class);

    public final DatePath<java.util.Date> createDate = createDate("createDate", java.util.Date.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.iuni.data.persist.domain.config.QRTag rtag;

    public final DatePath<java.util.Date> time = createDate("time", java.util.Date.class);

    public final StringPath ttype = createString("ttype");

    public QClickWebKpi(String variable) {
        this(ClickWebKpi.class, forVariable(variable), INITS);
    }

    @SuppressWarnings("all")
    public QClickWebKpi(Path<? extends ClickWebKpi> path) {
        this((Class)path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QClickWebKpi(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QClickWebKpi(PathMetadata<?> metadata, PathInits inits) {
        this(ClickWebKpi.class, metadata, inits);
    }

    public QClickWebKpi(Class<? extends ClickWebKpi> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.channel = inits.isInitialized("channel") ? new com.iuni.data.persist.domain.config.QChannel(forProperty("channel")) : null;
        this.rtag = inits.isInitialized("rtag") ? new com.iuni.data.persist.domain.config.QRTag(forProperty("rtag"), inits.get("rtag")) : null;
    }

}

