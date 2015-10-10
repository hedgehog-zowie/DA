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

    public static final QChannel channel = new QChannel("channel");

    public final QAbstractConfig _super = new QAbstractConfig(this);

    //inherited
    public final NumberPath<Integer> cancelFlag = _super.cancelFlag;

    public final SetPath<com.iuni.data.persist.domain.webkpi.ClickWebKpi, com.iuni.data.persist.domain.webkpi.QClickWebKpi> clickWebKpis = this.<com.iuni.data.persist.domain.webkpi.ClickWebKpi, com.iuni.data.persist.domain.webkpi.QClickWebKpi>createSet("clickWebKpis", com.iuni.data.persist.domain.webkpi.ClickWebKpi.class, com.iuni.data.persist.domain.webkpi.QClickWebKpi.class, PathInits.DIRECT2);

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

    public final SetPath<com.iuni.data.persist.domain.webkpi.PageWebKpi, com.iuni.data.persist.domain.webkpi.QPageWebKpi> pageWebKpis = this.<com.iuni.data.persist.domain.webkpi.PageWebKpi, com.iuni.data.persist.domain.webkpi.QPageWebKpi>createSet("pageWebKpis", com.iuni.data.persist.domain.webkpi.PageWebKpi.class, com.iuni.data.persist.domain.webkpi.QPageWebKpi.class, PathInits.DIRECT2);

    public final StringPath promotionUrl = createString("promotionUrl");

    public final StringPath shortUrl = createString("shortUrl");

    //inherited
    public final NumberPath<Integer> status = _super.status;

    //inherited
    public final StringPath updateBy = _super.updateBy;

    //inherited
    public final DatePath<java.util.Date> updateDate = _super.updateDate;

    public final SetPath<com.iuni.data.persist.domain.webkpi.WebKpi, com.iuni.data.persist.domain.webkpi.QWebKpi> webKpis = this.<com.iuni.data.persist.domain.webkpi.WebKpi, com.iuni.data.persist.domain.webkpi.QWebKpi>createSet("webKpis", com.iuni.data.persist.domain.webkpi.WebKpi.class, com.iuni.data.persist.domain.webkpi.QWebKpi.class, PathInits.DIRECT2);

    public QChannel(String variable) {
        super(Channel.class, forVariable(variable));
    }

    @SuppressWarnings("all")
    public QChannel(Path<? extends Channel> path) {
        super((Class)path.getType(), path.getMetadata());
    }

    public QChannel(PathMetadata<?> metadata) {
        super(Channel.class, metadata);
    }

}

