import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class GUIController extends WindowAdapter implements ActionListener {

    private GUI gui;

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
}
