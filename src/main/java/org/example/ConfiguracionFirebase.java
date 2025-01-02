package org.example;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import java.io.FileInputStream;
import java.io.IOException;

public class ConfiguracionFirebase {

    public static void inicializarFirebase() {
        try {
            FileInputStream serviceAccount = new FileInputStream("src/main/resources/serviceAccountKey.json");

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            FirebaseApp.initializeApp(options);
            System.out.println("Firebase inicializado correctamente.");
        } catch (IOException e) {
            System.err.println("Error inicializando Firebase: " + e.getMessage());
        }
    }
    // Método para acceder a la instancia de FirebaseApp (se puede mockear si es necesario)
}