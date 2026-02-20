/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package examenlab5_prograii;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;

/**
 *
 * @author jerem
 */
public class Submenu extends JFrame {

    private MenuActions itemSeleccionado; // Game que tiene el submenú
    private JLabel lblImagen;
    private JPanel panelInfo;

    public Submenu(MenuActions item) {
        this.itemSeleccionado = item;
        configurarVentana();
        mostrarInfoItem();
        crearBotonesSubmenu();
        setVisible(true);
    }

    private void configurarVentana() {
        setTitle("Submenú Videojuego");
        setSize(700, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(15, 15));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(new Color(245, 245, 245));
    }

    private void mostrarInfoItem() {
        panelInfo = new JPanel(new BorderLayout(10, 10));
        panelInfo.setBackground(Color.WHITE);
        panelInfo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(20, 20, 20, 20),
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1)
        ));

        // Imagen a la izquierda
        lblImagen = new JLabel("Sin imagen", SwingConstants.CENTER);
        lblImagen.setPreferredSize(new Dimension(250, 250));
        lblImagen.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 2),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        lblImagen.setOpaque(true);
        lblImagen.setBackground(new Color(250, 250, 250));

        if (itemSeleccionado instanceof RentItem ri && ri.getImagen() != null) {
            ImageIcon icon = ri.getImagen();
            Image img = icon.getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH);
            lblImagen.setIcon(new ImageIcon(img));
            lblImagen.setText("");
        }

        panelInfo.add(lblImagen, BorderLayout.WEST);

        // Información a la derecha
        JPanel panelDatos = new JPanel();
        panelDatos.setLayout(new BoxLayout(panelDatos, BoxLayout.Y_AXIS));
        panelDatos.setBackground(Color.WHITE);
        panelDatos.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 10));

        RentItem ri = (RentItem) itemSeleccionado;

        JLabel lblNombre = new JLabel("Nombre: " + ri.getNombre());
        lblNombre.setFont(new Font("Arial", Font.BOLD, 20));
        panelDatos.add(lblNombre);
        panelDatos.add(Box.createRigidArea(new Dimension(0, 10)));

        JLabel lblCodigo = new JLabel("Código: " + ri.getCodigo());
        lblCodigo.setFont(new Font("Arial", Font.PLAIN, 16));
        lblCodigo.setForeground(Color.GRAY);
        panelDatos.add(lblCodigo);
        panelDatos.add(Box.createRigidArea(new Dimension(0, 10)));

        JLabel lblTipo = new JLabel("Tipo: Videojuego");
        lblTipo.setFont(new Font("Arial", Font.ITALIC, 16));
        lblTipo.setForeground(Color.DARK_GRAY);
        panelDatos.add(lblTipo);
        panelDatos.add(Box.createRigidArea(new Dimension(0, 10)));

        JLabel lblPrecio = new JLabel("Precio Base: Lps. " + String.format("%.2f", ri.getPrecioRenta()));
        lblPrecio.setFont(new Font("Arial", Font.PLAIN, 16));
        panelDatos.add(lblPrecio);
        panelDatos.add(Box.createRigidArea(new Dimension(0, 10)));

        panelInfo.add(panelDatos, BorderLayout.CENTER);

        add(panelInfo, BorderLayout.CENTER);
    }

    private void crearBotonesSubmenu() {
        JPanel panelBotones = new JPanel(new GridLayout(4, 1, 15, 15));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        panelBotones.setBackground(new Color(245, 245, 245));

        JButton btnActualizar = BaseGUI.crearBoton("Actualizar Fecha de Publicación", new Color(33, 150, 243));
        JButton btnAgregar = BaseGUI.crearBoton("Agregar Especificación", new Color(76, 175, 80));
        JButton btnVer = BaseGUI.crearBoton("Ver Especificaciones", new Color(255, 152, 0));
        JButton btnSalir = BaseGUI.crearBoton("Salir", new Color(244, 67, 54));

        panelBotones.add(btnActualizar);
        panelBotones.add(btnAgregar);
        panelBotones.add(btnVer);
        panelBotones.add(btnSalir);

        add(panelBotones, BorderLayout.SOUTH);

        // Conectar botones con las acciones de Game
        btnActualizar.addActionListener(e -> itemSeleccionado.ejecutarOpcion(1));
        btnAgregar.addActionListener(e -> itemSeleccionado.ejecutarOpcion(2));
        btnVer.addActionListener(e -> itemSeleccionado.ejecutarOpcion(3));
        btnSalir.addActionListener(e -> dispose());
    }
}
