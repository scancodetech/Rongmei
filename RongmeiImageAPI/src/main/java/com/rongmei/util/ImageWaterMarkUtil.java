package com.rongmei.util;

import static org.opencv.highgui.Highgui.imread;
import static org.opencv.highgui.Highgui.imwrite;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

public class ImageWaterMarkUtil {

  static {
    //加载opencv动态库
    System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
  }

  /**
   * bufferedImage convert mat
   */
  public static Mat convertMat(BufferedImage im) {
    // Convert INT to BYTE
    im = toBufferedImageOfType(im, BufferedImage.TYPE_3BYTE_BGR);
    // Convert bufferedimage to byte array
    byte[] pixels = ((DataBufferByte) im.getRaster().getDataBuffer())
        .getData();
    // Create a Matrix the same size of image
    Mat image = new Mat(im.getHeight(), im.getWidth(), 16);
    // Fill Matrix with image values
    image.put(0, 0, pixels);
    return image;
  }

  /**
   * Mat转换成BufferedImage
   *
   * @param matrix 要转换的Mat
   * @param fileExtension 格式为 ".jpg", ".png", etc
   */
  public static BufferedImage mat2BufImg(Mat matrix, String fileExtension) {
    // convert the matrix into a matrix of bytes appropriate for
    // this file extension
    MatOfByte mob = new MatOfByte();
    Highgui.imencode(fileExtension, matrix, mob);
    // convert the "matrix of bytes" into a byte array
    byte[] byteArray = mob.toArray();
    BufferedImage bufImage = null;
    try {
      InputStream in = new ByteArrayInputStream(byteArray);
      bufImage = ImageIO.read(in);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return bufImage;
  }

  /**
   * 8-bit RGBA convert 8-bit RGB
   */
  private static BufferedImage toBufferedImageOfType(BufferedImage original, int type) {
    if (original == null) {
      throw new IllegalArgumentException("original == null");
    }

    // Don't convert if it already has correct type
    if (original.getType() == type) {
      return original;
    }
    // Create a buffered image
    BufferedImage image = new BufferedImage(original.getWidth(), original.getHeight(), type);
    // Draw the image onto the new buffer
    Graphics2D g = image.createGraphics();
    try {
      g.setComposite(AlphaComposite.Src);
      g.drawImage(original, 0, 0, null);
    } finally {
      g.dispose();
    }
    return image;
  }

  /**
   * 嵌入水印信息
   */
  public static Mat addImageWatermarkWithText(Mat image, int[][] watermark, double p) {
    List<Mat> allPlanes = new ArrayList<>();

    Mat Ycbcr = new Mat(image.rows(), image.cols(), CvType.CV_8UC1);
    Imgproc.cvtColor(image, Ycbcr, Imgproc.COLOR_RGB2YCrCb);
    Core.split(image, allPlanes);
    //获取YMat矩阵
    Mat YMat = allPlanes.get(0);

    //分成4096块
    for (int i = 0; i < watermark.length; i++) {
      for (int j = 0; j < watermark[0].length; j++) {
        //block 表示分块 而且为 方阵
        int length = image.rows() / watermark.length;
        Mat block;
        //提取每个分块
        block = getImageValue(YMat, i, j, length);

        double[] a = new double[1];
        double[] c = new double[1];

        int x1 = 1, y1 = 2;
        int x2 = 2, y2 = 1;

        a = block.get(x1, y1);
        c = block.get(x2, y2);

        //对分块进行DCT变换
        Core.dct(block, block);

        a = block.get(x1, y1);
        c = block.get(x2, y2);

        if (watermark[i][j] == 1) {
          block.put(x1, y1, p);
          block.put(x2, y2, 0);
        }

        if (watermark[i][j] == 0) {
          block.put(x1, y1, 0);
          block.put(x2, y2, p);
        }

        //对上面分块进行IDCT变换
        Core.idct(block, block);
        for (int m = 0; m < length; m++) {
          for (int t = 0; t < length; t++) {
            double[] e = block.get(m, t);
            YMat.put(i * length + m, j * length + t, e);
          }
        }
      }

    }

    Mat imageOut = new Mat();
    Core.merge(allPlanes, imageOut);

    return imageOut;
  }

  /**
   * 提取水印信息
   *
   * @return int[][]
   */
  public static int[][] getImageWatermarkWithText(Mat image, double p) {

    List<Mat> allPlanes = new ArrayList<Mat>();

    Mat Ycbcr = new Mat(image.rows(), image.cols(), CvType.CV_8UC1);
    Imgproc.cvtColor(image, Ycbcr, Imgproc.COLOR_RGB2YCrCb);
    Core.split(image, allPlanes);

    Mat YMat = allPlanes.get(0);

    int watermark[][] = new int[64][64];

    //分成64块，提取每块嵌入的水印信息
    for (int i = 0; i < 64; i++) {
      for (int j = 0; j < 64; j++) {
        //block 表示分块 而且为 方阵
        int length = image.rows() / watermark.length;
        Mat block = null;
        //提取每个分块
        block = getImageValue(YMat, i, j, length);

        //对分块进行DCT变换
        Core.dct(block, block);
        //用于容纳DCT系数
        double[] a;
        double[] c;

        int x1 = 1, y1 = 2;
        int x2 = 2, y2 = 1;

        a = block.get(x1, y1);
        c = block.get(x2, y2);

        if (a[0] >= c[0]) {
          watermark[i][j] = 1;
        }
      }
    }
    return watermark;
  }

  /**
   * 提取每个分块
   */
  public static Mat getImageValue(Mat YMat, int x, int y, int length) {
    Mat mat = new Mat(length, length, CvType.CV_32F);
    for (int i = 0; i < length; i++) {
      for (int j = 0; j < length; j++) {

        double[] temp = YMat.get(x * length + i, y * length + j);
        mat.put(i, j, temp);
      }
    }
    return mat;
  }

  /**
   * 获取二值图的信息
   */
  public static int[][] getInformationOfBinaryGraph(String srcPath) {
    int[][] waterMark = new int[64][64];

    Mat mat = imread(srcPath);

    int width = 64;
    int height = 64;
    double a[] = new double[3];
    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        a = mat.get(i, j);
        if ((int) a[0] == 255) {
          waterMark[i][j] = 1;
        } else {
          waterMark[i][j] = 0;
        }
      }
    }

    return waterMark;
  }


  /**
   * 将水印信息的二维数组转换为一张图片
   */
  public static void matrixToBinaryPhoto(int[][] watermark, String dstPath) {

    int width = 64, height = 64;
    Mat binaryPhoto = new Mat(width, height, Imgproc.THRESH_BINARY);

    double a[] = new double[]{255, 255, 255};
    double b[] = new double[]{0, 0, 0};

    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        if (watermark[i][j] == 1) {
          binaryPhoto.put(i, j, a);
        } else {
          binaryPhoto.put(i, j, b);
        }
      }
    }
    imwrite(dstPath, binaryPhoto);
  }


  /**
   * 将一张图片压缩成一张64x64的二值图
   */
  public static String getBinaryPhoto(String srcPath, String dstPath) {

    srcPath = thumbnail(srcPath, dstPath, 64, 64);

    //得到原图
    File file = new File(srcPath);
    BufferedImage image = null;
    try {
      image = ImageIO.read(file);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    int width = image.getWidth();
    int height = image.getHeight();
    //创建原图的二值图
    BufferedImage binaryPhoto = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);
    int min = new Color(0, 0, 0).getRGB();
    int max = new Color(255, 255, 255).getRGB();
    //判断标记
    int flag = 170;
    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        //像素
        int pixel = image.getRGB(i, j);
        //得到 rgb通道对应的元素
        int r, g, b;
        r = (pixel & 0xff0000) >> 16;
        g = (pixel & 0xff00) >> 8;
        b = (pixel & 0xff);
        int avg = (r + g + b) / 3;
        if (avg <= flag) {
          binaryPhoto.setRGB(i, j, min);
        } else {
          binaryPhoto.setRGB(i, j, max);
        }
      }
    }
    try {
      ImageIO.write(binaryPhoto, "bmp", new File(dstPath));
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return dstPath;
  }


  /**
   * 将图片变成指定大小的图片
   */
  public static String thumbnail(String srcImagePath, String desImagePath, int w, int h) {
    Mat src = imread(srcImagePath);
    Mat dst = src.clone();
    Imgproc.resize(src, dst, new Size(w, h));
    imwrite(desImagePath, dst);
    return desImagePath;
  }
}