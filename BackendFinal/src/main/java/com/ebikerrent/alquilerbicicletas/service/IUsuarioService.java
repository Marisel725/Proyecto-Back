package com.ebikerrent.alquilerbicicletas.service;

import com.ebikerrent.alquilerbicicletas.dto.entrada.modificacion.UsuarioModificacionEntrada;
import com.ebikerrent.alquilerbicicletas.dto.entrada.usuario.UsuarioEntradaDto;
import com.ebikerrent.alquilerbicicletas.dto.salida.usuario.UsuarioSalidaDto;
import com.ebikerrent.alquilerbicicletas.exceptions.DuplicateEntryException;
import com.ebikerrent.alquilerbicicletas.exceptions.ResourceNotFoundException;

import java.util.List;

public interface IUsuarioService {
    List<UsuarioSalidaDto> listar();
    UsuarioSalidaDto registrarUsuario(UsuarioEntradaDto usuario) throws DuplicateEntryException;
    UsuarioSalidaDto buscarUsuarioPorId(Long id)throws ResourceNotFoundException;
    UsuarioSalidaDto modificarUsuario (UsuarioModificacionEntrada usuarioModificacionEntrada) throws ResourceNotFoundException;
    UsuarioSalidaDto autenticarUsuario(String mail, String password)throws ResourceNotFoundException;
}
