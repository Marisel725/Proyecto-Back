import React, { useEffect, useState } from 'react'
import { useParams } from 'react-router-dom';
import axios from 'axios';
import { Link } from 'react-router-dom';

const CategoriasLista = (props) => {

    const { categoria } = useParams()
    const [productos, setProductos] = useState([]);

    useEffect(() => {
        const filtroCategorias = async () => {
            try {
                const response = await axios.get('https://backendebikerent-production.up.railway.app/productos/listar');
                if (categoria) {
                    setProductos(response.data.filter((producto) => producto.categoria.titulo === categoria));
                } else {
                    setProductos(response.data);
                }
            } catch (error) {
                console.error('Error al obtener categorias:', error);
            }
        };

        filtroCategorias();
    }, [categoria]);


    return (

        <>
            <div className='container-middle'>
                
                <h1 className='titulos'>
                    {categoria}
                </h1>
                <div className='div-card-producto'>
                    {productos.map((cicla, index) => (
                        <Link to={'/productos/' + cicla.id} key={index} className='wrapper-card-producto-home'>
                            <article className='card-producto-home'>
                                <img className='image-ciclas-home' src={cicla.imagenes[0].urlImg} alt={cicla.nombre} />
                                <div className='titulo-card-container'>
                                    <span>{cicla.nombre}</span>
                                </div>
                            </article>
                        </Link>

                    ))}
                </div>
            </div>
        </>

    )
}

export default CategoriasLista