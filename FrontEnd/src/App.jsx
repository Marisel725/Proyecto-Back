import './App.css'
import '@fortawesome/fontawesome-free/css/all.css';
import Home from './components/routes/Home'
import { BrowserRouter, Route, Routes, useLocation } from 'react-router-dom'
import Footer from './components/Footer/Footer'
import Header from './components/header/Header'
import Admin from './components/routes/Admin/Admin'
import ProductosListar from './components/routes/Admin/Productos/ProductosListar'
import ProductosRegistrar from './components/routes/Admin/Productos/ProductosRegistrar'
import DetalleProducto from './components/routes/DetalleProducto'
import RegistrarUsuario from './components/routes/RegistrarUsuario'
import IniciarSesion from './components/routes/IniciarSesion'
import CategoriaLista from './components/routes/Admin/Categorias/CategoriaLista'
import FormCategoria from './components/routes/Admin/Categorias/FormCategoria'
import CaracteristicaLista from './components/routes/Admin/Caracteristicas/CaracteristicaLista'
import FormCaracteristica from './components/routes/Admin/Caracteristicas/FormCaracteristica'
import CategoriasLista from './components/categorias/CategoriasLista'
import UsuariosListar from './components/routes/Admin/Usuarios/UsuariosListar'
import { useLayoutEffect, useState } from 'react';
import ConfirmarReserva from './components/routes/Reservas/ConfirmarReserva';
import ListarReservas from './components/routes/Reservas/ListarReservas';
import Favoritos from './components/header/Favoritos/Favoritos';


/*Se agrega este componente de envoltura de routes para hacer que en cada cambio de pagina
  se desplace al princiio de la misma */
const WrapperRoutes = ({children}) =>{
  const location = useLocation();
  useLayoutEffect(() =>{
    document.documentElement.scrollTo(0,0);
  }, [location.pathname]);

  return children;
};

function App() {


  return (
    <>
      <Header />
      <WrapperRoutes>
        <Routes>
          {/*Home del site */}
          <Route path='/' element={<Home />} />

          {/*Registro de usuarios */}
          <Route path='/signup' element={<RegistrarUsuario />} />

          {/*Inicio de sesión de usuarios */}
          <Route path='/login' element={<IniciarSesion />} />
        
          {/*Favoritos */}
          <Route path='/favoritos' element={<Favoritos />} />

          {/*Sección admin*/}
          <Route path='/admin' element={<Admin />}>
            {/*Administración de productos*/}
            <Route path='productos'  >
              <Route index element={<ProductosListar />} />
              <Route path='registrar' element={<ProductosRegistrar />} />
            </Route>
            {/*Administración de categorías*/}
            <Route path='categorias'  >
              <Route index element={<CategoriaLista />} />
              <Route path='registrar' element={<FormCategoria />} />

            </Route>

            {/*Administración de características*/}
            <Route path='caracteristicas'  >
              <Route index element={<CaracteristicaLista />} />
              <Route path='registrar' element={<FormCaracteristica />} />
            </Route>

            {/*Administración de lista usuarios*/}
            <Route path='usuarios'  >
              <Route index element={<UsuariosListar />} />
            </Route>

          </Route>

          {/*Administración de reservas*/}
          <Route path='/reservas'  >
              <Route index element={<ListarReservas />} />
              <Route path='confirmar' element={<ConfirmarReserva />} />
          </Route>

          {/*Detalle de producto*/}
          <Route path='/productos/:id' element={<DetalleProducto />} />
          <Route path='categorias/:categoria' element={<CategoriasLista />} />

          

          {/*Rutas no encontradas*/}
          <Route path='*' element={
            <div className='container-middle'><h1>Página/ruta no encontrada</h1></div>
          } />

        </Routes>
      </WrapperRoutes>
      <Footer />
    </>
  )
}

export default App
