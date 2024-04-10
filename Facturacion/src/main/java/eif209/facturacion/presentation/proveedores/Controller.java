package eif209.facturacion.presentation.proveedores;

import eif209.facturacion.logic.Service;
import eif209.facturacion.logic.Usuario;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@org.springframework.stereotype.Controller("proveedores")
public class Controller {
    @Autowired
    private Service service;

    @GetMapping("/presentation/proveedores/show")
    public String show(Model model, HttpSession session) {
        session.setAttribute("ordenEstado", "default");
        return listarProveedores(null, model, session);
    }

    @GetMapping("/proveedores/listar")
    public String listarProveedores(@RequestParam(name = "ordenarPor", required = false) String ordenarPor, Model model, HttpSession session) {
        String ordenEstado = (String) session.getAttribute("ordenEstado");
        if ("estado".equals(ordenarPor)) {
            if ("asc".equals(ordenEstado)) {
                model.addAttribute("proveedores", service.proveedorFindAllSortedByEstadoDesc());
                session.setAttribute("ordenEstado", "desc");
            } else if ("desc".equals(ordenEstado)) {
                model.addAttribute("proveedores", service.proveedorFindAll());
                session.setAttribute("ordenEstado", "default");
            } else {
                model.addAttribute("proveedores", service.proveedorFindAllSortedByEstadoAsc());
                session.setAttribute("ordenEstado", "asc");
            }
        } else {
            model.addAttribute("proveedores", service.proveedorFindAll());
        }
        return "/presentation/proveedores/View";
    }

    @GetMapping("/proveedores/activar/{id}")
    public String activarProveedor(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        if (service.cambiarEstadoProveedor(id, Usuario.Estado.ACTIVO)) {
            redirectAttributes.addFlashAttribute("mensaje", "Proveedor activado exitosamente.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Error al activar el proveedor.");
        }
        return "redirect:/presentation/proveedores/show";
    }

    @GetMapping("/proveedores/desactivar/{id}")
    public String desactivarProveedor(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        if (service.cambiarEstadoProveedor(id, Usuario.Estado.INACTIVO)) {
            redirectAttributes.addFlashAttribute("mensaje", "Proveedor desactivado exitosamente.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Error al desactivar el proveedor.");
        }
        return "redirect:/presentation/proveedores/show";
    }
}
