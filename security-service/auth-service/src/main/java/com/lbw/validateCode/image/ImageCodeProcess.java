package com.lbw.validateCode.image;


import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import com.lbw.validateCode.AbStractValidateCodeProcess;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by lenovo on 2018-03-07.
 */
@Component("imageValidateCodeProcess")
public class ImageCodeProcess extends AbStractValidateCodeProcess<ImageCode> {

    @Override
    protected void send(ServletWebRequest request, ImageCode imageCode) throws Exception {
    	HttpServletResponse response = request.getResponse();
    	response.setHeader("Pragma","no-cache");
        response.setHeader("Cache-Control","no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        ImageIO.write(imageCode.getBufferedImage(),"JPEG",response.getOutputStream());
    }
}
