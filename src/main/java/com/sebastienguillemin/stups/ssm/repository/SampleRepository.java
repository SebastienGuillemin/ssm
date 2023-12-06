package com.sebastienguillemin.stups.ssm.repository;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import org.eclipse.rdf4j.model.impl.SimpleIRI;
import org.eclipse.rdf4j.model.impl.SimpleLiteral;
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

import jakarta.xml.bind.DatatypeConverter;

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
        sample.setChimicalForm(this.findChemicalForm(sampleId));
        sample.setDate(this.findDate(sampleId));
        sample.setCharacteristics(this.findCharacteristics(sampleId));
        sample.setLogo(this.findLogo(sampleId));

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
                
                drugType = ((SimpleLiteral) bindingSet.getBinding("d").getValue()).stringValue();
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
                constituent.setName(( (SimpleLiteral) bindingSet.getBinding("name").getValue()).stringValue());
                constituent.setDosage(( (SimpleLiteral) bindingSet.getBinding("dosage").getValue()).floatValue());

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
        List<Constituent> cuttingProducts = new ArrayList<>();

        try {
            String query = 
              "PREFIX : <http://www.stups.fr/ontologies/2023/stups/>\n"
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
                constituent.setName(( (SimpleLiteral) bindingSet.getBinding("name").getValue()).stringValue());
                constituent.setDosage(( (SimpleLiteral) bindingSet.getBinding("dosage").getValue()).floatValue());

                cuttingProducts.add(constituent);
            }
            tupleQueryResult.close();
        } catch(QueryEvaluationException e) {
            e.printStackTrace();
            return null;
        }
        finally {
            connection.close();
        }
        return cuttingProducts;
    }

    private String findChemicalForm(String sampleId) {
        RepositoryConnection connection = this.repository.getConnection();
        String chimicalForm = "";

        try {
            String query = 
              "PREFIX : <http://www.stups.fr/ontologies/2023/stups/>\n"
            + "SELECT ?labelChimicalForm\n"
            + "WHERE {\n"
            + "    ?s a :Echantillon .\n"
            + "    ?s :id  \"%s\" .\n"
            + "    ?s :aPrincipeActif ?ap .\n"
            + "    ?p :aFormeChimique ?cf .\n"
            + "    ?cf :libelleFormeChimique ?labelChimicalForm .\n"
            + "}";

            TupleQuery tupleQuery = connection.prepareTupleQuery(QueryLanguage.SPARQL, String.format(query, sampleId));

            TupleQueryResult tupleQueryResult = tupleQuery.evaluate();
            
            while (tupleQueryResult.hasNext()) {
                BindingSet bindingSet = tupleQueryResult.next();
                
                chimicalForm = ((SimpleLiteral) bindingSet.getBinding("labelChimicalForm").getValue()).stringValue();
            }
            tupleQueryResult.close();
        } catch(QueryEvaluationException e) {
            e.printStackTrace();
            return null;
        }
        finally {
            connection.close();
        }
        return chimicalForm;
    }

    private LocalDateTime findDate(String sampleId) {
        RepositoryConnection connection = this.repository.getConnection();
        LocalDateTime date = null;

        try {
            String query = 
              "PREFIX : <http://www.stups.fr/ontologies/2023/stups/>\n"
            + "SELECT ?date\n"
            + "WHERE\n"
            + "{\n"
            + "    ?s a :Echantillon .\n"
            + "    ?s :id \"%s\" .\n"
            + "    ?s :provientDe ?sealed .\n"
            + "    ?sealed :estDansSaisine ?seizure .\n"
            + "    ?seizure :date ?date .\n"
            + "}";

            TupleQuery tupleQuery = connection.prepareTupleQuery(QueryLanguage.SPARQL, String.format(query, sampleId));

            TupleQueryResult tupleQueryResult = tupleQuery.evaluate();
            
            while (tupleQueryResult.hasNext()) {
                BindingSet bindingSet = tupleQueryResult.next();
                
                String dateString = ((SimpleLiteral) bindingSet.getBinding("date").getValue()).calendarValue().toString();

                date = ((GregorianCalendar) (DatatypeConverter.parseDateTime(dateString))).toZonedDateTime().withZoneSameLocal(ZoneId.systemDefault()).toLocalDateTime();

            }
            tupleQueryResult.close();
        } catch(QueryEvaluationException e) {
            e.printStackTrace();
            return null;
        }
        finally {
            connection.close();
        }
        System.out.println(date);
        return date;
    }

    private HashMap<String, String> findCharacteristics(String sampleId) {
        RepositoryConnection connection = this.repository.getConnection();
        HashMap<String, String> characteristics = new HashMap<>();

        try {
            String query = 
              "PREFIX : <http://www.stups.fr/ontologies/2023/stups/>\n"
            + "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
            + "SELECT ?charac ?value\n"
            + "WHERE\n"
            + "{\n"
            + "    ?s a :Echantillon .\n"
            + "    ?s :id \"%s\".\n"
            + "    ?s ?charac ?value .\n"
            + "    ?charac rdfs:subPropertyOf :caracteristiqueMacroscopique .\n"
            + "    FILTER (?charac != :caracteristiqueMacroscopique)\n"
            + "}";

            TupleQuery tupleQuery = connection.prepareTupleQuery(QueryLanguage.SPARQL, String.format(query, sampleId));

            TupleQueryResult tupleQueryResult = tupleQuery.evaluate();
            String key, value;
            while (tupleQueryResult.hasNext()) {
                BindingSet bindingSet = tupleQueryResult.next();

                key = ((SimpleIRI) bindingSet.getBinding("charac").getValue()).getLocalName();
                value = ((SimpleLiteral) bindingSet.getBinding("value").getValue()).stringValue();

                characteristics.put(key, value);
            }
            tupleQueryResult.close();
        } catch(QueryEvaluationException e) {
            e.printStackTrace();
            return null;
        }
        finally {
            connection.close();
        }
        return characteristics;
    }

    private String findLogo(String sampleId) {
        RepositoryConnection connection = this.repository.getConnection();
        String logo = "";

        try {
            String query = 
              "PREFIX : <http://www.stups.fr/ontologies/2023/stups/>\n"
            + "SELECT ?logo\n"
            + "WHERE {\n"
            + "    ?s a :Echantillon .\n"
            + "    ?s :id \"%s\" .\n"
            + "    ?s :nomLogo ?logo .\n"
            + "}";

            TupleQuery tupleQuery = connection.prepareTupleQuery(QueryLanguage.SPARQL, String.format(query, sampleId));

            TupleQueryResult tupleQueryResult = tupleQuery.evaluate();
            while (tupleQueryResult.hasNext()) {
                BindingSet bindingSet = tupleQueryResult.next();

                logo = ((SimpleLiteral) bindingSet.getBinding("logo").getValue()).stringValue();
            }
            tupleQueryResult.close();
        } catch(QueryEvaluationException e) {
            e.printStackTrace();
            return null;
        }
        finally {
            connection.close();
        }
        return logo;
    }
}
