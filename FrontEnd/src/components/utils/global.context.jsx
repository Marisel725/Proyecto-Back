import { createContext, useMemo, useState } from "react";
import pathLogoEmpresa from '../../images/e_bikernt_logo.png';
import pathIcoHamburgerUser from '../../images/hamburg-user-icon.svg';
import pathIcoBtnAddUser from '../../images/ico-btn-add-user.png';
import pathIcoBtnAdd from '../../images/ico-btn-add.png';
import pathIcoBtnGoLogginUser from '../../images/ico-btn-go-loggin-user.png';
import pathIcoBtnList from '../../images/ico-btn-list.png';
import pathIcoSave from '../../images/ico-save.png';
import pathLogoFooter from '../../images/logo-footer.png';
import pathIcoError from '../../images/ico-error.png';
import pathIcoOk1 from '../../images/ico-ok1.png';
import pathIcoLogin from '../../images/ico-login.png';
import pathLogoDelete from '../../images/delete.png';
import pathLogoEdit from '../../images/edit.png';
import pathIcoFacebook from '../../images/facebook1.png';
import pathIcoWhatsapp from '../../images/whatsapp1.png';
import pathIcoInstagram from '../../images/instagram1.png';
import pathIcoYoutube from '../../images/youtube1.png';
import pathIcoLupa from '../../images/ico-lupa.png';
import pathIcoCalendario from '../../images/ico-calendario.png';
import pathRol from '../../images/rol.png';
import pathIcoDone from '../../images/ico-done.png';
import pathIcoGotoBack from '../../images/ico-goto-back.png';
import pathIcoRedAlert from '../../images/ico-red-alert.png';

/*Manejo global de objeto de sesión */
const nameObjSession = 'ebikerent-session';

export const getObjSession = () =>{
  return JSON.parse(localStorage.getItem(nameObjSession)) ?? null;
}

export const setObjSession = (objSession) =>{
  localStorage.setItem(nameObjSession, JSON.stringify(objSession));
}

export const deleteObjSession = () =>{
  localStorage.removeItem(nameObjSession);
}

/*Contexto Global */
export const initialState = {
  theme: "light", 
  arrayCiclas: [],
  sesionActiva: false
};

export const ContextGlobal = createContext();

export const ContextProvider = ({ children }) => { 
  initialState.sesionActiva = getObjSession() === null ? false : true;
  const [contexto, setContexto] = useState(initialState);
  const providerValue = useMemo(() => ({contexto, setContexto}), [contexto]);
  
  return (
    <ContextGlobal.Provider value={providerValue}>
      {children}
    </ContextGlobal.Provider>
  );
};


/*Global values*/ 
export const urlLogoEmpresa = pathLogoEmpresa;
export const urlLogoFooter = pathLogoFooter;
export const pathIcons = {
  addUser: pathIcoBtnAddUser,
  goLogginUser: pathIcoBtnGoLogginUser,
  hamburgUser: pathIcoHamburgerUser,
  add: pathIcoBtnAdd,
  list: pathIcoBtnList,
  save: pathIcoSave,
  error: pathIcoError,
  ok1: pathIcoOk1,
  login: pathIcoLogin,
  delete: pathLogoDelete,
  edit:pathLogoEdit,
  facebook: pathIcoFacebook,
  whatsapp: pathIcoWhatsapp,
  instagram: pathIcoInstagram,
  youtube: pathIcoYoutube,
  lupa: pathIcoLupa,
  calendario: pathIcoCalendario,
  rol: pathRol,
  done: pathIcoDone,
  gotoBack: pathIcoGotoBack,
  redAlert: pathIcoRedAlert
};

export const urlBackend = 'https://backendebikerent-production.up.railway.app/';

export const modulosRedireccion ={
  confirmarReservas : 'confirmarReserva',
  admin : 'admin'
};