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
public class Perola extends Empurravel {
    boolean voando;
    int direcao;
    
    public Perola(int linha, int coluna) {
        super(linha, coluna, "perola.png");
        voando = false;
        direcao = -1;
    }
    
    public void voar(int direcao){
        this.voando = true;
        this.direcao = direcao;
        this.setbTransponivel(false);
    }
    
    public void autoDesenho(){
        this.atualizar();
        super.autoDesenho();
    }
    
    public void atualizar(){
        if(this.voando){
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
    
    public boolean isVoando(){
        return this.voando;
    }
}
