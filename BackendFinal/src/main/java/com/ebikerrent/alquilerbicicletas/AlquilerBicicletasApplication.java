package com.ebikerrent.alquilerbicicletas;


import com.ebikerrent.alquilerbicicletas.entity.Caracteristica;
import com.ebikerrent.alquilerbicicletas.entity.Categoria;
import com.ebikerrent.alquilerbicicletas.entity.Usuario;
import com.ebikerrent.alquilerbicicletas.entity.Producto;
import com.ebikerrent.alquilerbicicletas.entity.Imagen;
import com.ebikerrent.alquilerbicicletas.repository.CaracteristicaRepository;
import com.ebikerrent.alquilerbicicletas.repository.CategoriaRepository;
import com.ebikerrent.alquilerbicicletas.repository.UsuarioRepository;
import com.ebikerrent.alquilerbicicletas.repository.ProductoRepository;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@SpringBootApplication
@RestController
public class AlquilerBicicletasApplication {
	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private CategoriaRepository categoriaRepository;
	@Autowired
	private CaracteristicaRepository caracteristicaRepository;
	@Autowired
	private ProductoRepository productoRepository;
	private static final Logger LOGGER= LoggerFactory.getLogger(AlquilerBicicletasApplication.class);
	public static void main(String[] args) throws SQLException, ClassNotFoundException {

		SpringApplication.run(AlquilerBicicletasApplication.class, args) ;
		LOGGER.info("---eBikeRent EJECUTANDOSE---");
		LOGGER.info("---eBikeRent EJECUTANDOSE---");
		LOGGER.info("---eBikeRent EJECUTANDOSE---");
		LOGGER.info("---eBikeRent EJECUTANDOSE---");
	}
	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	@PostConstruct
	public void inicializar() {
		inicializarAdmin();
		inicializarCategorias();
		inicializarCaracteriscticas();
		inicializarProductos();
	}

	public void inicializarAdmin(){
		if (usuarioRepository.findByMail("admin@admin.com") == null) {
			Usuario user = new Usuario();
			user.setNombre("Admin");
			user.setApellido("Admin");
			user.setMail("admin@admin.com");
			user.setTelefono("3101234567");
			user.setPassword("password");
			user.setEsAdmin(true);
			usuarioRepository.save(user);
		}
	}

	private void inicializarCategorias() {
		if (categoriaRepository.count() == 0) {
			Categoria categoria1 = new Categoria();
			categoria1.setTitulo("Ruta");
			categoria1.setImagen("https://i.imgur.com/Tjh74DN.jpeg");
			categoria1.setDescripcion("Diseñadas para andar largos recorridos sobre terreno pavimentado a gran velocidad");
			categoriaRepository.save(categoria1);

			Categoria categoria2 = new Categoria();
			categoria2.setTitulo("Montaña");
			categoria2.setImagen("https://i.imgur.com/UXFtFtq.jpeg");
			categoria2.setDescripcion("Son diseñadas para acompañarte en tus viajes y aventuras por terrenos irregulares");
			categoriaRepository.save(categoria2);

			Categoria categoria3 = new Categoria();
			categoria3.setTitulo("Urbana");
			categoria3.setImagen("https://i.imgur.com/gDfEDdj.jpeg");
			categoria3.setDescripcion("Diseñada para ser cómoda, segura y práctica para los desplazamientos diarios.");
			categoriaRepository.save(categoria3);
		}
	}

	private void inicializarCaracteriscticas() {
		if (caracteristicaRepository.count() == 0) {
			Caracteristica caracteristica1 = new Caracteristica();
			caracteristica1.setNombre("Ambulancia");
			caracteristica1.setIcono("https://i.imgur.com/dBMQaSM.png");
			caracteristicaRepository.save(caracteristica1);

			Caracteristica caracteristica2 = new Caracteristica();
			caracteristica2.setNombre("Bateria extra");
			caracteristica2.setIcono("https://i.imgur.com/SU5Qi9O.png");
			caracteristicaRepository.save(caracteristica2);

			Caracteristica caracteristica3 = new Caracteristica();
			caracteristica3.setNombre("GoPro");
			caracteristica3.setIcono("https://i.imgur.com/XTLd8JW.png");
			caracteristicaRepository.save(caracteristica3);

			Caracteristica caracteristica4 = new Caracteristica();
			caracteristica4.setNombre("Cafe/Mate");
			caracteristica4.setIcono("https://i.imgur.com/TWorg4O.png");
			caracteristicaRepository.save(caracteristica4);

			Caracteristica caracteristica6 = new Caracteristica();
			caracteristica6.setNombre("PlayList");
			caracteristica6.setIcono("https://i.imgur.com/MyulwjF.png");
			caracteristicaRepository.save(caracteristica6);

		}
	}

	private void inicializarProductos(){
		if(productoRepository.count() == 0){
			Set<Caracteristica> caracteristicasProductos = new HashSet<>();
			for(Caracteristica c: caracteristicaRepository.findAll()){
				caracteristicasProductos.add(c);
			}


			String nombresProductos[] = {
					"Bicicleta Gw Hyena Shimano L R29 21 Velocidades Negro",
					"Bicicleta todoterreno para hombre Rin 26 18 cambios negro",
					"Bicicleta todoterreno Rin 29 pulgadas Jeep Lhotse1en aluminio",
					"Bicicleta todoterreno Rin 27.5 pulgadas Benotto acero",
					"Bicicleta todoterreno Rin 29 pulgadas Benotto aluminio",
					"Bicicleta todoterreno Rin 26 pulgadas Benotto aluminio",
					"Bicicleta de Montaña Todoterreno2 Sforzo Rin 26 pulgadas",
					"Bicicleta Urbana Urbana38 Sforzo Rin 26 pulgadas",
					"Bicicleta Urbana Playera GW Sunday Rin 26 M",
					"Bicicleta Rin 26 En Aluminio 18 Cambios Suspensión Azul",
					"Bicicleta Urbana Urbana15 Sforzo Rin 26 pulgadas Mujer",
					"Bicicleta GW Dione L Rin 26 Suspensión Full y Frenos Disco",
					"Bicicleta Urbana Urbana51 Sforzo Rin 700C",
					"Bicicleta Playera Hombre Rin 26 18 Cambios Negra Azul",
					"Bicicleta Urbana Playera Parrilla y Canasta Rin 26 D/P",
					"Bicicleta Gw Flamma Ruta Freno Disco Hidraulico Shimano",
					"Bicicleta Roadmaster Fire Shimano L R700 18Vel Negro Azul",
					"Bicicleta Ruta Gw Flamma Shimano 56 R700 14Vel Negro",
					"Bicicleta Ruta GW K2 Rin 700 S Shimano Tourney 7V",
					"Bicicleta Roadmaster Fire Shimano L R700 18Vel Negro Rojo"
			};

			String categoriasProductos[] ={
					"Urbana",
					"Montaña",
					"Montaña",
					"Montaña",
					"Montaña",
					"Montaña",
					"Montaña",
					"Urbana",
					"Urbana",
					"Urbana",
					"Urbana",
					"Urbana",
					"Urbana",
					"Urbana",
					"Urbana",
					"Ruta",
					"Ruta",
					"Ruta",
					"Ruta",
					"Ruta"
			};
			String urlImagenes[] = {
					"https://falabella.scene7.com/is/image/FalabellaCO/gsc_119836696_2499037_1",
					"https://falabella.scene7.com/is/image/FalabellaCO/gsc_119331865_2308949_1",
					"https://falabella.scene7.com/is/image/FalabellaCO/39158269_1",
					"https://falabella.scene7.com/is/image/FalabellaCO/72852458_1",
					"https://falabella.scene7.com/is/image/FalabellaCO/72852462_2",
					"https://falabella.scene7.com/is/image/FalabellaCO/72852457_1",
					"https://falabella.scene7.com/is/image/FalabellaCO/16925139_1",
					"https://falabella.scene7.com/is/image/FalabellaCO/16925282_1",
					"https://falabella.scene7.com/is/image/FalabellaCO/gsc_120440375_2671398_1",
					"https://falabella.scene7.com/is/image/FalabellaCO/gsc_123979555_3807434_1",
					"https://falabella.scene7.com/is/image/FalabellaCO/16925221_1",
					"https://falabella.scene7.com/is/image/FalabellaCO/gsc_128241980_5120041_1",
					"https://falabella.scene7.com/is/image/FalabellaCO/16925295_1",
					"https://falabella.scene7.com/is/image/FalabellaCO/gsc_123981089_3807898_1",
					"https://falabella.scene7.com/is/image/FalabellaCO/gsc_119671996_2436039_1",
					"https://falabella.scene7.com/is/image/FalabellaCO/gsc_128149677_5113339_1",
					"https://falabella.scene7.com/is/image/FalabellaCO/gsc_119642504_2421600_1",
					"https://falabella.scene7.com/is/image/FalabellaCO/gsc_119830187_2496461_1",
					"https://falabella.scene7.com/is/image/FalabellaCO/gsc_121702812_3150389_1",
					"https://falabella.scene7.com/is/image/FalabellaCO/gsc_119642533_2421618_1"
			};

			for(int i=0; i< nombresProductos.length; i++){
				Categoria categoria = categoriaRepository.findByTitulo(categoriasProductos[i]);
				
				List<Imagen> imagenes = new ArrayList<>();

				for (int j = 0; j < 5 ; j++) {
					Imagen imagen = new Imagen();
					imagen.setTitulo("Imagen_" + j +" "+ nombresProductos[i]);
					if(j == 0){
						imagen.setUrlImg(urlImagenes[i]);
					}else {
						imagen.setUrlImg(urlImagenes[j]);
					}
					imagenes.add(imagen);
				}

				Producto pr = new Producto();
				pr.setNombre(nombresProductos[i].toUpperCase());
				pr.setDescripcion(nombresProductos[i]);
				pr.setImagenes(imagenes);
				pr.setCategoria(categoria);
				pr.setCaracteristicas(caracteristicasProductos);

				productoRepository.save(pr);
			}
		}
	}

}
