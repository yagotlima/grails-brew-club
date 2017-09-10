<!--
  To change this license header, choose License Headers in Project Properties.
  To change this template file, choose Tools | Templates
  and open the template in the editor.
-->

<%@ page contentType="text/html;charset=UTF-8" %>

<html>
    <head>
        <meta name="layout" content="main" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cadastro</title>
    </head>
    <body>
        <div class="content">
            <h1>Por favor, preencha o formulário abaixo para se registrar</h1>
            <g:form action="registrar">
                <fieldset class="form">
                    <f:field property="nome">
                        <input type="text" name="nome" value="${flash.chainedParams?.email ?: ''}" />
                    </f:field>
                    <f:field property="email">
                        <input type="text" name="email" value="${flash.chainedParams?.email ?: ''}" />
                    </f:field>
                    <f:field property="senha">
                        <input type="password" name="senha" />
                    </f:field>
                    <f:field property="senha2" label="Repita a senha" >
                        <input type="password" name="senha2" />
                    </f:field>
                </fieldset>
                <fieldset class="buttons">
                    <g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" />
                </fieldset>
            </g:form>
        </div>
        <g:if test="${flash.erro}">
            <g:javascript>
                alert("${flash.erro}");
            </g:javascript>
        </g:if>
    </body>
</html>
