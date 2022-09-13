package app;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.Categoria;
import model.Producto;
import model.Proveedor;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;

public class FrmManteProd extends JFrame {

	private JPanel contentPane;

	private JTextArea txtSalida;
	private JTextField txtCodigo;
	private JComboBox cboCategorias;
	private JComboBox cboProveedores;
	private JTextField txtDescripcion;
	private JTextField txtStock;
	private JTextField txtPrecio;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrmManteProd frame = new FrmManteProd();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FrmManteProd() {
		setTitle("Mantenimiento de Productos");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 390);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnRegistrar = new JButton("Registrar");
		btnRegistrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				registrar();
			}
		});
		btnRegistrar.setBounds(324, 29, 89, 23);
		contentPane.add(btnRegistrar);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 171, 414, 143);
		contentPane.add(scrollPane);

		txtSalida = new JTextArea();
		scrollPane.setViewportView(txtSalida);

		JButton btnListado = new JButton("Listado");
		btnListado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listado();
			}
		});
		btnListado.setBounds(177, 322, 89, 23);
		contentPane.add(btnListado);

		txtCodigo = new JTextField();
		txtCodigo.setBounds(122, 11, 86, 20);
		contentPane.add(txtCodigo);
		txtCodigo.setColumns(10);

		JLabel lblCodigo = new JLabel("Id. Producto :");
		lblCodigo.setBounds(10, 14, 102, 14);
		contentPane.add(lblCodigo);

		cboCategorias = new JComboBox();
		cboCategorias.setBounds(122, 70, 86, 22);
		contentPane.add(cboCategorias);

		JLabel lblCategora = new JLabel("Categor\u00EDa");
		lblCategora.setBounds(10, 74, 102, 14);
		contentPane.add(lblCategora);

		JLabel lblNomProducto = new JLabel("Nom. Producto :");
		lblNomProducto.setBounds(10, 45, 102, 14);
		contentPane.add(lblNomProducto);

		txtDescripcion = new JTextField();
		txtDescripcion.setColumns(10);
		txtDescripcion.setBounds(122, 42, 144, 20);
		contentPane.add(txtDescripcion);

		JLabel lblStock = new JLabel("Stock:");
		lblStock.setBounds(10, 106, 102, 14);
		contentPane.add(lblStock);

		txtStock = new JTextField();
		txtStock.setColumns(10);
		txtStock.setBounds(122, 103, 77, 20);
		contentPane.add(txtStock);

		JLabel lblPrecio = new JLabel("Precio:");
		lblPrecio.setBounds(10, 134, 102, 14);
		contentPane.add(lblPrecio);

		txtPrecio = new JTextField();
		txtPrecio.setColumns(10);
		txtPrecio.setBounds(122, 131, 77, 20);
		contentPane.add(txtPrecio);

		JLabel lblProveedores = new JLabel("Proveedor:");
		lblProveedores.setBounds(230, 106, 102, 14);
		contentPane.add(lblProveedores);

		cboProveedores = new JComboBox();
		cboProveedores.setBounds(300, 104, 120, 22);
		contentPane.add(cboProveedores);

		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscar();
			}
		});
		btnBuscar.setBounds(324, 63, 89, 23);
		contentPane.add(btnBuscar);

		llenaCombo();
	}

	void llenaCombo() {
		// conexión
		EntityManagerFactory fabrica = Persistence.createEntityManagerFactory("jpa_sesion01");
		EntityManager em = fabrica.createEntityManager();
		// -- combo 1. listado de categorias
		List<Categoria> lstCategorias = em.createQuery("select c from Categoria c", Categoria.class).
				  getResultList();
		cboCategorias.addItem("Seleccione...");
		for (Categoria c : lstCategorias) {
			cboCategorias.addItem(c.getDescripcion());
		}
		// -- combo 2. listado de proveedores // DWIGHT ANTHONY HUANCA
		List<Proveedor> lstProveedor = em.createQuery("select c from Proveedor as c", Proveedor.class).getResultList();
		cboProveedores.addItem("seleccione...");
		for (Proveedor p: lstProveedor) {
			cboProveedores.addItem(p.getIdproveedor()+"-"+p.getNombre_rs());
		}
		
		em.close();
	}

	void registrar() {
		// leer los campos del form
		String codigo = leerCodigo();       // txtCodigo.getText();
		String nombre = txtDescripcion.getText();   // TAREA!!! Crear los métodos con sus validaciones
		int stock = Integer.parseInt(txtStock.getText());
		double precio = Double.parseDouble(txtPrecio.getText());
		int categoria = cboCategorias.getSelectedIndex();
		int proveedor = cboProveedores.getSelectedIndex();
		
		// crear un nuevo obj a grabar
		Producto p = new Producto();
		p.setId_prod(codigo);
		p.setDes_prod(nombre);
		p.setPre_prod(precio);
		p.setStk_prod(stock);
		p.setIdcategoria(categoria);
		p.setIdproveedor(proveedor);
		p.setEst_prod(1);  // valor fijo x default --> 1 : true / 0 : false
		
		// guardar el objeto en la tabla
		EntityManagerFactory fabrica = Persistence.createEntityManagerFactory("jpa_sesion01");
		EntityManager em = fabrica.createEntityManager();
		try {
			em.getTransaction().begin(); 
			em.persist(p);
			em.getTransaction().commit(); 
			// mostrar los mensajes de éxito o error.
			aviso("Producto registrado...!", "Aviso del sistema", JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception e) {
			aviso("Error al registrar Producto...!\n" + e.getMessage(), "Aviso del sistema", JOptionPane.ERROR_MESSAGE);
		}
		em.close();
	}

	private String leerCodigo() {
		// validaciones
		if (txtCodigo.getText().isEmpty()) {
			aviso("Debe ingresar un Código...!", "Error en campo", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		return txtCodigo.getText();
	}

	private void aviso(String msg, String titulo, int icono) {
		JOptionPane.showMessageDialog(this, msg, titulo, icono);
		
	}

	void listado() {
		// Mostrar el listado de los productos indicando las Fk  // BRANDON VILLEGAS!!!
		EntityManagerFactory fabrica = Persistence.createEntityManagerFactory("jpa_sesion01");
		EntityManager em = fabrica.createEntityManager();

		List<Producto> lstProducto = em.createQuery("select p from Producto p", Producto.class).
									  getResultList();
		System.out.println("-------------------------------------------------");
		for (Producto p : lstProducto) {
			imprimir("*******************");
			imprimir("Id Producto : " + p.getId_prod());
			imprimir("Descripcion : " + p.getDes_prod());
			imprimir("Stock : " + p.getStk_prod());
			imprimir("Precio : " + p.getPre_prod());
			imprimir("Categoria : " + p.getIdcategoria() + " - " + 
							p.getObjCategoria().getDescripcion());
			imprimir("Estado : " + p.getEst_prod());
			imprimir("Id Proveedor : " + p.getIdproveedor() + " - " + p.getObjProveedor().getNombre_rs() + "\n");
		}
		// cerrar
		em.close();
	}
	
	void imprimir(String s) {
		txtSalida.append(s + "\n");
	}

	void buscar() {
		// leer el código
		String codigo = leerCodigo();
		
		// obtener un producto según el código ingresado --> consulta
		EntityManagerFactory fabrica = Persistence.createEntityManagerFactory("jpa_sesion01");
		EntityManager em = fabrica.createEntityManager();
		
		// select * from tb_xxxx where idprod = xxxx
		Producto p = em.find(Producto.class, codigo);  // busca por ID
		
		// si el código no existe, muestra un mensaje, sino muestra los datos del producto
		if (p == null) {
			aviso("Código NO existe", "Aviso del sistema", JOptionPane.ERROR_MESSAGE);
		} else {
			txtDescripcion.setText(p.getDes_prod());
			txtStock.setText(p.getStk_prod()+"");
			// TAREA !!!! Completar
		}
		em.close();
	}
}







