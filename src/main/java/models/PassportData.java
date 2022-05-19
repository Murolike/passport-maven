package models;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@Embeddable
public class PassportData {

    @Column
    protected String series;

    @Column
    protected String number;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", insertable = false, updatable = false)
    protected Calendar createdAt;

    public PassportData() {
    }

    public PassportData(String series, String number) {
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

    /**
     * @todo Изменить формат
     * @return
     */
    public String getCreatedAt() {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy H:mm:ss");

        return format.format(this.createdAt.getTime());
    }
}
