import React, { useContext, useState, useEffect } from 'react'
import './categorias.css'
import axios from 'axios'
import { Link } from 'react-router-dom';
import { ContextGlobal } from '../utils/global.context';

const Categorias = (props) => {
  const { contexto, setContexto } = useContext(ContextGlobal);
  const [categorias, setCategorias] = useState([])
  const [ocultar, setOcultar] = useState(false)

  useEffect(() => {
    const manejadorCategorias = async () => {
      try {
        const response = await axios.get('https://backendebikerent-production.up.railway.app/categorias/listar');
        setCategorias(response.data);
        setOcultar(false)
      } catch (error) {
        console.error('Error al obtener categorias:', error);
      }
    };

    manejadorCategorias();
  }, []);

  useEffect(() => {
    if (contexto.arrayCiclas.length > 0) {
      //setOcultar(true)
    }
  }, [contexto.arrayCiclas]);

  return (
    <div className='categorias-wrapper' style={{ display: ocultar ? 'none' : 'block' }} id="categorias" >
      <div style={{ width: '100%' }}>
        <h3 className='titulos'> Nuestras Categorías</h3>
      </div>
      <div className='categorias-conteiner'>

        {categorias.map((categoria, index) => (
          <Link key={index} to={`categorias/${categoria.titulo}`} className='card-link'>
            <article className='card-categoria' key={index}>
              <img className='img-categoria' src={categoria.imagen} alt={categoria.titulo} />
              <span className='nombre-categoria'>{categoria.titulo}</span>
            </article>
          </Link>
        ))}

      </div>
    </div>
  )
}

export default Categorias