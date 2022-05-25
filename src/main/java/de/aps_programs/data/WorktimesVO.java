package de.aps_programs.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter @Setter
@AllArgsConstructor
public class WorktimesVO {

    private String Day;

    private double Worktime;

    private boolean IsWorkday;

    private long Pause;

    public long getSeconds() {
        return (long)(Worktime * 3600);
    }
    public void addPause(long seconds) {
        this.setPause(getPause() + seconds);
    }
}
