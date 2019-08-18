/*
 * This file is part of the OWL API.
 *
 * The contents of this file are subject to the LGPL License, Version 3.0.
 *
 * Copyright (C) 2011, The University of Manchester
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0
 * in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 *
 * Copyright 2011, University of Manchester
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.ac.manchester.cs.owl.owlapi;

import java.util.Collection;
import java.util.Set;
import java.util.logging.Logger;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAxiomVisitor;
import org.semanticweb.owlapi.model.OWLAxiomVisitorEx;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLMetamodellingAxiom;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLObjectVisitorEx;


/**
 * Author: Ignacio Vidal<br>
 * FIng - UdelaR<br>
 * Date: 25-Oct-2014<br><br>
 */
public class OWLMetamodellingAxiomImpl extends OWLClassAxiomImpl implements OWLMetamodellingAxiom {

	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(OWLMetamodellingAxiomImpl.class.getName());	
	private static final long serialVersionUID = 30402L;
	
	private final OWLClassExpression modelClass;
    private final OWLIndividual metamodelIndividual;

    @SuppressWarnings("javadoc")
    public OWLMetamodellingAxiomImpl(OWLClassExpression modelClass, OWLIndividual metamodel, Collection<? extends OWLAnnotation> annotations) {
        super(annotations);
        this.modelClass = modelClass;
        this.metamodelIndividual = metamodel;
    }

    @Override
    public OWLMetamodellingAxiom getAnnotatedAxiom(Set<OWLAnnotation> annotations) {
        return getOWLDataFactory().getOWLMetamodellingAxiom(modelClass, metamodelIndividual, mergeAnnos(annotations));
    }

    @Override
    public OWLMetamodellingAxiom getAxiomWithoutAnnotations() {
        if (!isAnnotated()) {
            return this;
        }
        return getOWLDataFactory().getOWLMetamodellingAxiom(modelClass, metamodelIndividual);
    }

    @Override
    public OWLClassExpression getModelClass() {
        return modelClass;
    }

    @Override
    public OWLIndividual getMetamodelIndividual() {
        return metamodelIndividual;
    }

    @Override
    public boolean isGCI() {
        return modelClass.isAnonymous();
    }

    @Override
	public boolean equals(Object obj) {
    	if (!(obj instanceof OWLMetamodellingAxiom)) {
            return false;
        }
    	if(super.equals(obj)) {

        OWLMetamodellingAxiom other = (OWLMetamodellingAxiom) obj;
        return other.getModelClass().equals(modelClass) && other.getMetamodelIndividual().equals(metamodelIndividual);
    	}
    	return false;
    }

    @Override
    public void accept(OWLAxiomVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public void accept(OWLObjectVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public <O> O accept(OWLAxiomVisitorEx<O> visitor) {
        return visitor.visit(this);
    }

    @Override
    public <O> O accept(OWLObjectVisitorEx<O> visitor) {
        return visitor.visit(this);
    }

    @Override
    public AxiomType<?> getAxiomType() {
        return AxiomType.METAMODELLING;
    }

    @Override
	protected int compareObjectOfSameType(OWLObject object) {
        OWLMetamodellingAxiom other = (OWLMetamodellingAxiom) object;
        int diff = modelClass.compareTo(other.getModelClass());
        if (diff != 0) {
            return diff;
        }
        return metamodelIndividual.compareTo(other.getMetamodelIndividual());
    }
}
