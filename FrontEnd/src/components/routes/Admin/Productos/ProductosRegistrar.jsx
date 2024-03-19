import React, { useState, useEffect } from 'react';
/*import ImageUploader from 'react-images-upload';*/
import axios from "axios";
import { pathIcons } from '../../../utils/global.context';
import './ProductosRegistrar.css';

const ProductosRegistrar = ({ onSubmit }) => {

  const [nombre, setNombre] = useState("");
  const [descripcion, setDescripcion] = useState("");
  const [categoria, setCategoria] = useState("");
  const [categorias, setCategorias] = useState([])
  const [urlImagen, setUrlmagenes] = useState("");
  const [tituloImagen, setTituloImagen] = useState("");
  const [imagenes, setImagenes] = useState([]);
  const [caracteristicas, setCaracteriticas] = useState([]);
  const [caracteristicaSeleccionada, setCaracteriticaSeleccionada] = useState([]);
  const [nombreCard, setNombreCard] = useState([]);
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

  const handleUrlImagenChange = (event) => {
    setUrlmagenes(event.target.value)
  }
  const handleTituloImagenChange = (event) => {
    setTituloImagen(event.target.value)
  }

  const handleAgregarImagen = () => {
    if (urlImagen !== "" && tituloImagen !== "") {
      const nuevaImagen = { titulo: tituloImagen, urlImg: urlImagen };
      setImagenes([...imagenes, nuevaImagen]);
    } else {
      alert("Por favor, ingrese tanto la URL como el título de la imagen.");
    }
  };

  /*const handleImagenesChange = (event) => {
    const urlImagen = Array.from(event.target.urlImagen);
    setImagenes(urlImagen);
  };*/

  const handleSubmit = async (event) => {
    event.preventDefault();
    crearProducto();
  };

  const crearProducto = async () => {
    const nuevoProducto = {
      nombre: nombre,
      descripcion: descripcion,
      imagenes: imagenes,
      /*
      imagenes: imagenes.map((imagen) => ({
        urlImagen: URL.createObjectURL(imagen),
      })),*/
      categoria: categoria,
      caracteristicas: caracteristicaSeleccionada
    };
    console.log(nuevoProducto)

    try {
      const response = await axios.post(
        "http://localhost:8080/productos/registrar",
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
      setUrlmagenes("");
      setTituloImagen("");
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
        const response = await fetch('http://localhost:8080/categorias/listar');
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
        const response = await fetch('http://localhost:8080/caracteristicas/listar');
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
        <div className="form-group">
          <label>Titulo Imagen:</label>
          <input
            type="text"
            name="Titulo Imagen"
            placeholder='Titulo Imagen'
            value={tituloImagen}
            onChange={handleTituloImagenChange}
          />
        </div>
        <div className="form-group">
          <label>Url Imagen:</label>
          <input
            type="text"
            name="Url imagen"
            placeholder='Url imagen'
            value={urlImagen}
            onChange={handleUrlImagenChange}
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



        {/*  <div className="form-group">
        <label>Subir imágenes:</label>
        <ImageUploader
          withIcon={true}
           buttonText="Seleccionar imágenes"
          onChange={handleImageUpload}
          imgExtension={['.jpg', '.gif', '.png', '.gif', '.jpeg']}
          maxFileSize={5242880} // 5MB
        />
      </div>*/}

        <button type="submit" onClick={handleAgregarImagen}>
          <img src={pathIcons.save} alt="Imagen del botón" className='saveButton' />
          Guardar Producto
        </button>
      </form>
    </div>

  );
};

export default ProductosRegistrar;
