package com.iuni.data.persist.domain.config;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QHoliday is a Querydsl query type for Holiday
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QHoliday extends EntityPathBase<Holiday> {

    private static final long serialVersionUID = 4810770;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QHoliday holiday = new QHoliday("holiday");

    public final QAbstractConfig _super = new QAbstractConfig(this);

    //inherited
    public final NumberPath<Integer> cancelFlag = _super.cancelFlag;

    //inherited
    public final StringPath createBy = _super.createBy;

    //inherited
    public final DatePath<java.util.Date> createDate = _super.createDate;

    //inherited
    public final StringPath desc = _super.desc;

    public final DatePath<java.util.Date> endDate = createDate("endDate", java.util.Date.class);

    public final QHolidayType holidayType;

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final StringPath name = createString("name");

    public final DatePath<java.util.Date> startDate = createDate("startDate", java.util.Date.class);

    //inherited
    public final NumberPath<Integer> status = _super.status;

    //inherited
    public final StringPath updateBy = _super.updateBy;

    //inherited
    public final DatePath<java.util.Date> updateDate = _super.updateDate;

    public final NumberPath<Integer> yr = createNumber("yr", Integer.class);

    public QHoliday(String variable) {
        this(Holiday.class, forVariable(variable), INITS);
    }

    @SuppressWarnings("all")
    public QHoliday(Path<? extends Holiday> path) {
        this((Class)path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QHoliday(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QHoliday(PathMetadata<?> metadata, PathInits inits) {
        this(Holiday.class, metadata, inits);
    }

    public QHoliday(Class<? extends Holiday> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.holidayType = inits.isInitialized("holidayType") ? new QHolidayType(forProperty("holidayType")) : null;
    }

}

