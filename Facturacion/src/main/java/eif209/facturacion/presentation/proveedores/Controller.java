package eif209.facturacion.presentation.proveedores;

import eif209.facturacion.logic.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@org.springframework.stereotype.Controller("proveedores")
public class Controller {
    @Autowired
    private Service service;

    @GetMapping("/presentation/proveedores/show")
    public String show(Model model) {
        model.addAttribute("proveedores", service.proveedorFindAll());
        return "/presentation/proveedores/View";
    }
}
