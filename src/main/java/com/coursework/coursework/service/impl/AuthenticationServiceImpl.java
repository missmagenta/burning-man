package com.coursework.coursework.service.impl;

import com.coursework.coursework.jwt.JwtUtils;
import com.coursework.coursework.model.event.entity.EventManagementProcess;
import com.coursework.coursework.model.organizer.entity.Organizer;
import com.coursework.coursework.model.participant.entity.Participant;
import com.coursework.coursework.model.payload.BasePayload;
import com.coursework.coursework.model.payload.Payload;
import com.coursework.coursework.model.payload.PayloadWithUser;
import com.coursework.coursework.model.person.entity.Person;
import com.coursework.coursework.model.user.payload.UserPayload;
import com.coursework.coursework.model.user.container.LoginUser;
import com.coursework.coursework.model.user.container.SignUpUser;
import com.coursework.coursework.model.user.entity.LoginedUser;
import com.coursework.coursework.model.user.entity.User;
import com.coursework.coursework.model.user.entity.UserRole;
import com.coursework.coursework.repository.*;
import com.coursework.coursework.security.UserDetailsImpl;
import com.coursework.coursework.service.interfaces.AuthenticationService;
import com.coursework.coursework.utils.UserConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.coursework.coursework.repository.PersonRepository.allFieldsExceptId;
import static com.coursework.coursework.utils.UserConverter.getRoleByOrganizer;
import static com.coursework.coursework.utils.UserConverter.getRoleParticipant;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    private final PersonRepository personRepository;

    private  final OrganizerRepository organizerRepository;
    private final ParticipantRepository participantRepository;
    private final EventRegistrationRepository eventRegistrationRepository;

    @Autowired
    public AuthenticationServiceImpl(AuthenticationManager authenticationManager,
                                     UserRepository userRepository,
                                     PasswordEncoder passwordEncoder,
                                     JwtUtils jwtUtils, PersonRepository personRepository,
                                     OrganizerRepository organizerRepository,
                                     ParticipantRepository participantRepository,
                                     EventRegistrationRepository eventRegistrationRepository) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.personRepository = personRepository;
        this.organizerRepository = organizerRepository;
        this.participantRepository = participantRepository;
        this.eventRegistrationRepository = eventRegistrationRepository;
    }

    @Override
    public Payload authenticateUser(LoginUser loginUser) {
        User user = userRepository.findByUsername(loginUser.getUsername());
        if (user == null) {
            return new BasePayload(400, "Пользователь не найден.");
        }

        LoginedUser loginedUser = login(user.getUsername(), loginUser.getPassword());
        Person person = personRepository.find(user.getPersonId());
        Organizer organizer = organizerRepository.getByPersonId(person.getId());

        UserRole role = null;
        if (organizer != null) {
            role = getRoleByOrganizer(organizer);
        } else {
            Participant participant = participantRepository.getByPersonId(person.getId());
            if (participant != null) {
                role = getRoleParticipant();
            }
        }
        if (role == null)  {
            role = UserRole.USER;
        }

        Integer omeId = null;
        EventManagementProcess ome = eventRegistrationRepository.findUpcomingOmeId(person.getId());
        if (ome != null) {
            omeId = ome.getId();
        }

        UserPayload userPayload = new UserPayload(
                loginedUser.username(),
                role,
                omeId,
                loginedUser.jwtToken(),
                person.getId(),
                person.getName() + " " + person.getSurname()
        );

        return new PayloadWithUser(200, userPayload);
    }

    @Override
    public Payload registerUser(SignUpUser signupUser) {

        Person person = personRepository.findByCondition(
                allFieldsExceptId(
                        signupUser.getName(),
                        signupUser.getSurname(),
                        signupUser.getEmail(),
                        signupUser.getPhoneNumber(),
                        signupUser.getPersonGender(),
                        signupUser.getBirthDate()
                )
        );
        if (person == null) {
            person = new Person();
            person.setName(signupUser.getName());
            person.setSurname(signupUser.getSurname());
            person.setEmail(signupUser.getEmail());
            person.setContactNumber(signupUser.getPhoneNumber());
            person.setGender(signupUser.getPersonGender());
            person.setBirthDate(signupUser.getBirthDate());
            personRepository.insert(person);
        }

        Organizer organizer = organizerRepository.getByPersonId(person.getId());
        Participant participant = participantRepository.getByPersonId(person.getId());
        UserRole role;
        if (organizer != null) {
            role = getRoleByOrganizer(organizer);
        } else if (participant != null) {
            role = getRoleParticipant();
        } else {
            role = UserRole.USER;
        }

        User user = new User();
        user.setPersonId(person.getId());
        user.setUsername(signupUser.getUsername());
        user.setPassword(passwordEncoder.encode(signupUser.getPassword()));
        user.setRole(role);

        Integer omeId = null;
        if (person != null) {
            EventManagementProcess ome = eventRegistrationRepository.findUpcomingOmeId(person.getId());
            if (ome != null) {
                omeId = ome.getId();
            }
        }

        userRepository.insert(user);
        LoginedUser loginedUser = login(user.getUsername(), signupUser.getPassword());
        UserPayload userPayload = new UserPayload(
                loginedUser.username(),
                role,
                omeId,
                loginedUser.jwtToken(),
                person.getId(),
                person.getName() + " " + person.getSurname()
        );

        return new PayloadWithUser(200, userPayload);
    }

    private LoginedUser login(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return UserConverter.convertToUserDTO(userDetails, jwt);
    }
}
