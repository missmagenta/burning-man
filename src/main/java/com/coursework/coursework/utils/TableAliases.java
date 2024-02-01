package com.coursework.coursework.utils;


import com.coursework.coursework.models.tables.*;

import static com.coursework.coursework.utils.Aliases.*;

public final class TableAliases {
    private TableAliases() {}
    public static final EventArchive EVENT_ARCHIVE_TABLE = EventArchive.EVENT_ARCHIVE.as(EVENT_ARCHIVE_ALIAS);
    public static final Person PERSON_TABLE = Person.PERSON.as(PERSON_ALIAS);
    public static final Role ROLE_TABLE = Role.ROLE.as(ROLE_ALIAS);
    public static final Organizer ORGANIZER_TABLE = Organizer.ORGANIZER.as(ORGANIZER_ALIAS);
    public static final Users USERS_TABLE = Users.USERS.as(USER_ALIAS);
    public static final Event EVENT_TABLE = Event.EVENT.as(EVENT_ALIAS);
    public static final OrganizerManagesEvent EVENT_MANAGEMENT_TABLE = OrganizerManagesEvent.ORGANIZER_MANAGES_EVENT.as(EVENT_MANAGEMENT_ALIAS);
    public static final Registration EVENT_REGISTRATION_TABLE = Registration.REGISTRATION.as(Aliases.EVENT_REGISTRATION_ALIAS);
    public static final Purchase EVENT_SALES_TABLE = Purchase.PURCHASE.as(Aliases.EVENT_SALES_ALIAS);
    public static final MainThemeCandidates MAIN_THEME_TABLE = MainThemeCandidates.MAIN_THEME_CANDIDATES.as(Aliases.MAIN_THEME_ALIAS);
    public static final ArtInstallationApplication ART_APPLICATION_TABLE = ArtInstallationApplication.ART_INSTALLATION_APPLICATION.as(Aliases.ART_APPLICATION_ALIAS);;
    public static final TicketType TICKET_TABLE = TicketType.TICKET_TYPE.as(Aliases.TICKET_ALIAS);
    public static final ParticipantRegistration PARTICIPANT_REGISTRATION_TABLE = ParticipantRegistration.PARTICIPANT_REGISTRATION.as(Aliases.PARTICIPANT_REGISTRATION_ALIAS);
    public static final ParticipantPurchase PARTICIPANT_PURCHASE_TABLE = ParticipantPurchase.PARTICIPANT_PURCHASE.as(Aliases.PARTICIPANT_PURCHASE_ALIAS);
    public static final Participant PARTICIPANT_TABLE = Participant.PARTICIPANT.as(PARTICIPANT_ALIAS);
}
