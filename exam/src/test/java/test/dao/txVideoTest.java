package test.dao;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.junit.Test;

import java.util.Iterator;

/**
 * Created by syb on 2017/8/18.
 */
public class txVideoTest {
    @Test
    public void test() throws Exception{
        String vid = "l0527y0xf8u"; //我自己上传到腾讯视频的一个视频的vid
        String url = "http://vv.video.qq.com/geturl?vid="+vid+"&otype=xml&platform=1&ran=0%2E9652906153351068";

        DefaultHttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        HttpResponse httpResponse = client.execute(httpGet);
        HttpEntity entity = httpResponse.getEntity();
        Document document = null;

        if(entity != null){
            String result = EntityUtils.toString(entity,"UTF-8");
            System.out.println("获取的结果是："+result);
//            document = DocumentHelper.parseText(result);
//            Element root = document.getRootElement();
//            listNodes(root);
        }

//        int s = (int)5.6;
//        System.out.println(s);

    }




    //遍历当前节点下的所有节点
    public void listNodes(Element node){
//        System.out.println("当前节点的名称：" + node.getName());
        //首先获取当前节点的所有属性节点
//        List<Attribute> list = node.attributes();
        //遍历属性节点
//        for(Attribute attribute : list){
//            System.out.println("属性"+attribute.getName() +":" + attribute.getValue());
//        }
        //如果当前节点内容不为空，则输出
        if(!(node.getTextTrim().equals(""))){
            if (node.getName().trim().equals("url"))
            System.out.println( node.getName() + "：" + node.getText());
        }
        //同时迭代当前节点下面的所有子节点
        //使用递归
        Iterator<Element> iterator = node.elementIterator();
        while(iterator.hasNext()){
            Element e = iterator.next();
            listNodes(e);
        }
    }

}
