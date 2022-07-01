package org.murolike.passportService.models;

import javax.persistence.*;
import java.util.Calendar;

@Table
@Entity(name = "tmp_passports")
public class TmpPassport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column
    protected String series;

    @Column
    protected String number;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", insertable = false, updatable = false)
    protected Calendar createdAt;

    public TmpPassport() {
    }

    public TmpPassport(String series, String number) {
        this.series = series;
        this.number = number;
    }

    public Long getId() {
        return id;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Calendar getCreatedAt() {
        return createdAt;
    }
}
