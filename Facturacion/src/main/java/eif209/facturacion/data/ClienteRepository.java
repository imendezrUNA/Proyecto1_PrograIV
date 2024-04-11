package eif209.facturacion.data;
import eif209.facturacion.logic.Cliente;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Collection;
import java.util.Optional;
@Repository
public interface ClienteRepository extends CrudRepository<Cliente, Long> {
    @Query("select c from Cliente c where c.id = ?1")
    Optional<Cliente> findByClienteId(String clienteId);
    Collection<Cliente> findClienteByProveedorId(Long proveedorId);
    Iterable<Cliente> findAll();


    @Override
    <S extends Cliente> S save(S entity);

    @Override
    Optional<Cliente> findById(Long aLong);
}
