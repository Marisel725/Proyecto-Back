package com.ebikerrent.alquilerbicicletas.dto.salida.favorito;

import com.ebikerrent.alquilerbicicletas.dto.salida.producto.ProductoSalidaDto;
import com.ebikerrent.alquilerbicicletas.dto.salida.usuario.UsuarioSalidaDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class FavoritoSalida {
    private Long id;
    private ProductoSalidaDto producto;
    private UsuarioSalidaDto usuario;
    private Boolean favorito;

    @Override
    public String toString() {
        return "FavoritoSalida{" +
                "producto=" + producto +
                ", usuario=" + usuario +
                ", favorito=" + favorito +
                '}';
    }
}
