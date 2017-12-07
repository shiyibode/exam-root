package com.ynsh.controller;

import com.ynsh.util.CheckUtil;
import com.ynsh.util.MessageUtil;
import com.ynsh.util.WeixinUtil;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;


/**
 * Created by syb on 2017/5/27.
 */
@Controller
@Configuration
@ImportResource("classpath:spring-config.xml")
public class WeiXinController {
    @Value("${token}")
    private String token;


    @RequestMapping(value ="/wx.do",method = RequestMethod.GET)
    public void checkSignature(HttpServletRequest req, HttpServletResponse res){

        String signature = req.getParameter("signature");
        String timestamp = req.getParameter("timestamp");
        String nonce = req.getParameter("nonce");
        String echostr = req.getParameter("echostr");

        System.out.println("token: "+token);


        try {
            PrintWriter out = res.getWriter();
            if (CheckUtil.checkSignature(signature,timestamp,nonce,"shiyibo")) out.print(echostr);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value ="/wx.do",method = RequestMethod.POST)
    public void textMessage(HttpServletRequest req, HttpServletResponse resp) throws Exception{

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        try {
            Map<String, String> map = MessageUtil.xmlToMap(req);
            String fromUserName = map.get("FromUserName");System.out.println("FromUerName: "+fromUserName);
            String toUserName = map.get("ToUserName");System.out.println("ToUerName: "+toUserName);
            String msgType = map.get("MsgType");
            String content = map.get("Content");

            String message = null;
            if(MessageUtil.MESSAGE_TEXT.equals(msgType)){
                if("1".equals(content)){
                    message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.firstMenu());
                }else if("2".equals(content)){
                    message = MessageUtil.initNewsMessage(toUserName, fromUserName);
                }else if("3".equals(content)){
                    message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.threeMenu());
                }else if("?".equals(content) || "？".equals(content)){
                    message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.menuText());
                }else if("4".equals(content)){
                    message = MessageUtil.initText(toUserName,fromUserName,"笨笨是笨蛋");
                }else if(content.startsWith("翻译")){
                    String word = content.replaceAll("^翻译", "").trim();
                    if("".equals(word)){
                        message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.threeMenu());
                    }else{
                        message = MessageUtil.initText(toUserName, fromUserName, WeixinUtil.translate(word));
                    }
                }else {
                    message = MessageUtil.initText(toUserName,fromUserName,"对不起，你的指令我无法理解");
                }
            }else if(MessageUtil.MESSAGE_EVNET.equals(msgType)){
                String eventType = map.get("Event");
                if(MessageUtil.MESSAGE_SUBSCRIBE.equals(eventType)){
                    message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.menuText());
                }else if(MessageUtil.MESSAGE_CLICK.equals(eventType)){
                    message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.menuText());
                }else if(MessageUtil.MESSAGE_VIEW.equals(eventType)){
                    String url = map.get("EventKey");
                    message = MessageUtil.initText(toUserName, fromUserName, url);
                }else if(MessageUtil.MESSAGE_SCANCODE.equals(eventType)){
                    String key = map.get("EventKey");
                    message = MessageUtil.initText(toUserName, fromUserName, key);
                }
            }else if(MessageUtil.MESSAGE_LOCATION.equals(msgType)){
                String label = map.get("Label");
                message = MessageUtil.initText(toUserName, fromUserName, label);
            }


            out.print(message);
        } catch (DocumentException e) {
            e.printStackTrace();
        }finally{
            out.close();
        }

    }


}
