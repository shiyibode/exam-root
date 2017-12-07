package com.ynsh.common.core.web.controller;

import com.ynsh.common.core.beanvalidator.BeanValidators;
import com.ynsh.common.core.mapper.JsonMapper;
import com.ynsh.common.core.service.ServiceException;
import com.ynsh.common.core.web.client.ResponseJson;
import com.ynsh.common.utils.DateUtils;
import com.ynsh.common.utils.StringUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.beans.PropertyEditorSupport;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * 控制器支持类
 */
public abstract class BaseController {

	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 系统管理模块基础路径
	 */
	@Value("${sysPath}")
	protected String sysPath;
	
	/**
	 * 前端基础路径
	 */
	@Value("${frontPath}")
	protected String frontPath;
	
	/**
	 * 前端URL后缀
	 */
	@Value("${urlSuffix}")
	protected String urlSuffix;
	
	/**
	 * 验证Bean实例对象
	 */
	@Autowired
	protected Validator validator;

	/**
	 * 服务端参数有效性验证
	 * @param object 验证的实体对象
	 * @param groups 验证组
	 * @return 验证成功：返回true；严重失败：将错误信息添加到 message 中
	 */
	protected boolean beanValidator(Model model, Object object, Class<?>... groups) {
		try {
			BeanValidators.validateWithException(validator, object, groups);
		} catch (ConstraintViolationException ex) {
			List<String> list = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
			list.add(0, "数据验证失败：");
			addMessage(model, list.toArray(new String[]{}));
			return false;
		}
		return true;
	}
	
	/**
	 * 服务端参数有效性验证
	 * @param object 验证的实体对象
	 * @param groups 验证组
	 * @return 验证成功：返回true；严重失败：将错误信息添加到 flash message 中
	 */
	protected boolean beanValidator(RedirectAttributes redirectAttributes, Object object, Class<?>... groups) {
		try{
			BeanValidators.validateWithException(validator, object, groups);
		}catch(ConstraintViolationException ex){
			List<String> list = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
			list.add(0, "数据验证失败：");
			addMessage(redirectAttributes, list.toArray(new String[]{}));
			return false;
		}
		return true;
	}
	
	/**
	 * 服务端参数有效性验证
	 * @param object 验证的实体对象
	 * @param groups 验证组，不传入此参数时，同@Valid注解验证
	 * @return 验证成功：继续执行；验证失败：抛出异常跳转400页面。
	 */
	protected void beanValidator(Object object, Class<?>... groups) {
		BeanValidators.validateWithException(validator, object, groups);
	}
	
	/**
	 * 添加Model消息
	 * @param messages
	 */
	protected void addMessage(Model model, String... messages) {
		StringBuilder sb = new StringBuilder();
		for (String message : messages){
			sb.append(message).append(messages.length>1?"<br/>":"");
		}
		model.addAttribute("message", sb.toString());
	}
	
	/**
	 * 添加Flash消息
	 * @param messages
	 */
	protected void addMessage(RedirectAttributes redirectAttributes, String... messages) {
		StringBuilder sb = new StringBuilder();
		for (String message : messages){
			sb.append(message).append(messages.length>1?"<br/>":"");
		}
		redirectAttributes.addFlashAttribute("message", sb.toString());
	}
	
	/**
	 * 客户端返回JSON字符串
	 * @param response
	 * @param object
	 * @return
	 */
	protected String renderString(HttpServletResponse response, Object object) {
		return renderString(response, JsonMapper.toJsonString(object), "application/json");
	}
	
	/**
	 * 客户端返回字符串
	 * @param response
	 * @param string
	 * @return
	 */
	protected String renderString(HttpServletResponse response, String string, String type) {
		try {
			response.reset();
	        response.setContentType(type);
	        response.setCharacterEncoding("utf-8");
			response.getWriter().print(string);
			return null;
		} catch (IOException e) {
			return null;
		}
	}

	///**
	// * 参数绑定异常
	// */
	//@ExceptionHandler({BindException.class, ConstraintViolationException.class, ValidationException.class})
    //public String bindException() {
     //   return "error/400";
    //}
	//
    ///**
	// * 授权登录异常
	// */
    //@ExceptionHandler({AuthenticationException.class})
    //public String authenticationException() {
     //   return "error/403";
    //}
    //
	///**
	// * 授权登录异常
	// */
	//@ExceptionHandler({UnauthorizedException.class})
	//public @ResponseBody Object unauthorizedException() {
	//	ResponseJson responseJson = new ResponseJson();
	//	responseJson.setSuccess(false);
	//	responseJson.setMsg("您无权限使用此系统或此功能！请与系统管理员联系！!");
	//	return responseJson;
	//}


	/**
	 *
	 */
	@ExceptionHandler
	public @ResponseBody Object exception(HttpServletRequest request, Exception ex) {
		ex.printStackTrace();

        String msg = "";
        String requestUri = request.getRequestURI();
        if (StringUtils.contains(requestUri, "get")) {
            msg = "获取数据失败!";
        } else if (StringUtils.contains(requestUri, "create")) {
            msg = "添加数据失败!";
        } else if (StringUtils.contains(requestUri, "update")) {
            msg = "更新数据失败!";
        } else if (StringUtils.contains(requestUri, "delete")) {
			msg = "删除数据失败!";
        } else if (StringUtils.contains(requestUri, "resetPassword")) {
			msg = "重置密码失败!";
        } else {
            msg = "后台出错, 请求没有成功!";
        }

        ResponseJson responseJson = new ResponseJson();
        responseJson.setSuccess(false);

        if (ex instanceof AuthenticationException) {
            responseJson.setMsg("登录系统失败！!");
        } else if (ex instanceof UnauthorizedException) {
			responseJson.setMsg("您无权限使用此系统或此功能！请与系统管理员联系！!");
		} else if (ex instanceof BindException) {
			responseJson.setMsg("请求参数不正确, 绑定参数时出错!");
        } else if (ex instanceof DuplicateKeyException) {
            String dupValue = StringUtils.substringBetween(ex.getMessage(), "=(", ")");
            responseJson.setMsg(msg + " 编号 " + dupValue + " 重复, 请重新输入!");
        } else if (ex instanceof ServiceException) {
            responseJson.setMsg(ex.getMessage());
        } else {
            responseJson.setMsg(msg);
        }

        request.setAttribute("success", false);
		return responseJson;
	}

	/**
	 * 初始化数据绑定
	 * 1. 将所有传递进来的String进行HTML编码，防止XSS攻击
	 * 2. 将字段中Date类型转换为String类型
	 */
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		// String类型转换，将所有传递进来的String进行HTML编码，防止XSS攻击
		binder.registerCustomEditor(String.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) {
				setValue(text == null ? null : StringEscapeUtils.escapeHtml4(text.trim()));
			}
			@Override
			public String getAsText() {
				Object value = getValue();
				return value != null ? value.toString() : "";
			}
		});
		// Date 类型转换
		binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) {
				setValue(DateUtils.parseDate(text));
			}
//			@Override
//			public String getAsText() {
//				Object value = getValue();
//				return value != null ? DateUtils.formatDateTime((Date)value) : "";
//			}
		});
	}

}
