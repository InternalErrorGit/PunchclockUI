package ch.zli.m223.punchclock;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author P. Gatzka
 * @version 22.09.2021
 * Project: PunchclockUI
 * Class: App
 */
public class RequestHandler {
    private RequestHandler() {

    }

    private static HttpURLConnection getConnection(String url) {
        try {
            URL u = new URL(url);
            return (HttpURLConnection) u.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void insert(Entry entry) {
        try {
            HttpURLConnection connection = getConnection("http://localhost:8080/entries");
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = entry.toJson().getBytes("utf-8");
                os.write(input, 0, input.length);
            }
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                System.out.println(response.toString());
            }
            Model.getModel().update();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public static void getAll() {
        try {
            HttpURLConnection connection = getConnection("http://localhost:8080/entries");
            assert connection != null;
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                String res = response.toString().replace("{", "<").replace("}", ">").replace("[", "").replace("]", "");
                String[] entries = res.toString().split(">,<");
                for (String entry : entries) {
                    String e = entry.replace("<","").replace(">","");
                    Entry obj = new Entry();
                    obj.fromJson(e);
                    Model.getModel().entries.add(obj);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void delete(Long id){
        try {
            HttpURLConnection connection = getConnection("http://localhost:8080/entries/" + id);
            connection.setRequestMethod("DELETE");
            connection.setDoOutput(true);
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                System.out.println(response.toString());
            }
            Model.getModel().update();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
