package com.javaCrud;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import java.io.FileInputStream;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import java.io.IOException;

public class App {
    public static void main(String[] args) {
        Firestore db = initializeFirestoreConnection();

        if (db != null) {
            Interface.startInterface(db);
        } else {
            System.out.println("Erro ao conectar ao Firestore. Encerrando o programa.");
        }
    }

    private static Firestore initializeFirestoreConnection() {
        String pathToCredentials = "javaProject\\javaCrud\\arduino-8cd69-firebase-adminsdk-j19t5-cdbfb66f93.json";

        try {
            FileInputStream serviceAccount = new FileInputStream(pathToCredentials);            

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setProjectId("arduino-8cd69") 
                    .build();

            FirebaseApp.initializeApp(options);

            return FirestoreClient.getFirestore();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}