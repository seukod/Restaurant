package cl.uach.info090.ContrerasFrancisco;

public class Comida extends ItemConsumo{

	public Comida(String id, String nombre, double precio) {
		super(id, nombre, precio);
		// TODO Auto-generated constructor stub
	}
	 @Override
	    public Comida clone() {
	        // Llamamos a super.clone() para duplicar el objeto
	        return new Comida(this.getId(),this.getNombre(), this.getPrecio());
	    }
}
