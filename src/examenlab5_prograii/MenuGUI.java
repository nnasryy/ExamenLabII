/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package examenlab5_prograii;


import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
/**
 *
 * @author jerem
 */
public class MenuGUI extends JFrame {

    private ArrayList<RentItem> items;
    private JPanel mainPanel;

    public MenuGUI() {
        items = new ArrayList<>();
        configurarVentana();
        crearMenu();
    }

    private void configurarVentana() {
        setTitle("RENTA MULTIMEDIA");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(240, 245, 250));
    }

    private void crearMenu() {
        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(250, 240, 240));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titulo = new JLabel("RENTA MULTIMEDIA", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 32));
        titulo.setForeground(new Color(178, 34, 34)); 
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        mainPanel.add(titulo, gbc);

        JLabel subtitulo = new JLabel("D: Gestion de Movies y Games :D", SwingConstants.CENTER);
        subtitulo.setFont(new Font("Arial", Font.ITALIC, 16));
        subtitulo.setForeground(new Color(220, 20, 60));
        gbc.gridy = 1;
        mainPanel.add(subtitulo, gbc);

        gbc.gridy = 2;
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)), gbc);

        gbc.gridy = 3;
        JButton btnAgregar = crearBotonMenu("Agregar Item", new Color(220, 20, 60));
        btnAgregar.addActionListener(e -> abrirAgregarItem());
        mainPanel.add(btnAgregar, gbc);

        gbc.gridy = 4;
        JButton btnRentar = crearBotonMenu("Rentar", new Color(178, 34, 34));
        btnRentar.addActionListener(e -> abrirRentar());
        mainPanel.add(btnRentar, gbc);

        gbc.gridy = 5;
        JButton btnSubmenu = crearBotonMenu("Ejecutar Submenu", new Color(205, 92, 92));
        btnSubmenu.addActionListener(e -> ejecutarSubmenu());
        mainPanel.add(btnSubmenu, gbc);

        gbc.gridy = 6;
        JButton btnImprimir = crearBotonMenu("Imprimir Todo", new Color(220, 20, 60));
        btnImprimir.addActionListener(e -> imprimirTodo());
        mainPanel.add(btnImprimir, gbc);

        gbc.gridy = 7;
        JButton btnSalir = crearBotonMenu("Salir", new Color(139, 0, 0));
        btnSalir.addActionListener(e -> salir());
        mainPanel.add(btnSalir, gbc);

        add(mainPanel, BorderLayout.CENTER);
    }


    private JButton crearBotonMenu(String texto, Color color) {
        JButton btn = new JButton(texto);
        btn.setPreferredSize(new Dimension(350, 60));
        btn.setFont(new Font("Arial", Font.BOLD, 18));
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(color.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(color);
            }
        });
        
        return btn;
    }

    private void abrirAgregarItem() {
        AgregarItem ventana = new AgregarItem(items);
        ventana.setVisible(true);
    }

    private void abrirRentar() {
        if (items.isEmpty()) {
            BaseGUI.mostrarAdvertencia(this, "No hay ítems registrados en el sistema.");
            return;
        }
        RentarItemGUI ventana = new RentarItemGUI(items);
        ventana.setVisible(true);
    }

    private void ejecutarSubmenu() {
        if (items.isEmpty()) {
            BaseGUI.mostrarAdvertencia(this, "No hay ítems registrados en el sistema.");
            return;
        }
        
        Integer codigo = BaseGUI.solicitarEntero(this, "Ingrese el código del ítem:");
        if (codigo == null) return;

        RentItem item = buscarItemPorCodigo(codigo);
        if (item == null) {
            BaseGUI.mostrarError(this, "Item No Existe");
            return;
        }

        if (item instanceof MenuActions) {
            MenuActions menuItem = (MenuActions) item;
            menuItem.submenu();
        } else {
            BaseGUI.mostrarAdvertencia(this, 
                "Este item no tiene submenú disponible.\n" +
                "Solo los videojuegos (Game) tienen submenu :c.");
        }
    }

    private void imprimirTodo() {
    if (items.isEmpty()) {
        BaseGUI.mostrarAdvertencia(this, "No hay ítems registrados en el sistema.");
        return;
    }
    
    JDialog dialogo = new JDialog(this, "Lista de Ítems", true);
    dialogo.setSize(950, 750);
    dialogo.setLocationRelativeTo(this);
    dialogo.setLayout(new BorderLayout(10, 10));

    JPanel contenedor = new JPanel();
    contenedor.setLayout(new BoxLayout(contenedor, BoxLayout.Y_AXIS));
    contenedor.setBackground(new Color(245, 245, 245));
    contenedor.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

    JLabel encabezado = new JLabel("Total de ítems: " + items.size(), SwingConstants.CENTER);
    encabezado.setFont(new Font("Arial", Font.BOLD, 18));
    encabezado.setForeground(new Color(33, 150, 243));
    contenedor.add(encabezado);
    contenedor.add(Box.createRigidArea(new Dimension(0, 15)));

    for (RentItem item : items) {
        JPanel tarjeta = crearTarjetaMejorada(item);
        contenedor.add(tarjeta);
        contenedor.add(Box.createRigidArea(new Dimension(0, 15)));
    }

    JScrollPane scrollPane = new JScrollPane(contenedor);
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    scrollPane.getVerticalScrollBar().setUnitIncrement(16);
    dialogo.add(scrollPane, BorderLayout.CENTER);

    JPanel panelBotonSalir = new JPanel();
    panelBotonSalir.setBackground(new Color(245, 245, 245));
    JButton btnSalir = BaseGUI.crearBoton("Salir", new Color(244, 67, 54));
    btnSalir.addActionListener(e -> dialogo.dispose());
    panelBotonSalir.add(btnSalir);
    dialogo.add(panelBotonSalir, BorderLayout.SOUTH);

    dialogo.setVisible(true);
    }

    private JPanel crearTarjetaMejorada(RentItem item) {
        JPanel tarjeta = new JPanel(new BorderLayout(15, 15));
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(178, 34, 34), 2), // borde rojo
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        tarjeta.setBackground(new Color(255, 240, 240)); // fondo suave rojo
        tarjeta.setMaximumSize(new Dimension(900, 220));

        if (item.getImagen() != null) {
            JLabel lblImagen = new JLabel();
            ImageIcon icon = item.getImagen();
            Image img = icon.getImage().getScaledInstance(180, 180, Image.SCALE_SMOOTH);
            lblImagen.setIcon(new ImageIcon(img));
            lblImagen.setBorder(BorderFactory.createLineBorder(new Color(139, 0, 0), 1));
            tarjeta.add(lblImagen, BorderLayout.WEST);
        }

        JPanel panelInfo = new JPanel();
        panelInfo.setLayout(new BoxLayout(panelInfo, BoxLayout.Y_AXIS));
        panelInfo.setBackground(new Color(255, 240, 240));

        JLabel lblNombre = new JLabel("" + item.getNombre());
        lblNombre.setFont(new Font("Arial", Font.BOLD, 20));
        lblNombre.setForeground(new Color(178, 34, 34));
        panelInfo.add(lblNombre);
        panelInfo.add(Box.createRigidArea(new Dimension(0, 10)));

        if (item instanceof Movie) {
            Movie movie = (Movie) item;
            JLabel lblEstado = new JLabel("Estado: " + movie.getEstado());
            lblEstado.setFont(new Font("Arial", Font.BOLD, 16));
            lblEstado.setForeground(new Color(220, 20, 60));
            panelInfo.add(lblEstado);
            panelInfo.add(Box.createRigidArea(new Dimension(0, 8)));
        }

        JLabel lblPrecio = new JLabel("Precio de Renta: Lps. " + 
            String.format("%.2f", item.getPrecioRenta()));
        lblPrecio.setFont(new Font("Arial", Font.PLAIN, 15));
        lblPrecio.setForeground(new Color(139, 0, 0));
        panelInfo.add(lblPrecio);
        panelInfo.add(Box.createRigidArea(new Dimension(0, 8)));

        JLabel lblCodigo = new JLabel("Codigo: " + item.getCodigo());
        lblCodigo.setFont(new Font("Arial", Font.PLAIN, 14));
        lblCodigo.setForeground(new Color(139, 0, 0));
        panelInfo.add(lblCodigo);
        panelInfo.add(Box.createRigidArea(new Dimension(0, 8)));

        String tipo = item instanceof Movie ? "Película" : "Videojuego";
        JLabel lblTipo = new JLabel("Tipo: " + tipo);
        lblTipo.setFont(new Font("Arial", Font.ITALIC, 14));
        lblTipo.setForeground(new Color(178, 34, 34));
        panelInfo.add(lblTipo);

        tarjeta.add(panelInfo, BorderLayout.CENTER);

        return tarjeta;
    }

    private void salir() {
        int opcion = JOptionPane.showConfirmDialog(this,
            "Esta seguro que desea salir del sistema?",
            "Confirmar Salida",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);
        
        if (opcion == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    private RentItem buscarItemPorCodigo(int codigo) {
        for (RentItem item : items) {
            if (item.getCodigo() == codigo) {
                return item;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            MenuGUI menu = new MenuGUI();
            menu.setVisible(true);
        });
    }
}
