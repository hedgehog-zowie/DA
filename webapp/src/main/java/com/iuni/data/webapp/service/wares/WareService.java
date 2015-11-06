package com.iuni.data.webapp.service.wares;

import com.iuni.data.persist.model.wares.CategoryDto;
import com.iuni.data.persist.model.wares.SkuDto;
import com.iuni.data.persist.model.wares.WareDto;

import java.util.List;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public interface WareService {

    /**
     * 查询除手机外的所有商品类型
     * @return
     */
    List<CategoryDto> selectCategoryExceptPhone();

    /**
     * 通过类型查询商品
     * @param categoryId
     * @return
     */
    List<WareDto> selectWareByCategory(String categoryId);

    /**
     * 通过商品查询型号
     * @param wareId
     * @return
     */
    List<SkuDto> selectSkuByWare(String wareId);

}
