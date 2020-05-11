package com.video.model;

import java.lang.reflect.Field;

/**
 * @Author biwenpan
 * @create 2020/4/28 8:36
 * @description
 */
public class Scene {
    private String sceneId;
    private String swopStatus;
    private String strqrCode;
    private String curProcInfo;
    private String prepaRemark;
    private String advertising;
    private String uteventhdlTips;
    private String conActivityTips;
    private String sysTime;

    public String getSceneId() {
        return sceneId;
    }

    public void setSceneId(String sceneId) {
        this.sceneId = sceneId;
    }

    public String getSwopStatus() {
        return swopStatus;
    }

    public void setSwopStatus(String swopStatus) {
        this.swopStatus = swopStatus;
    }

    public String getStrqrCode() {
        return strqrCode;
    }

    public void setStrqrCode(String strqrCode) {
        this.strqrCode = strqrCode;
    }

    public String getCurProcInfo() {
        return curProcInfo;
    }

    public void setCurProcInfo(String curProcInfo) {
        this.curProcInfo = curProcInfo;
    }

    public String getPrepaRemark() {
        return prepaRemark;
    }

    public void setPrepaRemark(String prepaRemark) {
        this.prepaRemark = prepaRemark;
    }

    public String getAdvertising() {
        return advertising;
    }

    public void setAdvertising(String advertising) {
        this.advertising = advertising;
    }

    public String getUteventhdlTips() {
        return uteventhdlTips;
    }

    public void setUteventhdlTips(String uteventhdlTips) {
        this.uteventhdlTips = uteventhdlTips;
    }

    public String getConActivityTips() {
        return conActivityTips;
    }

    public void setConActivityTips(String conActivityTips) {
        this.conActivityTips = conActivityTips;
    }

    public String getSysTime() {
        return sysTime;
    }

    public void setSysTime(String sysTime) {
        this.sysTime = sysTime;
    }

    public String toString() {
        return "sceneid:" + this.sceneId + ",swopstatus:" + this.swopStatus + ",strqrcode:" + this.strqrCode
                + ",curprocinfo:" + this.curProcInfo + ",preparemark:" + this.prepaRemark + ",advertising:"
                + this.advertising + ",uteventhdltips:" + this.uteventhdlTips + ",conactivitytips:"
                + this.conActivityTips + ",systime:" + this.sysTime;
    }

    public static Scene loadFromXML(String xml) {
        if (xml.isEmpty()) {
            return null;
        }
        xml = xml.toLowerCase();
        xml = xml.replaceAll(" ", "");
        Scene scene = new Scene();
        Field[] fields = scene.getClass().getDeclaredFields();
        String fieldName = "", fieldValue = "";
        int fieldLen = 0;
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                fieldName = field.getName().toLowerCase();
                fieldLen = ("<" + fieldName + ">").length();
                if (xml.indexOf("<" + fieldName + ">") != -1 && xml.indexOf("</" + fieldName + ">") != -1) {
                    fieldValue = xml.substring(xml.indexOf("<" + fieldName + ">") + fieldLen,
                            xml.indexOf("</" + fieldName + ">"));
                    String type = field.getType().toString();
                    if (type.endsWith("String")) {
                        field.set(scene, fieldValue);
                    }
                }
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return scene;
    }

}
