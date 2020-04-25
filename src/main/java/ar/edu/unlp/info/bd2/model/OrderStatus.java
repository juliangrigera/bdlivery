package ar.edu.unlp.info.bd2.model;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.Date;
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
	
	@Column(name = "startDate")
	private Date startDate;
	
	@Column(name = "endDate")
	private Date endDate;
	
	@ManyToOne
	@JoinColumn(name = "orderId")
	private Order ord;
	
	public OrderStatus() {}
	
	public OrderStatus(String status) {
		this.status = status;
		this.startDate = null;
		this.endDate = null;
		this.ord = null; //ESTO NO SE SI ESTA BIEN!!! sino habria q cambiar en todos esto
	}
	
	public OrderStatus(String status, Order ord, Date date) {
		this.status = status;
		this.startDate = date;
		this.endDate = null;
		this.ord = ord;
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

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	
	
	
	
}
