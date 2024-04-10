import React, { useState } from "react";
import './RegistrarUsuario.css';
import Form1 from "../Form1/Form1";
import {pathIcons, urlBackend} from '../../components/utils/global.context';
import axios from "axios";
import { useNavigate } from "react-router-dom";
/*

*/
/*Componente de ruta de creación/alta de usuarios*/
const RegistrarUsuario = () =>{

    /*Estados de validación de campos*/
    const [errorNombre, setErrorNombre] = useState('');
    const [errorApellido, setErrorApellido] = useState('');
    const [errorCorreo, setErrorCorreo] = useState('');
    const [errorContrasena1, setErrorContrasena1] = useState('');
    const [errorContrasena2, setErrorContrasena2] = useState('');

    /*Estados de resultado de consumo de servicio */
    const [errorConsumeService, setErrorConsumeService] = useState('');
    const [okConsumeService, setOkConsumeService] = useState('');

     /*Navigate para redireccionar a home en caso de login exitoso */
     const navigate = useNavigate();

    const inputs= [
        {
            idInput: 'identificacionId',
            textInput: 'DNI/Pasaporte/CC:',
            nameInput: 'identificacion',
            placeHolderInput: 'Este campo puede ser alfanumérico',
            typeInput: 'text',
            maxLengthInput: '20',
            required: 'required',
            errorTextInput: ''
        },
        {
            idInput: 'nombreId',
            textInput: 'Nombre:',
            nameInput: 'nombre',
            placeHolderInput: 'Este campo solo admite letras',
            typeInput: 'text',
            maxLengthInput: '50',
            required: 'required',
            errorTextInput: errorNombre
        },
        {
            idInput: 'apellidoId',
            textInput: 'Apellido:',
            nameInput: 'apellido',
            placeHolderInput: 'Este campo solo admite letras',
            typeInput: 'text',
            maxLengthInput: '50',
            required: 'required',
            errorTextInput: errorApellido
        },
        {
            idInput: 'correoId',
            textInput: 'Correo electrónico:',
            nameInput: 'correo',
            placeHolderInput: 'email@dominio.com',
            typeInput: 'email',
            maxLengthInput: '80',
            required: 'required',
            errorTextInput: errorCorreo
        },
        {
            idInput: 'telefonoId',
            textInput: 'Telefono:',
            nameInput: 'telefono',
            placeHolderInput: 'Este campo solo admite números',
            typeInput: 'number',
            maxLengthInput: '',
            required: 'required',
            errorTextInput: ''
        },
        {
            idInput: 'contrasena1Id',
            textInput: 'Contraseña:',
            nameInput: 'contrasena1',
            placeHolderInput: '10 caracteres',
            typeInput: 'password',
            maxLengthInput: '10',
            required: 'required',
            errorTextInput: errorContrasena1
        },
        {
            idInput: 'contrasena2Id',
            textInput: 'Confirmar contraseña:',
            nameInput: 'contrasena2',
            placeHolderInput: 'Repite y asegúrate que es la misma contraseña',
            typeInput: 'password',
            maxLengthInput: '10',
            required: 'required',
            errorTextInput: errorContrasena2
        }
    ];
    
    const validateForm = (form) =>{
        setErrorNombre('');
        setErrorApellido('');
        setErrorCorreo('');
        setErrorContrasena1('');
        setErrorContrasena2('');

        let isValidForm = true;
        let expRegularSoloTexto = /^[A-Z]+$/i;
        let expRegularCorreo = /^\w+([.-_+]?\w+)*@\w+([.-]?\w+)*(\.\w{2,10})+$/;
    
        /*Validación de campo nombre */
        if(!form.nombre){
            setErrorNombre('Ingresa un nombre');
            isValidForm = false;
        }else{
            if(!expRegularSoloTexto.test(form.nombre)){
                setErrorNombre('Nombre inválido');
                isValidForm = false;
            }
        }

        /*Validación de campo apellido */
        if(!form.apellido){
            setErrorApellido('Ingresa un apellido');
            isValidForm = false;
        }else{
            if(!expRegularSoloTexto.test(form.apellido)){
                setErrorApellido('Apellido inválido');
                isValidForm = false;
            }
        }

        /*Validación de Correo */
        if(!expRegularCorreo.test(form.correo)){
            setErrorCorreo('Correo inválido');
            isValidForm = false;
        }

        /*Validación contraseña 1 */
        if(!form.contrasena1){
            setErrorContrasena1('Debes ingresar una contraseña');
            isValidForm = false
        }else{
            /*Validación de confirmación de la contraseña */
            if(form.contrasena1 != form.contrasena2){
                setErrorContrasena2('Las contraseña no es la misma');
                isValidForm = false;
            }
        }

        return isValidForm;
    };

    const consumeService = async (formJson, formHTML) =>{
        const payload = {
            nombre: formJson.nombre,
            apellido: formJson.apellido,
            mail: formJson.correo,
            telefono: formJson.telefono,
            password: formJson.contrasena1,
            esAdmin: false
        };

        const endPoint = 'usuarios/registrar';
        const url = urlBackend + endPoint;

        try{
            const response = await axios.post(url, payload);
            formHTML.reset();
            setOkConsumeService('Felicidades!, te has registrado en E-Bikerent');
            setTimeout(() =>(navigate('/login')),2500)
        }catch(error){
            setErrorConsumeService(error.response.data.message);
        }
    } 

    const handleForm = (e) =>{
        setOkConsumeService('');
        setErrorConsumeService('');
        const formRegistrar = e.target;
        const formRegistrarData = new FormData(formRegistrar);
        const formJson = Object.fromEntries(formRegistrarData.entries());

        const isValidForm = validateForm(formJson);

        if(isValidForm){
            consumeService(formJson, formRegistrar);
        }
    };

    return (
        <div className="container-middle RegistrarUsuario-parent-center">
            <div className="RegistrarUsuario-main">
                <h1>Registro de usuario nuevo</h1>
                <span>Para poder rentar nuestros productos debes diligenciar los siguientes datos:</span>
                <br/>
                <Form1 inputs={inputs} handleForm={handleForm} textSubmit='Registrar datos' iconSubmit={pathIcons.save}
                       textSuccesfulService={okConsumeService} textErrorService={errorConsumeService}
                />
                <br />
                
            </div>
        </div>
    )
}

export default RegistrarUsuario;