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
    private ProveedorRepository proveedorRepository;
    private ClienteRepository clienteRepository;

    public Optional<Usuario> usuarioRead(int id) {
        return usuarioRepository.findById(String.valueOf(id));
    }

    /*public Optional<Proveedor> proveedorRead(int id) {
        return ;
    }*/

    public Iterable<Proveedor> proveedorFindAll() {
        return proveedorRepository.findAll();
    }

    public Iterable<Cliente> clienteFindAll() {
        return clienteRepository.findAll();
    }
}
