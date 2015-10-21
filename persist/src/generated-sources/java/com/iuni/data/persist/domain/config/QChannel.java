package com.iuni.data.persist.domain.config;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QChannel is a Querydsl query type for Channel
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QChannel extends EntityPathBase<Channel> {

    private static final long serialVersionUID = -348144451;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QChannel channel = new QChannel("channel");

    public final QAbstractConfig _super = new QAbstractConfig(this);

    //inherited
    public final NumberPath<Integer> cancelFlag = _super.cancelFlag;

    public final QChannelType channelType;

    public final SetPath<com.iuni.data.persist.domain.webkpi.ClickWebKpi, com.iuni.data.persist.domain.webkpi.QClickWebKpi> clickWebKpiSet = this.<com.iuni.data.persist.domain.webkpi.ClickWebKpi, com.iuni.data.persist.domain.webkpi.QClickWebKpi>createSet("clickWebKpiSet", com.iuni.data.persist.domain.webkpi.ClickWebKpi.class, com.iuni.data.persist.domain.webkpi.QClickWebKpi.class, PathInits.DIRECT2);

    public final StringPath code = createString("code");

    //inherited
    public final StringPath createBy = _super.createBy;

    //inherited
    public final DatePath<java.util.Date> createDate = _super.createDate;

    //inherited
    public final StringPath desc = _super.desc;

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final StringPath name = createString("name");

    public final StringPath originalUrl = createString("originalUrl");

    public final SetPath<com.iuni.data.persist.domain.webkpi.PageWebKpi, com.iuni.data.persist.domain.webkpi.QPageWebKpi> pageWebKpiSet = this.<com.iuni.data.persist.domain.webkpi.PageWebKpi, com.iuni.data.persist.domain.webkpi.QPageWebKpi>createSet("pageWebKpiSet", com.iuni.data.persist.domain.webkpi.PageWebKpi.class, com.iuni.data.persist.domain.webkpi.QPageWebKpi.class, PathInits.DIRECT2);

    public final StringPath promotionUrl = createString("promotionUrl");

    public final StringPath shortUrl = createString("shortUrl");

    //inherited
    public final NumberPath<Integer> status = _super.status;

    //inherited
    public final StringPath updateBy = _super.updateBy;

    //inherited
    public final DatePath<java.util.Date> updateDate = _super.updateDate;

    public final SetPath<com.iuni.data.persist.domain.webkpi.WebKpi, com.iuni.data.persist.domain.webkpi.QWebKpi> webKpiSet = this.<com.iuni.data.persist.domain.webkpi.WebKpi, com.iuni.data.persist.domain.webkpi.QWebKpi>createSet("webKpiSet", com.iuni.data.persist.domain.webkpi.WebKpi.class, com.iuni.data.persist.domain.webkpi.QWebKpi.class, PathInits.DIRECT2);

    public QChannel(String variable) {
        this(Channel.class, forVariable(variable), INITS);
    }

    @SuppressWarnings("all")
    public QChannel(Path<? extends Channel> path) {
        this((Class)path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QChannel(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QChannel(PathMetadata<?> metadata, PathInits inits) {
        this(Channel.class, metadata, inits);
    }

    public QChannel(Class<? extends Channel> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.channelType = inits.isInitialized("channelType") ? new QChannelType(forProperty("channelType")) : null;
    }

}

