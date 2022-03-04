package datawrapGUI;

import javax.swing.*;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

import lombok.Getter;
import data.day;
import utils.fileOps;


@Getter
public class wrapGUI extends JFrame {

    private wrapGUIController controller;
    private ArrayList<day> days;

    private JTable dataTable;
    private ScrollPane dataScroll;

    public wrapGUI() throws IOException {
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.setSize(400, 300);
        this.setLocationRelativeTo(null);
        this.setTitle("ApS Util - Worktime Data");

        this.initComponents();
        this.addComponents();
        this.setVisible(true);

    }

    private void initComponents() {

        this.controller = new wrapGUIController(this);
        this.addWindowListener(this.controller);

        this.days = new ArrayList<>();

        this.dataScroll = new ScrollPane();
    }

    private void addComponents() throws IOException {

        this.days = data.day.extractDataFromString(utils.fileOps.readAllDataFromDataFile(fileOps.dataFilePath));
        day.sumAllDataSameDays(this.days);
        if (this.days.size() > 0) {

            String[] columnNames = {"Date", "Worktimes", "Daily OT", "Weekly OT"};
            this.dataTable = new JTable(this.controller.fillData(this.days), columnNames);
        }
        else {
            this.dataTable = new JTable();
        }
        this.dataTable.setFillsViewportHeight(true);
        this.dataScroll.add(this.dataTable);

        this.setContentPane(this.dataScroll);

    }
}
