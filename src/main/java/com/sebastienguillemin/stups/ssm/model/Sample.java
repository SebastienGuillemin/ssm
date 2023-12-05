package com.sebastienguillemin.stups.ssm.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Sample {
    private String id;
    private String drugType;

    private List<Constituent> cuttingProducts;
    private List<Constituent> activePrinciples;
}
