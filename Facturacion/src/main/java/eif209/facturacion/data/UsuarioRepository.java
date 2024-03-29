package eif209.facturacion.data;

import eif209.facturacion.logic.Usuario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Integer> {
    Usuario findByNombreUsuario(String nombreUsuario);
}
