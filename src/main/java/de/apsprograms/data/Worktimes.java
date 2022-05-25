package de.apsprograms.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;


@Entity (name = "worktimesEntity")
@Table (name = "worktimes")
@Getter
@Setter
public class Worktimes {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private LocalDate Day;

    private double Worktime;

    private boolean IsWorkDay;

    private long Pause;
}
