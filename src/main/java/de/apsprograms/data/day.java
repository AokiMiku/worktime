package de.apsprograms.data;

import java.time.LocalDate;
import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;


@Getter
@Setter
public class day {

    private LocalDate date;
    private double hoursDecimal;
    private boolean isWorkDay;
    private long pauseSeconds;

    public long getSeconds() {
        return (long) (this.hoursDecimal * 3600);
    }

    public day (LocalDate date, double hoursDecimal, boolean isWorkDay, long pauseSeconds) {
        this.date = date;
        this.hoursDecimal = hoursDecimal;
        this.isWorkDay = isWorkDay;
        this.pauseSeconds = pauseSeconds;
    }

    public day (LocalDate date, String hoursDecimal, boolean isWorkDay, String pauseSeconds) {
        this(date, Double.parseDouble(hoursDecimal), isWorkDay, Long.parseLong(pauseSeconds));
    }

    @Override
    public String toString () {

        return "day{" +
            "date=" + this.date +
            ", hoursDecimal=" + this.hoursDecimal +
            ", isWorkDay=" + this.isWorkDay +
            ", pauseDecimal=" + this.pauseSeconds +
            '}';
    }

    public static day toDay(String data) {
        String[] dayString = data.split(",");
        return new day(LocalDate.parse(dayString[0]), (dayString[1]), Boolean.parseBoolean(dayString[2]), (dayString[3]));
    }

    public static void sumAllDataSameDays (ArrayList<day> days) {

        for (int i = 0; i < days.size(); i++) {
            if (days.size() == i + 1) {
                break;
            }

            if (days.get(i).getDate().equals(days.get(i + 1).getDate())) {
                days.get(i).setHoursDecimal(days.get(i).getHoursDecimal() + days.get(i + 1).getHoursDecimal());
                days.remove(i + 1);
                i--;
            }
        }
    }

    public static ArrayList<day> extractDataFromString (String data) {

        ArrayList<day> days = new ArrayList<>();
        while (data.contains(",")) {
            days.add(toDay(data.substring(0, data.indexOf("\n"))));

            data = data.substring(data.indexOf("\n") + 1);
        }
        return days;
    }

    public static day addWorkHoursForSameDay (@NotNull day left, @NotNull day right) {
        return new day(left.getDate(),
                       left.getHoursDecimal() + right.getHoursDecimal(),
                       left.isWorkDay() && right.isWorkDay(),
                       left.getPauseSeconds() + right.getPauseSeconds());
    }
}