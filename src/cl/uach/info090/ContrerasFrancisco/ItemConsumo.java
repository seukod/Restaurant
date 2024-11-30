package cl.uach.info090.ContrerasFrancisco;

public class ItemConsumo {
    private String id;
    private String nombre;
    private double precio;
    private int cantidad;

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

    public void incrementarCantidad() {
        this.cantidad += 1;
    }

    @Override
    public ItemConsumo clone() {
        ItemConsumo copia = new ItemConsumo(getId(), getNombre(), getPrecio());
        copia.cantidad = this.cantidad; // Mantener la cantidad en la copia
        return copia;
    }
}