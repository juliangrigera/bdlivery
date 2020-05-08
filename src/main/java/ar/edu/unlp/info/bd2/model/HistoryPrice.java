package ar.edu.unlp.info.bd2.model;

import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name="historyPrice")
public class HistoryPrice {
	
	public HistoryPrice() {}

	public HistoryPrice(Float price) {
		this.setPrice(price);
		Date date = new Date();
		this.setStartDate(date);
		this.setEndDate(null);
		this.product = null; //contemplando el tp1
	}
	
	public HistoryPrice(Float price, Date date, Product product) {
		this.price = price;
		this.startDate = date;
		this.endDate = new Date(92233720368547L);
		this.product = product;
		
	}
	
	

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="historyPriceId")
    private Long id;
	
	@Column(name = "price", nullable = false)
    private Float price;
	
	@Column(name = "startDate", nullable = false)
	private Date startDate;
	
	@Column(name = "endDate")
	private Date endDate;
	
	@ManyToOne
	@JoinColumn(name = "productId")
	private Product product;

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Long getId() {
		return id;
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

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
	

}
