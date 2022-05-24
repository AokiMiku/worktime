package de.apsprograms.timerGUI;

import javax.swing.*;


import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

import de.apsprograms.data.config.worktimesConfiguration;
import de.apsprograms.data.worktimesRepository;
import de.apsprograms.utils.dbOps;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@Getter @Setter
@SpringBootApplication
public class timerGUI extends JFrame {

    private timerGUIController controller;
    private JPanel pnlView;
    private JPanel pnlTime;
    private JPanel pnlButtons;
    private JLabel lblTime;
    private JLabel lblTimeDecimal;
    private JLabel lblPauseTime;
    private JButton btnStart;
    private JButton btnPause;
    private JButton btnStop;
    private LocalDateTime dtStartTime;
    private Timer timer;
    private final String defaultTextForLblTime = "00:00:00";
    private final String defaultTextForLblTimeDecimal = "0.00000";
    private long seconds = 0;
    private long todayAlreadyWorkedSeconds;
    private boolean pause = false;
    private LocalDateTime dtPauseStartTime;
    private long pauseSeconds;
    private boolean pause30Min = false;
    private boolean pause45Min = false;

    private final dbOps ops;

    private worktimesConfiguration configuration;

    public timerGUI() {

        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.setSize(240, 140);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setTitle("ApS Util - Worktime");

        this.configuration = new worktimesConfiguration();
        this.ops = new dbOps(configuration.repository);
        this.todayAlreadyWorkedSeconds = ops.getSpecificDay(LocalDate.now()).getSeconds();
        this.pauseSeconds = ops.getSpecificDay(LocalDate.now()).getPauseSeconds();

        this.initComponents();
        this.addComponents();
        this.setVisible(true);
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

        this.lblPauseTime = new JLabel(this.defaultTextForLblTime);

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
        this.pnlTime.add(this.lblPauseTime);

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
        this.dtStartTime = LocalDateTime.now().minusSeconds(this.todayAlreadyWorkedSeconds);
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
//        if (!this.pause) {
            this.actualizeLblTime();
//        }
    }

    public void actualizeLblTime() {
        calcAutoPause();
        if (this.pause) {
            this.lblPauseTime.setText(convertSecondsToStringTime(Duration.between(this.dtPauseStartTime, LocalDateTime.now()).getSeconds()));
        }
        else {
            this.lblTime.setText(this.convertSecondsToStringTime(this.seconds = Duration.between(this.dtStartTime, LocalDateTime.now()).getSeconds() - this.pauseSeconds));
            this.lblPauseTime.setText(convertSecondsToStringTime(this.pauseSeconds));
        }
        this.lblTimeDecimal.setText(String.format("%.5f", this.calculateHoursAndMinutesToHoursDecimal()));
    }

    private String convertSecondsToStringTime(long seconds) {

        long remainingSeconds = seconds;
        long minutes;
        long hours;

        minutes = remainingSeconds / 60;
        remainingSeconds -= minutes * 60;
        hours = minutes / 60;
        minutes -= hours * 60;


        return String.format("%02d:%02d:%02d", hours, minutes, remainingSeconds);
    }

    private void calcAutoPause() {

        if (seconds / 3600f > 9 && !this.pause45Min) {
            this.pauseSeconds = Math.max(45 * 60, this.pauseSeconds);
            this.pause45Min = true;
        } else if (seconds / 3600f > 6 && !this.pause30Min) {
            this.pauseSeconds = Math.max(30 * 60, this.pauseSeconds);
            this.pause30Min = true;
        }
    }

    public double calculateHoursAndMinutesToHoursDecimal() {
        return this.seconds / 3600f;
    }
}