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
	
	
	public Supplier(String name, String cuil, String address, Float coordX, Float coordY) {
		// TODO Auto-generated constructor stub
		this.name = name;
		this.cuil = cuil;
		this.address = address;
		this.coordX = coordX;
		this.coordY = coordY;
	}

	public Object getId() {
		// TODO Auto-generated method stub
		return this.id;
	}

	public Object getName() {
		// TODO Auto-generated method stub
		return null;
	}

}
