/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author joaop
 */
public class Coracao extends Colecionavel {
    int municao;
    public Coracao(int linha, int coluna) {
        super(linha, coluna, "coracao.png");
        this.municao = 0;
    }
    
    public Coracao(int linha, int coluna, int municao) {
        super(linha, coluna, "coracao.png");
        this.municao = municao;
    }

    public int getMunicao() {
        return municao;
    }
}
