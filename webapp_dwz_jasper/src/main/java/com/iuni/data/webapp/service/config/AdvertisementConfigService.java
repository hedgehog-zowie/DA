package com.iuni.data.webapp.service.config;

import java.util.List;

import com.iuni.data.persist.domain.config.Advertisement;
import com.iuni.data.webapp.common.PageVO;

public interface AdvertisementConfigService {

    //增加一条记录
    public void save(Advertisement ad);

    //删除一条记录,逻辑删除，将status的值置为0
    public void delete(Long id);

    //修改一条记录
    public void update(Advertisement ad);

    //查询所有的记录
    public List<Advertisement> findAll();

    //分页查询所有记录
    public List<Advertisement> findPagination(PageVO page);

    //通过Id，查询一条记录
    public Advertisement loadById(Long id);

    //批量查询记录，通过IDs
    public List<Advertisement> findAllByIds(Iterable<Long> ids);

    //批量更新用户，通过集合advertisements
    public void saveAll(Iterable<Advertisement> advertisements);

    //查询所有CANCEL_FLAG = 0的记录
    public List<Advertisement> findAllByCancelFlag(Advertisement advertisement, PageVO page);

    //使用QueryDsl查询记录
    public List<Advertisement> findAllQueryDsl(Advertisement advertisement, PageVO page);
}
