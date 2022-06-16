package org.murolike.passportService.models;

import javax.persistence.*;

@Entity
@Table(name = "invalid_passports_master")
public class MasterPassport implements Passport{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Embedded
    protected PassportData passportData;

    public PassportData getPassportData() {
        return passportData;
    }

    public void setPassportData(PassportData passportData) {
        this.passportData = passportData;
    }

    public MasterPassport() {
    }

    public Long getId() {
        return id;
    }
}
