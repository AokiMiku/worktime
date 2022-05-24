package de.apsprograms.utils;

import java.time.LocalDate;

import de.apsprograms.data.day;
import de.apsprograms.data.worktimes;
import de.apsprograms.data.worktimesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class dbOps {

    final worktimesRepository repository;

    public dbOps(worktimesRepository repository) {

        this.repository = repository;
    }


    public void writeData(day day) {

        worktimes wt = repository.findByDay(day.getDate());
        wt.setDay(day.getDate());
        wt.setIsWorkDay(day.isWorkDay());
        wt.setWorktime(day.getHoursDecimal());
        wt.setPause(day.getPauseSeconds());
        repository.save(wt);
    }

    public day getSpecificDay(LocalDate specificDay) {
        worktimes wt = repository.findByDay(specificDay);
        return new day(wt.getDay(), wt.getWorktime(), wt.isIsWorkDay(), wt.getPause());
    }
}
