import React, { useState } from 'react'
import Buscador from '../formBuscador/Buscador'
import CardBicicleta from '../cardCiclasHome/CardBicicleta'
import Categorias from '../categorias/Categorias'
import CarrucelFotos from '../carrucelFotos/CarrucelFotos'

const Home = () => {


  const [productoSeleccionado, setProductoSeleccionado] = useState(null);
  const [mostrarDetalleProducto, setMostrarDetalleProducto] = useState(false);

  const handleProductoSeleccionado = (producto) => {
    setProductoSeleccionado(producto);
    setMostrarDetalleProducto(true)
  };
  const handleCerrarDetalle = () => {
    console.log("Cerrando detalle...");
    setMostrarDetalleProducto(false);
  };




  return (
    <div className='container-middle'>
      <div id="top" ></div>
      <Buscador />
      <Categorias />
      <CarrucelFotos onProductoSeleccionado={handleProductoSeleccionado}/>
      
      {/*<CardBicicleta onProductoSeleccionado={handleProductoSeleccionado} />*/}

      {/*mostrarDetalleProducto && (
        <DetalleProducto
          srcImagen={productoSeleccionado.imgBici}
          nombreBici={productoSeleccionado.nombreBici}
          onClose={handleCerrarDetalle}
      />)*/}
    </div>
  )
}

export default Home