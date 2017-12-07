package test.dao;
import com.ynsh.po.AccessToken;
import com.ynsh.util.Constant;
import com.ynsh.util.WeixinUtil;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by syb on 2017/6/1.
 */
public class xstreamTest {
    private static Logger logger = LoggerFactory.getLogger(xstreamTest.class);


    @Test
    public void testXstream(){
//        XStream xStream = new XStream();
//        TextMessage textMessage = new TextMessage();
//        textMessage.setContent("this is content");
//        textMessage.setMsgId("1232323");
//        textMessage.setCreateTime(new Date().getTime());
//        textMessage.setFromUserName("fromUser");
//        textMessage.setToUserName("toUser");
//
//        xStream.alias("xml",textMessage.getClass());
//        String m = xStream.toXML(textMessage);
//        System.out.println("fromUser: "+textMessage.getFromUserName());
//        System.out.println("result: "+m);
        String t = "oBuv-0IIZxHg2llOPuYnthMkqNYo";
        System.out.println(t.length());
    }

//    @Test
//    public void openIdTest(){
//        String code = "0613T2GV0RQrvX18IJDV0aI3GV03T2Gg";
//        String url = Constant.OPEN_ID_URL.replace("APPID",Constant.AppId).replace("SECRET",Constant.AppSecret).replace("JSCODE",code);
//
//        DefaultHttpClient client = new DefaultHttpClient();
//        HttpGet httpGet = new HttpGet(url);
//        String result = null;
//        try {
//            HttpResponse httpResponse = client.execute(httpGet);
//            HttpEntity entity = httpResponse.getEntity();
//
//            if(entity != null){
//                result = EntityUtils.toString(entity,"UTF-8");
//                logger.info("获取的结果是：{}",result);
//            }
//
//            JSONObject jsonObject = JSONObject.fromObject(result);
//
//            System.out.println(jsonObject.getString("openid"));
//
//        }catch (Exception e){
//            logger.error("通过code获取openid时错误");
//        }
//    }
//
//    @Test
//    public void tokenTest(){
//        try {
//            AccessToken accessToken = WeixinUtil.getAccessToken();
//            System.out.println("token: "+ accessToken.getToken());
//            System.out.println("有效期: "+ accessToken.getExpiresIn());
//
//
//            String path = "C:\\Users\\syb\\Desktop\\Project\\cola.jpg";
//            //上传临时素材
////            String mediaId = WeixinUtil.upload(path,accessToken.getToken(),"image");
////            System.out.println("mediaId: "+mediaId);
//            //上传永久素材
////            JSONObject object = WeixinUtil.addMaterialEver(path,"image",accessToken.getToken());
////            System.out.println(object);
//
//            //创建菜单
//            String menu = JSONObject.fromObject(WeixinUtil.initMenu()).toString();
//            int result = WeixinUtil.createMenu(accessToken.getToken(),menu);
//            if (result == 0) {
//                System.out.println("创建菜单成功");
//            }else {
//                System.out.println("创建菜单失败： "+result);
//            }
//
//
//            WeixinUtil.getMaterialList(accessToken.getToken());
//
//            WeixinUtil.getMaterialCount(accessToken.getToken());
//
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
}
