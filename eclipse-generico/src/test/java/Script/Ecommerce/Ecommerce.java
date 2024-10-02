package Script.Ecommerce;

import java.util.Hashtable;

import Business.Amdocs;

public class Ecommerce {
	

	Business.Operaciones Oper  = new  Business.Operaciones();
	Business.Amdocs      Amdocs= new  Business.Amdocs();
	
	public void Prepago(Hashtable<String, String> Data) throws Exception
	{
		
		String Plan = Data.get("Plan");
		String Rut  = Data.get("Rut");
		Amdocs.IniciarAmdocs();
		Oper.BuscarPersonaExistente(Rut,"Rut");
		Oper.ValidarClienteEcommerce(Data);
		
	
	}

}
