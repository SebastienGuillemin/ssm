package com.sebastienguillemin.stups.ssm.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sebastienguillemin.stups.ssm.repository.RuleQueryRepository;
import com.sebastienguillemin.stups.ssm.request.RuleQuery;

@Service
public class RuleQueryService {
    @Autowired
    private RuleQueryRepository repository;

    public final static String DRUG_TYPE_RULE = 
              "PREFIX : <http://www.stups.fr/ontologies/2023/stups/>\n"
            + "SELECT ?match\n"
            + "WHERE {\n"
            + "   ?s1 a :Echantillon .\n"
            + "   ?s2 a :Echantillon .\n"
            + "   ?s1 :id \"%s\" .\n"
            + "   ?s2 :id \"%s\" .\n"
            + "   ?s1 :typeDrogue ?dt1 .\n"
            + "   ?s2 :typeDrogue ?dt2 .\n"
            + "   BIND ((?dt1 = ?dt2) as ?match)\n"
            + "}";

    public List<RuleQuery> exectuteQueries(String sample1Id, String sample2Id) throws Exception {
        List<RuleQuery> queries = new ArrayList<>();
        RuleQuery query = new RuleQuery("Type de drogue", String.format(DRUG_TYPE_RULE, sample1Id, sample2Id));
        
        queries.add(query);
        
        for (RuleQuery ruleQuery : queries) {
            boolean executionComplete = this.repository.execute(ruleQuery);
    
            if (!executionComplete)
                //TODO : créer exception custome
                throw new Exception("Error while querying : " + ruleQuery.getQuery());
        }

        return queries;
    }
}
