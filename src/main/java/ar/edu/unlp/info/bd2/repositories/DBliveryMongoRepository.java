package ar.edu.unlp.info.bd2.repositories;

import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.gte;
import static com.mongodb.client.model.Filters.lte;
import static com.mongodb.client.model.Filters.regex;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.size;
import static com.mongodb.client.model.Filters.near;
import com.mongodb.client.model.Sorts.*;
import com.mongodb.client.model.geojson.Point;
import com.mongodb.client.model.geojson.Position;

import ar.edu.unlp.info.bd2.model.*;
import ar.edu.unlp.info.bd2.mongo.*;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;


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

    public <T extends PersistentObject> List<T> getObjectsAssociatedWith(
            ObjectId objectId, Class<T> objectClass, String association, String destCollection) {
        AggregateIterable<T> iterable =
                this.getDb()
                        .getCollection(association, objectClass)
                        .aggregate(
                                Arrays.asList(
                                        match(eq("destination", objectId)),
                                        lookup(destCollection, "source", "_id", "_matches"),
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
    
    public Optional getOrderById(String field, Object parameter) {
    	MongoCollection collec = this.getDb().getCollection("order", Order.class);
    	return Optional.ofNullable(collec.find(eq(field, parameter) ).first() );
    }
    
    public void saveAssociation(Object obj, String string) {
    	this.getDb().getCollection(string, Association.class).insertOne((Association) obj);
    }
    
    //ver
    public FindIterable<Product> getProductsByName(String field, Object parameter,String nameClass) {
    	MongoCollection<Product> collection = this.getDb().getCollection(nameClass, Product.class);
        return   collection.find(regex(field, ""+parameter+""));
    }
    
    public void updateProduct(String field, Object parameter, Product prod) {
    	MongoCollection collec = this.getDb().getCollection("product", Product.class);
    	
    	collec.replaceOne( eq(field, parameter),  prod); //No quiero actualizar un atributo sino remplazar el objeto ya modificado
    }
    
    public void updateOrder(String field, Object parameter, Order o) {
    	MongoCollection collec = this.getDb().getCollection("order", Order.class);
    	
    	collec.replaceOne( eq(field, parameter),  o);
    }
    
    //Tambien trabajo con un FindIterable!
    public FindIterable<Order> getOrderByAtribute(String field, Object parameter) {
    	MongoCollection<Order> collection = this.getDb().getCollection("order", Order.class);
    	return collection.find(eq(field, parameter));
    }

    public <T extends PersistentObject> List<T> getSoldProductsOn(Date day) {
        AggregateIterable<T> collecAggregate = (AggregateIterable<T>) this.getDb().getCollection("order", Order.class).aggregate(
                Arrays.asList(
                        match(eq("dateOfOrder", day) ),
                        unwind("$productOrders"),
                        replaceRoot("$productOrders.product")
                ));

        Stream<T> stream =
                StreamSupport.stream(Spliterators.spliteratorUnknownSize(collecAggregate.iterator(), 0), false);
        return stream.collect(Collectors.toList());
    }

    public <T extends PersistentObject> List<T> getBestSellingProduct() {

        AggregateIterable<T> collecAggregate = (AggregateIterable<T>) this.getDb().getCollection("order", Order.class).aggregate(
                Arrays.asList(
                        unwind("$productOrders"),
                        new Document("$group", new Document("_id", "$productOrders.product").append("total", new Document("$sum", "$productOrders.quantity"))),
                        sort(Sorts.descending("total")),
                        replaceRoot("$_id"),
                        limit(1)
                ));

        Stream<T> stream =
                StreamSupport.stream(Spliterators.spliteratorUnknownSize(collecAggregate.iterator(), 0), false);
        return stream.collect(Collectors.toList());
    	/*
        group(
                new Document().append("product_id", "$products.product._id"), Accumulators.sum("count", 1)
        ),
        sort(Sorts.descending("count"))
		)).first().get("_id").toString().substring(21,45)));
    	 */
    }

    public FindIterable<Order> getDeliveredOrdersForUser(String username) {
        MongoCollection<Order> collec = this.getDb().getCollection("order", Order.class);
        return collec.find(and(eq("actualState.status", "Delivered"), eq("client.username", username) ) );
    }

    public List<Order> getOrdenNearPlazaMoreno() {
        ArrayList<Order> list = new ArrayList<>();
        MongoCollection<Order> collec = this.getDb().getCollection("order",Order.class);

        Point pMoreno = new Point(new Position(-34.921236,-57.954571) );
        for (Order o: collec.find(near("position", pMoreno, 400.0, 0.0))) {
            list.add(o);
        }

        return list;
    }

    public FindIterable<Product> getProductsOnePrice() {
        MongoCollection<Product> collec = this.getDb().getCollection("product", Product.class);
        return collec.find(size("historyPrice", 1) );
    }

    public FindIterable<Order> getDeliveredOrdersInPeriod(Date startDate, Date endDate) {
        MongoCollection<Order> collec = this.getDb().getCollection("order", Order.class);
        return collec.find(and(eq("actualState.status", "Delivered"), gte("dateOfOrder", startDate), lte("dateOfOrder", endDate) ) );
    }

    public Optional getMaxWeigth() {
        MongoCollection<Product> collec = this.getDb().getCollection("product", Product.class);
        return Optional.ofNullable(collec.find().sort(Sorts.descending("weight")).first()); //Se puede sacar el ofNullable

    }
    
    public <T extends PersistentObject> List<T> getTopNSuppliersInSentOrders(int n) {
    	AggregateIterable<T> collecAggregate = (AggregateIterable<T>) this.getDb().getCollection("order", Order.class).aggregate(
                Arrays.asList(
                		match(eq("actualState.status", "Sent") ),
                        unwind("$productOrders"),
                        new Document("$group", new Document("_id", "$productOrders.product.supplier").append("total", new Document("$sum", "$productOrders.quantity"))),
                        sort(Sorts.descending("total")),
                        replaceRoot("$_id"),
                        limit(n)
                ));
    	
    	Stream<T> stream =
                StreamSupport.stream(Spliterators.spliteratorUnknownSize(collecAggregate.iterator(), 0), false);
        return stream.collect(Collectors.toList());
    }
}
