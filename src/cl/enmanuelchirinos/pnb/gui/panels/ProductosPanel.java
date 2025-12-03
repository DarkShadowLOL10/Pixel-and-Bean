package cl.enmanuelchirinos.pnb.gui.panels;

import cl.enmanuelchirinos.pnb.controller.ProductoController;
import cl.enmanuelchirinos.pnb.model.Producto;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.List;

public class ProductosPanel extends JPanel {
    private final ProductoController productoController;
    private JTable table;
    private ProductosTableModel tableModel;
    private JTextField txtNombre, txtPrecio, txtBuscar;
    private JComboBox<String> cmbCategoria, cmbTipo;
    private JButton btnNuevo, btnGuardar, btnEliminar, btnLimpiar, btnToggleActivo; // cambiar estado
    private JComboBox<String> cmbFiltroCategoria; // filtro listado

    public ProductosPanel(ProductoController productoController) {
        this.productoController = productoController;
        setLayout(new BorderLayout());
        initComponents();
        loadData();
        initListeners();
    }

    private void initComponents() {
        // Top - búsqueda mejorada con filtro de categoría
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(new JLabel("Buscar:"));
        txtBuscar = new JTextField(20);
        top.add(txtBuscar);
        top.add(new JLabel("Categoría:"));
        cmbFiltroCategoria = new JComboBox<>(new String[]{"TODAS","BEBIDA","SNACK","TIEMPO"});
        top.add(cmbFiltroCategoria);
        JButton btnBuscar = new JButton("Filtrar");
        btnBuscar.addActionListener(e -> filter());
        top.add(btnBuscar);
        add(top, BorderLayout.NORTH);

        // Center - tabla
        tableModel = new ProductosTableModel();
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getSelectionModel().addListSelectionListener(e -> { if (!e.getValueIsAdjusting()) { int row = table.getSelectedRow(); if (row >= 0) fillForm(row); } });
        table.addMouseListener(new java.awt.event.MouseAdapter() { @Override public void mouseClicked(java.awt.event.MouseEvent evt){ if(evt.getClickCount()==2){ int r=table.getSelectedRow(); if(r>=0) fillForm(r);} } });
        // Renderer para precios y estado
        table.setDefaultRenderer(Object.class,new DefaultTableCellRenderer(){ @Override public Component getTableCellRendererComponent(JTable tbl,Object val,boolean sel,boolean focus,int row,int col){ Component c=super.getTableCellRendererComponent(tbl,val,sel,focus,row,col); if(col==5){ String activo=(String)val; c.setForeground("Activo".equals(activo)? new java.awt.Color(0,128,0): Color.RED); } else if(col==4){ if(val instanceof Double d){ setText(String.format("$%,.0f", d)); } } return c; }});
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Right - formulario
        JPanel form = new JPanel();
        form.setLayout(new GridBagLayout());
        form.setBorder(BorderFactory.createTitledBorder("Producto"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0; gbc.gridy = 0;
        form.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1;
        txtNombre = new JTextField(15); form.add(txtNombre, gbc);

        gbc.gridx = 0; gbc.gridy++;
        form.add(new JLabel("Categoría:"), gbc);
        gbc.gridx = 1;
        cmbCategoria = new JComboBox<>(new String[]{"BEBIDA","SNACK","TIEMPO"});
        form.add(cmbCategoria, gbc);

        gbc.gridx = 0; gbc.gridy++;
        form.add(new JLabel("Tipo:"), gbc);
        gbc.gridx = 1;
        cmbTipo = new JComboBox<>(new String[]{"CAFE","POSTRE","ARCADE"});
        form.add(cmbTipo, gbc);

        gbc.gridx = 0; gbc.gridy++;
        form.add(new JLabel("Precio:"), gbc);
        gbc.gridx = 1;
        txtPrecio = new JTextField(10); form.add(txtPrecio, gbc);

        // Botones
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnNuevo = new JButton("Nuevo");
        btnGuardar = new JButton("Guardar");
        btnEliminar = new JButton("Eliminar");
        btnLimpiar = new JButton("Limpiar");
        btnToggleActivo = new JButton("Cambiar Estado");
        buttons.add(btnNuevo); buttons.add(btnGuardar); buttons.add(btnEliminar); buttons.add(btnLimpiar); buttons.add(btnToggleActivo);
        gbc.gridx = 0; gbc.gridy++; gbc.gridwidth = 2;
        form.add(buttons, gbc);

        btnNuevo.addActionListener(e -> nuevo());
        btnGuardar.addActionListener(e -> guardar());
        btnEliminar.addActionListener(e -> eliminar());
        btnLimpiar.addActionListener(e -> limpiarFormulario());
        btnToggleActivo.addActionListener(e -> toggleEstado());

        add(form, BorderLayout.EAST);
    }

    private void loadData() {
        tableModel.setData(productoController.listarTodos());
    }

    private void initListeners() {
        // DocumentListener búsqueda incremental
        txtBuscar.getDocument().addDocumentListener(new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e){ filter(); }
            @Override public void removeUpdate(DocumentEvent e){ filter(); }
            @Override public void changedUpdate(DocumentEvent e){ }
        });
        cmbFiltroCategoria.addActionListener(e -> filter());
        cmbCategoria.addActionListener(e -> updateTipoOptions());
    }

    private void updateTipoOptions() {
        String categoria = (String) cmbCategoria.getSelectedItem();
        cmbTipo.removeAllItems();
        switch (categoria) {
            case "BEBIDA" -> { cmbTipo.addItem("CAFE"); cmbTipo.addItem("TE"); cmbTipo.addItem("OTRA"); }
            case "SNACK" -> { cmbTipo.addItem("POSTRE"); cmbTipo.addItem("SALADO"); }
            case "TIEMPO" -> { cmbTipo.addItem("ARCADE"); cmbTipo.addItem("VR"); }
        }
    }

    private void filter() {
        String texto = txtBuscar.getText().trim();
        String categoriaFiltro = (String) cmbFiltroCategoria.getSelectedItem();
        List<Producto> base;
        if ("TODAS".equals(categoriaFiltro)) {
            base = productoController.listarTodos();
        } else {
            base = productoController.listarPorCategoria(categoriaFiltro);
        }
        if (texto.isEmpty()) {
            tableModel.setData(base);
        } else {
            tableModel.setData(base.stream().filter(p -> p.getNombre().toLowerCase().contains(texto.toLowerCase())).toList());
        }
    }

    private void fillForm(int row) {
        Producto p = tableModel.getAt(row);
        txtNombre.setText(p.getNombre());
        cmbCategoria.setSelectedItem(p.getCategoria());
        updateTipoOptions();
        cmbTipo.setSelectedItem(p.getTipo());
        txtPrecio.setText(String.valueOf(p.getPrecio()));
    }

    private void nuevo() { limpiarFormulario(); table.clearSelection(); }

    private void guardar() {
        String nombre = txtNombre.getText().trim();
        String precioStr = txtPrecio.getText().trim();
        if (nombre.isEmpty()) { JOptionPane.showMessageDialog(this, "Nombre requerido"); return; }
        double precio;
        try { precio = Double.parseDouble(precioStr); if (precio <= 0) throw new NumberFormatException(); }
        catch (NumberFormatException ex) { JOptionPane.showMessageDialog(this, "Precio inválido"); return; }
        String categoria = (String) cmbCategoria.getSelectedItem();
        String tipo = (String) cmbTipo.getSelectedItem();

        try {
            int selected = table.getSelectedRow();
            if (selected >= 0) {
                Producto original = tableModel.getAt(selected);
                productoController.actualizarProducto(original.getId(), nombre, categoria, tipo, precio, original.isActivo());
            } else {
                productoController.crearProducto(nombre, categoria, tipo, precio);
            }
            loadData();
            limpiarFormulario();
            JOptionPane.showMessageDialog(this, "Producto guardado");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminar() {
        int row = table.getSelectedRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Seleccione un producto"); return; }
        Producto p = tableModel.getAt(row);
        int confirm = JOptionPane.showConfirmDialog(this, "¿Eliminar producto?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                productoController.eliminarProducto(p.getId());
                loadData();
                limpiarFormulario();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void toggleEstado() {
        int row = table.getSelectedRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Seleccione un producto"); return; }
        Producto p = tableModel.getAt(row);
        try {
            productoController.cambiarEstadoProducto(p.getId());
            loadData();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarFormulario() {
        txtNombre.setText("");
        txtPrecio.setText("");
        cmbCategoria.setSelectedIndex(0);
        cmbTipo.setSelectedIndex(0);
    }

    public void refresh() { loadData(); }

    // TableModel interno modificado para agregar columna activo
    private static class ProductosTableModel extends AbstractTableModel {
        private final String[] cols = {"ID","Nombre","Categoría","Tipo","Precio","Estado"};
        private List<Producto> data = List.of();
        public void setData(List<Producto> productos) { this.data = productos; fireTableDataChanged(); }
        public Producto getAt(int row) { return data.get(row); }
        @Override public int getRowCount() { return data.size(); }
        @Override public int getColumnCount() { return cols.length; }
        @Override public String getColumnName(int column) { return cols[column]; }
        @Override public Object getValueAt(int rowIndex, int columnIndex) {
            Producto p = data.get(rowIndex);
            return switch(columnIndex){
                case 0 -> p.getId();
                case 1 -> p.getNombre();
                case 2 -> p.getCategoria();
                case 3 -> p.getTipo();
                case 4 -> p.getPrecio();
                case 5 -> p.isActivo()?"Activo":"Inactivo";
                default -> null; };
        }
        @Override public Class<?> getColumnClass(int columnIndex) {
            return switch(columnIndex){
                case 0 -> Integer.class;
                case 4 -> Double.class;
                default -> String.class; };
        }
        @Override public boolean isCellEditable(int rowIndex, int columnIndex) { return false; }
    }
}
