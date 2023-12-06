package com.sebastienguillemin.stups.ssm.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Sample {
    private String id;
    private String drugType;
    private String chimicalForm;
    private String date;
    private List<Constituent> activePrinciples;
    private List<Constituent> cuttingProducts;
}
