import React, { useEffect, useState } from 'react'
import { useParams } from 'react-router-dom';
import axios from 'axios';

const CategoriasLista = (props) => {

    const { categoria } = useParams()
    const [productos, setProductos] = useState([]);

    useEffect(() => {
        const filtroCategorias = async () => {
            try {
                const response = await axios.get('http://localhost:8080/productos/listar');
                if (categoria) {
                    setProductos(response.data.filter((producto) => producto.categoria.titulo === categoria));
                } else {
                    setProductos(response.data);
                }
            } catch (error) {
                console.error('Error al obtener productos:', error);
            }
        };

        filtroCategorias();
    }, [categoria]);

    console.log(productos);
    console.log({ categoria });

    return (

        <>
            <h1>
                {categoria}
            </h1>
        </>

    )
}

export default CategoriasLista