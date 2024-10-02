package Business;

public class ObjEPC {
	private String offer_id;
	private String nombre;
	
	
	
	
	public ObjEPC() {
		super();
		// TODO Auto-generated constructor stub
	}




	public ObjEPC(String offer_id, String nombre) {
		super();
		this.offer_id = offer_id;
		this.nombre = nombre;
	}




	public String getOffer_id() {
		return offer_id;
	}




	public void setOffer_id(String offer_id) {
		this.offer_id = offer_id;
	}




	public String getNombre() {
		return nombre;
	}




	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	
	
}
