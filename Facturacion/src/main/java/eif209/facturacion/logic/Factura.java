package eif209.facturacion.logic;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Factura {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ID")
    private int id;
    @Basic
    @Column(name = "fecha")
    private Date fecha;
    @Basic
    @Column(name = "total")
    private BigDecimal total;
    @OneToMany(mappedBy = "facturaByFacturaId")
    private Collection<Detallefactura> detallefacturasById;
    @ManyToOne
    @JoinColumn(name = "proveedorID", referencedColumnName = "ID", nullable = false)
    private Proveedor proveedorByProveedorId;
    @ManyToOne
    @JoinColumn(name = "clienteID", referencedColumnName = "ID", nullable = false)
    private Cliente clienteByClienteId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Factura factura = (Factura) o;
        return id == factura.id && Objects.equals(fecha, factura.fecha) && Objects.equals(total, factura.total);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fecha, total);
    }

    public Collection<Detallefactura> getDetallefacturasById() {
        return detallefacturasById;
    }

    public void setDetallefacturasById(Collection<Detallefactura> detallefacturasById) {
        this.detallefacturasById = detallefacturasById;
    }

    public Proveedor getProveedorByProveedorId() {
        return proveedorByProveedorId;
    }

    public void setProveedorByProveedorId(Proveedor proveedorByProveedorId) {
        this.proveedorByProveedorId = proveedorByProveedorId;
    }

    public Cliente getClienteByClienteId() {
        return clienteByClienteId;
    }

    public void setClienteByClienteId(Cliente clienteByClienteId) {
        this.clienteByClienteId = clienteByClienteId;
    }
}
