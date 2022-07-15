/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Auxiliar.Consts;
import Auxiliar.Desenho;
import Controler.Tela;
import Auxiliar.Posicao;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Junio
 */
public abstract class Elemento implements Serializable {

    protected ImageIcon iImage;
    protected Posicao pPosicao;
    protected boolean bTransponivel; /*Pode passar por cima?*/
    protected boolean bMortal;       /*Se encostar, o Lolo morre?*/


    protected Elemento(int linha, int coluna, String sNomeImagePNG) {
        this.pPosicao = new Posicao(linha, coluna);
        this.bTransponivel = false;
        this.bMortal = false;
        if(sNomeImagePNG != null){
            try {
                iImage = new ImageIcon(new java.io.File(".").getCanonicalPath() + Consts.PATH + sNomeImagePNG);
                Image img = iImage.getImage();
                BufferedImage bi = new BufferedImage(Consts.CELL_SIDE, Consts.CELL_SIDE, BufferedImage.TYPE_INT_ARGB);
                Graphics g = bi.createGraphics();
                g.drawImage(img, 0, 0, Consts.CELL_SIDE, Consts.CELL_SIDE, null);
                iImage = new ImageIcon(bi);
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
    
    public boolean salvar(String nomeArq) {
        try {
            File file = new File(
                    new java.io.File(".").getCanonicalPath() + File.separator + "elementos" + File.separator + nomeArq);
            file.createNewFile();
            FileOutputStream fileOS = new FileOutputStream(file);
            GZIPOutputStream compactador = new GZIPOutputStream(fileOS);
            ObjectOutputStream serializador = new ObjectOutputStream(compactador);
            serializador.writeObject(this);
            serializador.flush();
            serializador.close();
        } catch (IOException ex) {
            System.out.println("Erro ao salvar elemento " + nomeArq);
            return false;
        }

        return true;
    }

    public static Elemento carregar(String nomeArq) {
        Elemento elemento = null;
        try {
            File file = new File(
                    new java.io.File(".").getCanonicalPath() + File.separator + "elementos" + File.separator + nomeArq);
            FileInputStream fileIS = new FileInputStream(file);
            GZIPInputStream compactador = new GZIPInputStream(fileIS);
            ObjectInputStream serializador = new ObjectInputStream(compactador);
            elemento = (Elemento) serializador.readObject();
            serializador.close();
        } catch (IOException ex) {
            System.out.println("Erro IO ao carregar elemento " + nomeArq);
        } catch (ClassNotFoundException ex) {
            System.out.println("Erro ClassNotFound ao carregar elemento " + nomeArq);
        }

        return elemento;
    }
    
    public void autoDesenho(){
        Desenho.desenhar(this.iImage, pPosicao.getColuna(), pPosicao.getLinha());
    }

    public boolean isbTransponivel() {
        return bTransponivel;
    }

    public void setbTransponivel(boolean bTransponivel) {
        this.bTransponivel = bTransponivel;
    }

    public boolean isbMortal() {
        return bMortal;
    }

    public void setbMortal(boolean bMortal) {
        this.bMortal = bMortal;
    }
    
    public Posicao getPosicao(){
        return this.pPosicao;
    }

    public boolean setPosicao(int x, int y) {
        return pPosicao.setPosicao(x, y);
    }
}
