import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { pathIcons } from '../../../utils/global.context';
import './UsuariosListar.css'

const UsuariosListar = () => {
  const [users, setUsers] = useState([]);

  const [isSmallScreen, setIsSmallScreen] = useState(false);

  useEffect(() => {
    const fetchUsers = async () => {
      try {
        const response = await axios.get('https://backendebikerent-production.up.railway.app/usuarios/listar');
        setUsers(response.data);
      } catch (error) {
        console.error('Error fetching users:', error);
      }
    };

    fetchUsers();

  
    // es para que el panel de admin no cargue en reponsive
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
    <div>
      <h1>Lista de Usuarios</h1>
      {/* Mostrar mensaje si la pantalla es pequeña */}
      {isSmallScreen && (
        <div className="not-available-message">
          El panel de administración no está disponible en dispositivos más pequeños.
        </div>
      )}

      {/* Mostrar lista de usuarios solo si la pantalla no es pequeña */}
      {!isSmallScreen && (
        <>
          <div className="producto-item">
            <div className="producto-id">ID </div>
            <div className="producto-nombre">Nombre </div>
            <div className="producto-acciones">Asignar rol admin</div>
          </div>

          <ul>
            {users.map(user => (
              <li key={user.id} className="producto-itemUser">
                <div > <p>{user.id}</p></div>
                <div><p> {user.nombre}</p></div>
                <div className='icon-aling'>
                  <img
                    src={pathIcons.rol}
                    alt="Asignar admin"
                    className="assign-admin"
                  />
                </div>
              </li>
            ))}
          </ul>
        </>
      )}
    </div>
  );
};

export default UsuariosListar;