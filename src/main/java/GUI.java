import javax.swing.*;

import java.time.Duration;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class GUI extends JFrame {

    private GUIController controller;
    private JPanel pnlView;
    private JPanel pnlTime;
    private JPanel pnlButtons;
    private JLabel lblTime;
    private JLabel lblTimeDecimal;
    private JButton btnStart;
    private JButton btnStop;
    private LocalDateTime dtStartTime;
    private Timer timer;
    private final String defaultTextForLblTime = "00:00:00";
    private final String defaultTextForLblTimeDecimal = "0.00000";
    private long hours = 0;
    private long minutes = 0;
    private long seconds = 0;

    public GUI(boolean instaStart) {
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.setSize(150, 150);
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        this.initComponents();
        this.addComponents();
        this.setVisible(true);

        if (instaStart) {
            this.start();
        }
    }

    private void initComponents() {
        this.controller = new GUIController(this);
        this.addWindowListener(this.controller);

        this.pnlView = new JPanel();
        this.pnlTime = new JPanel();
        this.pnlButtons = new JPanel();

        this.lblTime = new JLabel(this.defaultTextForLblTime);

        this.lblTimeDecimal = new JLabel(this.defaultTextForLblTimeDecimal);

        this.btnStart = new JButton("Start");
        this.btnStart.setSize(50, 25);
        this.btnStart.addActionListener(this.controller);

        this.btnStop = new JButton("Stop");
        this.btnStop.setSize(50, 25);
        this.btnStop.addActionListener(this.controller);

        this.timer = new Timer(100, this.controller);
    }

    private void addComponents() {
        this.pnlTime.add(this.lblTime);

        this.pnlView.add(this.pnlTime);
        this.pnlView.add(this.lblTimeDecimal);

        this.pnlButtons.add(this.btnStart);
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

    public void stop() {
        this.timer.stop();
    }

    public void actualizeLblTime() {
        this.lblTime.setText(this.convertSecondsToStringTime(Duration.between(this.dtStartTime, LocalDateTime.now()).getSeconds()));
        this.lblTimeDecimal.setText(String.format("%.5f", this.calculateHoursAndMinutesToHoursDecimal()));
    }

    private String convertSecondsToStringTime(long seconds) {
        String result = "";
        long remainingSeconds = seconds;
        long minutes = 0;
        long hours = 0;

        minutes = remainingSeconds / 60;
        this.seconds = remainingSeconds -= minutes * 60;
        this.hours = hours = minutes / 60;
        this.minutes = minutes -= hours * 60;

//        while (remainingSeconds >= 60) {
//            minutes++;
//            remainingSeconds -= 60;
//        }
//
//        while (minutes >= 60) {
//            hours++;
//            minutes -= 60;
//        }

        result = String.format("%02d:%02d:%02d", hours, minutes, remainingSeconds);

        return result;
    }

    private double calculateHoursAndMinutesToHoursDecimal() {
        return this.hours + this.minutes / 60f + this.seconds / 3600f;
    }
}
