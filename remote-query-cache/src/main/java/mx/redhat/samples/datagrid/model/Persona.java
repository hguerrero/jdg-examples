package mx.redhat.samples.datagrid.model;

import java.io.Serializable;

import org.infinispan.protostream.annotations.ProtoDoc;
import org.infinispan.protostream.annotations.ProtoField;

@ProtoDoc("@Indexed")
public class Persona implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private String persona;
	private String nombre;
	private String apellido_paterno;
	private String apellido_materno;
	private String rfc;
//	private Date fecha_nacimiento;
	
	public Persona() {}
	
	public Persona(String persona, 
			String nombre, 
			String apellido_paterno, 
			String apellido_materno, 
			String rfc) 
	{
		this.persona = persona;
		this.nombre = nombre;
		this.apellido_paterno = apellido_paterno;
		this.apellido_materno = apellido_materno;
		this.rfc = rfc;
	}
	
	@ProtoField(number = 1, required = true)
	public String getPersona() {
		return persona;
	}
	public void setPersona(String persona) {
		this.persona = persona;
	}
	@ProtoDoc("@Field(analyze = Analyze.YES, store = Store.NO, norms = Norms.NO, termVector = TermVector.NO)")
	@ProtoField(number = 2, required = true)
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	@ProtoDoc("@Field(analyze = Analyze.YES, store = Store.NO, norms = Norms.NO, termVector = TermVector.NO)")
	@ProtoField(number = 3, required = true)
	public String getApellidoPaterno() {
		return apellido_paterno;
	}
	public void setApellidoPaterno(String apellido_paterno) {
		this.apellido_paterno = apellido_paterno;
	}
	@ProtoDoc("@Field(analyze = Analyze.YES, store = Store.NO, norms = Norms.NO, termVector = TermVector.NO)")
	@ProtoField(number = 4, required = true)
	public String getApellidoMaterno() {
		return apellido_materno;
	}
	public void setApellidoMaterno(String apellido_materno) {
		this.apellido_materno = apellido_materno;
	}
	@ProtoDoc("@Field(analyze = Analyze.YES, store = Store.NO, norms = Norms.NO, termVector = TermVector.NO)")
	@ProtoField(number = 5, required = true)
	public String getRFC() {
		return rfc;
	}
	public void setRFC(String rfc) {
		this.rfc = rfc;
	}

}
