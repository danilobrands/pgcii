/* 
 * I developed some code for recognize motion detections with JavaCV.
 * Actually, it works with an array of Rect, performing, every cicle, an
 * intersection test with area of difference with the rect of interests
 * (this array is callad "sa", stands for SizedArea). I hope could this
 * helps someone.
 * 
 * Feel free to ask about any question regarding the code above, cheers!
 *
 * Angelo Marchesin <marchesin.angelo@gmail.com>
 */

import com.googlecode.javacpp.Loader;
import com.googlecode.javacv.*;
import static com.googlecode.javacv.cpp.opencv_core.*;
import static com.googlecode.javacv.cpp.opencv_imgproc.*;
import static com.googlecode.javacv.cpp.opencv_calib3d.*;
import static com.googlecode.javacv.cpp.opencv_objdetect.*;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import pgcii.ScreenCapture;
import com.googlecode.javacv.cpp.opencv_core;

public class MotionDetector {
    
    public static BufferedImage toBufferedImage(Image img)
{
    if (img instanceof BufferedImage)
    {
        return (BufferedImage) img;
    }

    // Create a buffered image with transparency
    BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

    // Draw the image on to the buffered image
    Graphics2D bGr = bimage.createGraphics();
    bGr.drawImage(img, 0, 0, null);
    bGr.dispose();

    // Return the buffered image
    return bimage;
}
    
    
    
    public static void main(String[] args) throws Exception {
        OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(0);
        grabber.start();

        IplImage frame = grabber.grab();
        IplImage image = null;
        IplImage prevImage = null;
        IplImage diff = null;
              
        ScreenCapture sc = new ScreenCapture();
        BufferedImage buffered = (BufferedImage) sc.capturar(CV_QR, CV_BLUR);
        //byte[] bytes = FileUtils.readFileToByteArray(new File("aaa.jpg"));
        //Mat mat = Imgcodecs.imdecode(new MatOfByte(bytes), Imgcodecs.CV_LOAD_IMAGE_UNCHANGED);

        CanvasFrame canvasFrame = new CanvasFrame("Some Title");
        canvasFrame.setCanvasSize(frame.width(), frame.height());

        CvMemStorage storage = CvMemStorage.create();

        while (canvasFrame.isVisible() && (frame = grabber.grab()) != null) {
            cvClearMemStorage(storage);

            cvSmooth(frame, frame, CV_GAUSSIAN, 9, 9, 2, 2);
            if (image == null) {
                image = IplImage.create(frame.width(), frame.height(), IPL_DEPTH_8U, 1);
                cvCvtColor(frame, image, CV_RGB2GRAY);
            } else {
                //prevImage = IplImage.create(frame.width(), frame.height(), IPL_DEPTH_8U, 1);
                prevImage = image;
                image = IplImage.create(frame.width(), frame.height(), IPL_DEPTH_8U, 1);
                //image = 
                //image = sc.capturar(400, 300);
                cvCvtColor(frame, image, CV_RGB2GRAY);
            }

            if (diff == null) {
                diff = IplImage.create(frame.width(), frame.height(), IPL_DEPTH_8U, 1);
            }

            if (prevImage != null) {
                // perform ABS difference
                cvAbsDiff(image, prevImage, diff);
                // do some threshold for wipe away useless details
                cvThreshold(diff, diff, 64, 255, CV_THRESH_BINARY);

                canvasFrame.showImage(diff);

                // recognize contours
                CvSeq contour = new CvSeq(null);
                cvFindContours(diff, storage, contour, Loader.sizeof(CvContour.class), CV_RETR_LIST, CV_CHAIN_APPROX_SIMPLE);

                while (contour != null && !contour.isNull()) {
                    if (contour.elem_size() > 0) {
                        CvBox2D box = cvMinAreaRect2(contour, storage);
                        // test intersection
                        if (box != null) {
                            CvPoint2D32f center = box.center();
                            CvSize2D32f size = box.size();
/*                            for (int i = 0; i < sa.length; i++) {
                                if ((Math.abs(center.x - (sa[i].offsetX + sa[i].width / 2))) < ((size.width / 2) + (sa[i].width / 2)) &&
                                    (Math.abs(center.y - (sa[i].offsetY + sa[i].height / 2))) < ((size.height / 2) + (sa[i].height / 2))) {

                                    if (!alarmedZones.containsKey(i)) {
                                        alarmedZones.put(i, true);
                                        activeAlarms.put(i, 1);
                                    } else {
                                        activeAlarms.remove(i);
                                        activeAlarms.put(i, 1);
                                    }
                                    System.out.println("Motion Detected in the area no: " + i +
                                            " Located at points: (" + sa[i].x + ", " + sa[i].y+ ") -"
                                            + " (" + (sa[i].x +sa[i].width) + ", "
                                            + (sa[i].y+sa[i].height) + ")");
                                }
                            }
*/
                        }
                    }
                    contour = contour.h_next();
                }
            }
        }
        grabber.stop();
        canvasFrame.dispose();
    }
}
