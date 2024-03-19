import React, { useState } from 'react'

const Capacidad = () => {

    const [checkMarcados, setCheckMarcados] = useState([]);

    const [opciones, setOpciones] = useState({
        quinientoscuatro: false,
        quinientos: false,
        dosciencuentaydos: false,
        doscincuenta: false,
        dostreintayseis: false
    });

    const handleChange = (event) => {
        const { name, checked } = event.target;
        setOpciones(prevState => ({
            ...prevState,
            [name]: checked
        }));

        if (checked) {
            setCheckMarcados(prevState => [...prevState, name]);
        } else {
            setCheckMarcados(prevState => prevState.filter(item => item !== name));
        }
    };
    console.log('CAPACIDAD:', checkMarcados);
  


    return (
        <div className='formulario-filtro'>
            <span className='titulo-filtro border-radius'>Capacidad</span>
            <form className='formulario-filtro-verde'>
                <label>
                    <input
                        type="checkbox"
                        name="504Wh"
                        checked={opciones.Quinientoscuatro}
                        onChange={handleChange}
                    />
                    504Wh
                </label>
                <br />
                <label>
                    <input
                        className='border-radius'
                        type="checkbox"
                        name="500Wh"
                        checked={opciones.Quinientos}
                        onChange={handleChange}
                    />
                    500Wh
                </label>
                <br />
                <label>
                    <input
                        type="checkbox"
                        name="252Wh"
                        checked={opciones.Dosciencuentaydos}
                        onChange={handleChange}
                    />
                    252Wh
                </label>
                <br />
                <label>
                    <input
                        type="checkbox"
                        name="250Wh"
                        checked={opciones.Doscincuenta}
                        onChange={handleChange}
                    />
                    250Wh
                </label>
                <br />
                <label>
                    <input
                        type="checkbox"
                        name="236Wh"
                        checked={opciones.Dostreintayseis}
                        onChange={handleChange}
                    />
                    236Wh
                </label>
            </form>
            <h3>Checked {checkMarcados}</h3>
        </div>
    )
}

export default Capacidad