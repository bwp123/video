package com.video.service;

/**
 * @Author chen.cheng
 * @create 2020/4/28 9:57
 * @description
 */
public interface IScreenService {

    /**
     * 根据坐标创建区域
     * @param xoor
     * @param ycoor
     * @return
     */
    String createArea(String areaNum, String sx, String sy, String width, String height);

    /**
     * 配置文本
     * @param areaNum
     * @param style
     * @param color
     * @param textId
     * @param halign
     * @param czdq
     * @param instyle
     * @param inspeed
     * @param intime
     * @param content
     * @return
     * @throws Exception
     */
    String createText(String areaNum, String style, String color, String textId, String halign, String czdq, String instyle, String inspeed, String intime, String cir, String content) throws Exception;

    /**
     * 校准时间
     * @param time
     * @return
     */
    String checkoutTime(String time);


    /**
     * 显示时间
     * @param areaNum
     * @param style
     * @param color
     * @param textId
     * @param halign
     * @param czdq
     * @param instyle
     * @param inspeed
     * @param intime
     * @param content
     * @return
     * @throws Exception
     */
    public String showTime(String areaNum, String style, String color, String textId, String halign, String czdq, String instyle, String inspeed, String intime, String cir, String content) throws Exception;


    /**
     * 显示数字时钟
     * @param areaNum
     * @param style
     * @param color
     * @param textId
     * @param halign
     * @param czdq
     * @param content
     * @return
     * @throws Exception
     */
    String showDataTime(String areaNum, String style, String color, String textId, String halign, String czdq, String content) throws Exception;


    /**
     * 简易时钟
     * @param areaNum
     * @param style
     * @param color
     * @param textId
     * @param halign
     * @param czdq
     * @return
     */
    String showSimpleTime(String areaNum, String style, String color, String textId, String halign, String czdq);


    /**
     * 删除分区
     * @param areaNum
     * @return
     */
    String deleteArea(String areaNum);


    /**
     * 修改分区
     * @param areaNum
     * @param sx
     * @param sy
     * @param width
     * @param height
     * @return
     */
    String modifyArea(String areaNum, String sx, String sy, String width, String height);


    /**
     * 发送语音
     * @param content
     * @return
     * @throws Exception
     */
    String sendSpeak(String content) throws Exception;


    /**
     * 调节音量
     * @param level
     * @return
     * @throws Exception
     */
    String adjustVol(String level) throws Exception;

    /**
     * 停止语音合成
     * @return
     */
    String stopSpeak();
}
