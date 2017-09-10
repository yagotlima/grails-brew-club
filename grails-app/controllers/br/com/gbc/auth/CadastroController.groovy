package br.com.gbc.auth

import grails.plugin.springsecurity.annotation.Secured
import grails.validation.Validateable

@Secured('IS_AUTHENTICATED_ANONYMOUSLY')
class CadastroController {

    def index() {
    }

    def registrar() {
        def nome = params.nome
        def email = params.email
        def senha = params.senha
        def senha2 = params.senha2

        if(senha != senha2) {
            flash.erro = "Senha não bate com a confirmação."
            flash.chainedParams = [email: email]
            redirect action: 'index'
        }

        def cargo = Cargo.findByAuthority('ROLE_USER')
        def usuario
        Usuario.withTransaction {
            usuario = new Usuario(nome: nome, username: email, password: senha)
                    .save(failOnError: true)
            UsuarioCargo.create(usuario, cargo, true)
        }

        render view: 'concluido', model: [usuario: usuario]
    }
}

