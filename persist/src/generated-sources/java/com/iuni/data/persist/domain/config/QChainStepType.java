package com.iuni.data.persist.domain.config;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QChainStepType is a Querydsl query type for ChainStepType
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QChainStepType extends EntityPathBase<ChainStepType> {

    private static final long serialVersionUID = -1474971071;

    public static final QChainStepType chainStepType = new QChainStepType("chainStepType");

    public final QAbstractConfig _super = new QAbstractConfig(this);

    //inherited
    public final NumberPath<Integer> cancelFlag = _super.cancelFlag;

    public final ListPath<ChainStep, QChainStep> chainSteps = this.<ChainStep, QChainStep>createList("chainSteps", ChainStep.class, QChainStep.class, PathInits.DIRECT2);

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

    public QChainStepType(String variable) {
        super(ChainStepType.class, forVariable(variable));
    }

    @SuppressWarnings("all")
    public QChainStepType(Path<? extends ChainStepType> path) {
        super((Class)path.getType(), path.getMetadata());
    }

    public QChainStepType(PathMetadata<?> metadata) {
        super(ChainStepType.class, metadata);
    }

}

