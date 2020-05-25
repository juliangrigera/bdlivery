package ar.edu.unlp.info.bd2.model;


import java.util.ArrayList;
import java.util.List;

import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.bson.types.ObjectId;

import ar.edu.unlp.info.bd2.mongo.PersistentObject;

@BsonDiscriminator
public class ProductOrder implements PersistentObject {

    @BsonId
    private ObjectId objectId;

    private Long quantity;

    private Product product;

    public ProductOrder(){

    }

    public ProductOrder(Long quantity, Product product) {
        this.quantity = quantity;
        this.product = product;
    }

    public Long getQuantity() {
        return this.quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return this.product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public ObjectId getObjectId() {
        return objectId;
    }

    @Override
    public void setObjectId(ObjectId objectId) {
        objectId = objectId;

    }

    public ObjectId getId(){
        return objectId;
    }

    public void setId(ObjectId objectId){
        objectId = objectId;
    }
}