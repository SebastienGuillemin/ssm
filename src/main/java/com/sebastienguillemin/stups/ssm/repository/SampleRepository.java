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
import com.sebastienguillemin.stups.ssm.model.Constituent;
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
        Sample sample = new Sample();
        sample.setId(sampleId);
        sample.setDrugType(this.findDrugType(sampleId));
        sample.setActivePrinciples(this.findActivePrinciples(sampleId));
        sample.setCuttingProducts(this.findCuttingProducts(sampleId));

        return sample;
    }

    private String findDrugType(String sampleId) {
        RepositoryConnection connection = this.repository.getConnection();
        String drugType = "";

        try {
            String query = 
              "PREFIX : <http://www.stups.fr/ontologies/2023/stups/>\n"
            + "SELECT ?d\n"
            + "WHERE {\n"
            + "     ?s a :Echantillon .\n"
            + "     ?s :id \"%s\" .\n"
            + "     ?s :typeDrogue ?d .\n"
            + "}";

            TupleQuery tupleQuery = connection.prepareTupleQuery(QueryLanguage.SPARQL, String.format(query, sampleId));

            TupleQueryResult tupleQueryResult = tupleQuery.evaluate();
            
            while (tupleQueryResult.hasNext()) {
                BindingSet bindingSet = tupleQueryResult.next();
                
                drugType = bindingSet.getBinding("d").getValue().toString();
            }
            tupleQueryResult.close();
        } catch(QueryEvaluationException e) {
            e.printStackTrace();
            return null;
        }
        finally {
            connection.close();
        }
        return drugType;
    }

    private List<Constituent> findActivePrinciples(String sampleId) {
        RepositoryConnection connection = this.repository.getConnection();
        List<Constituent> activePrinciples = new ArrayList<>();

        try {
            String query = 
              "PREFIX : <http://www.stups.fr/ontologies/2023/stups/>\n"
            + "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
            + "SELECT ?name ?dosage\n"
            + "WHERE {\n"
            + "    ?s a :Echantillon .\n"
            + "    ?s :id \"%s\" .\n"
            + "    \n"
            + "    ?s :aPrincipeActif ?ap .\n"
            + "    ?ap :aSubstance ?sb .\n"
            + "    ?sb :nomSubstance ?name .\n"
            + "    ?ap :dosage ?dosage .\n"
            + "}";

            TupleQuery tupleQuery = connection.prepareTupleQuery(QueryLanguage.SPARQL, String.format(query, sampleId));

            TupleQueryResult tupleQueryResult = tupleQuery.evaluate();
            
            Constituent constituent;
            while (tupleQueryResult.hasNext()) {
                BindingSet bindingSet = tupleQueryResult.next();
                
                constituent = new Constituent();
                constituent.setName(bindingSet.getBinding("name").getValue().toString());
                constituent.setDosage(bindingSet.getBinding("dosage").getValue().toString());

                activePrinciples.add(constituent);
            }
            tupleQueryResult.close();
        } catch(QueryEvaluationException e) {
            e.printStackTrace();
            return null;
        }
        finally {
            connection.close();
        }
        return activePrinciples;
    }

    private List<Constituent> findCuttingProducts(String sampleId) {
        RepositoryConnection connection = this.repository.getConnection();
        List<Constituent> activePrinciples = new ArrayList<>();

        try {
            String query = 
              "PREFIX : <http://www.stups.fr/ontologies/2023/stups/>\n"
            + "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
            + "SELECT ?name ?dosage\n"
            + "WHERE {\n"
            + "    ?s a :Echantillon .\n"
            + "    ?s :id \"%s\" .\n"
            + "    \n"
            + "    ?s :aProduitCoupage ?cp .\n"
            + "    ?cp :aSubstance ?sb .\n"
            + "    ?sb :nomSubstance ?name .\n"
            + "    ?cp :dosage ?dosage .\n"
            + "}";

            TupleQuery tupleQuery = connection.prepareTupleQuery(QueryLanguage.SPARQL, String.format(query, sampleId));

            TupleQueryResult tupleQueryResult = tupleQuery.evaluate();
            
            Constituent constituent;
            while (tupleQueryResult.hasNext()) {
                BindingSet bindingSet = tupleQueryResult.next();
                
                constituent = new Constituent();
                constituent.setName(bindingSet.getBinding("name").getValue().toString());
                constituent.setDosage(bindingSet.getBinding("dosage").getValue().toString());

                activePrinciples.add(constituent);
            }
            tupleQueryResult.close();
        } catch(QueryEvaluationException e) {
            e.printStackTrace();
            return null;
        }
        finally {
            connection.close();
        }
        return activePrinciples;
    }
}
