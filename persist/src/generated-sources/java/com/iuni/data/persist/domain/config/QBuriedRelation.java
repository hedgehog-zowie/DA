package com.iuni.data.persist.domain.config;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QBuriedRelation is a Querydsl query type for BuriedRelation
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QBuriedRelation extends EntityPathBase<BuriedRelation> {

    private static final long serialVersionUID = -2010713077;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBuriedRelation buriedRelation = new QBuriedRelation("buriedRelation");

    public final QAbstractConfig _super = new QAbstractConfig(this);

    public final QBuriedGroup buriedGroup;

    public final QBuriedPoint buriedPoint;

    //inherited
    public final NumberPath<Integer> cancelFlag = _super.cancelFlag;

    //inherited
    public final StringPath createBy = _super.createBy;

    //inherited
    public final DatePath<java.util.Date> createDate = _super.createDate;

    //inherited
    public final StringPath desc = _super.desc;

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final NumberPath<Integer> index = createNumber("index", Integer.class);

    //inherited
    public final NumberPath<Integer> status = _super.status;

    //inherited
    public final StringPath updateBy = _super.updateBy;

    //inherited
    public final DatePath<java.util.Date> updateDate = _super.updateDate;

    public QBuriedRelation(String variable) {
        this(BuriedRelation.class, forVariable(variable), INITS);
    }

    @SuppressWarnings("all")
    public QBuriedRelation(Path<? extends BuriedRelation> path) {
        this((Class)path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QBuriedRelation(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QBuriedRelation(PathMetadata<?> metadata, PathInits inits) {
        this(BuriedRelation.class, metadata, inits);
    }

    public QBuriedRelation(Class<? extends BuriedRelation> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.buriedGroup = inits.isInitialized("buriedGroup") ? new QBuriedGroup(forProperty("buriedGroup")) : null;
        this.buriedPoint = inits.isInitialized("buriedPoint") ? new QBuriedPoint(forProperty("buriedPoint")) : null;
    }

}

