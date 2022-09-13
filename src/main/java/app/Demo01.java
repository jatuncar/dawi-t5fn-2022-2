package app;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import model.Usuario;

public class Demo01 {

	public static void main(String[] args) {
		// Configurar --> obtiene la conexión (DAO)
		EntityManagerFactory fabrica = Persistence.createEntityManagerFactory("jpa_sesion01");
		EntityManager em = fabrica.createEntityManager();
		
		// empieza los procesos (transacción)  
		em.getTransaction().begin(); // --> registrar, actualizar, eliminar
		
		// proceso  -> registrar un nuevo Usuario
		// Usuario u = new Usuario(20, "Juan", "Perez", "jperez", "12345", "2000/05/15", 1, 1);
		Usuario u = new Usuario();
		u.setCodigo(20);
		u.setNombre("Juan");
		u.setApellido("Perez");
		u.setUsuario("jperez");
		u.setClave("12345");
		u.setFchnac("2000/05/15");
		u.setTipo(1);
		u.setEstado(1);
		
		em.persist(u);
		
		// confirmar los procesos
		em.getTransaction().commit();  // confirma los procesos
		
		// cerrar
		em.close();
	}

}
