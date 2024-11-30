package cl.uach.info090.ContrerasFrancisco;
/**
 * Interfaz que define el contrato para la creación de boletas en el sistema.
 * Cualquier clase que implemente esta interfaz debe proporcionar una implementación
 * del método generarBoleta, que crea una boleta basada en los parámetros proporcionados.
 * 
 * @author Francisco Contreras
 */
public interface CreadorBoleta{
	/**
     * Genera una boleta para un consumo en una mesa específica.
     *
     * @param mesa El identificador de la mesa para la cual se genera la boleta.
     * @param detalle El detalle del consumo realizado en la mesa.
     * @param neto El valor neto del consumo.
     * @return Una instancia de Boleta que representa el consumo en la mesa.
     */
	  Boleta generarBoleta(String mesa, String detalle, double neto);
}
