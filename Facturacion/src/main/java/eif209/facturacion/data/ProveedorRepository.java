package eif209.facturacion.data;

import eif209.facturacion.logic.Proveedor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProveedorRepository extends CrudRepository<Proveedor, Long> {
    Optional<Proveedor> findByUsuarioByUsuarioId_Id(int usuarioId);
}
