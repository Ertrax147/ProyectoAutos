package org.example;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.FileInputStream;
import java.io.IOException;

public class ConectionDataBase {
    private DatabaseReference databaseReference;

    public ConectionDataBase() {
        inicializarFirebase();
    }

    private void inicializarFirebase() {
        try {
            FileInputStream serviceAccount =
                    new FileInputStream("src/main/resources/serviceAccountKey.json");

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://prueba-266c8-default-rtdb.firebaseio.com/")
                    .build();

            FirebaseApp.initializeApp(options);

            databaseReference = FirebaseDatabase.getInstance().getReference();
            System.out.println("Conexión a Firebase exitosa!");

        } catch (IOException e) {
            System.out.println("Error al inicializar Firebase: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public DatabaseReference getDatabaseReference() {
        return databaseReference;
    }
}
