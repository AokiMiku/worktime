package de.aps_programs.views;

import javax.swing.*;
import java.time.LocalDate;

import de.aps_programs.controllers.WorktimesController;
import de.aps_programs.data.WorktimesVO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;


@Getter @Setter
@Component
public class Timer extends JFrame {

    private WorktimesController controller;
    private JPanel pnlView;
    private JPanel pnlTime;
    private JPanel pnlButtons;
    private JLabel lblTime;
    private JLabel lblTimeDecimal;
    private JLabel lblPauseTime;
    private JButton btnStart;
    private JButton btnPause;
    private JButton btnStop;

    public Timer(WorktimesController controller) {
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.setSize(240, 140);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setTitle("ApS Util - Worktime");
        this.controller = controller;
        this.controller.setGUI(this);

        this.initComponents();
        this.addComponents();
        this.setVisible(true);
    }

    public void initComponents() {

        this.addWindowListener(this.controller);

        this.controller.setWtVO(new WorktimesVO(LocalDate.now().toString(), 0, true, 0));

        this.pnlView = new JPanel();

        this.pnlTime = new JPanel();
        this.pnlTime.setLayout(new BoxLayout(this.pnlTime, BoxLayout.Y_AXIS));

        this.pnlButtons = new JPanel();

        this.lblTime = new JLabel(this.controller.defaultTextForLblTime);

        this.lblTimeDecimal = new JLabel(this.controller.defaultTextForLblTimeDecimal);

        this.lblPauseTime = new JLabel(this.controller.defaultTextForLblTime);

        this.btnStart = new JButton("Start");
        this.btnStart.setSize(50, 25);
        this.btnStart.addActionListener(this.controller);

        this.btnPause = new JButton("Pause");
        this.btnPause.setSize(50, 25);
        this.btnPause.addActionListener(this.controller);

        this.btnStop = new JButton("Stop");
        this.btnStop.setSize(50, 25);
        this.btnStop.addActionListener(this.controller);
    }

    public void addComponents() {

        this.pnlTime.add(this.lblTime);
        this.pnlTime.add(this.lblTimeDecimal);
        this.pnlTime.add(this.lblPauseTime);

        this.pnlView.add(this.pnlTime);

        this.pnlButtons.add(this.btnStart);
        this.pnlButtons.add(this.btnPause);
        this.pnlButtons.add(this.btnStop);

        this.pnlView.add(this.pnlButtons);
        this.setContentPane(this.pnlView);
    }

    public void actualizeLblTime() {

        this.lblTime.setText(this.controller.convertSecondsToStringTime(this.controller.getWtVO().getSeconds()));
        this.lblPauseTime.setText(this.controller.convertSecondsToStringTime(this.controller.getWtVO().getPause()));
        this.lblTimeDecimal.setText(String.format("%.5f", this.controller.calculateSecondsToHoursDecimal(this.controller.getWtVO().getSeconds())));
    }
}
