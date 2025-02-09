package gn.hotel.swing;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import gn.hotel.theme.ThemeManager;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;

public class KButton extends JButton {

    private FlatSVGIcon icon;

    public KButton(String text, Icon icon, boolean isDarkMode) {
        super(text, icon);
        this.icon = (FlatSVGIcon) icon;
        this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    public KButton() {
        this("N/A", new FlatSVGIcon("svgs/none.svg"), new ThemeManager().loadThemePreference() );
    }

    public KButton(Icon icon, String toolTipText) {
        this("", icon, new ThemeManager().loadThemePreference());
        setToolTipText(toolTipText);
    }

    @Override
    public void setToolTipText(String text) {
        super.setToolTipText(text);
    }

    private void propertyChangeListener(PropertyChangeEvent propertyChangeEvent){
        boolean isDarkMode = (boolean) propertyChangeEvent.getNewValue();
        applyIconColor(isDarkMode);
    }

    private void applyIconColor(boolean isDarkMode) {
        icon.setColorFilter(!isDarkMode ? null : new FlatSVGIcon.ColorFilter(c -> Color.WHITE));
        repaint();
    }

    public void setIcon(FlatSVGIcon icon) {
        this.icon = icon;
        super.setIcon(icon);
    }
}
