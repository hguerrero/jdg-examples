package mx.redhat.datagrid.demo.websample.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.codehaus.jackson.annotate.JsonBackReference;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.ProvidedId;
import org.hibernate.search.annotations.Store;


/**
 * The persistent class for the city database table.
 * 
 */
@Entity
@Indexed
@ProvidedId
public class City implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Country country;
	@Field(store = Store.YES)
	private String district;
	@Field(store = Store.YES)
	private String name;
	private Integer population;

	public City() {
	}


	@Id
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="countrycode")
	@JsonBackReference
	public Country getCountry() {
		return this.country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}


	public String getDistrict() {
		return this.district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}


	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public Integer getPopulation() {
		return this.population;
	}

	public void setPopulation(Integer population) {
		this.population = population;
	}

}