package com.iuni.data.persist.domain.config;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QAdvertisement is a Querydsl query type for Advertisement
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QAdvertisement extends EntityPathBase<Advertisement> {

    private static final long serialVersionUID = -1737630145;

    public static final QAdvertisement advertisement = new QAdvertisement("advertisement");

    public final QAbstractConfig _super = new QAbstractConfig(this);

    //inherited
    public final NumberPath<Integer> cancelFlag = _super.cancelFlag;

    public final StringPath channel = createString("channel");

    //inherited
    public final StringPath createBy = _super.createBy;

    //inherited
    public final DatePath<java.util.Date> createDate = _super.createDate;

    //inherited
    public final StringPath desc = _super.desc;

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final StringPath name = createString("name");

    public final StringPath position = createString("position");

    //inherited
    public final NumberPath<Integer> status = _super.status;

    public final StringPath type = createString("type");

    //inherited
    public final StringPath updateBy = _super.updateBy;

    //inherited
    public final DatePath<java.util.Date> updateDate = _super.updateDate;

    public final StringPath url = createString("url");

    public QAdvertisement(String variable) {
        super(Advertisement.class, forVariable(variable));
    }

    @SuppressWarnings("all")
    public QAdvertisement(Path<? extends Advertisement> path) {
        super((Class)path.getType(), path.getMetadata());
    }

    public QAdvertisement(PathMetadata<?> metadata) {
        super(Advertisement.class, metadata);
    }

}

