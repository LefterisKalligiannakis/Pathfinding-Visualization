/*
    Just some stuff on the ui to handle some user options
    for the visualization.

    @author Eleftherios Kalligiannakis
 */

import javax.swing.JPanel;
import javax.swing.JCheckBox;

public class OptionHandler extends JPanel {
    JCheckBox diagonals;
    JCheckBox visualize;


    public OptionHandler() {
        diagonals = new JCheckBox("Diagonals");
        visualize = new JCheckBox("Visualize");
        add(diagonals);
        add(visualize);
        setOpaque(true); // Make the panel opaque
        setVisible(true);
    }


}
