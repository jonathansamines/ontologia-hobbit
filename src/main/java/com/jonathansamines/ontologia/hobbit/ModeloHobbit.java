package com.jonathansamines.ontologia.hobbit;

import java.util.List;

import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.rulesys.GenericRuleReasoner;
import org.apache.jena.reasoner.rulesys.Rule;

public class ModeloHobbit {
	
	public String nombreArchivoModelo;
	public String nombreArchivoReglasInferencia;
	
	public ModeloHobbit(String nombreArchivoModelo, String nombreArchivoReglasInferencia) {
		this.nombreArchivoModelo = nombreArchivoModelo;
		this.nombreArchivoReglasInferencia = nombreArchivoReglasInferencia;
	}

	public InfModel crearModeloDeInferencia() {
		// Cargamos el modelo en el archivo a memoria
		Model modelo = ModelFactory.createDefaultModel();
		modelo.read(this.nombreArchivoModelo);
		
		// Cargamos las reglas de inferencia en el archivo a memoria
		List<Rule> reglas= Rule.rulesFromURL(this.nombreArchivoReglasInferencia);
		Reasoner razonador = new GenericRuleReasoner(reglas);
		
		// Habilitamos el calculo de inferencias
		razonador.setDerivationLogging(true);
		
		return ModelFactory.createInfModel(razonador, modelo);
	}
}
