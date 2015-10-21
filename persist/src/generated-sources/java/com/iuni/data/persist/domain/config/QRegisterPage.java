package com.iuni.data.persist.domain.config;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QRegisterPage is a Querydsl query type for RegisterPage
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QRegisterPage extends EntityPathBase<RegisterPage> {

    private static final long serialVersionUID = -97222568;

    public static final QRegisterPage registerPage = new QRegisterPage("registerPage");

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

    //inherited
    public final NumberPath<Integer> status = _super.status;

    //inherited
    public final StringPath updateBy = _super.updateBy;

    //inherited
    public final DatePath<java.util.Date> updateDate = _super.updateDate;

    public final StringPath url = createString("url");

    public QRegisterPage(String variable) {
        super(RegisterPage.class, forVariable(variable));
    }

    @SuppressWarnings("all")
    public QRegisterPage(Path<? extends RegisterPage> path) {
        super((Class)path.getType(), path.getMetadata());
    }

    public QRegisterPage(PathMetadata<?> metadata) {
        super(RegisterPage.class, metadata);
    }

}

