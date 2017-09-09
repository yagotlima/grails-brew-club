package br.com.gbc.auth

import grails.gorm.DetachedCriteria
import groovy.transform.ToString

import org.codehaus.groovy.util.HashCodeHelper
import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
@ToString(cache=true, includeNames=true, includePackage=false)
class UsuarioCargo implements Serializable {

	private static final long serialVersionUID = 1

	Usuario usuario
	Cargo cargo

	@Override
	boolean equals(other) {
		if (other instanceof UsuarioCargo) {
			other.usuarioId == usuario?.id && other.cargoId == cargo?.id
		}
	}

    @Override
	int hashCode() {
	    int hashCode = HashCodeHelper.initHash()
        if (usuario) {
            hashCode = HashCodeHelper.updateHash(hashCode, usuario.id)
		}
		if (cargo) {
		    hashCode = HashCodeHelper.updateHash(hashCode, cargo.id)
		}
		hashCode
	}

	static UsuarioCargo get(long usuarioId, long cargoId) {
		criteriaFor(usuarioId, cargoId).get()
	}

	static boolean exists(long usuarioId, long cargoId) {
		criteriaFor(usuarioId, cargoId).count()
	}

	private static DetachedCriteria criteriaFor(long usuarioId, long cargoId) {
		UsuarioCargo.where {
			usuario == Usuario.load(usuarioId) &&
			cargo == Cargo.load(cargoId)
		}
	}

	static UsuarioCargo create(Usuario usuario, Cargo cargo, boolean flush = false) {
		def instance = new UsuarioCargo(usuario: usuario, cargo: cargo)
		instance.save(flush: flush)
		instance
	}

	static boolean remove(Usuario u, Cargo r) {
		if (u != null && r != null) {
			UsuarioCargo.where { usuario == u && cargo == r }.deleteAll()
		}
	}

	static int removeAll(Usuario u) {
		u == null ? 0 : UsuarioCargo.where { usuario == u }.deleteAll() as int
	}

	static int removeAll(Cargo r) {
		r == null ? 0 : UsuarioCargo.where { cargo == r }.deleteAll() as int
	}

	static constraints = {
		cargo validator: { Cargo r, UsuarioCargo ur ->
			if (ur.usuario?.id) {
				UsuarioCargo.withNewSession {
					if (UsuarioCargo.exists(ur.usuario.id, r.id)) {
						return ['userRole.exists']
					}
				}
			}
		}
	}

	static mapping = {
		id composite: ['usuario', 'cargo']
		version false
	}
}
