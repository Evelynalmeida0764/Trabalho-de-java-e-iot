package com.javaCrud;

import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class FirestoreOperations {

    public static void readCollection(Firestore db, String collectionName) {
        // Referenciar a coleção
        com.google.cloud.firestore.CollectionReference collectionReference = db.collection(collectionName);

        try {
            // Obter todos os documentos na coleção
            QuerySnapshot querySnapshot = collectionReference.get().get();

            // Iterar sobre os documentos e imprimir seus dados
            List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
            for (QueryDocumentSnapshot document : documents) {
                System.out.println("ID do Documento: " + document.getId());
                System.out.println("Dados do Documento: " + document.getData());
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

     public static List<SensorData> readAndSortCollection(Firestore db, String collectionName) {
        // Seu código existente para ler e ordenar a coleção

        List<SensorData> sensorDataList = new ArrayList<>();

        try {
            // Obter todos os documentos na coleção
            QuerySnapshot querySnapshot = db.collection(collectionName).get().get();

            // Iterar sobre os documentos e criar objetos SensorData
            for (QueryDocumentSnapshot document : querySnapshot.getDocuments()) {
                String id = document.getId();
                String datetimeField = document.getString("datetime");

                // Extrair o valor de datetime
                String datetime = datetimeField.substring(datetimeField.indexOf('=') + 1).trim();

                int humidadeAr = document.getLong("humidade_ar").intValue();
                int humidadeSolo = document.getLong("humidade_solo").intValue();
                int temperatura = document.getLong("temperatura").intValue();

                SensorData sensorData = new SensorData(id, datetime, humidadeAr, humidadeSolo, temperatura);
                sensorDataList.add(sensorData);
            }

            // Ordenar a lista por data usando um Comparator
            Collections.sort(sensorDataList, (sd1, sd2) -> sd1.getDatetime().compareTo(sd2.getDatetime()));

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return sensorDataList;
    }

    public static List<SensorData> readCollectionData(Firestore db, String collectionName) {
        List<SensorData> sensorDataList = new ArrayList<>();

        try {
            QuerySnapshot querySnapshot = db.collection(collectionName).get().get();

            for (QueryDocumentSnapshot document : querySnapshot.getDocuments()) {
                String id = document.getId();
                String datetimeField = document.getString("datetime");

                // Extrair o valor de datetime
                String datetime = datetimeField.substring(datetimeField.indexOf('=') + 1).trim();

                Long humidadeArLong = document.getLong("umidade_ar");
                Long humidadeSoloLong = document.getLong("umidade_solo");
                Long tempLong = document.getLong("temperatura");

                int humidadeAr = (humidadeArLong != null) ? humidadeArLong.intValue() : 0;
                int humidadeSolo = (humidadeSoloLong != null) ? humidadeSoloLong.intValue() : 0;
                int temperatura = (tempLong != null) ? tempLong.intValue() : 0;

                SensorData sensorData = new SensorData(id, datetime, humidadeAr, humidadeSolo, temperatura);
                sensorDataList.add(sensorData);
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return sensorDataList;
    }

    // Adicione métodos adicionais para outras operações CRUD conforme necessário
}
