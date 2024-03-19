import React from "react";
import './Form1.css';
import Input1 from "../Input1/Input1";
import { pathIcons } from "../utils/global.context";

/*Form genérico tipo 1*/
const Form1 = ({inputs, handleForm, textSubmit, iconSubmit, textSuccesfulService, textErrorService}) =>{

    const handleSubmit = (e) =>{
        e.preventDefault();
        handleForm(e);
    }

    return (
        <form onSubmit={handleSubmit} className="Form1-main">
            {/*Campos inpuit a renderizar seún array pasado al componente como prop*/}
            {
                inputs.map(
                    (item, idx) =>(
                        <Input1 key={idx + 1}  idInput={item.idInput} textInput={item.textInput} nameInput={item.nameInput}
                                placeHolderInput={item.placeHolderInput} typeInput={item.typeInput} maxLengthInput={item.maxLengthInput}
                                required={item.required} errorTextInput={item.errorTextInput}
                        />
                    )
                )
            }
             {/*Mensaje fijo de obligatoriedad de campos */}
            <br/>
            <b style={{fontSize: '15px'}}>Los campos marcados con </b> 
            <span style={{color: 'red'}}>*</span> 
            <b> son obligatorios</b>
            <br />

             {/*Mensaje que indica si el consumo del servicio fue exitoso o tuco erores */}
            {textErrorService &&
                <span className="Form1-error-service">
                    <img src={pathIcons.error} alt={'Error service'} />
                    {textErrorService}
                </span>
            }
            {textSuccesfulService &&
                <span className="Form1-succesful-service">
                    <img src={pathIcons.ok1} alt={'Succes service'} />
                    {textSuccesfulService}
                </span>
            }
            <br />

             {/*Boton tipo Submit del formulario con icono asociado pasado como prop */}
            <div className="Form1-submit-button-section">
                <button type="submit" name='submitForm'>
                    <img src={iconSubmit} alt={textSubmit} />
                    {textSubmit}
                </button>
            </div>
        </form>
    )
}

export default Form1;