package com.coursework.coursework.utils;

import com.coursework.coursework.model.art.entity.ArtApplication;
import com.coursework.coursework.model.art.payload.ArtApplicationPayload;

public class ArtApplicationConverter {
    public static ArtApplicationPayload convertToPayload(ArtApplication record) {
        ArtApplicationPayload artApplicationPayload = new ArtApplicationPayload();
        artApplicationPayload.setId(record.getId());
        artApplicationPayload.setEventId(record.getEventId());
        artApplicationPayload.setEventName(record.getEvent().getName());
        artApplicationPayload.setName(record.getName());
        artApplicationPayload.setMaterial(record.getMaterial());
        artApplicationPayload.setDescription(record.getDescription());
        artApplicationPayload.setDesiredLocation(record.getDesiredLocation());
        artApplicationPayload.setApplicationDate(record.getApplicationDate());
        artApplicationPayload.setApproximateInstallationTime(record.getApproximateInstallationTime());
        artApplicationPayload.setModelLink(record.getModelLink());
        artApplicationPayload.setLightingEquipment(record.getLightingEquipment());
        artApplicationPayload.setParticipantId(record.getParticipantId());

        return artApplicationPayload;
    }
}
