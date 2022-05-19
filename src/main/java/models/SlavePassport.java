package models;

import javax.persistence.*;

@Entity
@Table(name = "invalid_passports_slave")
public class SlavePassport implements Passport{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Embedded
    protected PassportData passportData;

    public SlavePassport() {
    }

    public Long getId() {
        return id;
    }

    public PassportData getPassportData() {
        return passportData;
    }

    public void setPassportData(PassportData passportData) {
        this.passportData = passportData;
    }
}
