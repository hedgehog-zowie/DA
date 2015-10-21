package com.iuni.data.persist.domain.taobao;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QTradeRecord is a Querydsl query type for TradeRecord
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QTradeRecord extends EntityPathBase<TradeRecord> {

    private static final long serialVersionUID = 2108128635;

    public static final QTradeRecord tradeRecord = new QTradeRecord("tradeRecord");

    public final StringPath alipayOrderNo = createString("alipayOrderNo");

    public final StringPath createTime = createString("createTime");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath inOutType = createString("inOutType");

    public final StringPath merchantOrderNo = createString("merchantOrderNo");

    public final StringPath modifiedTime = createString("modifiedTime");

    public final StringPath oppositeLogonId = createString("oppositeLogonId");

    public final StringPath oppositeName = createString("oppositeName");

    public final StringPath oppositeUserId = createString("oppositeUserId");

    public final StringPath orderFrom = createString("orderFrom");

    public final StringPath orderStatus = createString("orderStatus");

    public final StringPath orderTitle = createString("orderTitle");

    public final StringPath orderType = createString("orderType");

    public final StringPath ownerLogonId = createString("ownerLogonId");

    public final StringPath ownerName = createString("ownerName");

    public final StringPath ownerUserId = createString("ownerUserId");

    public final StringPath serviceCharge = createString("serviceCharge");

    public final StringPath totalAmount = createString("totalAmount");

    public QTradeRecord(String variable) {
        super(TradeRecord.class, forVariable(variable));
    }

    @SuppressWarnings("all")
    public QTradeRecord(Path<? extends TradeRecord> path) {
        super((Class)path.getType(), path.getMetadata());
    }

    public QTradeRecord(PathMetadata<?> metadata) {
        super(TradeRecord.class, metadata);
    }

}

