import React, { useEffect } from 'react';
import axios from "axios";

const Inicializador = () => {
  useEffect(() => {
    const inicializarProductos = async () => {
      try {
        const ciclas = [
          {
            nombreBici: 'Haibike Bicicleta Eléctrica Advent1',
            imgBici: 'https://i.imgur.com/OFuVyJt.png'
          },
          {
            nombreBici: 'Wilier Bicicleta Eléctrica Triestina Hybrid GRX1',
            imgBici: 'https://i.imgur.com/oO2sILV.png'
          },
          {
            nombreBici: 'Bianchi Bicicleta Eléctrica E-Spillo Classic G1',
            imgBici: 'https://i.imgur.com/370InV6.png'
          },
          {
            nombreBici: 'Haibike Bicicleta Eléctrica MTB1',
            imgBici: 'https://i.imgur.com/IccTsb0.png'
          },
          {
            nombreBici: 'Youin Bicicleta Eléctrica Plegable1',
            imgBici: 'https://i.imgur.com/kSLWt3a.png'
          },
          {
            nombreBici: 'Winora Bicicleta Eléctrica Tria X91',
            imgBici: 'https://i.imgur.com/UfRt2hz.png'
          },
          {
            nombreBici: 'Bianchi Bicicleta Eléctrica Gravel E-Impulso1 ',
            imgBici: 'https://i.imgur.com/Ou8g9TW.png'
          },
          {
            nombreBici: 'Montana Bikes Bicicleta Eléctrica Carretera1',
            imgBici: 'https://i.imgur.com/fNgTMbJ.png'
          }
        ];

        const descripcion = "Cuadro: Monoscocca Carbon";

        for (const cicla of ciclas) {
          const nuevaCicla = {
            nombre: cicla.nombreBici,
            descripcion: descripcion,
            imagenes: [{ titulo: cicla.nombreBici, urlImg: cicla.imgBici }],
            categoria: 'Urbana',
            caracteristicas: ['Ambulancia']
          };
          

          console.log("Datos a enviar al servidor:", nuevaCicla);
          await axios.post('http://localhost:8080/productos/registrar', nuevaCicla); 
        }
      } catch (error) {
        console.log(error);
      }
    };

    /*inicializarProductos();*/
  }, []);

  return null; 
};

export default Inicializador;