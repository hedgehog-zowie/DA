package com.iuni.data.persist.domain.config;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QRTagType is a Querydsl query type for RTagType
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QRTagType extends EntityPathBase<RTagType> {

    private static final long serialVersionUID = 1887350952;

    public static final QRTagType rTagType = new QRTagType("rTagType");

    public final QAbstractConfig _super = new QAbstractConfig(this);

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

    public final ListPath<RTag, QRTag> rTags = this.<RTag, QRTag>createList("rTags", RTag.class, QRTag.class, PathInits.DIRECT2);

    //inherited
    public final NumberPath<Integer> status = _super.status;

    //inherited
    public final StringPath updateBy = _super.updateBy;

    //inherited
    public final DatePath<java.util.Date> updateDate = _super.updateDate;

    public QRTagType(String variable) {
        super(RTagType.class, forVariable(variable));
    }

    @SuppressWarnings("all")
    public QRTagType(Path<? extends RTagType> path) {
        super((Class)path.getType(), path.getMetadata());
    }

    public QRTagType(PathMetadata<?> metadata) {
        super(RTagType.class, metadata);
    }

}

