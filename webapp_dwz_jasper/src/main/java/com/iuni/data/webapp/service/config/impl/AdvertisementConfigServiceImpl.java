package com.iuni.data.webapp.service.config.impl;

import com.iuni.data.persist.domain.config.Advertisement;
import com.iuni.data.persist.domain.config.QAdvertisement;
import com.iuni.data.persist.repository.config.AdvertisementRepository;
import com.iuni.data.webapp.common.PageVO;
import com.iuni.data.webapp.service.config.AdvertisementConfigService;
import com.mysema.query.types.expr.BooleanExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class AdvertisementConfigServiceImpl implements AdvertisementConfigService{

    @Autowired
    private AdvertisementRepository advertisementRepository;
    
    @Override
    public void save(Advertisement ad) {
        advertisementRepository.save(ad);
    }

    @Override
    public void delete(Long id) {
        
    }

    @Override
    public void update(Advertisement ad) {
        advertisementRepository.save(ad);
    }

    @Override
    public List<Advertisement> findAll() {
        return (List<Advertisement>)advertisementRepository.findAll();
    }

    @Override
    public Advertisement loadById(Long id) {
        return (Advertisement)advertisementRepository.findOne(id);
    }

    @Override
    public List<Advertisement> findAllByIds(Iterable<Long> ids) {
        return (List<Advertisement>)advertisementRepository.findAll(ids);
    }

    @Override
    public void saveAll(Iterable<Advertisement> advertisements) {
        advertisementRepository.save(advertisements);
    }

    @Override
    public List<Advertisement> findPagination(PageVO page) {
        Pageable pageable = new PageRequest(page.getCurrentPage()-1,page.getPageSize());
        org.springframework.data.domain.Page<Advertisement> springPageData = advertisementRepository.findAll(pageable);
        List<Advertisement> advertisements = springPageData.getContent();
        page.setTotalPage(springPageData.getTotalPages());
        page.setTotalRecord(springPageData.getTotalElements());
       
        return advertisements;
    }

    @Override
    public List<Advertisement> findAllByCancelFlag(Advertisement advertisement,PageVO page) {
        advertisement.setCancelFlag(0);
        final Advertisement newAdvertisement = advertisement;
        Specification<Advertisement> spec = new Specification<Advertisement>() {
            public Predicate toPredicate(Root<Advertisement> root,
                    CriteriaQuery<?> query, CriteriaBuilder cb) {  
                if(newAdvertisement.getCancelFlag()!=null && !newAdvertisement.getCancelFlag().equals("")){
                    Predicate p = cb.equal(root.get("cancelFlag"),newAdvertisement.getCancelFlag());
                  //把Predicate应用到CriteriaQuery中去,因为还可以给CriteriaQuery添加其他的功能，比如排序、分组啥的  
                    query.where(p);
                }
                if(newAdvertisement.getStatus()!=null && !newAdvertisement.getStatus().equals("")){
                    Predicate p = cb.equal(root.get("status"),0);
                    query.where(p);
                }
                if(newAdvertisement.getName()!=null && !newAdvertisement.getName().equals("")){
                    Predicate p = cb.like(root.get("name").as(String.class), "%"+newAdvertisement.getName()+"%");
                    query.where(p);
                }
                if(newAdvertisement.getType()!=null && !newAdvertisement.getType().equals("")){
                    Predicate p = cb.like(root.get("type").as(String.class), "%"+newAdvertisement.getType()+"%");
                    query.where(p);
                }
              //添加排序的功能 
                query.orderBy(cb.asc(root.get("id"))); 
                
                return query.getRestriction();
            }  
        };

        Integer oldCurrentPage = page.getCurrentPage();
        Pageable pageable = new PageRequest(page.getCurrentPage()-1,page.getPageSize());
        org.springframework.data.domain.Page<Advertisement> springPageData = advertisementRepository.findAll(spec, pageable);
        page.setTotalPage(springPageData.getTotalPages());
        page.setTotalRecord(springPageData.getTotalElements());
        List<Advertisement> advertisements = springPageData.getContent();
        
        if(oldCurrentPage > page.getTotalPage()){
            page.setCurrentPage(oldCurrentPage - 1);
            Pageable pageableNew = new PageRequest(page.getCurrentPage()-1,page.getPageSize());
            org.springframework.data.domain.Page<Advertisement> springPageDataNew = advertisementRepository.findAll(spec, pageableNew);
            page.setTotalPage(springPageDataNew.getTotalPages());
            page.setTotalRecord(springPageDataNew.getTotalElements());
            List<Advertisement> newAdvertisements = springPageDataNew.getContent();
            return newAdvertisements;
        }
        
        return advertisements;
    }

    @Override
    public List<Advertisement> findAllQueryDsl(Advertisement advertisement,PageVO page) {
        // TODO Auto-generated method stub
        QAdvertisement qAdvertisement = QAdvertisement.advertisement;
        
        advertisement.setCancelFlag(0);
        
        BooleanExpression expression = null;
        
        if(advertisement.getCancelFlag()!=null && !advertisement.getCancelFlag().equals("")){
            BooleanExpression cancelFlagExpression = qAdvertisement.cancelFlag.eq(advertisement.getCancelFlag());
            expression = (expression == null)?cancelFlagExpression:expression.and(cancelFlagExpression);
        }
        if(advertisement.getStatus()!=null && !advertisement.getStatus().equals("")){
            BooleanExpression statusExpression = qAdvertisement.status.eq(advertisement.getStatus());
            expression = (expression == null)?statusExpression:expression.and(statusExpression);
        }
        if(advertisement.getName()!=null && !advertisement.getName().isEmpty()){
            BooleanExpression nameExpression = qAdvertisement.name.like("%"+ advertisement.getName()+"%");
            expression = (expression == null)?nameExpression:expression.and(nameExpression);
        }
        if(advertisement.getType()!=null && !advertisement.getType().isEmpty()){
            BooleanExpression typeExpression = qAdvertisement.type.like(advertisement.getType());
            expression = (expression == null)?typeExpression:expression.and(typeExpression);
        }
        
        Sort sort = new Sort(Sort.Direction.ASC,new String[]{"id"});
        
        //处理最后一页只有一条记录的情况，如果这条记录被删除，则通过判断进入最后一页的前一页，显示前一页的内容
        List<Advertisement> advertisements = null;
        Integer oldCurrentPage = page.getCurrentPage();
        do{
            Pageable pageable = new PageRequest(page.getCurrentPage()-1,page.getPageSize(),sort);
            Page<Advertisement> datas = advertisementRepository.findAll(expression,pageable);
            advertisements = datas.getContent();
            page.setTotalPage(datas.getTotalPages());
            page.setTotalRecord(datas.getTotalElements());
            page.setCurrentPage(oldCurrentPage);
            oldCurrentPage--;
        }while(oldCurrentPage > page.getTotalPage()-1);
        
        if(advertisements == null){
            advertisements = new ArrayList<Advertisement>();
        }
        
        return advertisements;
    }

    
    
}
