package de.aps_programs.services;

import java.time.LocalDate;

import de.aps_programs.data.Worktimes;
import de.aps_programs.data.WorktimesRepository;
import de.aps_programs.data.WorktimesVO;
import org.springframework.stereotype.Service;


@Service
public class WorktimesServices {

    WorktimesRepository repository;

    public WorktimesServices (WorktimesRepository repository) {
        this.repository = repository;
    }

    public void writeData(WorktimesVO day) {

        Worktimes wt = repository.findByDay(day.getDay());
        if (wt == null) {
            wt = new Worktimes();
            wt.setId(repository.count() + 1);
            while(repository.findById(wt.getId()).isPresent()) {
                wt.setId(wt.getId() + 1);
            }
        }
        wt.setDay(day.getDay());
        wt.setWorktime(day.getWorktime());
        wt.setWorkday(true);
        wt.setPause(day.getPause());
        wt.setStartingTime(day.getStartingTime());
        repository.save(wt);
    }

    public WorktimesVO getSpecificDay(LocalDate specificDay) {

        Worktimes wt = repository.findByDay(specificDay.toString());
        if (wt == null) {
            return WorktimesVO.builder().build();
        }
        return WorktimesVO.builder()
                            .Day(wt.getDay())
                            .Worktime(wt.getWorktime())
                            .IsWorkday(wt.isWorkday())
                            .Pause(wt.getPause())
                            .StartingTime(wt.getStartingTime())
                            .build();
    }
}
