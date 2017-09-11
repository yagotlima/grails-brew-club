package br.com.gbc.publicacao

class PublicacaoTagLib {
    static defaultEncodeAs = [taglib: 'html']
    static encodeAsForTags = [withPublicacoesRecentes: [taglib:'none']]

    def withPublicacoesRecentes = { attrs, body ->
        def max = attrs.int('max') ?: 10
        def criteria = Publicacao.createCriteria()
        def publicacoes = criteria.list([max: max]) {
            order('lastUpdated', 'desc')
        }

        publicacoes.each {
            out << body([it: it])
        }
    }
}
