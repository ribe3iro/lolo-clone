/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Modelo;

import Auxiliar.Consts;
import Auxiliar.Desenho;
import Controler.ControleDeJogo;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Junio
 */
public class Lolo extends Elemento implements Movel{
    private ArrayList<ImageIcon> sprites;
    private int direcao;
    
    public Lolo(int linha, int coluna) {
        super(linha, coluna, null);
        sprites = new ArrayList<>();
        direcao = 0;
        String nomeImagem = "lolo";
        for(int i = 0; i <= 3; i++){
            sprites.add(ControleDeJogo.carregarImagem(nomeImagem + i + ".png")); 
       }
    }
    
    public void autoDesenho(){
        Desenho.desenhar(this.sprites.get(direcao), pPosicao.getColuna(), pPosicao.getLinha());
    }

    public void voltaAUltimaPosicao(){
        this.pPosicao.volta();
    }

    public void atualizar(){
        
    }
    
    public boolean moveUp() {
        direcao = Consts.UP_DIR;
        return this.pPosicao.moveUp();
    }

    public boolean moveDown() {
        direcao = Consts.DOWN_DIR;
        return this.pPosicao.moveDown();
    }

    public boolean moveRight() {
        direcao = Consts.RIGHT_DIR;
        return this.pPosicao.moveRight();
    }

    public boolean moveLeft() {
        direcao = Consts.LEFT_DIR;
        return this.pPosicao.moveLeft();
    }
    
    public int getDirecao() {
        return direcao;
    }

    public void setDirecao(int direcao) {
        if(direcao >= 0 && direcao < this.sprites.size()){
            this.direcao = direcao;
        }
    }
}
