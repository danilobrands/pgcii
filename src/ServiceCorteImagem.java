package pgcii;

import java.awt.image.BufferedImage;
import java.util.List;


public class ServiceCorteImagem {

	public List<PropriedadesFace> CortarImagem(List<PropriedadesFace> dados, BufferedImage imagem){
		
		for(PropriedadesFace dado : dados){
			dado.setImageCortada(imagem.getSubimage(dado.getX(), dado.getY(), dado.getWidth(), dado.getHeight()));
		}
		
		return dados;
	}
}
