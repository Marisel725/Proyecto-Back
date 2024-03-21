import React, { useContext } from 'react' 
import './ButtonRightIcon.css';

const ButtonRightIcon = ({ title, text, icon }) => {
  return (
    <span className="ButtonRightIcon-main" title={title} >
      {text}
      <img src={icon} alt={title} />
    </span>
  );
};

export default ButtonRightIcon;