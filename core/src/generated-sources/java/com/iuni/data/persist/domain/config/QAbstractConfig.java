package com.iuni.data.persist.domain.config;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QAbstractConfig is a Querydsl query type for AbstractConfig
 */
@Generated("com.mysema.query.codegen.SupertypeSerializer")
public class QAbstractConfig extends EntityPathBase<AbstractConfig> {

    private static final long serialVersionUID = -985679606;

    public static final QAbstractConfig abstractConfig = new QAbstractConfig("abstractConfig");

    public final NumberPath<Integer> cancelFlag = createNumber("cancelFlag", Integer.class);

    public final StringPath createBy = createString("createBy");

    public final DatePath<java.util.Date> createDate = createDate("createDate", java.util.Date.class);

    public final StringPath desc = createString("desc");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> status = createNumber("status", Integer.class);

    public final StringPath updateBy = createString("updateBy");

    public final DatePath<java.util.Date> updateDate = createDate("updateDate", java.util.Date.class);

    public QAbstractConfig(String variable) {
        super(AbstractConfig.class, forVariable(variable));
    }

    @SuppressWarnings("all")
    public QAbstractConfig(Path<? extends AbstractConfig> path) {
        super((Class)path.getType(), path.getMetadata());
    }

    public QAbstractConfig(PathMetadata<?> metadata) {
        super(AbstractConfig.class, metadata);
    }

}

