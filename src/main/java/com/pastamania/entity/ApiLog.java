package com.pastamania.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class ApiLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    @JoinTable(name = "receipt_api_log",
            joinColumns = {@JoinColumn(name = "api_log_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "receipt_no", referencedColumnName = "receiptNo")}
    )
    private List<Receipt> receipts;

    private String apiName;

    private LocalDateTime callDate;

    private LocalDateTime responseDate;

    private String result;

    private String errorDetails;

    private String requestURL;

    @Column(columnDefinition = "json default null")
    private String requestBody;

    @Column(columnDefinition = "json default null")
    private String responseContent;

    public ApiLog(List<Receipt> receipts, String apiName, LocalDateTime callDate, LocalDateTime responseDate,
                  String result, String errorDetails, String requestURL, String requestBody, String responseContent) {
        this.receipts = receipts;
        this.apiName = apiName;
        this.callDate = callDate;
        this.responseDate = responseDate;
        this.result = result;
        this.errorDetails = errorDetails;
        this.requestURL = requestURL;
        this.requestBody = requestBody;
        this.responseContent = responseContent;
    }


}
