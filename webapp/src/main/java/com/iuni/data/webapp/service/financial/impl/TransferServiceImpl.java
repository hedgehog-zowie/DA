package com.iuni.data.webapp.service.financial.impl;

import com.iuni.data.persist.mapper.financial.TransferMapper;
import com.iuni.data.persist.model.financial.TransferDetailsQueryDto;
import com.iuni.data.persist.model.financial.TransferDetailsTableDto;
import com.iuni.data.webapp.service.financial.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
@Service("transferService")
public class TransferServiceImpl implements TransferService {

    @Autowired
    private TransferMapper transferMapper;

    @Override
    public List<TransferDetailsTableDto> selectTransferDetails(TransferDetailsQueryDto queryDto) {
        return transferMapper.selectTransferDetails(queryDto);
    }

}
