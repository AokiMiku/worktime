package datawrapGUI;

import javax.swing.*;

import java.io.IOException;
import java.util.ArrayList;

import lombok.Getter;
import data.day;


@Getter
public class wrapGUI extends JFrame {
    public static void main (String[] args) {

        try {
            new wrapGUI();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private wrapGUIController controller;
    private ArrayList<day> days;

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
    }

    private void addComponents() throws IOException {

        this.days = data.day.extractDataFromString(utils.fileOps.readDataFromDataFile());
        day.sumAllDataSameDays(this.days);

    }
}
