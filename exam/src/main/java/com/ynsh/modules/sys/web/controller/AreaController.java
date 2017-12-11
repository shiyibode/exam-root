package com.ynsh.modules.sys.web.controller;

import com.ynsh.common.core.web.controller.TreeController;
import com.ynsh.modules.sys.entity.Area;
import com.ynsh.modules.sys.service.AreaService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by chenjianjun on 16/8/4.
 */
@Controller
@RequestMapping("${sysPath}/area")
public class AreaController extends TreeController<AreaService, Area, Long> {

}
