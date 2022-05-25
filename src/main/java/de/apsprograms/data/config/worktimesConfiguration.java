package de.apsprograms.data.config;

import de.apsprograms.data.WorktimesRepository;
import org.springframework.context.annotation.Bean;


public class worktimesConfiguration {

    public WorktimesRepository repository;

    @Bean
    public void Configure(WorktimesRepository repository) {
        this.repository = repository;
    }
}
