/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author joaop
 */
public class Obstaculo extends Elemento{
    boolean empurravel;
    
    public Obstaculo(int linha, int coluna, String sNomeImagePNG) {
        super(linha, coluna, sNomeImagePNG);
        this.empurravel = false;
    }
    
    public Obstaculo(int linha, int coluna, String sNomeImagePNG, boolean empurravel) {
        super(linha, coluna, sNomeImagePNG);
        this.empurravel = empurravel;
    }
}
