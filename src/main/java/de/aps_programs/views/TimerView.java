package de.aps_programs.views;

import javax.swing.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

import de.aps_programs.controllers.WorktimesController;
import de.aps_programs.data.WorktimesVO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;


@Getter @Setter
@Component
public class TimerView extends JFrame {

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

    public TimerView(WorktimesController controller) {
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

        this.controller.setWtVO(WorktimesVO.builder().Day(LocalDate.now().toString()).Worktime(0).IsWorkday(true).Pause(0).StartingTime(
            LocalDateTime.now().toLocalTime().toString()).build());

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

        this.lblTime.setText(this.controller.getWorktimeAsTimeString());
        this.lblPauseTime.setText(this.controller.getPauseAsTimeString());
        this.lblTimeDecimal.setText(String.format("%.5f", this.controller.secondsToHoursDecimal(this.controller.getWtVO().getSeconds())));
    }
}
