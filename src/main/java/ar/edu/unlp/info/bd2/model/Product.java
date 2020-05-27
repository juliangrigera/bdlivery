package ar.edu.unlp.info.bd2.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.bson.types.ObjectId;

import ar.edu.unlp.info.bd2.mongo.PersistentObject;


public class Product implements PersistentObject{

    public Product() {}

    public Product(String name, Float price, Float weight, Supplier supplier) {
        this.name = name;
        this.weight = weight;
        this.supplier = supplier;
        this.historyPrice = new ArrayList<>();
        Date date = new Date();
        this.addPrice(price, date); //CREO Q ACA NO HACE FALTA MANDAR THIS
    }

    public Product(String name, Float price, Float weight, Supplier supplier, Date actualDate) {
        this.name = name;
        this.weight = weight;
        this.supplier = supplier;
        this.historyPrice = new ArrayList<>();
        Date date = actualDate;
        this.addPrice(price, date);
    }

    @BsonId
    private ObjectId objectId;

    private String name;

    private Float weight;

    private Float price;

    @BsonIgnore
    private List<HistoryPrice> historyPrice = new ArrayList<>();

    private Supplier supplier;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public Float getPrice() {
        return price;
    }

    public Float getPriceAt(Date day) {
        Float pricesAt=0F;
        for (int i = 0; i< this.getPrices().size(); i++) {
            if(this.getPrices().get(i).getStartDate().before(day)) {
                pricesAt=this.getPrices().get(i).getPrice();
            }
        }
        return pricesAt;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public void addPrice(Float price, Date date) {
        if(this.price != null) {
            HistoryPrice lastPrice = this.historyPrice.get(this.historyPrice.size()-1);
            lastPrice.setEndDate(date);
        }
        this.setPrice(price);
        HistoryPrice hp = new HistoryPrice(price, date, this);
        this.setHistoryPrice(hp);
    }

    public List<HistoryPrice> getPrices() {
        return historyPrice;
    }

    public void setHistoryPrice(HistoryPrice historyPrice) {
        this.historyPrice.add(historyPrice);
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
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