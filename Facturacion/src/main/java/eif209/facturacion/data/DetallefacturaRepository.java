package eif209.facturacion.data;

import eif209.facturacion.logic.Detallefactura;
import eif209.facturacion.logic.Factura;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetallefacturaRepository extends CrudRepository<Detallefactura, Integer> {

}
