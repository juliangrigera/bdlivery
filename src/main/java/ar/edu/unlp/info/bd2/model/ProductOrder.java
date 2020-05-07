package ar.edu.unlp.info.bd2.model;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "productOrder")
public class ProductOrder {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="productOrderId")
	private	Long id;
	
	@Column(name = "quantity", nullable = false)
	private Long quantity;
	
	@OneToOne()
	@JoinColumn(name = "productId")
	private Product product;
	
	public ProductOrder(){
		
	}
	
	public ProductOrder(Long quantity, Product product) {
		this.quantity = quantity;
		this.product = product;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
	
	
	
}
