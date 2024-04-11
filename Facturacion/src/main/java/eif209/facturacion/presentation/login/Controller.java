package eif209.facturacion.presentation.login;
import eif209.facturacion.logic.Service;
import eif209.facturacion.logic.Usuario;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
                        @RequestParam("contrasena") String contrasena, HttpSession httpSession) {
        Optional<Usuario> usuarioDBOpt = service.usuarioRead(nombreUsuario);
        if (usuarioDBOpt.isPresent() && contrasena.equals(usuarioDBOpt.get().getContrasena())) {
            Usuario usuarioDB = usuarioDBOpt.get();

            httpSession.setAttribute("usuario", usuarioDB);
            if (Usuario.Rol.PROVEEDOR.equals(usuarioDB.getRol()) && Usuario.Estado.ACTIVO.equals(usuarioDB.getEstado())) {
                return "redirect:/presentation/facturar/show";
            } else if (Usuario.Rol.ADMINISTRADOR.equals(usuarioDB.getRol()) && Usuario.Estado.ACTIVO.equals(usuarioDB.getEstado())) {
                return "redirect:/presentation/proveedores/show";
            }
        }
        return "redirect:/presentation/login/show?error=true";
    }

    @GetMapping("/presentation/login/logout")
    public String logout(HttpSession httpSession) {
        httpSession.invalidate();
        return "redirect:/presentation/login/show";
    }
}
