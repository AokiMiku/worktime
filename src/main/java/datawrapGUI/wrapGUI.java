package datawrapGUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.io.IOException;
import java.time.Month;
import java.util.ArrayList;

import lombok.Getter;
import data.day;
import utils.fileOps;


@Getter
public class wrapGUI extends JFrame {

    private wrapGUIController controller;
    private ArrayList<day> days;

    private JComboBox<String> selectMonths;
    private JPanel pnlContent;
    private JTable dataTable;
    private ScrollPane dataScroll;

    private int startMonth;

    public wrapGUI(int startMonth) throws IOException {

        this.startMonth = startMonth;

        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.setSize(400, 300);
        this.setLocationRelativeTo(null);
        this.setTitle("ApS Util - Worktime Data");

        this.initComponents();
        this.addComponents();
        this.setVisible(true);

    }

    private void initComponents() {

        this.setLayout(new FlowLayout());
        this.controller = new wrapGUIController(this);
        this.addWindowListener(this.controller);

        this.days = new ArrayList<>();

        this.selectMonths = new JComboBox<>();
        this.selectMonths.setPreferredSize(new Dimension(200, 25));
        this.selectMonths.addActionListener(this.controller);

        this.pnlContent = new JPanel();
        this.pnlContent.setLayout(new BoxLayout(this.pnlContent, BoxLayout.Y_AXIS));

        this.dataScroll = new ScrollPane();
    }

    private void addComponents() throws IOException {

        this.selectMonths.addItem(wrapGUIController.TEXT_ALL_CURRENT_YEAR);
        for (int i = 1; i <= 12; i++) {
            this.selectMonths.addItem(Month.of(i).toString());
        }
        if (this.startMonth != 0) {
            this.selectMonths.setSelectedIndex(this.startMonth);
        }

//        this.buildDataTable();

        this.dataTable.setFillsViewportHeight(true);
        this.dataScroll.add(this.dataTable);

        this.pnlContent.add(this.selectMonths);
        this.pnlContent.add(this.dataScroll);

        this.setContentPane(this.pnlContent);

    }

    public void buildDataTable() throws IOException {
        if (this.selectMonths.getSelectedIndex() != 0) {
            this.days = day.extractDataFromString(fileOps.readAllDataFromDataFile(fileOps.getDataFilePathForMonth(this.selectMonths.getSelectedIndex())));
        }
        else {
            for (int i = 1; i <= 12; i++) {
                this.days.addAll(day.extractDataFromString(fileOps.readAllDataFromDataFile(fileOps.getDataFilePathForMonth(i))));
            }
        }

        day.sumAllDataSameDays(this.days);
//        if (this.dataTable != null) {
//            ((DefaultTableModel)this.dataTable.getModel()).setRowCount(0);
////            this.dataTable.getModel().setRowCount(0);
//        }
        if (this.days.size() > 0) {

            String[] columnNames = {"Date", "Worktimes", "Daily OT", "Weekly OT"};
            this.dataTable = new JTable(this.controller.fillData(this.days), columnNames);
        }
        else {
            this.dataTable = new JTable();
        }
    }
}
