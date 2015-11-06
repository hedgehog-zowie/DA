package com.iuni.data.webapp.service.wares.impl;

import com.iuni.data.persist.mapper.wares.WareMapper;
import com.iuni.data.persist.model.wares.CategoryDto;
import com.iuni.data.persist.model.wares.SkuDto;
import com.iuni.data.persist.model.wares.WareDto;
import com.iuni.data.webapp.service.wares.WareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
@Service("wareService")
public class WareServiceImpl implements WareService {

    @Autowired
    private WareMapper wareMapper;

    @Override
    public List<CategoryDto> selectCategoryExceptPhone() {
        return wareMapper.selectCategoryExceptPhone();
    }

    @Override
    public List<WareDto> selectWareByCategory(String categoryId) {
        return wareMapper.selectWareByCategory(categoryId);
    }

    @Override
    public List<SkuDto> selectSkuByWare(String wareId) {
        return wareMapper.selectSkuByWare(wareId);
    }

}
