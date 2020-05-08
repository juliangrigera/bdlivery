package ar.edu.unlp.info.bd2.model;

import javax.persistence.*;

import ar.edu.unlp.info.bd2.repositories.DBliveryException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name="product")
public class Product {
	
	public Product() {}
	
	public Product(String name, Float price, Float weigth, Supplier supplier) {
		this.name = name;
		this.weigth = weigth;
		this.supplier = supplier;
		this.historyPrice = new ArrayList<>();
		Date date = new Date();
		this.addPrice(price, date); //CREO Q ACA NO HACE FALTA MANDAR THIS
		
	}
	
	public Product(String name, Float price, Float weigth, Supplier supplier, Date actualDate) {
		this.name = name;
		this.weigth = weigth;
		this.supplier = supplier;
		this.historyPrice = new ArrayList<>();
		Date date = actualDate;
		this.addPrice(price, date);
	}

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="productId")
    private Long id;
	
	@Column(name="name", nullable=false)
	private String name;
	
	@Column(name="weigth", nullable=false)
	private Float weigth;
	
	@Column(name="price", nullable=false)
	private Float price;
	
	@OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
	@OrderBy("startDate")
	@JoinColumn(name = "productId")
	private List<HistoryPrice> historyPrice = new ArrayList<>();
	
	@OneToOne
    @JoinColumn(name="supplierId")
    private Supplier supplier;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Float getWeigth() {
		return weigth;
	}

	public void setWeigth(Float weigth) {
		this.weigth = weigth;
	}

	public Float getPrice() {
		return price;
	}
	
	public Float getPriceAt(Date day) {
		Float pricesAt=0F;
		for (int i = 0; i< this.getPrices().size(); i++) {
			if(this.getPrices().get(i).getStartDate().before(day)) {
				pricesAt=this.getPrices().get(i).getPrice();
			}
		}
		return pricesAt;
	}

	public void setPrice(Float price) {
		this.price = price;
	}
	
	public void addPrice(Float price, Date date) {
		if(this.price != null) {
			HistoryPrice lastPrice = this.historyPrice.get(this.historyPrice.size()-1);
			lastPrice.setEndDate(date);
		}
		this.setPrice(price);
		HistoryPrice hp = new HistoryPrice(price, date, this);
		this.setHistoryPrice(hp);
	}
	
	public List<HistoryPrice> getPrices() {
		return historyPrice;
	}

	public void setHistoryPrice(HistoryPrice historyPrice) {
		this.historyPrice.add(historyPrice);
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public Long getId() {
		return id;
	}

}
