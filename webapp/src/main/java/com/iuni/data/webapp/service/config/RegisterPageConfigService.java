package com.iuni.data.webapp.service.config;

import java.util.List;

import com.iuni.data.persist.domain.config.RegisterPage;
import com.iuni.data.webapp.common.PageVO;

public interface RegisterPageConfigService {
    
    
  //增加一条记录
    public void save(RegisterPage rp);
    
    //删除一条记录,逻辑删除，将status的值置为0
    public void delete(Long id);
    
    //修改一条记录
    public void update(RegisterPage rp);
    
    //查询所有的记录
    public List<RegisterPage> findAll();
    
    //分页查询所有记录
    public List<RegisterPage> findPagination(PageVO page);
    
    //通过Id，查询一条记录
    public RegisterPage loadById(Long id);
    
    //批量查询记录，通过IDs
    public List<RegisterPage> findAllByIds(Iterable<Long> rps);
    
    //批量更新用户，通过集合registerPages
    public void saveAll(Iterable<RegisterPage> registerPages);
    
  //使用QueryDsl查询记录
    public List<RegisterPage> findAllQueryDsl(RegisterPage rp,PageVO page);
}
