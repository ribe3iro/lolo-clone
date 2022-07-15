/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import Auxiliar.Consts;

/**
 *
 * @author joaop
 */
public class Tiro extends Elemento implements Movel {
    int direcao;
    public Tiro(int linha, int coluna, int direcao){
        super(linha, coluna, "tiro.png");
        this.direcao = direcao;
    }
    
    public void autoDesenho(){
        super.autoDesenho();
        this.atualizar();
    }
    
    public void atualizar() {
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
    
    public void voltaAUltimaPosicao(){
        
    }
}
