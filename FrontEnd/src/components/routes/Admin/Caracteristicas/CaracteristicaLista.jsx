import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { pathIcons } from '../../../utils/global.context';
import './CaracteristicaLista.css'

// Lista las características
const CaracteristicaLista = () => {
  const [caracteristicas, setCaracteristicas] = useState([]);

  useEffect(() => {
    const fetchCaracteristicas = async () => {
      try {
        const response = await axios.get('https://backendebikerent-production.up.railway.app/caracteristicas/listar');
        setCaracteristicas(response.data);
      } catch (error) {
        console.error('Error al obtener características:', error);
      }
    };

    fetchCaracteristicas();
  }, []);


//aun no hay metodo para eliminar
  // const handleDeleteClick = async (caracteristicaId) =>  {
  //   try {
  //     const response = await axios.delete(`http://localhost:8080/caracteristicas/eliminar/${caracteristicaId}`);

  //     if (response.status === 204) {
  //       const updatedCaracteristicas = caracteristicas.filter(caracteristica => caracteristica.id !== caracteristicaId);
  //       setCaracteristicas(updatedCaracteristicas);
  //       alert('Característica eliminada correctamente.');
  //     } else {
  //       alert('Error al intentar eliminar la característica. Estado: ' + response.status);
  //     }
  //   } catch (error) {
  //     console.error('Error deleting characteristic:', error);
  //     alert('Error al intentar eliminar la característica. Detalles: ' + error.message);
  //   }
  // };







  return (
    <div>
      <h1>Lista de Características</h1>

      <div className="producto-item">
        <div className="producto-id">ID Característica</div>
        <div className="producto-nombre">Nombre Característica</div>
        <div className="producto-acciones">Icono</div>
        <div className="producto-acciones">Acciones</div>
      </div>

      <ul>
        {caracteristicas.map(caracteristica => (
          <li key={caracteristica.id} className="producto-item">
            <div> <p>{caracteristica.id}</p></div>
            <div><p> {caracteristica.nombre}</p></div>
            <div className='icon-aling'><img src={caracteristica.icono} alt="Icono" className="caracteristica-icon" /></div>
            <div >   

              {/*             
              <img
                src={pathIcons.delete}
                alt="Eliminar"
                className="delete-icon"
                onClick={() => handleDeleteClick(caracteristica.id)}
      
             />

              <img
                src={pathIcons.edit}
                alt="Modificar"
                className="delete-icon"
              />
              */}
              <h1>.</h1>

            </div>
      
          </li>
        ))}
      </ul>
    </div>
  );
};

export default CaracteristicaLista;
