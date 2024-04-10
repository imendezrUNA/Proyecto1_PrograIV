package eif209.facturacion.logic;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import org.hibernate.Hibernate;

import java.util.Objects;

@Embeddable
public class ProveedorclienteId implements java.io.Serializable {
    private static final long serialVersionUID = -3775554634851323056L;
    @NotNull(message = "El ID del proveedor no puede ser nulo")
    @Column(name = "proveedorID", nullable = false)
    private Long proveedorID;

    @NotNull(message = "El ID del cliente no puede ser nulo")
    @Column(name = "clienteID", nullable = false)
    private Long clienteID;

    public Long getProveedorID() {
        return proveedorID;
    }

    public void setProveedorID(Long proveedorID) {
        this.proveedorID = proveedorID;
    }

    public Long getClienteID() {
        return clienteID;
    }

    public void setClienteID(Long clienteID) {
        this.clienteID = clienteID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ProveedorclienteId entity = (ProveedorclienteId) o;
        return Objects.equals(this.proveedorID, entity.proveedorID) &&
                Objects.equals(this.clienteID, entity.clienteID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(proveedorID, clienteID);
    }

}