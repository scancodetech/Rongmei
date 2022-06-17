package com.rongmei.util;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageCreateEntity {

  /**
   * 宽度
   */
  private Integer width = 100;
  /**
   * 高度
   */
  private Integer height = 100;
  /*==============图片内容==============*/
  /**
   * 图片内容
   */
  private BufferedImage imgContent;
  /**
   * 图片宽度
   */
  private int imgWidth = 100;
  /**
   * 图片宽度
   */
  private int imgHeight = 100;
  /**
   * 图片渲染X起点
   */
  private int imgX;
  /**
   * 图片渲染Y轴起点
   */
  private int imgY;
  /*=====================文本内容===================*/
  /**
   * 文本内容
   */
  private String textContent;
  /**
   * 字体名称
   */
  private String fontName = "";
  /**
   * 字体文件路径
   */
  private String fontFilePath = "";
  /**
   * 字体尺寸
   */
  private float fontSize = 20f;
  /**
   * 字体风格
   */
  private int fontStyle = Font.PLAIN;
  /**
   * 字体颜色
   */
  private Color fontColor = Color.BLACK;
  /**
   * 字体间距，默认值为零，与当前字体尺寸相关
   */
  private Integer fontSpace = 0;
  /**
   * 行距
   */
  private Integer linePadding = 10;
  /**
   * 文本透明度：值从0-1.0，依次变得不透明
   */
  private float textTransparency = 1.0f;
  /**
   * 文本渲染X起点
   */
  private Integer textX = 0;
  /**
   * 文本渲染Y轴起点
   */
  private Integer textY = 0;
  /**
   * 左边距
   */
  private Integer textLeftPadding = 0;
  /**
   * 右边距
   */
  private Integer textRightPadding = 0;
  /**
   * 每行居中
   */
  private boolean isCenterLine;
  /*=================背景==============*/
  /**
   * 背景颜色
   */
  private Color backgroundColor = Color.WHITE;
  /**
   * 背景是否透明
   */
  private boolean isTransparentBackground = false;
  /**
   * 背景图片
   */
  private BufferedImage backgroundImg;

  public ImageCreateEntity() {
  }

  public Integer getWidth() {
    return width;
  }

  public void setWidth(Integer width) {
    this.width = width;
  }

  public Integer getHeight() {
    return height;
  }

  public void setHeight(Integer height) {
    this.height = height;
  }

  public BufferedImage getImgContent() {
    return imgContent;
  }

  public void setImgContent(BufferedImage imgContent) {
    this.imgContent = imgContent;
  }

  public int getImgWidth() {
    return imgWidth;
  }

  public void setImgWidth(int imgWidth) {
    this.imgWidth = imgWidth;
  }

  public int getImgHeight() {
    return imgHeight;
  }

  public void setImgHeight(int imgHeight) {
    this.imgHeight = imgHeight;
  }

  public int getImgX() {
    return imgX;
  }

  public void setImgX(int imgX) {
    this.imgX = imgX;
  }

  public int getImgY() {
    return imgY;
  }

  public void setImgY(int imgY) {
    this.imgY = imgY;
  }

  public String getTextContent() {
    return textContent;
  }

  public void setTextContent(String textContent) {
    this.textContent = textContent;
  }

  public String getFontName() {
    return fontName;
  }

  public void setFontName(String fontName) {
    this.fontName = fontName;
  }

  public String getFontFilePath() {
    return fontFilePath;
  }

  public void setFontFilePath(String fontFilePath) {
    this.fontFilePath = fontFilePath;
  }

  public float getFontSize() {
    return fontSize;
  }

  public void setFontSize(float fontSize) {
    this.fontSize = fontSize;
  }

  public int getFontStyle() {
    return fontStyle;
  }

  public void setFontStyle(int fontStyle) {
    this.fontStyle = fontStyle;
  }

  public Color getFontColor() {
    return fontColor;
  }

  public void setFontColor(Color fontColor) {
    this.fontColor = fontColor;
  }

  public Integer getFontSpace() {
    return fontSpace;
  }

  public void setFontSpace(Integer fontSpace) {
    this.fontSpace = fontSpace;
  }

  public Integer getLinePadding() {
    return linePadding;
  }

  public void setLinePadding(Integer linePadding) {
    this.linePadding = linePadding;
  }

  public float getTextTransparency() {
    return textTransparency;
  }

  public void setTextTransparency(float textTransparency) {
    this.textTransparency = textTransparency;
  }

  public Integer getTextX() {
    return textX;
  }

  public void setTextX(Integer textX) {
    this.textX = textX;
  }

  public Integer getTextY() {
    return textY;
  }

  public void setTextY(Integer textY) {
    this.textY = textY;
  }

  public Integer getTextLeftPadding() {
    return textLeftPadding;
  }

  public void setTextLeftPadding(Integer textLeftPadding) {
    this.textLeftPadding = textLeftPadding;
  }

  public Integer getTextRightPadding() {
    return textRightPadding;
  }

  public void setTextRightPadding(Integer textRightPadding) {
    this.textRightPadding = textRightPadding;
  }

  public boolean isCenterLine() {
    return isCenterLine;
  }

  public void setCenterLine(boolean centerLine) {
    isCenterLine = centerLine;
  }

  public Color getBackgroundColor() {
    return backgroundColor;
  }

  public void setBackgroundColor(Color backgroundColor) {
    this.backgroundColor = backgroundColor;
  }

  public boolean isTransparentBackground() {
    return isTransparentBackground;
  }

  public void setTransparentBackground(boolean transparentBackground) {
    isTransparentBackground = transparentBackground;
  }

  public BufferedImage getBackgroundImg() {
    return backgroundImg;
  }

  public void setBackgroundImg(BufferedImage backgroundImg) {
    this.backgroundImg = backgroundImg;
  }
}
