import React, { useEffect } from 'react'
import axios from "axios";

const Inicializador = () => {

  useEffect(() => {
    const inicializarProductos = async () => {
      try {

        const ciclas = [{
          nombreBici: 'Haibike Bicicleta Eléctrica Adventr FS ',
          imgBici: 'https://i.imgur.com/OFuVyJt.png'
        }, {
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
          nombreBici: 'Montana Bikes Bicicleta Eléctrica Carretera Gavia',
          imgBici: 'https://i.imgur.com/fNgTMbJ.png'
        }]

        const descripcion = "Cuadro: Monoscocca Carbon"

        for (const cicla of ciclas) {

          const nuevaCicla = {
            nombre: cicla.nombreBici,
            descripcion: descripcion,
            imagenes: [{
              titulo: cicla.nombreBici,
              urlImg: cicla.imgBici
            }],
            categoria: "Ruta",
            caracteristicas: ["GoPro"]
          };
          

          await axios.post('http://localhost:8080/productos/registrar', nuevaCicla);
          console.log('Producto inicializado:', cicla);
        }

        console.log('Todos los productos inicializados correctamente.');
      } catch (error) {
        console.error('Error al inicializar productos:', error);
      }
    };

    inicializarProductos();
  }, []);

  return (
    <></>
  )
}

export default Inicializador