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
        // Caminho para o arquivo de credenciais JSON
        String pathToCredentials = "javaCrud\\arduino-8cd69-firebase-adminsdk-j19t5-cdbfb66f93.json";
        // String pathToCredentials = "javaCrud\\javacrud-88968-firebase-adminsdk-3ueru-cb8252015c.json";

        FileInputStream serviceAccount = new FileInputStream(pathToCredentials);

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setProjectId("arduino-8cd69")  // Substitua pelo ID do seu projeto Firebase
                .build();

        FirebaseApp.initializeApp(options);

        // Obter a instância do Firestore
        Firestore db = FirestoreClient.getFirestore();

        // Agora você pode usar 'db' para interagir com o Firestore
        System.out.println("Conexão com o Firestore estabelecida com sucesso!");

        // Exemplo de criação de documento usando a classe FirestoreOperations
        // FirestoreOperations.createDocument(db, "sua-colecao", "id-do-novo-documento");
        FirestoreOperations.readCollection(db, "plantacao");
        FirestoreOperations.readAndSortCollection(db, "plantacao");
    }
}

