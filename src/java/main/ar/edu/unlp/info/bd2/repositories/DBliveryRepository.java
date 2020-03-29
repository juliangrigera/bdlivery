package ar.edu.unlp.info.bd2.repositories;

import ar.edu.unlp.info.bd2.model.*;
import org.hibernate.*;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


public class DBliveryRepository {
	
	SessionFactory sessionFactory;
	
	public User storeUser(User user){
	    sessionFactory.getCurrentSession().save(user);
	    return user;
	}
	
	/*
	public static void main (String [] args) {
		
		User u1 = new User("greco", "lucas");
		
		String userName = u1.getUsername();
		
		System.out.println(userName);
	}
	
	*/
}
