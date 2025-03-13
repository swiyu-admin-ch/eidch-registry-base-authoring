/*
 * SPDX-FileCopyrightText: 2025 Swiss Confederation
 *
 * SPDX-License-Identifier: MIT
 */

package ch.admin.bj.swiyu.registry.identifier.authoring.service.mapper;

import ch.admin.bj.swiyu.registry.identifier.authoring.api.DatastoreStatusDto;
import ch.admin.bj.swiyu.registry.identifier.authoring.domain.datastore.DatastoreStatus;
import org.junit.jupiter.api.Test;

import static ch.admin.bj.swiyu.registry.identifier.authoring.service.mapper.EnumMapper.mapEnum;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class EnumMapperTest {
    @Test
    void mapEnumTest() {
        assertAllValuesMapped(DatastoreStatus.class, DatastoreStatusDto.class);
    }

    private <T extends Enum<T>, S extends Enum<S>> void assertAllValuesMapped(Class<T> left, Class<S> right) {
        assertRightValuesAreMapped(left, right);
        assertLeftValuesAreMapped(left, right);
    }

    private static <T extends Enum<T>, S extends Enum<S>> void assertRightValuesAreMapped(
            Class<T> left,
            Class<S> right
    ) {
        for (S s : right.getEnumConstants()) {
            var result = mapEnum(left, s);
            assertNotNull(result);
        }
    }

    private static <T extends Enum<T>, S extends Enum<S>> void assertLeftValuesAreMapped(
            Class<T> left,
            Class<S> right
    ) {
        for (T t : left.getEnumConstants()) {
            var result = mapEnum(right, t);
            assertNotNull(result);
        }
    }

}
