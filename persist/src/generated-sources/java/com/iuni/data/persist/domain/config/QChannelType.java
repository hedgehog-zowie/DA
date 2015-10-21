package com.iuni.data.persist.domain.config;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QChannelType is a Querydsl query type for ChannelType
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QChannelType extends EntityPathBase<ChannelType> {

    private static final long serialVersionUID = -1752098409;

    public static final QChannelType channelType = new QChannelType("channelType");

    public final QAbstractConfig _super = new QAbstractConfig(this);

    //inherited
    public final NumberPath<Integer> cancelFlag = _super.cancelFlag;

    public final SetPath<Channel, QChannel> channelSet = this.<Channel, QChannel>createSet("channelSet", Channel.class, QChannel.class, PathInits.DIRECT2);

    public final StringPath code = createString("code");

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

    public QChannelType(String variable) {
        super(ChannelType.class, forVariable(variable));
    }

    @SuppressWarnings("all")
    public QChannelType(Path<? extends ChannelType> path) {
        super((Class)path.getType(), path.getMetadata());
    }

    public QChannelType(PathMetadata<?> metadata) {
        super(ChannelType.class, metadata);
    }

}

