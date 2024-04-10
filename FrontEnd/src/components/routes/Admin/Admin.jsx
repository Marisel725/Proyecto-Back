import React, { useEffect, useState } from 'react';
import ButtonRightIcon from '../../Buttons/ButtonRightIcon'
import { Link, Outlet, useNavigate } from 'react-router-dom'
import { pathIcons, getObjSession, modulosRedireccion } from '../../utils/global.context'
import './Admin.css'

const Admin = () => {
  /*Objeto de redireccionamiento según la acción realizada en el header */
  const navigate = useNavigate(); 
  const [isSmallScreen, setIsSmallScreen] = useState(false);

  useEffect(() => {
    const objSessionTmp = getObjSession();

    if(objSessionTmp === null){
      /*Si no hay objeto de sesión, es decir, no se ha logueado, no permite usar el modulo admin */
      const urlLogin = '/login?modulo=' + modulosRedireccion.admin;
      navigate(urlLogin)
    }else{
      /*Si existe el objeto de sesión, pero no tiene los permisos, no permite usar el modulo */
      if(!objSessionTmp.esAdmin){
        navigate('/');
      }
    }


  // eventos para ver si la pantalla es pequeña
  setIsSmallScreen(window.innerWidth <= 768);

  const handleResize = () => {
    setIsSmallScreen(window.innerWidth <= 768);
  };
  window.addEventListener('resize', handleResize);

  return () => {
    window.removeEventListener('resize', handleResize);
  };
}, []);


  return (
    <div className='container-middle Admin-parent-center'>
      <div className='Admin-container'>
  {/* Mostrar mensaje si la pantalla es pequeña */}
  {isSmallScreen && (
          <div className="not-available-message">
            El panel de administración no está disponible en dispositivos más pequeños.
          </div>
        )}

   {/* si lo no es... */}
   {!isSmallScreen && (
          <>
            {/* Grupo de productos */}
            <div className='Admin-group'>
              <Link to='productos/registrar'>
                <ButtonRightIcon title='Registrar producto' text='Agregar productos' icon={pathIcons.add} />
              </Link>
              <Link to='productos'>
                <ButtonRightIcon title='Listar productos' text='Lista de productos' icon={pathIcons.list} />
              </Link>
            </div>

            {/* Grupo de categorías */}
            <div className='Admin-group'>
              <Link to='categorias/registrar'>
                <ButtonRightIcon title='Registrar categoría' text='Crear categoría' icon={pathIcons.add} />
              </Link>
              <Link to='categorias'>
                <ButtonRightIcon title='Listar categorías' text='Lista de categorías' icon={pathIcons.list} />
              </Link>
            </div>

            {/* Grupo de características */}
            <div className='Admin-group'>
              <Link to='caracteristicas/registrar'>
                <ButtonRightIcon title='Registrar característica' text='Crear característica' icon={pathIcons.add} />
              </Link>
              <Link to='caracteristicas'>
                <ButtonRightIcon title='Listar características' text='Lista de características' icon={pathIcons.list} />
              </Link>
            </div>

            {/* Grupo de usuarios */}
            <div className='Admin-group'>
              <Link to='usuarios'>
                <ButtonRightIcon title='Lista de usuarios registrados' text='Lista de usuarios registrados' icon={pathIcons.list} />
              </Link>
            </div>
          </>
        )}
      </div>

      <Outlet />
    </div>
  );
};

export default Admin;