package br.com.gbc.auth

import grails.plugin.springsecurity.annotation.Secured
import grails.validation.Validateable

@Secured('IS_AUTHENTICATED_ANONYMOUSLY')
class CadastroController {

    def index() {
        def command = flash.chainedCommand ?: new CadastroCommand()
        [cadastroCommandInstance: command]
    }

    def registrar(CadastroCommand command) {
        if(!command || command.hasErrors()) {
            flash.message = "Falha ao cadastrar usuário."
            flash.chainedCommand = command
            redirect action: 'index'
            return
        }

        def cargo = Cargo.findByAuthority('ROLE_USER')
        def usuario
        Usuario.withTransaction {
            usuario = command.usuario.save(failOnError: true)
            UsuarioCargo.create(usuario, cargo, true)
        }

        render view: 'concluido', model: [usuario: usuario]
    }
}

class CadastroCommand implements grails.validation.Validateable {
    String nome
    String email
    String senha
    String senha2

    static constraints = {
        nome nullable: false, blank: false, size: 1..255
        email nullable: false, blank: false, email: true, size: 1..255
        senha nullable: false, blank: false, size: 6..255
        senha2 nullable: false, blank: false, validator: { val, obj ->
            if(val != obj.senha)
                'confirmacaoSenhaDiverge'
        }
    }

    Usuario getUsuario() {
        new Usuario(
            nome: nome,
            username: email,
            password: senha
        )
    }
}

