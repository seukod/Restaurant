package cl.uach.info090.ContrerasFrancisco;

public class Bebestible extends ItemConsumo{

	public Bebestible(String id, String nombre, double precio) {
		super(id, nombre, precio);
		// TODO Auto-generated constructor stub
	}
	 @Override
	    public Bebestible clone() {
	        // Llamamos a super.clone() para duplicar el objeto
	        return new Bebestible(this.getId(),this.getNombre(), this.getPrecio());
	    }
}
