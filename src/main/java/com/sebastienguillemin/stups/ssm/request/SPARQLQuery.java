package com.sebastienguillemin.stups.ssm.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class SPARQLQuery {
    protected String query;
}
