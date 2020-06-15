package ar.edu.unlp.info.bd2.services;

import ar.edu.unlp.info.bd2.model.*;
import ar.edu.unlp.info.bd2.repositories.DBliveryException;
import ar.edu.unlp.info.bd2.repositories.DBliveryMongoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.bson.types.ObjectId;

//import ar.edu.unlp.info.bd2.services.DBliveryStatisticsService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.swing.text.html.Option;

@Service
public class DBliveryServiceImpl implements  DBliveryService, DBliveryStatisticsService {
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
    	Optional<Order> o = repository.getOrderById("_id", id);

		return o;
    }

    @Override
    public Order createOrder(Date dateOfOrder, String address, Float coordX, Float coordY, User client) {
    	Order order = new Order(dateOfOrder, address, coordX, coordY, client);
    	repository.insertInto("order", Order.class, order);
        return order;
    	
    }

    @Override
    public Order addProduct(ObjectId order, Long quantity, Product product) throws DBliveryException {
    	Optional<Order> op = this.repository.getOrderById("_id", order);

		if (!op.isPresent() ) {
			throw new DBliveryException("The order doesnt exists");
		}

		Order o = op.get();
		//traemos el producto de la base
		Optional<Product> ppe = repository.getProductById("_id", product.getObjectId());
		Product este = ppe.get();
		
		ProductOrder po = new ProductOrder(quantity, este);
		o.addProductOrder(po);

		repository.updateOrder("_id", order, o);
		
		return o;
		
    }

    @Override
    public Order deliverOrder(ObjectId order, User deliveryUser) throws DBliveryException {
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
		o.addOrderStatus(sent); 
		
		repository.updateOrder("_id", order, o);
		
		return o;
		
    }

    @Override
    public Order cancelOrder(ObjectId order) throws DBliveryException {
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
		 
		repository.updateOrder("_id", order, o);
		return o;
    }

    @Override
    public Order finishOrder(ObjectId order) throws DBliveryException {
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
		 
		repository.updateOrder("_id", order, o);
		return o;
    }

    @Override
    public boolean canCancel(ObjectId order) throws DBliveryException {
    	Optional<Order> o = this.getOrderById(order);
    	if (o.isPresent()) {
    		Order o1 = o.get();
    		if(o1.getActualState().getStatus().equals("Pending") ) {
    			return true;
    		}
    	}else {
    		throw new DBliveryException("The order doesnt exist");
    	}
    	return false;
    }

    @Override
    public boolean canFinish(ObjectId id) throws DBliveryException {
    	Optional<Order> o = this.getOrderById(id);
    	if (o.isPresent()) {
    		Order o1 = o.get();
    		if (o1.getActualState().getStatus().equals("Sent") ) {
    			return true;
    		}
    	} else {
    		throw new DBliveryException("The order doesnt exist");
    	}
    	return false;
    }

    @Override
    public boolean canDeliver(ObjectId order) throws DBliveryException {
    	Optional<Order> o = this.getOrderById(order);
    	if (o.isPresent()) {
    		Order order1 = o.get();
    		if (order1.getProducts().size() >= 1) {
    			if (this.getActualStatus(order).getStatus().equals("Pending") ) {
    				return true;
    			} else {
    				throw new DBliveryException("The order can't be delivered");
    			}
    		}
    	}
    	return false;
    }

    @Override
    public OrderStatus getActualStatus(ObjectId order) {
    	Optional<Order> o = this.getOrderById(order);
    	if (!o.isPresent()) {
    		return null;
    	}
    	//La orden existe por lo tanto si o si tiene un estado
    	Order or = o.get();
    	//OrderStatus os = or.getStatus().get(or.getStatus().size()-1);
    	
    	OrderStatus os = or.getActualState();
    	return os;
    }

    @Override
    public List<Product> getProductsByName(String name) {
    	List<Product> list = new ArrayList();
        repository.getProductsByName("name", name, "product").into(list); //con into agrego todos los documentos del FindItereble a (en este caso) una lista
        return list;
    }
    
    @Override
    public Product createProduct(String name, Float price, Float weight, Supplier supplier, Date date) {
    	Product product = new Product(name, price, weight, supplier, date);
  	  
        repository.insertInto("product", Product.class, product);
       
        return product;
    }
    
    @Override
    public Order finishOrder(ObjectId order, Date date) throws DBliveryException {
    	Optional<Order> op = this.getOrderById(order);

		if(!op.isPresent() ){
			throw new DBliveryException("Order does not exist");
		}

		if (!this.canFinish(order) ) {
			throw new DBliveryException("The order can't be delivered");
		}

		Order o = op.get();

		o.addOrderStatus("Delivered", date);
		 
		repository.updateOrder("_id", order, o);
		return o;
    }
    
    @Override
    public Order cancelOrder(ObjectId order, Date date) throws DBliveryException {
    	Optional<Order> op = this.getOrderById(order);

		if(!op.isPresent() ){
			throw new DBliveryException("Order does not exist");
		}

		if (!this.canCancel(order) ) {
			throw new DBliveryException("The order can't be cancelled");
		}

		Order o = op.get();

		o.addOrderStatus("Cancelled", date);
		 
		repository.updateOrder("_id", order, o);
		return o;
    }
    
    @Override
    public Order deliverOrder(ObjectId order, User deliveryUser, Date date) throws DBliveryException {
    	Optional<Order> op = this.getOrderById(order);

		if(!op.isPresent() ){
			throw new DBliveryException("Order does not exist");
		}
			
		if (!this.canDeliver(order)) {
			throw new DBliveryException("The order can't be delivered");
		} 

		Order o = op.get();

		o.setDeliveryUser(deliveryUser);
		
		o.addOrderStatus("Sent", date);
		
		repository.updateOrder("_id", order, o);
		
		return o;
    }

	public List<Order> getAllOrdersMadeByUser(String username) throws DBliveryException {
		Optional<User> op = this.getUserByUsername(username);
		
		if(!op.isPresent() ){
			throw new DBliveryException("User does not exist");
		}
		
		List<Order> list = new ArrayList();
		User u1 = op.get();
		
		repository.getOrderByAtribute("client.username", username).into(list);
		
		
		
		return list;
	}

	public List<Supplier> getTopNSuppliersInSentOrders(int n) {
		// TODO Auto-generated method stub
		return null;
	}


	public List<Order> getPendingOrders() {
		List<Order> list = new ArrayList();

		repository.getOrderByAtribute("actualState.status", "Pending").into(list);

		return list;
	}


	public List<Order> getSentOrders() {
		List<Order> list = new ArrayList();

		repository.getOrderByAtribute("actualState.status", "Sent").into(list);

		return list;
	}


	public List<Order> getDeliveredOrdersInPeriod(Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		return null;
	}


	public List<Order> getDeliveredOrdersForUser(String username) {
		List<Order> list = new ArrayList();

		repository.getDeliveredOrdersForUser(username).into(list);

		return list;
	}


	public Product getBestSellingProduct() {
		List<Product> list = new ArrayList();
		list = repository.getBestSellingProduct();
		return list.get(0);
	}


	public List<Product> getProductsOnePrice() {
		List<Product> list = new ArrayList();
		repository.getProductsOnePrice().into(list);
		return list;

	}

	public List<Product> getSoldProductsOn(Date day) {
		List<Product> list = new ArrayList();
		list = repository.getSoldProductsOn(day);
		return list;
	}


	public Product getMaxWeigth() {
		// TODO Auto-generated method stub
		return null;
	}


	public List<Order> getOrderNearPlazaMoreno() {
		List<Order> list = new ArrayList();
		list = repository.getOrdenNearPlazaMoreno();
		return list;

	}
    

}
