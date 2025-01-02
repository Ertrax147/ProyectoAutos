package org.example;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class FirestoreCRUD {

    private static Firestore getFirestore() {
        return FirestoreClient.getFirestore();
    }

    // Create or Update a Document
    public static void crearOActualizarDocumento(String nombreColeccion, String IdDocumento, Map<String, Object> data) {
        Firestore db = getFirestore();
        ApiFuture<WriteResult> future = db.collection(nombreColeccion).document(IdDocumento).set(data);
        try {
            System.out.println("Documento actualizado en: " + future.get().getUpdateTime());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    // Read a Document
    public static void leerDocumento(String nombreColeccion, String IdDocumento) {
        Firestore db = getFirestore();
        DocumentReference docRef = db.collection(nombreColeccion).document(IdDocumento);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        try {
            DocumentSnapshot document = future.get();
            if (document.exists()) {
                System.out.println("Documento: " + document.getData());
            } else {
                System.out.println("No se encontró el documento.");
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    // Delete a Document
    public static void eliminarDocumento(String nombreColeccion, String IdDocumento) {
        Firestore db = getFirestore();
        ApiFuture<WriteResult> future = db.collection(nombreColeccion).document(IdDocumento).delete();
        try {
            System.out.println("Documento eliminado en: " + future.get().getUpdateTime());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }


    public static Map<String, Object> getAutoData(String patente) {
        Firestore db = getFirestore();
        DocumentReference docRef = db.collection("autos").document(patente);
        ApiFuture<DocumentSnapshot> future = docRef.get();

        try {
            DocumentSnapshot document = future.get();
            if (document.exists()) {
                return document.getData(); // Retorna los datos del auto
            } else {
                System.out.println("No se encontró el auto.");
                return null;
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }
    // Método para obtener todos los autos de Firestore
    public static List<Map<String, Object>> obtenerAutos() {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection("autos").get();
        List<Map<String, Object>> autosData = new ArrayList<>();
        try {
            // Esperamos la respuesta de Firestore
            QuerySnapshot querySnapshot = future.get();
            // Iteramos sobre los documentos y los agregamos a la lista
            for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                Map<String, Object> autoData = document.getData();  // Aseguramos que estamos obteniendo el Map
                if (autoData != null) {
                    autosData.add(autoData);
                }
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return autosData;
    }

    // Método para crear o actualizar un auto en Firestore
    public static Map<String, Object> crearMapaAuto(Auto auto) {
        Map<String, Object> autoData = new HashMap<>();
        autoData.put("patente", auto.getPatente());
        autoData.put("marca", auto.getMarca());
        autoData.put("modelo", auto.getModelo());
        autoData.put("ano", auto.getAno());
        autoData.put("color", auto.getColor());
        autoData.put("precio", auto.getPrecio());
        return autoData;
    }
}
