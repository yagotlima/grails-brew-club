package br.com.gbc.publicacao

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class PublicacaoController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Publicacao.list(params), model:[publicacaoCount: Publicacao.count()]
    }

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
        respond publicacao
    }

    @Transactional
    def update(Publicacao publicacao) {
        if (publicacao == null) {
            transactionStatus.setRollbackOnly()
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
