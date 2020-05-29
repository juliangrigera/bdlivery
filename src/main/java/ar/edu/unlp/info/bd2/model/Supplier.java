package ar.edu.unlp.info.bd2.model;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.bson.types.ObjectId;

import ar.edu.unlp.info.bd2.mongo.PersistentObject;

@BsonDiscriminator
public class Supplier implements PersistentObject {

    @BsonId
    private ObjectId objectId;

    private String name;

    private String cuil;

    private String address;

    private Float coordX;

    private Float coordY;

    public Supplier() {}

    public Supplier(String name, String cuil, String address, Float coordX, Float coordY) {
        this.name = name;
        this.cuil = cuil;
        this.address = address;
        this.coordX = coordX;
        this.coordY = coordY;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getCuil() {
        return cuil;
    }


    public void setCuil(String cuil) {
        this.cuil = cuil;
    }


    public String getAddress() {
        return address;
    }


    public void setAddress(String address) {
        this.address = address;
    }


    public Float getCoordX() {
        return coordX;
    }


    public void setCoordX(Float coordX) {
        this.coordX = coordX;
    }


    public Float getCoordY() {
        return coordY;
    }


    public void setCoordY(Float coordY) {
        this.coordY = coordY;
    }

    @Override
    public ObjectId getObjectId() {
        return this.objectId;
    }

    @Override
    public void setObjectId(ObjectId objectId) {
        this.objectId = objectId;

    }

}