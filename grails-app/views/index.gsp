<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Home</title>

    <asset:link rel="icon" href="favicon.ico" type="image/x-ico" />
</head>
<body>
    <content tag="nav">
        <sec:ifNotLoggedIn>
            <li><g:link controller="cadastro">Cadastre-se</g:link></li>
            <li><g:link controller="login">Fazer Login</g:link></li>
        </sec:ifNotLoggedIn>
        <sec:ifLoggedIn>
            <li>
                <a href="javascript:logoutForm.submit()" >Logout</a>
            </li>
        </sec:ifLoggedIn>
        <li>
            <g:link controller="publicacao">Suas publicacoes</g:link>
        </li>
        <g:form name="logoutForm" controller="logout" method="post"></g:form>
    </content>

    <div class="svg" role="presentation">
        <div class="grails-logo-container">
            <asset:image src="grails-cupsonly-logo-white.svg" class="grails-logo"/>
        </div>
    </div>

    <div id="content" role="main">
        <section class="row colset-2-its">
            <h1>Grails Brew Club</h1>

            <p>
                Bem vindo ao Grails Brew Club. Confira nossas publicações recentes abaixo:
            </p>

            <div id="controllers" role="navigation">
                <h2>Publicações Recentes:</h2>
                <ul>
                    <!--
                    <g:each var="c" in="${grailsApplication.controllerClasses.sort { it.fullName } }">
                        <li class="controller">
                            <g:link controller="${c.logicalPropertyName}">${c.fullName}</g:link>
                        </li>
                    </g:each>
                    -->
                    <g:withPublicacoesRecentes>
                        <li>
                            <g:link controller="publicacao" action="show" id="${it.id}">
                                ${it.titulo}
                            </g:link>
                        </li>
                    </g:withPublicacoesRecentes>
                </ul>
            </div>
        </section>
    </div>

</body>
</html>
