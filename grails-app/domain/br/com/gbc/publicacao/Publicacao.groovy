package br.com.gbc.publicacao


class Publicacao {
    String titulo
    String conteudo

    Date dateCreated
    Date lastUpdated

    static constraints = {
        titulo blank: false, size: 1..128
        conteudo blank: false, type: 'text'
    }
}
