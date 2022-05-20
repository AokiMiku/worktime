package data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class worktimes {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private LocalDate Day;

    private double Worktime;

    private boolean isWorkDay;

    private long pause;

}
