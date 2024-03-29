package eif209.facturacion.logic;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.Objects;

@Entity
public class Usuario {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ID")
    private int id;
    @Basic
    @Column(name = "nombreUsuario")
    private String nombreUsuario;
    @Basic
    @Column(name = "contrasena")
    private String contrasena;
    @Basic
    @Column(name = "estado")
    private Object estado;
    @Basic
    @Column(name = "rol")
    private Object rol;
    @OneToMany(mappedBy = "usuarioByUsuarioId")
    private Collection<Proveedor> proveedorsById;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public Object getEstado() {
        return estado;
    }

    public void setEstado(Object estado) {
        this.estado = estado;
    }

    public Object getRol() {
        return rol;
    }

    public void setRol(Object rol) {
        this.rol = rol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return id == usuario.id && Objects.equals(nombreUsuario, usuario.nombreUsuario) && Objects.equals(contrasena, usuario.contrasena) && Objects.equals(estado, usuario.estado) && Objects.equals(rol, usuario.rol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombreUsuario, contrasena, estado, rol);
    }

    public Collection<Proveedor> getProveedorsById() {
        return proveedorsById;
    }

    public void setProveedorsById(Collection<Proveedor> proveedorsById) {
        this.proveedorsById = proveedorsById;
    }
}
