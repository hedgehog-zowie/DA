package com.iuni.data.webapp.service.config.impl;

import com.iuni.data.persist.domain.config.FlowSource;
import com.iuni.data.persist.domain.config.FlowSourceType;
import com.iuni.data.persist.repository.config.FlowSourceRepository;
import com.iuni.data.persist.repository.config.FlowSourceTypeRepository;
import com.iuni.data.webapp.common.PageVO;
import com.iuni.data.webapp.service.config.FlowSourceConfigService;
import com.iuni.data.webapp.sso.service.AccountService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("flowSourceConfigService")
public class FlowSourceConfigServiceImpl implements FlowSourceConfigService {
	
	private static final Logger logger = LoggerFactory.getLogger(FlowSourceConfigService.class);
	
	@Autowired
	private FlowSourceRepository flowSourceRepository;
	
	@Autowired
	private FlowSourceTypeRepository flowSourceTypeRepository;
	
	@Autowired
	private AccountService accountService;
	
	@Override
	public List<FlowSource> listFlowSources(final FlowSource flowSource,PageVO pageVO) {
		List<FlowSource> flowSourceList;
		Pageable Pageable = new PageRequest(pageVO.getCurrentPage()-1 ,pageVO.getPageSize());
		
		Specification<FlowSource> specification = new Specification<FlowSource>(){

			@Override
			public Predicate toPredicate(Root<FlowSource> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				if (StringUtils.isNotEmpty(flowSource.getName())) {
					Predicate p1 = cb.like(root.get("name").as(String.class),"%"+ flowSource.getName()+"%");
					query.where(p1);
				}
				if (StringUtils.isNotEmpty(flowSource.getUrl())) {
					//Predicate p1 = cb.equal(root.get("url").as(String.class),flowSource.getUrl());
					Predicate p2 = cb.like(root.get("url").as(String.class),"%"+ flowSource.getUrl()+"%");
					query.where(p2);
				}
				
				return query.getRestriction();
			}
			
		};
		
		Page<FlowSource> flouwSourcePage = flowSourceRepository.findAll(specification,Pageable);
		pageVO.setTotalPage(flouwSourcePage.getTotalPages());
		pageVO.setTotalRecord(flouwSourcePage.getTotalElements());
		flowSourceList =  flouwSourcePage.getContent();
		return flowSourceList;
	}

	@Override
	public FlowSource editFlowSource(Long id) {
		return flowSourceRepository.findOne(id);
	}

	@Override
	public boolean updateFlowSource(FlowSource fs) {
		try{
			FlowSource flowSource = flowSourceRepository.findOne(fs.getId());
			flowSource.setName(fs.getName());
			flowSource.setUrl(fs.getUrl());
			FlowSourceType flowSourceType = flowSourceTypeRepository.findOne(fs.getFlowSourceType().getId());
			flowSource.setFlowSourceType(flowSourceType);
			
			flowSource.setUpdateBy(accountService.getCurrentUser().getLoginName());
			flowSource.setUpdateDate(new Date());
			
			flowSourceRepository.save(flowSource);
			return true;
		}catch (Exception e ){
			 logger.error(e.getLocalizedMessage());
	         return false;
		}
	}

	@Override
	public List<FlowSourceType> findFlowSourceTypes() {
		return flowSourceTypeRepository.findAll();
	}
	
	@Override
	public boolean addFlowSource(FlowSource flowSource) {
		try{
			flowSource.setBasicInfoForCreate(accountService.getCurrentUser().getLoginName());
			flowSourceRepository.save(flowSource);
			return true;
		}catch (Exception e ){
			 logger.error(e.getLocalizedMessage());
	         return false;
		}
	}



}
