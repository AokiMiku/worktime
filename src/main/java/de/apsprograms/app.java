package de.apsprograms;

import java.io.IOException;

import de.apsprograms.data.worktimesRepository;
import de.apsprograms.datawrapGUI.wrapGUI;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import de.apsprograms.timerGUI.timerGUI;


@SpringBootApplication
@EnableJpaRepositories("de.apsprograms.data")
@EnableTransactionManagement
@EntityScan("de.apsprograms.data")
public class app {

    private static boolean start = false;
    private static boolean useWrapper = false;
    private static int startMonth = 0;

    public static void main (String[] args) {

        boolean start = false;
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("--start") || args[i].equals("-s")) {
                start = true;
            } else if (args[i].equals("--wrap") || args[i].equals("-w")) {
                useWrapper = true;
                if (i + 1 < args.length) {
                    startMonth = Integer.parseInt(args[i + 1]);
                }
            }
        }
        app.start = start;
        SpringApplication.run(timerGUI.class);
//        SpringApplication.run(app.class);
    }

//    public static void run(worktimesRepository repository) {
//        if (useWrapper) {
//            try {
//                new wrapGUI(startMonth);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return;
//        }
////        if (start) {
//            SpringApplication.run(timerGUI.class);
////        } else {
////            new timerGUI(repository);
////        }
//    }
}
