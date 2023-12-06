package com.sebastienguillemin.stups.ssm.model;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Sample {
    private String id;
    private String drugType;
    private String chimicalForm;
    private Date date;
    private List<Constituent> activePrinciples;
    private List<Constituent> cuttingProducts;
}
