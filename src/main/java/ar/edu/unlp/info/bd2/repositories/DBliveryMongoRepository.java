package ar.edu.unlp.info.bd2.repositories;

import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.regex;

import ar.edu.unlp.info.bd2.model.*;
import ar.edu.unlp.info.bd2.mongo.*;
import com.mongodb.client.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;

public class DBliveryMongoRepository {

    @Autowired private MongoClient client;


    public void saveAssociation(PersistentObject source, PersistentObject destination, String associationName) {
        Association association = new Association(source.getObjectId(), destination.getObjectId());
        this.getDb()
                .getCollection(associationName, Association.class)
                .insertOne(association);
    }

    public MongoDatabase getDb() {
        return this.client.getDatabase("bd2_grupo" + this.getGroupNumber() );
    }

    private Integer getGroupNumber() { return 3; }

    public <T extends PersistentObject> List<T> getAssociatedObjects(
            PersistentObject source, Class<T> objectClass, String association, String destCollection) {
        AggregateIterable<T> iterable =
                this.getDb()
                        .getCollection(association, objectClass)
                        .aggregate(
                                Arrays.asList(
                                        match(eq("source", source.getObjectId())),
                                        lookup(destCollection, "destination", "_id", "_matches"),
                                        unwind("$_matches"),
                                        replaceRoot("$_matches")));
        Stream<T> stream =
                StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterable.iterator(), 0), false);
        return stream.collect(Collectors.toList());
    }
    
    public void insertInto(String collectionName, Class collectionClass, Object object) {
        this.getDb().getCollection(collectionName, collectionClass).insertOne(object);
    }
    
    public void insertProduct(Product prod, String string) {
    	this.getDb().getCollection(string, Product.class).insertOne(prod);
    }
    
    public Optional getUserBy(String field, Object parameter, String nameClass) {
    	MongoCollection collec = this.getDb().getCollection(nameClass, User.class);
    	return Optional.ofNullable(collec.find(eq(field, parameter) ).first() );
    } 
    
    public Optional getProductById(String field, Object parameter) {
    	MongoCollection collec = this.getDb().getCollection("product", Product.class);
    	return Optional.ofNullable(collec.find(eq(field, parameter) ).first() );
    }
    
    public void saveAssociation(Object obj, String string) {
    	this.getDb().getCollection(string, Association.class).insertOne((Association) obj);
    }
    
    public FindIterable<Product> getProductsByName(String field, Object parameter,String nameClass) {
    	MongoCollection<Product> collection = this.getDb().getCollection(nameClass, Product.class);
        return   collection.find(regex(field, ""+parameter+""));
    }
    
    public void updateProduct(String field, Object parameter, Product prod) {
    	MongoCollection collec = this.getDb().getCollection("product", Product.class);
    	
    	collec.replaceOne( eq(field, parameter),  prod); //No quiero actualizar un atributo sino remplazar el objeto ya modificado
    }

}
