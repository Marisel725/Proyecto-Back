import React from 'react'
import './Input2.css'
import { pathIcons } from '../utils/global.context'

/*Input tipo 2, solo para mostrar información*/
const Input2 = ({idInput, textInput, nameInput, typeInput, errorTextInput = '', value}) => {
  return (
    <div className={`Input2-main ${errorTextInput == '' ? '': 'Input2-main-with-error'}`}>
        <div className='Input2-field'>
            <label htmlFor={idInput}>
                {textInput}
            </label>
            {typeInput === 'number' && 
                <input id={idInput} type='number' name={nameInput} readOnly value={value}/>
            }
            {typeInput === 'text' && 
                <input id={idInput} type='text' name={nameInput} readOnly value={value}/>
            }
            {typeInput === 'email' && 
                <input id={idInput} type='email' name={nameInput} readOnly value={value}/>
            }
            {typeInput === 'password' && 
                <input id={idInput} type='password' name={nameInput} readOnly value={value}/>
            }
        </div>
        <div className='Input2-error-text'>
            {errorTextInput &&
              <img src={pathIcons.error} alt='error' />
            }
            <span>{errorTextInput}</span>
        </div>
    </div>
  )
}

export default Input2;