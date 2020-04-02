package ar.edu.unlp.info.bd2.model;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name ="orderStatus")
public class OrderStatus {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="orderStatusId")
	private Long id;
	
	@Column(name = "status", nullable = false)
	private String status;
	
	public OrderStatus() {}
	
	public OrderStatus(String status) {
		this.status = status;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
	
}
