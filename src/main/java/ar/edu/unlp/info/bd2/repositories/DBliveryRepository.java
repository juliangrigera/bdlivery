package ar.edu.unlp.info.bd2.repositories;

import ar.edu.unlp.info.bd2.model.*;
import org.hibernate.*;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DBliveryRepository {

	@Autowired
	SessionFactory sessionFactory;
	
	public Product updateProduct(Product p){
		sessionFactory.getCurrentSession().update(p);
		return p;
	}
	
	public Order updateOrder(Order o){
		sessionFactory.getCurrentSession().update(o);
		return o;
	}

	public User storeUser(User user) {
		sessionFactory.getCurrentSession().save(user);
		return user;
	}

	public Supplier storeSupplier(Supplier supplier) {
		sessionFactory.getCurrentSession().save(supplier);
		return supplier;
	}

	public Product storeProduct(Product p) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().save(p);
		return p;
	}
	
	public Order storeOrder(Order order) {
		sessionFactory.getCurrentSession().save(order);
		return order;
	}

	public List<Product> getProductByName(String name) {
		String stmt = "SELECT p FROM Product p WHERE p.name like ?1";
	    Session session = sessionFactory.getCurrentSession();
	   
	    Query<Product> query = session.createQuery(stmt, Product.class); 
	    query.setParameter(1,"%" + name + "%");
	    List<Product> results = query.getResultList();
		return results;
		
	}

	public Optional<Product> getProductByid(Long id) {
		String stmt = "SELECT p FROM Product p WHERE p.id = :id";
	    Session session = sessionFactory.getCurrentSession();
	    
	    Query<Product> query = session.createQuery(stmt, Product.class); 
	    query.setParameter("id", id);
	    return query.uniqueResultOptional();
	}
	
	public Optional<User> getUserByUsername(String username){
		String stmt = "SELECT u FROM User u WHERE u.username = :username";
	    Session session = sessionFactory.getCurrentSession();
	    
	    Query<User> query = session.createQuery(stmt, User.class); 
	    query.setParameter("username", username);
	    return query.uniqueResultOptional();
	}
	
	public Optional<User> getUserByEmail(String email){
		String stmt = "SELECT u FROM User u WHERE u.email = :email";
	    Session session = sessionFactory.getCurrentSession();
	    
	    Query<User> query = session.createQuery(stmt, User.class); 
	    query.setParameter("email", email);
	    return query.uniqueResultOptional();
	}
	
	public Optional<User> getUserById(Long id){
		String stmt = "SELECT u FROM User u WHERE u.id = :id";
	    Session session = sessionFactory.getCurrentSession();
	    
	    Query<User> query = session.createQuery(stmt, User.class); 
	    query.setParameter("id", id);
	    return query.uniqueResultOptional();
	}
	
	public Optional<Order> getOrderById(Long id){
		String stmt = "SELECT o FROM Order o WHERE o.id = :id";
	    Session session = sessionFactory.getCurrentSession();
	    
	    Query<Order> query = session.createQuery(stmt, Order.class); 
	    query.setParameter("id", id);
	    return query.uniqueResultOptional();
	}
	
	
	public OrderStatus getActualState(Long orderId) {
		String stmt = "SELECT os FROM OrderStatus os WHERE os.id = (SELECT o.actualState.id FROM Order o WHERE o.id = :orderId)"; //o.actualState.id
		Session session = sessionFactory.getCurrentSession();
		   
		Query<OrderStatus> query = session.createQuery(stmt, OrderStatus.class);
		query.setParameter("orderId", orderId);
		OrderStatus os = query.uniqueResult();
		   
		return os;
	}

	public List<Order> getPendingOrders() {
		String stmt = "SELECT o FROM Order o join o.actualState os WHERE os.status='Pending'";
		Session session = sessionFactory.getCurrentSession();
		   
		Query<Order> query = session.createQuery(stmt, Order.class);
		List<Order> results = query.getResultList();
		return results;
	}

	public List<Order> getSentOrders() {
		String stmt = "SELECT o FROM Order o join o.actualState os WHERE os.status='Sent'";
		Session session = sessionFactory.getCurrentSession();
		   
		Query<Order> query = session.createQuery(stmt, Order.class);
		List<Order> results = query.getResultList();
		return results;
	}

}
