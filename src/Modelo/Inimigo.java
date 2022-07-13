/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author joaop
 */
public abstract class Inimigo extends Elemento {
    public Inimigo(int linha, int coluna, String sNomeImagePNG){
        super(linha, coluna, sNomeImagePNG);
        this.setbMortal(true);
        this.setbTransponivel(true);
    }
}
