package de.aps_programs;

import java.awt.*;

import de.aps_programs.controllers.WorktimesController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication (scanBasePackages = "de.aps_programs")
@EntityScan("de.aps_programs")
@EnableJpaRepositories("de.aps_programs")
public class WorktimeSpringBootApplication
		implements CommandLineRunner {

	@Autowired
	WorktimesController controller;

	public static void main(String[] args) {
		var ctx = new SpringApplicationBuilder(WorktimeSpringBootApplication.class)
			.headless(false).run(args);

		EventQueue.invokeLater(() -> {

			var ex = ctx.getBean(WorktimeSpringBootApplication.class);
		});
//		SpringApplication.run(WorktimeSpringBootApplication.class, args);
	}

	@Override
	public void run(String... args) {
//		new Timer(controller);
	}
}
