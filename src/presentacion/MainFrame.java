package presentacion;

import excepciones.NegocioException;
import modelo.Vehiculo;
import negocio.VehiculoNegocio;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * Ventana principal del Sistema de Control de Parqueo.
 * Unicamente captura datos, muestra informacion y llama a la capa de
 * negocio. No contiene validaciones ni sentencias SQL.
 *
 * ------------------------------------------------------------------
 * NOMBRES DE COMPONENTES (usar estos mismos nombres si arma la
 * interfaz arrastrando componentes desde la Paleta de NetBeans):
 * ------------------------------------------------------------------
 *  JMenuBar          menuBar
 *  JMenu             menuArchivo, menuHerramientas
 *  JMenuItem         itemSalir, itemListar, itemLimpiar
 *  JToolBar          toolBar
 *  JButton           btnNuevo, btnGuardar, btnBuscar, btnActualizar, btnEliminar   (toolbar)
 *  JTabbedPane       tabbedPane
 *  --- Pestaña "Registro" ---
 *  JTextField        txtPlaca, txtPropietario, txtTelefono, txtEspacio
 *  JComboBox<String> cmbTipoVehiculo
 *  JButton           btnGuardarRegistro, btnActualizarRegistro, btnLimpiarRegistro
 *  --- Pestaña "Lista" ---
 *  JTextField        txtBuscarPlaca
 *  JButton           btnBuscarLista, btnListarLista, btnEliminarLista
 *  JTable            tblVehiculos
 * ------------------------------------------------------------------
 */
public class MainFrame extends JFrame {

    private final VehiculoNegocio vehiculoNegocio;

    // Menu
    private JMenuBar menuBar;
    private JMenuItem itemSalir;
    private JMenuItem itemListar;
    private JMenuItem itemLimpiar;

    // Toolbar
    private JButton btnNuevo;
    private JButton btnGuardar;
    private JButton btnBuscar;
    private JButton btnActualizar;
    private JButton btnEliminar;

    // Tabs
    private JTabbedPane tabbedPane;

    // Pestaña Registro
    private JTextField txtPlaca;
    private JTextField txtPropietario;
    private JTextField txtTelefono;
    private JTextField txtEspacio;
    private JComboBox<String> cmbTipoVehiculo;
    private JButton btnGuardarRegistro;
    private JButton btnActualizarRegistro;
    private JButton btnLimpiarRegistro;

    // Pestaña Lista
    private JTextField txtBuscarPlaca;
    private JButton btnBuscarLista;
    private JButton btnListarLista;
    private JButton btnEliminarLista;
    private JTable tblVehiculos;
    private DefaultTableModel modeloTabla;

    public MainFrame() {
        this.vehiculoNegocio = new VehiculoNegocio();
        inicializarComponentes();
        cargarTabla();
    }

    private void inicializarComponentes() {
        setTitle("Sistema de Control de Parqueo");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(760, 520);
        setLocationRelativeTo(null);

        construirMenu();
        construirTabbedPane();

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                salir();
            }
        });
    }

    // ---------------------------------------------------------------
    // Construccion de la interfaz
    // ---------------------------------------------------------------

    private void construirMenu() {
        menuBar = new JMenuBar();

        JMenu menuArchivo = new JMenu("Archivo");
        itemSalir = new JMenuItem("Salir");
        itemSalir.addActionListener(e -> salir());
        menuArchivo.add(itemSalir);

        JMenu menuHerramientas = new JMenu("Herramientas");
        itemListar = new JMenuItem("Listar");
        itemListar.addActionListener(e -> {
            cargarTabla();
            tabbedPane.setSelectedIndex(1);
        });
        itemLimpiar = new JMenuItem("Limpiar");
        itemLimpiar.addActionListener(e -> limpiarFormulario());
        menuHerramientas.add(itemListar);
        menuHerramientas.add(itemLimpiar);

        menuBar.add(menuArchivo);
        menuBar.add(menuHerramientas);
        setJMenuBar(menuBar);
    }

    private JToolBar toolBar;

    private JToolBar crearToolBar() {
        JToolBar barra = new JToolBar();
        barra.setFloatable(false);

        btnNuevo = new JButton("Nuevo");
        btnGuardar = new JButton("Guardar");
        btnBuscar = new JButton("Buscar");
        btnActualizar = new JButton("Actualizar");
        btnEliminar = new JButton("Eliminar");

        btnNuevo.addActionListener(e -> {
            limpiarFormulario();
            tabbedPane.setSelectedIndex(0);
        });
        btnGuardar.addActionListener(e -> guardarRegistro());
        btnBuscar.addActionListener(e -> buscarDesdeToolBar());
        btnActualizar.addActionListener(e -> actualizarRegistro());
        btnEliminar.addActionListener(e -> eliminarSeleccionado());

        barra.add(btnNuevo);
        barra.add(btnGuardar);
        barra.add(btnBuscar);
        barra.add(btnActualizar);
        barra.add(btnEliminar);
        return barra;
    }

    private void construirTabbedPane() {
        toolBar = crearToolBar();

        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Registro", crearPanelRegistro());
        tabbedPane.addTab("Lista", crearPanelLista());

        setLayout(new BorderLayout());
        add(toolBar, BorderLayout.NORTH);
        add(tabbedPane, BorderLayout.CENTER);
    }

    private JPanel crearPanelRegistro() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.anchor = GridBagConstraints.WEST;

        String[] tiposVehiculo = {"Carro", "Moto", "Camioneta", "Bus"};

        txtPlaca = new JTextField(15);
        txtPropietario = new JTextField(15);
        txtTelefono = new JTextField(15);
        txtEspacio = new JTextField(15);
        cmbTipoVehiculo = new JComboBox<>(tiposVehiculo);

        agregarFila(panel, gbc, 0, "Placa:", txtPlaca);
        agregarFila(panel, gbc, 1, "Propietario:", txtPropietario);
        agregarFila(panel, gbc, 2, "Telefono:", txtTelefono);
        agregarFila(panel, gbc, 3, "Numero de espacio:", txtEspacio);
        agregarFila(panel, gbc, 4, "Tipo de vehiculo:", cmbTipoVehiculo);

        btnGuardarRegistro = new JButton("Guardar");
        btnActualizarRegistro = new JButton("Actualizar");
        btnLimpiarRegistro = new JButton("Limpiar");

        btnGuardarRegistro.addActionListener(e -> guardarRegistro());
        btnActualizarRegistro.addActionListener(e -> actualizarRegistro());
        btnLimpiarRegistro.addActionListener(e -> limpiarFormulario());

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBotones.add(btnGuardarRegistro);
        panelBotones.add(btnActualizarRegistro);
        panelBotones.add(btnLimpiarRegistro);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        panel.add(panelBotones, gbc);

        return panel;
    }

    private void agregarFila(JPanel panel, GridBagConstraints gbc, int fila, String etiqueta, JComponent campo) {
        gbc.gridx = 0;
        gbc.gridy = fila;
        gbc.gridwidth = 1;
        panel.add(new JLabel(etiqueta), gbc);
        gbc.gridx = 1;
        panel.add(campo, gbc);
    }

    private JPanel crearPanelLista() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT));
        txtBuscarPlaca = new JTextField(15);
        btnBuscarLista = new JButton("Buscar por placa");
        btnBuscarLista.addActionListener(e -> buscarEnLista());
        panelBusqueda.add(new JLabel("Placa:"));
        panelBusqueda.add(txtBuscarPlaca);
        panelBusqueda.add(btnBuscarLista);

        String[] columnas = {"ID", "Placa", "Propietario", "Telefono", "Espacio", "Tipo", "Hora Entrada", "Estado"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblVehiculos = new JTable(modeloTabla);
        tblVehiculos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblVehiculos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    cargarSeleccionEnFormulario();
                }
            }
        });
        JScrollPane scroll = new JScrollPane(tblVehiculos);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnListarLista = new JButton("Listar / Recargar");
        btnEliminarLista = new JButton("Eliminar seleccionado");
        btnListarLista.addActionListener(e -> cargarTabla());
        btnEliminarLista.addActionListener(e -> eliminarSeleccionado());
        panelBotones.add(btnListarLista);
        panelBotones.add(btnEliminarLista);

        panel.add(panelBusqueda, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);
        panel.add(panelBotones, BorderLayout.SOUTH);

        return panel;
    }

    // ---------------------------------------------------------------
    // Acciones (unicamente capturan datos y llaman a la capa negocio)
    // ---------------------------------------------------------------

    private void guardarRegistro() {
        try {
            Vehiculo vehiculo = leerFormulario();
            vehiculoNegocio.agregar(vehiculo);
            JOptionPane.showMessageDialog(this, "Vehiculo registrado correctamente.",
                    "Exito", JOptionPane.INFORMATION_MESSAGE);
            limpiarFormulario();
            cargarTabla();
        } catch (NegocioException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "No se pudo guardar", JOptionPane.WARNING_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El numero de espacio debe ser un valor numerico.",
                    "Dato invalido", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void actualizarRegistro() {
        try {
            if (txtPlaca.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Busque o escriba la placa del vehiculo a actualizar.",
                        "Falta la placa", JOptionPane.WARNING_MESSAGE);
                return;
            }
            Vehiculo vehiculo = leerFormulario();
            vehiculoNegocio.actualizar(vehiculo);
            JOptionPane.showMessageDialog(this, "Vehiculo actualizado correctamente.",
                    "Exito", JOptionPane.INFORMATION_MESSAGE);
            limpiarFormulario();
            cargarTabla();
        } catch (NegocioException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "No se pudo actualizar", JOptionPane.WARNING_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El numero de espacio debe ser un valor numerico.",
                    "Dato invalido", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void buscarDesdeToolBar() {
        String placa = JOptionPane.showInputDialog(this, "Ingrese la placa a buscar:", "Buscar vehiculo",
                JOptionPane.QUESTION_MESSAGE);
        if (placa == null || placa.trim().isEmpty()) {
            return;
        }
        try {
            Vehiculo vehiculo = vehiculoNegocio.buscar(placa);
            mostrarEnFormulario(vehiculo);
            tabbedPane.setSelectedIndex(0);
        } catch (NegocioException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "No encontrado", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void buscarEnLista() {
        String placa = txtBuscarPlaca.getText().trim();
        if (placa.isEmpty()) {
            cargarTabla();
            return;
        }
        try {
            Vehiculo vehiculo = vehiculoNegocio.buscar(placa);
            modeloTabla.setRowCount(0);
            modeloTabla.addRow(convertirAFila(vehiculo));
        } catch (NegocioException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "No encontrado", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void eliminarSeleccionado() {
        String placa = obtenerPlacaSeleccionada();
        if (placa == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un vehiculo en la tabla o escriba su placa.",
                    "Nada seleccionado", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Desea eliminar el vehiculo con placa " + placa + "?",
                "Confirmar eliminacion", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (confirmacion != JOptionPane.YES_OPTION) {
            return;
        }
        try {
            vehiculoNegocio.eliminar(placa);
            JOptionPane.showMessageDialog(this, "Vehiculo eliminado correctamente.",
                    "Exito", JOptionPane.INFORMATION_MESSAGE);
            limpiarFormulario();
            cargarTabla();
        } catch (NegocioException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "No se pudo eliminar", JOptionPane.WARNING_MESSAGE);
        }
    }

    private String obtenerPlacaSeleccionada() {
        int fila = tblVehiculos.getSelectedRow();
        if (fila != -1) {
            return String.valueOf(modeloTabla.getValueAt(fila, 1));
        }
        if (!txtPlaca.getText().trim().isEmpty()) {
            return txtPlaca.getText().trim();
        }
        return null;
    }

    private void cargarSeleccionEnFormulario() {
        int fila = tblVehiculos.getSelectedRow();
        if (fila == -1) {
            return;
        }
        String placa = String.valueOf(modeloTabla.getValueAt(fila, 1));
        try {
            Vehiculo vehiculo = vehiculoNegocio.buscar(placa);
            mostrarEnFormulario(vehiculo);
            tabbedPane.setSelectedIndex(0);
        } catch (NegocioException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "No encontrado", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void cargarTabla() {
        try {
            List<Vehiculo> vehiculos = vehiculoNegocio.listar();
            modeloTabla.setRowCount(0);
            for (Vehiculo v : vehiculos) {
                modeloTabla.addRow(convertirAFila(v));
            }
        } catch (NegocioException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error al listar", JOptionPane.ERROR_MESSAGE);
        }
    }

    private Object[] convertirAFila(Vehiculo v) {
        return new Object[]{
                v.getId(), v.getPlaca(), v.getPropietario(), v.getTelefono(),
                v.getNumeroEspacio(), v.getTipoVehiculo(), v.getHoraEntrada(), v.getEstado()
        };
    }

    private Vehiculo leerFormulario() {
        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setPlaca(txtPlaca.getText().trim());
        vehiculo.setPropietario(txtPropietario.getText().trim());
        vehiculo.setTelefono(txtTelefono.getText().trim());
        String textoEspacio = txtEspacio.getText().trim();
        vehiculo.setNumeroEspacio(textoEspacio.isEmpty() ? 0 : Integer.parseInt(textoEspacio));
        vehiculo.setTipoVehiculo((String) cmbTipoVehiculo.getSelectedItem());
        return vehiculo;
    }

    private void mostrarEnFormulario(Vehiculo vehiculo) {
        txtPlaca.setText(vehiculo.getPlaca());
        txtPlaca.setEditable(false);
        txtPropietario.setText(vehiculo.getPropietario());
        txtTelefono.setText(vehiculo.getTelefono());
        txtEspacio.setText(String.valueOf(vehiculo.getNumeroEspacio()));
        cmbTipoVehiculo.setSelectedItem(vehiculo.getTipoVehiculo());
    }

    private void limpiarFormulario() {
        txtPlaca.setText("");
        txtPlaca.setEditable(true);
        txtPropietario.setText("");
        txtTelefono.setText("");
        txtEspacio.setText("");
        cmbTipoVehiculo.setSelectedIndex(0);
        txtBuscarPlaca.setText("");
    }

    private void salir() {
        int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Desea salir del sistema?", "Confirmar salida",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (confirmacion == JOptionPane.YES_OPTION) {
            dispose();
            System.exit(0);
        }
    }
}
