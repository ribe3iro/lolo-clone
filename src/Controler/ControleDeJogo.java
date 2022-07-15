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
import Modelo.Bau;
import Modelo.Inimigo;
import Modelo.Lolo;
import Modelo.Obstaculo;
import Modelo.Porta;
import Modelo.Perola;
import Modelo.Tiro;
import Auxiliar.Posicao;
import Modelo.Caveira;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;

/**
 *
 * @author junio
 */
public class ControleDeJogo {

    private int vidas;
    private int municao;
    private Fase faseAtual;
    private int numFaseAtual;
    private int maxFases;
    
    public ControleDeJogo(int vidas, int municao) {
        this.vidas = vidas;
        this.municao = municao;
        this.numFaseAtual = 1;
        this.maxFases = 3;
        carregarFase(numFaseAtual);
    }

    private void carregarFase(int numFase) {
        String nomeFase = "fase" + numFase + ".level";
        this.faseAtual = Fase.carregar(nomeFase);
    }

    public void desenhaTudo() {
        for (int i = faseAtual.size() - 1; i >= 0; i--) {
            faseAtual.get(i).autoDesenho();
            // System.out.print(faseAtual.get(i).toString());
            // System.out.print(i);
            // System.out.print("\n");
        }
    }

    public void processaTudo() {
        Lolo lolo = (Lolo) faseAtual.get(0);

        // Processando Inimigos
        List<Inimigo> inimigosList = faseAtual.stream()
                .filter(elem -> elem instanceof Inimigo)
                .map(elem -> (Inimigo) elem)
                .toList();
        ArrayList<Inimigo> inimigos = new ArrayList(inimigosList);

        Inimigo inimigoTemp;
        for (int i = 0; i < inimigos.size(); i++) {
            inimigoTemp = inimigos.get(i);
            if (lolo.getPosicao().igual(inimigoTemp.getPosicao()) && inimigoTemp.isbMortal()) {
                this.vidas--;
                if (this.vidas > 0) {
                    carregarFase(this.numFaseAtual);
                    this.municao = 0;
                } else {
                    // gameover
                    faseAtual.remove(lolo);
                }
            }
        }
        
        // Processando Caveiras Acordadas
        inimigos.stream()
                .filter(inimigo -> inimigo instanceof Caveira && ((Caveira)inimigo).isAcordada() && !this.ehPosicaoValida(inimigo))
                .map(inimigo -> (Caveira)inimigo)
                .forEach(caveira -> {
                    caveira.virar();
                    caveira.voltaAUltimaPosicao();
                });

        // Bau bau = (Bau) ((ArrayList<Elemento>) faseAtual.stream().filter(elem -> elem
        // instanceof Bau)).get(0);
        Bau bau = faseAtual.stream().filter(elem -> elem instanceof Bau).map(elem -> (Bau) elem).findFirst()
                .orElse(null);

        // Porta porta = (Porta) ((ArrayList<Elemento>) faseAtual.stream().filter(elem
        // -> elem instanceof Porta)).get(0);
        Porta porta = faseAtual.stream().filter(elem -> elem instanceof Porta).map(elem -> (Porta) elem).findFirst()
                .orElse(null);
        
        if(lolo.getPosicao().igual(porta.getPosicao()) && porta.isbAberto()){
            this.proximaFase();
        }

        // Processando colecionáveis

        List<Colecionavel> colecionaveisList = faseAtual.stream()
                .filter(elem -> elem instanceof Colecionavel)
                .map(elem -> (Colecionavel) elem)
                .toList();
        ArrayList<Colecionavel> colecionaveis = new ArrayList(colecionaveisList);
        int colecionaveisSize = colecionaveis.size();
        Colecionavel colecionavelTemp;
        for (int i = 0; i < colecionaveisSize; i++) {
            colecionavelTemp = colecionaveis.get(i);
            if (lolo.getPosicao().igual(colecionavelTemp.getPosicao())) {
                faseAtual.remove(colecionavelTemp);
                if(colecionavelTemp instanceof Coracao){
                    this.municao += ((Coracao)colecionavelTemp).getMunicao();
                }
            }

        }

        // bau.setbTransponivel(false);
        if (colecionaveisSize == 0) {
            bau.setbAberto(true);
            inimigos.stream()
                    .filter(inimigo -> inimigo instanceof Caveira && !((Caveira)inimigo).isAcordada())
                    .map(inimigo -> (Caveira)inimigo)
                    .forEach(Caveira::acordar);
        }

        // porta.setbTransponivel(false);
        if (lolo.getPosicao().igual(bau.getPosicao())) {
            porta.setbAberto(true);
            inimigos.stream()
                    .forEach(inimigo -> {
                        faseAtual.remove(inimigo);
                    });
        }

        // Processando tiros
        List<Tiro> tirosList = faseAtual.stream()
                .filter(elem -> elem instanceof Tiro)
                .map(elem -> (Tiro) elem)
                .toList();
        ArrayList<Tiro> tiros = new ArrayList(tirosList);

        Tiro tiroTemp;
        Elemento colisor;
        Posicao posTiro = new Posicao(0, 0);
        for (int i = 0; i < tiros.size(); i++) {
            tiroTemp = tiros.get(i);
            posTiro.setPosicao(tiroTemp.getPosicao().getLinha(), tiroTemp.getPosicao().getColuna());
            colisor = faseAtual.stream()
                    .filter(elem -> elem.getPosicao().igual(posTiro))
                    .findFirst()
                    .orElse(null);
            if (colisor == null) {
                continue;
            }
            if(colisor instanceof Inimigo){
                int linha = colisor.getPosicao().getLinha();
                int coluna = colisor.getPosicao().getColuna();
                faseAtual.remove(tiroTemp);
                faseAtual.add(new Perola(linha, coluna));
                faseAtual.remove(colisor);
            }
            if (colisor instanceof Obstaculo) {
                faseAtual.remove(tiroTemp);
            }
            if (colisor instanceof Perola) {
                ((Perola)colisor).voar(tiroTemp.getDirecao());
                faseAtual.remove(tiroTemp);
            }
        }
        
        // Processando perolas voando
        List<Perola> perolasVoandoList = faseAtual.stream()
                .filter(elem -> elem instanceof Perola)
                .map(elem -> (Perola) elem)
                .filter(perola -> perola.isVoando())
                .toList();
        ArrayList<Perola> perolasVoando = new ArrayList(perolasVoandoList);

        Perola perolaTemp;
        for (int i = 0; i < perolasVoando.size(); i++) {
            perolaTemp = perolasVoando.get(i);
            int linha = perolaTemp.getPosicao().getLinha();
            int coluna = perolaTemp.getPosicao().getColuna();
            if(linha >= Consts.RES-1 || linha <= 0 || coluna >= Consts.RES-1 || coluna <= 0){
                faseAtual.remove(perolaTemp);
            }
        }
    }
    
    public void proximaFase(){
        this.numFaseAtual++;
        if(numFaseAtual <= maxFases){
            this.carregarFase(numFaseAtual);
            this.municao = 0;
        }
    }

    public void teclaPressionada(int keyCode) {
        Lolo lolo = faseAtual.getLolo();
        switch (keyCode) {
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
                if (municao <= 0) {
                    break;
                }
                int linha = lolo.getPosicao().getLinha();
                int coluna = lolo.getPosicao().getColuna();
                int direcao = lolo.getDirecao();
                switch (direcao) {
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
        
        this.verificarEmpurrar();

        if (!this.ehPosicaoValida(lolo)) {
            lolo.voltaAUltimaPosicao();
        }
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
                if(!ehPosicaoValida(empurravelTemp)){
                    empurravelTemp.voltaAUltimaPosicao();
                    lolo.voltaAUltimaPosicao();
                }
            }
        }
    }
    
    /*
     * Retorna true se a posicao p é válida para Lolo com relacao a todos os
     * personagens no array
     */
    public boolean ehPosicaoValida(Elemento analisado) {
        Elemento pTemp;
        for (int i = 0; i < faseAtual.size(); i++) {
            pTemp = faseAtual.get(i);
            if (!pTemp.isbTransponivel() && analisado != pTemp) {
                if (pTemp.getPosicao().igual(analisado.getPosicao())) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public static ImageIcon carregarImagem(String nomeImagem){
        ImageIcon iImage = null;
        try {
            iImage = new ImageIcon(new java.io.File(".").getCanonicalPath() + Consts.PATH + nomeImagem);
            Image img = iImage.getImage();
            BufferedImage bi = new BufferedImage(Consts.CELL_SIDE, Consts.CELL_SIDE, BufferedImage.TYPE_INT_ARGB);
            Graphics g = bi.createGraphics();
            g.drawImage(img, 0, 0, Consts.CELL_SIDE, Consts.CELL_SIDE, null);
            iImage = new ImageIcon(bi);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return iImage;
    }
}
