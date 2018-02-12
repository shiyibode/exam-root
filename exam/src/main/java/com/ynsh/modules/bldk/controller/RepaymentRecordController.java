package com.ynsh.modules.bldk.controller;

import com.ynsh.common.core.web.controller.CrudController;
import com.ynsh.modules.bldk.entity.RepaymentRecord;
import com.ynsh.modules.bldk.service.RepaymentRecordService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by syb on 18-1-10.
 */
@Controller
@RequestMapping("${bldkPath}/repaymentrecord")
public class RepaymentRecordController extends CrudController<RepaymentRecordService,RepaymentRecord,Long>{




}
