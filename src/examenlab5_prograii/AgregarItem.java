/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package examenlab5_prograii;

/**
 *
 * @author jerem
 */

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.io.File;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.util.Calendar;

public class AgregarItem extends JFrame {
    
    private ArrayList<RentItem> items;
    private JTextField txtCodigo, txtNombre, txtPrecio, txtCopias;
    private JLabel lblImagen;
    private String rutaImagen = "";
    private JComboBox<String> comboTipo;

    public AgregarItem(ArrayList<RentItem> items) {
        this.items = items;
        configurarVentana();
        crearComponentes();
    }

    private void configurarVentana() {
        setTitle("Agregar Nuevo Ítem");
        setSize(700, 650);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(15, 15));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(new Color(245, 245, 245));
    }

    private void crearComponentes() {
        // Panel principal con padding
        JPanel panelPrincipal = new JPanel(new GridBagLayout());
        panelPrincipal.setBackground(Color.WHITE);
        panelPrincipal.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(20, 20, 20, 20),
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Título
        JLabel titulo = new JLabel("Agregar Nuevo Ítem", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 26));
        titulo.setForeground(new Color(33, 150, 243));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panelPrincipal.add(titulo, gbc);
        gbc.gridwidth = 1;

        // Tipo de ítem
        gbc.gridy = 1; gbc.gridx = 0;
        JLabel lblTipo = crearEtiqueta("Tipo de Ítem:");
        panelPrincipal.add(lblTipo, gbc);

        gbc.gridx = 1;
        comboTipo = new JComboBox<>(new String[]{"Movie", "Game"});
        comboTipo.setFont(new Font("Arial", Font.PLAIN, 14));
        comboTipo.setPreferredSize(new Dimension(250, 35));
        panelPrincipal.add(comboTipo, gbc);

        // Código
        gbc.gridy = 2; gbc.gridx = 0;
        panelPrincipal.add(crearEtiqueta("Código:"), gbc);

        gbc.gridx = 1;
        txtCodigo = crearCampoTexto();
        panelPrincipal.add(txtCodigo, gbc);

        // Nombre
        gbc.gridy = 3; gbc.gridx = 0;
        panelPrincipal.add(crearEtiqueta("Nombre:"), gbc);

        gbc.gridx = 1;
        txtNombre = crearCampoTexto();
        panelPrincipal.add(txtNombre, gbc);

        // Precio
        gbc.gridy = 4; gbc.gridx = 0;
        panelPrincipal.add(crearEtiqueta("Precio Base (Lps):"), gbc);

        gbc.gridx = 1;
        txtPrecio = crearCampoTexto();
        panelPrincipal.add(txtPrecio, gbc);

        // Copias
        gbc.gridy = 5; gbc.gridx = 0;
        panelPrincipal.add(crearEtiqueta("Cantidad de Copias:"), gbc);

        gbc.gridx = 1;
        txtCopias = crearCampoTexto();
        panelPrincipal.add(txtCopias, gbc);

        // Botón seleccionar imagen
        gbc.gridy = 6; gbc.gridx = 0; gbc.gridwidth = 2;
        JButton btnSeleccionar = new JButton("Seleccionar Imagen");
        btnSeleccionar.setFont(new Font("Arial", Font.BOLD, 14));
        btnSeleccionar.setBackground(new Color(255, 152, 0));
        btnSeleccionar.setForeground(Color.WHITE);
        btnSeleccionar.setFocusPainted(false);
        btnSeleccionar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSeleccionar.setPreferredSize(new Dimension(250, 40));
        btnSeleccionar.addActionListener(e -> seleccionarImagen());
        panelPrincipal.add(btnSeleccionar, gbc);

        // Vista previa de imagen
        gbc.gridy = 7; gbc.gridx = 0; gbc.gridwidth = 2;
        lblImagen = new JLabel("Sin imagen seleccionada", SwingConstants.CENTER);
        lblImagen.setPreferredSize(new Dimension(250, 250));
        lblImagen.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 2),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        lblImagen.setBackground(new Color(250, 250, 250));
        lblImagen.setOpaque(true);
        panelPrincipal.add(lblImagen, gbc);

        add(panelPrincipal, BorderLayout.CENTER);

        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        panelBotones.setBackground(new Color(245, 245, 245));

        JButton btnGuardar = crearBotonAccion("💾 Guardar", new Color(76, 175, 80));
        btnGuardar.addActionListener(e -> guardarItem());
        panelBotones.add(btnGuardar);

        JButton btnCancelar = crearBotonAccion("Cancelar", new Color(244, 67, 54));
        btnCancelar.addActionListener(e -> dispose());
        panelBotones.add(btnCancelar);

        add(panelBotones, BorderLayout.SOUTH);
    }

    private JLabel crearEtiqueta(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setForeground(new Color(60, 60, 60));
        return label;
    }

    private JTextField crearCampoTexto() {
        JTextField campo = new JTextField(20);
        campo.setFont(new Font("Arial", Font.PLAIN, 14));
        campo.setPreferredSize(new Dimension(250, 35));
        campo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        return campo;
    }

    private JButton crearBotonAccion(String texto, Color color) {
        JButton boton = new JButton(texto);
        boton.setPreferredSize(new Dimension(160, 45));
        boton.setFont(new Font("Arial", Font.BOLD, 15));
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return boton;
    }

    private void seleccionarImagen() {
        JFileChooser fileChooser = new JFileChooser();
        
        File directorioImagenes = new File("Imagenes Movie");
        if (directorioImagenes.exists()) {
            fileChooser.setCurrentDirectory(directorioImagenes);
        }
        
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "Imágenes (*.jpg, *.jpeg, *.png, *.gif)", "jpg", "jpeg", "png", "gif");
        fileChooser.setFileFilter(filter);

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            rutaImagen = selectedFile.getAbsolutePath();

            ImageIcon icon = new ImageIcon(rutaImagen);
            Image img = icon.getImage().getScaledInstance(230, 230, Image.SCALE_SMOOTH);
            lblImagen.setIcon(new ImageIcon(img));
            lblImagen.setText("");
        }
    }

    private void guardarItem() {
        try {
            // Validaciones
            if (txtCodigo.getText().trim().isEmpty() ||
                txtNombre.getText().trim().isEmpty() ||
                txtPrecio.getText().trim().isEmpty() ||
                txtCopias.getText().trim().isEmpty()) {
                BaseGUI.mostrarError(this, "Todos los campos son obligatorios.");
                return;
            }

            int codigo = Integer.parseInt(txtCodigo.getText().trim());
            String nombre = txtNombre.getText().trim();
            double precio = Double.parseDouble(txtPrecio.getText().trim());
            int copias = Integer.parseInt(txtCopias.getText().trim());

            // Validar código único
            for (RentItem item : items) {
                if (item.getCodigo() == codigo) {
                    BaseGUI.mostrarError(this, "El código ya existe. Use otro código.");
                    return;
                }
            }

            if (rutaImagen.isEmpty()) {
                BaseGUI.mostrarError(this, "Debe seleccionar una imagen para el ítem.");
                return;
            }

            if (precio <= 0) {
                BaseGUI.mostrarError(this, "El precio debe ser mayor a 0.");
                return;
            }

            if (copias < 0) {
                BaseGUI.mostrarError(this, "La cantidad de copias no puede ser negativa.");
                return;
            }

            // Crear el ítem según el tipo
            String tipo = (String) comboTipo.getSelectedItem();
            if (tipo.equals("Movie")) {
                Movie movie = new Movie(codigo, nombre, precio, copias, rutaImagen);
                
                // Preguntar por fecha de estreno
                int opcion = JOptionPane.showConfirmDialog(this,
                    "¿Desea establecer una fecha de estreno personalizada?",
                    "Fecha de Estreno",
                    JOptionPane.YES_NO_OPTION);
                
                if (opcion == JOptionPane.YES_OPTION) {
                    establecerFechaEstreno(movie);
                }
                
                items.add(movie);
                BaseGUI.mostrarExito(this, "¡Película agregada exitosamente!\n\n" +
                    "Código: " + codigo + "\n" +
                    "Nombre: " + nombre + "\n" +
                    "Estado: " + movie.getEstado());
                
            } else {
                Game game = new Game(codigo, nombre, precio, copias, rutaImagen);
                items.add(game);
                BaseGUI.mostrarExito(this, "¡Videojuego agregado exitosamente!\n\n" +
                    "Código: " + codigo + "\n" +
                    "Nombre: " + nombre);
            }

            dispose();

        } catch (NumberFormatException ex) {
            BaseGUI.mostrarError(this, "Por favor ingrese valores numéricos válidos.");
        } catch (Exception ex) {
            BaseGUI.mostrarError(this, "Error al guardar: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void establecerFechaEstreno(Movie movie) {
        try {
            Integer anio = BaseGUI.solicitarEntero(this, "Año de estreno:");
            Integer mes = BaseGUI.solicitarEntero(this, "Mes (1-12):");
            Integer dia = BaseGUI.solicitarEntero(this, "Día (1-31):");
            
            if (anio != null && mes != null && dia != null) {
                if (mes >= 1 && mes <= 12 && dia >= 1 && dia <= 31) {
                    Calendar fecha = Calendar.getInstance();
                    fecha.set(Calendar.YEAR, anio);
                    fecha.set(Calendar.MONTH, mes - 1);
                    fecha.set(Calendar.DAY_OF_MONTH, dia);
                    movie.setFechaEstreno(fecha);
                } else {
                    BaseGUI.mostrarAdvertencia(this, "Fecha inválida. Se usará la fecha actual.");
                }
            }
        } catch (Exception e) {
            BaseGUI.mostrarAdvertencia(this, "Error al establecer fecha. Se usará la fecha actual.");
        }
    }
}