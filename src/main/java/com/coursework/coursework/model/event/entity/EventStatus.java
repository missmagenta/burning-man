package com.coursework.coursework.model.event.entity;

public enum EventStatus {
    SCHEDULED("Scheduled", 0),
    IN_PROGRESS("In progress", 1),
    CANCELED("Canceled", 2),
    STOPPED("Stopped", 3),
    COMPLETED("Completed", 4);

    private final String displayStatus;
    private final Integer id;


    EventStatus(String displayStatus, Integer id) {
        this.displayStatus = displayStatus;
        this.id = id;
    }

    public String getDisplayStatus() {
        return displayStatus;
    }

    public static EventStatus valueOf(int id) {
        for (EventStatus value : EventStatus.values()) {
            if (value.id == id) {
                return value;
            }
        }
        throw new IllegalArgumentException("Invalid Enum id: " + id);
    }
}
