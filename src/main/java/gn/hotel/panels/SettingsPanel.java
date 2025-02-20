package gn.hotel.panels;

import com.formdev.flatlaf.FlatClientProperties;
import gn.hotel.base.KPanel;
import net.miginfocom.swing.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Properties;

public class SettingsPanel extends KPanel {
    private final Properties settings;
    private final String CONFIG_FILE = "config.properties";

    // Composants UI
    private JTextField dbHostField;
    private JTextField dbPortField;
    private JTextField dbNameField;
    private JTextField dbUserField;
    private JPasswordField dbPasswordField;
    private JComboBox<String> themeComboBox;
    private JSpinner fontSizeSpinner;
    private JCheckBox autoSaveCheckBox;
    private JSpinner autoSaveIntervalSpinner;
    private JCheckBox enableNotificationsCheckBox;
    private JTextField backupPathField;
    private JSpinner sessionTimeoutSpinner;

    public SettingsPanel() {
        settings = loadSettings();
        initializeComponents();
        setupLayout();
        loadCurrentSettings();
    }

    private void initializeComponents() {
        // Base de données
        dbHostField = new JTextField(20);
        dbPortField = new JTextField(6);
        dbNameField = new JTextField(20);
        dbUserField = new JTextField(20);
        dbPasswordField = new JPasswordField(20);

        // Interface utilisateur
        themeComboBox = new JComboBox<>(new String[]{"Light", "Dark", "System"});
        fontSizeSpinner = new JSpinner(new SpinnerNumberModel(12, 8, 24, 1));

        // Sauvegarde automatique
        autoSaveCheckBox = new JCheckBox("Activer la sauvegarde automatique");
        autoSaveIntervalSpinner = new JSpinner(new SpinnerNumberModel(5, 1, 60, 1));

        // Notifications
        enableNotificationsCheckBox = new JCheckBox("Activer les notifications");

        // Backup
        backupPathField = new JTextField(30);
        JButton browseButton = new JButton("Parcourir");
        browseButton.addActionListener(e -> chooseBackupPath());

        // Session
        sessionTimeoutSpinner = new JSpinner(new SpinnerNumberModel(30, 5, 120, 5));
    }

    private void setupLayout() {
        setLayout(new MigLayout("fillx, insets 20", "[grow]"));

        // Section Base de données
        JPanel dbPanel = createSection("Configuration Base de données", new MigLayout("fillx", "[][grow]"));
        dbPanel.add(new JLabel("Hôte:"), "");
        dbPanel.add(dbHostField, "growx, wrap");
        dbPanel.add(new JLabel("Port:"), "");
        dbPanel.add(dbPortField, "growx, wrap");
        dbPanel.add(new JLabel("Nom de la base:"), "");
        dbPanel.add(dbNameField, "growx, wrap");
        dbPanel.add(new JLabel("Utilisateur:"), "");
        dbPanel.add(dbUserField, "growx, wrap");
        dbPanel.add(new JLabel("Mot de passe:"), "");
        dbPanel.add(dbPasswordField, "growx, wrap");
        add(dbPanel, "growx, wrap 20");

        // Section Interface
        JPanel uiPanel = createSection("Interface utilisateur", new MigLayout("fillx", "[][grow]"));
        uiPanel.add(new JLabel("Thème:"), "");
        uiPanel.add(themeComboBox, "growx, wrap");
        uiPanel.add(new JLabel("Taille de police:"), "");
        uiPanel.add(fontSizeSpinner, "growx, wrap");
        add(uiPanel, "growx, wrap 20");

        // Section Sauvegarde
        JPanel savePanel = createSection("Sauvegarde", new MigLayout("fillx", "[][grow]"));
        savePanel.add(autoSaveCheckBox, "span 2, wrap");
        savePanel.add(new JLabel("Intervalle (minutes):"), "");
        savePanel.add(autoSaveIntervalSpinner, "growx, wrap");
        add(savePanel, "growx, wrap 20");

        // Section Notifications
        JPanel notifPanel = createSection("Notifications", new MigLayout("fillx", "[][grow]"));
        notifPanel.add(enableNotificationsCheckBox, "span 2, wrap");
        add(notifPanel, "growx, wrap 20");

        // Boutons de contrôle
        JPanel buttonPanel = new JPanel(new MigLayout("fillx, insets 0", "[grow][][]"));
        JButton saveButton = new JButton("Enregistrer");
        JButton cancelButton = new JButton("Annuler");

        saveButton.addActionListener(e -> saveSettings());
        cancelButton.addActionListener(e -> loadCurrentSettings());

        buttonPanel.add(new JLabel(), "growx");
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, "growx");
    }

    private JPanel createSection(String title, LayoutManager layout) {
        JPanel panel = new JPanel(layout);
        panel.putClientProperty(FlatClientProperties.STYLE, "arc: 8");
        panel.setBorder(BorderFactory.createTitledBorder(title));
        return panel;
    }

    private void chooseBackupPath() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            backupPathField.setText(chooser.getSelectedFile().getAbsolutePath());
        }
    }

    private Properties loadSettings() {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream(CONFIG_FILE)) {
            props.load(fis);
        } catch (IOException e) {
            // Utiliser les valeurs par défaut si le fichier n'existe pas
            setDefaultSettings(props);
        }
        return props;
    }

    private void setDefaultSettings(Properties props) {
        props.setProperty("db.host", "localhost");
        props.setProperty("db.port", "1521");
        props.setProperty("db.name", "HOTEL");
        props.setProperty("db.user", "admin");
        props.setProperty("theme", "Light");
        props.setProperty("fontSize", "12");
        props.setProperty("autoSave", "true");
        props.setProperty("autoSaveInterval", "5");
        props.setProperty("notifications", "true");
        props.setProperty("backupPath", System.getProperty("user.home"));
        props.setProperty("sessionTimeout", "30");
    }

    private void loadCurrentSettings() {
        dbHostField.setText(settings.getProperty("db.host", "localhost"));
        dbPortField.setText(settings.getProperty("db.port", "1521"));
        dbNameField.setText(settings.getProperty("db.name", "HOTEL"));
        dbUserField.setText(settings.getProperty("db.user", "admin"));
        dbPasswordField.setText(settings.getProperty("db.password", ""));
        themeComboBox.setSelectedItem(settings.getProperty("theme", "Light"));
        fontSizeSpinner.setValue(Integer.parseInt(settings.getProperty("fontSize", "12")));
        autoSaveCheckBox.setSelected(Boolean.parseBoolean(settings.getProperty("autoSave", "true")));
        autoSaveIntervalSpinner.setValue(Integer.parseInt(settings.getProperty("autoSaveInterval", "5")));
        enableNotificationsCheckBox.setSelected(Boolean.parseBoolean(settings.getProperty("notifications", "true")));
        backupPathField.setText(settings.getProperty("backupPath", System.getProperty("user.home")));
        sessionTimeoutSpinner.setValue(Integer.parseInt(settings.getProperty("sessionTimeout", "30")));
    }

    private void saveSettings() {
        settings.setProperty("db.host", dbHostField.getText());
        settings.setProperty("db.port", dbPortField.getText());
        settings.setProperty("db.name", dbNameField.getText());
        settings.setProperty("db.user", dbUserField.getText());
        settings.setProperty("db.password", new String(dbPasswordField.getPassword()));
        settings.setProperty("theme", (String) themeComboBox.getSelectedItem());
        settings.setProperty("fontSize", fontSizeSpinner.getValue().toString());
        settings.setProperty("autoSave", String.valueOf(autoSaveCheckBox.isSelected()));
        settings.setProperty("autoSaveInterval", autoSaveIntervalSpinner.getValue().toString());
        settings.setProperty("notifications", String.valueOf(enableNotificationsCheckBox.isSelected()));
        settings.setProperty("backupPath", backupPathField.getText());
        settings.setProperty("sessionTimeout", sessionTimeoutSpinner.getValue().toString());

        try (FileOutputStream fos = new FileOutputStream(CONFIG_FILE)) {
            settings.store(fos, "Application Settings");
            JOptionPane.showMessageDialog(this,
                    "Paramètres enregistrés avec succès",
                    "Succès",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                    "Erreur lors de l'enregistrement des paramètres: " + e.getMessage(),
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}