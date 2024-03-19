import React, { useContext, useEffect, useState } from "react";
import './IniciarSesion.css';
import Form1 from "../Form1/Form1";
import {ContextGlobal, pathIcons, urlBackend} from '../../components/utils/global.context';
import { useNavigate } from "react-router-dom";
import { getObjSession, setObjSession } from "../../components/utils/global.context";
import axios from "axios";
/*

*/
/*Componente de ruta de inicio de sesión*/
const IniciarSesion = () =>{
    /*Contexto global para actualzación y renders */
    const {contexto, setContexto} = useContext(ContextGlobal);

    /*Estados de validación de campos*/
    const [errorCorreo, setErrorCorreo] = useState('');

    /*Estados de resultado de consumo de servicio */
    const [errorConsumeService, setErrorConsumeService] = useState('');
    const [okConsumeService, setOkConsumeService] = useState('');

    /*Navigate para redireccionar a home en caso de login exitoso */
    const navigate = useNavigate();

    /*Se valida al cargue del componente si existe un objeto de sesión, si existe, redirecciona al home */
    useEffect(() =>{
        const objSessionTmp = getObjSession();
        if(!(objSessionTmp === null)){
            navigate('/');
        }
    }, []);

    /*Simula info del servicio de login */
    const objSessionSimul = {
        nombre: "German",
        apellido: "Sarmiento",
        correo: "german.sarmiento.diaz@gmail.com",
        esAdmin: true
    }

    const inputs= [
        {
            idInput: 'correoId',
            textInput: 'Correo electrónico',
            nameInput: 'correo',
            placeHolderInput: 'Email con el que te registraste',
            typeInput: 'email',
            maxLengthInput: '80',
            required: 'required',
            errorTextInput: ''
        },
        {
            idInput: 'contrasenaId',
            textInput: 'Contraseña:',
            nameInput: 'contrasena',
            placeHolderInput: '',
            typeInput: 'password',
            maxLengthInput: '10',
            required: 'required',
            errorTextInput: ''
        },
    ];
    
    const validateForm = (form) =>{
        setErrorCorreo('');

        let isValidForm = true;
        let expRegularCorreo = /^\w+([.-_+]?\w+)*@\w+([.-]?\w+)*(\.\w{2,10})+$/;
    
        
        /*Validación de Correo */
        if(!expRegularCorreo.test(form.correo)){
            setErrorCorreo('Correo inválido');
            isValidForm = false;
        }

        return isValidForm;
    };

    const consumeService = async (formJson, formHTML) =>{

        const endPoint = 'usuarios/login';
        const url = urlBackend + endPoint;

        const params = new URLSearchParams();
        params.append('mail', formJson.correo);
        params.append('password', formJson.contrasena);

        try{
            const response = await axios.post(url, params);
            formHTML.reset();
            setObjSession({
                nombre: response.data.nombre,
                apellido: response.data.apellido,
                correo: response.data.mail,
                esAdmin: response.data.esAdmin
            });
            setContexto({...contexto, sesionActiva: true});
            navigate('/');
        }catch(error){
            setErrorConsumeService(error.response.data);
        }
    } 

    const handleForm = (e) =>{
        setOkConsumeService('');
        setErrorConsumeService('');
        const formIniciarSesion = e.target;
        const formIniciarSesionData = new FormData(formIniciarSesion);
        const formJson = Object.fromEntries(formIniciarSesionData.entries());

        const isValidForm = validateForm(formJson);

        if(isValidForm){
            consumeService(formJson, formIniciarSesion);
        }
    };

    return (
        <div className={"container-middle IniciarSesion-parent-center " }>
            <div className="IniciarSesion-main">
                <h1>Iniciar sesión</h1>
                <span>Para ingresar a tu cuenta por favor diligencia los siguientes datos:</span>
                <br/>
                <Form1 inputs={inputs} handleForm={handleForm} textSubmit='Iniciar sesión' iconSubmit={pathIcons.login}
                       textSuccesfulService={okConsumeService} textErrorService={errorConsumeService}
                />
                <br />
                
            </div>
        </div>
    )
}

export default IniciarSesion;