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
        + "    BIND ((?countPC1 = ?countPC2 && ?countSB1 = ?countSB2 && ?countSB1 = ?commonSB) as ?match)\n"
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

    public List<RuleQuery> exectuteQueries(String sample1Id, String sample2Id) throws Exception {
        List<RuleQuery> queries = new ArrayList<>();
        RuleQuery typeQuery = new RuleQuery("Même types de drogue", String.format(DRUG_TYPE_RULE, sample1Id, sample2Id));
        RuleQuery cuttingProductsQuery = new RuleQuery("Même types de produits de coupage", String.format(CUTTING_PRODUCTS_RULE, sample1Id, sample2Id, sample1Id, sample2Id));
        RuleQuery dosageCuttingProductsQuery = new RuleQuery("Ecart de moins de 5% pour les dosages des produits de coupage", String.format(DOSAGE_CUTTING_PRODUCTS_RULE, sample1Id, sample2Id));
        // TODO : ne tester le dosage des produits de coupage que s'il y ales mêmes produits de coupage.

        // RuleQuery logoQuery = new RuleQuery("Même logos", String.format(LOGO_RULE, sample1Id, sample2Id));
        RuleQuery chilmicalFormQuery = new RuleQuery("Même formes chimiques", String.format(CHIMICAL_FORM_RULE, sample1Id, sample2Id));
        RuleQuery dateQuery = new RuleQuery("Dates de saisie éloignés de moins de 6 mois", String.format(DATE_RULE, sample1Id, sample2Id));

        queries.add(typeQuery);
        queries.add(cuttingProductsQuery);
        queries.add(dosageCuttingProductsQuery);
        // queries.add(logoQuery);
        queries.add(chilmicalFormQuery);
        queries.add(dateQuery);
        
        for (RuleQuery ruleQuery : queries) {
            boolean executionComplete = this.repository.execute(ruleQuery);
    
            if (!executionComplete)
                //TODO : créer exception custome
                throw new Exception("Error while querying : " + ruleQuery.getQuery());
        }

        return queries;
    }
}
