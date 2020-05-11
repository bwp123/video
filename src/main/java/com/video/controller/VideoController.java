package com.video.controller;

import com.video.util.QRcodeUtil;
import com.video.websocket.WebSocketServer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author biwenpan
 * @create 2020/4/28 8:36
 * @description
 */
@Controller
@RequestMapping(value = "/video", produces = "text/plain;charset=utf-8")
public class VideoController {

   public static final String userId = "1";

    /**
     *进入首页
    */
    @RequestMapping("/index")
    public String index(){
        return "index";
    }

    @RequestMapping("/receivePositionSdjust")
    public ResponseEntity<String> receivePositionSdjust(@RequestParam("commandType")String commandType)  {

        try{
            WebSocketServer.sendInfo(commandType,userId);
        }catch(IOException e){
            return new ResponseEntity("Failed", HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok("MSG SEND SUCCESS");
    }

    @RequestMapping("/getQRcode")
    public void getQRcode(String content,HttpServletResponse response) {
        //参数为二维码的内容、长、宽、响应对象
        QRcodeUtil.creatRrCode(content,230,230,response);
    }


}
