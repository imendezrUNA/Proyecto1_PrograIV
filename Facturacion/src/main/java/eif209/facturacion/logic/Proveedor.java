package eif209.facturacion.logic;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.Objects;

@Entity
public class Proveedor {
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ID")
    private long id;
    @Basic
    @Column(name = "nombre")
    private String nombre;
    @Basic
    @Column(name = "correoElectronico")
    private String correoElectronico;
    @Basic
    @Column(name = "numeroTelefono")
    private String numeroTelefono;
    @Basic
    @Column(name = "direccion")
    private String direccion;
    @OneToMany(mappedBy = "proveedorByProveedorId")
    private Collection<Factura> facturasById;
    @OneToMany(mappedBy = "proveedorByProveedorId")
    private Collection<Producto> productosById;
    @OneToOne
    @JoinColumn(name = "usuarioID", referencedColumnName = "ID", nullable = false)
    private Usuario usuarioByUsuarioId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getNumeroTelefono() {
        return numeroTelefono;
    }

    public void setNumeroTelefono(String numeroTelefono) {
        this.numeroTelefono = numeroTelefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Proveedor proveedor = (Proveedor) o;
        return id == proveedor.id && Objects.equals(nombre, proveedor.nombre) && Objects.equals(correoElectronico, proveedor.correoElectronico) && Objects.equals(numeroTelefono, proveedor.numeroTelefono) && Objects.equals(direccion, proveedor.direccion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, correoElectronico, numeroTelefono, direccion);
    }

    public Collection<Factura> getFacturasById() {
        return facturasById;
    }

    public void setFacturasById(Collection<Factura> facturasById) {
        this.facturasById = facturasById;
    }

    public Collection<Producto> getProductosById() {
        return productosById;
    }

    public void setProductosById(Collection<Producto> productosById) {
        this.productosById = productosById;
    }

    public Usuario getUsuarioByUsuarioId() {
        return usuarioByUsuarioId;
    }

    public void setUsuarioByUsuarioId(Usuario usuarioByUsuarioId) {
        this.usuarioByUsuarioId = usuarioByUsuarioId;
    }
}
