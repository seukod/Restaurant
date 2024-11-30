package cl.uach.info090.ContrerasFrancisco;

import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * Clase que representa una boleta en el sistema de pedidos.
 * Incluye información sobre la fecha, detalle del cobro, valor neto,
 * porcentaje de propina y valor total.
 * 
 * @author Francisco Contreras
 */
public class BoletaCL extends Boleta{
	private Date fecha;
    private String detalleCobro;
    private double valorNeto;
    private double porcentajePropina;
    private double valorTotal;
    /**
     * 
     * @param fecha La fecha de la boleta
     * @param detalleCobro Detalle del cobro
     * @param valorNeto Valor neto del consumo
     * @param porcentajePropina Porcentaje de propina
     */
    public BoletaCL(Date fecha, String detalleCobro, double valorNeto, double porcentajePropina) {
		this.fecha = fecha;
        this.detalleCobro = detalleCobro;
        this.valorNeto = valorNeto;
        this.porcentajePropina = porcentajePropina;
        this.valorTotal = valorNeto + (valorNeto * (porcentajePropina / 100));
    }
    /**
     * Devuelve una representación en cadena de la boleta,
     * incluyendo la fecha y el valor total.
     *
     * @return La representación en cadena de la boleta.
     */
	public String toString() {
		SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy  HH:mm");
		String fechaString = formatoFecha.format(this.fecha);
		
		return fechaString + "\t$ " + String.format("%.0f", this.valorTotal);
	}
	
	 /**
     * Devuelve el detalle completo de la boleta,
     * incluyendo fecha, detalle del cobro, valor neto,
     * propina y valor total.
     *
     * @return Detalle de la boleta como cadena.
     */
	@Override
	public String detalle() {
	    SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy\tHH:mm");
	    StringBuilder sb = new StringBuilder();
	    
	    sb.append("---------- BOLETA -----------\n");
	    sb.append("Fecha: ").append(formatoFecha.format(fecha)).append("\n");
	    sb.append("Detalle del cobro: ").append(detalleCobro).append("\n");
	    sb.append("Valor neto: $").append(String.format("%.0f", valorNeto)).append("\n");
	    sb.append("Propina (").append(porcentajePropina).append("%): $")
	      .append(String.format("%.0f", valorNeto * (porcentajePropina / 100))).append("\n");
	    sb.append("Valor total: $").append(String.format("%.0f", valorTotal)).append("\n");
	    sb.append("-----------------------------\n");
	    
	    return sb.toString();
	}
	
}
