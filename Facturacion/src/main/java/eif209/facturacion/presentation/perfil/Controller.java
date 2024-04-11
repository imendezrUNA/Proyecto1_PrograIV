package eif209.facturacion.presentation.perfil;

import eif209.facturacion.logic.Proveedor;
import eif209.facturacion.logic.Service;
import eif209.facturacion.logic.Usuario;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@org.springframework.stereotype.Controller("perfil")
public class Controller {
    @Autowired
    private Service service;

    @GetMapping("/presentation/perfil/show")
    public String show(Model model, HttpSession session) {
        Usuario usuarioSesion = (Usuario) session.getAttribute("usuario");
        if (usuarioSesion != null) {
            int usuarioId = usuarioSesion.getId();
            Optional<Proveedor> proveedorOpt = service.proveedorRead(usuarioId);
            if (proveedorOpt.isPresent()) {
                model.addAttribute("proveedor", proveedorOpt.get());
            } else {
                model.addAttribute("error", "Proveedor no encontrado.");
            }
        } else {
            return "redirect:/presentation/login/show";
        }
        return "presentation/perfil/View";
    }

    @PostMapping("/presentation/perfil/configurar")
    public String configurarPerfil(@Valid @ModelAttribute("proveedor") Proveedor proveedor, BindingResult result, HttpSession session, RedirectAttributes redirectAttributes, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("org.springframework.validation.BindingResult.proveedor", result);
            model.addAttribute("proveedor", proveedor);
            return "presentation/perfil/View";
        }

        Usuario usuarioSesion = (Usuario) session.getAttribute("usuario");
        if (usuarioSesion != null) {
            proveedor.setUsuarioByUsuarioId(usuarioSesion);
            boolean actualizado = service.actualizarProveedor(proveedor);
            if (actualizado) {
                redirectAttributes.addFlashAttribute("mensaje", "Perfil actualizado correctamente.");
            } else {
                redirectAttributes.addFlashAttribute("error", "No se pudo actualizar el perfil.");
            }
        } else {
            return "redirect:/presentation/login/show";
        }

        return "redirect:/presentation/perfil/show";
    }
}
