package datawrapGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import lombok.Getter;

@Getter
public class wrapGUIController extends WindowAdapter implements ActionListener {

    private final wrapGUI wrapGUI;

    public wrapGUIController (wrapGUI wrapGUI) {
        this.wrapGUI = wrapGUI;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        System.exit(0);
    }
}
