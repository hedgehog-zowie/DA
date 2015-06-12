package com.iuni.data.webapp.service.config.impl;

import com.iuni.data.common.ConfigConstants;
import com.iuni.data.persist.domain.config.*;
import com.iuni.data.persist.repository.config.ChainRepository;
import com.iuni.data.persist.repository.config.ChainStepRepository;
import com.iuni.data.webapp.common.PageVO;
import com.iuni.data.webapp.service.config.ChainService;
import com.iuni.data.webapp.sso.service.AccountService;
import com.mysema.query.types.expr.BooleanExpression;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
@Service
public class ChainServiceImpl implements ChainService {

    private static final Logger logger = LoggerFactory.getLogger(ChainService.class);

    @Autowired
    private AccountService accountService;
    @Autowired
    private ChainRepository chainRepository;
    @Autowired
    private ChainStepRepository chainStepRepository;

    @Override
    public Chain getChainById(Long id) {
        Chain chain = null;
        try {
            chain = chainRepository.findOne(id);
        } catch (Exception e) {
            logger.error("get chain by id error. msg:" + e.getLocalizedMessage());
        }
        if (chain == null)
            chain = new Chain();
        return chain;
    }

    @Override
    public List<Chain> listChain(PageVO page, final Chain chain) {
        List<Chain> chainList = null;
        QChain qChain = QChain.chain;
        BooleanExpression booleanExpression = null;

        if (chain.getCancelFlag() != null) {
            BooleanExpression cancelBooleanExpression = qChain.cancelFlag.eq(chain.getCancelFlag());
            booleanExpression = (booleanExpression == null ? cancelBooleanExpression : booleanExpression.and(cancelBooleanExpression));
        }
        if (StringUtils.isNotBlank(chain.getName())) {
            BooleanExpression nameBooleanExpression = qChain.name.like("%" + chain.getName() + "%");
            booleanExpression = (booleanExpression == null ? nameBooleanExpression : booleanExpression.and(nameBooleanExpression));
        }
        try {
            Sort sort = new Sort(Sort.Direction.ASC, new String[]{"name"});
            Pageable pageable = new PageRequest(page.getCurrentPage() - 1, page.getPageSize(), sort);
            Page resultPage;
            if (booleanExpression == null)
                resultPage = chainRepository.findAll(pageable);
            else
                resultPage = chainRepository.findAll(booleanExpression, pageable);
            page.setPage(resultPage);
            chainList = resultPage.getContent();
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
        }
        if (chainList == null)
            chainList = new ArrayList<>();
        return chainList;
    }

    @Override
    public boolean addChain(Chain chain) {
        chain.setBasicInfoForCreate(accountService.getCurrentUser().getLoginName());
        return saveChain(chain);
    }

    @Override
    public boolean updateChain(Chain chain) {
        Chain oldChain = getChainById(chain.getId());
        if (oldChain != null) {
            chain.setCreateDate(oldChain.getCreateDate());
            chain.setCreateBy(oldChain.getCreateBy());
            chain.setBasicInfoForUpdate(accountService.getCurrentUser().getLoginName());
        }
        return saveChain(chain);
    }

    public boolean saveChain(Chain chain) {
        try {
            chainRepository.save(chain);
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
            return false;
        }
        return true;
    }

    public boolean saveChain(List<Chain> chainList) {
        try {
            chainRepository.save(chainList);
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteChain(String ids) {
        List<Chain> ChainList = new ArrayList<>();
        try {
            String[] idArray = ids.split(",");
            for (String id : idArray) {
                if (!StringUtils.isNumeric(id))
                    continue;
                Chain chain = chainRepository.findOne(Long.parseLong(id));
                if (chain != null) {
                    // set cancel flag to 1, logical delete it.
                    chain.setBasicInfoForCancel(accountService.getCurrentUser().getLoginName());
                    ChainList.add(chain);
                }
            }
        } catch (Exception e) {
            logger.error("logic delte chain step error. msg: {}", e.getLocalizedMessage());
            return false;
        }
        return saveChain(ChainList);
    }

    @Override
    public boolean enableOrDisableChain(String ids, Integer status) {
        List<Chain> ChainList = new ArrayList<>();
        try {
            String[] idArray = ids.split(",");
            for (String id : idArray) {
                if (!StringUtils.isNumeric(id))
                    continue;
                Chain chain = chainRepository.findOne(Long.parseLong(id));
                if (chain != null) {
                    // set status to status, logical delete it.
                    chain.setStatus(status);
                    ChainList.add(chain);
                }
            }
        } catch (Exception e) {
            logger.error("logic delte chain step error. msg: {}", e.getLocalizedMessage());
            return false;
        }
        return saveChain(ChainList);
    }

    @Override
    public ChainStep getChainStepById(Long id) {
        ChainStep chainStep = null;
        try {
            chainStep = chainStepRepository.findOne(id);
        } catch (Exception e) {
            logger.error("get chain step by id error. msg:" + e.getLocalizedMessage());
        }
        if (chainStep == null)
            chainStep = new ChainStep();
        return chainStep;
    }

    @Override
    public List<ChainStep> listChainStep(PageVO page, final Chain chain, int type) {
        ChainStepType ChainStepType = new ChainStepType();
        // step type
        ChainStepType.setId(type);
        ChainStep chainStep = new ChainStep();
        chainStep.setChainStepType(ChainStepType);
        // set chain
        chainStep.setChain(chain);
        // set cancel flag 0
        chainStep.setCancelFlag(ConfigConstants.LOGICAL_CANCEL_FLAG_NOT_CANCEL);
        return listChainStep(page, chainStep);
    }

    private List<ChainStep> listChainStep(PageVO page, final ChainStep chainStep) {
        List<ChainStep> ChainStepList = null;
        QChainStep qChainStep = QChainStep.chainStep;
        BooleanExpression booleanExpression = null;
        if (chainStep.getCancelFlag() != null) {
            BooleanExpression cancelBooleanExpression = qChainStep.cancelFlag.eq(chainStep.getCancelFlag());
            booleanExpression = cancelBooleanExpression;
        }
        if (chainStep.getChainStepType() != null && chainStep.getChainStepType().getId() != 0) {
            BooleanExpression typeBooleanExpression = qChainStep.chainStepType.id.eq(chainStep.getChainStepType().getId());
            booleanExpression = (booleanExpression == null ? typeBooleanExpression : booleanExpression.and(typeBooleanExpression));
        }
        if (chainStep.getChain() != null && chainStep.getChain().getId() != 0) {
            BooleanExpression chainBooleanExpression = qChainStep.chain.id.eq(chainStep.getChain().getId());
            booleanExpression = (booleanExpression == null ? chainBooleanExpression : booleanExpression.and(chainBooleanExpression));
        }
        try {
            Sort sort = new Sort(Sort.Direction.ASC, new String[]{"stepIndex"});
            Pageable pageable = new PageRequest(page.getCurrentPage() - 1, page.getPageSize(), sort);
            Page resultPage;
            if (booleanExpression == null)
                resultPage = chainStepRepository.findAll(pageable);
            else
                resultPage = chainStepRepository.findAll(booleanExpression, pageable);
            page.setPage(resultPage);
            ChainStepList = resultPage.getContent();
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
        }
        if (ChainStepList == null)
            ChainStepList = new ArrayList<>();
        return ChainStepList;
    }

    @Override
    public boolean addChainStep(ChainStep chainStep) {
        chainStep.setBasicInfoForCreate(accountService.getCurrentUser().getLoginName());
        return saveChainStep(chainStep);
    }

    @Override
    public boolean updateChainStep(ChainStep chainStep) {
        ChainStep oldStep = getChainStepById(chainStep.getId());
        if (oldStep != null) {
            chainStep.setCreateDate(oldStep.getCreateDate());
            chainStep.setCreateBy(oldStep.getCreateBy());
            chainStep.setBasicInfoForUpdate(accountService.getCurrentUser().getLoginName());
        }
        return saveChainStep(chainStep);
    }

    @Override
    public boolean deleteChainStep(String ids) {
        List<ChainStep> RtagList = new ArrayList<>();
        try {
            String[] idArray = ids.split(",");
            for (String id : idArray) {
                if (!StringUtils.isNumeric(id))
                    continue;
                ChainStep chainStep = chainStepRepository.findOne(Long.parseLong(id));
                if (chainStep != null) {
                    // set cancel flag to 1, logical delete it.
                    chainStep.setBasicInfoForCancel(accountService.getCurrentUser().getLoginName());
                    RtagList.add(chainStep);
                }
            }
        } catch (Exception e) {
            logger.error("logic delete chain step error. msg: {}", e.getLocalizedMessage());
            return false;
        }
        return saveChainStep(RtagList);
    }

    private boolean saveChainStep(ChainStep chainStep) {
        try {
            chainStepRepository.save(chainStep);
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
            return false;
        }
        return true;
    }

    private boolean saveChainStep(List<ChainStep> chainStepList) {
        try {
            chainStepRepository.save(chainStepList);
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
            return false;
        }
        return true;
    }

}
