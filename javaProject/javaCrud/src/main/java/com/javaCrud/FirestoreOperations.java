package com.javaCrud;

import com.google.cloud.firestore.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.table.DefaultTableModel;

public class FirestoreOperations {

    public static void readCollection(Firestore db, String collectionName) {

        CollectionReference collectionReference = db.collection(collectionName);

        try {
            QuerySnapshot querySnapshot = collectionReference.orderBy("datetime", Query.Direction.DESCENDING).get().get();

            if (querySnapshot.isEmpty()) {
                System.out.println("A coleção está vazia.");
                return;
            }

            List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
            for (QueryDocumentSnapshot document : documents) {
                System.out.println("ID do Documento: " + document.getId());
                System.out.println("Dados do Documento: " + document.getData());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    public static void readCollection(Firestore db, String collectionName, DefaultTableModel model) {
        // Referenciar a coleção
        CollectionReference collectionReference = db.collection(collectionName);
    
        try {
            // Obter todos os documentos na coleção ordenados por data em ordem decrescente
            QuerySnapshot querySnapshot = collectionReference.orderBy("datetime", Query.Direction.DESCENDING).get().get();
    
            // Limpar o modelo existente antes de adicionar novos dados
            model.setRowCount(0);
    
            // Verificar se há documentos
            if (querySnapshot.isEmpty()) {
                System.out.println("A coleção está vazia.");
                return;
            }
    
            // Iterar sobre os documentos e adicionar ao modelo
            List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
            for (QueryDocumentSnapshot document : documents) {
                String data = document.getString("datetime");
    
                // Convertendo para os tipos desejados
                Long umidadeArLong = document.getLong("umidade_ar");
                int umidadeAr = (umidadeArLong != null) ? umidadeArLong.intValue() : 0;
    
                Long umidadeSoloLong = document.getLong("umidade_solo");
                int umidadeSolo = (umidadeSoloLong != null) ? umidadeSoloLong.intValue() : 0;
    
                Long temperaturaLong = document.getLong("temperatura");
                int temperatura = (temperaturaLong != null) ? temperaturaLong.intValue() : 0;
    
                model.addRow(new Object[]{data, umidadeAr, umidadeSolo, temperatura});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Map<String, Object> readFirstRow(Firestore db, String collectionName, DefaultTableModel model) {

        CollectionReference collectionReference = db.collection(collectionName);

        try {
            QuerySnapshot querySnapshot = collectionReference.orderBy("datetime", Query.Direction.DESCENDING).get().get();

            if (querySnapshot.isEmpty()) {
                System.out.println("A coleção está vazia.");
                return Collections.emptyMap();
            }

            QueryDocumentSnapshot document = querySnapshot.getDocuments().get(0);

            Map<String, Object> firstRow = new HashMap<>();
            firstRow.put("datetime", document.getString("datetime"));
            firstRow.put("umidade_ar", document.getLong("umidade_ar"));
            firstRow.put("umidade_solo", document.getLong("umidade_solo"));
            firstRow.put("temperatura", document.getLong("temperatura"));

            return firstRow;
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyMap();
        }
    }

}