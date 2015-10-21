package com.iuni.data.persist.domain.config;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QRTag is a Querydsl query type for RTag
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QRTag extends EntityPathBase<RTag> {

    private static final long serialVersionUID = 644711502;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRTag rTag = new QRTag("rTag");

    public final QAbstractConfig _super = new QAbstractConfig(this);

    //inherited
    public final NumberPath<Integer> cancelFlag = _super.cancelFlag;

    public final SetPath<com.iuni.data.persist.domain.webkpi.ClickWebKpi, com.iuni.data.persist.domain.webkpi.QClickWebKpi> clickWebKpis = this.<com.iuni.data.persist.domain.webkpi.ClickWebKpi, com.iuni.data.persist.domain.webkpi.QClickWebKpi>createSet("clickWebKpis", com.iuni.data.persist.domain.webkpi.ClickWebKpi.class, com.iuni.data.persist.domain.webkpi.QClickWebKpi.class, PathInits.DIRECT2);

    //inherited
    public final StringPath createBy = _super.createBy;

    //inherited
    public final DatePath<java.util.Date> createDate = _super.createDate;

    //inherited
    public final StringPath desc = _super.desc;

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final StringPath info = createString("info");

    public final StringPath name = createString("name");

    public final StringPath parent = createString("parent");

    public final StringPath rtag = createString("rtag");

    public final QRTagType rTagType;

    //inherited
    public final NumberPath<Integer> status = _super.status;

    //inherited
    public final StringPath updateBy = _super.updateBy;

    //inherited
    public final DatePath<java.util.Date> updateDate = _super.updateDate;

    public QRTag(String variable) {
        this(RTag.class, forVariable(variable), INITS);
    }

    @SuppressWarnings("all")
    public QRTag(Path<? extends RTag> path) {
        this((Class)path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QRTag(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QRTag(PathMetadata<?> metadata, PathInits inits) {
        this(RTag.class, metadata, inits);
    }

    public QRTag(Class<? extends RTag> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.rTagType = inits.isInitialized("rTagType") ? new QRTagType(forProperty("rTagType")) : null;
    }

}

