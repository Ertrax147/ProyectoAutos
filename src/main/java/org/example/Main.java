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

        adminFrame.add(verAutosButton);
        adminFrame.add(crearAutoButton);
        adminFrame.add(eliminarAutoButton);

        // Acción para ver los autos
        verAutosButton.addActionListener(e -> mostrarAutos());

        // Acción para crear un auto
        crearAutoButton.addActionListener(e -> crearAuto());

        // Acción para eliminar un auto
        eliminarAutoButton.addActionListener(e -> eliminarAuto());

        adminFrame.setVisible(true);
    }

    private static void mostrarAutos() {
        List<Map<String, Object>> autos = FirestoreCRUD.obtenerAutos();
        StringBuilder autosList = new StringBuilder();
        for (Map<String, Object> auto : autos) {
            autosList.append(auto).append("\n");
        }
        JOptionPane.showMessageDialog(null, "Autos en la base de datos:\n" + autosList.toString());
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
        // Obtener los autos de la base de datos
        List<Map<String, Object>> autos = FirestoreCRUD.obtenerAutos();

        // Crear la ventana para mostrar los autos
        JFrame clienteFrame = new JFrame("Autos Disponibles");
        clienteFrame.setSize(500, 400);
        clienteFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        clienteFrame.setLayout(new BorderLayout());

        // Crear un modelo de lista
        DefaultListModel<String> listModel = new DefaultListModel<>();

        // Agregar los autos a la lista
        for (Map<String, Object> auto : autos) {
            String marca = (String) auto.get("marca");
            String modelo = (String) auto.get("modelo");
            String precio = String.valueOf(auto.get("precio"));
            listModel.addElement(marca + " " + modelo + " - $" + precio);
        }

        // Crear el JList para mostrar los autos
        JList<String> autoList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(autoList);
        clienteFrame.add(scrollPane, BorderLayout.CENTER);

        // Hacer visible la ventana
        clienteFrame.setVisible(true);
    }
}

