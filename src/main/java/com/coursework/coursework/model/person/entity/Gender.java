package com.coursework.coursework.model.person.entity;

public enum Gender {
    Male("Male", 0),
    Female("Female", 1),
    Non_binary("NON-BINARY", 2);

    private final Integer id;
    private final String displayName;

    Gender(String displayName, Integer id) {
        this.displayName = displayName;
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }
}
