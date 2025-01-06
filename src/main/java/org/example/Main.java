package org.example;

import com.google.firebase.cloud.FirestoreClient;
import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Inicializamos Firebase
        ConfiguracionFirebase.inicializarFirebase();

        JFrame loginFrame = new JFrame("Inicio de Sesión");
        loginFrame.setSize(400, 300);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setLayout(new GridLayout(0, 1));

        JTextField emailField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JButton loginButton = new JButton("Iniciar Sesión");
        JButton registerButton = new JButton("Registrarse");

        loginFrame.add(new JLabel("Email:"));
        loginFrame.add(emailField);
        loginFrame.add(new JLabel("Contraseña:"));
        loginFrame.add(passwordField);
        loginFrame.add(loginButton);
        loginFrame.add(registerButton);

        loginFrame.setVisible(true);

        // Acción para iniciar sesión
        loginButton.addActionListener(e -> {
            String email = emailField.getText();
            String contrasena = new String(passwordField.getPassword());

            Map<String, Object> usuario = Autenticacion.iniciarSesion(email, contrasena);
            if (usuario != null) {
                loginFrame.dispose();
                String tipo = (String) usuario.get("tipo");
                if ("admin".equals(tipo)) {
                    abrirVentanaAdministrador();
                } else {
                    abrirVentanaCliente();
                }
            } else {
                JOptionPane.showMessageDialog(loginFrame, "Inicio de sesión fallido.");
            }
        });

        // Acción para registrarse
        registerButton.addActionListener(e -> {
            mostrarVentanaRegistro();
        });
    }

    private static void mostrarVentanaRegistro() {
        JFrame registroFrame = new JFrame("Registro de Usuario");
        registroFrame.setSize(400, 300);
        registroFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        registroFrame.setLayout(new GridLayout(0, 1));

        JTextField emailField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JTextField nombreField = new JTextField();
        JComboBox<String> tipoComboBox = new JComboBox<>(new String[]{"Admin", "Cliente"});
        JButton registerButton = new JButton("Registrar");

        registroFrame.add(new JLabel("Nombre:"));
        registroFrame.add(nombreField);
        registroFrame.add(new JLabel("Email:"));
        registroFrame.add(emailField);
        registroFrame.add(new JLabel("Contraseña:"));
        registroFrame.add(passwordField);
        registroFrame.add(new JLabel("Tipo de Usuario:"));
        registroFrame.add(tipoComboBox);
        registroFrame.add(registerButton);

        registroFrame.setVisible(true);

        registerButton.addActionListener(e -> {
            String nombre = nombreField.getText();
            String email = emailField.getText();
            String contrasena = new String(passwordField.getPassword());
            String tipo = (String) tipoComboBox.getSelectedItem();

            boolean registrado = Autenticacion.registrarUsuario(email, contrasena, nombre, tipo.equals("Admin") ? "admin" : "cliente");
            if (registrado) {
                JOptionPane.showMessageDialog(registroFrame, "Usuario registrado exitosamente.");
                registroFrame.dispose();
            } else {
                JOptionPane.showMessageDialog(registroFrame, "Error al registrar el usuario.");
            }
        });
    }

    private static void abrirVentanaAdministrador() {
        JFrame adminFrame = new JFrame("Ventana Administrador");
        adminFrame.setSize(600, 400);
        adminFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        adminFrame.setLayout(new GridLayout(5, 1));

        JButton verAutosButton = new JButton("Ver Autos");
        JButton crearAutoButton = new JButton("Crear Auto");
        JButton eliminarAutoButton = new JButton("Eliminar Auto");
        JButton modificarAutoButton = new JButton("Modificar Auto");

        adminFrame.add(verAutosButton);
        adminFrame.add(crearAutoButton);
        adminFrame.add(eliminarAutoButton);
        adminFrame.add(modificarAutoButton);

        // Acción para ver los autos
        verAutosButton.addActionListener(e -> mostrarAutos());

        // Acción para crear un auto
        crearAutoButton.addActionListener(e -> crearAuto());

        // Acción para eliminar un auto
        eliminarAutoButton.addActionListener(e -> eliminarAuto());

        modificarAutoButton.addActionListener(e -> modificarAuto());
        adminFrame.setVisible(true);
    }

    private static void modificarAuto() {
        String patente = JOptionPane.showInputDialog("Ingrese la patente del auto a modificar:");
        if (patente != null && !patente.isEmpty()) {
            Map<String, Object> autoData = FirestoreCRUD.getAutoData(patente);
            if (autoData != null) {
                JTextField marcaField = new JTextField((String) autoData.get("marca"));
                JTextField modeloField = new JTextField((String) autoData.get("modelo"));
                JTextField anoField = new JTextField(autoData.get("ano").toString());
                JTextField colorField = new JTextField((String) autoData.get("color"));
                JTextField precioField = new JTextField(autoData.get("precio").toString());

                Object[] message = {
                        "Marca:", marcaField,
                        "Modelo:", modeloField,
                        "Año:", anoField,
                        "Color:", colorField,
                        "Precio:", precioField
                };

                int option = JOptionPane.showConfirmDialog(null, message, "Modificar Auto", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    String marca = marcaField.getText();
                    String modelo = modeloField.getText();
                    int ano = Integer.parseInt(anoField.getText());
                    String color = colorField.getText();
                    double precio = Double.parseDouble(precioField.getText());

                    Auto autoModificado = new Auto(patente, marca, modelo, ano, color, precio);
                    Map<String, Object> autoModificadoData = FirestoreCRUD.crearMapaAuto(autoModificado);

                    FirestoreCRUD.crearOActualizarDocumento("autos", patente, autoModificadoData);
                    JOptionPane.showMessageDialog(null, "Auto modificado exitosamente.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Auto no encontrado.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Patente inválida.");
        }
    }

    private static void mostrarAutos() {
        List<Map<String, Object>> autos = FirestoreCRUD.obtenerAutos();
        String[] columnNames = {"Patente", "Marca", "Modelo", "Año", "Color", "Precio"};
        Object[][] data = new Object[autos.size()][6];

        for (int i = 0; i < autos.size(); i++) {
            Map<String, Object> auto = autos.get(i);
            data[i][0] = auto.get("patente");
            data[i][1] = auto.get("marca");
            data[i][2] = auto.get("modelo");
            data[i][3] = auto.get("ano");
            data[i][4] = auto.get("color");
            data[i][5] = auto.get("precio");
        }

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        JFrame frame = new JFrame("Autos en la base de datos");
        frame.setSize(800, 400);
        frame.add(scrollPane);
        frame.setVisible(true);
    }

    private static void crearAuto() {
        JTextField patenteField = new JTextField();
        JTextField marcaField = new JTextField();
        JTextField modeloField = new JTextField();
        JTextField anoField = new JTextField();
        JTextField colorField = new JTextField();
        JTextField precioField = new JTextField();

        Object[] message = {
                "Patente:", patenteField,
                "Marca:", marcaField,
                "Modelo:", modeloField,
                "Año:", anoField,
                "Color:", colorField,
                "Precio:", precioField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Nuevo Auto", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String patente = patenteField.getText();
            String marca = marcaField.getText();
            String modelo = modeloField.getText();
            int ano = Integer.parseInt(anoField.getText());
            String color = colorField.getText();
            double precio = Double.parseDouble(precioField.getText());

            Auto nuevoAuto = new Auto(patente, marca, modelo, ano, color, precio);
            Map<String, Object> autoData = FirestoreCRUD.crearMapaAuto(nuevoAuto);

            FirestoreCRUD.crearOActualizarDocumento("autos", patente, autoData);
            JOptionPane.showMessageDialog(null, "Auto creado exitosamente.");
        }
    }

    private static void eliminarAuto() {
        String patente = JOptionPane.showInputDialog("Ingrese la patente del auto a eliminar:");
        if (patente != null && !patente.isEmpty()) {
            FirestoreCRUD.eliminarDocumento("autos", patente);
            JOptionPane.showMessageDialog(null, "Auto eliminado exitosamente.");
        } else {
            JOptionPane.showMessageDialog(null, "Patente inválida.");
        }
    }

    private static void abrirVentanaCliente() {
        JFrame clienteFrame = new JFrame("Ventana Cliente");
        clienteFrame.setSize(600, 400);
        clienteFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        clienteFrame.setLayout(new GridLayout(6, 1));

        JButton verAutosButton = new JButton("Ver Autos");
        JButton verFavoritosButton = new JButton("Ver Favoritos");
        JButton agregarFavoritosButton = new JButton("Agregar a Favoritos");
        JButton eliminarFavoritosButton = new JButton("Eliminar de Favoritos");
        JButton comprarAutoButton = new JButton("Comprar Auto");

        clienteFrame.add(verAutosButton);
        clienteFrame.add(verFavoritosButton);
        clienteFrame.add(agregarFavoritosButton);
        clienteFrame.add(eliminarFavoritosButton);
        clienteFrame.add(comprarAutoButton);

        // Acción para ver los autos
        verAutosButton.addActionListener(e -> mostrarAutos());

        // Acción para ver los favoritos
        verFavoritosButton.addActionListener(e -> mostrarFavoritos());

        // Acción para agregar a favoritos
        agregarFavoritosButton.addActionListener(e -> agregarAFavoritos());

        // Acción para eliminar de favoritos
        eliminarFavoritosButton.addActionListener(e -> eliminarDeFavoritos());

        // Acción para comprar un auto
        comprarAutoButton.addActionListener(e -> comprarAuto());


        clienteFrame.setVisible(true);
    }

    private static void mostrarFavoritos() {
        List<Map<String, Object>> favoritos = FirestoreCRUD.obtenerFavoritos();
        String[] columnNames = {"Patente", "Marca", "Modelo", "Año", "Color", "Precio"};
        Object[][] data = new Object[favoritos.size()][6];

        for (int i = 0; i < favoritos.size(); i++) {
            Map<String, Object> favorito = favoritos.get(i);
            String patente = (String) favorito.get("patente");
            Map<String, Object> autoData = FirestoreCRUD.getAutoData(patente);

            if (autoData != null) {
                data[i][0] = autoData.getOrDefault("patente", "N/A");
                data[i][1] = autoData.getOrDefault("marca", "N/A");
                data[i][2] = autoData.getOrDefault("modelo", "N/A");
                data[i][3] = autoData.getOrDefault("ano", "N/A");
                data[i][4] = autoData.getOrDefault("color", "N/A");
                data[i][5] = autoData.getOrDefault("precio", "N/A");
            }
        }

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        JFrame frame = new JFrame("Favoritos");
        frame.setSize(800, 400);
        frame.add(scrollPane);
        frame.setVisible(true);
    }

    private static void agregarAFavoritos() {
        String patente = JOptionPane.showInputDialog("Ingrese la patente del auto a agregar a favoritos:");
        if (patente != null && !patente.isEmpty()) {
            FirestoreCRUD.agregarAFavoritos(patente);
            JOptionPane.showMessageDialog(null, "Auto agregado a favoritos exitosamente.");
        } else {
            JOptionPane.showMessageDialog(null, "Patente inválida.");
        }
    }

    private static void eliminarDeFavoritos() {
        String patente = JOptionPane.showInputDialog("Ingrese la patente del auto a eliminar de favoritos:");
        if (patente != null && !patente.isEmpty()) {
            FirestoreCRUD.eliminarDeFavoritos(patente);
            JOptionPane.showMessageDialog(null, "Auto eliminado de favoritos exitosamente.");
        } else {
            JOptionPane.showMessageDialog(null, "Patente inválida.");
        }
    }

    private static void comprarAuto() {
        String patente = JOptionPane.showInputDialog("Ingrese la patente del auto a comprar:");
        if (patente != null && !patente.isEmpty()) {
            Map<String, Object> autoData = FirestoreCRUD.getAutoData(patente);
            if (autoData != null) {
                Auto auto = new Auto(
                        (String) autoData.get("patente"),
                        (String) autoData.get("marca"),
                        (String) autoData.get("modelo"),
                        ((Long) autoData.get("ano")).intValue(),
                        (String) autoData.get("color"),
                        ((Number) autoData.get("precio")).doubleValue()
                );
                Cliente cliente = new Cliente("NombreCliente", "email@cliente.com", "contrasena");
                cliente.comprarAuto(auto);
            } else {
                JOptionPane.showMessageDialog(null, "Auto no encontrado.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Patente inválida.");
        }
    }

    private static void verHistorialDeCompras() {
        Cliente cliente = new Cliente("NombreCliente", "email@cliente.com", "contrasena");
        List<Venta> historialCompras = cliente.verHistorialDeCompras();
        String[] columnNames = {"Patente", "Marca", "Modelo", "Fecha", "Monto"};
        Object[][] data = new Object[historialCompras.size()][5];

        for (int i = 0; i < historialCompras.size(); i++) {
            Venta venta = historialCompras.get(i);
            Auto auto = venta.getAuto();
            data[i][0] = auto.getPatente();
            data[i][1] = auto.getMarca();
            data[i][2] = auto.getModelo();
            data[i][3] = venta.getFecha();
            data[i][4] = venta.getMonto();
        }

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        JFrame frame = new JFrame("Historial de Compras");
        frame.setSize(800, 400);
        frame.add(scrollPane);
        frame.setVisible(true);
    }
}