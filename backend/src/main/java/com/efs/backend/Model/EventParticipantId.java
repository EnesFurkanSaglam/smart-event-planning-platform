package com.efs.backend.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventParticipantId implements Serializable {

    private Long eventId;

    private Long userId;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EventParticipantId)) return false;

        EventParticipantId that = (EventParticipantId) o;

        return Objects.equals(eventId, that.eventId) && Objects.equals(userId, that.userId);
    }


    @Override
    public int hashCode() {
        return Objects.hash(eventId, userId);
    }
}
