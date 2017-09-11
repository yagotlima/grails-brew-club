package br.com.gbc.publicacao

import br.com.gbc.auth.Usuario

class Publicacao {
    static belongsTo = [autor: Usuario]

    String titulo
    String conteudo

    Date dateCreated
    Date lastUpdated

    static constraints = {
        titulo blank: false, size: 1..128
        conteudo blank: false, type: 'text'
    }
}
