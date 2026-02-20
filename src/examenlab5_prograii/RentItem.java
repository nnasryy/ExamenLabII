/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package examenlab5_prograii;

import javax.swing.ImageIcon;

/**
 *
 * @author nasry
 */
public abstract class RentItem {
    protected int codigo;
    protected String nombre;
    protected double precioRenta;
    protected int cantidadCopias;
    protected ImageIcon imagen;
    
    public RentItem(int codigo, String nombre, double precioRenta){
    this.codigo = codigo;
    this.nombre = nombre;
    this.precioRenta = precioRenta;
    this.cantidadCopias = 0;
    this.imagen = null;
   }
    
    public abstract double pagoRenta(int dias);
    
    public int getCodigo(){
        return codigo;
    }
    
    public String getNombre(){
        return nombre;
    }
    
    public double getPrecioRenta(){
        return precioRenta;
    }
    
    public int getCantidadCopias(){
        return cantidadCopias;
    }
    
    public ImageIcon getImagen(){
        return imagen;
    }
    
    public void setImagen(ImageIcon imagen){
        this.imagen = imagen;
    }
    
    public void setCantidadCopias(int cantidad){
        this.cantidadCopias = cantidad;
    }
    
    @Override
    public String toString(){
    return "Codigo"+codigo+"| Nombre: " + nombre + "| Precio Base: Lps."+precioRenta + "| Copias:"+cantidadCopias;
    }
}
