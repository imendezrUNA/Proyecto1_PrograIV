package eif209.facturacion.presentation.about;

import org.springframework.web.bind.annotation.GetMapping;

@org.springframework.stereotype.Controller("about")
public class Controller {
    @GetMapping("/presentation/about/show")
    public String show() {
        return "presentation/about/View";
    }
}
