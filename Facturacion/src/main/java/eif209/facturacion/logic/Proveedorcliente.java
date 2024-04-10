package eif209.facturacion.logic;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "proveedorcliente")
public class Proveedorcliente {
    @EmbeddedId
    private ProveedorclienteId id;

    @MapsId("proveedorID")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "proveedorID", nullable = false)
    @NotNull(message = "El proveedor no puede ser nulo")
    private Proveedor proveedorID;

    @MapsId("clienteID")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "clienteID", nullable = false)
    @NotNull(message = "El cliente no puede ser nulo")
    private Cliente clienteID;

    public ProveedorclienteId getId() {
        return id;
    }

    public void setId(ProveedorclienteId id) {
        this.id = id;
    }

    public Proveedor getProveedorID() {
        return proveedorID;
    }

    public void setProveedorID(Proveedor proveedorID) {
        this.proveedorID = proveedorID;
    }

    public Cliente getClienteID() {
        return clienteID;
    }

    public void setClienteID(Cliente clienteID) {
        this.clienteID = clienteID;
    }

}