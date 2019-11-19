/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pgcii;

import com.googlecode.javacv.cpp.opencv_objdetect.CascadeClassifier;
import org.opencv.core.*;
import org.opencv.highgui.*;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.core.Core;



/**
 *
 * @author danil
 */
public class PGCII {

    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) throws Exception {
        
        //System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        //System.loadLibrary(Core);
        System.load("C:\\opencv\\build\\java\\x64\\opencv_java411.dll");
        
        //System.load("‪C:\opencv\\build\\java\\opencv-411.jar");
        
        CascadeClassifier cascadeClassifier = new CascadeClassifier(System.getProperty("user.dir") + "C:\\opencv\\sources\\data\\haarcascades\\haarcascade_frontalcatface.xml");
        
        //Mat retVal = new Mat(imread_1(filename));
        Mat mat = Imgcodecs.imread("D:\\UFABC\\PGCII\\PGCII\\Imagens");
        //Mat mat = new Mat();
        //mat = Imgcodecs.imread("rostos.jpg");
        //Mat mat = Imgcodecs.imread(System.getProperty("user.dir") +"/Imagens/rostos.jpg");
        
        //Mat mat = Highgui.(System.getProperty("user.dir") +"/chaves.jpg");

        new Thread(Vision.t1).start();
        
    }
    
	public MatOfRect detectarFaces(org.opencv.objdetect.CascadeClassifier cascadeClassifier, Mat mat){
		
		MatOfRect matOfRect = new MatOfRect();
		cascadeClassifier.detectMultiScale(mat, matOfRect);
		
		return matOfRect;
	}
    
   
    
}
