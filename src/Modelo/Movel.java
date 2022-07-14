/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author joaop
 */
public interface Movel {
    public void atualizar();
    
    public boolean moveUp();
    public boolean moveDown();
    public boolean moveRight();
    public boolean moveLeft();
}
