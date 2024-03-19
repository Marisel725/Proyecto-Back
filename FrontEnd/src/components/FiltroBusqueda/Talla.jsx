import React, { useState } from 'react'

const Talla = () => {

    const [checkMarcados, setCheckMarcados] = useState([]);


    const [opciones, setOpciones] = useState({
        XL: false,
        L: false,
        M: false,
        S: false,
        XS: false
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

    console.log('TALLA:', checkMarcados);


    return (
        <div className='formulario-filtro'>
            <span className='titulo-filtro border-radius'>Talla</span>
            <form className='formulario-filtro-verde'>
                <label>
                    <input
                        type="checkbox"
                        name="XL"
                        checked={opciones.XL}
                        onChange={handleChange}
                    />
                    XL
                </label>
                <br />
                <label>
                    <input
                        className='border-radius'
                        type="checkbox"
                        name="L"
                        checked={opciones.L}
                        onChange={handleChange}
                    />
                    L
                </label>
                <br />
                <label>
                    <input
                        type="checkbox"
                        name="M"
                        checked={opciones.M}
                        onChange={handleChange}
                    />
                    M
                </label>
                <br />
                <label>
                    <input
                        type="checkbox"
                        name="S"
                        checked={opciones.S}
                        onChange={handleChange}
                    />
                    S
                </label>
                <br />
                <label>
                    <input
                        type="checkbox"
                        name="XS"
                        checked={opciones.XS}
                        onChange={handleChange}
                    />
                    XS
                </label>
            </form>
            <h3>Checked {checkMarcados}</h3>
        </div>
    )
}

export default Talla