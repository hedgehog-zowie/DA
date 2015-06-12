package com.iuni.data.persist.domain.config;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QHolidayType is a Querydsl query type for HolidayType
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QHolidayType extends EntityPathBase<HolidayType> {

    private static final long serialVersionUID = 1853559404;

    public static final QHolidayType holidayType = new QHolidayType("holidayType");

    public final QAbstractConfig _super = new QAbstractConfig(this);

    //inherited
    public final NumberPath<Integer> cancelFlag = _super.cancelFlag;

    //inherited
    public final StringPath createBy = _super.createBy;

    //inherited
    public final DatePath<java.util.Date> createDate = _super.createDate;

    //inherited
    public final StringPath desc = _super.desc;

    public final ListPath<Holiday, QHoliday> holidays = this.<Holiday, QHoliday>createList("holidays", Holiday.class, QHoliday.class, PathInits.DIRECT2);

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final StringPath name = createString("name");

    //inherited
    public final NumberPath<Integer> status = _super.status;

    //inherited
    public final StringPath updateBy = _super.updateBy;

    //inherited
    public final DatePath<java.util.Date> updateDate = _super.updateDate;

    public QHolidayType(String variable) {
        super(HolidayType.class, forVariable(variable));
    }

    @SuppressWarnings("all")
    public QHolidayType(Path<? extends HolidayType> path) {
        super((Class)path.getType(), path.getMetadata());
    }

    public QHolidayType(PathMetadata<?> metadata) {
        super(HolidayType.class, metadata);
    }

}

