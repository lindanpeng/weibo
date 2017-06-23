package com.alphaking.pojo.weibo;

import java.awt.image.BufferedImage;

public class VerificationCode {
private String codeText;
private BufferedImage codeImage;
public String getCodeText() {
	return codeText;
}
public void setCodeText(String codeText) {
	this.codeText = codeText;
}
public BufferedImage getCodeImage() {
	return codeImage;
}
public void setCodeImage(BufferedImage codeImage) {
	this.codeImage = codeImage;
}

}
