/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package examenlab5_prograii;

import java.util.Calendar;
import javax.swing.ImageIcon;

/**
 *
 * @author nasry
 */import javax.swing.*;
import java.util.Calendar;

public class Movie extends RentItem {

    private Calendar fechaEstreno;

    public Movie(int codigo, String nombre, double precioRenta, int cantidadCopias, String rutaImagen) {
        super(codigo, nombre, precioRenta);
        this.cantidadCopias = cantidadCopias;         
        this.imagen = new ImageIcon(rutaImagen);         
        this.fechaEstreno = Calendar.getInstance();      
    }

    public Calendar getFechaEstreno() {
        return fechaEstreno;
    }

    public void setFechaEstreno(Calendar fechaEstreno) {
        this.fechaEstreno = fechaEstreno;
    }

    public String getEstado() {
        Calendar actual = Calendar.getInstance();
        long diffMillis = actual.getTimeInMillis() - fechaEstreno.getTimeInMillis();
        long diffMeses = diffMillis / (1000L * 60 * 60 * 24 * 30);
        return (diffMeses <= 3) ? "ESTRENO" : "NORMAL";
    }

    @Override
    public double pagoRenta(int dias) {
        double total = precioRenta * dias;
        String estado = getEstado();
        if (estado.equals("ESTRENO") && dias > 2) {
            total += (dias - 2) * 50;
        } else if (estado.equals("NORMAL") && dias > 5) {
            total += (dias - 5) * 30;
        }
        return total;
    }

    @Override
    public String toString() {
        return super.toString() + "| Estado: " + getEstado() + " - Movie";
    }
}