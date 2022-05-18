package models;

import javax.persistence.*;

@Entity
@Table(name = "invalid_passports_master")
public class InvalidPassportMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String series;

    private String number;
    @Column(name = "created_at", insertable = false, updatable = false)
    private String createdAt;

    public InvalidPassportMaster() {
    }

    public InvalidPassportMaster(String series, String number) {
        this.series = series;
        this.number = number;
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

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
