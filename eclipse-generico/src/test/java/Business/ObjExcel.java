package Business;

import java.util.Date;

public class ObjExcel {
	private String ID;
	private String Caso;
	private String Ciclo;
	private String TipoCliente;
	private String SubTipoCliente;
	private String NombrePlan;
	private String Rut;
	private String PerfilAPN;
	private String SkuEquipo;
	private String Telefono;
	private String PlanBase;
	private String SubProducto;
	private String Tipo;
	private String ServicioAdicional;
	private String Play1;
	private String Play2;
	private String TipoAdquisicion;
	private String Play3;
	
	public String getTipoAdquisicion() {
		return TipoAdquisicion;
	}
	public void setTipoAdquisicion(String tipoAdquisicion) {
		TipoAdquisicion = tipoAdquisicion;
	}
	
	public ObjExcel() {}
	public ObjExcel(String ID,String Caso,String Ciclo,String TipoCliente,
			String SubTipoCliente,String NombrePlan,String Rut,String PerfilAPN,
			String SkuEquipo,String Telefono,String PlanBase,String SubProducto,
			String Tipo,String ServicioAdicional,String Play1,String PLay2, String Play3)
	{
		super();
		this.ID=ID;
		this.Caso=Caso;
		this.Ciclo=Ciclo;
		this.TipoCliente=TipoCliente;
		this.SubTipoCliente=SubTipoCliente;
		this.NombrePlan=NombrePlan;
		this.Rut=Rut;
		this.PerfilAPN=PerfilAPN;
		this.SkuEquipo=SkuEquipo;
		this.Telefono=Telefono;
		this.PlanBase=PlanBase;
		this.SubProducto=SubProducto;
		this.Tipo=Tipo;
		this.ServicioAdicional=ServicioAdicional;
		this.Play1=Play1;
		this.Play2=Play2;
		this.Play3=Play3;
	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getCaso() {
		return Caso;
	}
	public void setCaso(String caso) {
		Caso = caso;
	}
	public String getCiclo() {
		return Ciclo;
	}
	public void setCiclo(String ciclo) {
		Ciclo = ciclo;
	}
	public String getTipoCliente() {
		return TipoCliente;
	}
	public void setTipoCliente(String tipoCliente) {
		TipoCliente = tipoCliente;
	}
	public String getSubTipoCliente() {
		return SubTipoCliente;
	}
	public void setSubTipoCliente(String subTipoCliente) {
		SubTipoCliente = subTipoCliente;
	}
	public String getNombrePlan() {
		return NombrePlan;
	}
	public void setNombrePlan(String nombrePlan) {
		NombrePlan = nombrePlan;
	}
	public String getRut() {
		return Rut;
	}
	public void setRut(String rut) {
		Rut = rut;
	}
	public String getPerfilAPN() {
		return PerfilAPN;
	}
	public void setPerfilAPN(String perfilAPN) {
		PerfilAPN = perfilAPN;
	}
	public String getSkuEquipo() {
		return SkuEquipo;
	}
	public void setSkuEquipo(String skuEquipo) {
		SkuEquipo = skuEquipo;
	}
	public String getTelefono() {
		return Telefono;
	}
	public void setTelefono(String telefono) {
		Telefono = telefono;
	}
	public String getPlanBase() {
		return PlanBase;
	}
	public void setPlanBase(String planBase) {
		PlanBase = planBase;
	}
	public String getSubProducto() {
		return SubProducto;
	}
	public void setSubProducto(String subProducto) {
		SubProducto = subProducto;
	}
	public String getTipo() {
		return Tipo;
	}
	public void setTipo(String tipo) {
		Tipo = tipo;
	}
	public String getServicioAdicional() {
		return ServicioAdicional;
	}
	public void setServicioAdicional(String servicioAdicional) {
		ServicioAdicional = servicioAdicional;
	}
	public String getPlay1() {
		return Play1;
	}
	public void setPlay1(String play1) {
		Play1 = play1;
	}
	public String getPlay2() {
		return Play2;
	}
	public void setPlay2(String play2) {
		Play2 = play2;
	}
	
	public String getPlay3() {
		return Play3;
	}
	public void setPlay3(String play3) {
		Play3 = play3;
	}
	

}
