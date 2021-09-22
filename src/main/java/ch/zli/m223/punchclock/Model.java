package ch.zli.m223.punchclock;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @author P. Gatzka
 * @version 22.09.2021
 * Project: PunchclockUI
 * Class: App
 */
public class Model {

    private static Model model;
    public ObservableList<Entry> entries;

    private Model() {
        entries = FXCollections.observableArrayList();
    }

    public static Model getModel() {
        if (model == null) model = new Model();
        return model;
    }

    public void update() {
        entries.clear();
        RequestHandler.getAll();
    }
}
