import React, { useEffect } from 'react'
import ButtonRightIcon from '../../Buttons/ButtonRightIcon'
import { Link, Outlet, useNavigate } from 'react-router-dom'
import { pathIcons, getObjSession } from '../../utils/global.context'
import './Admin.css'

const Admin = () => {
  /*Objeto de redireccionamiento según la acción realizada en el header */
  const navigate = useNavigate(); 

  useEffect(() => {
    const objSessionTmp = getObjSession();

    if(objSessionTmp === null){
      /*Si no hay objeto de sesión, es decir, no se ha logueado, no permite usar el modulo admin */
      navigate('/login')
    }else{
      /*Si existe el objeto de sesión, pero no tiene los permisos, no permite usar el modulo */
      if(!objSessionTmp.esAdmin){
        navigate('/');
      }
    }
  }, []);

  return (
    <div className='container-middle Admin-parent-center'>
      <div className='Admin-container'>
        {/* Grupo de productos*/}
        <div className='Admin-group'>
          <Link to='productos/registrar'>
            <ButtonRightIcon title='Registrar producto' text='Agregar productos' icon={pathIcons.add} />
          </Link>
          <Link to='productos'>
            <ButtonRightIcon title='Listar productos' text='Lista de productos' icon={pathIcons.list} />
          </Link>
        </div>

        {/* Grupo de categorías*/}
        <div className='Admin-group'>
          <Link to='categorias/registrar'>
            <ButtonRightIcon title='Registrar categoría' text='Crear categoría' icon={pathIcons.add} />
          </Link>
          <Link to='categorias'>
            <ButtonRightIcon title='Listar categorías' text='Lista de categorías' icon={pathIcons.list} />
          </Link>
        </div>

        {/* Grupo de Ccracterísticas*/}
        <div className='Admin-group'>
          <Link to='caracteristicas/registrar'>
            <ButtonRightIcon title='Registrar carcacterística' text='Crear característica' icon={pathIcons.add} />
          </Link>
          <Link to='caracteristicas'>
            <ButtonRightIcon title='Listar características' text='Lista de características' icon={pathIcons.list} />
          </Link>
        </div>
      </div>
      
      <Outlet />
      {/*<ProductoManejador/>*/}
    </div>
  )
}

export default Admin