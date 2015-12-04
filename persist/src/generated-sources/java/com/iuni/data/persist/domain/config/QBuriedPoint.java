package com.iuni.data.persist.domain.config;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QBuriedPoint is a Querydsl query type for BuriedPoint
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QBuriedPoint extends EntityPathBase<BuriedPoint> {

    private static final long serialVersionUID = 1547775457;

    public static final QBuriedPoint buriedPoint = new QBuriedPoint("buriedPoint");

    public final QAbstractConfig _super = new QAbstractConfig(this);

    public final ListPath<BuriedRelation, QBuriedRelation> buriedRelations = this.<BuriedRelation, QBuriedRelation>createList("buriedRelations", BuriedRelation.class, QBuriedRelation.class, PathInits.DIRECT2);

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

    public final StringPath pageName = createString("pageName");

    public final StringPath pagePosition = createString("pagePosition");

    public final StringPath pointFlag = createString("pointFlag");

    public final NumberPath<Integer> pointType = createNumber("pointType", Integer.class);

    //inherited
    public final NumberPath<Integer> status = _super.status;

    //inherited
    public final StringPath updateBy = _super.updateBy;

    //inherited
    public final DatePath<java.util.Date> updateDate = _super.updateDate;

    public final StringPath website = createString("website");

    public final StringPath websiteCode = createString("websiteCode");

    public QBuriedPoint(String variable) {
        super(BuriedPoint.class, forVariable(variable));
    }

    @SuppressWarnings("all")
    public QBuriedPoint(Path<? extends BuriedPoint> path) {
        super((Class)path.getType(), path.getMetadata());
    }

    public QBuriedPoint(PathMetadata<?> metadata) {
        super(BuriedPoint.class, metadata);
    }

}

