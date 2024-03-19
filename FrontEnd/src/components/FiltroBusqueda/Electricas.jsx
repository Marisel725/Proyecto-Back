import React, { useState } from 'react'

const Electricas = () => {

    const [checkMarcados, setCheckMarcados] = useState([]);

    const [opciones, setOpciones] = useState({
        urbana: false,
        plegable: false,
        montana: false,
        carretera: false,
        carga: false
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
    console.log('ELECTRICAS:', checkMarcados);
    


    return (
        <>
            <div className='formulario-filtro'>
                <span className='titulo-filtro border-radius'>Eléctricas</span>
                <form className='formulario-filtro-verde'>
                    <label>
                        <input
                            type="checkbox"
                            name="urbana"
                            checked={opciones.urbana}
                            onChange={handleChange}
                        />
                        Urbana
                    </label>
                    <br />
                    <label>
                        <input
                            className='border-radius'
                            type="checkbox"
                            name="plegable"
                            checked={opciones.plegable}
                            onChange={handleChange}
                        />
                        Plegable
                    </label>
                    <br />
                    <label>
                        <input
                            type="checkbox"
                            name="montana"
                            checked={opciones.montana}
                            onChange={handleChange}
                        />
                        Montaña
                    </label>
                    <br />
                    <label>
                        <input
                            type="checkbox"
                            name="Carretera"
                            checked={opciones.carretera}
                            onChange={handleChange}
                        />
                        Carretera
                    </label>
                    <br />
                    <label>
                        <input
                            type="checkbox"
                            name="Carga"
                            checked={opciones.carga}
                            onChange={handleChange}
                        />
                        Carga
                    </label>

                </form>
                <h3>Checked {checkMarcados}</h3>
            </div>
        </>
    )
}

export default Electricas