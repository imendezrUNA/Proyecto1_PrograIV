package eif209.facturacion.presentation.facturar;

import eif209.facturacion.logic.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

@org.springframework.stereotype.Controller("facturar")
public class Controller {
    /*@Autowired
    private Service service;*/

    @GetMapping("/presentation/facturar/show")
    public String show() {
        return "presentation/facturar/View";
    }
}
