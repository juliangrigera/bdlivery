package ar.edu.unlp.info.bd2.model;

import javax.persistence.*; //Utilizo la persistencia de JPA no hibernate...

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;


@Entity
@Table(name="users")
public class User {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="userId")
    private Long id;
	
	@Column(name="email",unique=true, nullable=false)
    private String email;
	
	@Column(name="password", nullable=false)
    private String password;
	
	@Column(name="username",unique=true, nullable=false)
    private String username;

    @Column(name="name", nullable=false)
    private String name;
    
    @Column(name="dateOfBirth", nullable=false)
    private Date dateOfBirth;

    
    
   
    public User() {}
    
    public User(String email, String password, String username, String name, Date dateOfBirth) {
    	this.email = email;
    	this.password = password;
    	this.username = username;
    	this.name = name;
    	this.dateOfBirth = dateOfBirth;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	
    

}
