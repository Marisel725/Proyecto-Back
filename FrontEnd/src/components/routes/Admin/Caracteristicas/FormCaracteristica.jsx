import React, { useState } from 'react';
import axios from "axios";
import { pathIcons } from '../../../utils/global.context';
import '../Productos/ProductosRegistrar.css';

const FormCaracteristica = ({ onAgregarCaracteristica }) => {
  const [nombre, setNombre] = useState("");
  const [icono, setIcono] = useState("");

  // Manejadores
  const handleNombreChange = (event) => {
    setNombre(event.target.value);
  };

  const handleIconoChange = (event) => {
    setIcono(event.target.value);
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    agregarCaracteristica();
  };

  // Envia solicitud para agregar nueva característica
  
  const agregarCaracteristica = async () => {
    const nuevaCaracteristica = {
      nombre: nombre,
      icono: icono,
    };
  
    try {
      const response = await axios.post(
        "https://backendebikerent-production.up.railway.app/caracteristicas/registrar",
        nuevaCaracteristica
      );
  
      console.log("Característica agregada:", response.data);
      alert("CARACTERÍSTICA AGREGADA CORRECTAMENTE");
  
      setNombre("");
      setIcono("");
    } catch (error) {
      console.error("Error al agregar la característica:", error);
      alert("ERROR AL AGREGAR CARACTERÍSTICA" + error);
    }
  };




  return (
    <div className='FormCaracteristica-main'>
      <form className="caracteristica-form" onSubmit={handleSubmit}>
        <h1>Agregar nuevas características</h1>

        <div className="form-group">
          <label>Nombre:</label>
          <input
            type="text"
            name="nombre"
            placeholder="Ingrese el nombre"
            value={nombre}
            onChange={handleNombreChange}
          />
        </div>
        <div className="form-group">
          <label>Icono:</label>
          <input
            type="text"
            name="icono"
            placeholder="Ingrese la URL del icono"
            value={icono}
            onChange={handleIconoChange}
          />
        </div>

        <button type="submit">
          <img src={pathIcons.save} alt="Imagen del botón" className='saveButton' />
          Agregar Característica
        </button>
      </form>
    </div>
  );
};

export default FormCaracteristica;
