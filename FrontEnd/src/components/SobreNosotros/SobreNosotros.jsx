import React from 'react';
import './SobreNosotros.css';

const SobreNosotros = () => {

  const texto = `
    Nos complace compartir con entusiasmo que hemos completado con éxito nuestro primer proyecto grupal en Digital House. Este emocionante hito representa no solo un logro personal, sino también el resultado del arduo trabajo en equipo y la aplicación de los conocimientos adquiridos a lo largo de nuestro viaje educativo.

    Nuestro proyecto se centró en el desarrollo de una aplicación funcional utilizando tecnologías de vanguardia como React, Spring Boot y Railway. Además, aprovechamos herramientas como Netlify y Figma para diseñar una experiencia de usuario fluida y atractiva.

    Este logro es el resultado del compromiso y la colaboración de un grupo de estudiantes dedicados, cada uno aportando sus habilidades únicas y perspectivas individuales. Trabajamos incansablemente para superar desafíos técnicos, iterar sobre ideas y crear una solución que refleje nuestro aprendizaje y creatividad.

    Estamos encantados con el resultado final y orgullosos del trabajo en equipo que llevó a su realización. Este proyecto no solo nos permitió aplicar lo que aprendimos en clase, sino que también nos brindó la oportunidad de crecer como profesionales en el campo del desarrollo de software.

    Agradecemos a Digital House por brindarnos esta plataforma para aprender y crecer, así como a nuestros instructores por su orientación y apoyo continuo a lo largo de este desafiante pero gratificante proyecto.

    Miramos hacia el futuro con entusiasmo, listos para aplicar las lecciones aprendidas y enfrentar nuevos desafíos con confianza y determinación.

    El equipo está emocionado por lo que está por venir y agradecido por esta experiencia única!
  `;

  return (
    <>
    <h1> .</h1>
      <h1 className='titulo'>Sobre Nosotros</h1>
      <p>{texto}</p>
      <img src="https://i.imgur.com/f58g7mi.png" className='imagen' alt="" />
    </>
  );
};

export default SobreNosotros;