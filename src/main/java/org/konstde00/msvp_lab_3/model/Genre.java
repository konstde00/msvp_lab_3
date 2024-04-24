package org.konstde00.msvp_lab_3.model;

public enum Genre {

    ADVENTURE("Adventure"),
    DYSTOPIA("Dystopia"),
    ROMANCE("Romance"),
    PHILOSOPHICAL_FICTION("Philosophical Fiction");

    private final String name;

    Genre(String name) {
        this.name = name;
    }
}
