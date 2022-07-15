/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import Auxiliar.Consts;
import Auxiliar.Desenho;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.ImageIcon;

/**
 *
 * @author caior
 */
public class Porta extends Elemento {
    boolean bAberto = false;
    ImageIcon portaAberta = carregarImagem("porta_aberta.png");
    ImageIcon portaFechada = carregarImagem("porta_fechada.png");

    public Porta(int linha, int coluna) {
        super(linha, coluna, null);
        this.setbTransponivel(false);
    }

    public void autoDesenho() {
        if (this.bAberto) {
            this.setbTransponivel(true);
            Desenho.desenhar(portaAberta, pPosicao.getColuna(), pPosicao.getLinha());
        } else {
            Desenho.desenhar(portaFechada, pPosicao.getColuna(), pPosicao.getLinha());
        }

    }

    private ImageIcon carregarImagem(String nomeImagem) {
        ImageIcon iImage = null;
        try {
            iImage = new ImageIcon(new java.io.File(".").getCanonicalPath() + Consts.PATH + nomeImagem);
            Image img = iImage.getImage();
            BufferedImage bi = new BufferedImage(Consts.CELL_SIDE, Consts.CELL_SIDE, BufferedImage.TYPE_INT_ARGB);
            Graphics g = bi.createGraphics();
            g.drawImage(img, 0, 0, Consts.CELL_SIDE, Consts.CELL_SIDE, null);
            iImage = new ImageIcon(bi);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return iImage;
    }

    public boolean isbAberto() {
        return bAberto;
    }

    public void setbAberto(boolean bAberto) {
        this.bAberto = bAberto;
    }

}
