package com.video.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @Author chen.cheng
 * @create 2020/4/23 10:54
 * @description
 */
public class SocketUtils {

    /**
     * 打开socket,返回socket对象
     */
    public static Socket getSocket(String host, int port) {
        Socket socket = null;
        try {
            socket = new Socket(host, port);
            return socket;
        } catch (Exception e) {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 通过socket发送信息
     * @param socket
     * @param bytes
     */
    public static void sendMessage(Socket socket, byte[] bytes) {
        try {
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write(bytes);
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 从socket输入流中获取byte数组
     * @param socket
     * @return
     */
    public static byte[] getMessage(Socket socket){
        try {
            InputStream inputStream = socket.getInputStream();
            int size = inputStream.available();
            byte[] resp = new byte[size];
            inputStream.read(resp);
            return resp;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 关闭socket
     *
     * @param socket
     */
    public static void closeSocket(Socket socket) {
        try {
            if (socket != null) {
                InputStream inputStream = socket.getInputStream();
                OutputStream outputStream = socket.getOutputStream();
                socket.close();
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
