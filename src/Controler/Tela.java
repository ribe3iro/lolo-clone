/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Tela.java
 *
 * Created on 03/03/2011, 18:28:20
 */
package Controler;

import Modelo.Lolo;
import Auxiliar.Consts;
import Auxiliar.Desenho;
import Modelo.Cobra;
import Modelo.Elemento;
import Modelo.Grama;
import Modelo.Obstaculo;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import javax.swing.JButton;

/**
 *
 * @author junio
 */
public class Tela extends javax.swing.JFrame implements MouseListener, KeyListener {

    private ControleDeJogo controleDeJogo = new ControleDeJogo(5, 0, this);
    private Graphics g2;

    /**
     * Creates new form tabuleiro
     */
    public Tela() {
        Desenho.setCenario(this);
        initComponents();
        this.addMouseListener(this); /* mouse */

        this.addKeyListener(this); /* teclado */
        /* Cria a janela do tamanho do tabuleiro + insets (bordas) da janela */
        this.setSize((Consts.RES+1) * Consts.CELL_SIDE + getInsets().left + getInsets().right,
                Consts.RES * Consts.CELL_SIDE + getInsets().top + getInsets().bottom);

        this.setTitle("Adventures of Lolo 2 - Java Clone");
    }

    public Graphics getGraphicsBuffer() {
        return g2;
    }

    public void paint(Graphics gOld) {
        Graphics g = this.getBufferStrategy().getDrawGraphics();
        /* Criamos um contexto gráfico */
        g2 = g.create(getInsets().left, getInsets().top, getWidth() - getInsets().right, getHeight() - getInsets().top);
        /************* Desenha cenário de fundo **************/
        for (int i = 0; i < Consts.RES; i++) {
            for (int j = 0; j < Consts.RES; j++) {
                try {
                    Image newImage = Toolkit.getDefaultToolkit()
                            .getImage(new java.io.File(".").getCanonicalPath() + Consts.PATH + "bricks.png");
                    g2.drawImage(newImage,
                            j * Consts.CELL_SIDE, i * Consts.CELL_SIDE, Consts.CELL_SIDE, Consts.CELL_SIDE, null);

                } catch (IOException ex) {
                    Logger.getLogger(Tela.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        if(controleDeJogo.isFinalizado()){
            int meioTela = (Consts.RES * Consts.CELL_SIDE)/2;
            Font font = new Font("Serif", Font.BOLD, 100);
            g2.setFont(font);
            g2.setColor(Color.GREEN);
            g2.drawString("Parabéns!", meioTela - 180, meioTela - 100);

            font = new Font("Serif", Font.BOLD, 30);
            g2.setFont(font);
            g2.setColor(Color.WHITE);
            g2.drawString("Você finalizou o jogo!", meioTela - 100, meioTela - 40);
            g2.drawString("Muito obrigado por jogar!", meioTela - 130, meioTela);
            
            g2.drawString("Jogo recriado por: Caio Rovetta", meioTela - 170, meioTela + 60);
            g2.drawString("João Pedro Ribeiro", meioTela + 73, meioTela + 100);
        }
        else{
            for (int i = 0; i < Consts.RES; i++) {
                try {
                    Image newImage = Toolkit.getDefaultToolkit()
                            .getImage(new java.io.File(".").getCanonicalPath() + Consts.PATH + "fundo.png");
                    g2.drawImage(newImage,
                            Consts.RES * Consts.CELL_SIDE, i * Consts.CELL_SIDE, Consts.CELL_SIDE, Consts.CELL_SIDE, null);

                } catch (IOException ex) {
                    Logger.getLogger(Tela.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            try {
                Image newImage = Toolkit.getDefaultToolkit()
                        .getImage(new java.io.File(".").getCanonicalPath() + Consts.PATH + "lolo0.png");
                g2.drawImage(newImage,
                        Consts.RES * Consts.CELL_SIDE, 1 * Consts.CELL_SIDE, Consts.CELL_SIDE, Consts.CELL_SIDE, null);
                newImage = Toolkit.getDefaultToolkit()
                        .getImage(new java.io.File(".").getCanonicalPath() + Consts.PATH + "tiro.png");
                g2.drawImage(newImage,
                        Consts.RES * Consts.CELL_SIDE, 5 * Consts.CELL_SIDE, Consts.CELL_SIDE, Consts.CELL_SIDE, null);
                Font font = new Font("Arial", Font.BOLD, 40);
                g2.setFont(font);
                g2.setColor(Color.WHITE);
                g2.drawString(Integer.toString(controleDeJogo.getVidas()), Consts.RES * Consts.CELL_SIDE + 25, 3 * Consts.CELL_SIDE);
                g2.drawString(Integer.toString(controleDeJogo.getMunicao()), Consts.RES * Consts.CELL_SIDE + 25, 7 * Consts.CELL_SIDE);

            } catch (IOException ex) {
                Logger.getLogger(Tela.class.getName()).log(Level.SEVERE, null, ex);
            }

            if(controleDeJogo.isGameOver()){
                controleDeJogo.desenhaTudo();
                this.gameOver();
            }else{
                controleDeJogo.processaTudo();
                controleDeJogo.desenhaTudo();
            }
        }

        g.dispose();
        g2.dispose();
        if (!getBufferStrategy().contentsLost()) {
            getBufferStrategy().show();
        }
    }

    public void go() {
        TimerTask task = new TimerTask() {
            public void run() {
                repaint();
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 0, Consts.PERIOD);
    }

    public void keyPressed(KeyEvent e) {
        /*
         * if (e.getKeyCode() == KeyEvent.VK_C) {
         * this.e.clear();
         * } else if (e.getKeyCode() == KeyEvent.VK_L) {
         * try {
         * File tanque = new File("c:\\temp\\POO.zip");
         * FileInputStream canoOut = new FileInputStream(tanque);
         * GZIPInputStream compactador = new GZIPInputStream(canoOut);
         * ObjectInputStream serializador = new ObjectInputStream(compactador);
         * this.e = (ArrayList<Elemento>)serializador.readObject();
         * this.lLolo = (Lolo)this.e.get(0);
         * serializador.close();
         * } catch (Exception ex) {
         * Logger.getLogger(Tela.class.getName()).log(Level.SEVERE, null, ex);
         * }
         * } else if (e.getKeyCode() == KeyEvent.VK_S) {
         * try {
         * File tanque = new File("c:\\temp\\POO.zip");
         * tanque.createNewFile();
         * FileOutputStream canoOut = new FileOutputStream(tanque);
         * GZIPOutputStream compactador = new GZIPOutputStream(canoOut);
         * ObjectOutputStream serializador = new ObjectOutputStream(compactador);
         * serializador.writeObject(this.e);
         * serializador.flush();
         * serializador.close();
         * } catch (IOException ex) {
         * Logger.getLogger(Tela.class.getName()).log(Level.SEVERE, null, ex);
         * }
         * } else
         */
        
        if(e.getKeyCode() == KeyEvent.VK_R && controleDeJogo.isGameOver()){
            controleDeJogo = new ControleDeJogo(5, 0, this);
        }
        
        controleDeJogo.teclaPressionada(e.getKeyCode());
        // repaint(); /*invoca o paint imediatamente, sem aguardar o refresh*/
    }
    
    public void gameOver(){
        int meioTela = (Consts.RES * Consts.CELL_SIDE)/2;
        Font font = new Font("Serif", Font.BOLD, 70);
        g2.setFont(font);
        g2.setColor(Color.RED);
        g2.drawString("Game Over!", meioTela - 170, meioTela);
        
        font = new Font("Serif", Font.BOLD, 30);
        g2.setFont(font);
        g2.setColor(Color.WHITE);
        g2.drawString("Pressione [R] para tentar novamente", meioTela - 210, meioTela + 50);
    }

    public void mousePressed(MouseEvent e) {
        /*
         * Clique do mouse desligado
         * int x = e.getX();
         * int y = e.getY();
         * 
         * this.setTitle("X: "+ x + ", Y: " + y +
         * " -> Cell: " + (y/Consts.CELL_SIDE) + ", " + (x/Consts.CELL_SIDE));
         * 
         * this.lLolo.getPosicao().setPosicao(y/Consts.CELL_SIDE, x/Consts.CELL_SIDE);
         */
        repaint();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("POO2015-1 - Adventures of lolo");
        setAutoRequestFocus(false);
        setResizable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 561, Short.MAX_VALUE));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 500, Short.MAX_VALUE));

        pack();
    }// </editor-fold>//GEN-END:initComponents
     // Variables declaration - do not modify//GEN-BEGIN:variables
     // End of variables declaration//GEN-END:variables

    public void mouseMoved(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseDragged(MouseEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }
}
