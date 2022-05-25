package de.aps_programs.data;

import javax.persistence.Table;

import org.springframework.data.jpa.repository.JpaRepository;


@Table (name = "worktimes")
public interface WorktimesRepository extends JpaRepository<Worktimes, Long> {

    Worktimes findByDay(String Day);

    Long findTopByOrderByIdDesc();

    Worktimes getTopByOrderByIdDesc();
}
