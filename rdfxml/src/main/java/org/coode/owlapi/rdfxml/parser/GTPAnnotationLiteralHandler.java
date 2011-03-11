package org.coode.owlapi.rdfxml.parser;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationSubject;
import org.semanticweb.owlapi.model.OWLLiteral;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 09-Dec-2006<br><br>
 */
public class GTPAnnotationLiteralHandler extends AbstractLiteralTripleHandler {

    public GTPAnnotationLiteralHandler(OWLRDFConsumer consumer) {
        super(consumer);
    }


    @Override  @SuppressWarnings("unused")
	public boolean canHandleStreaming(IRI subject, IRI predicate, OWLLiteral object) {
        return !isAnonymous(subject) && !getConsumer().isAnnotation(subject) && getConsumer().isAnnotationProperty(predicate);
    }


    @Override  @SuppressWarnings("unused")
	public boolean canHandle(IRI subject, IRI predicate, OWLLiteral object) {
        if(isStrict()) {
            return isAnnotationPropertyOnly(predicate);
        }
        boolean axiom = getConsumer().isAxiom(subject);
        if(axiom) {
            return false;
        }
        boolean annotation = getConsumer().isAnnotation(subject);
        if(annotation) {
            return false;
        }
        if(isAnnotationPropertyOnly(predicate)) {
            return true;
        }
        if (!isAnonymous(subject)) {
            if(isClassExpressionLax(subject)) {
                return true;
            }
            if(isDataRangeLax(subject)) {
                return true;
            }
            if(isObjectPropertyLax(subject)) {
                return true;
            }
            if(isDataPropertyLax(subject)) {
                return true;
            }
        }
        return false;
    }


    @Override
	public void handleTriple(IRI subject, IRI predicate, OWLLiteral object) {
        consumeTriple(subject, predicate, object);
        OWLAnnotationProperty prop = getDataFactory().getOWLAnnotationProperty(predicate);
        OWLAnnotationSubject annotationSubject;
        if(isAnonymous(subject)) {
            annotationSubject = getDataFactory().getOWLAnonymousIndividual(subject.toString());
        }
        else {
            annotationSubject = subject;
        }
        if(getConsumer().isOntology(subject)) {
        	getConsumer().addOntologyAnnotation(getDataFactory().getOWLAnnotation(prop, object, getPendingAnnotations()));
        }
        else {
            OWLAnnotationAssertionAxiom ax = getDataFactory().getOWLAnnotationAssertionAxiom(prop, annotationSubject, object, getPendingAnnotations());
            addAxiom(ax);
        }
    }
}
