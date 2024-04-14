import React from 'react'
import './Whatsapp.css'

const Whatsapp = () => {

    const telefonoApiWhatsApp = '1193329618351';
    const mensajeApiWhatsApp = 'Hola, me gustaría saber mas acerca de las ciclas disponibles en EBikerent.';
    const endPointApiWhatsApp = 'https://api.whatsapp.com/send';
    const urlApiWhatsApp = endPointApiWhatsApp + '?phone=' + telefonoApiWhatsApp + '&text=' + mensajeApiWhatsApp.replace(/\s/g, '%20');


    return (
        <>
            <a href={urlApiWhatsApp} target='_blank'>
                <img src="https://i.imgur.com/cDxSZsO.png" alt="" className='logoWhatsapp' />
            </a>
        </>

    )
}

export default Whatsapp