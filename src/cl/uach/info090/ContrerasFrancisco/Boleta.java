package cl.uach.info090.ContrerasFrancisco;
/**
 * Clase abstracta que representa una boleta en el sistema de consumo.
 * Esta clase proporciona un método abstracto detalle() que debe ser implementado
 * por las clases concretas que extiendan Boleta para proporcionar detalles específicos
 * de la boleta generada.
 * 
 * @author Francisco Contreras
 */
public abstract class Boleta {
	
	/**
     * Método abstracto que debe ser implementado por las subclases para proporcionar
     * el detalle de la boleta.
     *
     * @return Un String que contiene el detalle de la boleta.
     */
	public abstract String detalle();
}
