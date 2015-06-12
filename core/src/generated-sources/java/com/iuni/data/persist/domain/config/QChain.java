package com.iuni.data.persist.domain.config;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QChain is a Querydsl query type for Chain
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QChain extends EntityPathBase<Chain> {

    private static final long serialVersionUID = -1502036741;

    public static final QChain chain = new QChain("chain");

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

    public final StringPath product = createString("product");

    //inherited
    public final NumberPath<Integer> status = _super.status;

    //inherited
    public final StringPath updateBy = _super.updateBy;

    //inherited
    public final DatePath<java.util.Date> updateDate = _super.updateDate;

    public QChain(String variable) {
        super(Chain.class, forVariable(variable));
    }

    @SuppressWarnings("all")
    public QChain(Path<? extends Chain> path) {
        super((Class)path.getType(), path.getMetadata());
    }

    public QChain(PathMetadata<?> metadata) {
        super(Chain.class, metadata);
    }

}

