package de.apsprograms.data;

import org.springframework.data.repository.CrudRepository;

public interface WorktimesRepository extends CrudRepository<Worktimes, String> {

    Worktimes findByDay(String workDay);

}
