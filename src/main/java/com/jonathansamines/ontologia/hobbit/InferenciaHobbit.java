package com.jonathansamines.ontologia.hobbit;

import java.util.Iterator;
import java.util.List;

import org.apache.jena.graph.Triple;
import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.reasoner.Derivation;
import org.apache.jena.reasoner.rulesys.RuleDerivation;

public class InferenciaHobbit {
	public static final String nombreArchivoModelo = "hobbit.n3";
	public static final String nombreArchivoReglasInferencia = "reglas-inferencia.rules";
	
	public void inferir() {
		ModeloHobbit modelo = new ModeloHobbit(InferenciaHobbit.nombreArchivoModelo, InferenciaHobbit.nombreArchivoReglasInferencia);
		InfModel modeloInferencia = modelo.crearModeloDeInferencia();

		this.imprimirInferencias(modeloInferencia);
	}

	private void imprimirInferencias(InfModel modeloInferencia) {
		StmtIterator oraciones = modeloInferencia.listStatements();
		
		while (oraciones.hasNext())
		{
			Statement oracion = oraciones.nextStatement();
			Iterator<Derivation> derivaciones = modeloInferencia.getDerivation(oracion);
			
			while (derivaciones.hasNext()) {
		        RuleDerivation derivacion = (RuleDerivation) derivaciones.next();
				
				this.imprimirConclusion(oracion);
				this.imprimirCausas(derivacion);
		    }
		}
	}

	private void imprimirCausas(RuleDerivation derivacion) {
		List<Triple> reglas = derivacion.getMatches();

		System.out.println(" + Debido a: ");
		for(Triple regla: reglas) {
			System.out.println("    + " + regla.getSubject().getLocalName() + " " + regla.getPredicate().getLocalName() + " " + regla.getObject());
		}
	}

	private void imprimirConclusion(Statement oracion) {
		Resource sujeto = oracion.getSubject();
		Property predicado = oracion.getPredicate();
		RDFNode objeto = oracion.getObject();

		System.out.println("");
		System.out.println(" + Concluimos que: ");
        System.out.println( "    => " + sujeto.getLocalName() + " " + predicado.getLocalName() + " " + objeto.asResource().getLocalName() );
	}
}
