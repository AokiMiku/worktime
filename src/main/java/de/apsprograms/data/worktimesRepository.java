package de.apsprograms.data;

import java.time.LocalDate;

import org.springframework.data.repository.CrudRepository;

public interface worktimesRepository extends CrudRepository<worktimes, String> {

    worktimes findByDay(LocalDate workDay);

}
