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
            @ModelAttribute("productosGuardar") Producto productoGuardar, HttpSession httpSession,
            Model model) {

        Usuario usuario = (Usuario) httpSession.getAttribute("usuario");
        //le asigno al producto que se va a guardar el proveedor que lo extraigo dsde el htmlsession
        productoGuardar.setProveedor(usuario.getProveedor());
        //aqui le estoy pasando el mismo producto que le pido a la vista
        service.guardarProducto(productoGuardar);
        //este primer add lo que hace es devolver la lista de productos que esta en la db
        model.addAttribute("productos", service.findProductByProveId((long) usuario.getProveedor().getId()));
        //este otro devuelve un nuevo producto para que el formulario salga en blanco
        model.addAttribute("productoNuevo", new Producto());
        return "presentation/productos/View";
        }
}