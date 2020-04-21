package ar.edu.unlp.info.bd2.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="supplier")
public class Supplier {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="supplierId")
    private Long id;
	
	@Column(name="name", unique=true, nullable=false)
	private String name;
	
	@Column(name="cuil", unique=true, nullable=false)
	private String cuil;
	
	@Column(name="address", nullable=false)
	private String address;

	@Column(name="coordX", nullable=false)
	private Float coordX;
	
	@Column(name="coordY", nullable=false)
	private Float coordY;
	
	public Supplier() {}
	
	public Supplier(String name, String cuil, String address, Float coordX, Float coordY) {
		// TODO Auto-generated constructor stub
		this.name = name;
		this.cuil = cuil;
		this.address = address;
		this.coordX = coordX;
		this.coordY = coordY;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getCuil() {
		return cuil;
	}


	public void setCuil(String cuil) {
		this.cuil = cuil;
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


	public Long getId() {
		return id;
	}

}
