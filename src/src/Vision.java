/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pgcii;

import com.googlecode.javacv.CanvasFrame;
import com.googlecode.javacv.cpp.opencv_core;
import static com.googlecode.javacv.cpp.opencv_highgui.cvLoadImage;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;


/**
 *
 * @author danil
 */
public class Vision {
    static CanvasFrame canvas = new CanvasFrame("Imagem");
    
        public static void exibirImagem(){
        opencv_core.IplImage image = cvLoadImage("Imagens\\03.png");
        
        canvas.showImage(image);
        canvas.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
    }
    
    public static Runnable t1 = new Runnable() {
        public void run() {
            try{
                    while(true){
                                ScreenCapture sc = new ScreenCapture();
                                Image ScreeBuffer;
                                ScreeBuffer = sc.capturar(400, 300);
                                 File outputFile = new File("Imagens\\03.png");
                                //Salva a imagem (api imageio)
                                ImageIO.write((BufferedImage) ScreeBuffer, "PNG", outputFile);
                                exibirImagem();
                }
            } catch (Exception e){}

        }
    };
    
}
