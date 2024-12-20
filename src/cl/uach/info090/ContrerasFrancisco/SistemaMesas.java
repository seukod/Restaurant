package cl.uach.info090.ContrerasFrancisco;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Clase que representa el sistema de gestión de mesas en un restaurante.
 * Permite la selección de mesas, la adición de pedidos, y la generación de boletas.
 * 
 * @author Francisco Contreras
 */
@SuppressWarnings("serial")
public class SistemaMesas extends JFrame implements ActionListener {
	// Singleton instance
	private static SistemaMesas sistema = new SistemaMesas();

	// Componentes de la ventana
	private JPanel panelPrincipal;
	private JPanel panelMesas;
	private JLabel labelTitulo;
	private JButton[] botonesMesas;
	private JLabel subtotalLabel;
	private JTable tablaPedidos;
	private JPanel panelDer; // Panel derecho para mostrar el consumo
	private JLabel campoNumeroMesa; // Label para el número de mesa
	private DefaultTableModel modeloTabla; // Modelo de tabla para los pedidos
	private Mesa mesaSeleccionada;
	private Mesa[] mesas;

	Border borde = BorderFactory.createLineBorder(Color.BLACK, 3);
	/**
     * Constructor privado para el patrón Singleton.
     * Inicializa la ventana principal y sus componentes.
     */
	private SistemaMesas() {
		super(" Sistema de mesas ");
		try {
		    UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
		    e.printStackTrace();
		}
		

		setTitle("Sistema de Mesas");
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		// Inicializar y configurar componentes
		inicializarComponentes();
	}
	/**
     * Método para obtener la instancia Singleton del sistema de mesas.
     *
     * @return La instancia única de SistemaMesas.
     */
	public static SistemaMesas getInstance() {
		return sistema;
	}

	/**
     * Inicializa todos los componentes de la ventana principal.
     */
	private void inicializarComponentes() {
		panelPrincipal = new JPanel();
		panelPrincipal.setLayout(new BorderLayout());
		add(panelPrincipal);

		// Inicializar el título
		labelTitulo = new JLabel("MESAS", SwingConstants.CENTER);
		
		// Panel superior para título
		JPanel panelSuperior = new JPanel();
		panelSuperior.setLayout(new BorderLayout());
		panelSuperior.add(labelTitulo, BorderLayout.WEST);
		panelPrincipal.add(panelSuperior, BorderLayout.NORTH);

		// Panel de mesas
		panelMesas = new JPanel();
		panelMesas.setLayout(new GridLayout(4, 3, 20, 20)); // Ajustar filas y columnas según el número de mesas
		cargarBotonesMesas();
		panelPrincipal.add(panelMesas, BorderLayout.CENTER);

		// Inicializar el panel derecho (solo una vez)
		panelDer = new JPanel();
		panelDer.setLayout(new BorderLayout());
		panelDer.setBackground(Color.GRAY);

		// Crear tabla de pedidos y su modelo
		String[] columnas = { "Item", "valor/u", "Cantidad", "total" };
		modeloTabla = new DefaultTableModel(columnas, 0);
		tablaPedidos = new JTable(modeloTabla);
		JScrollPane scrollPane = new JScrollPane(tablaPedidos);
		panelDer.add(scrollPane, BorderLayout.CENTER);

		JPanel panelTitulo = new JPanel(new BorderLayout());
		campoNumeroMesa = new JLabel("Mesa seleccionada: ", SwingConstants.CENTER);
		panelTitulo.add(campoNumeroMesa, BorderLayout.WEST); // Cambiado a CENTER para que esté centrado
		panelDer.add(panelTitulo, BorderLayout.NORTH);

		// Botón "Agregar" en el panel derecho
		JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton botonAgregar = new JButton("Agregar");
		botonAgregar.addActionListener(this);
		panelBoton.add(botonAgregar);
		panelTitulo.add(panelBoton, BorderLayout.SOUTH);
		
		
		//agregar subtotal
		JPanel panelSubtotal = new JPanel();
		panelSubtotal.setLayout(new BorderLayout());
		subtotalLabel = new JLabel("Subtotal: $0.0", SwingConstants.RIGHT);
		subtotalLabel.setFont(new Font("Arial", Font.BOLD, 14));
		subtotalLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Margen interno
		panelSubtotal.add(subtotalLabel, BorderLayout.NORTH);
		panelDer.add(panelSubtotal, BorderLayout.SOUTH);
		
		
		// Botón "Cerrar" en el panel derecho
		JButton botonCerrar = new JButton("Cerrar pedido y generar boleta");
		botonCerrar.addActionListener(this);
		panelSubtotal.add(botonCerrar, BorderLayout.SOUTH);
				
	}
	/**
     * Carga los botones de las mesas en el panel de mesas.
     */
	 

	// Método para cargar los botones de mesas
	private void cargarBotonesMesas() {
		int cantidadMesas = 12; // Ejemplo de número de mesas
		botonesMesas = new JButton[cantidadMesas];
		mesas = new Mesa[cantidadMesas];
		CreadorBoleta creadorBoleta = new CreadorBoletaCL();

		for (int i = 0; i < cantidadMesas; i++) {
			botonesMesas[i] = new JButton("Mesa " + (i + 1));
			botonesMesas[i].setBackground(new Color(200, 150, 255));
			botonesMesas[i].setForeground(Color.WHITE);
			 
			mesas[i] = new Mesa(i, creadorBoleta);
			botonesMesas[i].addActionListener(this); // SistemaMesas actuará como listener
			panelMesas.add(botonesMesas[i]);
		}
	}
	 /**
     * Método main para ejecutar el programa
     *
     * @param args Argumentos de línea de comandos.
     */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			SistemaMesas.getInstance().setVisible(true);
		});
	}

	/**
     * Maneja los eventos de acción de los botones.
     *
     * @param e El evento de acción.
     */
	@Override
	public void actionPerformed(ActionEvent e) {
	    JButton botonPresionado = (JButton) e.getSource();

	    if (botonPresionado.getText().equals("Cerrar pedido y generar boleta")) {
	        if (mesaSeleccionada != null && mesaSeleccionada.enUso()) {
	            // a) Invoca al método cerrarMesa() de la mesa seleccionada, y captura la boleta generada
	            Boleta boleta = mesaSeleccionada.cerrarMesa();
	            JOptionPane.showMessageDialog(this, boleta.detalle(), "Boleta Generada", JOptionPane.INFORMATION_MESSAGE);
	            
	            // b) Guarda la boleta generada en la carpeta /boletas
	            guardarBoletaEnArchivo(boleta, mesaSeleccionada.getId());

	            // Reiniciar subtotal
	            subtotalLabel.setText("Subtotal: $0");
	            mostrarMesa(mesaSeleccionada); // Actualizar la vista de la mesa
	        } else {
	            JOptionPane.showMessageDialog(this, "No hay pedidos en la mesa seleccionada.", "Error", JOptionPane.ERROR_MESSAGE);
	        }
	    } else if (botonPresionado.getText().equals("Agregar")) {
	        abrirDialogoAgregarItem(); // Abrir el diálogo de selección de ítems
	    } else {
	        // Determinar la mesa seleccionada a partir del texto del botón
	        String textoBoton = botonPresionado.getText();
	        int numeroMesa = Integer.parseInt(textoBoton.split(" ")[1]) - 1;

	        mesaSeleccionada = mesas[numeroMesa]; // Guardar la mesa seleccionada
	        mostrarMesa(mesaSeleccionada); // Llamar al método mostrarMesa con la mesa seleccionada
	        System.out.println("Mesa seleccionada: " + textoBoton);
	    }
	}
	/**
     * Abre un diálogo para agregar un ítem a la mesa seleccionada.
     * Permite al usuario seleccionar un ítem de tipo Bebestible, Comida u Otro.
     */
	private void abrirDialogoAgregarItem() {
	    ArrayList<ItemConsumo> itemsDisponibles = cargarItemsDesdeArchivo("data/items_consumo.txt");

	    JDialog dialog = new JDialog(this, "Agregar Item", true);
	    dialog.setSize(300, 200);
	    dialog.setLocationRelativeTo(this);
	    dialog.setLayout(new BorderLayout());

	    JPanel panelContenido = new JPanel(new GridLayout(4, 2, 10, 10));

	    JComboBox<String> comboBebestibles = new JComboBox<>();
	    JComboBox<String> comboComidas = new JComboBox<>();
	    JComboBox<String> comboOtros = new JComboBox<>();

	    // Crear un modelo personalizado para el JComboBox
	    DefaultComboBoxModel<String> modeloBebestibles = new DefaultComboBoxModel<>();
	    DefaultComboBoxModel<String> modeloComidas = new DefaultComboBoxModel<>();
	    DefaultComboBoxModel<String> modeloOtros = new DefaultComboBoxModel<>();

	   
	    comboBebestibles.setModel(modeloBebestibles);
	    comboComidas.setModel(modeloComidas);
	    comboOtros.setModel(modeloOtros);

	    // Asignamos color blanco a los JComboBox inicialmente
	    comboBebestibles.setBackground(Color.WHITE);
	    comboComidas.setBackground(Color.WHITE);
	    comboOtros.setBackground(Color.WHITE);

	    for (ItemConsumo item : itemsDisponibles) {
	        if (item instanceof Bebestible) {
	            modeloBebestibles.addElement(item.getNombre());
	        } else if (item instanceof Comida) {
	            modeloComidas.addElement(item.getNombre());
	        } else {
	            modeloOtros.addElement(item.getNombre());
	        }
	    }

	    // Configurar el primer item como el seleccionado por defecto (el valor null)
	    comboBebestibles.setSelectedItem(null);
	    comboComidas.setSelectedItem(null);
	    comboOtros.setSelectedItem(null);

	    panelContenido.add(new JLabel("Bebestible:"));
	    panelContenido.add(comboBebestibles);
	    panelContenido.add(new JLabel("Comida:"));
	    panelContenido.add(comboComidas);
	    panelContenido.add(new JLabel("Otro:"));
	    panelContenido.add(comboOtros);

	    dialog.add(panelContenido, BorderLayout.CENTER);

	    JPanel panelBotones = new JPanel();
	    JButton btnOk = new JButton("Ok");
	    JButton btnCancelar = new JButton("Cancelar");



	    btnOk.addActionListener(ev -> {
	        // Agregar los ítems seleccionados
	        String nombreBebestible = (String) comboBebestibles.getSelectedItem();
	        String nombreComida = (String) comboComidas.getSelectedItem();
	        String nombreOtro = (String) comboOtros.getSelectedItem();

	        // Agregar el Bebestible si está seleccionado
	        if (nombreBebestible != null) {
	            for (ItemConsumo item : itemsDisponibles) {
	                if (item.getNombre().equals(nombreBebestible)) {
	                    mesaSeleccionada.agregarItem(item); // Agregar el ítem de bebida a la mesa
	                    break;
	                }
	            }
	        }

	        // Agregar la Comida si está seleccionada
	        if (nombreComida != null) {
	            for (ItemConsumo item : itemsDisponibles) {
	                if (item.getNombre().equals(nombreComida)) {
	                    mesaSeleccionada.agregarItem(item); // Agregar el ítem de comida a la mesa
	                    break;
	                }
	            }
	        }

	        // Agregar el Otro si está seleccionado
	        if (nombreOtro != null) {
	            for (ItemConsumo item : itemsDisponibles) {
	                if (item.getNombre().equals(nombreOtro)) {
	                    mesaSeleccionada.agregarItem(item); // Agregar el ítem de otro a la mesa
	                    break;
	                }
	            }
	        }

	        // Actualizar la mesa con los nuevos ítems
	        mostrarMesa(mesaSeleccionada);

	        dialog.dispose();
	    });

	    btnCancelar.addActionListener(ev -> dialog.dispose());

	    panelBotones.add(btnCancelar);
	    panelBotones.add(btnOk);
	    dialog.add(panelBotones, BorderLayout.SOUTH);

	    dialog.setVisible(true);
	}


	 /**
     * Muestra la información de la mesa seleccionada en el panel derecho.
     * Actualiza la tabla con los ítems consumidos y el subtotal correspondiente.
     *
     * @param m La mesa cuya información se desea mostrar.
     */
	private void mostrarMesa(Mesa m) {
	    // Agregar el panel derecho si no está ya en el panel principal
	    if (panelPrincipal.getComponentCount() < 3) {
	        panelPrincipal.add(panelDer, BorderLayout.EAST);
	    }

	    // Actualizar el número de mesa en el campo
	    campoNumeroMesa.setText("Mesa " + (m.getId() + 1));

	    // Limpiar la tabla antes de agregar nuevas filas
	    modeloTabla.setRowCount(0);

	    // Llenar la tabla con los datos de los ítems en la mesa
	    for (ItemConsumo item : m.getItems()) {
	        Object[] fila = { 
	            item.getNombre(), 
	            String.format("$%d", (int) item.getPrecio()), // Valor c/u sin decimales
	            item.getCantidad(), 
	            String.format("$%d", (int) (item.getPrecio() * item.getCantidad())) // Valor total sin decimales
	        };
	        double subtotal = m.calcularTotalConsumo();
	        
	        subtotalLabel.setText(String.format("Subtotal: $%d",(int)subtotal));
	        modeloTabla.addRow(fila);
	    }
	    // Cambiar el color del botón de la mesa a naranja si tiene pedidos
	    if (m.getItems().size() > 0) { // Verifica si la mesa tiene pedidos
	        botonesMesas[m.getId()].setBackground(Color.ORANGE);
	    } else {
	        botonesMesas[m.getId()].setBackground(new Color(200, 150, 255)); // Color original
	    }

	   

	    // Refrescar el panel para mostrar cambios
	    panelDer.revalidate();
	    panelDer.repaint();
	    
	}
	 /**
     * Carga los ítems de consumo desde un archivo de texto.
     * Cada línea del archivo debe contener el ID, nombre y precio del ítem.
     *
     * @param rutaArchivo La ruta del archivo desde el cual se cargarán los ítems.
     * @return Una lista de ítems de consumo disponibles.
     */
	private ArrayList<ItemConsumo> cargarItemsDesdeArchivo(String rutaArchivo) {
	    ArrayList<ItemConsumo> itemsDisponibles = new ArrayList<>();
	    try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
	        String linea;
	        while ((linea = br.readLine()) != null) {
	            // Se divide la línea por comas
	            String[] partes = linea.split(",");
	            
	            // Verifica que la línea tenga exactamente 3 elementos
	            if (partes.length == 3) {
	                String id = partes[0].trim();
	                String nombre = partes[1].trim();
	                String precioStr = partes[2].trim();
	                
	                ItemConsumo item = null;
	                try {
	                    // Intentar parsear el precio como double
	                    double precio = Double.parseDouble(precioStr);
	                    
	                    // Determinar el tipo basándonos en la primera letra del ID
	                    String tipo = "";
	                    if (id.startsWith("B")) {
	                        tipo = "Bebestible";
	                    } else if (id.startsWith("C")) {
	                        tipo = "Comida";
	                    } else if (id.startsWith("O")) {
	                        tipo = "Otro";  // Puedes agregar más tipos si es necesario
	                    }

	                    // Crear el item según el tipo
	                    if (tipo.equals("Bebestible")) {
	                        item = new Bebestible(id, nombre, precio);
	                    } else if (tipo.equals("Comida")) {
	                        item = new Comida(id, nombre, precio);
	                    } else if (tipo.equals("Otro")) {
	                        item = new ItemConsumo(id, nombre, precio);  // Usar una clase genérica si es otro tipo
	                    }
	                } catch (NumberFormatException e) {
	                    System.out.println("Error al parsear el precio: " + precioStr);
	                }

	                // Si se creó el item, se añade a la lista
	                if (item != null) {
	                    itemsDisponibles.add(item);
	                }
	            } else {
	                System.out.println("Línea incompleta o mal formateada: " + linea);
	            }
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return itemsDisponibles;
	}
	/**
     * Guarda la boleta generada en un archivo de texto.
     * El nombre del archivo se basa en la fecha y la ID de la mesa.
     *
     * @param boleta La boleta que se desea guardar.
     * @param idMesa El ID de la mesa asociada a la boleta.
     */
	private void guardarBoletaEnArchivo(Boleta boleta, int idMesa) {
	    // Obtener la fecha actual para formatear el nombre del archivo
	    SimpleDateFormat formatoNombreArchivo = new SimpleDateFormat("yyyyMMdd.HHmm");
	    String nombreArchivo = formatoNombreArchivo.format(new Date()) + ".m" + (idMesa + 1) + ".txt";
	    
	    // Ruta donde se guardará la boleta
	    String rutaArchivo = "boletas/" + nombreArchivo;

	    // Crear el directorio si no existe

	    // Escribir la boleta en el archivo
	    try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivo))) {
	        writer.write(boleta.detalle());
	        writer.flush();
	    } catch (IOException ex) {
	        JOptionPane.showMessageDialog(this, "Error al guardar la boleta: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	    }
	}


}
