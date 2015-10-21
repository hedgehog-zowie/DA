package com.iuni.data.persist.domain.config;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QFlowSourceType is a Querydsl query type for FlowSourceType
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QFlowSourceType extends EntityPathBase<FlowSourceType> {

    private static final long serialVersionUID = -1065411383;

    public static final QFlowSourceType flowSourceType = new QFlowSourceType("flowSourceType");

    public final QAbstractConfig _super = new QAbstractConfig(this);

    //inherited
    public final NumberPath<Integer> cancelFlag = _super.cancelFlag;

    //inherited
    public final StringPath createBy = _super.createBy;

    //inherited
    public final DatePath<java.util.Date> createDate = _super.createDate;

    //inherited
    public final StringPath desc = _super.desc;

    public final SetPath<FlowSource, QFlowSource> flowSources = this.<FlowSource, QFlowSource>createSet("flowSources", FlowSource.class, QFlowSource.class, PathInits.DIRECT2);

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final StringPath name = createString("name");

    //inherited
    public final NumberPath<Integer> status = _super.status;

    //inherited
    public final StringPath updateBy = _super.updateBy;

    //inherited
    public final DatePath<java.util.Date> updateDate = _super.updateDate;

    public QFlowSourceType(String variable) {
        super(FlowSourceType.class, forVariable(variable));
    }

    @SuppressWarnings("all")
    public QFlowSourceType(Path<? extends FlowSourceType> path) {
        super((Class)path.getType(), path.getMetadata());
    }

    public QFlowSourceType(PathMetadata<?> metadata) {
        super(FlowSourceType.class, metadata);
    }

}

