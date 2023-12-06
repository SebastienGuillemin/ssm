<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>

<t:template>
    <h1>Résultats appariement</h1>

    <p>Test de l'appariement des échantillons suivants</p>

    <div id="sampleCards" class="row mb-5">
        <div class="col p-1">
            <t:sampleCard sample="${sample1}"/>
        </div>

        <div class="col p-1">
            <t:sampleCard sample="${sample2}"/>
        </div>
    </div>

    <h2>Résultats des différentes règles</h2>
    <div class="row">
        <div class="col-6">
            <ol class="list-group list-group-numbered">
                <c:forEach items="${queries}" var="query">
                    <c:set var="color" value="${query.passed ? 'text-success' : 'text-danger'}"/>
                    <c:set var="label" value="${query.passed ? 'Validée' : 'Non validée'}" />
                    <li class="list-group-item ${color}">
                        ${query.name} : ${label}
                    </li>
                </c:forEach>
            </ol>
        </div>
        <div class="col-6">
            <c:choose>
            <c:when test="${match}">
                <p class="text-center text-success fs-4">Les deux échantillons peuvent être appariés.</p>
            </c:when>    
            <c:otherwise>
                <p class="text-center text-danger fs-4">Les deux échantillons ne peuvent pas être appariés.</p>
            </c:otherwise>
        </c:choose>
        </div>
    </div>
</t:template>