package com.iuni.data.webapp.service.config.impl;

import java.util.ArrayList;
import java.util.List;

import com.iuni.data.persist.domain.config.QRegisterPage;
import com.iuni.data.persist.domain.config.RegisterPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.iuni.data.webapp.common.PageVO;
import com.iuni.data.persist.repository.config.RegisterPageConfigRepository;
import com.iuni.data.webapp.service.config.RegisterPageConfigService;
import com.mysema.query.types.expr.BooleanExpression;

@Repository
@Transactional
public class RegisterPageConfigServiceImpl implements RegisterPageConfigService {

    @Autowired
    private RegisterPageConfigRepository registerPageConfigRepository;
    
    @Override
    public void save(RegisterPage rp) {
        registerPageConfigRepository.save(rp);
    }

    @Override
    public void delete(Long id) {
        // TODO Auto-generated method stub
    }

    @Override
    public void update(RegisterPage rp) {
        registerPageConfigRepository.save(rp);
    }

    @Override
    public List<RegisterPage> findAll() {
        return registerPageConfigRepository.findAll();
    }

    @Override
    public List<RegisterPage> findPagination(PageVO page) {
        Pageable pageable = new PageRequest(page.getCurrentPage()-1,page.getPageSize());
        org.springframework.data.domain.Page<RegisterPage> springPageData = registerPageConfigRepository.findAll(pageable);
        List<RegisterPage> registerPages = springPageData.getContent();
        page.setTotalPage(springPageData.getTotalPages());
        page.setTotalRecord((long)springPageData.getTotalElements());
       
        return registerPages;
    }

    @Override
    public RegisterPage loadById(Long id) {
        return (RegisterPage)registerPageConfigRepository.findOne(id);
    }

    @Override
    public List<RegisterPage> findAllByIds(Iterable<Long> rps) {
        return (List<RegisterPage>)registerPageConfigRepository.findAll(rps);
    }

    @Override
    public void saveAll(Iterable<RegisterPage> registerPages) {
        registerPageConfigRepository.save(registerPages);
    }

    @Override
    public List<RegisterPage> findAllQueryDsl(RegisterPage rp, PageVO page) {
        // TODO Auto-generated method stub
        QRegisterPage qIuniDaRegisterPage = QRegisterPage.registerPage;
        rp = new RegisterPage();
        rp.setCancelFlag(0);
        BooleanExpression expression = null;
        if(rp.getCancelFlag()!=null && !rp.getCancelFlag().equals("")){
            BooleanExpression cancelFlagExpression = qIuniDaRegisterPage.cancelFlag.eq(rp.getCancelFlag());
            expression = (expression == null)?cancelFlagExpression:expression.and(cancelFlagExpression);
        }
        
        Sort sort = new Sort(Sort.Direction.ASC,new String[]{"id"});
        List<RegisterPage> registerPages = null;
        Integer oldCurrentPage = page.getCurrentPage();
        
        do {
            Pageable pageable = new PageRequest(page.getCurrentPage()-1, page.getPageSize(), sort);
            Page<RegisterPage> datas = registerPageConfigRepository.findAll(expression, pageable);
            registerPages = (List<RegisterPage>) datas.getContent();
            page.setTotalPage(datas.getTotalPages());
            page.setTotalRecord(datas.getTotalElements());
            page.setCurrentPage(oldCurrentPage);
            oldCurrentPage--;
        } while (oldCurrentPage>page.getTotalPage()-1);
        
        if(registerPages == null){
            registerPages = new ArrayList<RegisterPage>();
        }
        return registerPages;
    }

    

}
