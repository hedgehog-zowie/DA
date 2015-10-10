package com.tencent.protocol.pay_query_protocol;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * User: rizenguo
 * Date: 2014/12/17
 * Time: 15:44
 */
public class CouponData {

    private String coupon_batch_id = "";//优惠券批次ID
    private String coupon_id = ""; //优惠券ID
    private String coupon_fee = "";//单个优惠券金额

    public String getCoupon_batch_id() {
        return coupon_batch_id;
    }

    public void setCoupon_batch_id(String coupon_batch_id) {
        this.coupon_batch_id = coupon_batch_id;
    }

    public String getCoupon_id() {
        return coupon_id;
    }

    public void setCoupon_id(String coupon_id) {
        this.coupon_id = coupon_id;
    }

    public String getCoupon_fee() {
        return coupon_fee;
    }

    public void setCoupon_fee(String coupon_fee) {
        this.coupon_fee = coupon_fee;
    }

    public String toMap(){
        Map<String,Object> map = new LinkedHashMap<String, Object>();
        Field[] fields = this.getClass().getDeclaredFields();
        StringBuilder s=new StringBuilder("{");

        for (Field field : fields) {
            Object obj;
            try {
                obj = field.get(this);
                if(obj!=null){
                    if(s.length()>0){
                        s.append(" ");
                    }
                    s.append(field.getName());
                    s.append("=");
                    s.append(obj.toString());
//                    map.put(field.getName(), obj);
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        s.append("}");
        return s.toString();
    }

}
