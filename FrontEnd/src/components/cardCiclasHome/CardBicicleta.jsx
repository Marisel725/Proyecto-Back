import React, { useContext, useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';
import { ContextGlobal } from '../utils/global.context';
import './cardCiclasHome.css'
import { FaHeart } from 'react-icons/fa';


const CardBicicleta = () => {
  const [titulo, setTitulo] = useState("")
  const { contexto, setContexto } = useContext(ContextGlobal);
  const [nuevosProductos, setNuevosProductos] = useState([]);

  useEffect(() => {
    const manejadorProductos = async () => {
      try {
        const response = await axios.get('https://backendebikerent-production.up.railway.app/productos/listar');
        setNuevosProductos(response.data);
        setTitulo("Recomendaciones")
      } catch (error) {
        console.error('Error al obtener productos:', error);
      }
    };
    manejadorProductos();
  }, []);

  useEffect(() => {
    if (contexto.arrayCiclas.length > 0) {
      setNuevosProductos(contexto.arrayCiclas);
      setTitulo("Búsqueda Realizada");
    }
  }, [contexto.arrayCiclas]);




  //logica para guardar favoritos en local storage
  useEffect(() => {
    const productosFavoritosGuardados = JSON.parse(localStorage.getItem('productosFavoritos'));
    if (productosFavoritosGuardados) {
      setNuevosProductos(nuevosProductos.map((producto) => ({
        ...producto,
        favorito: productosFavoritosGuardados.includes(producto.id)
      })));
      console.log("Productos favoritos guardados:", productosFavoritosGuardados);
    }
  }, []);

  const toggleFavorito = (idProducto) => {
    const nuevosProductosActualizados = nuevosProductos.map((producto) =>
      producto.id === idProducto ? { ...producto, favorito: !producto.favorito } : producto
    );
    setNuevosProductos(nuevosProductosActualizados);
    const productosFavoritos = nuevosProductosActualizados
      .filter((producto) => producto.favorito)
      .map((producto) => ({ id: producto.id, nombre: producto.nombre }));
    localStorage.setItem('productosFavoritos', JSON.stringify([
      { id: 1, nombre: 'Producto 1' },
      { id: 2, nombre: 'Producto 2' },
      { id: 3, nombre: 'Producto 3' }
    ]));
  };



  //favs

  const [listadeFavoritos, setListadeFavoritos] = useState([])

  const handleFavoritos = async (id) => {
    
    const sessionDatos = localStorage.getItem('ebikerent-session')
    if (sessionDatos !== null) {
      const datos = JSON.parse(sessionDatos)
      const correo = datos.correo

      const datosFavorito = {
        "correo": correo,
        "producto_id": id,
        "favorito": true
      }
      try {
        const response = await axios.post('https://backendebikerent-production.up.railway.app/productoFavorito/agregar', datosFavorito);
      } catch (error) {
        console.error('Error al agregar favorito', error, datosFavorito);
      }
    }
    else {
      console.log("Debe estar registrado para guardar favoritos");
    }
  };

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
        } catch (error) {
          console.error('Error al listar favoritos', error, correo);
        }
      }
      else {
        console.log("Debe estar registrado para listar favoritos");
      }
    };

    listaFavoritos();
  }, [handleFavoritos])





  return (
    <div>
      <h3 className='titulos'>{titulo}</h3>
      <div className='div-card-producto'>
        {nuevosProductos.map((producto, index) => (
          <div className='wrapper-card-producto-home' key={index} >
            <article className='card-producto-home'>
              <Link to={'/productos/' + producto.id} >
                <img className='image-ciclas-home' src={producto.imagenes[0].urlImg} alt={producto.nombre} />
              </Link>
              <div className='titulo-card-container'>
                <div className='titulo-fav'>
                  <span>{producto.nombre}</span>
                  <FaHeart
                    className={'fa-hearth ' + (listadeFavoritos.map(objeto => objeto.id).includes(producto.id) ? 'esFavorito' : '')}
                    onClick={() => {
                      
                      handleFavoritos(producto.id);
                    }}
                  />
                </div>
              </div>
            </article>
          </div>
        ))}
      </div>
    </div>
  );
};

export default CardBicicleta;
