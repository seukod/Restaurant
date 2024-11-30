package cl.uach.info090.ContrerasFrancisco;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

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
	
	// Constructor privado para Singleton
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
	
	// Método para obtener la instancia Singleton
	public static SistemaMesas getInstance() {
		return sistema;
	}

	// Inicialización de todos los componentes
	private void inicializarComponentes() {
		panelPrincipal = new JPanel();
		panelPrincipal.setLayout(new BorderLayout());
		add(panelPrincipal);

		// Inicializar el título
		labelTitulo = new JLabel("Sistema de Mesas", SwingConstants.CENTER);
		
		// Panel superior para título
		JPanel panelSuperior = new JPanel();
		panelSuperior.setLayout(new BorderLayout());
		panelSuperior.add(labelTitulo, BorderLayout.NORTH);
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

		// Panel de título y número de mesa
		JPanel panelTitulo = new JPanel(new BorderLayout());
		campoNumeroMesa = new JLabel("", SwingConstants.CENTER);
		panelTitulo.add(campoNumeroMesa, BorderLayout.NORTH);
		panelDer.add(panelTitulo, BorderLayout.NORTH);

		// Botón "Agregar" en el panel derecho
		JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton botonAgregar = new JButton("Agregar");
		botonAgregar.addActionListener(this);
		panelBoton.add(botonAgregar);
		panelDer.add(panelBoton, BorderLayout.NORTH);
		
		
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

	// Método para cargar los botones de mesas
	private void cargarBotonesMesas() {
		int cantidadMesas = 12; // Ejemplo de número de mesas
		botonesMesas = new JButton[cantidadMesas];
		mesas = new Mesa[cantidadMesas];

		for (int i = 0; i < cantidadMesas; i++) {
			botonesMesas[i] = new JButton("Mesa " + (i + 1));
			botonesMesas[i].setBackground(new Color(200, 150, 255));
			botonesMesas[i].setForeground(Color.WHITE);
			 
			mesas[i] = new Mesa(i, null);
			botonesMesas[i].addActionListener(this); // SistemaMesas actuará como listener
			panelMesas.add(botonesMesas[i]);
		}
	}

	// Método main para ejecutar el programa
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			SistemaMesas.getInstance().setVisible(true);
		});
	}

	// Implementación del método actionPerformed

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton botonPresionado = (JButton) e.getSource();

		if (botonPresionado.getText().equals("Cerrar pedido y generar boleta")) {
			System.out.println("Cerrando pedido y generando boleta...");
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

	    // Listeners para los combos
	    final JComboBox<?>[] comboSeleccionado = {null};

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


	// Método para mostrar la mesa seleccionada en el panel derecho
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
	            item.getPrecio(), 
	            item.getCantidad(), 
	            item.getPrecio() * item.getCantidad() 
	        };
	        double subtotal = m.calcularTotalConsumo();
	        
	        subtotalLabel.setText(String.format("Subtotal: $%.2f", subtotal));
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




}
