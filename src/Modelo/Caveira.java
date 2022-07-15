/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import Auxiliar.Consts;
import Auxiliar.Desenho;
import Controler.ControleDeJogo;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.ImageIcon;

/**
 *
 * @author joaop
 */
public class Caveira extends Inimigo implements Movel{
    int direcao;
    boolean acordada;
    int contaFrames;
    private ArrayList<ImageIcon> sprites;
    public Caveira(int linha, int coluna) {
        super(linha, coluna, "caveira_dormindo.png");
        this.acordada = false;
        contaFrames = 0;
        
        String nomeImagem = "caveira_acordada";
        sprites = new ArrayList<>();
        for(int i = 0; i <= 3; i++){
            sprites.add(ControleDeJogo.carregarImagem(nomeImagem + i + ".png")); 
       }
    }
    
    public void acordar(){
        Random rand = new Random();
        if(!acordada){
            this.acordada = true;
            this.direcao = rand.nextInt(4);
        }
    }

    public void atualizar() {
        if(acordada && contaFrames >= Consts.TIMER_CAVEIRA){
            contaFrames = 0;
            switch(direcao){
                case Consts.DOWN_DIR:
                        this.moveDown();
                        break;
                    case Consts.LEFT_DIR:
                        this.moveLeft();
                        break;
                    case Consts.UP_DIR:
                        this.moveUp();
                        break;
                    case Consts.RIGHT_DIR:
                        this.moveRight();
                        break;
            }
        }
    }
    
    public void virar(){
        Random rand = new Random();
        int novaDirecao;
        do{
            novaDirecao = rand.nextInt(4);
        }while(novaDirecao == this.direcao);
        this.direcao = novaDirecao;
    }
    
    public void autoDesenho(){
        if(this.acordada){
            Desenho.desenhar(this.sprites.get(direcao), pPosicao.getColuna(), pPosicao.getLinha());
            this.atualizar();
            contaFrames++;
        }else{
            super.autoDesenho();
        }
    }

    public boolean moveUp() {
        return this.pPosicao.moveUp();
    }

    public boolean moveDown() {
        return this.pPosicao.moveDown();
    }

    public boolean moveRight() {
        return this.pPosicao.moveRight();
    }

    public boolean moveLeft() {
        return this.pPosicao.moveLeft();
    }

    public void voltaAUltimaPosicao() {
        this.pPosicao.volta();
    }
    
    public boolean isAcordada(){
        return this.acordada;
    }
}
