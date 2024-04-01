package eif209.facturacion.presentation.registro;

import eif209.facturacion.dto.ProveedorRegistroDTO;
import eif209.facturacion.logic.Service;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@org.springframework.stereotype.Controller("registro")
public class Controller {
    @Autowired
    private Service service;

    @GetMapping("/presentation/registro/show")
    public String show(Model model) {
        if (!model.containsAttribute("registroDTO")) {
            model.addAttribute("registroDTO", new ProveedorRegistroDTO());
        }
        return "presentation/registro/View";
    }

    @PostMapping("/presentation/registro/register")
    public String registrarProveedor(@Valid @ModelAttribute("registroDTO") ProveedorRegistroDTO registroDTO,
                                     BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.registroDTO", result);
            redirectAttributes.addFlashAttribute("registroDTO", registroDTO);
            return "redirect:/presentation/registro/show";
        }

        boolean registroExitoso = service.registrarProveedorYUsuario(registroDTO);
        if (!registroExitoso) {
            redirectAttributes.addFlashAttribute("error", "Ya existe un proveedor registrado con la misma cédula o nombre de usuario.");
            return "redirect:/presentation/registro/show";
        }

        return "redirect:/presentation/login/show";
    }
}
