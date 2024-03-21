import React from 'react'
import { useState,useEffect } from 'react';
import { BiSearch } from 'react-icons/bi';
import './formBuscador.css'

const Buscador = () => {

    const [currentIndex, setCurrentIndex] = useState(0);
    const images = [
      "url('https://i.imgur.com/ZacZoMt.jpg')",
      "url('https://i.imgur.com/rKkwnEt.jpg')",
      "url('https://i.imgur.com/4SoFTpo.jpg')",      
    ];
  
    useEffect(() => {
      const interval = setInterval(() => {
        setCurrentIndex((prevIndex) => (prevIndex + 1) % images.length);
      }, 5000);
  
      return () => clearInterval(interval);
    }, [images.length]);
  
    const containerStyle = {
      backgroundImage: images[currentIndex],
    };

    return (
        <>
            <div className='container-buscador' style={containerStyle}>

                <form className='form-buscador'>
                <h1 className='titulo-slogan'>Descubre la bicicleta que te llevará a nuevos destinos.</h1>
                    <label htmlFor="buscador"></label>
                    <div className="search-container">
                        <BiSearch className="search-icon" />
                        <input className="forms-busca-tu-bici"
                            type="text"
                            placeholder="Buscá tu bicicleta favorita"
                            id="nombre"
                        />
                    </div>
                    
                </form>
                
            </div>
        </>
    )
}

export default Buscador