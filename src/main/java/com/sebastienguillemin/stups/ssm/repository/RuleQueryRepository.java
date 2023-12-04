package com.sebastienguillemin.stups.ssm.repository;

import org.eclipse.rdf4j.query.Binding;
import org.eclipse.rdf4j.query.BindingSet;
import org.eclipse.rdf4j.query.QueryEvaluationException;
import org.eclipse.rdf4j.query.QueryLanguage;
import org.eclipse.rdf4j.query.TupleQuery;
import org.eclipse.rdf4j.query.TupleQueryResult;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ontotext.graphdb.repository.http.GraphDBHTTPRepository;
import com.sebastienguillemin.stups.ssm.request.RuleQuery;

@Repository
public class RuleQueryRepository {
    @Autowired
    private GraphDBHTTPRepository repository;

    /**
     * 
     * @param query the query to execute.
     * @return true if the request was executed correctly, false otherwise.
     */
    public boolean execute(RuleQuery query) {
        RepositoryConnection connection = this.repository.getConnection();
        try {
            TupleQuery tupleQuery = connection.prepareTupleQuery(QueryLanguage.SPARQL, query.getQuery());

            TupleQueryResult tupleQueryResult = tupleQuery.evaluate();
            while (tupleQueryResult.hasNext()) {
                BindingSet bindingSet = tupleQueryResult.next();

                Binding binding = bindingSet.getBinding("match");
                
                query.setPassed(binding.getValue().toString().equals("\"true\"^^<http://www.w3.org/2001/XMLSchema#boolean>"));
            }
            tupleQueryResult.close();
        } catch(QueryEvaluationException e) {
            e.printStackTrace();
            return false;
        }
        finally {
            connection.close();
        }

        return true;
    }
}
