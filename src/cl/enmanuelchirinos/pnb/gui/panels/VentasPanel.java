package cl.enmanuelchirinos.pnb.gui.panels;

import cl.enmanuelchirinos.pnb.controller.ProductoController;
import cl.enmanuelchirinos.pnb.controller.VentaController;
import cl.enmanuelchirinos.pnb.model.ItemVenta;
import cl.enmanuelchirinos.pnb.model.Producto;
import cl.enmanuelchirinos.pnb.model.Venta;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class VentasPanel extends JPanel {
    private final VentaController ventaController;
    private final ProductoController productoController;
    private final String currentUser;

    private JTable tableVentas, tableDetalle, tableProductos;
    private VentasTableModel ventasTableModel;
    private DetalleTableModel detalleTableModel;
    private ProductosVentaTableModel productosVentaTableModel;
    private JLabel lblTotal;
    private JButton btnAgregar, btnConfirmar, btnAnular;
    private JButton btnQuitar; // quitar del detalle
    private JSpinner spnCantidad; // cantidad

    private List<ItemVenta> detalle = new ArrayList<>();

    public VentasPanel(VentaController ventaController, ProductoController productoController, String currentUser) {
        this.ventaController = ventaController;
        this.productoController = productoController;
        this.currentUser = currentUser;
        setLayout(new BorderLayout());
        initComponents();
        loadData();
    }

    private void initComponents() {
        // Panel superior - lista de ventas
        ventasTableModel = new VentasTableModel();
        tableVentas = new JTable(ventasTableModel);
        tableVentas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(tableVentas), BorderLayout.NORTH);

        // Panel central - productos y detalle
        JPanel center = new JPanel(new GridLayout(1,2));

        productosVentaTableModel = new ProductosVentaTableModel();
        tableProductos = new JTable(productosVentaTableModel);
        center.add(new JScrollPane(tableProductos));

        detalleTableModel = new DetalleTableModel();
        tableDetalle = new JTable(detalleTableModel);
        center.add(new JScrollPane(tableDetalle));

        add(center, BorderLayout.CENTER);

        // Panel inferior - acciones
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lblTotal = new JLabel("Total: $0");
        btnAgregar = new JButton("Agregar producto");
        btnConfirmar = new JButton("Confirmar venta");
        btnAnular = new JButton("Anular venta");
        btnQuitar = new JButton("Quitar seleccionado");
        spnCantidad = new JSpinner(new SpinnerNumberModel(1,1,99,1));
        bottom.add(lblTotal); bottom.add(btnAgregar); bottom.add(btnConfirmar); bottom.add(btnAnular); bottom.add(spnCantidad); bottom.add(btnQuitar);
        add(bottom, BorderLayout.SOUTH);

        btnAgregar.addActionListener(e -> agregarProducto());
        btnConfirmar.addActionListener(e -> confirmarVenta());
        btnAnular.addActionListener(e -> anularVenta());
        btnQuitar.addActionListener(e -> quitarItem());
    }

    private void loadData() {
        ventasTableModel.setData(ventaController.listarTodas());
        productosVentaTableModel.setData(productoController.listarActivos());
        updateTotal();
    }

    private void agregarProducto() {
        int row = tableProductos.getSelectedRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Seleccione un producto"); return; }
        Producto p = productosVentaTableModel.getAt(row);
        int cantidad = (Integer) spnCantidad.getValue();
        ItemVenta existente = detalle.stream().filter(iv -> iv.getProducto().getId()==p.getId()).findFirst().orElse(null);
        if (existente != null) {
            existente.setCantidad(existente.getCantidad()+cantidad);
        } else {
            detalle.add(new ItemVenta(p, cantidad));
        }
        detalleTableModel.setData(detalle);
        updateTotal();
        spnCantidad.setValue(1);
    }

    private void quitarItem() {
        int row = tableDetalle.getSelectedRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Seleccione un ítem del detalle"); return; }
        detalle.remove(row);
        detalleTableModel.setData(detalle);
        updateTotal();
    }

    private void confirmarVenta() {
        if (detalle.isEmpty()) { JOptionPane.showMessageDialog(this, "No hay productos en el detalle"); return; }
        double total = detalle.stream().mapToDouble(ItemVenta::getSubtotal).sum();
        try {
            ventaController.registrarVenta(0, currentUser, total);
            ventasTableModel.setData(ventaController.listarTodas());
            detalle.clear();
            detalleTableModel.setData(detalle);
            updateTotal();
            JOptionPane.showMessageDialog(this, "Venta registrada");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void anularVenta() {
        int row = tableVentas.getSelectedRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Seleccione una venta"); return; }
        Venta v = ventasTableModel.getAt(row);
        try {
            ventaController.anularVenta(v.getId());
            ventasTableModel.setData(ventaController.listarTodas());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateTotal() {
        double total = detalle.stream().mapToDouble(ItemVenta::getSubtotal).sum();
        lblTotal.setText("Total: $" + String.format("%,.0f", total));
    }

    public void refresh() { loadData(); }

    // TableModels internos
    private static class VentasTableModel extends AbstractTableModel {
        private final String[] cols = {"ID","Fecha","Usuario","Total","Estado"};
        private List<Venta> data = List.of();
        public void setData(List<Venta> ventas){ this.data = ventas; fireTableDataChanged(); }
        public Venta getAt(int row){ return data.get(row); }
        @Override public int getRowCount(){ return data.size(); }
        @Override public int getColumnCount(){ return cols.length; }
        @Override public String getColumnName(int column){ return cols[column]; }
        @Override public Object getValueAt(int rowIndex, int columnIndex){
            Venta v = data.get(rowIndex);
            return switch(columnIndex){
                case 0 -> v.getId();
                case 1 -> v.getFechaHora().format(DateTimeFormatter.ofPattern("dd/MM/yy HH:mm"));
                case 2 -> v.getUsuarioNombre();
                case 3 -> String.format("$%,.0f", v.getTotal());
                case 4 -> v.getEstado();
                default -> null; };
        }
        @Override public Class<?> getColumnClass(int columnIndex){ return (columnIndex==0)?Integer.class:String.class; }
        @Override public boolean isCellEditable(int rowIndex, int columnIndex){ return false; }
    }

    private static class ProductosVentaTableModel extends AbstractTableModel {
        private final String[] cols = {"ID","Nombre","Precio"};
        private List<Producto> data = List.of();
        public void setData(List<Producto> productos){ this.data = productos; fireTableDataChanged(); }
        public Producto getAt(int row){ return data.get(row); }
        @Override public int getRowCount(){ return data.size(); }
        @Override public int getColumnCount(){ return cols.length; }
        @Override public String getColumnName(int column){ return cols[column]; }
        @Override public Object getValueAt(int rowIndex, int columnIndex){
            Producto p = data.get(rowIndex);
            return switch(columnIndex){
                case 0 -> p.getId();
                case 1 -> p.getNombre();
                case 2 -> p.getPrecio();
                default -> null; };
        }
        @Override public Class<?> getColumnClass(int columnIndex){ return (columnIndex==0)?Integer.class:String.class; }
        @Override public boolean isCellEditable(int rowIndex, int columnIndex){ return false; }
    }

    private static class DetalleTableModel extends AbstractTableModel {
        private final String[] cols = {"#","Producto","Cantidad","Precio Unit","Subtotal"};
        private List<ItemVenta> data = List.of();
        public void setData(List<ItemVenta> items){ this.data = items; fireTableDataChanged(); }
        @Override public int getRowCount(){ return data.size(); }
        @Override public int getColumnCount(){ return cols.length; }
        @Override public String getColumnName(int column){ return cols[column]; }
        @Override public Object getValueAt(int rowIndex, int columnIndex){
            ItemVenta iv = data.get(rowIndex);
            return switch(columnIndex){
                case 0 -> rowIndex + 1;
                case 1 -> iv.getProducto().getNombre();
                case 2 -> iv.getCantidad();
                case 3 -> String.format("$%,.0f", iv.getProducto().getPrecio());
                case 4 -> String.format("$%,.0f", iv.getSubtotal());
                default -> null; };
        }
        @Override public Class<?> getColumnClass(int columnIndex){ return switch(columnIndex){ case 0,2 -> Integer.class; default -> String.class; }; }
        @Override public boolean isCellEditable(int rowIndex, int columnIndex){ return false; }
    }
}
