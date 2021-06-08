package cc.lr.view;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.util.Messages;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.line.LineChartDataSet;
import org.primefaces.model.charts.line.LineChartModel;

import cc.lr.entidade.Transacao;
import cc.lr.servico.ServicoTransacao;

@Named
@RequestScoped
public class ChartJSView implements Serializable{

	private static final long serialVersionUID = 7507816021257824336L;

	private LineChartModel lineModel;
	
	@Inject
	private ServicoTransacao st;
	
	private List<Transacao> transacoes;
	
	private String numcar; 
	
	private String nome;
	
	private String credor;
	
	@PostConstruct
	public void init() {
		this.lineModel = new LineChartModel();
		//createLineModel();
		//createBarModel();
	}
	
	/**
	 * Cria o gráfico para buscar as transações por número de cartão de crédito digitado
	 */
	public void createLineModel() {
		lineModel = new LineChartModel();
		ChartData data = new ChartData();
		 
		DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");  
		
		LineChartDataSet dataSet = new LineChartDataSet();
		List<Object> values = new ArrayList<>();
		List<String> labels = new ArrayList<>();
		
		try {
			this.transacoes = st.buscarPorTransacao(numcar);
		} catch (Exception e) {
			Messages.addGlobalError(e.getMessage());
		}
		
		for(Transacao t:transacoes) {
			values.add(t.getValor());
			labels.add(dateFormat.format(t.getDataTransacao()));
		}
		
		dataSet.setData(values);
		dataSet.setFill(false);
        dataSet.setLabel("Valor da transação");
        data.addChartDataSet(dataSet);
        
        data.setLabels(labels);
        this.lineModel.setData(data);
	}
	
	/**
	 * Cria o gráfico para buscar as transações por nome de cliente
	 */
	public void createLineModel2() {
		lineModel = new LineChartModel();
		ChartData data = new ChartData();
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");  
		
		LineChartDataSet dataSet = new LineChartDataSet();
		List<Object> values = new ArrayList<>();
		List<String> labels = new ArrayList<>();
		
		try {
			this.transacoes = st.buscarCartaoPorCliente(nome);
		} catch (Exception e) {
			Messages.addGlobalError(e.getMessage());
		}
		
		for(Transacao t:transacoes) {
			values.add(t.getValor());
			labels.add(dateFormat.format(t.getDataTransacao()));
		}
		
		dataSet.setData(values);
		dataSet.setFill(false);
        dataSet.setLabel("Valor da transação");
        data.addChartDataSet(dataSet);
        
        data.setLabels(labels);
        this.lineModel.setData(data);
	}
	
	/**
	 * Cria o gráfico para buscar as transações por credor
	 */
	public void createLineModel3() {
		lineModel = new LineChartModel();
		ChartData data = new ChartData();
		 
		DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");  
		
		LineChartDataSet dataSet = new LineChartDataSet();
		List<Object> values = new ArrayList<>();
		List<String> labels = new ArrayList<>();
		
		try {
			this.transacoes = st.buscarCartaoPorCredor(credor);
		} catch (Exception e) {
			Messages.addGlobalError(e.getMessage());
		}
		
		for(Transacao t:transacoes) {
			values.add(t.getValor());
			labels.add(dateFormat.format(t.getDataTransacao()));
		}
		
		dataSet.setData(values);
		dataSet.setFill(false);
        dataSet.setLabel("Valor da transação");
        data.addChartDataSet(dataSet);
        
        data.setLabels(labels);
        this.lineModel.setData(data);
	}
	
	public LineChartModel getLineModel() {
		return lineModel;
	}

	public void setLineModel(LineChartModel lineModel) {
		this.lineModel = lineModel;
	}

	public List<Transacao> getLineCharts() {
		return transacoes;
	}

	public void setLineCharts(List<Transacao> lineCharts) {
		this.transacoes = lineCharts;
	}

	public String getNumcar() {
		return numcar;
	}

	public void setNumcar(String numcar) {
		this.numcar = numcar;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCredor() {
		return credor;
	}

	public void setCredor(String credor) {
		this.credor = credor;
	}
	
}
