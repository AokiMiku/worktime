package de.apsprograms.data.config;

import de.apsprograms.data.worktimesRepository;
import org.springframework.context.annotation.Bean;


public class worktimesConfiguration {

    public worktimesRepository repository;

    @Bean
    public void Configure(worktimesRepository repository) {
        this.repository = repository;
    }
}
