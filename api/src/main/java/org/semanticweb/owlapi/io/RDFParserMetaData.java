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
package org.semanticweb.owlapi.io;

import static org.semanticweb.owlapi.utilities.OWLAPIPreconditions.checkNotNull;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import org.semanticweb.owlapi.model.IRI;

/**
 * @author Matthew Horridge, The University of Manchester, Bio-Health Informatics Group
 * @since 3.2
 */
public class RDFParserMetaData implements OWLOntologyLoaderMetaData, Serializable {

    private final int tripleCount;
    private final RDFOntologyHeaderStatus headerStatus;
    private final Set<RDFTriple> unparsedTriples;
    private final Map<IRI, List<Class<?>>> guessedDeclarations;
    // TODO make something of these
    private final Set<RDFResourceParseError> errors = new HashSet<>();

    /**
     * @param headerStatus the header status
     * @param tripleCount the triple count
     * @param unparsedTriples the set of triples not parsed
     * @param guessedDeclarations guessed declarations map
     */
    public RDFParserMetaData(RDFOntologyHeaderStatus headerStatus, int tripleCount,
        Set<RDFTriple> unparsedTriples, Map<IRI, List<Class<?>>> guessedDeclarations) {
        this.tripleCount = tripleCount;
        this.headerStatus = checkNotNull(headerStatus, "headerStatus cannot be null");
        this.unparsedTriples = checkNotNull(unparsedTriples, "unparsedTriples cannot be null");
        this.guessedDeclarations =
            checkNotNull(guessedDeclarations, "guessedDeclarations cannot be null");
    }

    @Override
    public int getTripleCount() {
        return tripleCount;
    }

    @Override
    public RDFOntologyHeaderStatus getHeaderState() {
        return headerStatus;
    }

    @Override
    public Stream<RDFTriple> getUnparsedTriples() {
        return unparsedTriples.stream();
    }

    @Override
    public Map<IRI, List<Class<?>>> getGuessedDeclarations() {
        return Collections.unmodifiableMap(guessedDeclarations);
    }

    @Override
    public void addError(RDFResourceParseError error) {
        errors.add(error);
    }
}

