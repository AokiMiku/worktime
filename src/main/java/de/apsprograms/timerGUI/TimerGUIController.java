package de.apsprograms.timerGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;

import de.apsprograms.data.Day;


public class TimerGUIController extends WindowAdapter implements ActionListener {

    private final TimerGUI timerGui;

    public TimerGUIController(TimerGUI timerGui) {
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
            this.timerGui.getOps().writeData(new Day(LocalDate.now(),
                                                     this.timerGui.calculateHoursAndMinutesToHoursDecimal(),
                                                     true,
                                                     this.timerGui.getPauseSeconds()));
//                utils.fileOps.writeDataToDataFile(fileOps.dataFilePath,
//                                                  this.timerGui.calculateHoursAndMinutesToHoursDecimal() + "",
//                                                  this.timerGui.getPauseSeconds() + "");
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
