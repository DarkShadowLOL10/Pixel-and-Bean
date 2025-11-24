package cl.enmanuelchirinos.pnb.gui.panels;

import cl.enmanuelchirinos.pnb.model.Venta;
import cl.enmanuelchirinos.pnb.model.Producto;
import cl.enmanuelchirinos.pnb.service.ProductoService;
import cl.enmanuelchirinos.pnb.service.VentaService;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ReportesPanel extends JPanel {
    private final VentaService ventaService;
    private final ProductoService productoService;
    private JTable tableVentasDia;
    private VentasDiaTableModel ventasDiaTableModel;
    private JLabel lblTotalDia;
    private JButton btnRefrescar;
    private JComboBox<String> cmbPeriodo;
    private JTable tableTopProductos;
    private TopProductosTableModel topModel;
    private JLabel lblTopTitulo;

    public ReportesPanel(VentaService ventaService, ProductoService productoService) {
        this.ventaService = ventaService;
        this.productoService = productoService;
        setLayout(new BorderLayout());
        initComponents();
        loadData();
    }

    private void initComponents() {
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(new JLabel("Reportes de Ventas"));
        cmbPeriodo = new JComboBox<>(new String[]{"Hoy","Ayer","Última semana","Último mes"});
        top.add(cmbPeriodo);
        btnRefrescar = new JButton("Refrescar");
        btnRefrescar.addActionListener(e -> loadData());
        top.add(btnRefrescar);
        add(top, BorderLayout.NORTH);

        ventasDiaTableModel = new VentasDiaTableModel();
        tableVentasDia = new JTable(ventasDiaTableModel);
        tableVentasDia.setDefaultRenderer(Object.class,new DefaultTableCellRenderer(){ @Override public Component getTableCellRendererComponent(JTable t,Object v,boolean s,boolean f,int r,int c){ Component comp=super.getTableCellRendererComponent(t,v,s,f,r,c); if(c==3 && v instanceof Double d){ setText(String.format("$%,.0f", d)); comp.setForeground(new java.awt.Color(0,64,128)); } return comp; }});

        // Top productos sección
        JPanel right = new JPanel(new BorderLayout());
        lblTopTitulo = new JLabel("Top Productos (Precio)", SwingConstants.CENTER);
        topModel = new TopProductosTableModel();
        tableTopProductos = new JTable(topModel);
        tableTopProductos.setDefaultRenderer(Object.class,new DefaultTableCellRenderer(){ @Override public Component getTableCellRendererComponent(JTable t,Object v,boolean s,boolean f,int r,int c){ Component comp=super.getTableCellRendererComponent(t,v,s,f,r,c); if(c==1 && v instanceof Double d){ setText(String.format("$%,.0f", d)); comp.setForeground(r==0?new java.awt.Color(0,128,0):Color.BLACK);} return comp; }});
        right.add(lblTopTitulo, BorderLayout.NORTH);
        right.add(new JScrollPane(tableTopProductos), BorderLayout.CENTER);
        JPanel centerWrapper = new JPanel(new GridLayout(1,2));
        centerWrapper.add(new JScrollPane(tableVentasDia));
        centerWrapper.add(right);
        add(centerWrapper, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lblTotalDia = new JLabel("Total día: $0");
        bottom.add(lblTotalDia);
        add(bottom, BorderLayout.SOUTH);
    }

    private void loadData() {
        LocalDate hoy = LocalDate.now();
        List<Venta> filtradas;
        String periodo = (String) cmbPeriodo.getSelectedItem();
        switch (periodo) {
            case "Ayer" -> filtradas = ventaService.listByDate(hoy.minusDays(1)).stream().filter(v->"ACTIVA".equals(v.getEstado())).toList();
            case "Última semana" -> filtradas = ventaService.listAll().stream().filter(v -> v.getFechaHora().toLocalDate().isAfter(hoy.minusDays(7)) && v.getFechaHora().toLocalDate().isBefore(hoy.plusDays(1)) && "ACTIVA".equals(v.getEstado())).toList();
            case "Último mes" -> filtradas = ventaService.listAll().stream().filter(v -> v.getFechaHora().toLocalDate().isAfter(hoy.minusDays(30)) && v.getFechaHora().toLocalDate().isBefore(hoy.plusDays(1)) && "ACTIVA".equals(v.getEstado())).toList();
            default -> filtradas = ventaService.listToday().stream().filter(v->"ACTIVA".equals(v.getEstado())).toList();
        }
        ventasDiaTableModel.setData(filtradas);
        double total = filtradas.stream().mapToDouble(Venta::getTotal).sum();
        lblTotalDia.setText("Total periodo: $" + String.format("%,.0f", total));
        // Top productos simple por precio mayor
        topModel.setData(productoService.listActive().stream().sorted(Comparator.comparingDouble(Producto::getPrecio).reversed()).limit(5).toList());
    }

    public void refresh() { loadData(); }

    private static class VentasDiaTableModel extends AbstractTableModel {
        private final String[] cols = {"ID","Fecha","Usuario","Total"};
        private List<Venta> data = List.of();
        public void setData(List<Venta> ventas){ this.data = ventas; fireTableDataChanged(); }
        @Override public int getRowCount(){ return data.size(); }
        @Override public int getColumnCount(){ return cols.length; }
        @Override public String getColumnName(int column){ return cols[column]; }
        @Override public Object getValueAt(int rowIndex, int columnIndex){
            Venta v = data.get(rowIndex);
            return switch(columnIndex){
                case 0 -> v.getId();
                case 1 -> v.getFechaHora().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
                case 2 -> v.getUsuarioNombre();
                case 3 -> v.getTotal();
                default -> null; };
        }
        @Override public Class<?> getColumnClass(int columnIndex){ return (columnIndex==0)?Integer.class:String.class; }
        @Override public boolean isCellEditable(int rowIndex, int columnIndex){ return false; }
    }

    // Nuevo modelo top productos
    private static class TopProductosTableModel extends AbstractTableModel {
        private final String[] cols = {"Nombre","Precio"};
        private List<Producto> data = List.of();
        public void setData(List<Producto> productos){ this.data = productos; fireTableDataChanged(); }
        @Override public int getRowCount(){ return data.size(); }
        @Override public int getColumnCount(){ return cols.length; }
        @Override public String getColumnName(int column){ return cols[column]; }
        @Override public Object getValueAt(int rowIndex, int columnIndex){
            Producto p = data.get(rowIndex);
            return switch(columnIndex){
                case 0 -> p.getNombre();
                case 1 -> p.getPrecio();
                default -> null; };
        }
        @Override public Class<?> getColumnClass(int columnIndex){ return columnIndex==1?Double.class:String.class; }
        @Override public boolean isCellEditable(int rowIndex, int columnIndex){ return false; }
    }
}
