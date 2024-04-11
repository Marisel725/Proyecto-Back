package com.ebikerrent.alquilerbicicletas.service.impl;

import com.ebikerrent.alquilerbicicletas.dto.entrada.modificacion.UsuarioModificacionEntrada;
import com.ebikerrent.alquilerbicicletas.dto.entrada.usuario.UsuarioEntradaDto;
import com.ebikerrent.alquilerbicicletas.dto.salida.usuario.UsuarioSalidaDto;
import com.ebikerrent.alquilerbicicletas.entity.Usuario;
import com.ebikerrent.alquilerbicicletas.exceptions.DuplicateEntryException;
import com.ebikerrent.alquilerbicicletas.exceptions.ResourceNotFoundException;
import com.ebikerrent.alquilerbicicletas.repository.UsuarioRepository;
import com.ebikerrent.alquilerbicicletas.service.IUsuarioService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService implements IUsuarioService {
    private final Logger LOGGER = LoggerFactory.getLogger(UsuarioService.class);
    private final UsuarioRepository usuarioRepository;

    private final ModelMapper modelMapper;

    public UsuarioService(UsuarioRepository usuarioRepository, ModelMapper modelMapper) {
        this.usuarioRepository = usuarioRepository;
        this.modelMapper = modelMapper;
    }

    //crea usuario admin

    @Override
    public UsuarioSalidaDto autenticarUsuario(String mail, String password) throws ResourceNotFoundException {
        Optional<Usuario> usuarioOptional = Optional.ofNullable(usuarioRepository.findByMail(mail));
        UsuarioSalidaDto usuarioSalidaDto = null;
        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            // Verificar si la contraseña coincide
            if (usuario.getPassword().equals(password)) {
                usuarioSalidaDto = entidadADto(usuario);
                return usuarioSalidaDto;
            } else {
                throw new ResourceNotFoundException("Las credenciales proporcionadas son incorrectas.");
            }
        } else {
            throw new ResourceNotFoundException("Usuario no encontrado con el correo electrónico proporcionado: " + mail);
        }

    }

    @Override
    public List<UsuarioSalidaDto> listar() {
        List<UsuarioSalidaDto> usuarios = usuarioRepository.findAll().stream().map(this::entidadADto).toList();
        LOGGER.info("Usuarios registrados", usuarios);
        return usuarios;
    }

    @Override
    public UsuarioSalidaDto registrarUsuario(UsuarioEntradaDto usuario) throws DuplicateEntryException {
        if (usuarioRepository.findByMail(usuario.getMail()) != null) {
            throw new DuplicateEntryException("Existe un usuario registrado con el mismo correo: " + usuario.getMail());
        }
        Usuario usuarioGuardado = usuarioRepository.save(dtoEntradaAEntidad(usuario));
        UsuarioSalidaDto usuarioSalidaDto = entidadADto(usuarioGuardado);
        LOGGER.info("Nuevo usuario registrado", usuarioSalidaDto);
        return usuarioSalidaDto;
    }

    @Override
    public UsuarioSalidaDto buscarUsuarioPorId(Long id) throws ResourceNotFoundException {
        Usuario usuarioBuscado = usuarioRepository.findById(id).orElse(null);
        UsuarioSalidaDto usuarioSalidaDto = null;

        if (usuarioBuscado == null) {
            LOGGER.error("No se ecnontro usuario con id:" + id);
            throw new  ResourceNotFoundException ("No se ecnontro usuario con id=" + id);

        } else {
            usuarioSalidaDto = entidadADto(usuarioBuscado);
            LOGGER.info("Se encontro usuario" + usuarioSalidaDto);
        }
        return usuarioSalidaDto;
    }

    @Override
    public UsuarioSalidaDto modificarUsuario(UsuarioModificacionEntrada usuarioModificacionEntrada) throws ResourceNotFoundException {
        Optional<Usuario> usuarioAmodificar = usuarioRepository.findById(usuarioModificacionEntrada.getId());
        if (!usuarioAmodificar.isPresent()) {
            throw new ResourceNotFoundException("No se encuentra usuario");
        }

        return null;
    }


    //MAPEO
    private Usuario dtoEntradaAEntidad(UsuarioEntradaDto usuarioEntradaDto) {
        return modelMapper.map(usuarioEntradaDto, Usuario.class);
    }


    public UsuarioSalidaDto entidadADto(Usuario usuario) {
        return modelMapper.map(usuario, UsuarioSalidaDto.class);
    }
}

