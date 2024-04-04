package eif209.facturacion.presentation.productos;

import eif209.facturacion.logic.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@org.springframework.stereotype.Controller("productos")
@SessionAttributes({"productos"})
public class Controller {
        @Autowired
        private Service service;

        @ModelAttribute("productos")
        public Iterable<Producto> productos() {
            return new ArrayList<Producto>();
    }

    @GetMapping("/presentation/productos/show")
    public String show(Model model, HttpSession httpSession) {
        Usuario usuario = (Usuario) httpSession.getAttribute("usuario");
        // Cargar los productos en el modelo
        model.addAttribute("productos",service.findProductByProveId((long) usuario.getProveedor().getId()));
        // Devolver la vista correspondiente
        return"presentation/productos/View";
    }

    @PostMapping("/presentation/productos/add")
    public String search(
        @ModelAttribute
        ("productosSearch") Producto productoSearch, HttpSession httpSession, Model model) {
        Usuario usuario = (Usuario) httpSession.getAttribute("usuario");
        // Verificar si el usuario de la sesión coincide con el usuario recibido en la URL
        model.addAttribute("productos", service.findProductByProveId((long) usuario.getProveedor().getId()));
        model.addAttribute("productoNuevo", new Producto());
        return "presentation/productos/View";
        }
}