import React from 'react';
import './Footer.css'; 
import { Link } from 'react-router-dom';
import { urlLogoFooter, pathIcons } from '../utils/global.context';


const Footer = () => {

  const telefonoApiWhatsApp = '1193329618351';
  const mensajeApiWhatsApp = 'Hola, me gustaría saber mas acerca de las ciclas disponibles en EBikerent.';
  const endPointApiWhatsApp = 'https://api.whatsapp.com/send';
  const urlApiWhatsApp = endPointApiWhatsApp + '?phone=' + telefonoApiWhatsApp + '&text=' + mensajeApiWhatsApp.replace(/\s/g, '%20');

  return (
    <footer>
      <div className="footer-content">

        <div className="left">
        <div className="logo"><img src="https://i.imgur.com/MN8hsjZ.png"alt="Logo" /></div>
          <div className="copy"> <p>Copyright © 2024</p></div>
        </div>
        <div className='right'>
          <a href="https://www.facebook.com" target='_blank'>
            <img src={pathIcons.facebook} alt='Facebook' />
          </a>
          <a href={urlApiWhatsApp} target='_blank'>
            <img src={pathIcons.whatsapp} alt='WhatsApp'/>
          </a>
          <a href="https://www.instagram.com" target='_blank'>
            <img src={pathIcons.instagram} alt='Instagram' />
          </a>
          <a href="https://www.youtube.com" target='_blank'>
            <img src={pathIcons.youtube} alt='Youtube' />
          </a>
        </div>
      </div>  
    </footer>
  );
};

export default Footer;