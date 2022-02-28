package datawrapGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.DayOfWeek;
import java.time.LocalDate;
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

        int dataSizeOffset = 0;
        LocalDate dayOne = days.get(0).getDate();

        while (!dayOne.getDayOfWeek().equals(DayOfWeek.MONDAY)) {
            dayOne = dayOne.minusDays(1);
            dataSizeOffset++;
        }

        Object[][] data = new Object[days.size() + 1 + dataSizeOffset][4];

        for (int i = 0; i < dataSizeOffset; i++) {
            writeDataRow(data, i, dayOne,
                            String.format("%.5f", utils.fileOps.getWorkhoursForSpecificDay(dayOne)),
                            String.format("%.5f", this.calculateDailyOvertime(utils.fileOps.getWorkhoursForSpecificDay(dayOne))));
            dayOne = dayOne.plusDays(1);
        }

        for (int i = 0, k = dataSizeOffset; i < days.size(); i++, k++) {

            writeDataRow(data, k, days.get(i).getDate(),
                         String.format("%.5f", days.get(i).getHoursDecimal()),
                         String.format("%.5f", this.calculateDailyOvertime(days.get(i).getHoursDecimal())));
            if (days.get(i).getDate().getDayOfWeek().equals(DayOfWeek.FRIDAY)) {
                double sum = 0;
                for (int j = 0; j < 5; j ++) {
                    sum += this.calculateDailyOvertime(utils.fileOps.getWorkhoursForSpecificDay(days.get(i).getDate().minusDays(j)));
                }
                data[k][3] = String.format("%.5f", sum);
            }
            else {
                data[k][3] = null;
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

    private void writeDataRow(Object[][] data, int rowIdx, Object... columnData) {

        System.arraycopy(columnData, 0, data[rowIdx], 0, columnData.length);
    }

    public double calculateDailyOvertime(double workedHours) {
        return workedHours - 8;
    }
}
