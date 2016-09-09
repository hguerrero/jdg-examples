package mx.redhat.datagrid.demo.websample.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.codehaus.jackson.annotate.JsonManagedReference;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.ProvidedId;
import org.hibernate.search.annotations.Store;


/**
 * The persistent class for the country database table.
 * 
 */
@Entity
@Indexed
@ProvidedId
public class Country implements Serializable {
	private static final long serialVersionUID = 1L;
	@Field(store = Store.YES)
	private String code;
	private String code2;
	@Field(store = Store.YES)
	private String continent;
	private BigDecimal gnp;
	private BigDecimal gnpold;
	@Field(store = Store.YES)
	private String governmentform;
	@Field(store = Store.YES)
	private String headofstate;
	@Field(store = Store.YES)
	private Integer indepyear;
	private Float lifeexpectancy;
	@Field(store = Store.YES)
	private String localname;
	@Field(store = Store.YES)
	private String name;
	private Integer population;
	@Field(store = Store.YES)
	private String region;
	private Float surfacearea;
	@IndexedEmbedded
	private City city;

	public Country() {
	}


	@Id
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}


	public String getCode2() {
		return this.code2;
	}

	public void setCode2(String code2) {
		this.code2 = code2;
	}


	public String getContinent() {
		return this.continent;
	}

	public void setContinent(String continent) {
		this.continent = continent;
	}


	public BigDecimal getGnp() {
		return this.gnp;
	}

	public void setGnp(BigDecimal gnp) {
		this.gnp = gnp;
	}


	public BigDecimal getGnpold() {
		return this.gnpold;
	}

	public void setGnpold(BigDecimal gnpold) {
		this.gnpold = gnpold;
	}


	public String getGovernmentform() {
		return this.governmentform;
	}

	public void setGovernmentform(String governmentform) {
		this.governmentform = governmentform;
	}


	public String getHeadofstate() {
		return this.headofstate;
	}

	public void setHeadofstate(String headofstate) {
		this.headofstate = headofstate;
	}


	public Integer getIndepyear() {
		return this.indepyear;
	}

	public void setIndepyear(Integer indepyear) {
		this.indepyear = indepyear;
	}


	public Float getLifeexpectancy() {
		return this.lifeexpectancy;
	}

	public void setLifeexpectancy(Float lifeexpectancy) {
		this.lifeexpectancy = lifeexpectancy;
	}


	public String getLocalname() {
		return this.localname;
	}

	public void setLocalname(String localname) {
		this.localname = localname;
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


	public String getRegion() {
		return this.region;
	}

	public void setRegion(String region) {
		this.region = region;
	}


	public Float getSurfacearea() {
		return this.surfacearea;
	}

	public void setSurfacearea(Float surfacearea) {
		this.surfacearea = surfacearea;
	}


	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="capital")
	@JsonManagedReference
	public City getCity() {
		return this.city;
	}

	public void setCity(City city) {
		this.city = city;
	}

}