package com.ynsh.modules.present.service;

import com.ynsh.common.core.service.CrudService;
import com.ynsh.modules.present.dao.PresentTypeDao;
import com.ynsh.modules.present.entity.PresentCustomer;
import com.ynsh.modules.present.entity.PresentType;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by syb on 18-1-22.
 */
@Service
public class PresentTypeService extends CrudService<PresentTypeDao,PresentType,Long> {

    List<PresentType> getValidPresents(PresentType presentType){
        return dao.getValidPresents(presentType);
    }

}
