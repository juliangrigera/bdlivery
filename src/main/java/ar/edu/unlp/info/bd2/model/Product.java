package ar.edu.unlp.info.bd2.model;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="product")
public class Product {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="productId")
    private Long id;
	
	@Column(name="name", unique=true, nullable=false)
	private String name;
	
	@Column(name="weigth", unique=true, nullable=false)
	private float weight;
	
	@Column(name="actualPrice", unique=true, nullable=false)
	private float actualPrice;
	
	@Column(name="name", unique=true, nullable=false)
	
	 @OneToMany(cascade = CascadeType.ALL,
     fetch = FetchType.LAZY,
     mappedBy = "product")
	 private List<historyPrice> historyPrice = new ArrayList<historyPrice>();

	public Object getId() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Product> getPrices() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getName() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getPrice() {
		// TODO Auto-generated method stub
		return null;
	}

}
