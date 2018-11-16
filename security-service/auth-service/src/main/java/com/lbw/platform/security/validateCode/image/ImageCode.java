package com.lbw.platform.security.validateCode.image;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

import com.lbw.platform.security.validateCode.ValidateCode;

/**
 * Created by lenovo on 2018-03-06.
 * 图片生成器参数
 */
public class ImageCode  extends ValidateCode {


    private BufferedImage bufferedImage;


    public ImageCode(){
    }

    public ImageCode(String valiCode,BufferedImage bufferedImage,LocalDateTime expireDateTime ){
        super(valiCode,expireDateTime);
    }

    public ImageCode(String valiCode,BufferedImage bufferedImage,int expireIn ){
        this.setValiCode(valiCode);
        this.bufferedImage =bufferedImage;
        this.setExpireTime( LocalDateTime.now().plusSeconds(expireIn));
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    public void setBufferedImage(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
    }

}
