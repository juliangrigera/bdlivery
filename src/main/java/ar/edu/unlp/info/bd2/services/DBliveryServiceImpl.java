package ar.edu.unlp.info.bd2.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import ar.edu.unlp.info.bd2.model.*;
import ar.edu.unlp.info.bd2.repositories.DBliveryException;
import ar.edu.unlp.info.bd2.repositories.DBliveryRepository;

public class DBliveryServiceImpl implements DBliveryService {

	private DBliveryRepository repository;

	public DBliveryServiceImpl(DBliveryRepository repository) {
		this.repository = repository;
	}

	@Transactional
	public Product createProduct(String name, Float price, Float weight, Supplier supplier) {
		// TODO Auto-generated method stub
		Product p = new Product(name, price, weight, supplier);
		return repository.storeProduct(p);
	}

	@Transactional
	public Supplier createSupplier(String name, String cuil, String address, Float coordX, Float coordY) {
		// TODO Auto-generated method stub
		Supplier s = new Supplier(name, cuil, address, coordX, coordY);
		return repository.storeSupplier(s);

	}

	public User createUser(String email, String password, String username, String name, Date dateOfBirth) {
		User user = new User(email, password, username, name, dateOfBirth);
		return repository.storeUser(user);
	}
	
	public Product updateProductPrice(Long id, Float price, Date startDate) throws DBliveryException {
		// TODO Auto-generated method stub
		Optional<Product> prod = this.getProductById(id);
		if (!prod.isPresent() ) {
			throw new DBliveryException("The product does not exist");
		}

		Product prd = prod.get();
		prd.addPrice(price, startDate);

		return repository.updateProduct(prd);
		
	}
	
	public Optional<User> getUserById(Long id) {
		Optional<User> u = repository.getUserById(id);

		return u;
	}

	public Optional<User> getUserByEmail(String email) {
		Optional<User> u = repository.getUserByEmail(email);

		return u;
	}

	public Optional<User> getUserByUsername(String username) {
		Optional<User> u = repository.getUserByUsername(username);

		return u;
	}

	public Optional<Product> getProductById(Long id) {
		// TODO Auto-generated method stub
		return this.repository.getProductByid(id);
	}

	public Optional<Order> getOrderById(Long id) {
		Optional<Order> o = repository.getOrderById(id);

		return o;
	}

	public Order createOrder(Date dateOfOrder, String address, Float coordX, Float coordY, User client) {
		Order order = new Order(dateOfOrder, address, coordX, coordY, client);
		return repository.storeOrder(order);
	}

	public Order addProduct(Long order, Long quantity, Product product) throws DBliveryException {
		Optional<Order> op = this.repository.getOrderById(order);

		if (!op.isPresent() ) {
			throw new DBliveryException("The order doesnt exists");
		}

		Order o = op.get();
		ProductOrder po = new ProductOrder(quantity, product);
		o.addProductOrder(po);

		return repository.updateOrder(o);
	}
	
	public Order deliverOrder(Long order, User deliveryUser) throws DBliveryException {
		Optional<Order> op = this.getOrderById(order);

		if(!op.isPresent() ){
			throw new DBliveryException("Order does not exist");
		}
			
		if (!this.canDeliver(order)) {
			throw new DBliveryException("The order can't be delivered");
		} 

		Order o = op.get();

		o.setDeliveryUser(deliveryUser);
		OrderStatus sent = new OrderStatus("Sent");
		o.addOrderStatus(sent); //Este metodo setea el actualState!

		return repository.updateOrder(o);
	}
	

	public Order cancelOrder(Long order) throws DBliveryException {
		Optional<Order> op = this.getOrderById(order);

		if(!op.isPresent() ){
			throw new DBliveryException("Order does not exist");
		}

		if (!this.canCancel(order) ) {
			throw new DBliveryException("The order can't be cancelled");
		}

		Order o = op.get();

		OrderStatus cancelled = new OrderStatus("Cancelled");
		o.addOrderStatus(cancelled);
		 
		 return repository.updateOrder(o);
	}

	public Order finishOrder(Long order) throws DBliveryException {
		Optional<Order> op = this.getOrderById(order);

		if(!op.isPresent() ){
			throw new DBliveryException("Order does not exist");
		}

		if (!this.canFinish(order) ) {
			throw new DBliveryException("The order can't be delivered");
		}

		Order o = op.get();

		OrderStatus delivered = new OrderStatus("Delivered");
		o.addOrderStatus(delivered);
		 
		 return repository.updateOrder(o);
	}

	public boolean canCancel(Long order) throws DBliveryException {
		// TODO Auto-generated method stub
		Optional<Order> o = this.getOrderById(order);
		if (o.isPresent()) {
			Order o1 = o.get();
			String status = o1.getActualState().getStatus();
			if (status == "Pending") {
				return true;
			}
		}
		else {
			throw new DBliveryException("The order doesnt exist");
		}
		return false;
		}
		

	@Transactional
	public boolean canFinish(Long id) throws DBliveryException {
		// TODO Auto-generated method stub
		Optional<Order> o = this.getOrderById(id);
		if (o.isPresent()) {
			Order o1 = o.get();
			if (o1.getActualState().getStatus() == "Sent") {
				return true;
			}
		}
		else {
			throw new DBliveryException("The order doesnt exist");
		}
		return false;
	}

	public boolean canDeliver(Long order) throws DBliveryException {
		// TODO Auto-generated method stub
		Optional<Order> o = this.getOrderById(order);
		if (o.isPresent()) {
			Order order1 = o.get();
			if (order1.getProducts().size() >= 1) {
				if (this.getActualStatus(order).getStatus() == "Pending") {
					return true;
				} else {
					throw new DBliveryException("The order can't be delivered");
				}
			}
		}
		return false;
	}

	public OrderStatus getActualStatus(Long order) {
		// TODO Auto-generated method stub
		Optional<Order> o = this.getOrderById(order);
		if (!o.isPresent()) {
			return null;
		}
		//La orden existe por lo tanto si o si tiene un estado
		OrderStatus os = repository.getActualState(order);
		return os;
		
	}

	@Transactional
	public List<Product> getProductByName(String name) {
		// TODO Auto-generated method stub
		return repository.getProductByName(name);
	}

}
