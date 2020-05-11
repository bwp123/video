package com.video.util;

import com.alibaba.fastjson.JSONObject;

/**
 * @Author chen.cheng
 * @create 2020/5/6 17:13
 * @description
 */
public class CommonUtil {
    public static String getResultJson(String result,String info){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result",result);
        jsonObject.put("info",info);
        return jsonObject.toJSONString();
    }
}
