package br.com.gbc.publicacao

import static org.springframework.http.HttpStatus.*
import grails.plugin.springsecurity.annotation.Secured
import grails.transaction.Transactional

@Secured(['ROLE_ADMIN', 'ROLE_USER'])
@Transactional(readOnly = true)
class PublicacaoController {
    def springSecurityService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        def criteria = Publicacao.createCriteria()
        def publicacoes = criteria.list(params) {
            eq('autor', springSecurityService.currentUser)
        }
        respond publicacoes, model:[publicacaoCount: publicacoes.totalCount]
    }

    @Secured('IS_AUTHENTICATED_ANONYMOUSLY')
    def show(Publicacao publicacao) {
        respond publicacao
    }

    def create() {
        respond new Publicacao(params)
    }

    @Transactional
    def save(Publicacao publicacao) {
        if (publicacao == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        publicacao.clearErrors()
        publicacao.autor = springSecurityService.currentUser
        publicacao.validate()

        if (publicacao.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond publicacao.errors, view:'create'
            return
        }

        publicacao.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'publicacao.label', default: 'Publicacao'), publicacao.id])
                redirect publicacao
            }
            '*' { respond publicacao, [status: CREATED] }
        }
    }

    def edit(Publicacao publicacao) {
        if(publicacao.autor != springSecurityService.currentUser) {
            notFound()
            return
        }

        respond publicacao
    }

    @Transactional
    def update(Publicacao publicacao) {
        if (publicacao == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if(publicacao.autor != springSecurityService.currentUser) {
            notFound()
            return
        }

        if (publicacao.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond publicacao.errors, view:'edit'
            return
        }

        publicacao.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'publicacao.label', default: 'Publicacao'), publicacao.id])
                redirect publicacao
            }
            '*'{ respond publicacao, [status: OK] }
        }
    }

    @Transactional
    def delete(Publicacao publicacao) {

        if (publicacao == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if(publicacao.autor != springSecurityService.currentUser) {
            notFound()
            return
        }

        publicacao.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'publicacao.label', default: 'Publicacao'), publicacao.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'publicacao.label', default: 'Publicacao'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
