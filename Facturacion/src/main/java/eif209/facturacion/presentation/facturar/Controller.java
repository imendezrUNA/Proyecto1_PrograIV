package eif209.facturacion.presentation.facturar;

import eif209.facturacion.dto.ProveedorRegistroDTO;
import eif209.facturacion.logic.*;
import jakarta.servlet.http.HttpSession;
import org.apache.catalina.filters.ExpiresFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.embedded.netty.NettyWebServer;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Controller("facturar")
public class Controller {
    @Autowired
    private Service service;
    private BigDecimal total = BigDecimal.valueOf(0.0);
    List<Detallefactura> detallesFactura = new ArrayList<>();
    Cliente cliente = new Cliente();
    
    @GetMapping("/presentation/facturar/show")
    public String show(Model modelo){
        if (cliente.getNombre() == null) {
            cliente.setNombre("...");
        }
        modelo.addAttribute("total", total);
        modelo.addAttribute("cliente", cliente);
        modelo.addAttribute("detalles", detallesFactura);
        Factura f1 = new Factura();
        Factura f2 = new Factura();
        modelo.addAttribute("f1", f1);
        modelo.addAttribute("f2", f2);
        return "presentation/facturar/View";
    }

    @PostMapping("/presentation/facturar/searchClient")
    public String buscarCliente(
        @ModelAttribute("idCliente") String idCliente){
        if (idCliente==null || idCliente == ""){
            return "redirect:/presentation/facturar/show";
        }

        Optional<Cliente> clienteDBopt = service.clienteRead(idCliente);
        if (clienteDBopt.isPresent()){
            cliente = clienteDBopt.get();
            return "redirect:/presentation/facturar/show";
        }
        return "redirect:/presentation/facturar/show?error=true";
    }

    @PostMapping("/presentation/facturar/searchProduct")
    public String buscarProducto(
                                @ModelAttribute("codigoProducto") String productoId){
        if (productoId==null || productoId == ""){
            return "redirect:/presentation/facturar/show";
        }
        if(!cliente.getNombre().equals("...")) {
            Optional<Producto> productoDBopt = service.productoRead(productoId);
            if (productoDBopt.isPresent()) {
                Producto productoDB = productoDBopt.get();
                Detallefactura detalle = new Detallefactura();
                detalle.setCantidad(1);
                detalle.setPrecioUnitario(productoDB.getPrecio());
                detalle.setProductoByProductoId(productoDB);
                detallesFactura.add(detalle);
                total = total.add(detalle.getSubtotal());

                return "redirect:/presentation/facturar/show";
            }
            return "redirect:/presentation/facturar/show";
        }
        return "redirect:/presentation/facturar/show";
    }
    @PostMapping("/presentation/facturar/agregar")
    public String agregarProducto(
                                 @ModelAttribute("seleccionado") String nomProd) {

        for(Detallefactura d : detallesFactura) {
            if (d.getProductoByProductoId().getNombre().equals(nomProd)) {
                d.setCantidad(d.getCantidad() + 1);
                total = total.add(d.getProductoByProductoId().getPrecio());
                return "redirect:/presentation/facturar/show";
            }
        }
        return "redirect:/presentation/facturar/show";
    }

    @PostMapping("/presentation/facturar/disminuir")
    public String disminuirProducto(
            @ModelAttribute("seleccionado") String nomProd) {

        for(Detallefactura d : detallesFactura) {
            if (d.getProductoByProductoId().getNombre().equals(nomProd)) {
                d.setCantidad(d.getCantidad() - 1);
                if(d.getCantidad() == 0){
                    detallesFactura.remove(d);
                }
                total = total.subtract(d.getProductoByProductoId().getPrecio());
                return "redirect:/presentation/facturar/show";
            }
        }
        return "redirect:/presentation/facturar/show";
    }
    @PostMapping("/presentation/facturar/guardar")
    public String guardar(HttpSession httpSession) {

        if(!cliente.getNombre().equals("...")) {
            Usuario usuario = (Usuario) httpSession.getAttribute("usuario");
            Proveedor proveedor = service.proveedorRead(usuario.getId()).get();
            service.registrarFactura(cliente, proveedor, detallesFactura, total);
            total = BigDecimal.valueOf(0.0);
            detallesFactura = new ArrayList<>();
            cliente = new Cliente();

        }
        return "redirect:/presentation/facturar/show";
    }



}
