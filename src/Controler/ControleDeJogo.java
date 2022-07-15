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
import Modelo.Empurravel;
import Modelo.Grama;
import Modelo.Inimigo;
import Modelo.Lolo;
import Modelo.Obstaculo;
import Modelo.Perola;
import Modelo.Tiro;
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
        
        // Processando Inimigos
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
        
        // Processando colecionáveis
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
                }
            }
        }
        
        // Processando tiros
        List<Tiro> tirosList = faseAtual.stream()
                                       .filter(elem -> elem instanceof Tiro)
                                       .map(elem -> (Tiro)elem)
                                       .toList();
        ArrayList<Tiro> tiros = new ArrayList(tirosList);
        
        Tiro tiroTemp;
        Elemento colisor;
        Posicao posTiro = new Posicao(0, 0);
        for(int i = 0; i < tiros.size(); i++){
            tiroTemp = tiros.get(i);
            posTiro.setPosicao(tiroTemp.getPosicao().getLinha(), tiroTemp.getPosicao().getColuna());
            colisor = faseAtual.stream()
                                       .filter(elem -> elem.getPosicao().igual(posTiro))
                                       .findFirst()
                                       .orElse(null);
            if(colisor == null){
                continue;
            }
            if(colisor instanceof Inimigo){
                int linha = colisor.getPosicao().getLinha();
                int coluna = colisor.getPosicao().getColuna();
                faseAtual.remove(tiroTemp);
                faseAtual.add(new Perola(linha, coluna));
                faseAtual.remove(colisor);
            }
            if(colisor instanceof Obstaculo){
                faseAtual.remove(tiroTemp);
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
                
            case KeyEvent.VK_SPACE:
                if(municao <= 0){
                    break;
                }
                int linha = lolo.getPosicao().getLinha();
                int coluna = lolo.getPosicao().getColuna();
                int direcao = lolo.getDirecao();
                switch(direcao){
                    case Consts.DOWN_DIR:
                        linha += 1;
                        break;
                    case Consts.LEFT_DIR:
                        coluna -= 1;
                        break;
                    case Consts.UP_DIR:
                        linha -= 1;
                        break;
                    case Consts.RIGHT_DIR:
                        coluna += 1;
                        break;
                }
                faseAtual.add(new Tiro(linha, coluna, direcao));
                municao--;
        }
        
        if (!this.ehPosicaoValida(lolo.getPosicao())) {
            lolo.voltaAUltimaPosicao();
        }
        
        this.verificarEmpurrar();
    }
    
    private void verificarEmpurrar(){
        Lolo lolo = this.faseAtual.getLolo();
        // Processando Empurráveis
        List<Empurravel> empurraveisList = faseAtual.stream()
                                       .filter(elem -> elem instanceof Empurravel)
                                       .map(elem -> (Empurravel)elem)
                                       .toList();
        ArrayList<Empurravel> empurraveis = new ArrayList(empurraveisList);
        Empurravel empurravelTemp;
        for(int i = 0; i < empurraveis.size(); i++){
            empurravelTemp = empurraveis.get(i);
            if(lolo.getPosicao().igual(empurravelTemp.getPosicao())){
                switch(lolo.getDirecao()){
                    case Consts.DOWN_DIR:
                        empurravelTemp.moveDown();
                        break;
                    case Consts.LEFT_DIR:
                        empurravelTemp.moveLeft();
                        break;
                    case Consts.UP_DIR:
                        empurravelTemp.moveUp();
                        break;
                    case Consts.RIGHT_DIR:
                        empurravelTemp.moveRight();
                        break;
                }
                if(!ehPosicaoValida(empurravelTemp.getPosicao())){
                    empurravelTemp.voltaAUltimaPosicao();
                    lolo.voltaAUltimaPosicao();
                }
            }
        }
    }
    
    /*Retorna true se a posicao p é válida para Lolo com relacao a todos os personagens no array*/
    private boolean ehPosicaoValida(Posicao p){
        // Processando Obstáculos
        List<Obstaculo> obstaculosList = faseAtual.stream()
                                       .filter(elem -> elem instanceof Obstaculo)
                                       .map(elem -> (Obstaculo)elem)
                                       .toList();
        ArrayList<Obstaculo> obstaculos = new ArrayList(obstaculosList);
        Obstaculo obstaculoTemp;
        for(int i = 0; i < obstaculos.size(); i++){
            obstaculoTemp = obstaculos.get(i);
            if(p.igual(obstaculoTemp.getPosicao())){
                return false;
            }
        }
        return true;
    }
}
