import React, { useContext, useEffect, useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { ContextGlobal, urlLogoEmpresa, pathIcons } from '../utils/global.context';
import './Header.css';
import HamburgMenuUser from './HamburgMenuUser';
import { getObjSession, setObjSession, deleteObjSession } from "../../components/utils/global.context";
import { HashLink } from 'react-router-hash-link';


const Header = () => {
  /*Estado para manejo de información de usuario logueado */
  const [objSession, setObjSession] = useState(null);
  /*Estado para manejo de opciones de usuario, sea logueado o anonimo */
  const [optionsList, setOptionList] = useState([]);

  /*Contexto global usado para actualizar header cuando se presenta inicio de sesión o cierre de sesión */
  const { contexto, setContexto } = useContext(ContextGlobal);

  /*Objeto de redireccionamiento según la acción realizada en el header */
  const navigate = useNavigate();

  const handleCerarSesion = () => {
    deleteObjSession();
    setObjSession(null);
    setContexto({ ...contexto, sesionActiva: false });
    options = optionsUserAnonimus.slice();
    setOptionList(options);
    navigate('/');
  };

  const handleAdmin = () => {
    navigate('/Admin');
  };

  const handleFavoritos = () => {
    navigate('/favoritos');
  };



  let options = [];
  const optionsUserAnonimus = [
    { title: 'Crear cuenta', text: 'Crear cuenta', icon: pathIcons.addUser, link: '/signup', handle: null },
    { title: 'Iniciar sesión', text: 'Iniciar sesión', icon: pathIcons.goLogginUser, link: '/login', handle: null }
  ];

  const optionsUserLoged = [
    { title: 'Mi perfil', text: 'Mi perfil', icon: 'none', link: '', handle: null },
    { title: 'Favoritos', text: 'Favoritos', icon: 'none', link: '', handle: handleFavoritos },
    { title: 'Cambiar contraseña', text: 'Cambiar contraseña', icon: 'none', link: '', handle: null },
    { title: 'Cerrar sesión', text: 'Cerrar sesión', icon: 'none', link: '', handle: handleCerarSesion }
  ];

  const optionAdmin = { title: 'Módulo administrador', text: 'Módulo administrador', icon: 'none', link: '', handle: handleAdmin }

  useEffect(() => {
    const objSessionTmp = getObjSession();

    if (!(objSessionTmp === null)) {
      /*Arma listado de opciones de usuario */
      options = optionsUserLoged.slice();
      if (objSessionTmp.esAdmin) {
        options.unshift(optionAdmin);
      }
      setObjSession(objSessionTmp);
    } else {
      options = optionsUserAnonimus.slice();
    }

    setOptionList(options);

    const handleScrollToTop = () => {
      window.scrollTo({ top: 0, behavior: 'smooth' });
    };

    const scrollToTopIfOnHomepage = () => {
      if (window.location.pathname === '/') {
        handleScrollToTop();
      }
    };

    document.querySelector('.header-logo-empresa').addEventListener('click', scrollToTopIfOnHomepage);

    return () => {
      document.querySelector('.header-logo-empresa').removeEventListener('click', scrollToTopIfOnHomepage);
    };
  }, []);

  useEffect(() => {
    const objSessionTmp = getObjSession();

    if (!(objSessionTmp === null)) {
      /*Arma listado de opciones de usuario */
      options = optionsUserLoged.slice();
      if (objSessionTmp.esAdmin) {
        options.unshift(optionAdmin);
      }
    } else {
      options = optionsUserAnonimus.slice();
    }

    setOptionList(options);
    setObjSession(objSessionTmp);

  }, [contexto.sesionActiva]);

  const reiniciarHome = () => {

  }

  return (
    <header className='header-flex-container'>

      <div className='links-header'>
        <Link to={'/'} className='link-header' onClick={() => window.scrollTo({ top: 0, behavior: 'smooth' })}>Inicio</Link>
        <HashLink to='#categorias' smooth offset={-1000} className='link-header'>Categorías</HashLink>
      </div>

      <div className='header-container-logo-empresa'>
        <Link to={'/'}>
          <img src="https://i.imgur.com/MN8hsjZ.png" alt='Home' className='header-logo-empresa' />
        </Link>
      </div>

      <div className='right-header'>
        <div className='links-header'>
          <Link to={"/sobrenosotros"} className='link-header'>Sobre nosotros</Link>
          <Link className='link-header'>Contacto</Link>
        </div>
        <HamburgMenuUser options={optionsList} userData={objSession} />
      </div>
      
    </header>
  )
}

export default Header;