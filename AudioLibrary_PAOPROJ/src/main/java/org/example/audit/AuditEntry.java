package org.example.audit;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
public class AuditEntry {
    private String username;
    private LocalDateTime timestamp;
    private String command;
    private String output;

    @Override
    public String toString() {
        return "AuditEntry{" +
                "username='" + username + '\'' +
                ", timestamp=" + timestamp +
                ", command='" + command + '\'' +
                ", output='" + output + '\'' +
                '}';
    }


}
