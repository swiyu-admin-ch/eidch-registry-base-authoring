/*
 * SPDX-FileCopyrightText: 2025 Swiss Confederation
 *
 * SPDX-License-Identifier: MIT
 */

package ch.admin.bj.swiyu.registry.identifier.authoring.service.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.List;

/**
 * Utility class for mapping enums which do not differ between boundaries (1:1).
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EnumMapper {

    public static <T extends Enum<T>, S extends Enum<S>> List<T> mapEnum(Class<T> enumType, Collection<S> source) {
        if (source == null) {
            return null;
        }
        return source.stream().map(g -> mapEnum(enumType, g)).toList();
    }

    public static <T extends Enum<T>, S extends Enum<S>> T mapEnum(Class<T> enumType, S source) {
        if (source == null) {
            return null;
        }
        return Enum.valueOf(enumType, source.toString());
    }

    public static <T extends Enum<T>> T mapEnum(Class<T> enumType, String source) {
        if (source == null) {
            return null;
        }

        try {
            return Enum.valueOf(enumType, source);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
