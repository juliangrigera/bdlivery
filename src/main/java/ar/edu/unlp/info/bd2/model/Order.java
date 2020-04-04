package ar.edu.unlp.info.bd2.model;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="orderId")
	private Long id;
	
	@Column(name="dateOfOrder")
	private Date dateOfOrder;
	
	@Column(name="address")
	private String address;
	
	@Column(name="coordX")
	private Float coordX;
	
	@Column(name="coordY")
	private Float coordY;
	
	@OneToOne()
	@JoinColumn(name = "clientId")
	private User client;
	
	@OneToOne()
	@JoinColumn(name = "deliveryId")
	private User deliveryUser;
	
	@OneToOne(cascade = CascadeType.ALL) /*Cuando borro la orden deberia borrar sus estados*/
	@JoinColumn(name = "actualStatusId")
	private OrderStatus actualState;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "orderId")
	private List<ProductOrder> productOrders = new ArrayList<ProductOrder>();
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "orderId")
	private List<OrderStatus> collectionOrderStatus = new ArrayList<OrderStatus>();
	

	/*--------------------------------------------------------------*/
	
	public Order() {}
	
	public Order(Date dateOfOrder, String address, Float coordX, Float coordY, User client) {
		this.dateOfOrder = dateOfOrder;
		this.address = address;
		this.coordX = coordX;
		this.coordY = coordY;
		this.client = client;
		
		this.deliveryUser = null;
		this.actualState = new OrderStatus("Pending");
		this.productOrders = new ArrayList<>();
		this.collectionOrderStatus = new ArrayList<>();
		this.addOrderStatus(this.actualState); // q te parece??
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDateOfOrder() {
		return dateOfOrder;
	}

	public void setDateOfOrder(Date dateOfOrder) {
		this.dateOfOrder = dateOfOrder;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Float getCoordX() {
		return coordX;
	}

	public void setCoordX(Float coordX) {
		this.coordX = coordX;
	}

	public Float getCoordY() {
		return coordY;
	}

	public void setCoordY(Float coordY) {
		this.coordY = coordY;
	}
	
	public Object getClient() {
		return this.client;
	}

	public void setClient(User client) {
		this.client = client;
	}
	
	public User getDeliveryUser() {
		return deliveryUser;
	}

	public void setDeliveryUser(User deliveryUser) {
		this.deliveryUser = deliveryUser;
	}
	
	public OrderStatus getActualState() {
		return actualState;
	}

	public void setActualState(OrderStatus actualState) {
		this.actualState = actualState;
	}
	
	public List<ProductOrder> getProducts() { //Diferente al set por como esta en DBliveryServiceTestCase
		return productOrders;
	}

	public void setProductOrders(List<ProductOrder> productOrders) {
		this.productOrders = productOrders;
	}
	
	public void addProductOrder(ProductOrder po) {
		this.productOrders.add(po);
	}
	
	public List<OrderStatus> getStatus() { //Diferente al set por como esta en DBliveryServiceTestCase
		return collectionOrderStatus;
	}

	public void setCollectionOrderStatus(List<OrderStatus> collectionOrderStatus) {
		this.collectionOrderStatus = collectionOrderStatus;
	}
	
	public void addOrderStatus(OrderStatus orderStatus) {
		//FALTA IMPLEMENTAR! ..CONTROLAR! Si no es pending no puedo cancelar!
		
		this.collectionOrderStatus.add(orderStatus);
	}
	
	/*--------------------------------------------------*/


}
