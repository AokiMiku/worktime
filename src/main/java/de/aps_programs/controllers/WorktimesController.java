package de.aps_programs.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import de.aps_programs.data.WorktimesVO;
import de.aps_programs.services.WorktimesServices;
import de.aps_programs.views.TimerView;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;


@Controller
public class WorktimesController extends WindowAdapter implements ActionListener {

    public static final int SAVE_TO_DB_EVERY_X_MINUTES = 15;

    @Getter @Setter
    TimerView GUI;

    @Autowired
    private WorktimesServices service;
    @Getter @Setter
    private WorktimesVO wtVO;
    private final javax.swing.Timer timer;
    private boolean pause45Min;
    private boolean pause30Min;
    public final String defaultTextForLblTime = "00:00:00";
    public final String defaultTextForLblTimeDecimal = "0.00000";
    public boolean pause;
    public LocalDateTime pauseStartTime;
    public LocalTime startTime;
    public String getPauseAsTimeString() {
        if (this.pause) {
            return convertSecondsToTimeString(Duration.between(this.pauseStartTime, LocalDateTime.now()).getSeconds() + this.getWtVO().getPause());
        } else {
            return convertSecondsToTimeString(this.getWtVO().getPause());
        }
    }
    public String getWorktimeAsTimeString() {
        return this.convertSecondsToTimeString(this.getWtVO().getSeconds());
    }

    public WorktimesController() {

        this.timer = new javax.swing.Timer(100, this);
        this.setWtVO(WorktimesVO.builder()
                                .Day(LocalDate.now().toString())
                                .Worktime(0)
                                .IsWorkday(true)
                                .Pause(0)
                                .StartingTime(LocalDateTime.now().toLocalTime().toString())
                                .build());
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

        if (actionEvent.getSource() == this.GUI.getBtnStart()) {
            this.start();
        }
        else if (actionEvent.getSource() == this.GUI.getBtnPause()) {
            this.pauseToggle();
        }
        else if (actionEvent.getSource() == this.GUI.getBtnStop()) {
            this.stop();
            saveCurrentWorktime();
        }
        else if (actionEvent.getSource() == this.timer) {
            this.calcAutoPause(this.getWtVO().getSeconds(), this.getWtVO().getPause());
            this.timerHandle();
        }
    }

    @Override
    public void windowClosing(WindowEvent e) {
        this.stop();
        System.exit(0);
    }

    private void saveCurrentWorktime() {

        this.service.writeData(this.wtVO);
    }

    public void start() {

        if (this.timer.isRunning()) {
            this.stop();
            this.GUI.getLblTime().setText(this.defaultTextForLblTime);
            this.GUI.getLblTimeDecimal().setText(this.defaultTextForLblTimeDecimal);
        }
        this.setWtVO(this.service.getSpecificDay(LocalDate.now()));
        if (this.getWtVO().getStartingTime() == null) {
            this.startTime = LocalTime.now();
            this.getWtVO().setStartingTime(this.startTime.toString().substring(0, 8));
        } else {
            this.startTime = LocalTime.parse(this.getWtVO().getStartingTime());
        }
        this.GUI.getLblStartingTime().setText(this.getWtVO().getStartingTime());
        this.timer.start();
    }

    public void pauseToggle() {
        if (!this.pause) {
            this.pauseStartTime = LocalDateTime.now();
            this.pause = true;
        }
        else {
            this.getWtVO().addPause(Duration.between(this.pauseStartTime, LocalDateTime.now()).getSeconds());
            this.pause = false;
        }
    }

    public void stop() { this.timer.stop(); }

    public double secondsToHoursDecimal(long seconds) { return seconds / 3600f; }

    public void timerHandle() {

        this.getWtVO().setWorktime(this.secondsToHoursDecimal(Duration.between(this.startTime, LocalDateTime.now()).getSeconds()));

        this.GUI.actualizeLblTime();
        if (this.wtVO.getSeconds() % minutesToSeconds(SAVE_TO_DB_EVERY_X_MINUTES) == 0) {
            this.saveCurrentWorktime();
        }
    }

    private int minutesToSeconds(int minutes) { return minutes * 60; }

    public String convertSecondsToTimeString(long seconds) {

        long remainingSeconds = seconds;

        long minutes = remainingSeconds / 60;
        remainingSeconds -= (minutes * 60);
        long hours = minutes / 60;
        minutes -= hours * 60;

        return String.format("%02d:%02d:%02d", hours, minutes, remainingSeconds);
    }

    private void calcAutoPause(long seconds, long pauseSeconds) {

        if ((seconds - pauseSeconds) / 3600f > 9 && !this.pause45Min) {
            this.getWtVO().setPause(Math.max(this.minutesToSeconds(45), this.getWtVO().getPause()));
            this.pause45Min = true;
        } else if ((seconds - pauseSeconds) / 3600f > 6 && !this.pause30Min) {
            this.getWtVO().setPause(Math.max(this.minutesToSeconds(30), this.getWtVO().getPause()));
            this.pause30Min = true;
        }
    }
}
