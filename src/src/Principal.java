package pgcii;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.highgui.*;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.imgcodecs.Imgcodecs;



public class Principal {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
                        System.load("C:\\opencv\\build\\java\\x64\\opencv_java411.dll");

		//esse arquivo cont�m as parametriza��es para fazer a detec��o facial
            CascadeClassifier cascadeClassifier = new CascadeClassifier();
            cascadeClassifier.load("C:\\opencv\\sources\\data\\haarcascades\\haarcascade_frontalface_alt.xml");
            
            if (!(cascadeClassifier.load("C:\\opencv\\sources\\data\\haarcascades\\haarcascade_frontalface_alt.xml"))) {
            System.err.println("--(!)Error loading face cascade: ");
            System.exit(0);
        }
                
                    
                               
                                    //CascadeClassifier cascade = new CascadeClassifierlt.xml"
                                if(cascadeClassifier.empty()){
                                    System.out.print("\n\nProblema com cascade");
                                }
                          

		Mat mat = Imgcodecs.imread(System.getProperty("user.dir") +"D:\\UFABC\\PGCII\\PGCII\\Imagens");
		
		//faz a detec��o das faces
                    ServiceDeteccaoFacesImagem serviceExtractFaces = new ServiceDeteccaoFacesImagem();
                    MatOfRect matOfRect = serviceExtractFaces.detectarFaces(cascadeClassifier, mat);
		
		//obtem os dados de onde est�o as faces (altura, largura, posi��o x e y)
		List<PropriedadesFace> propsFaces = serviceExtractFaces.obterDadosFaces(matOfRect);
		
		//desfoca a imagem
		ServiceDesfoqueImagem serviceBlur = new ServiceDesfoqueImagem();
		BufferedImage imagemCorteDesfoque = serviceBlur.DesfocarImagem(mat);
		
		//corta os rostos da imagem desfocada, 
		ServiceCorteImagem serviceCrop = new ServiceCorteImagem();
		propsFaces = serviceCrop.CortarImagem(propsFaces, imagemCorteDesfoque);
		
		ServiceSobreposicaoImagem serviceOverlay = new ServiceSobreposicaoImagem();
		
		//obtem toda a imagem se efeitos
		BufferedImage imagemSemEfeitos = Util.converterParaImage(mat);
		
		//"cola" os rostos desfocados sobre a imagem original
		//imagemCorteDesfoque = serviceOverlay.juntarImagens(propsFaces, imagemSemEfeitos);
		
		File outputfile = new File("chaves menor.jpg");
		
	    try {
			ImageIO.write(imagemCorteDesfoque, "jpg", outputfile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
