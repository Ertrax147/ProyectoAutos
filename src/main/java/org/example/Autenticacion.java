package org.example;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;

import java.util.Map;
import java.util.concurrent.ExecutionException;
import javax.swing.*;

import static com.google.firebase.cloud.FirestoreClient.getFirestore;

public class Autenticacion {

    // Método de registro de usuario
    public static boolean registrarUsuario(String email, String contrasena, String nombre, String tipo) {
        Map<String, Object> datosExistentes = FirestoreCRUD.getAutoData(email);
        if (datosExistentes != null) {
            System.out.println("El usuario ya existe.");
            return false;
        }

        Map<String, Object> datosUsuario = Map.of(
                "nombre", nombre,
                "email", email,
                "contrasena", contrasena,
                "tipo", tipo
        );
        FirestoreCRUD.crearOActualizarDocumento("usuarios", email, datosUsuario);
        System.out.println("Usuario registrado exitosamente.");
        return true;
    }

    // Método de inicio de sesión
    // Método de inicio de sesión
    public static Map<String, Object> iniciarSesion(String email, String contrasena) {
        Firestore db = getFirestore();
        DocumentReference docRef = db.collection("usuarios").document(email);  //docRef hace referencia a la coleccion y lo necesario

        try {
            ApiFuture<DocumentSnapshot> future = docRef.get();
            DocumentSnapshot document = future.get();

            if (document.exists()) {
                String contrasenaGuardada = document.getString("contrasena");
                if (contrasenaGuardada != null && contrasenaGuardada.equals(contrasena)) {
                    System.out.println("Inicio de sesión exitoso.");
                    return document.getData();
                } else {
                    System.out.println("Contraseña incorrecta.");
                }
            } else {
                System.out.println("Usuario no encontrado.");
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
}