package eif209.facturacion.logic;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Producto {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ID")
    private int id;
    @Basic
    @Column(name = "nombre")
    private String nombre;
    @Basic
    @Column(name = "descripcion")
    private String descripcion;
    @Basic
    @Column(name = "precio")
    private BigDecimal precio;
    @OneToMany(mappedBy = "productoByProductoId")
    private Collection<Detallefactura> detallefacturasById;
    @ManyToOne
    @JoinColumn(name = "proveedorID", referencedColumnName = "ID", nullable = false)
    private Proveedor proveedorByProveedorId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Producto producto = (Producto) o;
        return id == producto.id && Objects.equals(nombre, producto.nombre) && Objects.equals(descripcion, producto.descripcion) && Objects.equals(precio, producto.precio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, descripcion, precio);
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
}
