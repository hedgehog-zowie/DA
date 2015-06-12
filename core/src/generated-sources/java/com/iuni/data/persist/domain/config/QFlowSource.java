package com.iuni.data.persist.domain.config;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QFlowSource is a Querydsl query type for FlowSource
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QFlowSource extends EntityPathBase<FlowSource> {

    private static final long serialVersionUID = -1190975249;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFlowSource flowSource = new QFlowSource("flowSource");

    public final QAbstractConfig _super = new QAbstractConfig(this);

    //inherited
    public final NumberPath<Integer> cancelFlag = _super.cancelFlag;

    //inherited
    public final StringPath createBy = _super.createBy;

    //inherited
    public final DatePath<java.util.Date> createDate = _super.createDate;

    //inherited
    public final StringPath desc = _super.desc;

    public final QFlowSourceType flowSourceType;

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final StringPath name = createString("name");

    //inherited
    public final NumberPath<Integer> status = _super.status;

    //inherited
    public final StringPath updateBy = _super.updateBy;

    //inherited
    public final DatePath<java.util.Date> updateDate = _super.updateDate;

    public final StringPath url = createString("url");

    public final SetPath<com.iuni.data.persist.domain.webkpi.WebKpi, com.iuni.data.persist.domain.webkpi.QWebKpi> webKpis = this.<com.iuni.data.persist.domain.webkpi.WebKpi, com.iuni.data.persist.domain.webkpi.QWebKpi>createSet("webKpis", com.iuni.data.persist.domain.webkpi.WebKpi.class, com.iuni.data.persist.domain.webkpi.QWebKpi.class, PathInits.DIRECT2);

    public QFlowSource(String variable) {
        this(FlowSource.class, forVariable(variable), INITS);
    }

    @SuppressWarnings("all")
    public QFlowSource(Path<? extends FlowSource> path) {
        this((Class)path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QFlowSource(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QFlowSource(PathMetadata<?> metadata, PathInits inits) {
        this(FlowSource.class, metadata, inits);
    }

    public QFlowSource(Class<? extends FlowSource> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.flowSourceType = inits.isInitialized("flowSourceType") ? new QFlowSourceType(forProperty("flowSourceType")) : null;
    }

}

