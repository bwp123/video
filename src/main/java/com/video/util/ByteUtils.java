package com.video.util;


import org.apache.commons.lang3.ArrayUtils;
import java.nio.ByteBuffer;
import java.util.Locale;

/**
 * Byte转换工具
 * com.boz.gscada.util
 *
 * @author lise
 * @comment
 * @date 2019/11/20 5:08 下午
 */
public class ByteUtils {

    /**
     * 获取一个字节的bit数组
     *
     * @param value
     * @return
     */
    public static byte[] getByteArray(byte value) {
        byte[] byteArr = new byte[8]; //一个字节八位
        for (int i = 7; i > 0; i--) {
            byteArr[i] = (byte) (value & 1); //获取最低位
            value = (byte) (value >> 1); //每次右移一位
        }
        if(value<0){
            //解决byte最高位是1的问题
            byteArr[0] = 1;
        }
        return byteArr;
    }

    /**
     * 把byte转为字符串的bit
     *
     * @param b
     * @return
     */
    public static String byteToBitString(byte b) {
        return ""
                + (byte) ((b >> 7) & 0x1) + (byte) ((b >> 6) & 0x1)
                + (byte) ((b >> 5) & 0x1) + (byte) ((b >> 4) & 0x1)
                + (byte) ((b >> 3) & 0x1) + (byte) ((b >> 2) & 0x1)
                + (byte) ((b >> 1) & 0x1) + (byte) ((b >> 0) & 0x1);
    }

    /**
     * 获取一个字节第n位,
     * 思路：右移n位，与1
     *
     * @param value
     * @param index
     * @return
     */
    public static int get(byte value, int index) {
        return (value >> index) & 0x1;
    }

    /**
     * 获取一个字节的第m到第n位
     *
     * @param value
     * @param start >0
     * @param end   >0
     * @return
     */
    public static byte[] getBitRange(byte value, int start, int end) {
        byte[] rangeArray = new byte[end - start + 1];
        if (start > 7 || start < 0) {
            throw new RuntimeException("illegal start param");
        }
        if (end > 7 || end < 0) {
            throw new RuntimeException("illegal end param");
        }
        if (start > end) {
            throw new RuntimeException("start can not bigger than end");
        }
        if (start == end) {
            rangeArray[0] = (byte) ByteUtils.get(value, start);
            return rangeArray;
        }
        for (int i = end; i < start; i--) {
            rangeArray[i] = (byte) ByteUtils.get(value, start);
        }
        return rangeArray;
    }

    /**
     * 位字符串转字节
     *
     * @param str
     * @return
     */
    public static byte bitStringToByte(String str) {
        if (null == str) {
            throw new RuntimeException("when bit string convert to byte, Object can not be null!");
        }
        if (8 != str.length()) {
            throw new RuntimeException("bit string'length must be 8");
        }
        try {
            //判断最高位，决定正负
            if (str.charAt(0) == '0') {
                return (byte) Integer.parseInt(str, 2);
            } else if (str.charAt(0) == '1') {
                return (byte) (Integer.parseInt(str, 2) - 256);
            }
        } catch (NumberFormatException e) {
            throw new RuntimeException("bit string convert to byte failed, byte String must only include 0 and 1!");
        }

        return 0;
    }

    /**
     * 转换short为byte
     *
     * @param s 需要转换的short
     */
    public static byte[] short2Bytes(short s) {
        byte[] b = new byte[2];
        b[0] = (byte) ((s & 0xFF00) >> 8);
        b[1] = (byte) (s & 0xFF);
        return b;
    }

    /**
     * 通过byte数组取到short
     *
     * @param b
     * @param index 第几位开始取
     * @return
     */
    public static short getShort(byte[] b, int index) {
        return (short) (((b[index + 1] << 8) | b[index + 0] & 0xff));
    }

    /**
     * 转换int为byte数组
     *
     * @param bb
     * @param x
     * @param index
     */
    public static void putInt(byte[] bb, int x, int index) {
        bb[index + 3] = (byte) (x >> 24);
        bb[index + 2] = (byte) (x >> 16);
        bb[index + 1] = (byte) (x >> 8);
        bb[index + 0] = (byte) (x >> 0);
    }

    /**
     * 通过byte数组取到int
     *
     * @param bb
     * @param index 第几位开始
     * @return
     */
    public static int getInt(byte[] bb, int index) {
        return (int) ((((bb[index + 3] & 0xff) << 24)
                | ((bb[index + 2] & 0xff) << 16)
                | ((bb[index + 1] & 0xff) << 8) | ((bb[index + 0] & 0xff) << 0)));
    }

    /**
     * 转换long型为byte数组
     *
     * @param bb
     * @param x
     * @param index
     */
    public static void putLong(byte[] bb, long x, int index) {
        bb[index + 7] = (byte) (x >> 56);
        bb[index + 6] = (byte) (x >> 48);
        bb[index + 5] = (byte) (x >> 40);
        bb[index + 4] = (byte) (x >> 32);
        bb[index + 3] = (byte) (x >> 24);
        bb[index + 2] = (byte) (x >> 16);
        bb[index + 1] = (byte) (x >> 8);
        bb[index + 0] = (byte) (x >> 0);
    }

    /**
     * 通过byte数组取到long
     *
     * @param bb
     * @param index
     * @return
     */
    public static long getLong(byte[] bb, int index) {
        return ((((long) bb[index + 7] & 0xff) << 56)
                | (((long) bb[index + 6] & 0xff) << 48)
                | (((long) bb[index + 5] & 0xff) << 40)
                | (((long) bb[index + 4] & 0xff) << 32)
                | (((long) bb[index + 3] & 0xff) << 24)
                | (((long) bb[index + 2] & 0xff) << 16)
                | (((long) bb[index + 1] & 0xff) << 8) | (((long) bb[index + 0] & 0xff) << 0));
    }

    /**
     * 字符到字节转换
     *
     * @param ch
     * @return
     */
    public static void putChar(byte[] bb, char ch, int index) {
        int temp = (int) ch;
        // byte[] b = new byte[2];
        for (int i = 0; i < 2; i++) {
            bb[index + i] = new Integer(temp & 0xff).byteValue(); // 将最高位保存在最低位
            temp = temp >> 8; // 向右移8位
        }
    }

    /**
     * 字节到字符转换
     *
     * @param b
     * @return
     */
    public static char getChar(byte[] b, int index) {
        int s = 0;
        if (b[index + 1] > 0)
            s += b[index + 1];
        else
            s += 256 + b[index + 0];
        s *= 256;
        if (b[index + 0] > 0)
            s += b[index + 1];
        else
            s += 256 + b[index + 0];
        char ch = (char) s;
        return ch;
    }

    /**
     * float转换byte
     *
     * @param bb
     * @param x
     * @param index
     */
    public static void putFloat(byte[] bb, float x, int index) {
        // byte[] b = new byte[4];
        int l = Float.floatToIntBits(x);
        for (int i = 0; i < 4; i++) {
            bb[index + i] = new Integer(l).byteValue();
            l = l >> 8;
        }
    }

    /**
     * 通过byte数组取得float
     *
     * @param
     * @param index
     * @return
     */
    public static float getFloat(byte[] b, int index) {
        int l;
        l = b[index + 0];
        l &= 0xff;
        l |= ((long) b[index + 1] << 8);
        l &= 0xffff;
        l |= ((long) b[index + 2] << 16);
        l &= 0xffffff;
        l |= ((long) b[index + 3] << 24);
        return Float.intBitsToFloat(l);
    }

    /**
     * double转换byte
     *
     * @param bb
     * @param x
     * @param index
     */
    public static void putDouble(byte[] bb, double x, int index) {
        // byte[] b = new byte[8];
        long l = Double.doubleToLongBits(x);
        for (int i = 0; i < 4; i++) {
            bb[index + i] = new Long(l).byteValue();
            l = l >> 8;
        }
    }

    /**
     * 通过byte数组取得float
     *
     * @param
     * @param index
     * @return
     */
    public static double getDouble(byte[] b, int index) {
        long l;
        l = b[0];
        l &= 0xff;
        l |= ((long) b[1] << 8);
        l &= 0xffff;
        l |= ((long) b[2] << 16);
        l &= 0xffffff;
        l |= ((long) b[3] << 24);
        l &= 0xffffffffl;
        l |= ((long) b[4] << 32);
        l &= 0xffffffffffl;
        l |= ((long) b[5] << 40);
        l &= 0xffffffffffffl;
        l |= ((long) b[6] << 48);
        l &= 0xffffffffffffffl;
        l |= ((long) b[7] << 56);
        return Double.longBitsToDouble(l);
    }

    /**
     * 十六进制字符串转byte[]
     *
     * @param hex 十六进制字符串
     * @return byte[]
     */
    public static byte[] hexStr2Byte(String hex) {
        if (hex == null) {
            return new byte[]{};
        }

        // 奇数位补0
        if (hex.length() % 2 != 0) {
            hex = "0" + hex;
        }

        int length = hex.length();
        ByteBuffer buffer = ByteBuffer.allocate(length / 2);
        for (int i = 0; i < length; i++) {
            String hexStr = hex.charAt(i) + "";
            i++;
            hexStr += hex.charAt(i);
            byte b = (byte) Integer.parseInt(hexStr, 16);
            buffer.put(b);
        }
        return buffer.array();
    }

    /**
     * byte[]转十六进制字符串
     *
     * @param array byte[]
     * @return 十六进制字符串
     */
    public static String byteArrayToHexString(byte[] array) {
        if (array == null) {
            return "";
        }
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < array.length; i++) {
            buffer.append(byteToHex(array[i]));
        }
        return buffer.toString();
    }

    /**
     * byte转十六进制字符
     *
     * @param b byte
     * @return 十六进制字符
     */
    public static String byteToHex(byte b) {
        String hex = Integer.toHexString(b & 0xFF);
        if (hex.length() == 1) {
            hex = '0' + hex;
        }
        return hex.toUpperCase(Locale.getDefault());
    }

    /**
     * 位字符串转为16进制字符串
     * @param bytes
     * @return
     */
    public static String bytesToHex(byte[] bytes){
        StringBuilder stringBuilder = new StringBuilder();
        for(byte i:bytes){
            stringBuilder.append(i);
        }
        long keys = Long.parseLong(stringBuilder.toString(), 2);//
        String result = Long.toHexString(keys).toUpperCase();
        return result;
    }


    public static String bytesToHexByNum(byte[] resultBytes){
        StringBuilder stringBuilder = new StringBuilder();
        int bytesize = resultBytes.length-1;
        for(int i=0;i<bytesize;i=i+4){
            //每4位转换为一个16进制数字
            String tmpString = String.valueOf(resultBytes[i]) + String.valueOf(resultBytes[i+1])+ String.valueOf(resultBytes[i+2])+ String.valueOf(resultBytes[i+3]);
            int keys = Integer.parseInt(tmpString, 2);
            stringBuilder.append(Integer.toHexString(keys).toUpperCase());
        }
        String result = stringBuilder.toString();
        return result;
    }

    /**
     * 16进制字符串转为位字符串（指定位字符串的大小）
     * @param
     * @return
     */
    public static byte[] HexTobytes(String hexString,int size){
        //先转换为字节
        byte[] bytes = ByteUtils.hexStr2Byte(hexString);
        byte[] tmpbytes = new byte[0];
        //字节转换为位数组
        for (byte b : bytes){
            tmpbytes = ArrayUtils.addAll(tmpbytes, ByteUtils.getByteArray(b));
        }
        int length = tmpbytes.length;
        if(length>=size){
            return ArrayUtils.subarray(tmpbytes,length-size,length);
        }else {
            //将前面的位数补充为0
            byte[] zerobytes = new byte[size-length];
            ByteUtils.initArray(zerobytes);
            return ArrayUtils.addAll(zerobytes, tmpbytes);
        }

    }

    /**
     * 填充指定位置的位
     * @param bytes
     * @param parambytes
     * @param start
     * @param end
     */
    public static void fillbytesByPosition(byte[] bytes,byte[] parambytes,int start,int end){
        if(end>=start){
            int size = end - start;
            for (int i = 0;i<=size;i++){
                bytes[start++] = parambytes[i];
            }
        }else {
            int size = start - end;
            for (int i = 0;i<=size;i++){
                bytes[start--] = parambytes[i];
            }
        }

    }


    /**
     * 将字符串转为位字节
     * @param s
     * @return
     */
    public static byte[] stringTobytes(String s,int ss){
        //先把差的位数给补上
        String zero = "000000000000000000000000000000000000000000000000000000";
        int a = ss-s.length();
        if(a>0){
            s = zero.substring(0,a)+s;
        }
        int len = s.length();
        byte[] bytes = new byte[len];
        for (int i=0;i<len;i++){
            bytes[i] = Byte.valueOf(s.substring(i,i+1));
        }
        return bytes;
    }

    /**
     * 将所有位初始化为0
     * @param bytes
     */
    public static void initArray(byte[] bytes){
        for (byte b : bytes){
            b = 0;
        }
    }


    /**
     * 反转数组并返回数组
     * @param bytes
     * @return
     */
    public static byte[] reverse(byte[] bytes){
        ArrayUtils.reverse(bytes);
        return bytes;
    }


    /**
     * 十进制转换为16进制
     * @param
     * @return
     */
    public static String intToHexString(int i){
        String data =  Integer.toHexString(i).toUpperCase();
        if(data.length()%2!=0){
            data = "0"+data;
        }
        return data;
    }

    /**
     * 十进制转换为16进制(小端模式)
     * @param
     * @return
     */
    public static String intToHexStringXd(int i,int len){
        String zero = "000000000000";
        String data =  Integer.toHexString(i).toUpperCase();
        if(len>data.length()){
            int shortNum = len-data.length();
            data = zero.substring(0,shortNum)+data;
        }
        int size = data.length();
        String result="";
        for (int j = 0;j<size;j=j+2){
            result =data.substring(j,j+2)+result;
        }
        return result;
    }


    public static String hexToHexStringXd(String data,int len){
        String zero = "000000000000000000000000000000000000000000000";
        if(len>data.length()){
            int shortNum = len-data.length();
            data = zero.substring(0,shortNum)+data;
        }
        int size = data.length();
        StringBuilder result=new StringBuilder();
        for (int j = 0;j<size;j=j+2){
            result = result.insert(0,data.substring(j,j+2));
        }
        return result.toString();
    }


    public static String testString(byte[] bytes){
        StringBuilder stringBuilder = new StringBuilder();
        for(byte i:bytes){
            stringBuilder.append(i);
        }
        return stringBuilder.toString();
    }
}
