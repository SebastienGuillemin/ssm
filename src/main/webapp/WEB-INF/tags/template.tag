<%@tag pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html data-bs-theme="dark">
<head>
    <meta charset='utf-8'>
    <meta http-equiv='X-UA-Compatible' content='IE=edge'>
    <meta name='viewport' content='width=device-width, initial-scale=1'>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
</head>
<body class="d-flex flex-column min-vh-100">
    <header>
        <div class="container">
            <nav class="navbar navbar-dark bg-dark mb-5">
                <ul class="navbar-nav mr-auto mb-2">
                    <li class="nav-item">
                        <a class="nav-link" href="/">Choix des échantillons</a>
                    </li>
                </ul>
            </div>
    
            </nav>
        </div>
    </header>

    <main class="container">
        <jsp:doBody/>
    </main>

    <footer class="mt-auto p-3">
        <div class="container mt-5">
            <div class="row flex-wrap align-items-center">
                <div class="col-1"></div>
                <div class="col-2">
                    <a href="https://lib.u-bourgogne.fr/" target="_blank">
                        <img class="img-fluid rounded" src="<c:url value='/resources/img/logo-lib.jpg' />" alt="Laboratoire d'informatique de Bourgogne">
                    </a>
                </div>
                <div class="col-2"></div>
                <div class="col-2">
                    <a href="https://www.u-bourgogne.fr/" target="_blank">
                        <img class="img-fluid rounded" src="<c:url value='/resources/img/ub.png' />" alt="Laboratoire d'informatique de Bourgogne">
                    </a>
                </div>
                <div class="col-2"></div>
                <div class="col-2">
                    <a href="https://www.police-nationale.interieur.gouv.fr/nous-decouvrir/notre-organisation/organisation/service-national-de-police-scientifique-snps" target="_blank">
                        <img class="img-fluid rounded" src="<c:url value='/resources/img/snps.jpg' />" alt="Laboratoire d'informatique de Bourgogne">
                    </a>
                </div>
                <div class="col-1"></div>
            </div>
        </div>
    </footer>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
</body>
</html>