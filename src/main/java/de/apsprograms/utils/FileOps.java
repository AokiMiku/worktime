package de.apsprograms.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.Month;

import de.apsprograms.data.Day;
import org.apache.commons.io.FileUtils;

import static de.apsprograms.data.Day.addWorkHoursForSameDay;


public class FileOps {
    public static final String dataFilePath;
    public static final String patternFilePath = "de/apsprograms/data/worktimePattern.csv";

    static {
        LocalDate today = LocalDate.now();
        dataFilePath = getDataFilePathForMonth(today);

        try {
            createFileIfNotExists(today);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getDataFilePathForMonth(int month) {
        return getDataFilePathForMonth(LocalDate.of(LocalDate.now().getYear(), Month.of(month), 1));
    }

    public static String getDataFilePathForMonth(String month) {
        return getDataFilePathForMonth(LocalDate.of(LocalDate.now().getYear(), Month.valueOf(month), 1));
    }

    public static String getDataFilePathForMonth(LocalDate month) {
        return String.format("de/apsprograms/data/%d%02d_%s.csv", month.getYear(), month.getMonthValue(), month.getMonth());
    }

    public static void writeDataToDataFile(String path, String hoursDecimal, String pauseSeconds) throws IOException {

        PrintWriter pw = new PrintWriter(new FileWriter(new File(path).getAbsolutePath(), true));
        pw.println(String.format("%s,%s,true,%s", LocalDate.now(), hoursDecimal, pauseSeconds));
        pw.close();
    }

    public static String readAllDataFromDataFile(String path) throws IOException {

        File f = new File(path);
        if (!f.exists()) {
            return "";
        }

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

    public static Day getWorkhoursForSpecificDay(LocalDate specificDay) {

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
                            return Day.toDay(data[i]);
                        }
                        else {
                            Day allWorkHours = Day.toDay(data[i]);
                            allWorkHours.setHoursDecimal(0);
                            while (i != idx) {
                                allWorkHours = addWorkHoursForSameDay(allWorkHours, Day.toDay(data[--idx]));
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
            return new Day(specificDay, 0, false, 0);
        }

        return new Day(specificDay, 0, false, 0);
    }
}

