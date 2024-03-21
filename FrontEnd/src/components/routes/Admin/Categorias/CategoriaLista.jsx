import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { pathIcons } from '../../../utils/global.context';

//lista las categorias
const CategoriaLista = () => {
  const [categorias, setCategorias] = useState([]);

  useEffect(() => {
    const fetchCategorias = async () => {
      try {
        const response = await axios.get('http://localhost:8080/categorias/listar');
        console.log('Categorías obtenidas:', response.data);
        setCategorias(response.data);
      } catch (error) {
        console.error('Error al obtener categorías:', error);
      }
    };

    fetchCategorias();
  }, []);




  return (
    <div>
      <h1>Lista de Categorias</h1>


<div className="producto-item">
  <div className="producto-id">ID categoria</div>
   <div className="producto-nombre">Nombre Categoria</div>
   <div className="producto-acciones">Acciones</div>
 </div>

      <ul>
        {categorias.map(categoria => (
          <li key={categoria.id} className="producto-item">

            <div> <p>{categoria.id}</p></div>
            <div><p> {categoria.titulo}</p></div>
            {/* <p>{categoria.descripcion}</p> */}
            {/* <img src={categoria.imagen} alt="" className='card-categoria'/> */}
                    <div>               
                      <img
                      src={pathIcons.delete}
                      alt="Eliminar"
                      className="delete-icon"
                      
                    />
                       <img
                      src={pathIcons.edit}
                      alt="Modificar"
                      className="delete-icon"
                     
                    />
                    </div>
           
          </li>
        ))}
      </ul>
    </div>
  );
};

export default CategoriaLista;
