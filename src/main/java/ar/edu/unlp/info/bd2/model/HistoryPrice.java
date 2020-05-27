package ar.edu.unlp.info.bd2.model;

import java.util.Date;

import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

import ar.edu.unlp.info.bd2.mongo.PersistentObject;


public class HistoryPrice implements PersistentObject{

    public HistoryPrice() {}

    public HistoryPrice(Float price) {
        this.setPrice(price);
        Date date = new Date();
        this.setStartDate(date);
        this.setEndDate(null);
        this.product = null; //contemplando el tp1
    }

    public HistoryPrice(Float price, Date date, Product product) {
        this.price = price;
        this.startDate = date;
        this.endDate = null;
        this.product = product;

    }

    @BsonId
    private ObjectId objectId;

    private Float price;

    private Date startDate;

    private Date endDate;

    private Product product;

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public ObjectId getObjectId() {
        return this.objectId;
    }

    @Override
    public void setObjectId(ObjectId objectId) {
        this.objectId = objectId;

    }

    public ObjectId getId(){
        return this.objectId;
    }

    public void setId(ObjectId objectId){
        this.objectId = objectId;
    }
}