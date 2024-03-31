package eif209.facturacion.logic;

import eif209.facturacion.data.ClienteRepository;
import eif209.facturacion.data.ProveedorRepository;
import eif209.facturacion.data.UsuarioRepository;
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
    public boolean registrarProveedorYUsuario(Proveedor proveedor, String nombreUsuario, String contrasena) {
        // Verificar si ya existe un proveedor con el mismo ID
        if (proveedorRepository.findById(proveedor.getId()).isPresent()) {
            return false;
        }

        // Si el proveedor NO existe
        Usuario usuario = new Usuario();
        usuario.setNombreUsuario(nombreUsuario);
        usuario.setContrasena(contrasena); // texto plano momentáneamente
        usuario.setEstado(Usuario.Estado.PENDIENTE);
        usuario.setRol(Usuario.Rol.PROVEEDOR);
        usuarioRepository.save(usuario);

        proveedor.setUsuarioByUsuarioId(usuario);
        proveedorRepository.save(proveedor);

        return true;
    }

    public Optional<Usuario> usuarioRead(String nombreUsuario) {
        Usuario usuario = usuarioRepository.findByNombreUsuario(nombreUsuario);
        return Optional.ofNullable(usuario);
    }

    public Optional<Proveedor> proveedorRead(int usuarioId) {
        return Optional.ofNullable(proveedorRepository.findByUsuarioByUsuarioId_Id(usuarioId));
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
