<%@tag pageEncoding="UTF-8"%>
<%@attribute name="sample" required="true" type="com.sebastienguillemin.stups.ssm.model.Sample" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>

<div class="card p-4">
    <div class="card-body">
        <h2 class="card-title">Echantillon ${sample.id}</h2>
        
        <ul class="list-group">
            <li class="list-group-item" aria-disabled="true">Type de drogue : ${sample.drugType}</li>
            <li class="list-group-item" aria-disabled="true">Forme chimique : ${sample.chimicalForm}</li>
            <li class="list-group-item" aria-disabled="true">Logo :  ${empty sample.logo ? 'Pas de logo' : sample.logo}</li>
            <li class="list-group-item" aria-disabled="true">Principes actifs :
                <ul class="list-group p-1">
                    <c:forEach items="${sample.activePrinciples}" var="activePrinciple">
                        <li class="list-group-item">Nom : <span class="text-primary">${activePrinciple.name}</span>, Dosage : <span class="text-primary">${activePrinciple.dosage}</span></li>
                    </c:forEach>
                </ul>
            </li>
            <li class="list-group-item" aria-disabled="true">Produits de coupage :
                <c:choose>
                    <c:when test="${empty sample.cuttingProducts}">
                        Pas de produits de coupage.
                    </c:when>
                    <c:otherwise>
                        <ul class="list-group p-1">
                            <c:forEach items="${sample.cuttingProducts}" var="cuttingProduct">
                                <li class="list-group-item">Nom : <span class="text-primary">${cuttingProduct.name}</span>, Dosage : <span class="text-primary">${cuttingProduct.dosage}</span></li>
                            </c:forEach>
                        </ul>
                    </c:otherwise>    
                </c:choose>
            </li>
            <li class="list-group-item" aria-disabled="true">Caractéristiques :
                <ul class="list-group p-1">
                    <c:forEach items="${sample.characteristics}" var="entry">
                        <li class="list-group-item">${entry.key} :  ${entry.value}</li>
                    </c:forEach>
                </ul>
            </li>
            <li class="list-group-item" aria-disabled="true">Date : ${sample.dateString}</li>
        </ul>
    </div>
</div>