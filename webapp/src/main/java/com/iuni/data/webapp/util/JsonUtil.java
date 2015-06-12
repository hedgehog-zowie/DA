package com.iuni.data.webapp.util;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.iuni.data.common.DateUtils;
import com.iuni.data.persist.domain.webkpi.WebKpi;
import net.sf.json.JSONArray;

public class JsonUtil {
    public static String getCategory(){
        StringBuffer sb = new StringBuffer();
        sb.append("[\"");
        List<String> list = new ArrayList<String>();
        for(int i=1;i<8;i++){
            list.add("周"+i);
        }
        
        String json = "[\"周一\",\"周二\",\"周三\",\"周四\",\"周五\",\"周六\",\"周日\"]";
        JSONArray ja=JSONArray.fromObject(list);
        return ja.toString();
    } 
    //通过给定的集合List<Webkpi> datas，得到json字符串(横坐标)
    public static String getCategory(List<WebKpi> datas){
        StringBuffer sb = new StringBuffer();
        sb.append("[");
        int size = datas.size();
        for(int i=0;i<size;i++){
            WebKpi idw = datas.get(i);
            sb.append("\""+ DateUtils.dateToSimpleDateStr(idw.getTime(), "yyyy-MM-dd EE")+"\"");
            if(i != (size-1)){
                sb.append(",");
            }
        }
        sb.append("]");
        JSONArray ja=JSONArray.fromObject(sb.toString());
        return ja.toString();
    }
    
    //通过数组得到json字符串
    public static String getCategoryByArray(Object[] objects){
        if(objects != null && objects.length > 0){
            StringBuffer sb = new StringBuffer();
            sb.append("[");
            int length = objects.length  ;
            for(int i=0;i<length;i++){
                sb.append("\""+objects[i]+"\"");
                if(i != (length-1)){
                    sb.append(",");
                }
            }
            sb.append("]");
            JSONArray ja=JSONArray.fromObject(sb.toString());
            return ja.toString();
        }else{
            return null;
        }
        
    }
    //通过给定的集合List<Webkpi> datas，得到json字符串(纵坐标)
    //纵坐标又包含多个属性，如name、type、stack、smooth、symbol等等
    public static String getValue(List<WebKpi> datas){
        JSONArray ja = new JSONArray();
        if(datas != null){
            StringBuffer sb = new StringBuffer();
            
            
            Map map = new HashMap();
            
            String name = "PV";
            String type = "line";
            String stack = "总量";
            boolean smooth = true;//显示平滑曲线
            String symbol = "emptyCircle";//显示符号
            int size = datas.size();
            
            List listPV = new ArrayList();
            List listUV = new ArrayList();
            List listVV = new ArrayList();
            List listNewUV = new ArrayList();
            for (WebKpi data : datas) {
                listPV.add(data.getPv());
                listUV.add(data.getUv());
                listVV.add(data.getVv());
                listNewUV.add(data.getNewUv());
            }
            
            
            String arr[] = new String[4];
            List names = new ArrayList();
            arr[0] = "PV";
            names.add(arr[0]);
            arr[1] = "UV";
            names.add(arr[1]);
            arr[2] = "访问次数";
            names.add(arr[2]);
            arr[3] = "新独立访客";
            names.add(arr[3]);
            map.put(names.get(0), listPV);
            map.put(names.get(1), listUV);
            map.put(names.get(2), listVV);
            map.put(names.get(3), listNewUV);
            
            
            Set set = map.keySet();
            
            sb.append("[");
            for(int i=0;i<names.size();i++){
                sb.append("{\"name\":\""+names.get(i)+"\",\"type\":\""+type+"\",\"stack\": \""+stack+"\",data:"+map.get(names.get(i))+",\"smooth\":"+smooth+",\"symbol\":\""+symbol+"\"}");
                if(i!=(names.size()-1)){
                    sb.append(",");
                }
            }
            sb.append("]");
            ja=JSONArray.fromObject(sb.toString());
        }
        return ja.toString();
    }
    
    public  static String getValue(List<WebKpi> datas,String[] indicators ){
        List<Map<String,Object>> names = getListMap(datas,indicators );
        JSONArray ja = new JSONArray();
        ja = getJaToString(names);
        return ja.toString();
    }
    //通过List<Webkpi> datas,String[] indicators 得到  List<Map<String,Object>> 
    //给 getJaToString(List<Map<String,Object>> names)使用
    public static List<Map<String,Object>> getListMap(List<WebKpi> datas,String[] indicators ){
        String name = "name";
        String type = "type";
        String stack = "stack";
        String data = "data";
        String smooth = "smooth";
        String symbol = "symbol";
        
        
        if (indicators != null && indicators.length > 0) {
            List<Map<String,Object>> names = new ArrayList<Map<String,Object>>();
            
            if (datas != null && datas.size() > 0) {
                int length = indicators.length;
                int size = datas.size();
                /*List<String> listStr = new ArrayList<String>();
                for(int i=0;i<length;i++){
                    listStr.add(indicators[i]);
                }*/
                
                for (int i = 0; i < length; i++) {
                    Map<String,Object> map = new HashMap<String,Object>();
                    map.put(name, indicators[i]);
                    map.put(type, "line");
                    map.put(stack, "stack");
                    map.put(data, getListByString(datas,indicators[i]));
                    map.put(smooth, true);
                    map.put(symbol, "emptyCircle");
                    names.add(map);
                }
            }
            return names;
        }else{
            return null;
        }
        
    }
    //通过对比字符串，返回特定的集合
    //TODO 有问题，需调整
    public static List<Object> getListByString(List<WebKpi> datas,String str){
        if(str != null && !"".equals(str)){
            List<Object> list = new ArrayList<Object>();
            for(WebKpi data : datas){
                if("PV".equals(str)){
                    list.add(data.getPv());
                }else if("UV".equals(str)){
                    list.add(data.getUv());
                }else if("IP".equals(str)){
                    list.add(data.getIp());
                }else if("新独立访客".equals(str)){
                    list.add(data.getNewUv());
                }else if("访问次数".equals(str)){
                    list.add(data.getVv());
                }else if("人均浏览页面数".equals(str)){
                    list.add(new DecimalFormat("0.0").format(data.getPv()/data.getUv()));
                }else if("平均访问深度".equals(str)){
                    list.add(new DecimalFormat("0.0").format(data.getPv()/data.getVv()));
                }else if("平均访问时长".equals(str)){
                    list.add(data.getTotalTime()/data.getVv()+data.getTotalTime()%data.getVv());
                    System.out.println((long)data.getTotalTime()/(long)data.getVv());
                }else if("跳出率".equals(str)){
                    list.add(new DecimalFormat("00.00").format((new Double(data.getTotalJump())/new Double(data.getVv()))*100));
                    System.out.println(new DecimalFormat("0.00%").format((new Double(data.getTotalJump())/new Double(data.getVv()))));
                }
            }
            return list;
        }else{
            return null;
        }
        
    }
    
    //通过List<Map<String,Object>> names得到JSONArray
    public static JSONArray getJaToString(List<Map<String,Object>> names){
        StringBuffer sb = new StringBuffer();
        sb.append("[");
        int size_i = names.size();
        
        for(int i=0;i<size_i;i++){
            Map<String,Object> map = names.get(i);
            sb.append("{\"name\":\""+map.get("name")+"\",\"type\":\""+map.get("type")+"\",\"stack\": \""+map.get("stack")+"\",data:"+map.get("data")+",\"smooth\":"+map.get("smooth")+",\"symbol\":\""+map.get("symbol")+"\"}");
            if(i != (size_i - 1)){
                sb.append(",");
            }
        }
        
        sb.append("]");
        JSONArray ja = new JSONArray();
        ja=JSONArray.fromObject(sb.toString());
        return ja;
    }
    
    //得到Webkpi中 平均时长的分钟+秒钟
    public static String getTotalTimeByDatas(WebKpi iuniDaWebKpi){
        long minute = iuniDaWebKpi.getTotalTime()/ iuniDaWebKpi.getVv();
        long second = iuniDaWebKpi.getTotalTime()% iuniDaWebKpi.getVv();
        return minute+"分"+second+"秒";
    }
    //得到平均时长中秒钟
    
    public static String getValue(){
        List<Object> list = new ArrayList<Object>();
        String str = "[{\"name\":\"PV\",\"type\":\"line\",\"stack\": \"总量\",data:[120, 132, 101, 134, 90, 230, 210]}]";
        
        String name = "PV";
        String type = "line";
        String stack = "总量";
        int[]  a = {120, 132, 101, 134, 90, 230, 210};
        List b = new ArrayList();
        for(int i=0;i<a.length;i++){
            b.add(a[i]);
        }
        String arr = b.toString();
        
        
        StringBuffer sb = new StringBuffer();
        sb.append("[");
        for(int i=1;i<2;i++){
            sb.append("{\"name\":\""+name+"\",\"type\":\""+type+"\",\"stack\": \""+stack+"\",data:"+arr+"}");
            if(i!=(2-1)){
                sb.append(",");
            }
        }
        sb.append("]");
        JSONArray ja=JSONArray.fromObject(sb.toString());
        return ja.toString();
    }
    
    public static void main(String[] args) {
        int[]  a = {120, 132, 101, 134, 90, 230, 210};
        List b = new ArrayList();
        for(int i=0;i<a.length;i++){
            b.add(a[i]);
        }
        String str = b.toString();
        StringBuffer sb = new StringBuffer();
        sb.append(str);
        sb.append("");
//        System.out.println(sb);
        
        System.out.println(getValue());
    }
}
