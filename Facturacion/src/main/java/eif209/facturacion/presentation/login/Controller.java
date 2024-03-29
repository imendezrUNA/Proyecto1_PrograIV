package eif209.facturacion.presentation.login;

import eif209.facturacion.logic.Service;
import eif209.facturacion.logic.Usuario;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@org.springframework.stereotype.Controller("login")
public class Controller {
    @Autowired
    private Service service;

    /*@PostMapping("/presentation/login/login")
    public String login(Usuario usuario, HttpSession httpSession) {
        try {
            Optional<Usuario> usuarioDB = service.usuarioRead(usuario.getId());
            httpSession.setAttribute("usuario", usuarioDB);
            httpSession.setAttribute("proveedor", service.proveedorRead(//buscar al proveedor cuyo usuarioID sea el mismo que el de usuarioDB);
            switch (usuarioDB.getRol()) {
                case "proveedor":
                    return "redirect:/presentation/facturar/show";
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }*/
}
