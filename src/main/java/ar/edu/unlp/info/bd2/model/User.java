package ar.edu.unlp.info.bd2.model;

import javax.persistence.*; //Utilizo la persistencia de JPA no hibernate...

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.bson.types.ObjectId;

import ar.edu.unlp.info.bd2.mongo.PersistentObject;

@BsonDiscriminator
public class User implements PersistentObject {

    @BsonId
    private ObjectId objectId;


    private String email;


    private String password;


    private String username;


    private String name;


    private Date dateOfBirth;




    public User() {}

    public User(String email, String password, String username, String name, Date dateOfBirth) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
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