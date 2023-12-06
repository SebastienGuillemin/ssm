<%@ page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>

<t:template>
    <h1>Résultats appariement</h1>

    <p>Test de l'appariement des échantillons suivants</p>

    <div id="sampleCards" class="row mb-5">
        <div class="col p-1">
            <div class="card p-4">
                <div class="card-body">
                    <h2 class="card-title">Echantillon ${sample1.id}</h2>
                    
                    <ul class="list-group">
                        <li class="list-group-item" aria-disabled="true">Type de drogue : ${sample1.drugType}</li>
                        <li class="list-group-item" aria-disabled="true">Forme chimique : ${sample1.chimicalForm}</li>
                        <li class="list-group-item" aria-disabled="true">Logo :  ${empty sample1.logo ? 'Pas de logo' : sample1.logo}</li>
                        <li class="list-group-item" aria-disabled="true">Principes actifs :
                            <ul class="list-group p-1">
                                <c:forEach items="${sample1.activePrinciples}" var="activePrinciple">
                                    <li class="list-group-item">Nom : <span class="text-primary">${activePrinciple.name}</span>, Dosage : <span class="text-primary">${activePrinciple.dosage}</span></li>
                                </c:forEach>
                            </ul>
                        </li>
                        <li class="list-group-item" aria-disabled="true">Produits de coupage :
                            <c:choose>
                                <c:when test="${empty sample1.cuttingProducts}">
                                    Pas de produits de coupage.
                                </c:when>
                                <c:otherwise>
                                    <ul class="list-group p-1">
                                        <c:forEach items="${sample1.cuttingProducts}" var="cuttingProduct">
                                            <li class="list-group-item">Nom : <span class="text-primary">${cuttingProduct.name}</span>, Dosage : <span class="text-primary">${cuttingProduct.dosage}</span></li>
                                        </c:forEach>
                                    </ul>
                                </c:otherwise>    
                            </c:choose>
                        </li>
                        <li class="list-group-item" aria-disabled="true">Caractéristiques :
                            <ul class="list-group p-1">
                                <c:forEach items="${sample1.characteristics}" var="entry">
                                    <li class="list-group-item">${entry.key} :  ${entry.value}</li>
                                </c:forEach>
                            </ul>
                        </li>
                        <li class="list-group-item" aria-disabled="true">Date : ${sample1.dateString}</li>
                    </ul>
                </div>
            </div>
        </div>

        <div class="col p-1">
            <div class="card p-4">
                <div class="card-body">
                    <h2 class="card-title">Echantillon ${sample2.id}</h2>
                    
                    <ul class="list-group">
                        <li class="list-group-item" aria-disabled="true">Type de drogue : ${sample2.drugType}</li>
                        <li class="list-group-item" aria-disabled="true">Forme chimique : ${sample2.chimicalForm}</li>
                        <li class="list-group-item" aria-disabled="true">Logo : ${empty sample2.logo ? 'Pas de logo' : sample2.logo}</li>
                        <li class="list-group-item" aria-disabled="true">Principes actifs :
                            <ul class="list-group p-1">
                                <c:forEach items="${sample2.activePrinciples}" var="activePrinciple">
                                    <li class="list-group-item">Nom : <span class="text-primary">${activePrinciple.name}</span>, Dosage : <span class="text-primary">${activePrinciple.dosage}</span></li>
                                </c:forEach>
                            </ul>
                        </li>
                        <li class="list-group-item" aria-disabled="true">Produits de coupage :
                            <c:choose>
                                <c:when test="${empty sample2.cuttingProducts}">
                                    Pas de produits de coupage.
                                </c:when>
                                <c:otherwise>
                                    <ul class="list-group p-1">
                                        <c:forEach items="${sample2.cuttingProducts}" var="cuttingProduct">
                                            <li class="list-group-item">Nom : <span class="text-primary">${cuttingProduct.name}</span>, Dosage : <span class="text-primary">${cuttingProduct.dosage}</span></li>
                                        </c:forEach>
                                    </ul>
                                </c:otherwise>    
                            </c:choose>
                        </li>
                        <li class="list-group-item" aria-disabled="true">Caractéristiques :
                            <ul class="list-group p-1">
                                <c:forEach items="${sample2.characteristics}" var="entry">
                                    <li class="list-group-item">${entry.key} :  ${entry.value}</li>
                                </c:forEach>
                            </ul>
                        </li>
                        <li class="list-group-item" aria-disabled="true">Date : ${sample2.dateString}</li>
                    </ul>
                </div>
            </div>
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