import React, { useContext, useState } from 'react'; 
import {pathIcons} from '../utils/global.context';
import './HamburgMenuUser.css';
import ButtonLeftIcon from '../Buttons/ButtonLeftIcon';
import { Link } from 'react-router-dom';

const HamburgMenuUser = ({options, userData}) => {
  const[isActiveMenu, setIsActiveMenu] = useState(false);
  const activateMenu = () =>{
    setIsActiveMenu(!isActiveMenu);
  };

  const defaultAcctionHandle = (handle) =>{
    setIsActiveMenu(false);
    if(!(handle === null)){
      handle();
    }
  };

  return (
    <div className={'HamburgMenuUser-main ' + (userData === null ? '' : 'HamburgMenuUser-main-user')}>

      {/*Si no hay objeto de sesión, muestra el icono anónimo, de lo contrario, arma le avatar*/}
      {userData === null ?
          <a className='HamburgMenuUser-anonimus-avatar' onClick={activateMenu}>
            <img src={pathIcons.hamburgUser} alt='Opciones usuario' className={`HamburgMenuUser-icon ${isActiveMenu? 'HamburgMenuUser-isActive': ''}`} />
          </a>
        :
        <div className={'HamburgMenuUser-avatar ' + (isActiveMenu? 'HamburgMenuUser-isActive': '')} onClick={activateMenu}>
          {userData.nombre.charAt(0).toUpperCase() + userData.apellido.charAt(0).toUpperCase()}
        </div>
      }

      <nav className={'HamburgMenuUser-nav ' + ((userData === null ? '': 'HamburgMenuUser-nav-user ')
                       + (isActiveMenu? 'HamburgMenuUser-isActive ': ''))
                      }>

        {/*Si hay objeto de sesión, renderiza avatar en menú flotante con datos de usuario*/}
        {userData === null?
          ''
          :
          <div className='HamburgMenuUser-cont-inner-avatar'>
            <div className={'HamburgMenuUser-avatar ' + (isActiveMenu? 'HamburgMenuUser-isActive': '')}>
                {userData.nombre.charAt(0).toUpperCase() + userData.apellido.charAt(0).toUpperCase()}
            </div>
            <div className='HamburgMenuUser-cont-inner-avatar-data'>
              <div className='HamburgMenuUser-cont-inner-avatar-name'>
                <b>{userData.nombre + ' ' +userData.apellido}</b>
              </div>
              <div className='HamburgMenuUser-cont-inner-avatar-email'>
                {userData.correo}
              </div>
            </div>
          </div>
        }

        {/*Renderizado de opciones de usario, tanto para usuario registrado como para anónimo*/}
        <ul className='HamburgMenuUser-ul'>
          {
            options.map((item, idx) =>(
              <li key={idx} className='HamburgMenuUser-li'>
                {userData === null?
                  <Link to={item.link}>
                    <ButtonLeftIcon title={item.title} text={item.text} icon={item.icon} handleClick={() =>(defaultAcctionHandle(item.handle))}/>
                  </Link>
                  :
                  
                  <ButtonLeftIcon title={item.title} text={item.text} icon={item.icon} handleClick={() =>(defaultAcctionHandle(item.handle))} />
                }
              </li>
            ))
          }
        </ul>
      </nav>
    </div>
  )
}

export default HamburgMenuUser