package cl.uach.info090.ContrerasFrancisco;
/**
 * Clase que representa un ítem de consumo en el sistema.
 * Cada ítem tiene un ID, nombre, precio y cantidad.
 * Permite incrementar la cantidad y clonar el ítem.
 * 
 * @author Francisco Contreras
 */
public class ItemConsumo {
    private String id;
    private String nombre;
    private double precio;
    private int cantidad;
    /**
     * Constructor de la clase ItemConsumo.
     *
     * @param id El identificador del ítem.
     * @param nombre El nombre del ítem.
     * @param precio El precio unitario del ítem.
     */
    public ItemConsumo(String id, String nombre, double precio) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = 1; // Por defecto, inicia con una cantidad de 1
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public int getCantidad() {
        return cantidad;
    }
    /**
     * Incrementa la cantidad del ítem en 1.
     */
    public void incrementarCantidad() {
        this.cantidad += 1;
    }
    /**
     * Clona el ítem de consumo, creando una copia con la misma información.
     *
     * @return Una nueva instancia de ItemConsumo con los mismos atributos.
     */
    @Override
    public ItemConsumo clone() {
        ItemConsumo copia = new ItemConsumo(getId(), getNombre(), getPrecio());
        copia.cantidad = this.cantidad; // Mantener la cantidad en la copia
        return copia;
    }
}