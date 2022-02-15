import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;

public class GUIController extends WindowAdapter implements ActionListener {

    private final GUI gui;

    public GUIController(GUI gui) {
        this.gui = gui;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.gui.getBtnStart()) {
            this.gui.start();
        }
        else if (e.getSource() == this.gui.getBtnPause()) {
            this.gui.pauseToggle();
        }
        else if (e.getSource() == this.gui.getBtnStop()) {
            this.gui.stop();
            try {
                this.writeDataToFile(this.gui.calculateHoursAndMinutesToHoursDecimal() + "");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        else if (e.getSource() == this.gui.getTimer()) {
            this.gui.timerHandle();
        }
    }

    @Override
    public void windowClosing(WindowEvent e) {
        this.gui.stop();
        System.exit(0);
    }

    private void writeDataToFile(String text) throws IOException {

        PrintWriter pw = new PrintWriter(new FileWriter(new File("data/worktime.csv").getAbsolutePath(), true));
        pw.println(String.format("%s,%s", LocalDate.now(), text));
        pw.close();
    }
}
