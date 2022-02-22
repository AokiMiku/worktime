package timerGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;


public class timerGUIController extends WindowAdapter implements ActionListener {

    private final timerGUI timerGui;

    public timerGUIController(timerGUI timerGui) {
        this.timerGui = timerGui;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.timerGui.getBtnStart()) {
            this.timerGui.start();
        }
        else if (e.getSource() == this.timerGui.getBtnPause()) {
            this.timerGui.pauseToggle();
        }
        else if (e.getSource() == this.timerGui.getBtnStop()) {
            this.timerGui.stop();
            try {
                utils.fileOps.writeDataToDataFile(this.timerGui.calculateHoursAndMinutesToHoursDecimal() + "");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        else if (e.getSource() == this.timerGui.getTimer()) {
            this.timerGui.timerHandle();
        }
    }

    @Override
    public void windowClosing(WindowEvent e) {
        this.timerGui.stop();
        System.exit(0);
    }
}
