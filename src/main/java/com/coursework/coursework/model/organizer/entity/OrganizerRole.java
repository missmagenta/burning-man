package com.coursework.coursework.model.organizer.entity;

public enum OrganizerRole {
    ART_MANAGER("ArtManager", 0),
    SALES_MANAGER("SalesManager", 1),
    BURNING_MANAGER("BurningManager", 2),
    MAIN_IDEA_MANAGER("MainIdeaManager", 3);

    private final String displayRole;
    private final Integer id;

    OrganizerRole(String displayRole, Integer id) {
        this.displayRole = displayRole;
        this.id = id;
    }

    public String getDisplayRole() {
        return displayRole;
    }

    public static OrganizerRole valueOf(int id) {
        for (OrganizerRole role : OrganizerRole.values()) {
            if (role.id == id) {
                return role;
            }
        }
        throw new IllegalArgumentException("Invalid enum id" + id);
    }
}
