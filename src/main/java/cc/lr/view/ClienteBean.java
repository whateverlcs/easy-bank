package cc.lr.view;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.util.Messages;

import cc.lr.entidade.Cliente;
import cc.lr.servico.ServicoCliente;

@Named
@ViewScoped
public class ClienteBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3931595754160441225L;
	
	@Inject
	private ServicoCliente servicoCliente;
	
	private List<Cliente> clientes;
	
	private Cliente cliente;
	
	public ClienteBean() {
	}
	
	@PostConstruct
	public void init() {
		this.cliente = new Cliente();
		this.atualizarClientes();
	}
	
	private void atualizarClientes() {
		this.clientes = this.servicoCliente.listar();
	}
	
	public void cadastrar() {
		try {
			this.servicoCliente.cadastrar(this.cliente);
			this.cliente = new Cliente();
			this.atualizarClientes();
			Messages.addGlobalInfo("Cliente cadastrado com sucesso!");
		}catch (Exception e) {
			Messages.addGlobalWarn(e.getMessage());
		}
	}
	
	public void excluir(Cliente cliente) {
		try {
			this.servicoCliente.excluir(cliente);
			this.atualizarClientes();
			Messages.addGlobalInfo("Cliente excluído com sucesso!");
		}catch (Exception e) {
			Messages.addGlobalWarn(e.getMessage());
		}
	}

	public ServicoCliente getServicoCliente() {
		return servicoCliente;
	}

	public void setServicoCliente(ServicoCliente servicoCliente) {
		this.servicoCliente = servicoCliente;
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
}
