package org.murolike.passportService.enums;

public enum InvalidPassportTables {
    INVALID_PASSPORTS_MASTER("invalid_passports_master"),
    INVALID_PASSPORTS_SLAVE("invalid_passports_slave"),

    TMP_PASSPORTS("tmp_passports");

    private final String title;

    InvalidPassportTables(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
