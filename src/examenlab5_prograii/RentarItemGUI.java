/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package examenlab5_prograii;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author jerem
 */
public class RentarItemGUI extends JFrame {
    
    private ArrayList<RentItem> items;
    private JTextField txtCodigo;
    private JButton btnBuscar;
    private JPanel panelInfo;
    private RentItem itemEncontrado;
    
    public RentarItemGUI(ArrayList<RentItem> items) {
        this.items = items;
        this.itemEncontrado = null;
        
        configurarVentana();
        inicializarComponentes();
    }
    
    private void configurarVentana() {
        setTitle("Rentar Ítem");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
    }
    
    private void inicializarComponentes() {
        // Panel superior - Búsqueda
        JPanel panelBusqueda = crearPanelBusqueda();
        add(panelBusqueda, BorderLayout.NORTH);
        
        // Panel central - Información del ítem
        panelInfo = new JPanel();
        panelInfo.setLayout(new BorderLayout());
        panelInfo.setBorder(BorderFactory.createTitledBorder("Información del Ítem"));
        panelInfo.setPreferredSize(new Dimension(580, 300));
        add(panelInfo, BorderLayout.CENTER);
        
        // Panel inferior - Acciones
        JPanel panelAcciones = crearPanelAcciones();
        add(panelAcciones, BorderLayout.SOUTH);
    }
    
    private JPanel crearPanelBusqueda() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel lblCodigo = new JLabel("Código del Ítem:");
        lblCodigo.setFont(new Font("Arial", Font.BOLD, 14));
        
        txtCodigo = new JTextField(15);
        txtCodigo.setFont(new Font("Arial", Font.PLAIN, 14));
        
        btnBuscar = new JButton("Buscar");
        btnBuscar.setFont(new Font("Arial", Font.BOLD, 14));
        btnBuscar.setBackground(new Color(33, 150, 243));
        btnBuscar.setForeground(Color.WHITE);
        btnBuscar.setFocusPainted(false);
        btnBuscar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnBuscar.addActionListener(e -> buscarItem());
        
        // Enter en el campo de texto también busca
        txtCodigo.addActionListener(e -> buscarItem());
        
        panel.add(lblCodigo);
        panel.add(txtCodigo);
        panel.add(btnBuscar);
        
        return panel;
    }
    
    private JPanel crearPanelAcciones() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JButton btnRentar = new JButton("Rentar");
        btnRentar.setPreferredSize(new Dimension(150, 40));
        btnRentar.setFont(new Font("Arial", Font.BOLD, 14));
        btnRentar.setBackground(new Color(76, 175, 80));
        btnRentar.setForeground(Color.WHITE);
        btnRentar.setFocusPainted(false);
        btnRentar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRentar.addActionListener(e -> procesarRenta());
        
        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.setPreferredSize(new Dimension(150, 40));
        btnCerrar.setFont(new Font("Arial", Font.BOLD, 14));
        btnCerrar.setBackground(new Color(244, 67, 54));
        btnCerrar.setForeground(Color.WHITE);
        btnCerrar.setFocusPainted(false);
        btnCerrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCerrar.addActionListener(e -> dispose());
        
        panel.add(btnRentar);
        panel.add(btnCerrar);
        
        return panel;
    }
    
    private void buscarItem() {
        // Validar que hay ítems
        if (items.isEmpty()) {
            BaseGUI.mostrarAdvertencia(this, "No hay ítems registrados en el sistema.");
            return;
        }
        
        // Validar entrada
        String codigoStr = txtCodigo.getText().trim();
        if (codigoStr.isEmpty()) {
            BaseGUI.mostrarError(this, "Por favor ingrese un código.");
            txtCodigo.requestFocus();
            return;
        }
        
        try {
            int codigo = Integer.parseInt(codigoStr);
            itemEncontrado = buscarItemPorCodigo(codigo);
            
            if (itemEncontrado == null) {
                BaseGUI.mostrarError(this, "Item No Existe");
                limpiarPanelInfo();
            } else {
                mostrarInformacionItem();
            }
            
        } catch (NumberFormatException e) {
            BaseGUI.mostrarError(this, "El código debe ser un número válido.");
            txtCodigo.requestFocus();
        }
    }
    
    private void mostrarInformacionItem() {
        panelInfo.removeAll();
        
        JPanel contenedor = new JPanel(new BorderLayout(10, 10));
        contenedor.setBackground(Color.WHITE);
        contenedor.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // Imagen a la izquierda
        if (itemEncontrado.getImagen() != null) {
            JLabel lblImagen = new JLabel();
            ImageIcon icon = itemEncontrado.getImagen();
            Image img = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            lblImagen.setIcon(new ImageIcon(img));
            lblImagen.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
            contenedor.add(lblImagen, BorderLayout.WEST);
        }
        
        // Información a la derecha
        JPanel panelDatos = new JPanel();
        panelDatos.setLayout(new BoxLayout(panelDatos, BoxLayout.Y_AXIS));
        panelDatos.setBackground(Color.WHITE);
        panelDatos.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 10));
        
        // Nombre
        JLabel lblNombre = new JLabel("Nombre: " + itemEncontrado.getNombre());
        lblNombre.setFont(new Font("Arial", Font.BOLD, 18));
        panelDatos.add(lblNombre);
        panelDatos.add(Box.createRigidArea(new Dimension(0, 15)));
        
        // Estado (solo para películas)
        if (itemEncontrado instanceof Movie) {
            Movie movie = (Movie) itemEncontrado;
            JLabel lblEstado = new JLabel("Estado: " + movie.getEstado());
            lblEstado.setFont(new Font("Arial", Font.PLAIN, 16));
            lblEstado.setForeground(movie.getEstado().equals("ESTRENO") ? 
                new Color(255, 87, 34) : new Color(76, 175, 80));
            panelDatos.add(lblEstado);
            panelDatos.add(Box.createRigidArea(new Dimension(0, 10)));
        }
        
        // Precio
        JLabel lblPrecio = new JLabel("Precio Base: Lps. " + 
            String.format("%.2f", itemEncontrado.getPrecioRenta()));
        lblPrecio.setFont(new Font("Arial", Font.PLAIN, 16));
        panelDatos.add(lblPrecio);
        panelDatos.add(Box.createRigidArea(new Dimension(0, 10)));
        
        // Código
        JLabel lblCodigo = new JLabel("Código: " + itemEncontrado.getCodigo());
        lblCodigo.setFont(new Font("Arial", Font.PLAIN, 14));
        lblCodigo.setForeground(Color.GRAY);
        panelDatos.add(lblCodigo);
        panelDatos.add(Box.createRigidArea(new Dimension(0, 10)));
        
        // Tipo
        String tipo = itemEncontrado instanceof Movie ? "Película" : "Videojuego";
        JLabel lblTipo = new JLabel("Tipo: " + tipo);
        lblTipo.setFont(new Font("Arial", Font.ITALIC, 14));
        lblTipo.setForeground(Color.GRAY);
        panelDatos.add(lblTipo);
        
        contenedor.add(panelDatos, BorderLayout.CENTER);
        
        panelInfo.add(contenedor, BorderLayout.CENTER);
        panelInfo.revalidate();
        panelInfo.repaint();
    }
    
    private void procesarRenta() {
        if (itemEncontrado == null) {
            BaseGUI.mostrarAdvertencia(this, 
                "Primero debe buscar y seleccionar un ítem.");
            return;
        }
        
        // Solicitar cantidad de días
        Integer dias = BaseGUI.solicitarEntero(this, 
            "Ingrese la cantidad de días de renta:");
        
        if (dias == null) return; // Usuario canceló
        
        if (dias <= 0) {
            BaseGUI.mostrarError(this, 
                "La cantidad de días debe ser mayor a 0.");
            return;
        }
        
        // Calcular monto total
        double total = itemEncontrado.pagoRenta(dias);
        
        // Crear panel con detalles de la renta
        JPanel panelResumen = new JPanel();
        panelResumen.setLayout(new BoxLayout(panelResumen, BoxLayout.Y_AXIS));
        panelResumen.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel lblItem = new JLabel("Ítem: " + itemEncontrado.getNombre());
        lblItem.setFont(new Font("Arial", Font.BOLD, 14));
        
        JLabel lblDias = new JLabel("Días de renta: " + dias);
        lblDias.setFont(new Font("Arial", Font.PLAIN, 14));
        
        JLabel lblTotal = new JLabel("TOTAL A PAGAR: Lps. " + 
            String.format("%.2f", total));
        lblTotal.setFont(new Font("Arial", Font.BOLD, 16));
        lblTotal.setForeground(new Color(76, 175, 80));
        
        panelResumen.add(lblItem);
        panelResumen.add(Box.createRigidArea(new Dimension(0, 10)));
        panelResumen.add(lblDias);
        panelResumen.add(Box.createRigidArea(new Dimension(0, 15)));
        panelResumen.add(lblTotal);
        
        JOptionPane.showMessageDialog(this, 
            panelResumen, 
            "Resumen de Renta", 
            JOptionPane.INFORMATION_MESSAGE);
        
        // Limpiar formulario para otra renta
        limpiarFormulario();
    }
    
    private RentItem buscarItemPorCodigo(int codigo) {
        for (RentItem item : items) {
            if (item.getCodigo() == codigo) {
                return item;
            }
        }
        return null;
    }
    
    private void limpiarPanelInfo() {
        panelInfo.removeAll();
        panelInfo.revalidate();
        panelInfo.repaint();
        itemEncontrado = null;
    }
    
    private void limpiarFormulario() {
        txtCodigo.setText("");
        limpiarPanelInfo();
        txtCodigo.requestFocus();
    }
}