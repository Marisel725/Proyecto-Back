import React, { useState, useEffect } from 'react';
import axios from "axios";
import { useParams } from 'react-router-dom';

const FormActualizar = ({ onUpdate, producto }) => {


  const { id } = producto || {};
  console.log('ID en FormActualizar:', id);//lo puse para verificar un erro y ver si me tomaba el id

  const [nombre, setNombre] = useState(producto ? producto.nombre : "");
  const [descripcion, setDescripcion] = useState(producto ? producto.descripcion : "");
  const [categoria, setCategoria] = useState(producto ? producto.categoria?.titulo : "")
  const [categorias, setCategorias] = useState([])
  const [urlImagen, setUrlImagen] = useState("");
  const [tituloImagen, setTituloImagen] = useState("");
  const [imagenes, setImagenes] = useState([]);
  const [caracteristicas, setCaracteriticas] = useState([]);
  const [caracteristicaSeleccionada, setCaracteriticaSeleccionada] = useState([]);

console.log(nombre,descripcion,categoria,id);

  //manejo de eventos
  function handleCaracteristicaChange(event) {
    const { value, checked } = event.target;
    if (checked) {
      setCaracteriticaSeleccionada([...caracteristicaSeleccionada, value]); // Cambiar a 'caracteristicasSeleccionadas'
    } else {
      setCaracteriticaSeleccionada(
        caracteristicaSeleccionada.filter((caracteristicaId) => caracteristicaId !== value)
      );
    }
  }

  const handleNombreChange = (event) => {
    setNombre(event.target.value);
  };

  const handleDescripcionChange = (event) => {
    setDescripcion(event.target.value);
  };

  const handleCategoriaChange = (event) => {
    setCategoria(event.target.value);
  };

  const handleUrlImagenChange = (event) => {
    setUrlImagen(event.target.value);
  };

  const handleTituloImagenChange = (event) => {
    setTituloImagen(event.target.value);
  };

    
  const handleSubmit = async (event) => {
    event.preventDefault();
    await actualizarProducto();
  };
  



  const handleAgregarImagen = () => {
    if (urlImagen !== "" && tituloImagen !== "") {
      const nuevaImagen = { titulo: tituloImagen, urlImg: urlImagen };
      setImagenes([...imagenes, nuevaImagen]);
    } else {
      alert("Por favor, ingrese tanto la URL como el título de la imagen.");
    }
  };

  //en esta envioo solicitud apra modificar y es la que me falla 

  const actualizarProducto = async () => {
    
    const productoActualizado ={
      "id": id,
      "nombre": nombre,
      "descripcion": descripcion,
      "categoria": categoria,
      "caracteristicas": caracteristicaSeleccionada
    };

  console.log("NEW",productoActualizado);

    try {
      const response = await axios.put(
        `https://backendebikerent-production.up.railway.app/productos/modificar`,
        productoActualizado
      );
  
      console.log("Producto actualizado:", response.data);
      alert("PRODUCTO ACTUALIZADO CORRECTAMENTE");
  
      window.location.reload();
      
    } catch (error) {
      console.error("Error al actualizar el producto:", error);
      console.log(error); 
      alert("ERROR AL ACTUALIZAR PRODUCTO" + error);
      console.log("ERROR AL ACTUALIZAR",productoActualizado);
    }
  };
    //para traer lista de categorias 
    useEffect(() => {
      async function fetchCategorias() {
        try {
          const response = await fetch('https://backendebikerent-production.up.railway.app/categorias/listar');
          if (!response.ok) {
            throw new Error('Error al cargar las categorías');
          }
          const data = await response.json();
          setCategorias(data);
        } catch (error) {
          console.error('Error:', error);
        }
      }
  
      fetchCategorias();
    }, []);

  //CARECTERISTICAS!!
  useEffect(() => {
    async function fetchCaracteristicas() {
      try {
        const response = await fetch('https://backendebikerent-production.up.railway.app/caracteristicas/listar');
        if (!response.ok) {
          throw new Error('Error al cargar las categorías');
        }
        const data = await response.json();
        setCaracteriticas(data);
      } catch (error) {
        console.error('Error:', error);
      }
    }

    fetchCaracteristicas();
  }, []);

  return (
    <div>
      <h1>Actualizar Producto</h1>
      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label>Nombre:</label>
          <input
            type="text"
            name="nombre"
            value={nombre}
            onChange={handleNombreChange}
          />
        </div>
        <div className="form-group">
          <label>Descripción:</label>
          <input
            type="text"
            name="descripcion"
            value={descripcion}
            onChange={handleDescripcionChange}
          />
        </div>
        <div className="form-group">
          <label>Categoria:</label>
          <select
            id="categoria"
            name="categoria"
            value={categoria}
            onChange={handleCategoriaChange}
          >
            <option value="">Selecciona una categoría</option>
            {categorias.map((categoria) => (
              <option key={categoria.id} value={categoria.titulo}>
                {categoria.titulo}
              </option>
            ))}
          </select>
        </div>

        <div className="form-group">
          <label>Características:</label>
          
          {Array.isArray(caracteristicas) && caracteristicas.map((caracteristica) => (
            <div key={caracteristica.id}>
              <input
                type="checkbox"
                id={`caracteristica-${caracteristica.id}`}
                name={`caracteristica-${caracteristica.nombre}`}
                value={caracteristica.nombre}
                checked={caracteristicaSeleccionada.includes(caracteristica.nombre)}
                onChange={handleCaracteristicaChange}
              />
              <label htmlFor={`caracteristica-${caracteristica.id}`}>{caracteristica.nombre}</label>
            </div>
          ))}
          
        </div>

        {/* estos no los inclui en el form porque segun el back modifica sin imegnes y titulo*/}

        {/* <div className="form-group">
          <label>Titulo Imagen:</label>
          <input
            type="text"
            name="tituloImagen"
            value={tituloImagen}
            onChange={handleTituloImagenChange}
          />
        </div>
        <div className="form-group">
          <label>Url Imagen:</label>
          <input
            type="text"
            name="urlImagen"
            value={urlImagen}
            onChange={handleUrlImagenChange}
          />
        </div> */}

        <button type="submit" onClick={actualizarProducto}>
          Actualizar producto
        </button>
      </form>
    </div>
  );
};

export default FormActualizar;
