package com.iuni.data.persist.domain.config;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QBuriedGroup is a Querydsl query type for BuriedGroup
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QBuriedGroup extends EntityPathBase<BuriedGroup> {

    private static final long serialVersionUID = 1539559120;

    public static final QBuriedGroup buriedGroup = new QBuriedGroup("buriedGroup");

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

    public final StringPath name = createString("name");

    //inherited
    public final NumberPath<Integer> status = _super.status;

    //inherited
    public final StringPath updateBy = _super.updateBy;

    //inherited
    public final DatePath<java.util.Date> updateDate = _super.updateDate;

    public QBuriedGroup(String variable) {
        super(BuriedGroup.class, forVariable(variable));
    }

    @SuppressWarnings("all")
    public QBuriedGroup(Path<? extends BuriedGroup> path) {
        super((Class)path.getType(), path.getMetadata());
    }

    public QBuriedGroup(PathMetadata<?> metadata) {
        super(BuriedGroup.class, metadata);
    }

}

