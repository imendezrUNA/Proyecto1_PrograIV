package eif209.facturacion.logic;

import eif209.facturacion.data.ClienteRepository;
import eif209.facturacion.data.ProductoRepository;
import eif209.facturacion.data.ProveedorRepository;
import eif209.facturacion.data.UsuarioRepository;
import eif209.facturacion.dto.ProveedorRegistroDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service("service")
public class Service {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private ProveedorRepository proveedorRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private ProductoRepository productoRepository;

    @Transactional
    public boolean registrarProveedorYUsuario(ProveedorRegistroDTO registroDTO) {
        // Verificar si ya existe un usuario con el mismo username
        if (usuarioRepository.findByNombreUsuario(registroDTO.getNombreUsuario()).isPresent()) {
            return false;
        }

        // Verificar si ya existe un proveedor con el mismo ID
        if (proveedorRepository.findById(Long.valueOf(registroDTO.getIdProveedor())).isPresent()) {
            return false;
        }

        // Crear el nuevo Usuario
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombreUsuario(registroDTO.getNombreUsuario());
        nuevoUsuario.setContrasena(registroDTO.getContrasena()); // texto plano
        nuevoUsuario.setEstado(Usuario.Estado.PENDIENTE);
        nuevoUsuario.setRol(Usuario.Rol.PROVEEDOR);

        // Crear el nuevo Proveedor
        Proveedor nuevoProveedor = new Proveedor();
        nuevoProveedor.setId(Long.parseLong(registroDTO.getIdProveedor()));
        nuevoProveedor.setNombre(registroDTO.getNombreProveedor());
        nuevoProveedor.setCorreoElectronico(registroDTO.getCorreoElectronico());
        nuevoProveedor.setNumeroTelefono(registroDTO.getNumeroTelefono());
        nuevoProveedor.setDireccion(registroDTO.getDireccion());
        nuevoProveedor.setUsuarioByUsuarioId(nuevoUsuario); // Asociar el usuario al proveedor

        // Guardar las entidades en la db
        usuarioRepository.save(nuevoUsuario);
        proveedorRepository.save(nuevoProveedor);

        return true;
    }

    @Transactional
    public boolean cambiarEstadoProveedor(Long proveedorId, Usuario.Estado nuevoEstado) {
        Optional<Proveedor> proveedorOpt = proveedorRepository.findById(proveedorId);
        if (proveedorOpt.isPresent()) {
            Proveedor proveedor = proveedorOpt.get();
            Usuario usuario = proveedor.getUsuarioByUsuarioId();
            usuario.setEstado(nuevoEstado);
            usuarioRepository.save(usuario);
            return true;
        }
        return false;
    }

    public boolean registroDeProducto(Producto producto) {
        if (productoRepository.findProductoById(producto.getId()).equals(producto.getId())) {
            return false;
        }
        //crea nuevo producto
        Producto nuevo = new Producto();
        nuevo.setId(producto.getId());
        nuevo.setDescripcion(producto.getDescripcion());
        nuevo.setNombre(producto.getNombre());
        nuevo.setPrecio(producto.getPrecio());
        nuevo.setProveedor(producto.getProveedor());
        return true;
    }

    public Iterable<Producto> findProductByProveId(Long proveId) {
        return productoRepository.findProductoByProveedorId(proveId);
    }

    public Optional<Usuario> usuarioRead(String nombreUsuario) {
        return usuarioRepository.findByNombreUsuario(nombreUsuario);
    }

    public Optional<Proveedor> proveedorRead(int usuarioId) {
        return proveedorRepository.findByUsuarioByUsuarioId_Id(usuarioId);
    }

    public Iterable<Proveedor> proveedorFindAll() {
        return proveedorRepository.findAll();
    }

    public List<Proveedor> proveedorFindAllSortedByEstadoAsc() {
        return proveedorRepository.findAllByUsuarioEstadoOrderByEstadoAsc();
    }

    public List<Proveedor> proveedorFindAllSortedByEstadoDesc() {
        return proveedorRepository.findAllByUsuarioEstadoOrderByEstadoDesc();
    }

    public Iterable<Cliente> clienteFindAll() {
        return clienteRepository.findAll();
    }

    public Iterable<Cliente> clienteReadAll(Proveedor proveedor) {
        return null; //cambiar
    }

    public Iterable<Cliente> clienteSearch(Proveedor proveedor, String nombreCliente) {
        return null; //cambiar
    }

    public void guardarProducto(Producto productoGuardar) {
        productoRepository.save(productoGuardar);

    }

    @Transactional
    public boolean actualizarProveedor(Proveedor proveedorActualizado) {
        if (proveedorActualizado == null || proveedorActualizado.getUsuarioByUsuarioId() == null) {
            return false;
        }

        Optional<Proveedor> proveedorOpt = proveedorRepository.findById(proveedorActualizado.getId());
        if (proveedorOpt.isEmpty()) {
            return false; // No se encontró un proveedor con el ID
        }

        Proveedor proveedorExistente = proveedorOpt.get();
        proveedorExistente.setNombre(proveedorActualizado.getNombre());
        proveedorExistente.setCorreoElectronico(proveedorActualizado.getCorreoElectronico());
        proveedorExistente.setNumeroTelefono(proveedorActualizado.getNumeroTelefono());
        proveedorExistente.setDireccion(proveedorActualizado.getDireccion());

        proveedorRepository.save(proveedorExistente);
        return true;
    }
}
