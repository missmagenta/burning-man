package com.coursework.coursework.utils;

import com.coursework.coursework.model.organizer.entity.Organizer;
import com.coursework.coursework.model.user.entity.LoginedUser;
import com.coursework.coursework.model.user.entity.User;
import com.coursework.coursework.model.user.entity.UserRole;
import com.coursework.coursework.security.UserDetailsImpl;

public final class UserConverter {
    private UserConverter() {}

    public static LoginedUser convertToUserDTO(UserDetailsImpl userDetails, String jwt) {
        UserRole userRole = UserRole.valueOf(userDetails.getAuthority().getAuthority());
        System.out.println(userDetails.getUsername() + userRole + jwt);
        return new LoginedUser(userDetails.getUsername(), userRole, jwt);
    }

    public static LoginedUser convertToUserDTO(User user) {
        return new LoginedUser(user.getUsername(), user.getRole(), "");
    }

    public static UserRole getRoleByOrganizer(Organizer organizer) {
        switch (organizer.getRole().getOrganizerRole()) {
            case ART_MANAGER -> {
                return UserRole.ART_MANAGER;
            }
            case SALES_MANAGER -> {
                return UserRole.SALES_MANAGER;
            }
            case BURNING_MANAGER -> {
                return UserRole.BURNING_MANAGER;
            }
            case MAIN_IDEA_MANAGER -> {
                return UserRole.MAIN_IDEA_MANAGER;
            }
            default -> {
                return UserRole.USER;
            }
        }
    }


    public static UserRole getRoleParticipant() {
        System.out.println("i am participant");
        return UserRole.PARTICIPANT;
    }
}