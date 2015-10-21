package com.iuni.data.persist.domain.config;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QChainStep is a Querydsl query type for ChainStep
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QChainStep extends EntityPathBase<ChainStep> {

    private static final long serialVersionUID = 296960615;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QChainStep chainStep = new QChainStep("chainStep");

    public final QAbstractConfig _super = new QAbstractConfig(this);

    //inherited
    public final NumberPath<Integer> cancelFlag = _super.cancelFlag;

    public final QChain chain;

    public final QChainStepType chainStepType;

    //inherited
    public final StringPath createBy = _super.createBy;

    //inherited
    public final DatePath<java.util.Date> createDate = _super.createDate;

    //inherited
    public final StringPath desc = _super.desc;

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final StringPath name = createString("name");

    public final StringPath orderType = createString("orderType");

    public final StringPath pageName = createString("pageName");

    public final StringPath pageUrl = createString("pageUrl");

    public final QRTag rTag;

    //inherited
    public final NumberPath<Integer> status = _super.status;

    public final NumberPath<Integer> stepIndex = createNumber("stepIndex", Integer.class);

    //inherited
    public final StringPath updateBy = _super.updateBy;

    //inherited
    public final DatePath<java.util.Date> updateDate = _super.updateDate;

    public QChainStep(String variable) {
        this(ChainStep.class, forVariable(variable), INITS);
    }

    @SuppressWarnings("all")
    public QChainStep(Path<? extends ChainStep> path) {
        this((Class)path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QChainStep(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QChainStep(PathMetadata<?> metadata, PathInits inits) {
        this(ChainStep.class, metadata, inits);
    }

    public QChainStep(Class<? extends ChainStep> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.chain = inits.isInitialized("chain") ? new QChain(forProperty("chain")) : null;
        this.chainStepType = inits.isInitialized("chainStepType") ? new QChainStepType(forProperty("chainStepType")) : null;
        this.rTag = inits.isInitialized("rTag") ? new QRTag(forProperty("rTag"), inits.get("rTag")) : null;
    }

}

