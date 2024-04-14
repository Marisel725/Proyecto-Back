import React, { useContext, useState, useEffect } from 'react'
import './formBuscador.css'
import Calendar from 'react-calendar';
import 'react-calendar/dist/Calendar.css'
import { ContextGlobal, pathIcons, urlBackend } from '../utils/global.context';
import axios from 'axios';
import Whatsapp from '../Buttons/Whatsapp';

const labelDayWeek = [
    'Dom',
    'Lun',
    'Mar',
    'Mié',
    'Jue',
    'Vie',
    'Sab'
];

const labelMonth = [
    'ene',
    'feb',
    'mar',
    'abr',
    'may',
    'jun',
    'jul',
    'ago',
    'sep',
    'oct',
    'nov',
    'dic'
];

const pad = (number) => {
    if (number < 10) {
        return "0" + number;
    }
    return number;
};

const Buscador = () => {
    const { contexto, setContexto } = useContext(ContextGlobal);

    const [rangoFechas, setRangoFechas] = useState('');
    const [rangoFechasServicio, setRangoFechasServicio] = useState([]);
    const [activarCalendario, setActivarCalendario] = useState(false);

    const [mensajeOk, setMensajeOk] = useState('');
    const [mensajeError, setMensajeError] = useState('');

    const rangoHandle = (value, event) => {
        let fecha1 = new Date(value[0]);
        fecha1 = labelDayWeek[fecha1.getDay()] + ', ' + fecha1.getDate() + ' ' + labelMonth[fecha1.getMonth()];
        let fecha2 = new Date(value[1]);
        fecha2 = labelDayWeek[fecha2.getDay()] + ', ' + fecha2.getDate() + ' ' + labelMonth[fecha2.getMonth()];
        setRangoFechas((fecha1 + ' - ' + fecha2));
        setRangoFechasServicio([new Date(value[0]), new Date(value[1])]);
    };

    const validateForm = (form) => {
        let isValidForm = true;

        if (!form.rango_fechas) {
            isValidForm = false;
            setMensajeError('Selecciona por favor que días buscas');
        }
        return isValidForm;
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        setMensajeOk('');
        setMensajeError('');
        const formIniciarSesion = e.target;
        const formIniciarSesionData = new FormData(formIniciarSesion);
        const formJson = Object.fromEntries(formIniciarSesionData.entries());

        const isValidForm = validateForm(formJson);

        if (isValidForm) {
            consumeService(formJson, formIniciarSesion);
        }
    }

    const consumeService = async (formJson, formHTML) => {

        const endPoint = 'productos/buscarProductoDisponible';
        const url = urlBackend + endPoint;

        const nombre = formJson.nombre_producto.toUpperCase().trim();

        const fecha1 = rangoFechasServicio[0].getFullYear() + '-' +
            pad(rangoFechasServicio[0].getMonth() + 1) + '-' +
            pad(rangoFechasServicio[0].getDate());

        const fecha2 = rangoFechasServicio[1].getFullYear() + '-' +
            pad(rangoFechasServicio[1].getMonth() + 1) + '-' +
            pad(rangoFechasServicio[1].getDate());

        const payload = {
            nombreProducto: nombre,
            fechaInicio: fecha1,
            fechaFin: fecha2
        };

        console.log(payload);

        try {
            const response = await axios.post(url, payload);
            const numeroResultados = response.data.length;
            if (numeroResultados > 0) {
                setMensajeOk('Se han encontrado ' + response.data.length + ' resultados');
            } else {
                setMensajeOk('No se han encontrado resultados')
            }

            setContexto({ ...contexto, arrayCiclas: response.data })
            //formHTML.reset();
        } catch (error) {
            setMensajeError(error.response.data.message);
        }
    }

    const handlerContainerBuscador = (e) => {
        const parent = e.target.closest('#buscador-seccion-calendario');
        if (parent === null) {
            if (activarCalendario) {
                setActivarCalendario(false);
            }
        }
    };



    //imagen background 
    const [currentIndex, setCurrentIndex] = useState(0);
    const [fraseIndex, setFraseIndex] = useState(0);

    const images = [
        "url('https://i.imgur.com/ZacZoMt.jpg')",
        "url('https://i.imgur.com/rKkwnEt.jpg')",
        "url('https://i.imgur.com/4SoFTpo.jpg')",
    ];
    const frases = [
        "Encuentra la bicicleta perfecta para cada aventura.",
        "Explora el mundo en dos ruedas y comienza tu viaje.",
        "Encuentra tu bicicleta ideal y comienza tu aventura por el mundo.",
        "Descubre nuevas rutas y destinos con la bicicleta perfecta.",
        "Inicia tu viaje y encuentra la bicicleta ideal para cada experiencia."
    ];

    useEffect(() => {
        const interval = setInterval(() => {
            setCurrentIndex((prevIndex) => (prevIndex + 1) % images.length);
            setFraseIndex((prevIndex) => (prevIndex + 1) % frases.length);

        }, 3000);
        return () => clearInterval(interval);
    }, []);

    const fraseInicio = frases[fraseIndex]
    const containerStyle = {
        backgroundImage: images[currentIndex],
    };

    const tileContentCalendario = ({ date, view }) => {
        let fechaActual = new Date();
        fechaActual.setHours(0, 0, 0);
        fechaActual.setMilliseconds(0);
        let returnX = false;
        switch (view) {
            case 'month':
                if (date < fechaActual) {
                    returnX = true;
                }
                break;
            case 'year':
                if (date < fechaActual && (date.getMonth() < fechaActual.getMonth())) {
                    returnX = true;
                }
                break;
        }

        if (returnX) {
            return <span>X</span>
        }

    }

    return (
        <>
            <div className='container-buscador' onClick={handlerContainerBuscador} style={containerStyle}>
                <h1 className='titulo-slogan'>{fraseInicio}</h1>
                <form className='form-buscador' onSubmit={handleSubmit}>
                    <input className="forms-busca-tu-bici"
                        type="text"
                        id="nombre"
                        placeholder='¿Cómo la buscas?'
                        name='nombre_producto'
                    />
                    <div></div>
                    <div className='buscador-seccion-rango-fechas'
                        onClick={() => (setActivarCalendario(!activarCalendario))}>
                        <img src={pathIcons.calendario} alt='Buscar' />
                        <input id='id_fechas' type='text' name='rango_fechas' value={rangoFechas} readOnly={true}
                            placeholder='Desde - Hasta'
                        />
                    </div>
                    <button type="submit" name='submitForm'>
                        <img src={pathIcons.lupa} alt='Buscar' />
                        Buscar
                    </button>
                </form>

                {mensajeOk &&
                    <div className='buscador-tooltip-ok'>
                        <span>{mensajeOk}</span>
                    </div>
                }

                {mensajeError &&
                    <div className='buscador-tooltip-error'>
                        <span>{mensajeError}</span>
                    </div>
                }
                <div className={'buscador-seccion-calendario ' +
                    (activarCalendario ? 'calendario-activo' : '')}
                    id='buscador-seccion-calendario'
                >
                    <Calendar showDoubleView={true} locale='es'
                        showNeighboringMonth={false}
                        selectRange={true} returnValue='range'
                        onChange={rangoHandle}
                        minDate={new Date()}
                        tileContent={tileContentCalendario}
                    />
                </div>
            </div>
        </>
    )
}

export default Buscador
