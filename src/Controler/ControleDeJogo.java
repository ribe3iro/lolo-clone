/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controler;

import Auxiliar.Consts;
import Modelo.Cobra;
import Modelo.Elemento;
import Modelo.Grama;
import Modelo.Inimigo;
import Modelo.Personagem;
import Modelo.Lolo;
import Modelo.Obstaculo;
import auxiliar.Posicao;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author junio
 */
public class ControleDeJogo {
    private int vidas;
    private int municao;
    private Fase faseAtual;
    
    public ControleDeJogo(int vidas, int municao){
        this.vidas = vidas;
        this.municao = municao;
        faseAtual = new Fase();
        /*Cria e adiciona personagens*/
        // Lolo
        faseAtual.add(new Lolo(8, 6));
        
        // Bordas
        for(int i = 0; i < Consts.RES; i++){
            faseAtual.add(new Obstaculo(i, 0, "parede_vertical.png"));
            faseAtual.add(new Obstaculo(i, Consts.RES-1, "parede_vertical.png"));
        }
        for(int j = 1; j < Consts.RES-1; j++){
            faseAtual.add(new Obstaculo(0, j, "parede_horizontal.png"));
            faseAtual.add(new Obstaculo(Consts.RES-1, j, "parede_horizontal.png"));
        }
        
        // Obstaculos
        faseAtual.add(new Obstaculo(1, 1, "pedra.png"));
        faseAtual.add(new Obstaculo(1, 9, "arvore.png"));
        faseAtual.add(new Obstaculo(1, 10, "arvore.png"));
        faseAtual.add(new Obstaculo(2, 3, "arvore.png"));
        faseAtual.add(new Obstaculo(2, 4, "arvore.png"));
        faseAtual.add(new Obstaculo(2, 8, "pedra.png"));
        faseAtual.add(new Obstaculo(2, 9, "arvore.png"));
        faseAtual.add(new Obstaculo(2, 10, "arvore.png"));
        faseAtual.add(new Obstaculo(3, 1, "pedra.png"));
        faseAtual.add(new Obstaculo(3, 3, "arvore.png"));
        faseAtual.add(new Obstaculo(3, 4, "arvore.png"));
        faseAtual.add(new Obstaculo(3, 8, "pedra.png"));
        faseAtual.add(new Obstaculo(3, 9, "pedra.png"));
        faseAtual.add(new Obstaculo(3, 10, "arvore.png"));
        faseAtual.add(new Obstaculo(4, 1, "arvore.png"));
        faseAtual.add(new Obstaculo(4, 9, "pedra.png"));
        faseAtual.add(new Obstaculo(7, 1, "arvore.png"));
        faseAtual.add(new Obstaculo(8, 1, "arvore.png"));
        faseAtual.add(new Obstaculo(8, 2, "arvore.png"));
        faseAtual.add(new Obstaculo(9, 1, "arvore.png"));
        faseAtual.add(new Obstaculo(9, 2, "arvore.png"));
        faseAtual.add(new Obstaculo(9, 7, "arvore.png"));
        faseAtual.add(new Obstaculo(9, 11, "pedra.png"));
        faseAtual.add(new Obstaculo(10, 1, "pedra.png"));
        faseAtual.add(new Obstaculo(10, 5, "pedra.png"));
        faseAtual.add(new Obstaculo(10, 6, "pedra.png"));
        faseAtual.add(new Obstaculo(10, 7, "arvore.png"));
        faseAtual.add(new Obstaculo(10, 10, "pedra.png"));
        faseAtual.add(new Obstaculo(10, 11, "arvore.png"));
        faseAtual.add(new Obstaculo(11, 1, "pedra.png"));
        faseAtual.add(new Obstaculo(11, 4, "pedra.png"));
        faseAtual.add(new Obstaculo(11, 5, "pedra.png"));
        faseAtual.add(new Obstaculo(11, 6, "arvore.png"));
        faseAtual.add(new Obstaculo(11, 7, "arvore.png"));
        faseAtual.add(new Obstaculo(11, 10, "arvore.png"));
        faseAtual.add(new Obstaculo(11, 11, "arvore.png"));
        
        // Grama
        faseAtual.add(new Grama(5, 1));
        faseAtual.add(new Grama(6, 1));
        faseAtual.add(new Grama(6, 2));
        faseAtual.add(new Grama(6, 11));
        faseAtual.add(new Grama(7, 2));
        faseAtual.add(new Grama(7, 11));
        faseAtual.add(new Grama(8, 10));
        faseAtual.add(new Grama(8, 11));
        faseAtual.add(new Grama(9, 10));
        
        // Inimigos
        faseAtual.add(new Cobra(2, 2));
        faseAtual.add(new Cobra(6, 4));
        faseAtual.add(new Cobra(6, 8));
    }
    
    public void desenhaTudo(){
        for(int i = faseAtual.size()-1; i >= 0; i--){
            faseAtual.get(i).autoDesenho();
        }
    }
    public void processaTudo(){
        Lolo lolo = (Lolo)faseAtual.get(0);
        
        List<Inimigo> inimigosList = faseAtual.stream()
                                       .filter(elem -> elem instanceof Inimigo)
                                       .map(elem -> (Inimigo)elem)
                                       .toList();
        ArrayList<Inimigo> inimigos = new ArrayList(inimigosList);
        
        Inimigo inimigoTemp;
        for(int i = 1; i < inimigos.size(); i++){
            inimigoTemp = inimigos.get(i);
            if(lolo.getPosicao().igual(inimigoTemp.getPosicao()) && inimigoTemp.isbMortal()){
                this.vidas--;
                if(this.vidas > 0){
                    lolo.voltaAUltimaPosicao();
                }else{
                    // gameover
                    faseAtual.remove(lolo);
                }
            }
        }
    }
    
    public void teclaPressionada(int keyCode){
        Lolo lolo = faseAtual.getLolo();
        if (keyCode == KeyEvent.VK_UP) {
            lolo.moveUp();
            lolo.setDirecao(Lolo.UP_DIR);
        } else if (keyCode == KeyEvent.VK_DOWN) {
            lolo.moveDown();
            lolo.setDirecao(Lolo.DOWN_DIR);
        } else if (keyCode == KeyEvent.VK_LEFT) {
            lolo.moveLeft();
            lolo.setDirecao(Lolo.LEFT_DIR);
        } else if (keyCode == KeyEvent.VK_RIGHT) {
            lolo.moveRight();
            lolo.setDirecao(Lolo.RIGHT_DIR);
        }
        if (!this.ehPosicaoValida(lolo.getPosicao())) {
            lolo.voltaAUltimaPosicao();
        }
    }
    
    /*Retorna true se a posicao p é válida para Lolo com relacao a todos os personagens no array*/
    public boolean ehPosicaoValida(Posicao p){
        Elemento pTemp;
        for(int i = 1; i < faseAtual.size(); i++){
            pTemp = faseAtual.get(i);            
            if(!pTemp.isbTransponivel())
                if(pTemp.getPosicao().igual(p))
                    return false;
        }        
        return true;
    }
}
