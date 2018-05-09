/*
 *
 *  * Copyright (c) 2013-2017 Atlanmod INRIA LINA Mines Nantes.
 *  * All rights reserved. This program and the accompanying materials
 *  * are made available under the terms of the Eclipse Public License v1.0
 *  * which accompanies this distribution, and is available at
 *  * http://www.eclipse.org/legal/epl-v10.html
 *  *
 *  * Contributors:
 *  *     Atlanmod INRIA LINA Mines Nantes - initial API and implementation
 *
 *
 */

package org.consistency.core.tests.unit;

import org.atlanmod.consistency.core.CompositeId;
import org.atlanmod.consistency.core.Id;
import org.atlanmod.consistency.core.IdBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class IdTest {

    private CompositeId cid;
    private CompositeId opposite;


    @BeforeEach
    void setup() {
        Id i785 = IdBuilder.fromInt(785);
        Id i33 = IdBuilder.fromInt(33);
        cid = i785.composeWith(i33);
        opposite = i33.composeWith(i785);
    }

    @Test
    void testEqualsIntId() {
        assertThat(IdBuilder.fromInt(55)).isEqualTo(IdBuilder.fromInt(55));
    }

    @Test
    void testEqualsShortId() {
        short s = 23;
        assertThat(IdBuilder.fromShort(s)).isEqualTo(IdBuilder.fromShort(s));
    }

    @Test
    void testEquals() {

        assertThat(cid).isEqualTo(IdBuilder.fromInt(785).composeWith(IdBuilder.fromInt(33)));
    }

    @Test
    void testEqualsToHimself() {
        assertThat(cid).isEqualTo(cid);
    }

    @Test
    void testNotEqual() {

        assertThat(cid).isNotEqualTo(opposite);
    }




}