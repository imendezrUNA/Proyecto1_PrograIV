package eif209.facturacion.logic;

import eif209.facturacion.data.ClienteRepository;
import eif209.facturacion.data.ProveedorRepository;
import eif209.facturacion.data.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;

@org.springframework.stereotype.Service("service")
public class Service {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private ProveedorRepository proveedorRepository;
    @Autowired
    private ClienteRepository clienteRepository;

    public boolean existeUsuario(String nombreUsuario) {
        return usuarioRepository.findByNombreUsuario(nombreUsuario) != null;
    }

    public Usuario crearUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Proveedor crearProveedor(Proveedor proveedor) {
        return proveedorRepository.save(proveedor);
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
