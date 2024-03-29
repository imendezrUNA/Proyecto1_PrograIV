package eif209.facturacion.presentation.registro;

import eif209.facturacion.logic.Proveedor;
import eif209.facturacion.logic.Service;
import eif209.facturacion.logic.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@org.springframework.stereotype.Controller("registro")
public class Controller {
    @Autowired
    private Service service;

    @GetMapping("/presentation/registro/show")
    public String showRegistroForm() {
        return "presentation/registro/View";
    }

    @PostMapping("/presentation/registro/register")
    public ModelAndView register(
            @RequestParam("id") Long id,
            @RequestParam("nombre") String nombre,
            @RequestParam("correoElectronico") String correoElectronico,
            @RequestParam("numeroTelefono") String numeroTelefono,
            @RequestParam("direccion") String direccion,
            @RequestParam("nombreUsuario") String nombreUsuario,
            @RequestParam("contrasena") String contrasena) {
        if (service.existeUsuario(nombreUsuario)) {
            // Nombre de usuario ya existente, redirige a formulario con mensaje de error
            ModelAndView mav = new ModelAndView("presentation/registro/View");
            mav.addObject("error", "El nombre de usuario ya existe");
            return mav;
        }

        // Crear el usuario
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombreUsuario(nombreUsuario);
        nuevoUsuario.setContrasena(contrasena); // texto plano
        nuevoUsuario.setEstado("activo");
        nuevoUsuario.setRol("proveedor");

        // Crear el proveedor
        Proveedor nuevoProveedor = new Proveedor();
        nuevoProveedor.setId(id);
        nuevoProveedor.setNombre(nombre);
        nuevoProveedor.setCorreoElectronico(correoElectronico);
        nuevoProveedor.setNumeroTelefono(numeroTelefono);
        nuevoProveedor.setDireccion(direccion);

        try {
            service.registrarProveedorYUsuario(nuevoUsuario, nuevoProveedor);
            return new ModelAndView("redirect:/presentation/login/View");
        } catch (Exception e) {
            ModelAndView mav = new ModelAndView("presentation/registro/View");
            mav.addObject("error", "No se pudo registrar. Intente de nuevo.");
            return mav;
        }
    }
}
