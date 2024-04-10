import React, { useState, useEffect } from 'react';
import axios from "axios";
import { pathIcons } from '../../../utils/global.context';
import './ProductosRegistrar.css';

const ProductosRegistrar = ({ onSubmit }) => {

  const [nombre, setNombre] = useState("");
  const [descripcion, setDescripcion] = useState("");
  const [categoria, setCategoria] = useState("");
  const [categorias, setCategorias] = useState([])
  const [urlImagen, setUrlImagen] = useState([""]);
  const [imagenes, setImagenes] = useState([]);
  const [caracteristicas, setCaracteriticas] = useState([]);
  const [caracteristicaSeleccionada, setCaracteriticaSeleccionada] = useState([]);
  const [nombreCard, setNombreCard] = useState([]);


  const handleUrlImagenChange = (event, index) => {
    const newImagenes = [...urlImagen];
    newImagenes[index] = event.target.value;
    setUrlImagen(newImagenes);
  };

  //para que no tire error el boton de "agregar nuevo campo"
  const agregarCampoImagen = () => {
    setUrlImagen([...urlImagen, '']);
  };


  const handleNombreChange = (event) => {
    setNombre(event.target.value);
  };

  const handleCategoriaChange = (event) => {
    setCategoria(event.target.value);
  };

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

  const handleDescripcionChange = (event) => {
    setDescripcion(event.target.value);
  };

  const handleAgregarImagen = () => {
    if (urlImagen !== "" && nombre !== "") {
      const nuevasImagenes = urlImagen.map((url) => ({ titulo: nombre, urlImg: url }));
      setImagenes([...imagenes, ...nuevasImagenes]);

    } else {
      alert("Por favor, ingrese tanto la URL como el nombre del producto.");
    }
  };


  const handleSubmit = async (event) => {
    event.preventDefault();
    crearProducto();
  };

  const crearProducto = async () => {
    const nuevoProducto = {
      nombre: nombre,
      descripcion: descripcion,
      imagenes: imagenes,
      categoria: categoria,
      caracteristicas: caracteristicaSeleccionada
    };
    console.log(nuevoProducto)

    try {
      const response = await axios.post(
        "https://backendebikerent-production.up.railway.app/productos/registrar",
        nuevoProducto
      );
      console.log("Producto guardado:", response.data);
      const productoCreado = JSON.stringify(response.data)
      alert("PRODUCTO CREADO CORRECTAMENTE" + productoCreado)

      setNombreCard({ nombre })

      setNombre("");
      setCategoria("");
      setDescripcion("");
      setImagenes([]);
      setUrlImagen([""]);
      setCaracteriticaSeleccionada("");
      setCaracteriticas("");

    } catch (error) {
      console.error("Error al guardar el producto:", error, nuevoProducto);
      alert("ERROR AL CREAR PRODUCTO" + error)
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

    <div className='ProductoRegistrar-main'>

      <form className="product-form" onSubmit={handleSubmit}>

        <h1>Agregar nuevos productos</h1>

        <div className="form-group">
          <label>Nombre:</label>
          <input
            type="text"
            name="name"
            placeholder='ingrese nombre'
            value={nombre}
            onChange={handleNombreChange}
          />
        </div>
        <div className="form-group">
          <label>Descripcion:</label>
          <input
            type="text"
            name="descripcion"
            placeholder='Descripcion de producto'
            value={descripcion}
            onChange={handleDescripcionChange}
          />
        </div>

        <div>
          <label>Url Imagen:</label>
          {urlImagen.map((imagen, index) => (
            <div key={index} className="form-group">
              <input
                type="text"
                name={`Url imagen ${index + 1}`}
                placeholder={`Url imagen ${index + 1}`}
                value={imagen}
                onChange={(event) => handleUrlImagenChange(event, index)}
              />
              {index === urlImagen.length - 1 && (
                <button onClick={agregarCampoImagen}>Agregar otra URL</button>
              )}
            </div>
          ))}

          {/*Esto lo puse porque no entendia porque no funcionaba, 
cualquier cosa se puede sacarr*/}
          <div>
            <h2>Url imagenes cargadas:</h2>
            <ul>
              {urlImagen.map((url, index) => (
                <li key={index}>{url}</li>
              ))}
            </ul>
          </div>
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
          <label>Características:

            {Array.isArray(caracteristicas) && caracteristicas.map((caracteristica) => (
              <div key={caracteristica.id} className='caracteristicas-coneiner'>
                <input
                  type="checkbox"
                  id={`caracteristica-${caracteristica.id}`}
                  name={`caracteristica-${caracteristica.nombre}`}
                  value={caracteristica.nombre}
                  checked={caracteristicaSeleccionada.includes(caracteristica.nombre)}
                  onChange={handleCaracteristicaChange}
                />
                <span htmlFor={`caracteristica-${caracteristica.id}`}>{caracteristica.nombre}</span>
              </div>
            ))}
          </label>
        </div>

        <button type="submit" onClick={handleAgregarImagen}>
          <img src={pathIcons.save} alt="Imagen del botón" className='saveButton' />
          Guardar Producto
        </button>
      </form>
    </div>

  );
};

export default ProductosRegistrar;
