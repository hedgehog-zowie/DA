package com.iuni.data.webapp.service.config;

import com.iuni.data.persist.domain.config.Chain;
import com.iuni.data.persist.domain.config.ChainStep;
import com.iuni.data.webapp.common.PageVO;

import java.util.List;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public interface ChainService {

    Chain getChainById(Long id);

    /**
     * list all setBasicInfoForCreate chains by page
     * @param chain
     * @return
     */
    List<Chain> listChain(PageVO page, final Chain chain);

    boolean addChain(Chain chain);

    boolean updateChain(Chain chain);

    /**
     * delete chains by id string, ids like "1,2,3,4"
     * @param ids
     * @return
     */
    boolean deleteChain(String ids);

    /**
     * disable chains by id string, ids like "1,2,3,4"
     * @param ids
     * @return
     */
    boolean enableOrDisableChain(String ids, Integer status);

    ChainStep getChainStepById(Long id);

    List<ChainStep> listChainStep(PageVO page, final Chain chain, int type);

    boolean addChainStep(ChainStep chainStep);

    boolean updateChainStep(ChainStep chainStep);

    /**
     * delete steps by id string, ids like "1,2,3,4"
     * @param ids
     * @return
     */
    boolean deleteChainStep(String ids);

}
