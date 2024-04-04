package eif209.facturacion.data;


import eif209.facturacion.logic.Producto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ProductoRepository extends CrudRepository<Producto, Long> {
    @Override
    Iterable<Producto> findAll();
    Iterable findProductoById(int id);

    Collection<Producto> findProductoByProveedorId(Long proveedorId);
}
