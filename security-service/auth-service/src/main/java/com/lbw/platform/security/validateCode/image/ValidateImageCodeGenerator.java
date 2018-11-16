package com.lbw.platform.security.validateCode.image;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import com.lbw.platform.security.properties.SecurityProperties;
import com.lbw.platform.security.validateCode.ValidateCodeGenerator;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * Created by lenovo on 2018-03-06.
 * 图片生成器
 */

public class ValidateImageCodeGenerator implements ValidateCodeGenerator {


    private static final Logger logger = LoggerFactory.getLogger(ValidateImageCodeGenerator.class);
    public static final String SESSION_IMAGE_KEY= "SESSION_IMAGE_KEY";//放到session中的key

    private Random random = new Random();

    private SecurityProperties securityProperties ;

    /**
     * 获得字体
     */
    private Font getFont() {
        return new Font("Fixedsys", Font.CENTER_BASELINE, 18);
    }

    /**
     * 获得颜色
     */
    private Color getRandColor(int fc, int bc) {
        if (fc > 255)
            fc = 255;
        if (bc > 255)
            bc = 255;
        int r = fc + random.nextInt(bc - fc - 16);
        int g = fc + random.nextInt(bc - fc - 14);
        int b = fc + random.nextInt(bc - fc - 18);
        return new Color(r, g, b);
    }

    /**
     * 生成随机图片
     */
    public ImageCode getGenerator(ServletWebRequest request) {

        int width = ServletRequestUtils.getIntParameter(request.getRequest(), "width",
                securityProperties.getValiCode().getImageCode().getWidth());
        int height = ServletRequestUtils.getIntParameter(request.getRequest(), "height",
                securityProperties.getValiCode().getImageCode().getHeight());

      //  HttpSession session = request.getSession();
        // BufferedImage类是具有缓冲区的Image类,Image类是用于描述图像信息的类
        BufferedImage image = new BufferedImage(width,height
                , BufferedImage.TYPE_INT_BGR);
        Graphics g = image.getGraphics();// 产生Image对象的Graphics对象,改对象可以在图像上进行各种绘制操作
        g.fillRect(0, 0, width, height);//图片大小
        g.setFont(new Font("Times New Roman", Font.ROMAN_BASELINE, 18));//字体大小
        g.setColor(getRandColor(110, 133));//字体颜色
        // 绘制干扰线
        for (int i = 0; i <= securityProperties.getValiCode().getImageCode().getLineSize(); i++) {
            drowLine(g);
        }
        // 绘制随机字符
        String randomString = "";
        for (int i = 1; i <= securityProperties.getValiCode().getImageCode().getLength(); i++) {
            randomString = drowString(g, randomString, i);
        }
        logger.info(randomString);
        g.dispose();
        return new ImageCode(randomString,image, securityProperties.getValiCode().getImageCode().getExpireIn());
    }

    /**
     * 绘制字符串
     */
    private String drowString(Graphics g, String randomString, int i) {
        g.setFont(getFont());
        g.setColor(new Color(random.nextInt(101), random.nextInt(111), random
                .nextInt(121)));
        String rand = String.valueOf(getRandomString(random.nextInt(securityProperties.getValiCode().getImageCode().getRandString().length())));
        randomString += rand;
        g.translate(random.nextInt(3), random.nextInt(3));
        g.drawString(rand, 13 * i, 16);
        return randomString;
    }

    /**
     * 绘制干扰线
     */
    private void drowLine(Graphics g) {
        int x = random.nextInt(securityProperties.getValiCode().getImageCode().getWidth());
        int y = random.nextInt(securityProperties.getValiCode().getImageCode().getHeight());
        int xl = random.nextInt(13);
        int yl = random.nextInt(15);
        g.drawLine(x, y, x + xl, y + yl);
    }

    /**
     * 获取随机的字符
     */
    public String getRandomString(int num) {
        return String.valueOf(securityProperties.getValiCode().getImageCode().getRandString().charAt(num));
    }

    public SecurityProperties getSecurityProperties() {
        return securityProperties;
    }

    public void setSecurityProperties(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }


}
