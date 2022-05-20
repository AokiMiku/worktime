package de.apsprograms;

import java.io.IOException;

import datawrapGUI.wrapGUI;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import timerGUI.timerGUI;

@SpringBootApplication
public class app {
    public static void main (String[] args) {

        boolean start = false;
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("--start") || args[i].equals("-s")) {
                start = true;
            }
            else if (args[i].equals("--wrap") || args[i].equals("-w")) {
                try {
                    int startMonth = 0;

                    if (i + 1 < args.length) {
                        startMonth = Integer.parseInt(args[i + 1]);
                    }

                    new wrapGUI(startMonth);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return;
            }
        }
        new timerGUI(start);
    }
}
