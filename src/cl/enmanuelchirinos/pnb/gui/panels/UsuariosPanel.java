package cl.enmanuelchirinos.pnb.gui.panels;

import cl.enmanuelchirinos.pnb.model.Usuario;
import cl.enmanuelchirinos.pnb.service.UsuarioService;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.List;

public class UsuariosPanel extends JPanel {
    private final UsuarioService usuarioService;
    private JTable table;
    private UsuariosTableModel tableModel;
    private JTextField txtUsername, txtNombre, txtBuscar;
    private JPasswordField txtPassword; // nuevo campo
    private JComboBox<String> cmbRol;
    private JCheckBox chkActivo;
    private JButton btnNuevo, btnGuardar, btnEliminar, btnLimpiar, btnCancelar, btnToggleActivo; // cambiar estado, cancelar edición

    public UsuariosPanel(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
        setLayout(new BorderLayout());
        initComponents();
        loadData();
    }

    private void initComponents() {
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(new JLabel("Buscar:"));
        txtBuscar = new JTextField(18); top.add(txtBuscar);
        JButton btnBuscar = new JButton("Filtrar");
        btnBuscar.addActionListener(e -> filter());
        top.add(btnBuscar);
        add(top, BorderLayout.NORTH);

        tableModel = new UsuariosTableModel();
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getSelectionModel().addListSelectionListener(e -> { if(!e.getValueIsAdjusting() && table.getSelectedRow()>=0) fillForm(table.getSelectedRow()); });
        table.addMouseListener(new java.awt.event.MouseAdapter() { @Override public void mouseClicked(java.awt.event.MouseEvent evt){ if (evt.getClickCount()==2){ int r=table.getSelectedRow(); if(r>=0) fillForm(r); } } });
        // Renderer columna activo
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){ @Override public Component getTableCellRendererComponent(JTable tbl,Object val,boolean sel,boolean focus,int row,int col){ Component c=super.getTableCellRendererComponent(tbl,val,sel,focus,row,col); if(col==4){ String v=(String)val; c.setForeground("Sí".equals(v)?new java.awt.Color(0,128,0):Color.RED);} else c.setForeground(Color.BLACK); return c; }});
        add(new JScrollPane(table), BorderLayout.CENTER);

        initComponentsForm();
        setupSearchListener();
    }

    private void initComponentsForm() {
        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createTitledBorder("Usuario"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5); gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx=0; gbc.gridy=0; form.add(new JLabel("Username:"), gbc);
        gbc.gridx=1; txtUsername = new JTextField(15); form.add(txtUsername, gbc);
        gbc.gridx=0; gbc.gridy++; form.add(new JLabel("Password:"), gbc);
        gbc.gridx=1; txtPassword = new JPasswordField(15); form.add(txtPassword, gbc);
        gbc.gridx=0; gbc.gridy++; form.add(new JLabel("Nombre:"), gbc);
        gbc.gridx=1; txtNombre = new JTextField(15); form.add(txtNombre, gbc);
        gbc.gridx=0; gbc.gridy++; form.add(new JLabel("Rol:"), gbc);
        gbc.gridx=1; cmbRol = new JComboBox<>(new String[]{"ADMIN","OPERADOR"}); form.add(cmbRol, gbc);
        gbc.gridx=0; gbc.gridy++; form.add(new JLabel("Activo:"), gbc);
        gbc.gridx=1; chkActivo = new JCheckBox(); chkActivo.setSelected(true); form.add(chkActivo, gbc);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnNuevo = new JButton("Nuevo"); btnGuardar = new JButton("Guardar"); btnEliminar = new JButton("Eliminar"); btnLimpiar = new JButton("Limpiar"); btnCancelar = new JButton("Cancelar"); btnToggleActivo = new JButton("Cambiar Estado");
        buttons.add(btnNuevo); buttons.add(btnGuardar); buttons.add(btnEliminar); buttons.add(btnLimpiar); buttons.add(btnCancelar); buttons.add(btnToggleActivo);
        gbc.gridx=0; gbc.gridy++; gbc.gridwidth=2; form.add(buttons, gbc);

        btnNuevo.addActionListener(evt -> nuevo());
        btnGuardar.addActionListener(evt -> guardar());
        btnEliminar.addActionListener(evt -> eliminar());
        btnLimpiar.addActionListener(evt -> limpiarFormulario());
        btnCancelar.addActionListener(evt -> limpiarFormulario());
        btnToggleActivo.addActionListener(evt -> toggleEstado());

        add(form, BorderLayout.EAST);
    }

    private void loadData() { tableModel.setData(usuarioService.listAll()); }
    private void filter() {
        String txt = txtBuscar.getText().trim();
        if (txt.isEmpty()) loadData(); else tableModel.setData(usuarioService.searchByUsername(txt));
    }

    private void setupSearchListener() {
        txtBuscar.getDocument().addDocumentListener(new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e){ filterRealtime(); }
            @Override public void removeUpdate(DocumentEvent e){ filterRealtime(); }
            @Override public void changedUpdate(DocumentEvent e){ }
            private void filterRealtime() { String txt = txtBuscar.getText().trim(); tableModel.setData(txt.isEmpty()? usuarioService.listAll(): usuarioService.searchByUsername(txt)); }
        });
    }

    private void fillForm(int row) {
        Usuario u = tableModel.getAt(row);
        txtUsername.setText(u.getUsername());
        txtPassword.setText(u.getPassword());
        txtNombre.setText(u.getNombreCompleto());
        cmbRol.setSelectedItem(u.getRol());
        chkActivo.setSelected(u.isActivo());
    }

    private void nuevo() { limpiarFormulario(); table.clearSelection(); }
    private void guardar() {
        String username = txtUsername.getText().trim();
        String nombre = txtNombre.getText().trim();
        String password = new String(txtPassword.getPassword());
        if (username.isEmpty() || nombre.isEmpty() || password.isEmpty()) { JOptionPane.showMessageDialog(this, "Campos requeridos"); return; }
        String rol = (String) cmbRol.getSelectedItem();
        boolean activo = chkActivo.isSelected();
        int selected = table.getSelectedRow();
        if (selected >= 0) {
            Usuario original = tableModel.getAt(selected);
            Usuario actualizado = new Usuario(original.getId(), username, password, nombre, rol, activo);
            usuarioService.update(actualizado);
        } else {
            usuarioService.add(new Usuario(0, username, password, nombre, rol, activo));
        }
        loadData(); limpiarFormulario(); JOptionPane.showMessageDialog(this, "Usuario guardado");
    }

    private void eliminar() {
        int row = table.getSelectedRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Seleccione un usuario"); return; }
        Usuario u = tableModel.getAt(row);
        int confirm = JOptionPane.showConfirmDialog(this, "Eliminar usuario?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) { usuarioService.deleteById(u.getId()); loadData(); limpiarFormulario(); }
    }

    private void toggleEstado() {
        int row = table.getSelectedRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Seleccione un usuario"); return; }
        Usuario u = tableModel.getAt(row);
        usuarioService.toggleActive(u.getId(), !u.isActivo());
        loadData();
    }

    private void limpiarFormulario() { txtUsername.setText(""); txtPassword.setText(""); txtNombre.setText(""); cmbRol.setSelectedIndex(0); chkActivo.setSelected(true); table.clearSelection(); }
    public void refresh() { loadData(); }

    // TableModel interno
    private static class UsuariosTableModel extends AbstractTableModel {
        private final String[] cols = {"ID","Username","Nombre","Rol","Activo"};
        private List<Usuario> data = List.of();
        public void setData(List<Usuario> usuarios){ this.data = usuarios; fireTableDataChanged(); }
        public Usuario getAt(int row){ return data.get(row); }
        @Override public int getRowCount(){ return data.size(); }
        @Override public int getColumnCount(){ return cols.length; }
        @Override public String getColumnName(int column){ return cols[column]; }
        @Override public Object getValueAt(int rowIndex, int columnIndex){
            Usuario u = data.get(rowIndex);
            return switch(columnIndex){
                case 0 -> u.getId();
                case 1 -> u.getUsername();
                case 2 -> u.getNombreCompleto();
                case 3 -> u.getRol();
                case 4 -> u.isActivo() ? "Sí" : "No";
                default -> null; };
        }
        @Override public Class<?> getColumnClass(int columnIndex){
            return switch(columnIndex){
                case 0 -> Integer.class;
                default -> String.class; };
        }
        @Override public boolean isCellEditable(int rowIndex, int columnIndex){ return false; }
    }
}
