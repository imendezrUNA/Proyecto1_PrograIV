package eif209.facturacion.logic;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
public class Detallefactura {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ID")
    private int id;
    @Basic
    @Column(name = "cantidad")
    private int cantidad;
    @Basic
    @Column(name = "precioUnitario")
    private BigDecimal precioUnitario;
    @Basic
    @Column(name = "subtotal")
    private BigDecimal subtotal;
    @ManyToOne
    @JoinColumn(name = "facturaID", referencedColumnName = "ID", nullable = false)
    private Factura facturaByFacturaId;
    @ManyToOne
    @JoinColumn(name = "productoID", referencedColumnName = "ID", nullable = false)
    private Producto productoByProductoId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Detallefactura that = (Detallefactura) o;
        return id == that.id && cantidad == that.cantidad && Objects.equals(precioUnitario, that.precioUnitario) && Objects.equals(subtotal, that.subtotal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cantidad, precioUnitario, subtotal);
    }

    public Factura getFacturaByFacturaId() {
        return facturaByFacturaId;
    }

    public void setFacturaByFacturaId(Factura facturaByFacturaId) {
        this.facturaByFacturaId = facturaByFacturaId;
    }

    public Producto getProductoByProductoId() {
        return productoByProductoId;
    }

    public void setProductoByProductoId(Producto productoByProductoId) {
        this.productoByProductoId = productoByProductoId;
    }
}
