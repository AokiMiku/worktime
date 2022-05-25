package de.aps_programs.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity (name = "worktimes")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Worktimes {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "day")
    private String day;

    @Column(name = "worktime")
    private double worktime;

    @Column(name = "is_Workday")
    private boolean isWorkday;

    @Column(name = "pause")
    private long pause;

}
