package grails.brew.club

import br.com.gbc.auth.Cargo

class BootStrap {

    def init = { servletContext ->
        // Cargos do sistema:
        [
            'ROLE_ADMIN',
            'ROLE_USER'
        ].each {
            if(!Cargo.findByAuthority(it))
                new Cargo(authority: it).save(flush: true, failOnError: true)
        }
    }

    def destroy = {
    }
}
