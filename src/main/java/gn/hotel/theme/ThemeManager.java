package gn.hotel.theme;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class ThemeManager {

    private static final PropertyChangeSupport support = new PropertyChangeSupport(ThemeManager.class);
    private static final String THEME_FILE = "theme.properties";
    private static final String THEME_KEY = "isDarkMode";
    private static boolean isDarkMode;

    public ThemeManager() {}

    public boolean loadThemePreference() {
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream(THEME_FILE)) {
            properties.load(fis);
            isDarkMode = Boolean.parseBoolean(properties.getProperty(THEME_KEY, "true"));
            support.firePropertyChange(THEME_KEY, !isDarkMode, isDarkMode);
            return isDarkMode;
        } catch (IOException e) {
            return true; // Mode sombre par défaut si le fichier n'existe pas
        }
    }

    public void saveThemePreference(boolean isDarkMode) {
        ThemeManager.isDarkMode = isDarkMode;
        support.firePropertyChange(THEME_KEY, !isDarkMode, isDarkMode);
        Properties properties = new Properties();
        properties.setProperty(THEME_KEY, Boolean.toString(isDarkMode));
        try (FileOutputStream fos = new FileOutputStream(THEME_FILE)) {
            properties.store(fos, "User theme preference");
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
    }

    // Ajoute un écouteur pour surveiller les changements de la propriété "isDarkMode"
    public static void addIsDarkModeChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(THEME_KEY, listener);
    }

    // Obtient l'état actuel du thème
    public static boolean isDarkMode() {
        return isDarkMode;
    }
}

