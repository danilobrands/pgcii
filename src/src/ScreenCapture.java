package pgcii;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.Robot;
import java.awt.Rectangle;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;
import java.awt.image.RenderedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.util.StringTokenizer;

public class ScreenCapture extends JFrame {
    private JLabel tela = new JLabel();
    private JToolBar barra = new JToolBar();
    private Image img;


    /**
     *  Constructor for the ScreenCapture object
     */
    public ScreenCapture() {
        super("Captura de tela");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tela.setBorder(BorderFactory.createLoweredBevelBorder());
        this.getContentPane().add(tela);
        this.barra.add(new CapturaAction());
        this.barra.add(new SalvarAction());
        this.getContentPane().add(barra, BorderLayout.NORTH);
    }


    /**
     *  Captura a tela corrente
     *
     */
    public Image capturar(int altura, int largura) throws Exception {
        Robot robo = new Robot();
        //Pega a image
        this.img = robo.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
        //Gera um thumbnail
        //Image scaledImg = img.getScaledInstance(altura, largura, Image.SCALE_FAST);
        //Exibe o thumbnail
        //tela.setIcon(new ImageIcon(scaledImg));
        tela.validate();
        
        File outputFile = new File("Imagens\\02.png");
        //Salva a imagem (api imageio)
        ImageIO.write((BufferedImage) this.img, "PNG", outputFile);
        
        return this.img;
    }

    /**
     *  Salva a tela atual em PNG
     *
     */
    public void salvar() throws Exception {
        if (this.img == null) {
            return;
        }
        JFileChooser jfc = new JFileChooser();
        //Define um filtro para os arquivos exibidos no Chooser
        jfc.setFileFilter(
            new FileFilter() {
                public boolean accept(File f) {
                    StringTokenizer stk = new StringTokenizer(f.getName(), ".");
                    String ext = null;
                    while (stk.hasMoreTokens()) {
                        ext = stk.nextToken();
                    }
                    if (ext == null) {
                        return false;
                    }
                    if (ext.equalsIgnoreCase("png")) {
                        return true;
                    } else {
                        return false;
                    }
                }

                public String getDescription() {
                    return "Imagens PNG";
                }
            });
        int resp = jfc.showSaveDialog(this);
        if (resp == JFileChooser.CANCEL_OPTION) {
            return;
        }
        System.out.println(jfc.getSelectedFile());
        File outputFile = new File("Imagens\\01.png");
        //Salva a imagem (api imageio)
        ImageIO.write((BufferedImage) this.img, "PNG", outputFile);
    }


    /**
     *  Action para capturar a tela
     *
     *@author     cmsilva (copernico@javafree.com.br)
     *@version    19 de Outubro de 2004
     */
    private class CapturaAction extends AbstractAction {
        /**
         *  Constructor for the Captura object
         */
        public CapturaAction() {
            super("Capturar");
        }

        public void actionPerformed(ActionEvent ev) {
            try {
                //capturar();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *  Action para salvar a tela
     *
     *@author     cmsilva (copernico@javafree.com.br)
     *@version    19 de Outubro de 2004
     */
    private class SalvarAction extends AbstractAction {
        /**
         *  Constructor for the SalvarAction object
         */
        public SalvarAction() {
            super("Salvar");
        }
        public void actionPerformed(ActionEvent ev) {
            try {
                salvar();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     *  MAIN
     *
     *@param  args  Description of the Parameter
     */
}
