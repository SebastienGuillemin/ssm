package com.sebastienguillemin.stups.ssm.repository;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.rdf4j.model.Value;
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
import com.sebastienguillemin.stups.ssm.model.Sample;

@Repository
public class SampleRepository {
    @Autowired
    private GraphDBHTTPRepository repository;

    public List<Sample> findSamples()  {
        RepositoryConnection connection = this.repository.getConnection();
        List<Sample> samples = new ArrayList<>();

        try {
            String query = 
              "PREFIX : <http://www.stups.fr/ontologies/2023/stups/>\n"
            + "SELECT DISTINCT ?id\n"
            + "WHERE {\n"
            + "     ?e a :Echantillon .\n"
            + "     ?e :id ?id .\n"
            + "}\n"
            + "ORDER BY ?id";

            TupleQuery tupleQuery = connection.prepareTupleQuery(QueryLanguage.SPARQL, query);

            TupleQueryResult tupleQueryResult = tupleQuery.evaluate();
            Sample sample;
            while (tupleQueryResult.hasNext()) {
                BindingSet bindingSet = tupleQueryResult.next();
                
                Binding binding = bindingSet.getBinding("id");
                sample = new Sample();
                sample.setId(binding.getValue().toString());

                samples.add(sample);
            }
            tupleQueryResult.close();
        } catch(QueryEvaluationException e) {
            e.printStackTrace();
            return null;
        }
        finally {
            connection.close();
        }

        return samples;
    }

    public Sample findSampleById(String sampleId) {
        Sample sample = null;
        RepositoryConnection connection = this.repository.getConnection();

        try {
            String query = 
              "PREFIX : <http://www.stups.fr/ontologies/2023/stups/>\n"
            + "SELECT ?d\n"
            + "WHERE {\n"
            + "     ?e a :Echantillon .\n"
            + "     ?e :id \"%s\" .\n"
            + "     ?e :typeDrogue ?d .\n"
            + "}";

            TupleQuery tupleQuery = connection.prepareTupleQuery(QueryLanguage.SPARQL, String.format(query, sampleId));

            TupleQueryResult tupleQueryResult = tupleQuery.evaluate();
            
            while (tupleQueryResult.hasNext()) {
                BindingSet bindingSet = tupleQueryResult.next();
                
                sample = new Sample();
                sample.setId(sampleId);
                sample.setDrugType(bindingSet.getBinding("d").getValue().toString());
            }
            tupleQueryResult.close();
        } catch(QueryEvaluationException e) {
            e.printStackTrace();
            return null;
        }
        finally {
            connection.close();
        }

        return sample;
    }
}
