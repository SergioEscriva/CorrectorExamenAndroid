package com.universae.correctorexamenes.utilidades;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.json.JSONArray;
import org.json.JSONException;

import utilidades.DialogoFicheros;
import utilidades.Utilidades;

public class PantallaPrincipal extends JFrame {

    private final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JPanel panel;
    private Utilidades utilidades = new Utilidades();
    private BuscarCirculos busquedaCirculos = new BuscarCirculos();

    private JSONArray listaPlantillas;
    private JLabel lblPlantillaCorrecion;
    private JFrame frame1;
    private JLabel lblExamenCorrecion;
    private JLabel lblNotaCalculada;
    private JLabel lblAcertadas_2;
    private JLabel lblFalladas_2;
    private JLabel lblEnBlanco;
    private JLabel lblNulas_2;
    private JButton btnExamen;

    private JLabel lblExamen_1;
    private JLabel lblNota;
    private JLabel lblAcertadas;
    private JLabel lblFalladas;
    private JLabel lblNulas;
    private JLabel lblBlanco;
    private JLabel lblPlantilla;
    private JLabel lblPenalizacion;

    private JTextField tfIntroducirPlantilla;
    private JTextField txtFPenalizacion;

    public static void main(String[] args) {
	EventQueue.invokeLater(new Runnable() {
	    public void run() {
		try {
		    PantallaPrincipal frame = new PantallaPrincipal();

		    frame.setVisible(true);
		} catch (Exception e) {
		    e.printStackTrace();
		}
	    }
	});
    }

    public PantallaPrincipal() throws JSONException, IOException {
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setBounds(100, 100, 523, 571);
	contentPane = new JPanel();
	contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	this.setLocationRelativeTo(null);

	setContentPane(contentPane);
	contentPane.setLayout(null);

	panel = new JPanel();
	panel.setAutoscrolls(true);
	panel.setBounds(0, 0, 535, 539);
	panel.setPreferredSize(new Dimension(450, 300));
	panel.setBackground(new Color(9, 37, 72));
	contentPane.add(panel);
	panel.setLayout(null);

	// Botón cargar y corregir examen
	btnExamen = new JButton();
	String texto = "Cargar y Corregir...";
	btnExamen.setText(texto);
	btnExamen.setEnabled(false);
	btnExamen.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
	    }
	});
	btnExamen.addMouseListener(new MouseAdapter() {
	    @Override
	    public void mouseClicked(MouseEvent e) {
		if (btnExamen.isEnabled()) {
		    abrirExamen();

		}
	    }
	});

	btnExamen.setBounds(284, 434, 206, 62);
	panel.add(btnExamen);

	// Botón cargar plantilla
	JButton btnPlantilla = new JButton("Cargar Plantilla...");
	btnPlantilla.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent arg0) {
	    }
	});
	btnPlantilla.addMouseListener(new MouseAdapter() {
	    @Override
	    public void mouseClicked(MouseEvent e) {
		cargarPlantilla();
	    }

	});
	btnPlantilla.setBounds(37, 459, 183, 37);
	panel.add(btnPlantilla);

	lblPlantilla = new JLabel("Plantilla de correción:");
	lblPlantilla.setFont(new Font("Dialog", Font.BOLD, 14));
	lblPlantilla.setVisible(true);
	lblPlantilla.setForeground(new Color(255, 255, 255));
	lblPlantilla.setBounds(33, 70, 235, 15);
	panel.add(lblPlantilla);

	lblPlantillaCorrecion = new JLabel("Cargar plantilla para comenzar a corregir. ");
	lblPlantillaCorrecion.setFont(new Font("Dialog", Font.BOLD, 14));
	lblPlantillaCorrecion.setVisible(false);
	lblPlantillaCorrecion.setForeground(Color.WHITE);
	lblPlantillaCorrecion.setBounds(33, 86, 471, 30);
	panel.add(lblPlantillaCorrecion);

	lblExamenCorrecion = new JLabel("Cargar examen para comenzar a corregir.");
	lblExamenCorrecion.setVisible(false);
	lblExamenCorrecion.setFont(new Font("Dialog", Font.BOLD, 14));
	lblExamenCorrecion.setForeground(Color.WHITE);
	lblExamenCorrecion.setBounds(33, 150, 475, 30);
	panel.add(lblExamenCorrecion);

	lblExamen_1 = new JLabel("Examen a corregir:");
	lblExamen_1.setFont(new Font("Dialog", Font.BOLD, 14));
	lblExamen_1.setVisible(true);
	lblExamen_1.setForeground(Color.WHITE);
	lblExamen_1.setBounds(33, 138, 235, 15);
	panel.add(lblExamen_1);

	lblNota = new JLabel("Nota de Examen");
	lblNota.setFont(new Font("Dialog", Font.BOLD, 14));
	lblNota.setVisible(false);
	lblNota.setForeground(Color.WHITE);
	lblNota.setBounds(63, 205, 171, 15);
	panel.add(lblNota);

	lblNotaCalculada = new JLabel("Sin Calificación");
	lblNotaCalculada.setVisible(false);
	lblNotaCalculada.setFont(new Font("Dialog", Font.BOLD, 16));
	lblNotaCalculada.setForeground(Color.WHITE);
	lblNotaCalculada.setBounds(63, 221, 171, 62);
	panel.add(lblNotaCalculada);

	lblAcertadas = new JLabel("Correctas: ");
	lblAcertadas.setFont(new Font("Dialog", Font.BOLD, 14));
	lblAcertadas.setVisible(false);
	lblAcertadas.setForeground(Color.WHITE);
	lblAcertadas.setBounds(284, 205, 103, 15);
	panel.add(lblAcertadas);

	lblFalladas = new JLabel("Falladas: ");
	lblFalladas.setFont(new Font("Dialog", Font.BOLD, 14));
	lblFalladas.setVisible(false);
	lblFalladas.setForeground(Color.WHITE);
	lblFalladas.setBounds(284, 230, 88, 15);
	panel.add(lblFalladas);

	lblBlanco = new JLabel("En Blanco: ");
	lblBlanco.setFont(new Font("Dialog", Font.BOLD, 14));
	lblBlanco.setVisible(false);
	lblBlanco.setForeground(Color.WHITE);
	lblBlanco.setBounds(284, 257, 88, 15);
	panel.add(lblBlanco);

	lblNulas = new JLabel("Nulas:  ");
	lblNulas.setFont(new Font("Dialog", Font.BOLD, 14));
	lblNulas.setVisible(false);
	lblNulas.setForeground(Color.WHITE);
	lblNulas.setBounds(284, 283, 88, 15);
	panel.add(lblNulas);

	lblNulas_2 = new JLabel("0");
	lblNulas_2.setFont(new Font("Dialog", Font.BOLD, 14));
	lblNulas_2.setVisible(false);
	lblNulas_2.setForeground(Color.WHITE);
	lblNulas_2.setBounds(384, 283, 88, 15);
	panel.add(lblNulas_2);

	lblEnBlanco = new JLabel("0");
	lblEnBlanco.setFont(new Font("Dialog", Font.BOLD, 14));
	lblEnBlanco.setVisible(false);
	lblEnBlanco.setForeground(Color.WHITE);
	lblEnBlanco.setBounds(384, 256, 88, 15);
	panel.add(lblEnBlanco);

	lblFalladas_2 = new JLabel("0");
	lblFalladas_2.setFont(new Font("Dialog", Font.BOLD, 14));
	lblFalladas_2.setVisible(false);
	lblFalladas_2.setForeground(Color.WHITE);
	lblFalladas_2.setBounds(384, 229, 88, 15);
	panel.add(lblFalladas_2);

	lblAcertadas_2 = new JLabel("0");
	lblAcertadas_2.setFont(new Font("Dialog", Font.BOLD, 14));
	lblAcertadas_2.setVisible(false);
	lblAcertadas_2.setForeground(Color.WHITE);
	lblAcertadas_2.setBounds(384, 204, 88, 15);
	panel.add(lblAcertadas_2);

	lblPenalizacion = new JLabel("Penalización: ");
	lblPenalizacion.setFont(new Font("Dialog", Font.BOLD, 14));
	lblPenalizacion.setVisible(false);
	lblPenalizacion.setForeground(Color.WHITE);
	lblPenalizacion.setBounds(285, 328, 114, 15);
	panel.add(lblPenalizacion);

	txtFPenalizacion = new JTextField("-0.20");
	txtFPenalizacion.setFont(new Font("Dialog", Font.BOLD, 14));
	txtFPenalizacion.setVisible(false);
	txtFPenalizacion.setForeground(Color.BLUE);
	txtFPenalizacion.setBounds(284, 350, 114, 25);
	panel.add(txtFPenalizacion);
	txtFPenalizacion.setColumns(10);

	// Selecciona el texto de la ventana de numero de plantilla
	tfIntroducirPlantilla = new JTextField();
	tfIntroducirPlantilla.addMouseListener(new MouseAdapter() {
	    @Override
	    public void mouseClicked(MouseEvent arg0) {
		tfIntroducirPlantilla.selectAll();
		tfIntroducirPlantilla.getSelectedText();
	    }
	});
	tfIntroducirPlantilla.addFocusListener(new FocusAdapter() {
	    @Override
	    public void focusGained(FocusEvent arg0) {
		tfIntroducirPlantilla.selectAll();
		tfIntroducirPlantilla.getSelectedText();
	    }
	});
	tfIntroducirPlantilla.setText("00001"); // "Introducir N.º Plantilla"
	tfIntroducirPlantilla.setBounds(37, 422, 183, 25);
	panel.add(tfIntroducirPlantilla);
	tfIntroducirPlantilla.setColumns(10);

	JLabel lblNmeroDePlantilla = new JLabel("Identificador de Plantilla:");
	lblNmeroDePlantilla.setForeground(Color.WHITE);
	lblNmeroDePlantilla.setBounds(37, 405, 235, 15);
	panel.add(lblNmeroDePlantilla);

	JLabel lblExamen_1_1 = new JLabel("Examen Alumno");
	lblExamen_1_1.setForeground(Color.WHITE);
	lblExamen_1_1.setBounds(284, 417, 189, 15);
	panel.add(lblExamen_1_1);

	JLabel lblTitulo = new JLabel("Corrector de Exámenes");
	lblTitulo.setFont(new Font("Dialog", Font.BOLD, 24));
	lblTitulo.setForeground(new Color(246, 245, 244));
	lblTitulo.setBounds(95, 12, 325, 29);
	panel.add(lblTitulo);

	JLabel lblNewLabel = new JLabel("");
	lblNewLabel.setIcon(new ImageIcon(PantallaPrincipal.class.getResource("/resources/logounFondopng.png")));
	lblNewLabel.setBounds(73, 295, 103, 125);
	panel.add(lblNewLabel);

    }

    public void abrirExamen() {
	Map<Integer, String> examenalumnoMap = new HashMap<Integer, String>();
	try {

	    try {
		// poner todo a cero
		lblNotaCalculada.setText(String.valueOf("Sin Calificación"));
		lblAcertadas_2.setText(String.valueOf("0"));
		lblFalladas_2.setText(String.valueOf("0"));
		lblEnBlanco.setText(String.valueOf("0"));
		lblNulas_2.setText(String.valueOf("0"));

		lblExamenCorrecion.setFont(new Font("Dialog", Font.BOLD, 12));
		lblExamenCorrecion.setForeground(Color.YELLOW);
		lblExamenCorrecion.setVisible(true);
		lblExamenCorrecion.setText("Cargando y corrigiendo examen");

		// Abre dialogo fichero para cargar la imagen
		DialogoFicheros dialogoFicheros = new DialogoFicheros();
		String rutaExamen = dialogoFicheros.abrirExplorador();
		if (rutaExamen != null) {
		    examenalumnoMap = busquedaCirculos.buscarRespuestas(rutaExamen);
		}

		lblExamenCorrecion.setVisible(true);

		// BusquedaCirculos busquedaCirculos = new BusquedaCirculos();

		if (!examenalumnoMap.isEmpty()) {
		    Double penalizacion = Double.valueOf(txtFPenalizacion.getText());
		    Map<String, String> notaFinal = busquedaCirculos.calcularNota(listaPlantillas, penalizacion);
		    lblNotaCalculada.setForeground(Color.RED);
		    lblNotaCalculada.setText(String.valueOf(notaFinal.get("notaFinal")));
		    lblAcertadas_2.setText(String.valueOf(notaFinal.get("aciertos")));
		    lblFalladas_2.setText(String.valueOf(notaFinal.get("fallos")));
		    lblEnBlanco.setText(String.valueOf(notaFinal.get("blanco")));
		    lblNulas_2.setText(String.valueOf(notaFinal.get("nulas")));

		    if (Double.valueOf(notaFinal.get("notaFinal")) >= 5) {
			lblNotaCalculada.setForeground(Color.green);
		    }

		    lblExamenCorrecion.setForeground(Color.GREEN);
		    lblExamenCorrecion.setText("Examen cargado y corregido correctamente.");
		} else {
		    lblNotaCalculada.setText("Nota de Examen");
		    lblNotaCalculada.setForeground(Color.WHITE);
		    lblExamenCorrecion.setForeground(Color.RED);
		    lblExamenCorrecion.setVisible(true);
		    lblExamenCorrecion.setText("Imagen no válida. Por favor Introduzca una imagen centrada");

		}

	    } catch (Exception e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	    }
	} catch (Exception e1) {
	    // TODO Auto-generated catch block
	    e1.printStackTrace();
	}

	lblNota.setVisible(true);
	lblAcertadas.setVisible(true);
	lblFalladas.setVisible(true);
	lblFalladas.setVisible(true);
	lblNulas.setVisible(true);
	lblNotaCalculada.setVisible(true);
	lblNulas_2.setVisible(true);
	lblEnBlanco.setVisible(true);
	lblFalladas_2.setVisible(true);
	lblAcertadas_2.setVisible(true);
	lblBlanco.setVisible(true);
	txtFPenalizacion.setVisible(true);
	lblPenalizacion.setVisible(true);

    }

    public Map<Integer, String> escanearPlantilla() throws IOException, JSONException {
	DialogoFicheros dialogoFicheros = new DialogoFicheros();
	BuscarCirculos busquedaCirculos = new BuscarCirculos();
	Map<Integer, String> plantillaCorrecion = new HashMap<Integer, String>();

	String rutaPlantilla = dialogoFicheros.abrirExplorador();

	if (rutaPlantilla != null) {
	    plantillaCorrecion = busquedaCirculos.buscarRespuestas(rutaPlantilla);
	}

	return plantillaCorrecion;
    }

    public void crearVentanaExamen(String codigoTest) {

	frame1 = new JFrame("Crear Plantilla");
	frame1.setSize(258, 150);
	frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	frame1.setLocationRelativeTo(null);

	panel = new JPanel();
	frame1.getContentPane().add(panel);
	placeComponents(panel, codigoTest);

	frame1.setVisible(true);

    }

    private void placeComponents(JPanel panel, String codigoTest) {

	panel.setLayout(null);

	JLabel plantillaLabel = new JLabel("Identificador Nuevo:");
	plantillaLabel.setBounds(20, 10, 200, 25);
	panel.add(plantillaLabel);

	JTextField plantillaText = new JTextField(20);
	plantillaText.setEditable(false);
	plantillaText.setBounds(20, 35, 218, 25);
	plantillaText.setText(codigoTest);
	panel.add(plantillaText);

	// Botón de abrir plantilla
	JButton nuevaPlantillaButton = new JButton("Escanear");
	nuevaPlantillaButton.addMouseListener(new MouseAdapter() {
	    @Override
	    public void mouseClicked(MouseEvent e) {

		try {
		    Map<Integer, String> plantillaCorrecionMap = escanearPlantilla();
		    utilidades.guardarJson(plantillaCorrecionMap, codigoTest);
		    frame1.setVisible(false);

		} catch (IOException e1) {
		    // TODO Auto-generated catch block
		    e1.printStackTrace();
		} catch (JSONException e1) {
		    // TODO Auto-generated catch block
		    e1.printStackTrace();
		}
		cargarPlantilla();

	    }
	});
	nuevaPlantillaButton.setBounds(10, 80, 110, 25);
	panel.add(nuevaPlantillaButton);

	JButton registerButton = new JButton("Cancelar");
	registerButton.addMouseListener(new MouseAdapter() {
	    @Override
	    public void mouseClicked(MouseEvent e) {
		frame1.setVisible(false);
	    }
	});

	registerButton.setBounds(135, 80, 110, 25);
	panel.add(registerButton);
    }

    public void cargarPlantilla() {

	try {

	    try {

		lblPlantillaCorrecion.setForeground(Color.GREEN);
		listaPlantillas = utilidades.json(tfIntroducirPlantilla.getText());

		if (listaPlantillas == null || listaPlantillas.length() == 0) {
		    lblPlantillaCorrecion.setForeground(Color.YELLOW);
		    lblPlantillaCorrecion.setText("Creando plantilla " + tfIntroducirPlantilla.getText());
		    crearVentanaExamen(tfIntroducirPlantilla.getText());
		    listaPlantillas = utilidades.json(tfIntroducirPlantilla.getText());

		} else {

		    String numeroPlantillaString = tfIntroducirPlantilla.getText();
		    lblPlantillaCorrecion.setText("Plantilla " + numeroPlantillaString + " cargada correctamente.");
		}

	    } catch (Exception e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	    }
	} catch (Exception e1) {
	    // TODO Auto-generated catch block
	    e1.printStackTrace();
	}
	btnExamen.setEnabled(true);

	lblPlantillaCorrecion.setVisible(true);

    }
}
