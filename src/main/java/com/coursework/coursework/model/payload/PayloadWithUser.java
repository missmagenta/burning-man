package com.coursework.coursework.model.payload;

import com.coursework.coursework.model.user.payload.UserPayload;
import lombok.Getter;

@Getter
public class PayloadWithUser extends PayloadWithData<UserPayload> {
    public PayloadWithUser(Integer code, UserPayload data) {
        super(code, data);
    }
}
