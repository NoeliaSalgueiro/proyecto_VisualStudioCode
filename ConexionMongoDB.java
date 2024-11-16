/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.javaymongodb;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import java.util.List;
import org.bson.Document;
import org.bson.conversions.Bson;

/**
 *
 * @author Moncho
 */
public class ConexionMongoDB {

    // URI de conexión a MongoDB (ajusta según tus credenciales y dirección de MongoDB)
    String uri = "mongodb://localhost:27017";

    // Nombre de la base de datos a la que queremos conectarno
    String bbdd;

    public MongoDatabase mongoDatabase;
    public MongoClient mongoClient;

    ConexionMongoDB(String bbdd) {
        // Crear cliente MongoDB
        try {
        this.bbdd = bbdd;
            mongoClient = MongoClients.create(uri);
            // Conectar a la base de datos
            mongoDatabase = mongoClient.getDatabase(bbdd);
            System.out.println("Conexión exitosa a la base de datos: " + mongoDatabase.getName());
        } catch (Exception e) {
            System.out.println("Error en la conexión: " + e.getMessage());
        }

    }

    /**
     * Muestro las colecciones de la bbdd actual
     */
    public void mostrarColecciones() {
        System.out.println(">>Monstrando las colecciones de " + mongoDatabase.getName());
        for (String nombre : mongoDatabase.listCollectionNames()) {
            System.out.println(nombre);
        }
    }

    /**
     * Muesto las estadísticas de la bbdd actual
     */
    public void estadisticas() {
        System.out.println(">>Estadisticas de " + mongoDatabase.getName());
        Document estadisticas = mongoDatabase.runCommand(new Document("dbStats", 1));
        System.out.println(estadisticas.toJson());

    }

    /**
     * Añadir un documento
     * @param coleccion Colección en la que queremos buscar
     * @param doc Documento que queremos añadir
     */
    public void anadeDocumento(String coleccion, Document doc) {
        mongoDatabase.getCollection(coleccion).insertOne(doc);
    }

    /**
     * Añadir múltiples documentos
     * @param coleccion Colección en la que queremos buscar
     * @param docs Lista de documentos que queremos insertar
     */
    public void anadeDocumento(String coleccion, List<Document> docs) {
        mongoDatabase.getCollection(coleccion).insertMany(docs);
    }

    /**
     * Buscar un conjunto de documentos indicando la proyeccción
     * @param coleccion Colección en la que queremos buscar
     * @param doc Documento con los parámetros de la búsqueda
     */
    public void buscarDocumento(String coleccion, Document doc) {
        FindIterable<Document> documentos = mongoDatabase.getCollection(coleccion).find(doc);
        System.out.println("Elementos encontrados con las codiciones: " + doc.toJson());
        for (Document documento : documentos) {
            System.out.println(documento.toJson());
        }
    }

    /**
     * Buscar un conjunto de documentos indicando la proyeccción
     * @param coleccion Colección en la que queremos buscar
     * @param doc Documento con los parámetros de la búsqueda
     * @param proyeccion Documento con los parámetros de la proyección
     */
    public void buscarDocumento(String coleccion, Document doc, Document proyeccion) {
        FindIterable<Document> documentos = mongoDatabase.getCollection(coleccion).find(doc)
                .projection(proyeccion);
        System.out.println("Elementos encontrados con las codiciones: " + doc.toJson() + " y la proyeccion " + proyeccion.toJson());
        for (Document documento : documentos) {
            System.out.println(documento.toJson());
        }
    }
    
    /**
     * 
     * @param coleccion Colección en la que queremos buscar
     * @param doc Documento con los parámetros de la búsqueda
     * @param proyeccion Proyección con los parámetros de la proyección
     */
    public void buscarDocumento(String coleccion, Document doc, Bson proyeccion) {
        FindIterable<Document> documentos = mongoDatabase.getCollection(coleccion).find(doc)
                .projection(proyeccion);
        System.out.println("Elementos encontrados con las codiciones: " + doc.toJson() + " y la proyeccion " + proyeccion.toString());
        for (Document documento : documentos) {
            System.out.println(documento.toJson());
        }
    }
    
    /**
     * Eliminamos el primer documento que encontremos
     * @param coleccion Colección en la que queremos buscar
     * @param doc Documento con los parámetros de la búsqueda
     */
    public void eliminarDocumento(String coleccion, Document doc){
        System.out.println("Eliminar documento con las condiciones: " + doc.toJson());
        DeleteResult respuesta= mongoDatabase.getCollection(coleccion).deleteOne(doc);
        System.out.println(respuesta);
    }
    
    /**
     * Eliminamos el primer documento que coincidan
     * @param coleccion Colección en la que queremos buscar
     * @param doc Documento con los parámetros de la búsqueda
     */
    public void eliminarDocumentos(String coleccion, Document doc){
        System.out.println("Eliminar documento con las condiciones: " + doc.toJson());
        DeleteResult respuesta= mongoDatabase.getCollection(coleccion).deleteMany(doc);
        System.out.println(respuesta);
    }
    
    /**
     * Actualizamos un campo en un documento
     * @param coleccion Colección en la que queremos buscar
     * @param filtro Bson con los parámetros de la búsqueda
     * @param actualizacion Bson con los datos a actualizar de la búsqueda
     */
    public void actualizaDocumento(String coleccion, Bson filtro, Bson actualizacion){
        UpdateResult respuesta = mongoDatabase.getCollection(coleccion).updateOne(filtro, actualizacion);
        System.out.println(respuesta);
    }
    
    /**
     * Actualizamos un campo en múltiples documentos
     * @param coleccion Colección en la que queremos buscar
     * @param filtro Bson con los parámetros de la búsqueda
     * @param actualizacion Bson con los datos a actualizar de la búsqueda
     */
    public void actualizaDocumentos(String coleccion, Bson filtro, Bson actualizacion){
        UpdateResult respuesta = mongoDatabase.getCollection(coleccion).updateMany(filtro, actualizacion);
        System.out.println(respuesta);
    }
}
