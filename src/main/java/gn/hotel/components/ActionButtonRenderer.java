package gn.hotel.components;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

import static gn.hotel.base.KPanel.ICON_SIZE;

public class ActionButtonRenderer extends JPanel implements TableCellRenderer {
    private JButton btnView;
    private JButton btnEdit;
    private JButton btnDelete;

    public ActionButtonRenderer() {
        setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));

        // Créer les boutons avec des icônes
        btnView = new JButton(new ImageIcon(new ImageIcon(getClass().getResource("/icons/view.png")).getImage().getScaledInstance(ICON_SIZE, ICON_SIZE, Image.SCALE_SMOOTH)));
        btnEdit = new JButton(new ImageIcon(new ImageIcon(getClass().getResource("/icons/edit.png")).getImage().getScaledInstance(ICON_SIZE, ICON_SIZE, Image.SCALE_SMOOTH)));
        btnDelete = new JButton(new ImageIcon(new ImageIcon(getClass().getResource("/icons/delete.png")).getImage().getScaledInstance(ICON_SIZE, ICON_SIZE, Image.SCALE_SMOOTH)));

        // Styliser les boutons
        styleButton(btnView);
        styleButton(btnEdit);
        styleButton(btnDelete);

        // Ajouter les boutons au panel
        add(btnView);
        add(btnEdit);
        add(btnDelete);

        setBackground(Color.WHITE);
    }

    private void styleButton(JButton button) {
        button.setPreferredSize(new Dimension(24, 24));
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column) {
        if (isSelected) {
            setBackground(table.getSelectionBackground());
        } else {
            setBackground(table.getBackground());
        }
        return this;
    }
}