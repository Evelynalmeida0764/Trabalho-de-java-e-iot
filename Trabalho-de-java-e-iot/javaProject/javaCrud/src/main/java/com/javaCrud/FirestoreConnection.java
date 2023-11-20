package com.javaCrud;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import java.io.FileInputStream;
import java.io.IOException;

public class FirestoreConnection {

    public static void main(String[] args) throws IOException {
        String pathToCredentials = "javaCrud\\arduino-8cd69-firebase-adminsdk-j19t5-cdbfb66f93.json";

        FileInputStream serviceAccount = new FileInputStream(pathToCredentials);

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setProjectId("arduino-8cd69") 
                .build();

        FirebaseApp.initializeApp(options);

        Firestore db = FirestoreClient.getFirestore();

        System.out.println("Conexão com o Firestore estabelecida com sucesso!");
        System.out.println(db);

        FirestoreOperations.readCollection(db, "plantacao");
    }
}

