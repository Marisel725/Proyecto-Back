import React,{useState , useEffect} from 'react'
import './categorias.css'
import axios from 'axios'

const Categorias = () => {

    const [categorias,setCategorias] = useState ([])

    useEffect(() => {
        const manejadorCategorias = async () => {
          try {
            const response = await axios.get('http://localhost:8080/categorias/listar');
            setCategorias(response.data);
          } catch (error) {
            console.error('Error al obtener categorias:', error);
          }
        };
        
        manejadorCategorias();
      }, []);



    return (
        <div className='categorias-wrapper'>
            <h3 className='titulos'> Nuestras Categorías</h3>
            <div className='categorias-conteiner'>

                {categorias.map((categoria, index) => (
                    <article className='card-categoria' key={index}>
                        <img className='img-categoria' src={categoria.imagen} alt={categoria.titulo} />
                        <span className='nombre-categoria'>{categoria.titulo}</span>
                    </article>
                ))}

            </div>
        </div>
    )
}

export default Categorias