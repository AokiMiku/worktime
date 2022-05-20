package data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;


@Getter
@Setter
public class day {

    private LocalDate date;
    private double hoursDecimal;
    private boolean isWorkDay;

    public long getSeconds() {
        return (long) (this.hoursDecimal * 3600);
    }

    public day (LocalDate date, double hoursDecimal, boolean isWorkDay) {
        this.date = date;
        this.hoursDecimal = hoursDecimal;
        this.isWorkDay = isWorkDay;
    }

    public day (LocalDate date, String hoursDecimal, boolean isWorkDay) {
        this(date, Double.parseDouble(hoursDecimal), isWorkDay);
    }

    @Override
    public String toString () {

        return "day{" +
            "date=" + date +
            ", hoursDecimal=" + hoursDecimal +
            ", isWorkDay=" + this.isWorkDay +
            '}';
    }

    public static day toDay(String data) {
        String[] dayString = data.split(",");
        return new day(LocalDate.parse(dayString[0]), (dayString[1]), Boolean.parseBoolean(dayString[2]));
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
        return new day(left.getDate(), left.getHoursDecimal() + right.getHoursDecimal(), left.isWorkDay() && right.isWorkDay());
    }
}
