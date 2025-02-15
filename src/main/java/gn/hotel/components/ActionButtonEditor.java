package gn.hotel.components;

import javax.swing.*;
import java.awt.*;

public class ActionButtonEditor extends DefaultCellEditor {
    private JPanel panel;
    private JButton btnView;
    private JButton btnEdit;
    private JButton btnDelete;
    private String currentAction = "";
    private JTable table;

    public ActionButtonEditor(JTable table) {
        super(new JTextField());
        this.table = table;
        panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));

        // Créer les boutons avec des icônes
        btnView = new JButton(new ImageIcon(getClass().getResource("/icons/view.png")));
        btnEdit = new JButton(new ImageIcon(getClass().getResource("/icons/edit.png")));
        btnDelete = new JButton(new ImageIcon(getClass().getResource("/icons/delete.png")));

        // Styliser les boutons
        styleButton(btnView);
        styleButton(btnEdit);
        styleButton(btnDelete);

        // Ajouter les listeners
        btnView.addActionListener(e -> {
            currentAction = "view";
            fireEditingStopped();
        });

        btnEdit.addActionListener(e -> {
            currentAction = "edit";
            fireEditingStopped();
        });

        btnDelete.addActionListener(e -> {
            currentAction = "delete";
            fireEditingStopped();
        });

        // Ajouter les boutons au panel
        panel.add(btnView);
        panel.add(btnEdit);
        panel.add(btnDelete);
        panel.setBackground(Color.WHITE);
    }

    private void styleButton(JButton button) {
        button.setPreferredSize(new Dimension(24, 24));
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
                                                 boolean isSelected, int row, int column) {
        return panel;
    }

    @Override
    public Object getCellEditorValue() {
        return currentAction;
    }
}