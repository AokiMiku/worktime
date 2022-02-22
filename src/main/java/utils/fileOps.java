package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.time.LocalDate;

import org.jetbrains.annotations.NotNull;


public class fileOps {
    public static final String dataFilePath = "data/worktime.csv";

    public static void writeDataToDataFile(String text) throws IOException {

        PrintWriter pw = new PrintWriter(new FileWriter(new File(dataFilePath).getAbsolutePath(), true));
        pw.println(String.format("%s,%s", LocalDate.now(), text));
        pw.close();
    }

    public static @NotNull String readDataFromDataFile() throws IOException {

        StringBuilder resultStringBuilder = new StringBuilder();

        BufferedReader reader = new BufferedReader(new FileReader(dataFilePath));

        String line = reader.readLine(); // read first line to ignore the headlines
        while ((line = reader.readLine()) != null) {
            resultStringBuilder.append(line).append("\n");
        }
        reader.close();

        return resultStringBuilder.toString();
    }
}

