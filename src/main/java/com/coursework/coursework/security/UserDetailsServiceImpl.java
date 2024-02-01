package com.coursework.coursework.security;

import com.coursework.coursework.model.organizer.entity.Organizer;
import com.coursework.coursework.model.participant.entity.Participant;
import com.coursework.coursework.model.user.entity.User;
import com.coursework.coursework.model.user.entity.UserRole;
import com.coursework.coursework.repository.OrganizerRepository;
import com.coursework.coursework.repository.ParticipantRepository;
import com.coursework.coursework.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.coursework.coursework.utils.UserConverter.getRoleByOrganizer;
import static com.coursework.coursework.utils.UserConverter.getRoleParticipant;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    private final OrganizerRepository organizerRepository;
    private final ParticipantRepository participantRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository, OrganizerRepository organizerRepository, ParticipantRepository participantRepository) {
        this.userRepository = userRepository;
        this.organizerRepository = organizerRepository;
        this.participantRepository = participantRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User Not Found with username: " + username);
        }

        Organizer organizer = organizerRepository.getByPersonId(user.getPersonId());
        if (organizer != null) {
            user.setRole(getRoleByOrganizer(organizer));
        } else {
            Participant participant = participantRepository.getByPersonId(user.getPersonId());
            if (participant != null) {
                user.setRole(getRoleParticipant());
            } else {
                user.setRole(UserRole.USER);
            }
        }
        return UserDetailsImpl.build(user);
    }

    public User getUser() {
        String username =
                ((UsernamePasswordAuthenticationToken) SecurityContextHolder
                        .getContext()
                        .getAuthentication()).getName();
        return userRepository.findByUsername(username);
    }

}
