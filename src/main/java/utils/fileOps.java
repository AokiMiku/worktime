package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;

import data.day;
import org.apache.commons.io.FileUtils;

import static data.day.addWorkHoursForSameDay;


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
        pw.println(String.format("%s,%s,true", LocalDate.now(), text));
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

    public static day getWorkhoursForSpecificDay(LocalDate specificDay) {

        String filepath = getDataFilePathForMonth(specificDay);
        if (new File(filepath).exists()) {
            try {
                String[] data = readAllDataFromDataFile(filepath).split("\n");
                for (int i = 0; i < data.length; i++) {
                    if (data[i].startsWith(specificDay.toString())) {
                        int idx = i;
                        while (++idx < data.length) {
                            if (!data[idx].startsWith(specificDay.toString())) {
                                break;
                            }
                        }
                        if (i == idx) {
                            return day.toDay(data[i]);
                        }
                        else {
                            day allWorkHours = day.toDay(data[i]);
                            allWorkHours.setHoursDecimal(0);
                            while (i != idx) {
                                allWorkHours = addWorkHoursForSameDay(allWorkHours, day.toDay(data[--idx]));
                            }
                            return allWorkHours;
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            return new day(specificDay, 0, false);
        }

        return new day(specificDay, 0, false);
    }
}

