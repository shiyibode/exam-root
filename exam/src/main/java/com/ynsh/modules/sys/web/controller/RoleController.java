package com.ynsh.modules.sys.web.controller;

import com.ynsh.common.core.web.controller.CrudController;
import com.ynsh.modules.sys.entity.Role;
import com.ynsh.modules.sys.service.RoleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by chenjianjun on 2016/10/14.
 */
@Controller
@RequestMapping("${sysPath}/role")
public class RoleController extends CrudController<RoleService, Role, Long> {

}
