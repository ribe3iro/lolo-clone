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
import Modelo.Porta;
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
        fase1.add(new Bau(3, 6, "bau_fechado.jpeg"));

        // Bordas
        for (int i = 0; i < Consts.RES; i++) {
            fase1.add(new Obstaculo(i, 0, "parede_vertical.png"));
            fase1.add(new Obstaculo(i, Consts.RES - 1, "parede_vertical.png"));
        }
        for (int j = 1; j < Consts.RES - 1; j++) {

            if (j == 6) {
                fase1.add(new Porta(0, j, "porta_fechada.jpeg"));
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

        // Colecionáveis
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
        fase2.add(new Bau(1, 1, "bau_fechado.jpeg"));
        for (int i = 0; i < Consts.RES; i++) {
            fase2.add(new Obstaculo(i, 0, "parede_vertical.png"));
            fase2.add(new Obstaculo(i, Consts.RES - 1, "parede_vertical.png"));
        }
        for (int j = 1; j < Consts.RES - 1; j++) {

            if (j == 7) {
                fase2.add(new Porta(0, j, "porta_fechada.jpeg"));
            } else {
                fase2.add(new Obstaculo(0, j, "parede_horizontal.png"));
            }
            fase2.add(new Obstaculo(Consts.RES - 1, j, "parede_horizontal.png"));
        }

        fase2.add(new Obstaculo(11, 10, "arvore.png"));
        fase2.add(new Obstaculo(10, 10, "arvore.png"));
        fase2.add(new Obstaculo(10, 10, "arvore.png"));

        fase2.salvar("fase2.level");

    }
}
