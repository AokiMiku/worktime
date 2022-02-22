package data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class day {
    private LocalDate date;
    private double hoursDecimal;

    public day (LocalDate date, double hoursDecimal) {
        this.date = date;
        this.hoursDecimal = hoursDecimal;
    }

    public day (LocalDate date, String hoursDecimal) {
        this(date, Double.parseDouble(hoursDecimal));
    }

    @Override
    public String toString () {

        return "day{" +
            "date=" + date +
            ", hoursDecimal=" + hoursDecimal +
            '}';
    }

    public static void sumAllDataSameDays (ArrayList<day> days) {

        for (int i = 0; i < days.size(); i++) {
            if (days.size() == i + 1) {
                break;
            }

            if (days.get(i).getDate().equals(days.get(i + 1).getDate())) {
                days.get(i).setHoursDecimal(days.get(i).getHoursDecimal() + days.get(i + 1).getHoursDecimal());
                days.remove(i + 1);
            }
        }
    }

    public static ArrayList<day> extractDataFromString (String data) {

        ArrayList<day> days = new ArrayList<>();
        while (data.contains(",")) {
            days.add(new day(LocalDate.parse(data.substring(0, data.indexOf(','))), data.substring(data.indexOf(',') + 1, data.indexOf("\n"))));

            data = data.substring(data.indexOf("\n") + 1);
        }
        return days;
    }
}
