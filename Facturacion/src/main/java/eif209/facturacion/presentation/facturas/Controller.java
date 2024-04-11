package eif209.facturacion.presentation.facturas;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Table;
import eif209.facturacion.logic.*;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templatemode.TemplateMode;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Phaser;

@org.springframework.stereotype.Controller("facturas")
public class Controller {
    @Autowired
    private Service service;

    @GetMapping("/presentation/facturas/show")
    public String show(HttpSession httpSession, Model modelo) {
        Usuario usuario = (Usuario) httpSession.getAttribute("usuario");
        Proveedor proveedor = service.proveedorRead(usuario.getId()).get();
        List<Factura> facturas = service.facturasByProveedorId(proveedor.getId());

        modelo.addAttribute("proveedor",proveedor);
        modelo.addAttribute("facturas",facturas);
        return "presentation/facturas/View";
    }

    @PostMapping("/presentation/facturas/pdf")
    public void pdf(@ModelAttribute("facturaNumero") int facturaNumero, HttpServletResponse response) throws IOException{
        Factura factura = service.getFacturaById(facturaNumero);
        PdfWriter writer = new PdfWriter(response.getOutputStream());

        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf, PageSize.A4);

        Paragraph paragraph = new Paragraph();
        paragraph.add("Detalles de la Factura\n");

        Table table = new Table(5);

        table.addCell("ID de la Factura");
        table.addCell("Fecha");
        table.addCell("Cliente del ID");
        table.addCell("Nombre del Cliente");
        table.addCell("Total");

        table.addCell( String.valueOf(factura.getId()));
        table.addCell(factura.getFecha().toString());
        table.addCell(String.valueOf(factura.getClienteByClienteId().getId()));
        table.addCell(factura.getClienteByClienteId().getNombre());
        table.addCell(String.valueOf(factura.getTotal()));

        Table table2 = new Table(5);



        table2.addCell("ID del Producto");
        table2.addCell("Descripción del Producto");
        table2.addCell("Cantidad");
        table2.addCell("Precio");
        table2.addCell("Subtotal");

        for (Detallefactura d: factura.getDetallefacturasById()){

            table2.addCell(String.valueOf(d.getProductoByProductoId().getId()));
            table2.addCell(d.getProductoByProductoId().getNombre());
            table2.addCell(String.valueOf(d.getCantidad()));
            table2.addCell(String.valueOf(d.getPrecioUnitario()));
            table2.addCell(String.valueOf(d.getSubtotal()));

        }
        Paragraph paragraph2 = new Paragraph();
        paragraph2.add("\nDetalles de los Productos");
        document.add(paragraph);
        document.add(table);
        document.add(paragraph2);
        document.add(table2);
        document.close();
    }

    @PostMapping("/presentation/facturas/xml")
    public void xml(@ModelAttribute("facturaNumero") int facturaNumero, HttpServletResponse response) throws IOException {
        Factura factura = service.getFacturaById(facturaNumero);
        SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
        resolver.setApplicationContext(new AnnotationConfigApplicationContext());
        resolver.setPrefix("classpath:/templates/");
        resolver.setSuffix(".xml");
        resolver.setCharacterEncoding("UTF-8");
        resolver.setTemplateMode(TemplateMode.XML);
        SpringTemplateEngine engine = new SpringTemplateEngine();
        engine.setTemplateResolver(resolver);
        Context ctx = new Context();
        ctx.setVariable("factura", factura);
        String xml = engine.process("presentation/facturas/XmlView",ctx);
        response.setContentType("application/xml");
        PrintWriter writer = response.getWriter();
        writer.print(xml);
        writer.close();

    }
}
