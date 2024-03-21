import React from 'react'
import './Input.css'
import { pathIcons } from '../utils/global.context'

/*Input genérico tipo 1*/
const Input1 = ({idInput, textInput, nameInput, placeHolderInput, typeInput, maxLengthInput, required, errorTextInput}) => {
  return (
    <div className={`Input1-main ${errorTextInput == '' ? '': 'Input1-main-with-error'}`}>
        <div className='Input1-field'>
            <label htmlFor={idInput}>
                <span className='Input1-field-ind-required'>{required ==='required' && '* '}</span>
                {textInput}
            </label>
            {typeInput === 'number' && 
                <input id={idInput} type='number' name={nameInput} placeholder={placeHolderInput} required={required} />
            }
            {typeInput === 'text' && 
                <input id={idInput} type='text' name={nameInput} placeholder={placeHolderInput} maxLength={maxLengthInput} required={required} />
            }
            {typeInput === 'email' && 
                <input id={idInput} type='email' name={nameInput} placeholder={placeHolderInput} maxLength={maxLengthInput} required={required} />
            }
            {typeInput === 'password' && 
                <input id={idInput} type='password' name={nameInput} placeholder={placeHolderInput} maxLength={maxLengthInput} required={required} />
            }
        </div>
        <div className='Input1-error-text'>
            {errorTextInput &&
              <img src={pathIcons.error} alt='error' />
            }
            <span>{errorTextInput}</span>
        </div>
    </div>
  )
}

export default Input1;