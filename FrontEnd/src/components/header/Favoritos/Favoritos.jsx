import React, { useEffect, useState } from 'react'
import axios from 'axios';
import { Link } from 'react-router-dom';

const Favoritos = () => {

    const [listadeFavoritos, setListadeFavoritos] = useState([])
console.log(listadeFavoritos);

    useEffect(() => {
        const listaFavoritos = async () => {
            const sessionDatos = localStorage.getItem('ebikerent-session')
            if (sessionDatos !== null) {
                const datos = JSON.parse(sessionDatos)
                const correo = datos.correo
                const payload = {
                    correo: correo
                }
                try {
                    const response = await axios.post('https://backendebikerent-production.up.railway.app/productoFavorito/listarFavoritosPorUsuario', payload);
                    setListadeFavoritos(response.data)
                    console.log("FAVORITOS", response.data);
                } catch (error) {
                    console.error('Error al listar favoritos', error, correo);
                }
            }
            else {
                console.log("Debe estar registrado para listar favoritos");
            }

            
        };

        listaFavoritos();
    }, [])


    return (
        <div className='container-middle'>
            <h3 className='titulos'>Favoritos</h3>
            <div className='div-card-producto'>
                {listadeFavoritos.map((producto, index) => (
                    <div className='wrapper-card-producto-home' key={index} >
                        <article className='card-producto-home'>
                            <Link to={'/productos/' + producto.id} >
                                <img className='image-ciclas-home' src={producto.imagenes[0].urlImg} alt={producto.nombre} />
                            </Link>
                            <div className='titulo-card-container'>
                                <div className='titulo-fav'>
                                    <span>{producto.nombre}</span>
                                    
                                </div>
                            </div>
                        </article>
                    </div>
                ))}
            </div>
        </div>
    )
}

export default Favoritos