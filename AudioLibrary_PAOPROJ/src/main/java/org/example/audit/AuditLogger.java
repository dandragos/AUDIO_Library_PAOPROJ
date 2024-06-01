package org.example.audit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AuditLogger {
    private static final String AUDIT_FILE_PATH = "src/logs/audit_logs/audit_log.json";

    public static void log(String username, String command, String output) {
        LocalDateTime timestamp = LocalDateTime.now();
        AuditEntry entry = new AuditEntry(username, timestamp, command, output);
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();

        String json = gson.toJson(entry) + "\n";

        try (FileWriter writer = new FileWriter(AUDIT_FILE_PATH, true)) {
            writer.append(json).append("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<AuditEntry> readAuditEntriesByUsername(String username, int page, int pageSize) {
        List<AuditEntry> userAuditEntries = new ArrayList<>();
        int startIndex = (page - 1) * pageSize;
        int endIndex = startIndex + pageSize;

        try (BufferedReader reader = new BufferedReader(new FileReader(AUDIT_FILE_PATH))) {
            String line;
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                    .create();
            Type listType = new TypeToken<List<AuditEntry>>() {}.getType();

            int entriesAdded = 0;
            while ((line = reader.readLine()) != null && entriesAdded < endIndex) {
                if (!line.trim().isEmpty()) {
                    AuditEntry entry = gson.fromJson(line, AuditEntry.class);
                    if (entry != null && entry.getUsername().equals(username)) {
                        if (entriesAdded >= startIndex) {
                            userAuditEntries.add(entry);
                        }
                        entriesAdded++;
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return userAuditEntries;
    }


}

