package ar.edu.unlp.info.bd2.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import ar.edu.unlp.info.bd2.model.*;
import ar.edu.unlp.info.bd2.model.Product;
import ar.edu.unlp.info.bd2.repositories.DBliveryException;
import ar.edu.unlp.info.bd2.repositories.DBliveryRepository;

public class DBliveryServiceImpl implements DBliveryService {
	
	private DBliveryRepository repository;
	
	public DBliveryServiceImpl (DBliveryRepository repository) {
		this.repository = repository;
	}

	
	public Product createProduct(String name, Float price, Float weight, Supplier supplier) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Supplier createSupplier(String name, String cuil, String address, Float coordX, Float coordY) {
		// TODO Auto-generated method stub
		Supplier s = new Supplier(name, cuil, address, coordX, coordY);
		return repository.storeSupplier(s);
		
	}

	
	public User createUser(String email, String password, String username, String name, Date dateOfBirth) {
		User user = new User (username, name);
        return repository.storeUser(user);
	}

	
	public Product updateProductPrice(Long id, Float price, Date startDate) throws DBliveryException {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Optional<User> getUserById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Optional<User> getUserByEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Optional<User> getUserByUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Optional<Product> getProductById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Optional<Order> getOrderById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Order createOrder(Date dateOfOrder, String address, Float coordX, Float coordY, User client) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Order addProduct(Long order, Long quantity, Product product) throws DBliveryException {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Order deliverOrder(Long order, User deliveryUser) throws DBliveryException {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Order cancelOrder(Long order) throws DBliveryException {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Order finishOrder(Long order) throws DBliveryException {
		// TODO Auto-generated method stub
		return null;
	}

	
	public boolean canCancel(Long order) throws DBliveryException {
		// TODO Auto-generated method stub
		return false;
	}

	
	public boolean canFinish(Long id) throws DBliveryException {
		// TODO Auto-generated method stub
		return false;
	}

	
	public boolean canDeliver(Long order) throws DBliveryException {
		// TODO Auto-generated method stub
		return false;
	}

	
	public OrderStatus getActualStatus(Long order) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public List<Product> getProductByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Product updateProductPrice(Object id, Float valueOf, Date startDate) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
}
