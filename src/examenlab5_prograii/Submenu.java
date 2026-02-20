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
import java.util.Calendar;


public class Submenu extends JFrame {

    private MenuActions itemSeleccionado; // Game que tiene el submenú
    private JLabel lblImagen;
    private JLabel lblFechaPublicacion; // Nuevo JLabel para la fecha
    private JPanel panelInfo;

    public Submenu(MenuActions item) {
        this.itemSeleccionado = item;
        configurarVentana();
        mostrarInfoItem();
        crearBotonesSubmenu();
        setVisible(true);
    }

    private void configurarVentana() {
        setTitle("Submenu Videojuego");
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

        // NUEVO: Fecha de publicación
        lblFechaPublicacion = new JLabel("Fecha Publicación: " + formatoFecha());
        lblFechaPublicacion.setFont(new Font("Arial", Font.PLAIN, 16));
        lblFechaPublicacion.setForeground(Color.RED.darker());
        panelDatos.add(lblFechaPublicacion);
        panelDatos.add(Box.createRigidArea(new Dimension(0, 10)));

        panelInfo.add(panelDatos, BorderLayout.CENTER);
        add(panelInfo, BorderLayout.CENTER);
    }

    private String formatoFecha() {
        RentItem ri = (RentItem) itemSeleccionado;
        if (ri instanceof Game game) {
            Calendar f = game.getFechaPublicacion();
            return f.get(Calendar.DAY_OF_MONTH) + "/" + (f.get(Calendar.MONTH) + 1) + "/" + f.get(Calendar.YEAR);
        }
        return "-";
    }

    private void crearBotonesSubmenu() {
        JPanel panelBotones = new JPanel(new GridLayout(4, 1, 15, 15));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        panelBotones.setBackground(new Color(245, 245, 245));

        // Tonalidades rojas para todos los botones
        JButton btnActualizar = BaseGUI.crearBoton("Actualizar Fecha de Publicación", new Color(200, 0, 0));
        JButton btnAgregar = BaseGUI.crearBoton("Agregar Especificación", new Color(180, 0, 0));
        JButton btnVer = BaseGUI.crearBoton("Ver Especificaciones", new Color(160, 0, 0));
        JButton btnSalir = BaseGUI.crearBoton("Salir", new Color(140, 0, 0));

        panelBotones.add(btnActualizar);
        panelBotones.add(btnAgregar);
        panelBotones.add(btnVer);
        panelBotones.add(btnSalir);
        add(panelBotones, BorderLayout.SOUTH);

        // Conectar botones con las acciones de Game y refrescar fecha
        btnActualizar.addActionListener(e -> {
    if (itemSeleccionado instanceof Game game) {
        SpinnerDateModel dateModel = new SpinnerDateModel(game.getFechaPublicacion().getTime(), null, null, Calendar.DAY_OF_MONTH);
        JSpinner dateSpinner = new JSpinner(dateModel);
        dateSpinner.setEditor(new JSpinner.DateEditor(dateSpinner, "dd/MM/yyyy")); // Formato dd/MM/yyyy

        int opcion = JOptionPane.showOptionDialog(
            this,
            dateSpinner,
            "Seleccione Fecha de Publicación",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            null,
            null
        );

        if (opcion == JOptionPane.OK_OPTION) {
            // Actualizar la fecha del Game
            Calendar nuevaFecha = Calendar.getInstance();
            nuevaFecha.setTime((java.util.Date) dateSpinner.getValue());
            game.setFechaPublicacion(
                nuevaFecha.get(Calendar.YEAR),
                nuevaFecha.get(Calendar.MONTH) + 1,
                nuevaFecha.get(Calendar.DAY_OF_MONTH)
            );

            // Refrescar la etiqueta
            lblFechaPublicacion.setText("Fecha Publicación: " + formatoFecha());
        }
        }
    });

        btnAgregar.addActionListener(e -> itemSeleccionado.ejecutarOpcion(2));
        btnVer.addActionListener(e -> itemSeleccionado.ejecutarOpcion(3));
        btnSalir.addActionListener(e -> dispose());
    }
}