package cl.uach.info090.ContrerasFrancisco;

import javax.swing.JButton;
import java.util.ArrayList;
/**
 * La clase representa a una mesa.
 * Cada mesa tiene un identificador, una lista de ítems consumidos y un creador de boletas.
 *  @author Francisco Contreras Ramos
 *  
 */
@SuppressWarnings("serial")
public class Mesa extends JButton {
	private int id;
	private CreadorBoleta creadorBoleta;
	private ArrayList<ItemConsumo> items;
	
	/**
	 * // Constructor que inicializa id, creadorBoleta y el ArrayList de items
	 * @param id identificador de mesa
	 * @param creadorBoleta El creador de boletas asociado a la mesa.
	 */
	
	public Mesa(int id, CreadorBoleta creadorBoleta) {
		this.id = id;
		this.creadorBoleta = creadorBoleta;
		this.items = new ArrayList<>();
		setText("Mesa " + id); // Texto en el botón
	}
	/**
	 * Método para agregar un item a la lista de consumo
	 * Si el ítem ya existe, se incrementa su cantidad.
	 * 
	 * @param item El ítem de consumo a agregar a la mesa.
	 */

	public void agregarItem(ItemConsumo item) {
	    boolean itemExistente = false;
	    for (ItemConsumo existente : items) {
	        if (existente.getId().equals(item.getId())) {
	            // Incrementar cantidad del ítem existente
	            existente.incrementarCantidad(); // Método en ItemConsumo
	            itemExistente = true;
	            break;
	        }
	    }

	    if (!itemExistente) {
	        items.add(item.clone());
	    }
	}

	

	public int getId() {
		return id;
	}

	public CreadorBoleta getCreadorBoleta() {
		return creadorBoleta;
	}

    /**
     * Método para quitar un item de la lista de consumo.
     *
     * @param item El ítem de consumo a quitar de la mesa.
     */
	public void quitarItem(ItemConsumo item) {
		items.remove(item);
	}

	// Getter para obtener la lista de items
	public ArrayList<ItemConsumo> getItems() {
		return items;
	}
	/**
	 * Verifica si la mesa tiene pedidos (si la lista de ítems no está vacía).
	 * @return devuelve true si la mesa tiene pedidos (lista no vacía)
	 */
	
	public boolean enUso() {
		return !items.isEmpty();
	}
	/**
	 * Método cerrarMesa que genera una boleta y vacía la lista de items
	 * @return La boleta generada al cerrar la mesa.
	 */

	public Boleta cerrarMesa() {
		// Crear el detalle del consumo como un String
		StringBuilder detalle = new StringBuilder();
		for (ItemConsumo item : items) {
			detalle.append(item.getNombre()).append(" - $").append(item.getPrecio()).append("\n");
		}
		// Calcular el valor neto del consumo
		double neto = calcularTotalConsumo();
		// Generar la boleta usando el creadorBoleta
		Boleta boleta = creadorBoleta.generarBoleta("Mesa " + id, detalle.toString(), neto);
		
		// Vaciar la lista de items después de generar la boleta
		items.clear();

		return boleta;
	}
	/**
     * Método auxiliar para calcular el total del consumo en la mesa.
     *
     * @return El total del consumo.
     */
	public double calcularTotalConsumo() {
		double total = 0;
		for (ItemConsumo item : items) {
			total += item.getPrecio();
		}
		return total;
	}
}
