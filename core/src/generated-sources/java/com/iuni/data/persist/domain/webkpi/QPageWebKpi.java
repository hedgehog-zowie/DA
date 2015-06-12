package com.iuni.data.persist.domain.webkpi;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QPageWebKpi is a Querydsl query type for PageWebKpi
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QPageWebKpi extends EntityPathBase<PageWebKpi> {

    private static final long serialVersionUID = 965450711;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPageWebKpi pageWebKpi = new QPageWebKpi("pageWebKpi");

    public final com.iuni.data.persist.domain.config.QChannel channel;

    public final DatePath<java.util.Date> createDate = createDate("createDate", java.util.Date.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> ip = createNumber("ip", Integer.class);

    public final StringPath page = createString("page");

    public final NumberPath<Integer> pv = createNumber("pv", Integer.class);

    public final DatePath<java.util.Date> time = createDate("time", java.util.Date.class);

    public final StringPath ttype = createString("ttype");

    public final NumberPath<Integer> uv = createNumber("uv", Integer.class);

    public final NumberPath<Integer> vv = createNumber("vv", Integer.class);

    public QPageWebKpi(String variable) {
        this(PageWebKpi.class, forVariable(variable), INITS);
    }

    @SuppressWarnings("all")
    public QPageWebKpi(Path<? extends PageWebKpi> path) {
        this((Class)path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QPageWebKpi(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QPageWebKpi(PathMetadata<?> metadata, PathInits inits) {
        this(PageWebKpi.class, metadata, inits);
    }

    public QPageWebKpi(Class<? extends PageWebKpi> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.channel = inits.isInitialized("channel") ? new com.iuni.data.persist.domain.config.QChannel(forProperty("channel")) : null;
    }

}

