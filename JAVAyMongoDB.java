/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.javaymongodb;

import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Updates;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import org.bson.conversions.Bson;

/**
 *
 * @author Moncho
 */
public class JAVAyMongoDB {

    public static void main(String[] args) {

        // Establecemos la conexión a la bbdd
        
            ConexionMongoDB conexionMongoDB=new ConexionMongoDB("agenda");

        // Creamos una colección
            conexionMongoDB.mongoDatabase.createCollection("perros");
        
        // Listar colecciones
            conexionMongoDB.mostrarColecciones();
        
        // Borrar colección
            conexionMongoDB.mongoDatabase.getCollection("perros").drop();
        
        // Listar colecciones
            conexionMongoDB.mostrarColecciones();
        
        // Muestro las estádísticas de la bbdd
            conexionMongoDB.estadisticas();
           
        // Añadir un documento
        Document doc = new Document("nombre", "Pepito")
            .append("edad", 30)
            .append("apellidos","Fernández Gutierrez")
            .append("mote", "Joselito")
            .append("teléfono", "+34 666 777 888");
        conexionMongoDB.anadeDocumento("amigos",doc);
         
        // Añadir un documento a un documento
        Document felicitaciones = new Document("Cumpleaños","25-06")
                .append("Santo","13-12");
        doc = new Document("nombre", "Pepito")
            .append("edad", 30)
            .append("apellidos","Fernández Gutierrez")
            .append("mote", "Joselito")
            .append("teléfono", "+34 666 777 888")
            .append("felicitaciones", felicitaciones);
        
        conexionMongoDB.anadeDocumento("amigos",doc);
        
        // Añadir múltiples documentos
        List<Document> docs = new ArrayList<>();
        Document doc1 = new Document("nombre", "Jesús")
            .append("edad", 30)
            .append("apellidos","Fernández Gutierrez")
            .append("mote", "Suso")
            .append("teléfono", "+34 666 777 888");
        docs.add(doc1);
        Document doc2 = new Document("nombre", "Jesusa")
            .append("edad", 30)
            .append("apellidos","Fernández Gutierrez")
            .append("mote", "Susanita")
            .append("teléfono", "+34 666 777 888");
        docs.add(doc2);
        conexionMongoDB.anadeDocumento("amigos",docs);
        
        
        // Buscar en una coleccion
        Document documentoBuscar = new Document("edad",30);
        conexionMongoDB.buscarDocumento("amigos",documentoBuscar);
        
        // Buscar en una coleccion con proyección
       
        Document proyeccion = new Document("_id",0)
                .append("nombre",1)
                .append("mote",1);
        conexionMongoDB.buscarDocumento("amigos",documentoBuscar,proyeccion);
        
        // Buscar en una coleccion con proyección usando Projections
        conexionMongoDB.buscarDocumento("amigos",documentoBuscar,Projections.include("nombre","mote"));
        conexionMongoDB.buscarDocumento("amigos",documentoBuscar,Projections.exclude("_id","teléfono"));
        conexionMongoDB.buscarDocumento("amigos",documentoBuscar,Projections.excludeId());
        conexionMongoDB.buscarDocumento("amigos",documentoBuscar,Projections.fields(Projections.include("nombre","mote"),Projections.excludeId()));
        
       // Acutalizamos un documento
       
       Bson documentoNuevo = Updates.set("edad",31);
       conexionMongoDB.actualizaDocumento("amigos", documentoBuscar, documentoNuevo);
       documentoBuscar = new Document("edad",31);
       conexionMongoDB.buscarDocumento("amigos",documentoBuscar);
       
       // Actualizamos más de un dato en un documento
       documentoNuevo = Updates.combine(
               Updates.set("edad",31),
               Updates.set("nombre","Velero")
       );
       conexionMongoDB.actualizaDocumento("amigos", documentoBuscar, documentoNuevo);
       documentoBuscar = new Document("edad",31);
       conexionMongoDB.buscarDocumento("amigos",documentoBuscar);
       
       // Actulizamos todos los documentos
       documentoNuevo = Updates.set("nuevoCampo","Eeeeeooo");
       conexionMongoDB.actualizaDocumentos("amigos", documentoBuscar, documentoNuevo);
       documentoBuscar = new Document("nuevoCampo","Eeeeeooo");
       conexionMongoDB.buscarDocumento("amigos",documentoBuscar);
       
       //Eliminamos un documento
       conexionMongoDB.eliminarDocumento("amigos",documentoBuscar);
       conexionMongoDB.eliminarDocumentos("amigos",documentoBuscar);
       documentoBuscar = new Document("edad",31);
       conexionMongoDB.buscarDocumento("amigos",documentoBuscar);

    }
    
    
}
