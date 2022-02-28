package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;

import org.apache.commons.io.FileUtils;


public class fileOps {
    public static final String dataFilePath;
    public static final String patternFilePath = "data/worktimePattern.csv";

    static {
        LocalDate today = LocalDate.now();
        dataFilePath = getDataFilePathForMonth(today);

        try {
            createFileIfNotExists(today);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static String getDataFilePathForMonth(LocalDate month) {
        return String.format("data/%d%02d_%s.csv", month.getYear(), month.getMonthValue(), month.getMonth());
    }

    public static void writeDataToDataFile(String path, String text) throws IOException {

        PrintWriter pw = new PrintWriter(new FileWriter(new File(path).getAbsolutePath(), true));
        pw.println(String.format("%s,%s", LocalDate.now(), text));
        pw.close();
    }

    public static String readAllDataFromDataFile(String path) throws IOException {

        StringBuilder resultStringBuilder = new StringBuilder();

        BufferedReader reader = new BufferedReader(new FileReader(path));

        String line = reader.readLine(); // read first line to ignore the headlines
        while ((line = reader.readLine()) != null) {
            resultStringBuilder.append(line).append("\n");
        }
        reader.close();

        return resultStringBuilder.toString();
    }

    public static void createFileIfNotExists(LocalDate month) throws IOException {

        File data;
        if ((data = new File(getDataFilePathForMonth(month))).exists()) {
            return;
        }

        FileUtils.copyFile(new File(patternFilePath), data);
    }

    public static double getWorkhoursForSpecificDay(LocalDate month) {

        String filepath = getDataFilePathForMonth(month);
        if (new File(filepath).exists()) {
            try {
                String data = readAllDataFromDataFile(filepath);
                while (data.contains(",")) {
                    if (data.startsWith(month.toString())) {
                        data = data.substring(data.indexOf(",") + 1, data.indexOf("\n"));
                        break;
                    }
                    data = data.substring(data.indexOf("\n") + 1);
                }
                return Double.parseDouble(data);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            return 0;
        }

        return 0;
    }
}

