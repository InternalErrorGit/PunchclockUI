package ch.zli.m223.punchclock;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import tornadofx.control.DateTimePicker;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author P. Gatzka
 * @version 22.09.2021
 * Project: PunchclockUI
 * Class: App
 */
public class PunchclockUI extends BorderPane {

    private VBox form;
    private TableView tableView;

    private DateTimePicker checkInDateTime;
    private DateTimePicker checkOutDateTime;

    private Button submit;
    private Button delete;


    public PunchclockUI() {
        setPrefSize(800, 600);
        form = new VBox();
        tableView = new TableView();
        init();
    }

    private void init() {
        form.getChildren().add(new Label("Check in"));

        checkInDateTime = new DateTimePicker();
        form.getChildren().add(checkInDateTime);

        checkOutDateTime = new DateTimePicker();
        form.getChildren().add(checkOutDateTime);

        submit = new Button("Save");
        submit.setOnAction(actionEvent -> {
            Entry entry = new Entry();
            entry.setCheckIn(checkInDateTime.getDateTimeValue());
            entry.setCheckOut(checkOutDateTime.getDateTimeValue());
            RequestHandler.insert(entry);
        });

        delete = new Button("Delete selected");
        delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Long id = ((Entry) tableView.getSelectionModel().getSelectedItem()).getId();
                RequestHandler.delete(id);
            }
        });


        form.getChildren().addAll(submit, delete);
        form.setPadding(new Insets(15, 15, 15, 15));
        form.setMaxSize(300, USE_COMPUTED_SIZE);
        form.setSpacing(10);

        setCenter(form);

        tableView.setItems(Model.getModel().entries);

        TableColumn id = new TableColumn("ID");
        id.setCellValueFactory(new PropertyValueFactory<String, Entry>("id"));
        TableColumn checkIn = new TableColumn("CHECK IN");
        checkIn.setCellValueFactory(new PropertyValueFactory<String, Entry>("checkIn"));
        TableColumn checkOut = new TableColumn("CHECK OUT");
        checkOut.setCellValueFactory(new PropertyValueFactory<String, Entry>("checkOut"));
        tableView.getColumns().addAll(id, checkIn, checkOut);
        setBottom(tableView);
    }


}
