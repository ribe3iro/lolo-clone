/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controler;

import Auxiliar.Consts;
import Modelo.Cobra;
import Modelo.Coracao;
import Modelo.Elemento;
import Modelo.Grama;
import Modelo.Lolo;
import Modelo.Obstaculo;
import Modelo.Bau;
import Modelo.BlocoEmpurravel;
import Modelo.Caveira;
import Modelo.Porta;
import Modelo.Tatu;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 *
 * @author joaop
 */
public class Fase extends ArrayList<Elemento> implements Serializable {
    public Fase() {
        super();
    }

    public Lolo getLolo() {
        return (Lolo) this.get(0);
    }

    public boolean salvar(String nomeArq) {
        try {
            File file = new File(
                    new java.io.File(".").getCanonicalPath() + File.separator + "fases" + File.separator + nomeArq);
            file.createNewFile();
            FileOutputStream fileOS = new FileOutputStream(file);
            GZIPOutputStream compactador = new GZIPOutputStream(fileOS);
            ObjectOutputStream serializador = new ObjectOutputStream(compactador);
            serializador.writeObject(this);
            serializador.flush();
            serializador.close();
        } catch (IOException ex) {
            System.out.println("Erro ao salvar fase " + nomeArq);
            return false;
        }

        return true;
    }

    public static Fase carregar(String nomeArq) {
        Fase fase = null;
        try {
            File file = new File(
                    new java.io.File(".").getCanonicalPath() + File.separator + "fases" + File.separator + nomeArq);
            FileInputStream fileIS = new FileInputStream(file);
            GZIPInputStream compactador = new GZIPInputStream(fileIS);
            ObjectInputStream serializador = new ObjectInputStream(compactador);
            fase = (Fase) serializador.readObject();
            serializador.close();
        } catch (IOException ex) {
            System.out.println("Erro IO ao carregar fase " + nomeArq);
        } catch (ClassNotFoundException ex) {
            System.out.println("Erro ClassNotFound ao carregar fase " + nomeArq);
        }

        return fase;
    }

    public static void main(String[] args) {
        // Criando fase1.level

        Fase fase1 = new Fase();
        /* Cria e adiciona personagens */
        // Lolo

        fase1.add(new Lolo(8, 6));

        // bau
        fase1.add(new Bau(3, 6));

        // Bordas
        for (int i = 0; i < Consts.RES; i++) {
            fase1.add(new Obstaculo(i, 0, "parede_vertical.png"));
            fase1.add(new Obstaculo(i, Consts.RES - 1, "parede_vertical.png"));
        }
        for (int j = 1; j < Consts.RES - 1; j++) {

            if (j == 6) {
                fase1.add(new Porta(0, j));
            } else {
                fase1.add(new Obstaculo(0, j, "parede_horizontal.png"));
            }
            fase1.add(new Obstaculo(Consts.RES - 1, j, "parede_horizontal.png"));
        }

        // Obstaculos
        fase1.add(new Obstaculo(1, 1, "pedra.png"));
        fase1.add(new Obstaculo(1, 9, "arvore.png"));
        fase1.add(new Obstaculo(1, 10, "arvore.png"));
        fase1.add(new Obstaculo(2, 3, "arvore.png"));
        fase1.add(new Obstaculo(2, 4, "arvore.png"));
        fase1.add(new Obstaculo(2, 8, "pedra.png"));
        fase1.add(new Obstaculo(2, 9, "arvore.png"));
        fase1.add(new Obstaculo(2, 10, "arvore.png"));
        fase1.add(new Obstaculo(3, 1, "pedra.png"));
        fase1.add(new Obstaculo(3, 3, "arvore.png"));
        fase1.add(new Obstaculo(3, 4, "arvore.png"));
        fase1.add(new Obstaculo(3, 8, "pedra.png"));
        fase1.add(new Obstaculo(3, 9, "pedra.png"));
        fase1.add(new Obstaculo(3, 10, "arvore.png"));
        fase1.add(new Obstaculo(4, 1, "arvore.png"));
        fase1.add(new Obstaculo(4, 9, "pedra.png"));
        fase1.add(new Obstaculo(7, 1, "arvore.png"));
        fase1.add(new Obstaculo(8, 1, "arvore.png"));
        fase1.add(new Obstaculo(8, 2, "arvore.png"));
        fase1.add(new Obstaculo(9, 1, "arvore.png"));
        fase1.add(new Obstaculo(9, 2, "arvore.png"));
        fase1.add(new Obstaculo(9, 7, "arvore.png"));
        fase1.add(new Obstaculo(9, 11, "pedra.png"));
        fase1.add(new Obstaculo(10, 1, "pedra.png"));
        fase1.add(new Obstaculo(10, 5, "pedra.png"));
        fase1.add(new Obstaculo(10, 6, "pedra.png"));
        fase1.add(new Obstaculo(10, 7, "arvore.png"));
        fase1.add(new Obstaculo(10, 10, "pedra.png"));
        fase1.add(new Obstaculo(10, 11, "arvore.png"));
        fase1.add(new Obstaculo(11, 1, "pedra.png"));
        fase1.add(new Obstaculo(11, 4, "pedra.png"));
        fase1.add(new Obstaculo(11, 5, "pedra.png"));
        fase1.add(new Obstaculo(11, 6, "arvore.png"));
        fase1.add(new Obstaculo(11, 7, "arvore.png"));
        fase1.add(new Obstaculo(11, 10, "arvore.png"));
        fase1.add(new Obstaculo(11, 11, "arvore.png"));
        // fase1.add(new Obstaculo(3,6,"bau_fechado.jpeg"));

        // Grama
        fase1.add(new Grama(5, 1));
        fase1.add(new Grama(6, 1));
        fase1.add(new Grama(6, 2));
        fase1.add(new Grama(6, 11));
        fase1.add(new Grama(7, 2));
        fase1.add(new Grama(7, 11));
        fase1.add(new Grama(8, 10));
        fase1.add(new Grama(8, 11));
        fase1.add(new Grama(9, 10));

        // Inimigos
        fase1.add(new Cobra(2, 2));
        fase1.add(new Cobra(6, 4));
        fase1.add(new Cobra(6, 8));

        // Colecion??veis
        fase1.add(new Coracao(1, 8, 2));
        fase1.add(new Coracao(1, 11));
        fase1.add(new Coracao(2, 1));
        fase1.add(new Coracao(11, 2));
        fase1.add(new Coracao(11, 8));
        fase1.add(new Coracao(11, 9));

        fase1.salvar("fase1.level");

        /* Fase 2 */
        Fase fase2 = new Fase();
        /* Cria e adiciona personagens */
        fase2.add(new Lolo(11, 11));
        fase2.add(new Bau(1, 1));
        for (int i = 0; i < Consts.RES; i++) {
            fase2.add(new Obstaculo(i, 0, "parede_vertical.png"));
            fase2.add(new Obstaculo(i, Consts.RES - 1, "parede_vertical.png"));
        }
        for (int j = 1; j < Consts.RES - 1; j++) {

            if (j == 7) {
                fase2.add(new Porta(0, j));
            } else {
                fase2.add(new Obstaculo(0, j, "parede_horizontal.png"));
            }
            fase2.add(new Obstaculo(Consts.RES - 1, j, "parede_horizontal.png"));
        }

        fase2.add(new Obstaculo(7, 11, "arvore.png"));
        fase2.add(new Obstaculo(11, 10, "arvore.png"));
        fase2.add(new Obstaculo(10, 10, "arvore.png"));
        fase2.add(new Obstaculo(10, 10, "arvore.png"));
        fase2.add(new Obstaculo(8, 10, "arvore.png"));
        fase2.add(new Obstaculo(4, 10, "arvore.png"));
        fase2.add(new Obstaculo(3, 10, "arvore.png"));
        fase2.add(new Obstaculo(8, 9, "arvore.png"));
        fase2.add(new Obstaculo(9, 8, "arvore.png"));
        fase2.add(new Obstaculo(1, 8, "arvore.png"));
        fase2.add(new Obstaculo(2, 8, "arvore.png"));
        fase2.add(new Obstaculo(4, 8, "arvore.png"));
        fase2.add(new Obstaculo(6, 7, "arvore.png"));
        fase2.add(new Obstaculo(8, 7, "arvore.png"));
        fase2.add(new Obstaculo(10, 7, "arvore.png"));
        fase2.add(new Obstaculo(1, 6, "arvore.png"));
        fase2.add(new Obstaculo(2, 6, "arvore.png"));
        fase2.add(new Obstaculo(5, 6, "arvore.png"));
        fase2.add(new Obstaculo(10, 6, "arvore.png"));
        fase2.add(new Obstaculo(1, 5, "arvore.png"));
        fase2.add(new Obstaculo(6, 5, "arvore.png"));
        fase2.add(new Obstaculo(1, 4, "arvore.png"));
        fase2.add(new Obstaculo(2, 4, "arvore.png"));
        fase2.add(new Obstaculo(8, 4, "arvore.png"));
        fase2.add(new Obstaculo(10, 4, "arvore.png"));
        fase2.add(new Obstaculo(6, 3, "arvore.png"));
        fase2.add(new Obstaculo(1, 2, "arvore.png"));
        fase2.add(new Obstaculo(5, 2, "arvore.png"));
        fase2.add(new Obstaculo(11, 2, "arvore.png"));
        fase2.add(new Obstaculo(7, 1, "arvore.png"));

        fase2.add(new Obstaculo(2, 10, "pedra.png"));
        fase2.add(new Obstaculo(5, 1, "pedra.png"));
        fase2.add(new Obstaculo(11, 1, "pedra.png"));
        fase2.add(new Obstaculo(3, 2, "pedra.png"));
        fase2.add(new Obstaculo(4, 2, "pedra.png"));
        fase2.add(new Obstaculo(10, 2, "pedra.png"));
        fase2.add(new Obstaculo(8, 3, "pedra.png"));
        fase2.add(new Obstaculo(4, 4, "pedra.png"));
        fase2.add(new Obstaculo(6, 4, "pedra.png"));
        fase2.add(new Obstaculo(11, 4, "pedra.png"));
        fase2.add(new Obstaculo(8, 5, "pedra.png"));
        fase2.add(new Obstaculo(4, 6, "pedra.png"));
        fase2.add(new Obstaculo(8, 6, "pedra.png"));
        fase2.add(new Obstaculo(3, 8, "pedra.png"));
        fase2.add(new Obstaculo(8, 8, "pedra.png"));
        fase2.add(new Obstaculo(6, 9, "pedra.png"));
        fase2.add(new Obstaculo(11, 9, "pedra.png"));
        fase2.add(new Obstaculo(2, 10, "pedra.png"));
        fase2.add(new Obstaculo(5, 10, "pedra.png"));
        // Inimigos
        fase2.add(new Cobra(7, 5));
        fase2.add(new Caveira(3, 5));
        fase2.add(new Caveira(6, 8));

        // colecionaveis
        fase2.add(new Coracao(6, 1, 2));
        fase2.add(new Coracao(10, 1));
        fase2.add(new Coracao(1, 3));
        fase2.add(new Coracao(6, 6));
        fase2.add(new Coracao(1, 7));
        fase2.add(new Coracao(9, 7, 2));
        fase2.add(new Coracao(8, 11));

        fase2.salvar("fase2.level");

        // Fase 3
        Fase fase3 = new Fase();
        fase3.add(new Lolo(11, 6));
        fase3.add(new Bau(5, 2));
        for (int i = 0; i < Consts.RES; i++) {
            fase3.add(new Obstaculo(i, 0, "parede_vertical.png"));
            fase3.add(new Obstaculo(i, Consts.RES - 1, "parede_vertical.png"));
        }
        for (int j = 1; j < Consts.RES - 1; j++) {

            if (j == 7) {
                fase3.add(new Porta(0, j));
            } else {
                fase3.add(new Obstaculo(0, j, "parede_horizontal.png"));
            }
            fase3.add(new Obstaculo(Consts.RES - 1, j, "parede_horizontal.png"));
        }
        fase3.add(new Obstaculo(1, 4, "pedra.png"));
        fase3.add(new Obstaculo(1, 9, "pedra.png"));
        fase3.add(new Obstaculo(2, 4, "pedra.png"));
        fase3.add(new Obstaculo(2, 5, "pedra.png"));
        fase3.add(new Obstaculo(2, 8, "pedra.png"));
        fase3.add(new Obstaculo(2, 9, "pedra.png"));
        fase3.add(new Obstaculo(4, 2, "pedra.png"));
        fase3.add(new Obstaculo(4, 3, "pedra.png"));
        fase3.add(new Obstaculo(4, 4, "pedra.png"));
        fase3.add(new Obstaculo(5, 5, "pedra.png"));
        fase3.add(new Obstaculo(8, 10, "pedra.png"));
        fase3.add(new Obstaculo(9, 9, "pedra.png"));
        fase3.add(new Obstaculo(10, 9, "pedra.png"));
        fase3.add(new Obstaculo(1, 5, "arvore.png"));
        fase3.add(new Obstaculo(1, 6, "arvore.png"));
        fase3.add(new Obstaculo(2, 6, "arvore.png"));
        fase3.add(new Obstaculo(4, 1, "arvore.png"));
        fase3.add(new Obstaculo(4, 5, "arvore.png"));
        fase3.add(new Obstaculo(4, 6, "arvore.png"));
        fase3.add(new Obstaculo(4, 7, "arvore.png"));
        fase3.add(new Obstaculo(4, 9, "arvore.png"));
        fase3.add(new Obstaculo(4, 10, "arvore.png"));
        fase3.add(new Obstaculo(5, 6, "arvore.png"));
        fase3.add(new Obstaculo(8, 9, "arvore.png"));
        fase3.add(new Obstaculo(9, 6, "arvore.png"));
        fase3.add(new Obstaculo(10, 5, "arvore.png"));
        fase3.add(new Obstaculo(11, 9, "arvore.png"));
        
        fase3.add(new Obstaculo(5, 4, "agua.png"));
        fase3.add(new Obstaculo(6, 4, "agua.png"));
        fase3.add(new Obstaculo(7, 4, "agua.png"));
        fase3.add(new Obstaculo(7, 5, "agua.png"));
        fase3.add(new Obstaculo(8, 4, "agua.png"));
        fase3.add(new Obstaculo(8, 5, "agua.png"));
        fase3.add(new Obstaculo(8, 6, "agua.png"));
        fase3.add(new Obstaculo(9, 4, "agua.png"));
        fase3.add(new Obstaculo(9, 5, "agua.png"));
        fase3.add(new Obstaculo(10, 4, "agua.png"));

        // Inimigos
        fase3.add(new Tatu(1, 1));
        
        // colecionaveis
        fase3.add(new Coracao(1, 8));
        fase3.add(new Coracao(3, 4));
        fase3.add(new Coracao(5, 1));
        fase3.add(new Coracao(5, 3));
        fase3.add(new Coracao(8, 2));
        fase3.add(new Coracao(9, 10));

        // bloco empurravel
        fase3.add(new BlocoEmpurravel(7, 1));
        fase3.add(new BlocoEmpurravel(7, 2));
        fase3.add(new BlocoEmpurravel(7, 3));
        fase3.add(new BlocoEmpurravel(10, 1));
        fase3.add(new BlocoEmpurravel(10, 2));
        fase3.add(new BlocoEmpurravel(10, 3));
        fase3.add(new BlocoEmpurravel(7, 10));
        

        fase3.salvar("fase3.level");

        // Elementos din??micos serializados
        Cobra cobra = new Cobra(5, 9);
        cobra.salvar("cobra.elem");
        
        Caveira caveira = new Caveira(7, 9);
        caveira.acordar();
        caveira.salvar("caveira.elem");
        
        Tatu tatu = new Tatu(7, 9);
        tatu.salvar("tatu.elem");
    }
}
