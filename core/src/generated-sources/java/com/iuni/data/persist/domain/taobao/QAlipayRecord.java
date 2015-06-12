package com.iuni.data.persist.domain.taobao;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QAlipayRecord is a Querydsl query type for AlipayRecord
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QAlipayRecord extends EntityPathBase<AlipayRecord> {

    private static final long serialVersionUID = 209086805;

    public static final QAlipayRecord alipayRecord = new QAlipayRecord("alipayRecord");

    public final StringPath alipayOrderNo = createString("alipayOrderNo");

    public final StringPath balance = createString("balance");

    public final StringPath businessType = createString("businessType");

    public final StringPath createTime = createString("createTime");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath inAmount = createString("inAmount");

    public final StringPath memo = createString("memo");

    public final StringPath merchantOrderNo = createString("merchantOrderNo");

    public final StringPath optUserId = createString("optUserId");

    public final StringPath outAmount = createString("outAmount");

    public final StringPath selfUserId = createString("selfUserId");

    public final StringPath type = createString("type");

    public QAlipayRecord(String variable) {
        super(AlipayRecord.class, forVariable(variable));
    }

    @SuppressWarnings("all")
    public QAlipayRecord(Path<? extends AlipayRecord> path) {
        super((Class)path.getType(), path.getMetadata());
    }

    public QAlipayRecord(PathMetadata<?> metadata) {
        super(AlipayRecord.class, metadata);
    }

}

