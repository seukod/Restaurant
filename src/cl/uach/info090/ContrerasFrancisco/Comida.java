package cl.uach.info090.ContrerasFrancisco;
/**
 * Clase que representa un ítem de tipo Comida en el sistema de consumo.
 * Hereda de la clase ItemConsumo y permite crear objetos que representan alimentos.
 * 
 * @author Francisco Contreras
 */
public class Comida extends ItemConsumo{
	/**
     * Constructor de la clase Comida.
     *
     * @param id El identificador de la comida.
     * @param nombre El nombre de la comida.
     * @param precio El precio unitario de la comida.
     */
	public Comida(String id, String nombre, double precio) {
		super(id, nombre, precio);
		// TODO Auto-generated constructor stub
	}
	/**
     * Clona el objeto Comida, creando una copia con la misma información.
     *
     * @return Una nueva instancia de Comida con los mismos atributos.
     */
	 @Override
	    public Comida clone() {
	        // Llamamos a super.clone() para duplicar el objeto
	        return new Comida(this.getId(),this.getNombre(), this.getPrecio());
	    }
}
