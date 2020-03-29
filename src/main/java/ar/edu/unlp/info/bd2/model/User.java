package ar.edu.unlp.info.bd2.model;

import javax.persistence.*; //Utilizo la persistencia de JPA no hibernate...
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Entity
@Table(name="users")
public class User {
	
	@Column(name="username",unique=true, nullable=false)
    private String username;

    @Column(nullable=false)
    private String name;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="userId")
    private Long id;
    
    @Column(name="email", nullable=false, unique=true)
    private String email;
    
    @Column(name="dni", nullable=false, unique=true)
    private int dni;
    
    public User() {}
    
    public User(String username, String name) {
    	this.username = username;
    	this.name = name;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Object getEmail() {
		// TODO Auto-generated method stub
		return null;
	}
    
    

}
