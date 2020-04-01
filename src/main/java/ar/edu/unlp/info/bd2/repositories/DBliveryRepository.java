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
import java.util.stream.Collectors;


public class DBliveryRepository {
	
	@Autowired
	SessionFactory sessionFactory;
	
	public User storeUser(User user){
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


	@SuppressWarnings("unchecked")
	public List<Product> getProductByName(String name) {
	    Session session = sessionFactory.getCurrentSession();
	    String stmt = "SELECT p FROM Product p WHERE p.name like ?1";
	    Query<Product> query = session.createQuery(stmt);
	    query.setParameter(1, "%"+name+"%");
		List<Product> results = query.getResultList();
	    return results;
	}
	
	
}
