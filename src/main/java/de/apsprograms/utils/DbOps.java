package de.apsprograms.utils;

import java.time.LocalDate;

import de.apsprograms.data.Day;
import de.apsprograms.data.Worktimes;
import de.apsprograms.data.WorktimesRepository;
import org.springframework.stereotype.Component;


@Component
public class DbOps {

    final WorktimesRepository repository;

    public DbOps(WorktimesRepository repository) {

        this.repository = repository;
    }


    public void writeData(Day day) {

        Worktimes wt = repository.findByDay(day.getDate().toString());
        wt.setDay(day.getDate());
        wt.setIsWorkDay(day.isWorkDay());
        wt.setWorktime(day.getHoursDecimal());
        wt.setPause(day.getPauseSeconds());
        repository.save(wt);
    }

    public Day getSpecificDay(LocalDate specificDay) {
        Worktimes wt = repository.findByDay(specificDay.toString());
        return new Day(wt.getDay(), wt.getWorktime(), wt.isIsWorkDay(), wt.getPause());
    }
}
