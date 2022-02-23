package datawrapGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.DayOfWeek;
import java.util.ArrayList;

import data.day;
import lombok.Getter;

@Getter
public class wrapGUIController extends WindowAdapter implements ActionListener {

    private final wrapGUI wrapGUI;

    public wrapGUIController (wrapGUI wrapGUI) {
        this.wrapGUI = wrapGUI;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        System.exit(0);
    }

    public Object[][] fillData(ArrayList<day> days) {

        Object[][] data = new Object[days.size() + 1][4];

        for (int i = 0; i < days.size(); i++) {
            data[i][0] = days.get(i).getDate();
            data[i][1] = String.format("%.5f", days.get(i).getHoursDecimal());
            data[i][2] = String.format("%.5f", this.calculateDailyOvertime(days.get(i).getHoursDecimal()));
            if (days.get(i).getDate().getDayOfWeek().equals(DayOfWeek.FRIDAY)) {
                double sum = 0;
                for (int j = 0; j < 5; j ++) {
                    if (i - j < 0) {
                        break;
                    }
                    sum += this.calculateDailyOvertime(days.get(i - j).getHoursDecimal());
                }
                data[i][3] = String.format("%.5f", sum);
            }
            else {
                data[i][3] = null;
            }
        }

        double sum = 0;
        for (data.day day : days) {
            sum += this.calculateDailyOvertime(day.getHoursDecimal());
        }
        data[data.length - 1][0] = "Gesamt";
        data[data.length - 1][1] = "-------";
        data[data.length - 1][2] = "-------";
        data[data.length - 1][3] = sum;

        return data;
    }

    public double calculateDailyOvertime(double workedHours) {
        return workedHours - 8;
    }
}
