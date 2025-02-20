package gn.hotel.main;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import gn.hotel.components.KMenuLeft;
import gn.hotel.panels.*;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {

    // Gestion du thème
    public static Main main;

    // Constructeur principal
    public Main() {
        initUI();
        setupFrame();
        setupComponents();
        main = this;
    }

    // Initialisation de l'interface utilisateur et du thème
    private void initUI() {
        UIManager.put("TextComponent.arc", 10);
        FlatLaf.registerCustomDefaultsSource("design");
        FlatRobotoFont.install();
        UIManager.put("defaultFont", new Font(FlatRobotoFont.FAMILY, Font.PLAIN, 16));
        FlatMacLightLaf.setup();
    }

    // Configuration générale de la fenêtre
    private void setupFrame() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Positionnement au centre de l'écran
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension size = new Dimension(900, 600);
        int x = (screenSize.width - size.width) / 2;
        int y = (screenSize.height - size.height) / 2;
        setLocation(x, y);
        setSize(size);
        setVisible(true);
    }

    // Création et disposition des composants de l'interface
    private void setupComponents() {
        JPanel contentPane = (JPanel) getContentPane();
        contentPane.setLayout(new BorderLayout());


        contentPane.add(new KMenuLeft(this), BorderLayout.WEST);
        contentPane.add(new ClientPanel(), BorderLayout.CENTER);
    }

    // Point d'entrée de l'application
    public static void main(String[] args) {
        EventQueue.invokeLater(Main::new);
    }
}
