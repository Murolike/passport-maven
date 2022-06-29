package org.murolike.passportService.models;

import javax.persistence.*;
import java.util.Calendar;

@Entity
@Table(name = "log_request_search")
public class LogRequestSearch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String series;

    @Column
    private String number;

    @Column(name = "request_ip")
    private String requestIp;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", insertable = false, updatable = false)
    private Calendar createdAt;

    @Column(name = "search_result")
    private String searchResult;

    @Column(name = "is_successful")
    private Integer isSuccessful;

    @Column(name = "search_in_table")
    private String searchInTable;

    public LogRequestSearch() {
    }

    public LogRequestSearch(String series, String number, String requestIp) {
        this.series = series;
        this.number = number;
        this.requestIp = requestIp;
    }

    public LogRequestSearch(String series, String number, String requestIp, String searchInTable) {
        this.series = series;
        this.number = number;
        this.requestIp = requestIp;
        this.searchInTable = searchInTable;
    }

    public Long getId() {
        return id;
    }

    public String getSeries() {
        return series;
    }

    public String getNumber() {
        return number;
    }

    public String getRequestIp() {
        return requestIp;
    }

    public Calendar getCreatedAt() {
        return createdAt;
    }

    public String getSearchResult() {
        return searchResult;
    }

    public Integer getIsSuccessful() {
        return isSuccessful;
    }

    public void setSearchResult(String searchResult) {
        this.searchResult = searchResult;
    }

    public void setIsSuccessful(Integer isSuccessful) {
        this.isSuccessful = isSuccessful;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setRequestIp(String requestIp) {
        this.requestIp = requestIp;
    }

    public void setSearchInTable(String searchInTable) {
        this.searchInTable = searchInTable;
    }
}
