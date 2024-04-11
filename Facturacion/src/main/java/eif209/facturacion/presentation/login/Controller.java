package eif209.facturacion.presentation.login;

import eif209.facturacion.logic.Service;
import eif209.facturacion.logic.Usuario;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@org.springframework.stereotype.Controller("login")
public class Controller {
    @Autowired
    private Service service;

    @GetMapping("/presentation/login/show")
    public String show() {
        return "presentation/login/View";
    }

    @PostMapping("/presentation/login/login")
    public String login(@RequestParam("nombreUsuario") String nombreUsuario,
                        @RequestParam("contrasena") String contrasena, HttpSession httpSession, RedirectAttributes redirectAttributes) {
        Optional<Usuario> usuarioDBOpt = service.usuarioRead(nombreUsuario);
        if (usuarioDBOpt.isPresent()) {
            Usuario usuarioDB = usuarioDBOpt.get();
            if (contrasena.equals(usuarioDB.getContrasena())) {
                if (Usuario.Estado.ACTIVO.equals(usuarioDB.getEstado())) {
                    httpSession.setAttribute("usuario", usuarioDB);
                    if (Usuario.Rol.PROVEEDOR.equals(usuarioDB.getRol())) {
                        return "redirect:/presentation/facturar/show";
                    } else if (Usuario.Rol.ADMINISTRADOR.equals(usuarioDB.getRol())) {
                        return "redirect:/presentation/proveedores/show";
                    }
                } else {
                    redirectAttributes.addFlashAttribute("error", "Su cuenta no está activa. Por favor, contacte al administrador.");
                    return "redirect:/presentation/login/show";
                }
            }
        }
        redirectAttributes.addFlashAttribute("error", "Nombre de usuario o contraseña incorrectos.");
        return "redirect:/presentation/login/show";
    }

    @GetMapping("/presentation/login/logout")
    public String logout(HttpSession httpSession) {
        httpSession.invalidate();
        return "redirect:/presentation/login/show";
    }
}
