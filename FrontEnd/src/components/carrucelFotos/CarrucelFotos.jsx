import React, { useState } from 'react'
import 'carrucelFotos.css'

const CarrucelFotos = () => {

  const ciclas = [
    {
      nombreBici: 'Haibike Bicicleta Eléctrica Adventr FS 9',
      imgBici: 'https://i.imgur.com/OFuVyJt.png'
    },
    {
      nombreBici: 'Wilier Bicicleta Eléctrica Triestina Hybrid GRX812',
      imgBici: 'https://i.imgur.com/oO2sILV.png'
    }
    ,
    {
      nombreBici: 'Bianchi Bicicleta Eléctrica E-Spillo Classic G Altus',
      imgBici: 'https://i.imgur.com/370InV6.png'
    }
    ,
    {
      nombreBici: 'Haibike Bicicleta Eléctrica MTB Alltrail',
      imgBici: 'https://i.imgur.com/IccTsb0.png'
    }
    ,
    {
      nombreBici: 'Youin Bicicleta Eléctrica Plegable Dakar',
      imgBici: 'https://i.imgur.com/kSLWt3a.png'
    }
    ,
    {
      nombreBici: 'Winora Bicicleta Eléctrica Tria X9 Wave',
      imgBici: 'https://i.imgur.com/UfRt2hz.png'
    }
    ,
    {
      nombreBici: 'Bianchi Bicicleta Eléctrica Gravel E-Impulso Ultegra RD-R8000 2021',
      imgBici: 'https://i.imgur.com/Ou8g9TW.png'
    }
    ,
    {
      nombreBici: 'Montana Bikes Bicicleta Elécsdftrica Carretera Gavia',
      imgBici: 'https://i.imgur.com/fNgTMbJ.png'
    }
  ]


  const [fotos, setFotos] = useState(0);
  const [cantFotos, setCantfotos] = useState(ciclas.slice(0, 3));

  const proxFoto = () => {
    const proximagen = fotos === ciclas.length - 1 ? 0 : fotos + 1;
    setFotos(proximagen);
    setCantfotos(ciclas.slice(proximagen, proximagen + 3));
  };
  const fotoAnterior = () => {
    const imagAnt = fotos === 0 ? ciclas.length - 1 : fotos - 1;
    setFotos(imagAnt);
    setCantfotos(ciclas.slice(imagAnt, imagAnt + 3));
  };

  return (
    <>
    <h3 className='titulos'>Recomendaciones</h3>
      <div className="carrusel-container">
        <button className="carrusel-button" onClick={fotoAnterior}>{'<'}</button>

        {/* creo que asi voy a renderizar solo 1 en celulares*/}
        {window.innerWidth < 768 ? (
          <div className='card-carrusel'>
            <img
              className="carrusel-image"
              src={cantFotos[0].imgBici}
              alt={cantFotos[0].nombreBici}
            />
            <h3 className='titulo-carrusel'>Nombre bicicleta</h3>
          </div>
        ) : (
          cantFotos.map((cicla, index) => (
            <div className='card-carrusel'>
              <img
                key={index}
                className="carrusel-image"
                src={cicla.imgBici}
                alt={cicla.nombreBici}
              />
              <h3 className='titulo-carrusel'>Nombre bicicleta</h3>

            </div>

          ))
        )}

        {/*
      {cantFotos.map((cicla, index) => (
        <img key={index} className="carrusel-image" src={cicla.imgBici} alt={cicla.nombreBici} />
      ))}
      */}

        <button className="carrusel-button" onClick={proxFoto}>{'>'}</button>
      </div>
    </>
  );
};

export default CarrucelFotos