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

package fr.atlanmod.consistency.core.tests.unit;

import fr.inria.atlanmod.consistency.core.CompositeId;
import fr.inria.atlanmod.consistency.core.Id;
import fr.inria.atlanmod.consistency.core.IdBuilder;
import fr.inria.atlanmod.consistency.core.IntegerId;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class IdTest {

    private CompositeId cid, opposite;


    @Before
    public void setup() {
        Id i785 = IdBuilder.fromInt(785);
        Id i33 = IdBuilder.fromInt(33);
        cid = i785.composeWith(i33);
        opposite = i33.composeWith(i785);
    }

    @Test
    public void testEqualsIntId() {
        assertThat(IdBuilder.fromInt(55)).isEqualTo(IdBuilder.fromInt(55));
    }

    @Test
    public void testEqualsShortId() {
        short s = 23;
        assertThat(IdBuilder.fromShort(s)).isEqualTo(IdBuilder.fromShort(s));
    }

    @Test
    public void testEquals() {
        assertThat(cid).isEqualTo(cid);
        assertThat(cid).isNotEqualTo(opposite);
        assertThat(cid).isEqualTo(IdBuilder.fromInt(785).composeWith(IdBuilder.fromInt(33)));
    }


}