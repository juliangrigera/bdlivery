package ar.edu.unlp.info.bd2.model;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

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
	
	@Column(name="amount")
	private Float amount;
	
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
	@OrderBy("startDate")
	@JoinColumn(name = "orderId")
	private List<OrderStatus> collectionOrderStatus = new ArrayList<OrderStatus>();
	

	/*--------------------------------------------------------------*/
	
	public Order() {}
	
	public Order(Date dateOfOrder, String address, Float coordX, Float coordY, User client) {
		this.dateOfOrder = dateOfOrder;
		this.address = address;
		this.coordX = coordX;
		this.coordY = coordY;
		this.amount = (float) 0;
		this.client = client;
		
		this.deliveryUser = null;
		this.actualState = new OrderStatus("Pending", this, dateOfOrder); //NUEVO! NO LO TENIAMOS EN CUENTA!
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
		for(int i= 0; i < productOrders.size(); i++){
			this.setAmountPO(productOrders.get(i));
		}
		
	}
	
	public void addProductOrder(ProductOrder po) {
		this.productOrders.add(po);
		this.setAmountPO(po);
	}
	
	public void setAmountPO(ProductOrder po) {
		Float price=po.getProduct().getPriceAt(getDateOfOrder()) * po.getQuantity();
		if (price == 0F) {
			price = po.getProduct().getPrice() * po.getQuantity();
		}
		this.amount = this.amount + price;
	}
	
	public List<OrderStatus> getStatus() { //Diferente al set por como esta en DBliveryServiceTestCase
		return collectionOrderStatus;
	}

	public void setCollectionOrderStatus(List<OrderStatus> collectionOrderStatus) {
		this.collectionOrderStatus = collectionOrderStatus;
	}
	
	public void addOrderStatus(OrderStatus orderStatus) {
		
		this.collectionOrderStatus.add(orderStatus);
		this.actualState = orderStatus;
		
	}
	
	public void addOrderStatus(OrderStatus orderStatus, Date date) {
		if(this.collectionOrderStatus.size() >= 1) {
			OrderStatus lastOrderStatus = this.collectionOrderStatus.get(this.collectionOrderStatus.size()-1);
			lastOrderStatus.setEndDate(date); //Falta volver a agregar al array?
		}
		this.actualState = orderStatus;
		
		this.collectionOrderStatus.add(orderStatus);
		
	}
	
	public void addOrderStatus(String status, Date date) {
		OrderStatus os = new OrderStatus(status, this, date);
		
		if(this.collectionOrderStatus.size() >= 1) {
			OrderStatus lastOrderStatus = this.collectionOrderStatus.get(this.collectionOrderStatus.size()-1);
			lastOrderStatus.setEndDate(date); //Falta volver a agregar al array?
		}
		this.actualState = os;
		
		this.collectionOrderStatus.add(os);
		
	}
	
	public Float getAmount() {
		return this.amount;
	}

	public Float calcularPrecioTotal() {
		Float total = 0F;
		for(int i=0; i < this.productOrders.size(); i++) {
			total =(float) (total + (this.getProducts().get(i).getProduct().getPriceAt(getDateOfOrder()) ) * ( this.getProducts().get(i).getQuantity()));
		}
		return total;
		
	}
	
	/*--------------------------------------------------*/


}
