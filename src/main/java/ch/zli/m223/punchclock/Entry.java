package ch.zli.m223.punchclock;

import javafx.util.converter.LocalDateStringConverter;
import javafx.util.converter.LocalTimeStringConverter;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * @author P. Gatzka
 * @version 22.09.2021
 * Project: PunchclockUI
 * Class: App
 */
public class Entry {

    private Long id;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalDateTime checkIn) {
        this.checkIn = checkIn;
    }

    public LocalDateTime getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalDateTime checkOut) {
        this.checkOut = checkOut;
    }

    public String toJson() {
        String base = "{\n" +
                "  \"checkIn\": \"checkInTime\",\n" +
                "  \"checkOut\": \"checkOutTime\"\n" +
                "}";
        String checkInString = getCheckIn().toString();
        String checkOutString = getCheckOut().toString();
        base = base.replace("checkInTime", checkInString);
        base = base.replace("checkOutTime", checkOutString);
        return base;
    }

    @Override
    public String toString() {
        return "Entry{" +
                "id=" + id +
                ", checkIn=" + checkIn +
                ", checkOut=" + checkOut +
                '}';
    }

    public void fromJson(String json) {
        String idString = json.substring(0, json.indexOf(","));
        id = Long.valueOf(idString.replace("\"id\":", ""));

        String checkInString = json.substring(json.indexOf(","), json.indexOf(",", json.indexOf(",") + 1)).replace(",\"checkIn\":\"", "").replace("\"", "");
        String dateInString = checkInString.split("T")[0];
        String timeInString = checkInString.split("T")[1];

        String checkOutString = json.substring(json.indexOf("checkOut")).replace("checkOut\":", "").replace("\"", "");
        String dateOutString = checkOutString.split("T")[0];
        String timeOutString = checkOutString.split("T")[1];

        setCheckIn(LocalDateTime.of(LocalDate.parse(dateInString), LocalTime.parse(timeInString)));
        setCheckOut(LocalDateTime.of(LocalDate.parse(dateOutString), LocalTime.parse(timeOutString)));
    }
}
