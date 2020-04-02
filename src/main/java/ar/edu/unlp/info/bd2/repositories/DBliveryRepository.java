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

	@SuppressWarnings("unchecked")
	public List<Product> getProductByName(String name) {
		Session session = sessionFactory.getCurrentSession();
		String stmt = "SELECT p FROM Product p WHERE p.name like ?1";
		Query<Product> query = session.createQuery(stmt);
		query.setParameter(1, "%" + name + "%");
		List<Product> results = query.getResultList();
		return results;
	}

	public Product updateProductPrice(Long id, Float price, Date startDate) {
		// TODO Auto-generated method stub
		this.sessionFactory.getCurrentSession();
		return null;
	}

	public Optional<Product> getProductByid(Long id) {
		// TODO Auto-generated method stub
		@SuppressWarnings("unchecked")
		Optional<Product> uniqueResultOptional = this.sessionFactory.getCurrentSession().createQuery("from Product where id = '" + id + "'").uniqueResultOptional();
		return uniqueResultOptional;
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
		@SuppressWarnings("unchecked")
		Optional<User> uniqueResultOptional = this.sessionFactory.getCurrentSession().createQuery("from User where id = '" + id + "'").uniqueResultOptional();
		return uniqueResultOptional; 
	}
	
	public Optional<Order> getOrderById(Long id){
		@SuppressWarnings("unchecked")
		Optional<Order> uniqueResultOptional = this.sessionFactory.getCurrentSession().createQuery("from Order where id = '" + id + "'").uniqueResultOptional();
		return uniqueResultOptional;
		
	}

}
