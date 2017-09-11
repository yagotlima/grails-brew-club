package br.com.gbc.auth

import br.com.gbc.publicacao.Publicacao
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
@EqualsAndHashCode(includes='username')
@ToString(includes='username', includeNames=true, includePackage=false)
class Usuario implements Serializable {

	private static final long serialVersionUID = 1

	String username
	String password

	String nome
    
	boolean enabled = true
	boolean accountExpired
	boolean accountLocked
	boolean passwordExpired

	static hasMany = [publicacoes: Publicacao]

	Set<Cargo> getAuthorities() {
		(UsuarioCargo.findAllByUsuario(this) as List<UsuarioCargo>)*.cargo as Set<Cargo>
	}

	static constraints = {
		nome nullable: false, blank: false
		password blank: false, password: true
		username blank: false, unique: true
	}

	static mapping = {
		password column: '`password`'
	}
}
