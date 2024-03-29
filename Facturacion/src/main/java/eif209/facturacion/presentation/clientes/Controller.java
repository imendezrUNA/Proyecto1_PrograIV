package eif209.facturacion.presentation.clientes;

import eif209.facturacion.logic.Cliente;
import eif209.facturacion.logic.Proveedor;
import eif209.facturacion.logic.Service;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.ArrayList;

@org.springframework.stereotype.Controller("clientes")
@SessionAttributes({"clientes","clienteSearch","clienteEdit","proveedor"})
public class Controller {
    @Autowired private Service service;
    @ModelAttribute("clientes") public Iterable<Cliente> clientes() { return new ArrayList<Cliente>(); }
    @ModelAttribute("clienteSearch") public Cliente clienteSearch() { return new Cliente(); }
    @ModelAttribute("clienteEdit") public Cliente clienteEdit() { return new Cliente(); }
    @ModelAttribute("proveedor") public Proveedor proveedor() { return new Proveedor(); }

    @GetMapping("/presentation/clientes/show")
    public String show(@ModelAttribute("proveedor") Proveedor proveedor, HttpSession httpSession, Model model) {
        if (httpSession.getAttribute("clientes") == null) {
            model.addAttribute("clientes", service.clienteReadAll(proveedor));
        }
        return "presentation/clientes/View";
    }
    @PostMapping("/presentation/clientes/search")
    public String search(
            @ModelAttribute("clienteSearch") Cliente clienteSearch,
            @ModelAttribute(name="proveedor", binding=false) Proveedor proveedor,
            Model model) {
        model.addAttribute("clientes", service.clienteSearch (proveedor, clienteSearch.getNombre()));
        model.addAttribute("clienteEdit", new Cliente());
        return "presentation/clientes/View";
    }
}
