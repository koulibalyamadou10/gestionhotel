package gn.hotel.components;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import gn.hotel.base.KPanel;
import gn.hotel.main.Main;
import gn.hotel.panels.*;
import gn.hotel.swing.KButton;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static gn.hotel.main.Main.*;

public class KMenuLeft extends KPanel {

    private Main main;

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(K_MENU_LEFT_WIDTH, super.getPreferredSize().height);
    }

    @Override
    public Dimension getMaximumSize() {
        return new Dimension(K_MENU_LEFT_WIDTH, super.getPreferredSize().height);
    }

    @Override
    public Dimension getMinimumSize() {
        return new Dimension(K_MENU_LEFT_WIDTH, super.getPreferredSize().height);
    }

    @Override
    public Color getBackground() {
        return Color.GRAY;
    }

    public KMenuLeft(Main main) {
        super();
        this.main = main;
        setOpaque(false);
        setLayout(new MigLayout());
        initUI();
        addMouseListener(mouseAdapter);
    }

    private MouseAdapter mouseAdapter = new MouseAdapter() {
        @Override
        public void mouseEntered(MouseEvent e) {
            super.mouseEntered(e);
            setPreferredSize(new Dimension(300, KMenuLeft.super.getPreferredSize().height));
            main.repaint();
            main.revalidate();
        }

        @Override
        public void mouseExited(MouseEvent e) {
            super.mouseExited(e);
            System.out.println("mouseexited");
        }
    };

    private void initUI(){
        FlatSVGIcon[] flatSVGIcons = {
                clientIcon,
                reservationIcon,
                chambreIcon,
                employeeIcon,
                serviceIcon,
                factureIcon,
                statistiqueIcon,
                settingsIcon,
                logoutIcon
        };
        String[] toolTipTexts = {
                MENU_LEFT_ELEMENT_CLIENT,
                MENU_LEFT_ELEMENT_RESERVATION,
                MENU_LEFT_ELEMENT_CHAMBRE,
                MENU_LEFT_ELEMENT_EMPLOYEE,
                MENU_LEFT_ELEMENT_SERVICE,
                MENU_LEFT_ELEMENT_FACTURE,
                MENU_LEFT_ELEMENT_STATISTIQUE,
                MENU_LEFT_ELEMENT_SETTINGS,
                MENU_LEFT_ELEMENT_LOGOUT
        };

        for (int i = 0; i < flatSVGIcons.length; i++) {
            FlatSVGIcon flatSVGIcon = flatSVGIcons[i];
            String toolTipText = toolTipTexts[i];
            KButton kButton = new KButton(flatSVGIcon, toolTipText);
            kButton.addActionListener(this::kbuttonListener);

            // ajout d'un espace vide avant le button deconnecter
            if( i == flatSVGIcons.length - 1 ) add( new NonePanelWithOutOpacity(), "height 100% ,wrap" );

            add(kButton, "wrap");
        }
    }

    private void kbuttonListener(ActionEvent actionEvent) {
        JButton button = (JButton) actionEvent.getSource();
        String toolTipText = button.getToolTipText();
        System.out.println(toolTipText);
        switch (toolTipText) {
            case MENU_LEFT_ELEMENT_CLIENT -> updateMainUI(new ClientPanel());
            case MENU_LEFT_ELEMENT_RESERVATION -> updateMainUI(new ReservationPanel());
            case MENU_LEFT_ELEMENT_CHAMBRE -> updateMainUI(new ChambrePanel());
            case MENU_LEFT_ELEMENT_FACTURE -> updateMainUI(new FacturePanel());
            case MENU_LEFT_ELEMENT_EMPLOYEE -> updateMainUI(new EmployePanel());
            case MENU_LEFT_ELEMENT_SERVICE -> updateMainUI(new ServicePanel());
            case MENU_LEFT_ELEMENT_STATISTIQUE -> updateMainUI(new StatistiquePanel());
            case MENU_LEFT_ELEMENT_SETTINGS -> updateMainUI(new SettingsPanel());
            case MENU_LEFT_ELEMENT_LOGOUT -> btnLogoutListener(actionEvent);
        }
    }

    private void updateMainUI(Component component) {
        JPanel contentPane = (JPanel) this.main.getContentPane();
        Component centerComponent = contentPane.getComponent(1);
        contentPane.remove(centerComponent);
        contentPane.add(component, BorderLayout.CENTER);
        contentPane.repaint();
        contentPane.revalidate();
    }

    private void btnLogoutListener(ActionEvent actionEvent) {
        int question = JOptionPane.showConfirmDialog(this, "Êtes vous sur de vous deconnecter ?", "Deconnexion", JOptionPane.YES_NO_OPTION);
        if( question == JOptionPane.YES_OPTION )
            System.exit(0);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // draw in rounded border
        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(getBackground());
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
    }
}
