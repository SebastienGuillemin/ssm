<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>

<t:template>
    <h1>Choix d'un échantillon</h1>

    <div class="px-5 my-5">
        <form id="samplesSelectionForm">
            <div class="form-floating mb-3">
                <select class="form-select" id="echantillon1" aria-label="Echantillon 1">
                    <c:forEach items="${samples}" var="sample">
                        <option value="${sample}">${sample}</option>
                    </c:forEach>
                </select>
                <label for="echantillon1">Echantillon 1</label>
            </div>
            <div class="form-floating mb-3">
                <select class="form-select" id="echantillon2" aria-label="Echantillon 2">
                    <c:forEach items="${samples}" var="sample">
                        <option value="${sample}">${sample}</option>
                    </c:forEach>
                </select>
                <label for="echantillon2">Echantillon 2</label>
            </div>
            <div class="d-none" id="submitSuccessMessage">
                <div class="text-center mb-3">
                    <div class="fw-bolder">Form submission successful!</div>
                    <p>To activate this form, sign up at</p>
                    <a href="https://startbootstrap.com/solution/contact-forms">https://startbootstrap.com/solution/contact-forms</a>
                </div>
            </div>
            <div class="d-none" id="submitErrorMessage">
                <div class="text-center text-danger mb-3">Error sending message!</div>
            </div>
            <div class="d-grid">
                <button class="btn btn-primary btn-lg disabled" id="submitButton" type="submit">Submit</button>
            </div>
        </form>
    </div>
</t:template>