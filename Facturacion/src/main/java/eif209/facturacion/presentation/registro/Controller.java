package eif209.facturacion.presentation.registro;

import eif209.facturacion.logic.Proveedor;
import eif209.facturacion.logic.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@org.springframework.stereotype.Controller("registro")
public class Controller {
    @Autowired
    private Service service;

    @GetMapping("/presentation/registro/show")
    public String show() {
        return "presentation/registro/View";
    }

    @PostMapping("/presentation/registro/register")
    public String registrarProveedor(Proveedor proveedor, @RequestParam("nombreUsuario") String nombreUsuario,
                                     @RequestParam("contrasena") String contrasena, RedirectAttributes redirectAttributes) {
        boolean registroExitoso = service.registrarProveedorYUsuario(proveedor, nombreUsuario, contrasena);
        if (!registroExitoso) {
            redirectAttributes.addFlashAttribute("error", "El ID del proveedor ya existe.");
            return "redirect:/presentation/registro/show";
        }
        return "redirect:/presentation/login/show";
    }
}
