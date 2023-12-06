<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>

<t:template>
    <h1>Choix des échantillons</h1>
        <section>
            <form id="samplesSelectionForm" action="selectSamples" method="POST">
                <div class="form-floating mb-3">
                    <select class="form-select" name="sample1Id" id="sample1" aria-label="Echantillon 1">
                        <c:forEach items="${samples}" var="sample">
                            <option value=${sample.id}>Echantillon ${sample.id}</option>
                        </c:forEach>
                    </select>
                    <label for="sample1">Echantillon 1</label>
                </div>
                <div class="form-floating mb-3">
                    <select class="form-select" name="sample2Id" id="sample2" aria-label="Echantillon 2">
                        <c:forEach items="${samples}" var="sample">
                            <option value=${sample.id}>Echantillon ${sample.id}</option>
                        </c:forEach>
                    </select>
                    <label for="sample2">Echantillon 2</label>
                </div>
    
                <input class="btn btn-primary" type="submit" value="Tester l'appariement">
            </form>
        </section>

        <section class="mt-5">
            <h2>Comment utilier le vérificateur d'appariements</h2>

            <p>Pour utiliser le vérificateur d'appariements, il suffit de choisir deux échantillons à l'aide des champs de sélection ci-dessus.</p>

            <p>Une fois la sélection faite, vous serez automatiquement redirigé vers une page détaillant les informations de chaque échantillon ainsi que la liste des règles d'appariement. Si une règle n'est pas validée par la paire d'échantillons alors l'appariement sera indiqué comme impossible.</p>

            <p>Si des données sont manquantes pour l'un des deux échantillons alors la règle d'appariement portant sur ces données sera considérée comme invalidée.</p>

            <p>Vous pouvez revenir sur cette page à tout moment en cliquant sur le bouton "Choix des échantillons" en haut de la page.</p>
        </section>
</t:template>