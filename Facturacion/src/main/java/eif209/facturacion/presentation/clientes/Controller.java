package eif209.facturacion.presentation.clientes;

import eif209.facturacion.logic.Cliente;
import eif209.facturacion.logic.Proveedor;
import eif209.facturacion.logic.Service;
import eif209.facturacion.logic.Usuario;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@org.springframework.stereotype.Controller("clientes")
@SessionAttributes({"clientes","clienteSearch","clienteEdit","proveedor"})
public class Controller {
    @Autowired private Service service;
    @ModelAttribute("clientes") public Iterable<Cliente> clientes() { return new ArrayList<Cliente>(); }
    @ModelAttribute("clienteSearch") public Cliente clienteSearch() { return new Cliente(); }
    @ModelAttribute("clienteEdit") public Cliente clienteEdit() { return new Cliente(); }
    @ModelAttribute("proveedor") public Proveedor proveedor() { return new Proveedor(); }

    @GetMapping("/presentation/clientes/show")
    public String show(HttpSession httpSession, Model model) {
        Usuario usuario = (Usuario) httpSession.getAttribute("usuario");
        model.addAttribute("clientes", service.findClienteByProveedorId((long) usuario.getProveedor().getId()));
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
    @PostMapping("/presentation/clientes/add")
    public String agregarCliente(
            @ModelAttribute("clienteGuardar") Cliente clienteGuardar, HttpSession httpSession,
            Model model) {

        Usuario usuario = (Usuario) httpSession.getAttribute("usuario");
        // Le asigno al cliente que se va a guardar el proveedor que lo extraigo desde la sesión HTTP
        clienteGuardar.setProveedor(usuario.getProveedor());
        // Aquí estoy guardando el cliente que recibo del formulario
        service.guardarCliente(clienteGuardar);
        // Este primer add devuelve la lista de clientes que están en la base de datos
        model.addAttribute("clientes", service.findClienteByProveedorId((long) usuario.getProveedor().getId()));
        // Este otro devuelve un nuevo cliente para que el formulario salga en blanco
        model.addAttribute("clienteNuevo", new Cliente());
        return "presentation/clientes/View";
    }
    @GetMapping("/presentation/clientes/{id}/editar")
    public String mostrarFormularioEdicion(@PathVariable("id") long id, Model model) {
        Optional<Cliente> cliente = service.clienteporID(id);
        model.addAttribute("cliente", cliente.orElse(new Cliente())); // Si no se encuentra el cliente, se envía uno vacío
        return "/presentation/clientes/show"; // La misma vista se utiliza para mostrar el formulario de edición
    }

    @PostMapping("/presentation/clientes/editar")
    public String editarCliente(@ModelAttribute("cliente") Cliente cliente, HttpSession httpSession, Model model) {
        Usuario usuario = (Usuario) httpSession.getAttribute("usuario");
        cliente.setProveedor(usuario.getProveedor());
        service.guardarCliente(cliente);
        model.addAttribute("clientes", service.findClienteByProveedorId((long) usuario.getProveedor().getId()));
        return "clientes";
    }



}
