package com.sebastienguillemin.stups.ssm.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RuleQuery extends SPARQLQuery {
    private String name;
    private boolean passed;
    private boolean hardRule;

    public RuleQuery(String name, String query) {
        this.name = name;
        this.query= query;
        this.passed = false;
        this.hardRule = false;
    }

    public RuleQuery(String name, String query, boolean hardRule) {
        this(name, query);
        this.hardRule = hardRule;
    }
}
