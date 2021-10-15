package com.example;

public class Entity<Id extends NumericId> {

    private Id id;

    public Id getId() {
        return id;
    }

    public void setId(Id id) {
        this.id = id;
    }
}
