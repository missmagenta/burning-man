package com.coursework.coursework.model.payload;

import lombok.Getter;

import java.util.Collection;

@Getter
public class PayloadWithCollection<T> implements Payload {
    private final Integer code;
    private final String message;
    private final Collection<T> collection;

    public PayloadWithCollection(Integer code, String message, Collection<T> collection) {
        this.code = code;
        this.message = message;
        this.collection = collection;
    }

    public PayloadWithCollection(Integer code, Collection<T> collection) {
        this.code = code;
        this.message = "";
        this.collection = collection;
    }

    @Override
    public Integer code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }

    public Collection<T> collection() {
        return collection;
    }
}
