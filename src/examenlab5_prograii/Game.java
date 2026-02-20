/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package examenlab5_prograii;

import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author hermi
 */
public class Game extends RentItem implements MenuActions{
    private Calendar fechaPublicacion;
    private ArrayList<String> especificaciones;
    
    public Game(int codigo, String nombre, ImageIcon imagen){
        super(codigo, nombre, 20.0);
        this.fechaPublicacion = Calendar.getInstance();
        this.especificaciones = new ArrayList<>();
        this.imagen = imagen;
    }
    
    public void setFechaPublicacion(int year, int mes, int dia){
        fechaPublicacion.set(year, mes - 1, dia);
    }
    
    public Calendar getFechaPublicacion(){
        return fechaPublicacion;
    }
    
    @Override
    public double pagoRenta(int dias){
        return precioRenta * dias;
    }
    
    @Override
    public String toString(){
        return super.toString() + "|Fecha Pub: " + fechaPublicacion.get(Calendar.DAY_OF_MONTH) + "/" +
                (fechaPublicacion.get(Calendar.MONTH) + 1) + "/" + fechaPublicacion.get(Calendar.YEAR) + "- PS3 Game";
        
    }
    
    
    public void listEspecificaciones(){
        if(especificaciones.isEmpty()){
            System.out.println("No hay especificaciones.");
        
        JOptionPane.showMessageDialog(null,"No hay especificaciones registradas.");
        }else{
        StringBuilder sb = new StringBuilder("Especificaciones:\n");
            listarRecursivo(0,sb);
            JOptionPane.showMessageDialog(null,sb.toString());
        }
    }
    
    private void listarRecursivo(int index, StringBuilder sb){
        if(index >= especificaciones.size()){
            return;
        }
        sb.append("-").append(especificaciones.get(index)).append("\n");
        listarRecursivo(index + 1, sb);
    }
    
    @Override
    public void submenu(){
        String opciones = "1. Actualizar Fecha de Publicacion\n"+
                          "2. Agregar Especificacion\n"+
                          "3. Ver Especificacones\n"+
                          "4. Salir";
        JOptionPane.showMessageDialog(null,opciones,"SubMenu Game",JOptionPane.INFORMATION_MESSAGE);
    }
    
    @Override
    public void ejecutarOpcion(int opcion){
        switch(opcion){
            case 1:
                try {
                    String yStr = JOptionPane.showInputDialog("Año:");
                    String mStr = JOptionPane.showInputDialog("Mes:");
                    String dStr = JOptionPane.showInputDialog("Dia:");
                    
                    setFechaPublicacion(Integer.parseInt(yStr), Integer.parseInt(mStr), Integer.parseInt(dStr));
                    
                    JOptionPane.showMessageDialog(null, "Fecha actualizada.");
                } catch (Exception e){
                    JOptionPane.showMessageDialog(null,"Error en los datos.");
                }
                break;
            case 2:
                String spec = JOptionPane.showInputDialog("Ingrese nueva especificacion:");
                    if(spec != null && !spec.isEmpty()){
                       especificaciones.add(spec);
                       JOptionPane.showMessageDialog(null, "Especificacion agregada.");
                    }
                break;
            case 3:
                listEspecificaciones();
                break;
            case 4:
                break;
            default:
            JOptionPane.showConfirmDialog(null, "Opcion Invalida.");
        }
    }
}
    
