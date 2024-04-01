package eif209.facturacion.logic;

import eif209.facturacion.data.ClienteRepository;
import eif209.facturacion.data.ProveedorRepository;
import eif209.facturacion.data.UsuarioRepository;
import eif209.facturacion.dto.ProveedorRegistroDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@org.springframework.stereotype.Service("service")
public class Service {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private ProveedorRepository proveedorRepository;
    @Autowired
    private ClienteRepository clienteRepository;

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

    public Optional<Usuario> usuarioRead(String nombreUsuario) {
        return usuarioRepository.findByNombreUsuario(nombreUsuario);
    }

    public Optional<Proveedor> proveedorRead(int usuarioId) {
        return proveedorRepository.findByUsuarioByUsuarioId_Id(usuarioId);
    }

    public Iterable<Proveedor> proveedorFindAll() {
        return proveedorRepository.findAll();
    }

    public Iterable<Cliente> clienteFindAll() {
        return clienteRepository.findAll();
    }

    public Iterable<Cliente> clienteReadAll(Proveedor proveedor) {
        return null; //cambiar
    }

    public Iterable<Cliente> clienteSearch (Proveedor proveedor, String nombreCliente) {
        return null; //cambiar
    }
}
