package cl.uach.info090.ContrerasFrancisco;

import javax.swing.JButton;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class Mesa extends JButton {
	private int id;
	private CreadorBoleta creadorBoleta;
	private ArrayList<ItemConsumo> items;

	// Constructor que inicializa id, creadorBoleta y el ArrayList de items
	public Mesa(int id, CreadorBoleta creadorBoleta) {
		this.id = id;
		this.creadorBoleta = creadorBoleta;
		this.items = new ArrayList<>();
		setText("Mesa " + id); // Texto en el botón
	}

	// Método para agregar un item a la lista de consumo
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

	// Método para quitar un item de la lista de consumo
	public void quitarItem(ItemConsumo item) {
		items.remove(item);
	}

	// Getter para obtener la lista de items
	public ArrayList<ItemConsumo> getItems() {
		return items;
	}

	// Método enUso que devuelve true si la mesa tiene pedidos (lista no vacía)
	public boolean enUso() {
		return !items.isEmpty();
	}

	// Método cerrarMesa que genera una boleta y vacía la lista de items
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

	// Método auxiliar para calcular el total del consumo
	public double calcularTotalConsumo() {
		double total = 0;
		for (ItemConsumo item : items) {
			total += item.getPrecio();
		}
		return total;
	}
}
