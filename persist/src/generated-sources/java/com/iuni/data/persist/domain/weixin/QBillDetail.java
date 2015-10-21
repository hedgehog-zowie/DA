package com.iuni.data.persist.domain.weixin;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QBillDetail is a Querydsl query type for BillDetail
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QBillDetail extends EntityPathBase<BillDetail> {

    private static final long serialVersionUID = 1730085406;

    public static final QBillDetail billDetail = new QBillDetail("billDetail");

    public final StringPath amount = createString("amount");

    public final StringPath bank = createString("bank");

    public final StringPath currency = createString("currency");

    public final StringPath device = createString("device");

    public final StringPath discount = createString("discount");

    public final StringPath fee = createString("fee");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath mchDatePak = createString("mchDatePak");

    public final StringPath mchId = createString("mchId");

    public final StringPath mchOrderSn = createString("mchOrderSn");

    public final StringPath mchRefundSn = createString("mchRefundSn");

    public final StringPath merchandise = createString("merchandise");

    public final StringPath publicAccount = createString("publicAccount");

    public final StringPath rate = createString("rate");

    public final StringPath refund = createString("refund");

    public final StringPath refundDiscount = createString("refundDiscount");

    public final StringPath refundStatus = createString("refundStatus");

    public final StringPath refundType = createString("refundType");

    public final StringPath subMchId = createString("subMchId");

    public final StringPath tradeDate = createString("tradeDate");

    public final StringPath tradeStatus = createString("tradeStatus");

    public final StringPath tradeType = createString("tradeType");

    public final StringPath userSign = createString("userSign");

    public final StringPath wxOrderSn = createString("wxOrderSn");

    public final StringPath wxRefundSn = createString("wxRefundSn");

    public QBillDetail(String variable) {
        super(BillDetail.class, forVariable(variable));
    }

    @SuppressWarnings("all")
    public QBillDetail(Path<? extends BillDetail> path) {
        super((Class)path.getType(), path.getMetadata());
    }

    public QBillDetail(PathMetadata<?> metadata) {
        super(BillDetail.class, metadata);
    }

}

