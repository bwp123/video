package com.video.controller;

import com.alibaba.fastjson.JSONObject;
import com.video.service.IScreenService;
import com.video.util.ByteUtils;
import com.video.util.CommonUtil;
import com.video.util.SocketUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.net.Socket;
import java.util.Calendar;

/**
 * @Author chen.cheng
 * @create 2020/4/28 8:36
 * @description
 */
@RestController
@RequestMapping("/screenSocketConfig")
@Slf4j
public class ScreenSocketConfig {

    @Autowired
    private IScreenService iScreenService;

    /**
     * 发送帧数据
     *
     * @param params
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/sendFrame", method = RequestMethod.POST)
    public String sendFrame(@RequestBody String params) {
        try {
            JSONObject jsonObject = JSONObject.parseObject(params);
            String ipadress = jsonObject.getString("ipadress");
            String portNum = jsonObject.getString("portNum");
            Socket socket = SocketUtils.getSocket(ipadress, Integer.parseInt(portNum));
            String frameData = jsonObject.getString("frameData");
            //将空字符串给去掉
            frameData.replace(" ", "");
            byte[] bytes = ByteUtils.hexStr2Byte(frameData);
            SocketUtils.sendMessage(socket, bytes);
            byte[] a = SocketUtils.getMessage(socket);
            String response = ByteUtils.byteArrayToHexString(a);
            SocketUtils.closeSocket(socket);
            return CommonUtil.getResultJson("0",response);
        } catch (Exception e) {
            e.printStackTrace();
            return CommonUtil.getResultJson("1",e.getMessage());
        }
    }


    /**
     * 时间校准
     *
     * @param params
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/adjustTime", method = RequestMethod.POST)
    public String adjustTime(@RequestBody String params) {
        try {
            JSONObject jsonObject = JSONObject.parseObject(params);
            String ipadress = jsonObject.getString("ipadress");
            String portNum = jsonObject.getString("portNum");
            Socket socket = SocketUtils.getSocket(ipadress, Integer.parseInt(portNum));
            String time = jsonObject.getString("time");
            String frameData = iScreenService.checkoutTime(time);
            byte[] bytes = ByteUtils.hexStr2Byte(frameData);
            SocketUtils.sendMessage(socket, bytes);
            byte[] a = SocketUtils.getMessage(socket);
            String response = ByteUtils.byteArrayToHexString(a);
            SocketUtils.closeSocket(socket);
            return CommonUtil.getResultJson("0",response);
            
        } catch (Exception e) {
            e.printStackTrace();
            return CommonUtil.getResultJson("1",e.getMessage());
        }
    }


    /**
     * 时间校准自动
     *
     * @param params
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/adjustTimeAuto", method = RequestMethod.POST)
    public String adjustTimeAuto(@RequestBody String params) {
        try {
            JSONObject jsonObject = JSONObject.parseObject(params);
            String ipadress = jsonObject.getString("ipadress");
            String portNum = jsonObject.getString("portNum");
            Socket socket = SocketUtils.getSocket(ipadress, Integer.parseInt(portNum));
            Calendar cal = Calendar.getInstance();
            StringBuilder stringBuilder = new StringBuilder();
            String time = stringBuilder.append(cal.get(Calendar.YEAR)).append(".").append(cal.get(Calendar.MONTH)+1).append(".")
                    .append(cal.get(Calendar.DATE)).append(".").append(cal.get(Calendar.HOUR_OF_DAY)).append(".").append(cal.get(Calendar.MINUTE))
                    .append(".").append(cal.get(Calendar.SECOND)).toString();
            String frameData = iScreenService.checkoutTime(time);
            byte[] bytes = ByteUtils.hexStr2Byte(frameData);
            SocketUtils.sendMessage(socket, bytes);
            byte[] a = SocketUtils.getMessage(socket);
            String response = ByteUtils.byteArrayToHexString(a);
            SocketUtils.closeSocket(socket);
            return CommonUtil.getResultJson("0",response);
        } catch (Exception e) {
            e.printStackTrace();
            return CommonUtil.getResultJson("1",e.getMessage());
        }
    }

    /**
     * 创建分区
     *
     * @param params
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/createArea", method = RequestMethod.POST)
    public String createArea(@RequestBody String params) {
        try {
            JSONObject jsonObject = JSONObject.parseObject(params);
            String ipadress = jsonObject.getString("ipadress");
            String portNum = jsonObject.getString("portNum");
            Socket socket = SocketUtils.getSocket(ipadress, Integer.parseInt(portNum));
            String areaNum = jsonObject.getString("areaNum");
            String sx = jsonObject.getString("sx");
            String sy = jsonObject.getString("sy");
            String width = jsonObject.getString("width");
            String height = jsonObject.getString("height");
            String frameData = iScreenService.createArea(areaNum, sx, sy, width, height);
            byte[] bytes = ByteUtils.hexStr2Byte(frameData);
            SocketUtils.sendMessage(socket, bytes);
            byte[] a = SocketUtils.getMessage(socket);
            String response = ByteUtils.byteArrayToHexString(a);
            SocketUtils.closeSocket(socket);
            return CommonUtil.getResultJson("0",response);
        } catch (Exception e) {
            e.printStackTrace();
            return CommonUtil.getResultJson("1",e.getMessage());
        }
    }


    /**
     * 显示文字
     *
     * @param params
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/showText", method = RequestMethod.POST)
    public String showText(@RequestBody String params) {
        try {
            JSONObject jsonObject = JSONObject.parseObject(params);
            String ipadress = jsonObject.getString("ipadress");
            String portNum = jsonObject.getString("portNum");
            Socket socket = SocketUtils.getSocket(ipadress, Integer.parseInt(portNum));
            String areaNum = jsonObject.getString("areaNum");
            String style = jsonObject.getString("style");
            String color = jsonObject.getString("color");
            String textId = jsonObject.getString("textId");
            String halign = jsonObject.getString("halign");
            String czdq = jsonObject.getString("czdq");
            String instyle = jsonObject.getString("instyle");
            String inspeed = jsonObject.getString("inspeed");
            String intime = jsonObject.getString("intime");
            String cir = jsonObject.getString("cir");
            String content = jsonObject.getString("content");
            String frameData = iScreenService.createText(areaNum, style, color, textId, halign, czdq, instyle, inspeed, intime, cir, content);
            byte[] bytes = ByteUtils.hexStr2Byte(frameData);
            SocketUtils.sendMessage(socket, bytes);
            byte[] a = SocketUtils.getMessage(socket);
            String response = ByteUtils.byteArrayToHexString(a);
            SocketUtils.closeSocket(socket);
            return CommonUtil.getResultJson("0",response);
        } catch (Exception e) {
            e.printStackTrace();
            return CommonUtil.getResultJson("1",e.getMessage());
        }
    }


    /**
     * 高级时钟
     *
     * @param params
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/showTime", method = RequestMethod.POST)
    public String showTime(@RequestBody String params) {
        try {
            JSONObject jsonObject = JSONObject.parseObject(params);
            String ipadress = jsonObject.getString("ipadress");
            String portNum = jsonObject.getString("portNum");
            Socket socket = SocketUtils.getSocket(ipadress, Integer.parseInt(portNum));
            String areaNum = jsonObject.getString("areaNum");
            String style = jsonObject.getString("style");
            String color = jsonObject.getString("color");
            String textId = jsonObject.getString("textId");
            String halign = jsonObject.getString("halign");
            String czdq = jsonObject.getString("czdq");
            String instyle = jsonObject.getString("instyle");
            String inspeed = jsonObject.getString("inspeed");
            String intime = jsonObject.getString("intime");
            String cir = jsonObject.getString("cir");
            String content = jsonObject.getString("content");
            String frameData = iScreenService.showTime(areaNum, style, color, textId, halign, czdq, instyle, inspeed, intime, cir, content);
            byte[] bytes = ByteUtils.hexStr2Byte(frameData);
            SocketUtils.sendMessage(socket, bytes);
            byte[] a = SocketUtils.getMessage(socket);
            String response = ByteUtils.byteArrayToHexString(a);
            SocketUtils.closeSocket(socket);
            return CommonUtil.getResultJson("0",response);
        } catch (Exception e) {
            e.printStackTrace();
            return CommonUtil.getResultJson("1",e.getMessage());
        }
    }


    /**
     * 简易时钟
     *
     * @param params
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/showSimpleTime", method = RequestMethod.POST)
    public String showSimpleTime(@RequestBody String params) {
        try {
            JSONObject jsonObject = JSONObject.parseObject(params);
            String ipadress = jsonObject.getString("ipadress");
            String portNum = jsonObject.getString("portNum");
            Socket socket = SocketUtils.getSocket(ipadress, Integer.parseInt(portNum));
            String areaNum = jsonObject.getString("areaNum");
            String style = jsonObject.getString("style");
            String color = jsonObject.getString("color");
            String textId = jsonObject.getString("textId");
            String halign = jsonObject.getString("halign");
            String czdq = jsonObject.getString("czdq");
            String frameData = iScreenService.showSimpleTime(areaNum, style, color, textId, halign, czdq);
            byte[] bytes = ByteUtils.hexStr2Byte(frameData);
            SocketUtils.sendMessage(socket, bytes);
            byte[] a = SocketUtils.getMessage(socket);
            String response = ByteUtils.byteArrayToHexString(a);
            SocketUtils.closeSocket(socket);
            return CommonUtil.getResultJson("0",response);
        } catch (Exception e) {
            e.printStackTrace();
            return CommonUtil.getResultJson("1",e.getMessage());
        }
    }


    /**
     * 数字时钟
     *
     * @param params
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/showDataTime", method = RequestMethod.POST)
    public String showDataTime(@RequestBody String params) {
        try {
            JSONObject jsonObject = JSONObject.parseObject(params);
            String ipadress = jsonObject.getString("ipadress");
            String portNum = jsonObject.getString("portNum");
            Socket socket = SocketUtils.getSocket(ipadress, Integer.parseInt(portNum));
            String areaNum = jsonObject.getString("areaNum");
            String style = jsonObject.getString("style");
            String color = jsonObject.getString("color");
            String textId = jsonObject.getString("textId");
            String halign = jsonObject.getString("halign");
            String czdq = jsonObject.getString("czdq");
            String content = jsonObject.getString("content");
            String frameData = iScreenService.showDataTime(areaNum, style, color, textId, halign, czdq, content);
            byte[] bytes = ByteUtils.hexStr2Byte(frameData);
            SocketUtils.sendMessage(socket, bytes);
            byte[] a = SocketUtils.getMessage(socket);
            String response = ByteUtils.byteArrayToHexString(a);
            SocketUtils.closeSocket(socket);
            return CommonUtil.getResultJson("0",response);
        } catch (Exception e) {
            e.printStackTrace();
            return CommonUtil.getResultJson("1",e.getMessage());
        }
    }


    /**
     * 修改分区
     *
     * @param params
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/modifyArea", method = RequestMethod.POST)
    public String modifyArea(@RequestBody String params) {
        try {
            JSONObject jsonObject = JSONObject.parseObject(params);
            String ipadress = jsonObject.getString("ipadress");
            String portNum = jsonObject.getString("portNum");
            Socket socket = SocketUtils.getSocket(ipadress, Integer.parseInt(portNum));
            String areaNum = jsonObject.getString("areaNum");
            String sx = jsonObject.getString("sx");
            String sy = jsonObject.getString("sy");
            String width = jsonObject.getString("width");
            String height = jsonObject.getString("height");
            String frameData = iScreenService.modifyArea(areaNum, sx, sy, width, height);
            byte[] bytes = ByteUtils.hexStr2Byte(frameData);
            SocketUtils.sendMessage(socket, bytes);
            byte[] a = SocketUtils.getMessage(socket);
            String response = ByteUtils.byteArrayToHexString(a);
            SocketUtils.closeSocket(socket);
            return CommonUtil.getResultJson("0",response);
        } catch (Exception e) {
            e.printStackTrace();
            return CommonUtil.getResultJson("1",e.getMessage());
        }
    }


    /**
     * 删除分区
     *
     * @param params
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/deleteArea", method = RequestMethod.POST)
    public String deleteArea(@RequestBody String params) {
        try {
            JSONObject jsonObject = JSONObject.parseObject(params);
            String ipadress = jsonObject.getString("ipadress");
            String portNum = jsonObject.getString("portNum");
            Socket socket = SocketUtils.getSocket(ipadress, Integer.parseInt(portNum));
            String areaNum = jsonObject.getString("areaNum");
            String frameData = iScreenService.deleteArea(areaNum);
            byte[] bytes = ByteUtils.hexStr2Byte(frameData);
            SocketUtils.sendMessage(socket, bytes);
            byte[] a = SocketUtils.getMessage(socket);
            String response = ByteUtils.byteArrayToHexString(a);
            SocketUtils.closeSocket(socket);
            return CommonUtil.getResultJson("0",response);
        } catch (Exception e) {
            e.printStackTrace();
            return CommonUtil.getResultJson("1",e.getMessage());
        }
    }


    /**
     * 发送语音
     * @param params
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/sendSpeak", method = RequestMethod.POST)
    public String sendSpeak(@RequestBody String params) {
        try {
            JSONObject jsonObject = JSONObject.parseObject(params);
            String ipadress = jsonObject.getString("ipadress");
            String portNum = jsonObject.getString("portNum");
            Socket socket = SocketUtils.getSocket(ipadress, Integer.parseInt(portNum));
            String content = jsonObject.getString("content");
            String frameData = iScreenService.sendSpeak(content);
            byte[] bytes = ByteUtils.hexStr2Byte(frameData);
            SocketUtils.sendMessage(socket, bytes);
            byte[] a = SocketUtils.getMessage(socket);
            String response = ByteUtils.byteArrayToHexString(a);
            SocketUtils.closeSocket(socket);
            return CommonUtil.getResultJson("0",response);
        }catch (Exception e){
            e.printStackTrace();
            return CommonUtil.getResultJson("1",e.getMessage());
        }
    }


    /**
     * 调节音量
     * @param params
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/adjustVol", method = RequestMethod.POST)
    public String adjustVol(@RequestBody String params) {
        try {
            JSONObject jsonObject = JSONObject.parseObject(params);
            String ipadress = jsonObject.getString("ipadress");
            String portNum = jsonObject.getString("portNum");
            Socket socket = SocketUtils.getSocket(ipadress, Integer.parseInt(portNum));
            String level = jsonObject.getString("level");
            String frameData = iScreenService.adjustVol(level);
            byte[] bytes = ByteUtils.hexStr2Byte(frameData);
            SocketUtils.sendMessage(socket, bytes);
            byte[] a = SocketUtils.getMessage(socket);
            String response = ByteUtils.byteArrayToHexString(a);
            SocketUtils.closeSocket(socket);
            return CommonUtil.getResultJson("0",response);
        }catch (Exception e){
            e.printStackTrace();
            return CommonUtil.getResultJson("1",e.getMessage());
        }
    }


    /**
     * 发送语音
     * @param params
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/stopSpeak", method = RequestMethod.POST)
    public String stopSpeak(@RequestBody String params) {
        try {
            JSONObject jsonObject = JSONObject.parseObject(params);
            String ipadress = jsonObject.getString("ipadress");
            String portNum = jsonObject.getString("portNum");
            Socket socket = SocketUtils.getSocket(ipadress, Integer.parseInt(portNum));
            String frameData = iScreenService.stopSpeak();
            byte[] bytes = ByteUtils.hexStr2Byte(frameData);
            SocketUtils.sendMessage(socket, bytes);
            byte[] a = SocketUtils.getMessage(socket);
            String response = ByteUtils.byteArrayToHexString(a);
            SocketUtils.closeSocket(socket);
            return CommonUtil.getResultJson("0",response);
        }catch (Exception e){
            e.printStackTrace();
            return CommonUtil.getResultJson("1",e.getMessage());
        }
    }

}
