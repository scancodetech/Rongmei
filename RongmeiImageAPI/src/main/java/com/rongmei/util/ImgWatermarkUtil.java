package com.rongmei.util;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import java.util.ArrayList;
import java.util.List;

/**
 * 添加图片盲水印工具类
 */
public class ImgWatermarkUtil {

  static {
    //加载opencv动态库
    System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
  }

  private static List<Mat> planes = new ArrayList<>();
  private static List<Mat> allPlanes = new ArrayList<>();

  /**
   * <pre>
   *     添加图片文字水印
   * <pre>
   * @author Yangxiaohui
   * @date 2018-10-25 19:16
   * @param image             图片对象
   * @param watermarkText     水印文字
   */
  public static Mat addImageWatermarkWithText(Mat image, String watermarkText) {
    Mat complexImage = new Mat();
    //优化图像的尺寸
    //Mat padded = optimizeImageDim(image);
    Mat padded = splitSrc(image);
    padded.convertTo(padded, CvType.CV_32F);
    planes.add(padded);
    planes.add(Mat.zeros(padded.size(), CvType.CV_32F));
    Core.merge(planes, complexImage);
    // dft
    Core.dft(complexImage, complexImage);
    // 添加文本水印
    Scalar scalar = new Scalar(0, 0, 0);
    Point point = new Point(40, 40);
    Core.putText(complexImage, watermarkText, point, Core.FONT_HERSHEY_DUPLEX, 1D, scalar);
    Core.flip(complexImage, complexImage, -1);
    Core.putText(complexImage, watermarkText, point, Core.FONT_HERSHEY_DUPLEX, 1D, scalar);
    Core.flip(complexImage, complexImage, -1);
    return antitransformImage(complexImage, allPlanes);
  }

  /**
   * <pre>
   *     获取图片水印
   * <pre>
   * @author Yangxiaohui
   * @date 2018-10-25 19:58
   * @param image
   */
  public static Mat getImageWatermarkWithText(Mat image) {
    List<Mat> planes = new ArrayList<Mat>();
    Mat complexImage = new Mat();
    Mat padded = splitSrc(image);
    padded.convertTo(padded, CvType.CV_32F);
    planes.add(padded);
    planes.add(Mat.zeros(padded.size(), CvType.CV_32F));
    Core.merge(planes, complexImage);
    // dft
    Core.dft(complexImage, complexImage);
    Mat magnitude = createOptimizedMagnitude(complexImage);
    planes.clear();
    return magnitude;
  }

  private static Mat splitSrc(Mat mat) {
    mat = optimizeImageDim(mat);
    Core.split(mat, allPlanes);
    Mat padded = new Mat();
    if (allPlanes.size() > 1) {
      for (int i = 0; i < allPlanes.size(); i++) {
        if (i == 0) {
          padded = allPlanes.get(i);
          break;
        }
      }
    } else {
      padded = mat;
    }
    return padded;
  }

  private static Mat antitransformImage(Mat complexImage, List<Mat> allPlanes) {
    Mat invDFT = new Mat();
    Core.idft(complexImage, invDFT, Core.DFT_SCALE | Core.DFT_REAL_OUTPUT, 0);
    Mat restoredImage = new Mat();
    invDFT.convertTo(restoredImage, CvType.CV_8U);
    if (allPlanes.size() == 0) {
      allPlanes.add(restoredImage);
    } else {
      allPlanes.set(0, restoredImage);
    }
    Mat lastImage = new Mat();
    Core.merge(allPlanes, lastImage);
    return lastImage;
  }

  /**
   * <pre>
   *     为加快傅里叶变换的速度，对要处理的图片尺寸进行优化
   * <pre>
   * @author Yangxiaohui
   * @date 2018-10-25 19:33
   * @param image
   * @return
   */
  private static Mat optimizeImageDim(Mat image) {
    Mat padded = new Mat();
    int addPixelRows = Core.getOptimalDFTSize(image.rows());
    int addPixelCols = Core.getOptimalDFTSize(image.cols());
    Imgproc.copyMakeBorder(image, padded, 0, addPixelRows - image.rows(), 0,
        addPixelCols - image.cols(),
        Imgproc.BORDER_CONSTANT, Scalar.all(0));

    return padded;
  }

  private static Mat createOptimizedMagnitude(Mat complexImage) {
    List<Mat> newPlanes = new ArrayList<Mat>();
    Mat mag = new Mat();
    Core.split(complexImage, newPlanes);
    Core.magnitude(newPlanes.get(0), newPlanes.get(1), mag);
    Core.add(Mat.ones(mag.size(), CvType.CV_32F), mag, mag);
    Core.log(mag, mag);
    shiftDFT(mag);
    mag.convertTo(mag, CvType.CV_8UC1);
    Core.normalize(mag, mag, 0, 255, Core.NORM_MINMAX, CvType.CV_8UC1);
    return mag;
  }

  private static void shiftDFT(Mat image) {
    image = image.submat(new Rect(0, 0, image.cols() & -2, image.rows() & -2));
    int cx = image.cols() / 2;
    int cy = image.rows() / 2;

    Mat q0 = new Mat(image, new Rect(0, 0, cx, cy));
    Mat q1 = new Mat(image, new Rect(cx, 0, cx, cy));
    Mat q2 = new Mat(image, new Rect(0, cy, cx, cy));
    Mat q3 = new Mat(image, new Rect(cx, cy, cx, cy));
    Mat tmp = new Mat();
    q0.copyTo(tmp);
    q3.copyTo(q0);
    tmp.copyTo(q3);
    q1.copyTo(tmp);
    q2.copyTo(q1);
    tmp.copyTo(q2);
  }
}
