package eif209.facturacion.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class ProveedorRegistroDTO {
    @NotBlank(message = "El nombre de usuario no puede estar en blanco")
    @Size(max = 50, message = "El nombre de usuario no puede tener más de 50 caracteres")
    private String nombreUsuario;

    @NotBlank(message = "La contraseña no puede estar en blanco")
    @Size(min = 8, max = 20, message = "La contraseña debe tener al menos 8 caracteres y como máximo 20")
    private String contrasena;

    private String idProveedor;

    @NotBlank(message = "El nombre no puede estar en blanco")
    @Size(max = 100, message = "El nombre no puede tener más de 100 caracteres")
    private String nombreProveedor;

    @NotBlank(message = "El correo electrónico no puede estar en blanco")
    @Email(message = "El correo electrónico debe ser válido")
    @Size(max = 255, message = "El correo electrónico no puede tener más de 255 caracteres")
    private String correoElectronico;

    @NotBlank(message = "El número de teléfono no puede estar en blanco")
    @Pattern(regexp = "^(\\+506)?[2-8]\\d{7}$", message = "El número de teléfono debe tener el formato correcto")
    private String numeroTelefono;

    @Size(max = 255, message = "La dirección no puede tener más de 255 caracteres")
    private String direccion;

    public String getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(String idProveedor) {
        this.idProveedor = idProveedor;
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

    public String getNombreProveedor() {
        return nombreProveedor;
    }

    public void setNombreProveedor(String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
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
}
