package ar.edu.unlp.info.bd2.model;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.bson.types.ObjectId;

import ar.edu.unlp.info.bd2.mongo.PersistentObject;

@BsonDiscriminator
public class OrderStatus implements PersistentObject {

    @BsonId
    private ObjectId objectId;

    private String status;

    private Date startDate;

    private Date endDate;

    private Order ord;

    public OrderStatus() {}

    public OrderStatus(String status) {
        this.status = status;
        this.startDate = null;
        this.endDate = null;
        this.ord = null; //ESTO NO SE SI ESTA BIEN!!! sino habria q cambiar en todos esto
    }

    public OrderStatus(String status, Order ord, Date date) {
        this.status = status;
        this.startDate = date;
        this.endDate = null;
        this.ord = ord;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public ObjectId getObjectId() {
        return this.objectId;
    }

    public void setObjectId(ObjectId objectId) {
        this.objectId = objectId;

    }
}