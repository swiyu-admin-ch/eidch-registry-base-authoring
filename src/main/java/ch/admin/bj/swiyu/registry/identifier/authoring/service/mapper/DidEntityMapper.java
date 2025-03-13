/*
 * SPDX-FileCopyrightText: 2025 Swiss Confederation
 *
 * SPDX-License-Identifier: MIT
 */

package ch.admin.bj.swiyu.registry.identifier.authoring.service.mapper;

import ch.admin.bj.swiyu.registry.identifier.authoring.api.DidEntityResponseDto;
import ch.admin.bj.swiyu.registry.identifier.authoring.domain.DidEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DidEntityMapper {

    public static DidEntityResponseDto toDidEntityResponseDto(DidEntity entity) {
        return new DidEntityResponseDto(entity.getReadUri(), entity.getContent() != null);
    }

    public static DidEntityResponseDto toNotConfiguredDidEntityResponseDto(String readUri) {
        return new DidEntityResponseDto(readUri, false);
    }
}
