/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author joaop
 */
public abstract class Movel extends Elemento {
    public Movel(int linha, int coluna, String sNomeImagePNG) {
        super(linha, coluna, sNomeImagePNG);
    }
    
    abstract protected void atualizar();
    
    public void autoDesenho(){
        this.atualizar();
        super.autoDesenho();
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
}
