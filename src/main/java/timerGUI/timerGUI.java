package timerGUI;

import javax.swing.*;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;

import datawrapGUI.wrapGUI;
import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class timerGUI extends JFrame {

    private timerGUIController controller;
    private JPanel pnlView;
    private JPanel pnlTime;
    private JPanel pnlButtons;
    private JLabel lblTime;
    private JLabel lblTimeDecimal;
    private JButton btnStart;
    private JButton btnPause;
    private JButton btnStop;
    private LocalDateTime dtStartTime;
    private Timer timer;
    private final String defaultTextForLblTime = "00:00:00";
    private final String defaultTextForLblTimeDecimal = "0.00000";
    private long seconds = 0;
    private boolean pause = false;
    private LocalDateTime dtPauseStartTime;
    private long pauseSeconds = 0;

    public timerGUI(boolean instantStart) {

        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.setSize(240, 110);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setTitle("ApS Util - Worktime");

        this.initComponents();
        this.addComponents();
        this.setVisible(true);

        if (instantStart) {
            this.start();
        }
    }

    public void initComponents() {
        this.controller = new timerGUIController(this);
        this.addWindowListener(this.controller);

        this.pnlView = new JPanel();
//        this.pnlView.setLayout(new BoxLayout(this.pnlView, BoxLayout.Y_AXIS));

        this.pnlTime = new JPanel();
        this.pnlTime.setLayout(new BoxLayout(this.pnlTime, BoxLayout.Y_AXIS));

        this.pnlButtons = new JPanel();
//        this.pnlButtons.setLayout(new BoxLayout(this.pnlButtons, BoxLayout.X_AXIS));

        this.lblTime = new JLabel(this.defaultTextForLblTime);

        this.lblTimeDecimal = new JLabel(this.defaultTextForLblTimeDecimal);

        this.btnStart = new JButton("Start");
        this.btnStart.setSize(50, 25);
//        this.btnStart.setMargin(new Insets(2, 2, 2, 2));
        this.btnStart.addActionListener(this.controller);

        this.btnPause = new JButton("Pause");
        this.btnPause.setSize(50, 25);
        this.btnPause.addActionListener(this.controller);

        this.btnStop = new JButton("Stop");
        this.btnStop.setSize(50, 25);
        this.btnStop.addActionListener(this.controller);

        this.timer = new Timer(100, this.controller);
    }

    public void addComponents() {
        this.pnlTime.add(this.lblTime);
        this.pnlTime.add(this.lblTimeDecimal);

        this.pnlView.add(this.pnlTime);

        this.pnlButtons.add(this.btnStart);
        this.pnlButtons.add(this.btnPause);
        this.pnlButtons.add(this.btnStop);

        this.pnlView.add(this.pnlButtons);
        this.setContentPane(this.pnlView);
    }

    public void start() {
        if (this.timer.isRunning()) {
            this.stop();
            this.lblTime.setText(this.defaultTextForLblTime);
            this.lblTimeDecimal.setText(this.defaultTextForLblTimeDecimal);
        }
        this.dtStartTime = LocalDateTime.now();
        this.timer.start();
    }

    public void pauseToggle() {
        if (!this.pause) {
            this.dtPauseStartTime = LocalDateTime.now();
            this.pause = true;
        }
        else {
            this.pauseSeconds += Duration.between(this.dtPauseStartTime, LocalDateTime.now()).getSeconds();
            this.pause = false;
        }
    }

    public void stop() {
        this.timer.stop();
    }

    public void timerHandle() {
        if (!this.pause) {
            this.actualizeLblTime();
        }
    }

    public void actualizeLblTime() {
        this.lblTime.setText(this.convertSecondsToStringTime(Duration.between(this.dtStartTime, LocalDateTime.now()).getSeconds()));
        this.lblTimeDecimal.setText(String.format("%.5f", this.calculateHoursAndMinutesToHoursDecimal()));
    }

    private String convertSecondsToStringTime(long seconds) {
        long remainingSeconds = this.seconds = seconds - this.pauseSeconds;
        long minutes;
        long hours;

        minutes = remainingSeconds / 60;
        remainingSeconds -= minutes * 60;
        hours = minutes / 60;
        minutes -= hours * 60;

        return String.format("%02d:%02d:%02d", hours, minutes, remainingSeconds);
    }

    public double calculateHoursAndMinutesToHoursDecimal() {
        return this.seconds / 3600f;
    }
}
