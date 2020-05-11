package com.video.service.impl;


import cn.hutool.core.util.StrUtil;
import com.video.service.IScreenService;
import com.video.util.ByteUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Service;

/**
 * @Author chen.cheng
 * @create 2020/4/28 9:59
 * @description
 */
@Service
public class IScreenServiceImpl implements IScreenService {

    /**
     * 处理帧的通用部分
     * @param data
     * @return
     */
    public String handleFrame(String data){
        StringBuilder stringBuilder = new StringBuilder();
        //帧头
        stringBuilder.append("AA A5")
                //长度 + 固定00（不算校验码）
                .append(ByteUtils.intToHexStringXd(data.length()/2+6,2)).append("00")
                //目标地址DES
                .append("FF FF")
                //原地址SRC
                .append("00 00")
                //会话ID TID
                .append("B0 A1")
                //拼装剩余的数据
                .append(data)
                //校验位为0，帧尾5A55
                .append("00 00 5A 55");
        return StrUtil.cleanBlank(stringBuilder);
    }


    /**
     * 根据坐标创建区域
     * @param xoor
     * @param ycoor
     * @return
     */
    @Override
    public String createArea(String areaNum,String sx,String sy,String width,String height){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("03 03")
                //区序号
                .append(ByteUtils.intToHexStringXd(Integer.parseInt(areaNum),4))
                //分区存在则覆盖
                .append("01 00")
                //x坐标
                .append(ByteUtils.intToHexStringXd(Integer.parseInt(sx),4))
                //y坐标
                .append(ByteUtils.intToHexStringXd(Integer.parseInt(sy),4))
                //分区宽度
                .append(ByteUtils.intToHexStringXd(Integer.parseInt(width),4))
                //分区高度
                .append(ByteUtils.intToHexStringXd(Integer.parseInt(height),4))
                //创建永久分区
                .append("02 00 00 00")
                //保留
                .append("00 00 00 00");
        return handleFrame(StrUtil.cleanBlank(stringBuilder.toString()));
    }


    /**
     * 在指定区域显示文字
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
    public String createText(String areaNum,String style,String color,String textId,String halign,String czdq,String instyle,String inspeed,String intime,String cir,String content) throws Exception{
        StringBuilder stringBuilder = new StringBuilder();
        byte[] formatBytes = new byte[32];
        ByteUtils.initArray(formatBytes);
        //颜色ID
        byte[] colorBytes = ByteUtils.stringTobytes(color,4);
        ByteUtils.fillbytesByPosition(formatBytes,colorBytes,19,16);
        //字体ID
        byte[] textIdbytes = ByteUtils.stringTobytes(Integer.toBinaryString(Integer.parseInt(textId)),8);
        ByteUtils.fillbytesByPosition(formatBytes,textIdbytes,15,8);
        //水平对齐
        byte[] halignbytes = ByteUtils.stringTobytes(halign,2);
        ByteUtils.fillbytesByPosition(formatBytes,halignbytes,7,6);
        //垂直对齐
        byte[] czdqbytes = ByteUtils.stringTobytes(czdq,2);
        ByteUtils.fillbytesByPosition(formatBytes,czdqbytes,5,4);
        formatBytes[2] = 1;
        formatBytes[1] = 1;
        ArrayUtils.reverse(formatBytes);
        String format = ByteUtils.bytesToHexByNum(formatBytes);
        byte[] contentbytes = content.getBytes("GBK");
        int textSize = contentbytes.length;
        stringBuilder.append("10 03")
                //分区编号
                .append(ByteUtils.intToHexStringXd(Integer.parseInt(areaNum),4))
                //保留
                .append("00 00")
                //节目样式 00为临时节目，02为永久节目
                .append(style).append("00 00 00")
                //文本显示格式
                .append(ByteUtils.hexToHexStringXd(format,8))
                //进入效果 01
                .append(instyle).append("00")
                //进入效果速度
                .append(ByteUtils.intToHexStringXd(Integer.parseInt(inspeed),4))
                //进入效果停留时间
                .append(ByteUtils.intToHexStringXd(Integer.parseInt(intime),4))
                //保留位
                .append("00 00 00 00 00 00")
                //退出效果
                .append("00 00")
                //退出速度
                .append("00 00")
                //重复次数
                //重复次数
                .append(ByteUtils.intToHexStringXd(Integer.parseInt(cir),4))
                //TEXT 字节数
                .append(ByteUtils.intToHexStringXd(textSize,4))
                //text
                .append(ByteUtils.byteArrayToHexString(contentbytes));
        return handleFrame(StrUtil.cleanBlank(stringBuilder.toString()));
    }


    /**
     * 校验时间
     * @param time
     * @return
     */
    @Override
    public String checkoutTime(String time){
        String[] strings = time.split("\\.");
        String year = strings[0];
        String month = strings[1];
        String day = strings[2];
        String hour = strings[3];
        String minute = strings[4];
        String second = strings[5];
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("66 00")
            .append(ByteUtils.intToHexStringXd(Integer.parseInt(year),4))
            .append(ByteUtils.intToHexStringXd(Integer.parseInt(month),4))
                //星期用0填充
            .append("00 00")
            .append(ByteUtils.intToHexStringXd(Integer.parseInt(day),4))
            .append(ByteUtils.intToHexStringXd(Integer.parseInt(hour),4))
            .append(ByteUtils.intToHexStringXd(Integer.parseInt(minute),4))
            .append(ByteUtils.intToHexStringXd(Integer.parseInt(second),4))
                //保留值
            .append("00 00");
        return handleFrame(StrUtil.cleanBlank(stringBuilder.toString()));
    }


    /**
     * 高级时钟
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
    public String showTime(String areaNum,String style,String color,String textId,String halign,String czdq,String instyle,String inspeed,String intime,String cir,String content) throws Exception{
        StringBuilder stringBuilder = new StringBuilder();
        byte[] formatBytes = new byte[32];
        ByteUtils.initArray(formatBytes);
        //颜色ID
        byte[] colorBytes = ByteUtils.stringTobytes(color,4);
        ByteUtils.fillbytesByPosition(formatBytes,colorBytes,19,16);
        //字体ID
        byte[] textIdbytes = ByteUtils.stringTobytes(Integer.toBinaryString(Integer.parseInt(textId)),8);
        ByteUtils.fillbytesByPosition(formatBytes,textIdbytes,15,8);
        //水平对齐
        byte[] halignbytes = ByteUtils.stringTobytes(halign,2);
        ByteUtils.fillbytesByPosition(formatBytes,halignbytes,7,6);
        //垂直对齐
        byte[] czdqbytes = ByteUtils.stringTobytes(czdq,2);
        ByteUtils.fillbytesByPosition(formatBytes,czdqbytes,5,4);
        formatBytes[2] = 1;
        formatBytes[1] = 1;
        ArrayUtils.reverse(formatBytes);
        String format = ByteUtils.bytesToHexByNum(formatBytes);
        byte[] contentbytes = content.getBytes("GBK");
        int textSize = contentbytes.length;
        stringBuilder.append("27 03")
                //分区编号
                .append(ByteUtils.intToHexStringXd(Integer.parseInt(areaNum),4))
                //保留
                .append("00 00")
                //节目样式 00为临时节目，02为永久节目
                .append(style).append("00 00 00")
                //文本显示格式
                .append(ByteUtils.hexToHexStringXd(format,8))
                //OFFSET
                .append("00 00 00 00")
                //进入效果 01
                .append(instyle).append("00")
                //进入效果速度
                .append(ByteUtils.intToHexStringXd(Integer.parseInt(inspeed),4))
                //进入效果停留时间
                .append(ByteUtils.intToHexStringXd(Integer.parseInt(intime),4))
                //保留位
                .append("00 00 00 00 00 00")
                //退出效果
                .append("00 00")
                //退出速度
                .append("00 00")
                //重复次数（0不会刷新时间）
                .append("FF FF")
                //TEXT 字节数
                .append(ByteUtils.intToHexStringXd(textSize,4))
                //text
                .append(ByteUtils.byteArrayToHexString(contentbytes));
        return handleFrame(StrUtil.cleanBlank(stringBuilder.toString()));
    }


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
    public String showSimpleTime(String areaNum,String style,String color,String textId,String halign,String czdq){
        StringBuilder stringBuilder = new StringBuilder();
        byte[] formatBytes = new byte[32];
        ByteUtils.initArray(formatBytes);
        //颜色ID
        byte[] colorBytes = ByteUtils.stringTobytes(color,4);
        ByteUtils.fillbytesByPosition(formatBytes,colorBytes,19,16);
        //字体ID
        byte[] textIdbytes = ByteUtils.stringTobytes(Integer.toBinaryString(Integer.parseInt(textId)),8);
        ByteUtils.fillbytesByPosition(formatBytes,textIdbytes,15,8);
        //水平对齐
        byte[] halignbytes = ByteUtils.stringTobytes(halign,2);
        ByteUtils.fillbytesByPosition(formatBytes,halignbytes,7,6);
        //垂直对齐
        byte[] czdqbytes = ByteUtils.stringTobytes(czdq,2);
        ByteUtils.fillbytesByPosition(formatBytes,czdqbytes,5,4);
        formatBytes[2] = 1;
        formatBytes[1] = 1;
        ArrayUtils.reverse(formatBytes);
        String format = ByteUtils.bytesToHexByNum(formatBytes);
        stringBuilder.append("16 03")
                //分区编号
                .append(ByteUtils.intToHexStringXd(Integer.parseInt(areaNum),4))
                //保留
                .append("00 00")
                //节目样式 00为临时节目，02为永久节目
                .append(style).append("00 00 00")
                //文本显示格式
                .append(ByteUtils.hexToHexStringXd(format,8))
                //OFFSET
                .append("00 00 00 00");
        return handleFrame(StrUtil.cleanBlank(stringBuilder.toString()));
    }



    /**
     * 数字时钟
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
    public String showDataTime(String areaNum,String style,String color,String textId,String halign,String czdq,String content) throws Exception{
        StringBuilder stringBuilder = new StringBuilder();
        byte[] formatBytes = new byte[32];
        ByteUtils.initArray(formatBytes);
        //颜色ID
        byte[] colorBytes = ByteUtils.stringTobytes(color,4);
        ByteUtils.fillbytesByPosition(formatBytes,colorBytes,19,16);
        //字体ID
        byte[] textIdbytes = ByteUtils.stringTobytes(Integer.toBinaryString(Integer.parseInt(textId)),8);
        ByteUtils.fillbytesByPosition(formatBytes,textIdbytes,15,8);
        //水平对齐
        byte[] halignbytes = ByteUtils.stringTobytes(halign,2);
        ByteUtils.fillbytesByPosition(formatBytes,halignbytes,7,6);
        //垂直对齐
        byte[] czdqbytes = ByteUtils.stringTobytes(czdq,2);
        ByteUtils.fillbytesByPosition(formatBytes,czdqbytes,5,4);
        formatBytes[2] = 1;
        formatBytes[1] = 1;
        ArrayUtils.reverse(formatBytes);
        String format = ByteUtils.bytesToHexByNum(formatBytes);
        byte[] contentbytes = content.getBytes("GBK");
        int textSize = contentbytes.length;
        stringBuilder.append("17 03")
                //分区编号
                .append(ByteUtils.intToHexStringXd(Integer.parseInt(areaNum),4))
                //保留
                .append("00 00")
                //节目样式 00为临时节目，02为永久节目
                .append(style).append("00 00 00")
                //文本显示格式
                .append(ByteUtils.hexToHexStringXd(format,8))
                //OFFSET
                .append("00 00 00 00")
                //TEXT 字节数
                .append(ByteUtils.intToHexStringXd(textSize,4))
                //text
                .append(ByteUtils.byteArrayToHexString(contentbytes));
        return handleFrame(StrUtil.cleanBlank(stringBuilder.toString()));
    }


    /**
     * 修改区域
     * @param areaNum
     * @param sx
     * @param sy
     * @param width
     * @param height
     * @return
     */
    @Override
    public String modifyArea(String areaNum,String sx,String sy,String width,String height){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("04 03")
                //区序号
                .append(ByteUtils.intToHexStringXd(Integer.parseInt(areaNum),4))
                //x坐标
                .append(ByteUtils.intToHexStringXd(Integer.parseInt(sx),4))
                //y坐标
                .append(ByteUtils.intToHexStringXd(Integer.parseInt(sy),4))
                //分区宽度
                .append(ByteUtils.intToHexStringXd(Integer.parseInt(width),4))
                //分区高度
                .append(ByteUtils.intToHexStringXd(Integer.parseInt(height),4))
                //创建永久分区
                .append("02 00 00 00")
                //保留
                .append("00 00 00 00");
        return handleFrame(StrUtil.cleanBlank(stringBuilder.toString()));
    }


    /**
     * 删除分区
     * @param areaNum
     * @return
     */
    @Override
    public String deleteArea(String areaNum){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("05 03")
                //区序号
                .append(ByteUtils.intToHexStringXd(Integer.parseInt(areaNum),4));
        return handleFrame(StrUtil.cleanBlank(stringBuilder.toString()));
    }


    /**
     * 发送语音
     * @param areaNum
     * @return
     */
    @Override
    public String sendSpeak(String content) throws Exception{
        StringBuilder stringBuilder = new StringBuilder();
        byte[] contentbytes = content.getBytes("GB2312");
        int textSize = contentbytes.length;
        stringBuilder.append("00 07")
                //选项
                .append("00 00 00 00")
                //字符个数
                .append(ByteUtils.intToHexStringXd(textSize,4))
                //text
                .append(ByteUtils.byteArrayToHexString(contentbytes));
        return handleFrame(StrUtil.cleanBlank(stringBuilder.toString()));
    }


    /**
     * 调节音量
     * @param level
     * @return
     * @throws Exception
     */
    @Override
    public String adjustVol(String level) throws Exception{
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("07 07")
                //音量
                .append(ByteUtils.intToHexStringXd(Integer.parseInt(level),4))
                .append("00 00 00 00 00 00");
        return handleFrame(StrUtil.cleanBlank(stringBuilder.toString()));
    }


    /**
     * 停止语音合成
     * @return
     */
    @Override
    public String stopSpeak(){
        return "AAA50800FFFF0000B0A1010700005A55";
    }

}
