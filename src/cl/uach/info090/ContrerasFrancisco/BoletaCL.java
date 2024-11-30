package cl.uach.info090.ContrerasFrancisco;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BoletaCL extends Boleta{
	private Date fecha;
    private String detalleCobro;
    private double valorNeto;
    private double porcentajePropina;
    private double valorTotal;
    
    public BoletaCL(Date fecha, String detalleCobro, double valorNeto, double porcentajePropina) {
		this.fecha = fecha;
        this.detalleCobro = detalleCobro;
        this.valorNeto = valorNeto;
        this.porcentajePropina = porcentajePropina;
        this.valorTotal = valorNeto + (valorNeto * (porcentajePropina / 100));
    }
	public String toString() {
		SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy  HH:mm");
		String fechaString = formatoFecha.format(this.fecha);
		
		return fechaString + "\t$ " + String.format("%.0f", this.valorTotal);
	}

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
