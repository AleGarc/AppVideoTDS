package tests;
import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.BeforeEach;

import modelo.Usuario;
import modelo.Video;

class TestUsuario {

	Usuario user1;
	
	@BeforeEach
	public void inicialiar1() {
		user1 = new Usuario("Usuario Rodriguez", "05/04/2000", "usuariorodriguez@gmail.com", "uRodri", "1234");
	}
	
	@Test
	public void prueba1CrearUsuario() {
		assertEquals("Usuario Rodriguez", user1.getNombre_completo(),"resultado prueba1.1");
		assertEquals("05/04/2000", user1.getFecha_nacimiento(),"resultado prueba1.2");
		assertEquals("usuariorodriguez@gmail.com", user1.getEmail(),"resultado prueba1.3");
		assertEquals("uRodri", user1.getUsuario(),"resultado prueba1.4");
		assertEquals("1234", user1.getPassword(),"resultado prueba1.5");
	}

	Usuario user2;
	
	@BeforeEach
	public void inicialiar2() {
		user2 = new Usuario("Usuario Rodriguez", "05/04/2000", "usuariorodriguez@gmail.com", "uRodri", "1234");
	}
	
	@Test
	public void prueba2CompararUsuario() {
		assertEquals(true, user1.equals(user2),"resultado prueba2.1");
	}
	
	@Test
	public void prueba3CambiarNombre() {
		user1.setNombre_completo("Usuario Fernindez");
		assertEquals("Usuario Fernindez", user1.getNombre_completo(),"resultado prueba3.1");
	}
	
	@Test
	public void prueba4CambiarUsuario() {
		user1.setUsuario("usRodriguez");
		assertEquals("usRodriguez", user1.getUsuario(),"resultado prueba4.1");
	}
	
	@Test
	public void prueba5CambiarLoRestante() {
		user1.setEmail("userrodrigz@hotmail.es");
		user1.setFecha_nacimiento("03/07/1995");
		user1.setPassword("contrase123");
		assertEquals("userrodrigz@hotmail.es", user1.getEmail(),"resultado prueba5.1");
		assertEquals("03/07/1995", user1.getFecha_nacimiento(),"resultado prueba5.2");
		assertEquals("contrase123", user1.getPassword(),"resultado prueba5.3");
	}
	
	@Test
	public void prueba6Filtros(){
		assertEquals("NoFiltro", user1.getFiltroString(),"resultado prueba6.1");
		user1.setFiltro("FiltroMisListas");
		assertEquals("FiltroMisListas", user1.getFiltroString(),"resultado prueba6.2");
		user1.setFiltro("FiltroNombresLargos");
		assertEquals("FiltroNombresLargos", user1.getFiltroString(),"resultado prueba6.3");
	}
	
	@Test
	public void prueba7Premium() {
		assertEquals(false, user1.esPremium(),"resultado prueba7.1");
		user1.cambiarRol(true);
		assertEquals(true, user1.esPremium(),"resultado prueba7.2");
	}
	
	@Test
	public void prueba8CompararDeNuevo() {
		user1.setFecha_nacimiento("03/07/1995");
		assertEquals(false, user1.equals(user2),"resultado prueba8.1");
	}
	
	@BeforeEach
	public void inicialiar3() {
		Video video1 = new Video("titulo1", "url1");
		Video video2 = new Video("titulo2", "url2");
		Video video3 = new Video("titulo3", "url3");
		user1.addVideoReciente(video1);
		user1.addVideoReciente(video2);
		user1.addVideoReciente(video3);
		user2.addVideoReciente(video1);
		user2.addVideoReciente(video2);
		user2.addVideoReciente(video3);
	}
	
	@Test
	public void prueba9VideosRecientes() {
		assertEquals(true, user1.getVideosRecientes().equals(user2.getVideosRecientes()));
	}
}

