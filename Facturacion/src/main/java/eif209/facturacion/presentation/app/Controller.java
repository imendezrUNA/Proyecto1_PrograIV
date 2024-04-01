package eif209.facturacion.presentation.app;

import org.springframework.web.bind.annotation.GetMapping;

@org.springframework.stereotype.Controller("App")
public class Controller {
    @GetMapping("/")
    public String redirectLogin() {
        return "redirect:/presentation/login/show";
    }
}
