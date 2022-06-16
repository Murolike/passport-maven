package org.murolike.passportService.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "vw_passport")
public class Passport {
    @Id
    private String series;
    private String number;

    public Passport() {
    }

    public String getSeries() {
        return series;
    }

    public String getNumber() {
        return number;
    }
}
