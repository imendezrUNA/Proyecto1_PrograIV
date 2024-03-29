package eif209.facturacion.presentation.home;

import org.springframework.web.bind.annotation.GetMapping;

@org.springframework.stereotype.Controller("Home")
public class Controller {
    @GetMapping("/")
    public String redirectLogin() {
        return "redirect:/presentation/login/show";
    }
}
