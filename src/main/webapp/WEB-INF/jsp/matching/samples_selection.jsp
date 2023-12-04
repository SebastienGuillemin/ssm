<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>

<t:template>
    <h1>Choix d'un échantillon</h1>

    <div class="mt-5">
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
    </div>
</t:template>