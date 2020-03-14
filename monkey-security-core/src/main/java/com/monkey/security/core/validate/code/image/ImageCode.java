package com.monkey.security.core.validate.code.image;

import com.monkey.security.core.validate.code.ValidateCode;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

/**
 * 图片验证码
 * @author: monkey
 * @date: 2018/10/13 15:21
 */
public class ImageCode extends ValidateCode {

    //验证码图片
    private BufferedImage image;

    /**
     *
     * @param image 图片
     * @param code 验证码值
     * @param expireIn 过期时间（单位：秒）
     */
    public ImageCode(BufferedImage image, String code, int expireIn) {
        super(code,expireIn);
        this.image = image;
    }

    public ImageCode(BufferedImage image, String code, LocalDateTime expireTime) {
        super(code,expireTime);
        this.image = image;
    }



    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

}
