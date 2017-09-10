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
            <g:if test="${flash.message}">
                <div class="message" role="status">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${cadastroCommandInstance}">
                <ul class="errors" role="alert">
                    <g:eachError bean="${cadastroCommandInstance}" var="error">
                    <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                    </g:eachError>
                </ul>
            </g:hasErrors>
            <g:form action="registrar">
                <fieldset class="form">
                    <f:with bean="${cadastroCommandInstance}">
                        <f:field property="nome" />
                        <f:field property="email" />
                        <f:field property="senha">
                            <input type="password" name="senha" required />
                        </f:field>
                        <f:field property="senha2" label="Confirme a senha" >
                            <input type="password" name="senha2" required />
                        </f:field>
                    </f:with>
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
