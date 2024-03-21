import React, { useState } from 'react';
import { IoIosArrowBack } from "react-icons/io";
import { Link } from 'react-router-dom';

const VerMasFotos = ({ srcImagen, nombreBici, onClose }) => {
    const ciclas = [
        {
            nombreBici: 'Haibike Bicicleta Eléctrica Adventr FS 9',
            imgBici: 'https://i.imgur.com/OFuVyJt.png'
        },
        {
            nombreBici: 'Haibike Bicicleta Eléctrica Adventr FS 9',
            imgBici: 'https://i.imgur.com/OFuVyJt.png'
        },
        {
            nombreBici: 'Haibike Bicicleta Eléctrica Adventr FS 9',
            imgBici: 'https://i.imgur.com/OFuVyJt.png'
        }
    ];

    const [fotos, setFotos] = useState(0);
    const [cantFotos, setCantFotos] = useState(ciclas.slice(0, 3));

    const proxFoto = () => {
        const proximagen = fotos === ciclas.length - 1 ? 0 : fotos + 1;
        setFotos(proximagen);
        setCantFotos(ciclas.slice(proximagen, proximagen + 3));
    };

    const fotoAnterior = () => {
        const imagAnt = fotos === 0 ? ciclas.length - 1 : fotos - 1;
        setFotos(imagAnt);
        setCantFotos(ciclas.slice(imagAnt, imagAnt + 3));
    };

    return (
        <>

            <Link to="/">
                <button className='button button-detalle'>
                    Volver Atras
                </button>
            </Link>

            <h3 className='titulos'>{ciclas[0].nombreBici}</h3>
            <div className="carrusel-container">
                <button className="carrusel-button" onClick={fotoAnterior}>{'<'}</button>
{/*
               
                {window.innerWidth < 768 ? (
                    <div className='card-carrusel'>
                        <img
                            key={cantFotos[0].nombreBici}
                            className="carrusel-image"
                            src={cantFotos[0].imgBici}
                            alt={cantFotos[0].nombreBici}
                        />
                    </div>
                ) : (
                    cantFotos.map((cicla, index) => (
                        <div className='card-carrusel' key={cicla.nombreBici + index}>
                            <img
                                key={index}
                                className="carrusel-image"
                                src={cicla.imgBici}
                                alt={cicla.nombreBici}
                            />
                        </div>
                    ))

                )}*/}
                <div className='card-carrusel'>
                        <img
                            key={cantFotos[0].nombreBici}
                            className="ver-mas-fotos"
                            src={cantFotos[0].imgBici}
                            alt={cantFotos[0].nombreBici}
                        />
                    </div>

                <button className="carrusel-button" onClick={proxFoto}>{'>'}</button>
            </div>
        </>
    );
}

export default VerMasFotos;