import React, { useState } from 'react';
import axios from "axios";
import { pathIcons } from '../../../utils/global.context';
import '../Productos/ProductosRegistrar.css';

const FormCategoria = ({ onSubmit }) => {
  const [titulo, setTitulo] = useState("");
  const [descripcion, setDescripcion] = useState("");
  const [imagen, setImagen] = useState("");


  //manejadores
  const handleTituloChange = (event) => {
    setTitulo(event.target.value);
  };

  const handleDescripcionChange = (event) => {
    setDescripcion(event.target.value);
  };

  const handleImagenChange = (event) => {
    setImagen(event.target.value);
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    crearCategoria();
  };
  

  //envia solicitud para crear nueva categoria
  const crearCategoria = async () => {
    const nuevaCategoria = {
      titulo: titulo,
      descripcion: descripcion,
      imagen: imagen,
    };

    try {
      const response = await axios.post(
        "http://localhost:8080/categorias/registrar",
        nuevaCategoria
      );

      console.log("Categoría creada:", response.data);
      alert("CATEGORÍA CREADA CORRECTAMENTE");

      setTitulo("");
      setDescripcion("");
      setImagen("");
    } catch (error) {
      console.error("Error al crear la categoría:", error);
      alert("ERROR AL CREAR CATEGORÍA" + error);
    }
  };

  return (
    <div className='FormCategoria-main'>
      <form className="category-form" onSubmit={handleSubmit}>
        <h1>Agregar nuevas categorías</h1>

        <div className="form-group">
          <label>Título:</label>
          <input
            type="text"
            name="titulo"
            placeholder="Ingrese el título"
            value={titulo}
            onChange={handleTituloChange}
          />
        </div>
        <div className="form-group">
          <label>Descripción:</label>
          <input
            type="text"
            name="descripcion"
            placeholder="Ingrese la descripción"
            value={descripcion}
            onChange={handleDescripcionChange}
          />
        </div>
        <div className="form-group">
          <label>Imagen:</label>
          <input
            type="text"
            name="imagen"
            placeholder="Ingrese la URL de la imagen"
            value={imagen}
            onChange={handleImagenChange}
          />
        </div>

        <button type="submit">
          <img src={pathIcons.save} alt="Imagen del botón" className='saveButton' />
          Guardar Categoría
        </button>
      </form>
    </div>
  );
};

export default FormCategoria;