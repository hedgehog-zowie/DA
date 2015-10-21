package com.iuni.data.persist.domain.config;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QUserDefinedReport is a Querydsl query type for UserDefinedReport
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QUserDefinedReport extends EntityPathBase<UserDefinedReport> {

    private static final long serialVersionUID = -723871284;

    public static final QUserDefinedReport userDefinedReport = new QUserDefinedReport("userDefinedReport");

    public final QAbstractConfig _super = new QAbstractConfig(this);

    //inherited
    public final NumberPath<Integer> cancelFlag = _super.cancelFlag;

    //inherited
    public final StringPath createBy = _super.createBy;

    //inherited
    public final DatePath<java.util.Date> createDate = _super.createDate;

    //inherited
    public final StringPath desc = _super.desc;

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final StringPath name = createString("name");

    public final StringPath path = createString("path");

    //inherited
    public final NumberPath<Integer> status = _super.status;

    //inherited
    public final StringPath updateBy = _super.updateBy;

    //inherited
    public final DatePath<java.util.Date> updateDate = _super.updateDate;

    public final StringPath user = createString("user");

    public QUserDefinedReport(String variable) {
        super(UserDefinedReport.class, forVariable(variable));
    }

    @SuppressWarnings("all")
    public QUserDefinedReport(Path<? extends UserDefinedReport> path) {
        super((Class)path.getType(), path.getMetadata());
    }

    public QUserDefinedReport(PathMetadata<?> metadata) {
        super(UserDefinedReport.class, metadata);
    }

}

