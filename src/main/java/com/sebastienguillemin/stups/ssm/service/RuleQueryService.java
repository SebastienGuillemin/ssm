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

    public final static String LOGO_RULE = 
            "PREFIX : <http://www.stups.fr/ontologies/2023/stups/>"
        + "SELECT ?match\n"
        + "WHERE\n"
        + "{\n"
        + "    ?s1 a :Echantillon .\n"
        + "    ?s2 a :Echantillon .\n"
        + "    ?s1 :id \"%s\" .\n"
        + "    ?s2 :id \"%s\" .\n"
        + "    ?s1 :logo ?logo1 .\n"
        + "    ?s2 :logo ?logo2 .\n"
        + "    ?s1 :nomLogo ?logoName1 .\n"
        + "    ?s2 :nomLogo ?logoName2 .\n"
        + "    BIND((?logo1 = ?logo2 && ?logoName1 = ?logoName2) as ?match)\n"
        + "}";

    public final static String CHIMICAL_FORM_RULE =
          "PREFIX : <http://www.stups.fr/ontologies/2023/stups/>"
        + "SELECT ?match\n"
        + "WHERE\n"
        + "{\n"
        + "    ?s1 a :Echantillon .\n"
        + "    ?s2 a :Echantillon .\n"
        + "    ?s1 :id \"%s\" .\n"
        + "    ?s2 :id \"%s\" .\n"
        + "    ?s1 :aPrincipeActif ?ap1 .\n"
        + "    ?s2 :aPrincipeActif ?ap2 .\n"
        + "    ?ap1 :aFormeChimique ?f1 .\n"
        + "    ?ap2 :aFormeChimique ?f2 .\n"
        + "    BIND ((?f1 = ?f2) as ?match)\n"
        + "}";

    public final static String DATE_RULE = 
          "PREFIX : <http://www.stups.fr/ontologies/2023/stups/>"
        + "SELECT ?match\n"
        + "WHERE\n"
        + "{\n"
        + "    ?s1 a :Echantillon .\n"
        + "    ?s2 a :Echantillon .\n"
        + "    ?s1 :id \"%s\" .\n"
        + "    ?s2 :id \"%s\" .\n"
        + "    ?s1 :provientDe ?se1 .\n"
        + "    ?se1 :estDansSaisine ?sa1 .\n"
        + "    ?sa1 :date ?d1 .\n"
        + "    \n"
        + "    ?s2 :provientDe ?se2 .\n"
        + "    ?se2 :estDansSaisine ?sa2 .\n"
        + "    ?sa2 :date ?d2 .\n"
        + "    bind ((?d1 - ?d2) as ?diff) .\n"
        + "    bind (if(?diff < \"P0Y0M0DT0H0M20.000S\"^^xsd:duration, (-1) * ?diff, ?diff) as ?absDiff) .\n"
        + "    bind ((?absDiff < \"P0Y6M0DT0H0M20.000S\"^^xsd:duration) as ?match) .\n"
        + "}";

    public static final String MACROSCOPIC_CHARACTERISTICS_RULE =
          "PREFIX : <http://www.stups.fr/ontologies/2023/stups/>\n"
        + "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
        + "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n"
        + "\n"
        + "SELECT ?match\n"
        + "WHERE \n"
        + "{\n"
        + "    {\n"
        + "		SELECT (COUNT(?caracMatch) as ?countCaracNoMatch)\n"
        + "        WHERE \n"
        + "        {\n"
        + "            ?s1 a :Echantillon .\n"
        + "            ?s2 a :Echantillon .\n"
        + "            ?s1 :id \"%s\" .\n"
        + "            ?s2 :id \"%s\" .\n"
        + "            ?s1 ?p1 ?v1 .\n"
        + "            ?s2 ?p2 ?v2 .\n"
        + "            ?p1 rdfs:subPropertyOf :caracteristiqueMacroscopique .\n"
        + "            ?p2 rdfs:subPropertyOf :caracteristiqueMacroscopique .\n"
        + "            FILTER(?p1 = ?p2 && ?p1 != :caracteristiqueMacroscopique && ?p2 != :caracteristiqueMacroscopique)\n"
        + "            BIND(datatype(?v1) as ?type1) .    \n"
        + "            BIND(datatype(?v2) as ?type2) .    \n"
        + "            BIND(IF(?v1 >= ?v2, ?v1, ?v2) as ?maxValue) .\n"
        + "            BIND(IF(?v1 < ?v2, ?v1, ?v2) as ?minValue) .\n"
        + "            BIND ((((?type1 = xsd:string || ?type1 = xsd:boolean) && ?v1 = ?v2) || ((?type1 = xsd:decimal || ?type1 = xsd:float || ?type1 = xsd:integer) && (100.0 - (?minValue * 100.0 / ?maxValue)) < 5)) as ?caracMatch)\n"
        + "    		FILTER(!?caracMatch)\n"
        + "        }\n"
        + "    }\n"
        + "    BIND ((?countCaracNoMatch = 0) as ?match)\n"
        + "}";

    public final static String ACTIVE_PRINCIPLES_RULE =
          "PREFIX : <http://www.stups.fr/ontologies/2023/stups/>\n"
        + "SELECT DISTINCT ?match\n"
        + "WHERE\n"
        + "{\n"
        + "    {\n"
        + "        SELECT (COUNT(DISTINCT ?ap1) as ?countAP1) (COUNT(DISTINCT ?ap2) as ?countAP2) (COUNT(DISTINCT ?sb1) as ?countSB1) (COUNT(DISTINCT ?sb2) as ?countSB2)\n"
        + "        WHERE\n"
        + "        {\n"
        + "            ?s1 a :Echantillon .\n"
        + "            ?s1 :id \"%s\" .\n"
        + "            ?s1 :aPrincipeActif ?ap1 .\n"
        + "            ?ap1 :aSubstance ?sb1 .\n"
        + "            ?s2 a :Echantillon .\n"
        + "            ?s2 :id \"%s\" .\n"
        + "            ?s2 :aPrincipeActif ?ap2 .\n"
        + "            ?ap2 :aSubstance ?sb2 .\n"
        + "        }\n"
        + "    }\n"
        + "    {\n"
        + "        SELECT (COUNT(DISTINCT ?sb) as ?commonSB)\n"
        + "        WHERE\n"
        + "        {\n"
        + "            ?s1 a :Echantillon .\n"
        + "            ?s1 :id \"%s\" .\n"
        + "            ?s1 :aPrincipeActif ?ap1 .\n"
        + "            ?ap1 :aSubstance ?sb .\n"
        + "            ?s2 a :Echantillon .\n"
        + "            ?s2 :id \"%s\" .\n"
        + "            ?s2 :aPrincipeActif ?ap2 .\n"
        + "            ?ap2 :aSubstance ?sb .\n"
        + "        }\n"
        + "    }\n"
        + "    BIND ((?countAP1 > 0 &&  ?countAP1 = ?countAP2 && ?countSB1 = ?countSB2 && ?countSB1 = ?commonSB) as ?match)\n"
        + "}";

    public final static String DOSAGE_ACTIVE_PRINCIPLES_RULE =
          "PREFIX : <http://www.stups.fr/ontologies/2023/stups/>\n"
        + "SELECT ?match\n"
        + "WHERE {\n"
        + "    {\n"
        + "        SELECT ?dosageMatch\n"
        + "        WHERE {\n"
        + "            ?s1 a :Echantillon .\n"
        + "            ?s2 a :Echantillon .\n"
        + "            ?s1 :id \"%s\" .\n"
        + "            ?s2 :id \"%s\" .\n"
        + "            ?s1 :aPrincipeActif ?ap1 .\n"
        + "            ?ap1 :aSubstance ?sb .\n"
        + "            ?ap1 :dosage ?d1 .\n"
        + "            ?s2 :aPrincipeActif ?ap2 .\n"
        + "            ?ap2 :aSubstance ?sb .\n"
        + "            ?ap2 :dosage ?d2\n"
        + "            BIND ((ABS(?d1 - ?d2) < 5) as ?dosageMatch)\n"
        + "        }\n"
        + "        GROUP BY ?dosageMatch\n"
        + "    }\n"
        + "    BIND ((?dosageMatch != false) as ?match)\n"
        + "}";

    public final static String CUTTING_PRODUCTS_RULE =
          "PREFIX : <http://www.stups.fr/ontologies/2023/stups/>"
        + "SELECT DISTINCT ?match\n"
        + "WHERE\n"
        + "{\n"
        + "    {\n"
        + "        SELECT (COUNT(DISTINCT ?pc1) as ?countPC1) (COUNT(DISTINCT ?pc2) as ?countPC2) (COUNT(DISTINCT ?sb1) as ?countSB1) (COUNT(DISTINCT ?sb2) as ?countSB2)\n"
        + "        WHERE\n"
        + "        {\n"
        + "            ?s1 a :Echantillon .\n"
        + "            ?s1 :id \"%s\" .\n"
        + "            ?s1 :aProduitCoupage ?pc1 .\n"
        + "            ?pc1 :aSubstance ?sb1 .\n"
        + "            ?s2 a :Echantillon .\n"
        + "            ?s2 :id \"%s\" .\n"
        + "            ?s2 :aProduitCoupage ?pc2 .\n"
        + "            ?pc2 :aSubstance ?sb2 .\n"
        + "        }\n"
        + "    }\n"
        + "    {\n"
        + "        SELECT (COUNT(DISTINCT ?sb) as ?commonSB)\n"
        + "        WHERE\n"
        + "        {\n"
        + "            ?s1 a :Echantillon .\n"
        + "            ?s1 :id \"%s\" .\n"
        + "            ?s1 :aProduitCoupage ?pc1 .\n"
        + "            ?pc1 :aSubstance ?sb .\n"
        + "            ?s2 a :Echantillon .\n"
        + "            ?s2 :id \"%s\" .\n"
        + "            ?s2 :aProduitCoupage ?pc2 .\n"
        + "            ?pc2 :aSubstance ?sb .\n"
        + "        }\n"
        + "    }\n"
        + "    BIND ((?countPC1 > 0 &&  ?countPC1 = ?countPC2 && ?countSB1 = ?countSB2 && ?countSB1 = ?commonSB) as ?match)\n"
        + "}";
    
    public final static String DOSAGE_CUTTING_PRODUCTS_RULE =
          "PREFIX : <http://www.stups.fr/ontologies/2023/stups/>\n"
        + "SELECT ?match\n"
        + "WHERE {\n"
        + "    {\n"
        + "        SELECT ?dosageMatch\n"
        + "        WHERE {\n"
        + "            ?s1 a :Echantillon .\n"
        + "            ?s2 a :Echantillon .\n"
        + "            ?s1 :id \"%s\" .\n"
        + "            ?s2 :id \"%s\" .\n"
        + "            ?s1 :aProduitCoupage ?pc1 .\n"
        + "            ?pc1 :aSubstance ?sb .\n"
        + "            ?pc1 :dosage ?d1 .\n"
        + "            ?s2 :aProduitCoupage ?pc2 .\n"
        + "            ?pc2 :aSubstance ?sb .\n"
        + "            ?pc2 :dosage ?d2\n"
        + "            BIND ((ABS(?d1 - ?d2) < 5) as ?dosageMatch)\n"
        + "        }\n"
        + "        GROUP BY ?dosageMatch\n"
        + "    }\n"
        + "    BIND ((?dosageMatch != false) as ?match)\n"
        + "}";

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
        RuleQuery logoQuery = new RuleQuery("Même logos", String.format(LOGO_RULE, sample1Id, sample2Id));
        RuleQuery chimicalFormQuery = new RuleQuery("Mêmes formes chimiques", String.format(CHIMICAL_FORM_RULE, sample1Id, sample2Id));
        RuleQuery dateQuery = new RuleQuery("Dates de saisie éloignés de moins de 6 mois", String.format(DATE_RULE, sample1Id, sample2Id));
        RuleQuery macroCaracQuery = new RuleQuery("Caractéristiques macroscopiques différentes de moins de 5%", String.format(MACROSCOPIC_CHARACTERISTICS_RULE, sample1Id, sample2Id));
        // TODO : caract. macro.

        // TODO : ne tester le dosage des principes actifs que s'il y a les mêmes principes actifs.
        RuleQuery activePrincipaleQuery = new RuleQuery("Mêmes principes actifs", String.format(ACTIVE_PRINCIPLES_RULE, sample1Id, sample2Id, sample1Id, sample2Id));
        RuleQuery dosageActivePrincipleQuery = new RuleQuery("Écart de moins de 5% pour les dosages des principes actifs", String.format(DOSAGE_ACTIVE_PRINCIPLES_RULE, sample1Id, sample2Id));
        
        // TODO : ne tester le dosage des produits de coupage que s'il y a les mêmes produits de coupage.
        RuleQuery cuttingProductsQuery = new RuleQuery("Mêmes produits de coupage", String.format(CUTTING_PRODUCTS_RULE, sample1Id, sample2Id, sample1Id, sample2Id));
        RuleQuery dosageCuttingProductsQuery = new RuleQuery("Écart de moins de 5% pour les dosages des produits de coupage", String.format(DOSAGE_CUTTING_PRODUCTS_RULE, sample1Id, sample2Id));

        RuleQuery typeQuery = new RuleQuery("Mêmes types de drogue", String.format(DRUG_TYPE_RULE, sample1Id, sample2Id));

        queries.add(logoQuery);
        queries.add(chimicalFormQuery);
        queries.add(dateQuery);
        queries.add(macroCaracQuery);
        queries.add(activePrincipaleQuery);
        queries.add(dosageActivePrincipleQuery);
        queries.add(cuttingProductsQuery);
        queries.add(dosageCuttingProductsQuery);
        queries.add(typeQuery);

        
        for (RuleQuery ruleQuery : queries) {
            boolean executionComplete = this.repository.execute(ruleQuery);
    
            if (!executionComplete)
                //TODO : créer exception custome
                throw new Exception("Error while querying : " + ruleQuery.getQuery());
        }

        return queries;
    }
}
