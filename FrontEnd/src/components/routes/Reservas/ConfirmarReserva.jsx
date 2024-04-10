import React, { useEffect, useState } from 'react'
import { useLocation, useNavigate, useSearchParams } from 'react-router-dom';
import { getObjSession, modulosRedireccion, urlBackend, pathIcons } from '../../utils/global.context';
import axios from 'axios';
import ButtonLeftIcon from '../../Buttons/ButtonLeftIcon';
import './ConfirmarReserva.css';
import Input2 from '../../Input2/Input2';

const msgError1 = 'Existe un error en los datos de la reserva, por favor elíge una bicicleta y asegúrate de seleccionar los días en los que quieres reservarla, luego selecciona  el botón “Iniciar Reserva”.';
const msgError2 = 'Existe un error con las fechas de reserva, fecha inicial de reserva [';
const msgError2_1 = '] fecha final de reserva ['; 
const msgError2_2 = '].'; 
const msgError3 = 'Existe un error con los datos de la bicicleta';
const msgError4 = 'La bicicleta no se puede reservar en las fechas seleccionadas';
const msgError5 = 'Esta bicicleta no existe';
const msgError6 = 'Tardaste mucho en confirmar la reserva, la bicicleta ya no esta disponible en esas fechas, intenta en otra fecha o con otra bicicleta.';

const ConfirmarReserva = () => {
  /*Estados para mistrar cards/mensajes de error o confirmación*/
  const [errorConsumeService, setErrorConsumeService] = useState(null);
  const [okConsumeService, setOkConsumeService] = useState(null);
  const [errorConsumeService2, setErrorConsumeService2] = useState(null);
  const [okConsumeService2, setOkConsumeService2] = useState(null);

  /*Estado para guardar datos de producto a reservar*/
  const [datosProducto, setDatosProducto] = useState(null);

  /*Estados para datos de formulario de datos personales*/
  const [datosPerNombre, setDatosPerNombre] = useState('');
  const [datosPerApellido, setDatosPerApellido] = useState('');
  const [datosPerCorreo, setDatosPerCorreo] = useState('');
  const [datosPerTelefono, setDatosPerTelefono] = useState('');

  /*Estados de datos de reserva para consumo de servicio */
  const [fechaInicioService, setFechaInicioService] = useState('');
  const [fechaFinService, setFechaFinService] = useState('');
  const [idProductoService, setIdProductoService] = useState('');

  /*Para extraer parametros de URL*/
  const [reservaParams, setReservaParams] = useSearchParams();

  /*Para realizar navegación/redireccionamientos*/
  const navigate = useNavigate();

  useEffect(()=>{
    const idProducto = reservaParams.get('id');
    const fecha1 = reservaParams.get('fecha1');
    const fecha2 = reservaParams.get('fecha2');

    /*Se verifica que exista una sesión activa */
    const objSessionTmp = getObjSession();

    if(objSessionTmp === null){
      const urlLogin = '/login?modulo=' + modulosRedireccion.confirmarReservas +
                       '&id=' + idProducto +  
                       '&fecha1=' + encodeURIComponent(fecha1) + 
                       '&fecha2=' + encodeURIComponent(fecha2);
      navigate(urlLogin)
    }else{
      setDatosPerNombre(objSessionTmp.nombre);
      setDatosPerApellido(objSessionTmp.apellido);
      setDatosPerCorreo(objSessionTmp.correo);
      setDatosPerTelefono(objSessionTmp.telefono);

      if(isValidBookingValues(idProducto, fecha1, fecha2)){
        consultarProductoPorId(idProducto)
          .then((response) =>{
            setDatosProducto(response.data);
            buscarReservaPorProducto(idProducto, fecha1, fecha2);
        })
          .catch((error) => {
            setErrorConsumeService(msgError5);
            setErrorConsumeService2(error.response.data.message)
        });
      }
    }
  },[]);

  /*Se genera validación de los datos de la reserva dado que los parametros son manipulables por url*/
  const isValidBookingValues = (idProducto, fecha1, fecha2) =>{
    /*Se valida que se los tres parametros para la reserva vengan informados*/
    if(idProducto === null || fecha1 === null || fecha2 === null){
      setErrorConsumeService(msgError1);
      return false;
    }

    /*Se valida que el id del producto sea un valor entero*/
    if(Number.isNaN(Number.parseInt(idProducto))){
      setErrorConsumeService(msgError3);
      return false;
    }

    /*Se valida que las fechas informadas tengan el formato válido y sean fechas lógicas*/
    if(!isValidDate(fecha1) || !isValidDate(fecha2)){
      setErrorConsumeService(msgError2 + fecha1 + msgError2_1 + fecha2 + msgError2_2);
      return false;
    }

    return true
  };

  const isValidDate= (date) =>{
    /*Valida que el formato de fecha sea AAAA-mm-dd */
    const DATE_REGEX = /^(\d{4})(\-)(0[1-9]|1[012])\2(0[1-9]|[1-2]\d|3[01])$/;
    
    if(!date.match(DATE_REGEX)){
      return false;
    }

    const valuesDate = date.split('-');

    const year  = parseInt(valuesDate[0]);
    const month = parseInt(valuesDate[1]);
    const day   = parseInt(valuesDate[2]);

    const monthDays = new Date(year, month, 0).getDate();

    if(day > monthDays){
      return false;
    }

    return true;

  };

  const consultarProductoPorId = async (idProducto) =>{
    const endPoint = 'productos/buscarPorId/' + idProducto;
    const url = urlBackend + endPoint;

    return axios.get(url);
  };

  const buscarReservaPorProducto = async (idProducto, fecha1, fecha2) => {
    const endPoint = 'reservas/buscarReservaPorProducto';
    const url = urlBackend + endPoint;

    const payload = {
      fechaInicio: fecha1,
      fechaFin: fecha2,
      producto_id: idProducto,
      correo: 'correo@correo.com'
    };

    try{
      const response = await axios.post(url, payload);
      setFechaInicioService(fecha1);
      setFechaFinService(fecha2);
      setIdProductoService(idProducto);
    }catch(error){
      setErrorConsumeService(msgError4);
      setErrorConsumeService2(error.response.data.message);
    }
  }

  const handleGoToBack = () =>{
    navigate(-1);
  }

  const handleConfirmarReserva = async () =>{
    setErrorConsumeService('');
    setOkConsumeService('');
    setErrorConsumeService2('');
    setOkConsumeService2('');

    const endPonit = 'reservas/registrar';
    const url = urlBackend + endPonit;

    const payload = {
      fechaInicio: fechaInicioService,
      fechaFin: fechaFinService,
      producto_id: idProductoService,
      correo: datosPerCorreo
    };

    console.log(payload)

    try{
      const response = await axios.post(url, payload);
      setOkConsumeService('Tu bicicleta se ha reservado con éxito');
      setOkConsumeService2('N° de reserva generado: ' + response.data.id);
      setReservaParams('');
    }catch(error){
      setErrorConsumeService(msgError6);
      setErrorConsumeService2(error.response.data.message);
    }

  }

  const handleGoToHome = () =>{
    navigate('/');
  };

  return (
    <div className='container-middle ConfirmarReserva-parent-center'>
        <div className='ConfirmarReserva-titulo-principal'>
          <h2>Confirmar Reserva</h2>
        </div>
        {(errorConsumeService || okConsumeService) ?
          <div className='ConfirmarReserva-vertical-container ConfirmarReserva-mensajes-servicio'>
            <div className='ConfirmarReserva-mensajes-servicio--item'>
              <img src={errorConsumeService? pathIcons.redAlert: pathIcons.done} alt="" />
            </div>
            <div className='ConfirmarReserva-mensajes-servicio--item'>
              <div style={{display: 'flex', flexDirection: 'column'}}>
              <p className={errorConsumeService? 'ConfirmarReserva-mensajes-servicio--error': ''}>
                {errorConsumeService ?
                  errorConsumeService
                  :
                  okConsumeService
                }
              </p>
              {errorConsumeService2 &&
                <div className='ConfirmarReserva-mensajes-servicio--DetalleError'>{errorConsumeService2}</div>
              }
              {okConsumeService2 &&
                <div>{okConsumeService2}</div>
              }
              </div>
            </div>
            <div className='ConfirmarReserva-mensajes-servicio--item ConfirmarReserva-mensajes-servicio--buttons'>
              <ButtonLeftIcon title='Volver al inicio' text='Volver al inicio' icon='none' handleClick={handleGoToHome}/>
            </div>
          </div>
          :
          <div className='ConfirmarReserva-main'>
            <div className='ConfirmarReserva-seccion-botones-top'>
              <ButtonLeftIcon title='Volver atrás' text='Volver atrás' icon={pathIcons.gotoBack} handleClick={handleGoToBack} />
            </div>
            
            <div className='ConfirmarReserva-seccion-datos-personales ConfirmarReserva-vertical-container'>
              <h3>Revisa tus datos</h3>
              <form onSubmit={(e) => (e.preventDefault())}>
                <Input2 idInput='nombre' textInput='Nombre' nameInput='nombre' typeInput='text' value={datosPerNombre}/>
                <Input2 idInput='apellido' textInput='Apellido' nameInput='apellido' typeInput='text' value={datosPerApellido}/>
                <Input2 idInput='correo' textInput='Correo electrónico' nameInput='correo' typeInput='email' value={datosPerCorreo}/>
                <Input2 idInput='telefono' textInput='Teléfono' nameInput='telefono' typeInput='text' value={datosPerTelefono} />
              </form>
            </div>
            
            <div className='ConfirmarReserva-seccion-datos-reserva ConfirmarReserva-vertical-container'>
              <h3>Detalle de la reserva</h3>
              {datosProducto &&
                <div className='ConfirmarReserva-seccion-datos-reserva--card'>
                  <img src={datosProducto.imagenes[0].urlImg} alt={datosProducto.imagenes[0].titulo} />
                  <div className='ConfirmarReserva-seccion-datos-reserva--nombre'>
                    {datosProducto.nombre}
                  </div>
                  <div className='ConfirmarReserva-seccion-datos-reserva--fechas'>
                    <span>Fecha inicio</span>
                    <span>{fechaInicioService}</span>
                  </div>
                  <div className='ConfirmarReserva-seccion-datos-reserva--fechas'>
                    <span>Fecha fin</span>
                    <span>{fechaFinService}</span>
                  </div>
                </div>
              }
            </div>
            
            <div className='ConfirmarReserva-seccion-datos-adicionales ConfirmarReserva-vertical-container'>
              <h3>Estamos ubicados en</h3>
              <span>Av. Alicia Moreau de Justo, 516 1107 Buenos Aires Ciudad autónoma de Buenos Aires</span>
              <h3>Horario</h3>
              <span>Lunes a Domingo de 8:00am a 5:30pm</span>
            </div>

            <div className='ConfirmarReserva-seccion-botones-bottom'>
              <ButtonLeftIcon title='Confirmar Reserva' text='Confirmar reserva' icon={pathIcons.login} handleClick={handleConfirmarReserva} />
            </div>
          </div>
        }
    </div>
  )
}

export default ConfirmarReserva;