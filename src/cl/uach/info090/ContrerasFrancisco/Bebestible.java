package cl.uach.info090.ContrerasFrancisco;
/**
 * Clase que representa un ítem de tipo Bebestible en el sistema de consumo.
 * Hereda de la clase ItemConsumo y permite crear objetos que representan bebidas.
 * 
 * @author Francisco Contreras
 */
public class Bebestible extends ItemConsumo{
	/**
     * Constructor de la clase Bebestible.
     *
     * @param id El identificador del bebible.
     * @param nombre El nombre del bebible.
     * @param precio El precio unitario del bebible.
     */
	public Bebestible(String id, String nombre, double precio) {
		super(id, nombre, precio);
	}
	/**
     * Clona el objeto Bebestible, creando una copia con la misma información.
     *
     * @return Una nueva instancia de Bebestible con los mismos atributos.
     */
	 @Override
	    public Bebestible clone() {
	        // Llamamos a super.clone() para duplicar el objeto
	        return new Bebestible(this.getId(),this.getNombre(), this.getPrecio());
	    }
}
