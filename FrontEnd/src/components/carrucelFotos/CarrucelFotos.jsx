import React, { useContext, useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';
import { ContextGlobal } from '../utils/global.context';
import './carrucelFotos.css';

const CarrucelFotos = () => {
  const { contexto, setContexto } = useContext(ContextGlobal);
  const [titulo, setTitulo] = useState("")
  const [ciclas, setCiclas] = useState([])
  const [fotos, setFotos] = useState(0);
  const [cantFotos, setCantfotos] = useState(ciclas.slice(0, 3));

  useEffect(() => {
    const manejadorProductos = async () => {
      try {
        const response = await axios.get('https://backendebikerent-production.up.railway.app/productos/listar');
        setCiclas(response.data);
        setTitulo("Recomendaciones")
      } catch (error) {
        console.error('Error al obtener productos:', error);
      }
    };
    manejadorProductos();
  }, []);

  useEffect(() => {
    if (contexto.arrayCiclas.length > 0) {
      setCiclas(contexto.arrayCiclas);
      setTitulo("Búsqueda Realizada");
    }
  }, [contexto.arrayCiclas]);

  useEffect(() => {
    setCantfotos(ciclas.slice(0, 3));
  }, [ciclas]);

  const proxFoto = () => {
    const proximagen = fotos === ciclas.length - 1 ? 0 : fotos + 1;
    setFotos(proximagen);
    setCantfotos(ciclas.slice(proximagen, proximagen + 3));
  };
  const fotoAnterior = () => {
    const imagAnt = fotos === 0 ? ciclas.length - 1 : fotos - 1;
    setFotos(imagAnt);
    setCantfotos(ciclas.slice(imagAnt, imagAnt + 3));
  };


  return (
    <>
      <h3 className='titulos'>{titulo}</h3>
      <div className="carrusel-container">
        <button className="carrusel-button" onClick={fotoAnterior}>{'<'}</button>

        {cantFotos.length > 0 ? (
          <>
            {window.innerWidth < 768 ? (
              <div className='card-carrusel'>
                <img
                  className="carrusel-image"
                  src={cantFotos[0].imagenes[0].urlImg}
                  alt={cantFotos[0].nombre}
                />
                <h3 className='titulo-carrusel'>{cantFotos[0].nombre}</h3>
              </div>
            ) : (
              cantFotos.map((cicla, index) => (
                <div className='card-carrusel' key={index}>
                  <Link to={'/productos/' + cicla.id} >
                    <img
                      key={index}
                      className="carrusel-image"
                      src={cicla.imagenes[0].urlImg}
                      alt={cicla.nombre}
                    />
                  </Link>
                  <h3 className='titulo-carrusel'>{cicla.nombre}</h3>
                </div>
              ))
            )}
          </>
        ) : (
          <p>Cargando...</p>
        )}

        {/*
      {cantFotos.map((cicla, index) => (
        <img key={index} className="carrusel-image" src={cicla.imgBici} alt={cicla.nombreBici} />
      ))}
      */}

        <button className="carrusel-button" onClick={proxFoto}>{'>'}</button>
      </div>
    </>
  );
};

export default CarrucelFotos