/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controler;

import Auxiliar.Consts;
import Modelo.Cobra;
import Modelo.Colecionavel;
import Modelo.Coracao;
import Modelo.Elemento;
import Modelo.Grama;
import Modelo.Inimigo;
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
    private String nomeFaseAtual;
    
    public ControleDeJogo(int vidas, int municao, String nomeFaseInicial){
        this.vidas = vidas;
        this.municao = municao;
        carregarFase(nomeFaseInicial);
    }
    
    private void carregarFase(String nomeFase){
        this.nomeFaseAtual = nomeFase;
        this.faseAtual = Fase.carregar(nomeFase);
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
        for(int i = 0; i < inimigos.size(); i++){
            inimigoTemp = inimigos.get(i);
            if(lolo.getPosicao().igual(inimigoTemp.getPosicao()) && inimigoTemp.isbMortal()){
                this.vidas--;
                if(this.vidas > 0){
                    carregarFase(this.nomeFaseAtual);
                    this.municao = 0;
                }else{
                    // gameover
                    faseAtual.remove(lolo);
                }
            }
        }
        
        List<Colecionavel> colecionaveisList = faseAtual.stream()
                                       .filter(elem -> elem instanceof Colecionavel)
                                       .map(elem -> (Colecionavel)elem)
                                       .toList();
        ArrayList<Colecionavel> colecionaveis = new ArrayList(colecionaveisList);
        
        Colecionavel colecionavelTemp;
        for(int i = 0; i < colecionaveis.size(); i++){
            colecionavelTemp = colecionaveis.get(i);
            if(lolo.getPosicao().igual(colecionavelTemp.getPosicao())){
                faseAtual.remove(colecionavelTemp);
                if(colecionavelTemp instanceof Coracao){
                    this.municao += ((Coracao)colecionavelTemp).getMunicao();
                    System.out.println(this.municao);
                }
            }
        }
    }
 
    public void teclaPressionada(int keyCode){
        Lolo lolo = faseAtual.getLolo();
        switch(keyCode){
            case KeyEvent.VK_UP:
                lolo.moveUp();
                break;
            case KeyEvent.VK_DOWN:
                lolo.moveDown();
                break;
            case KeyEvent.VK_LEFT:
                lolo.moveLeft();
                break;
            case KeyEvent.VK_RIGHT:
                lolo.moveRight();
                break;
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
