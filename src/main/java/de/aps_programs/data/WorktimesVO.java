package de.aps_programs.data;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter @Setter
@AllArgsConstructor
@Builder
public class WorktimesVO {

    @Builder.Default
    private String Day = LocalDate.now().toString();

    private String StartingTime;

    @Builder.Default
    private double Worktime = 0;

    @Builder.Default
    private boolean IsWorkday = true;

    @Builder.Default
    private long Pause = 0;

    public long getSeconds() {
        return (long)(Worktime * 3600);
    }
    public void addPause(long seconds) {
        this.setPause(getPause() + seconds);
    }
}
