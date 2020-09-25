/* This file is part of the OWL API.
 * The contents of this file are subject to the LGPL License, Version 3.0.
 * Copyright 2014, The University of Manchester
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0 in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License. */
package org.semanticweb.owlapi.api.test.axioms;

import static org.junit.Assert.*;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.*;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Nonnull;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.AbstractFileRoundTrippingTestCase;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLMetamodellingAxiom;
import org.semanticweb.owlapi.model.OWLNamedIndividual;

/**
 * @author Matthew Horridge, The University Of Manchester, Information
 *         Management Group
 * @since 2.2.0
 */
@SuppressWarnings("javadoc")
public class MetamodellingAxiomTestCase extends AbstractFileRoundTrippingTestCase {

    @Nonnull
    @Override
    protected String getFileName() {
        return "MetaModelling.rdf";
    }

    @Test
    public void testCorrectAxioms() {
        Set<OWLAxiom> axioms = new HashSet<>();

        OWLClass A_modelClass = Class(iri("A"));
        OWLNamedIndividual a_metamodel = NamedIndividual(iri("a"));
        OWLClass B_modelClass = Class(iri("B"));
        OWLNamedIndividual b_metamodel = NamedIndividual(iri("b"));

        axioms.add(Declaration(a_metamodel));
        axioms.add(Declaration(b_metamodel));
        axioms.add(Declaration(A_modelClass));
        axioms.add(Declaration(B_modelClass));
        axioms.add(SubClassOf(B_modelClass, A_modelClass));
        axioms.add(Metamodelling(A_modelClass, a_metamodel));
        axioms.add(Metamodelling(B_modelClass, b_metamodel));

        assertEquals(getOnt().getAxioms(), axioms);
    }

    /** Tests the isGCI method on OWLSubClassAxiom */
    @Test
    public void testIsGCIMethod() {
        OWLClass clsA = Class(iri("A"));
        OWLClass clsB = Class(iri("B"));
        OWLNamedIndividual metamodel = NamedIndividual(iri("a"));
        OWLClassExpression desc = ObjectIntersectionOf(clsA, clsB);
        OWLMetamodellingAxiom ax1 = Metamodelling(clsA, metamodel);
        assertFalse(ax1.isGCI());
        OWLMetamodellingAxiom ax2 = Metamodelling(desc, metamodel);
        assertTrue(ax2.isGCI());
    }
}
