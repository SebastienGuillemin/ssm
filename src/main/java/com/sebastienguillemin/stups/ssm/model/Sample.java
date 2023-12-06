package com.sebastienguillemin.stups.ssm.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Sample {
    private String id;
    private String drugType;
    private String chimicalForm;
    private LocalDateTime date;
    private String dateString;
    private List<Constituent> activePrinciples;
    private List<Constituent> cuttingProducts;
    private HashMap<String, String> characteristics;
    private String logo;

    public void setDate(LocalDateTime date) {
        this.date = date;
        DateTimeFormatter CUSTOM_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy 'à' HH:mm:ss");
        this.dateString = this.date.format(CUSTOM_FORMATTER);
    }

    public void addCharacteristics(String charac, String value) {
        this.characteristics.put(charac, value);
    }
}
