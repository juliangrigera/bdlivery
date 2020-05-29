package ar.edu.unlp.info.bd2.services;

import ar.edu.unlp.info.bd2.model.*;
import ar.edu.unlp.info.bd2.repositories.DBliveryException;
import ar.edu.unlp.info.bd2.repositories.DBliveryMongoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.swing.text.html.Option;

@Service

public class DBliveryServiceImpl implements  DBliveryService {
    private DBliveryMongoRepository repository;

    public DBliveryServiceImpl(DBliveryMongoRepository repository) {
        this.repository = repository;
    }


    @Override
    public Product createProduct(String name, Float price, Float weight, Supplier supplier) {
    	  Product product = new Product(name, price, weight, supplier);
    	  //repository.insertProduct(prod, "product");
          repository.insertInto("product", Product.class, product);
          //return prod;
          return product;
    }

    @Override
    public Product createProduct(String name, Float price, Float weight, Supplier supplier, Date date) {
      return null;
    }

    @Override
    public Supplier createSupplier(String name, String cuil, String address, Float coordX, Float coordY) {
        Supplier supplier = new Supplier(name, cuil, address, coordX, coordY);
        repository.insertInto("supplier", Supplier.class, supplier);
        return supplier;
    }

    @Override
    public User createUser(String email, String password, String username, String name, Date dateOfBirth) {
        User user = new User(email, password, username, name, dateOfBirth);
        repository.insertInto("user", User.class, user);
        return user;
    }

    @Override
    public Product updateProductPrice(ObjectId id, Float price, Date startDate) throws DBliveryException {
        Optional<Product> op = repository.getProductById("_id", id);
        
        if (!op.isPresent() ) {
			throw new DBliveryException("The product does not exist");
		}

		Product prod = op.get();
		prod.addPrice(price, startDate);
		
		repository.updateProduct("_id", id, prod);

		return prod;
        
    }

    @Override
    public Optional<User> getUserById(ObjectId id) {
    	return repository.getUserBy("_id", id, "user");
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
    	return repository.getUserBy("email", email, "user");
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        return repository.getUserBy("username", username, "user");
    }

    @Override
    public Optional<Order> getOrderById(ObjectId id) {
        return Optional.empty();
    }

    @Override
    public Order createOrder(Date dateOfOrder, String address, Float coordX, Float coordY, User client) {
        return null;
    }

    @Override
    public Order addProduct(ObjectId order, Long quantity, Product product) throws DBliveryException {
        return null;
    }

    @Override
    public Order deliverOrder(ObjectId order, User deliveryUser) throws DBliveryException {
        return null;
    }

    @Override
    public Order deliverOrder(ObjectId order, User deliveryUser, Date date) throws DBliveryException {
        return null;
    }

    @Override
    public Order cancelOrder(ObjectId order) throws DBliveryException {
        return null;
    }

    @Override
    public Order cancelOrder(ObjectId order, Date date) throws DBliveryException {
        return null;
    }

    @Override
    public Order finishOrder(ObjectId order) throws DBliveryException {
        return null;
    }

    @Override
    public Order finishOrder(ObjectId order, Date date) throws DBliveryException {
        return null;
    }

    @Override
    public boolean canCancel(ObjectId order) throws DBliveryException {
        return false;
    }

    @Override
    public boolean canFinish(ObjectId id) throws DBliveryException {
        return false;
    }

    @Override
    public boolean canDeliver(ObjectId order) throws DBliveryException {
        return false;
    }

    @Override
    public OrderStatus getActualStatus(ObjectId order) {
        return null;
    }

    @Override
    public List<Product> getProductsByName(String name) {
    	List<Product> list = new ArrayList();
        repository.getProductsByName("name", name, "product").into(list); //con into agrego todos los documentos del FindItereble a (en este caso) una lista
        return list;
    }
    

}
