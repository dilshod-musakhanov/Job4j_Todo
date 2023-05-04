package ru.job4j.todo.util;

import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.TimeZone;

public class TimeZoneConvertor {
    public static void setTimeZone(User user, Task task) {
        ZoneId defaultTimeZone = java.util.TimeZone.getDefault().toZoneId();
        if (null == user.getTimezone()) {
            var defTz = TimeZone.getDefault().toZoneId().toString();
            user.setTimezone(defTz);
        }
        ZoneId userTimeZone = ZoneId.of(user.getTimezone());
        LocalDateTime dateTime = task.getCreated()
                .atZone(defaultTimeZone)
                .withZoneSameInstant(userTimeZone)
                .toLocalDateTime();
        task.setCreated(dateTime);
    }
}
