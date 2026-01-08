/*
 * SPDX-FileCopyrightText: 2025 Swiss Confederation
 *
 * SPDX-License-Identifier: MIT
 */

package ch.admin.bj.swiyu.registry.identifier.authoring.service.mapper;

import static ch.admin.bj.swiyu.registry.identifier.authoring.service.mapper.EnumMapper.mapEnum;

import ch.admin.bj.swiyu.registry.identifier.authoring.api.DatastoreEntityResponseDto;
import ch.admin.bj.swiyu.registry.identifier.authoring.api.DatastoreStatusDto;
import ch.admin.bj.swiyu.registry.identifier.authoring.api.DidEntityResponseDto;
import ch.admin.bj.swiyu.registry.identifier.authoring.domain.datastore.DatastoreEntity;
import java.util.Map;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DatastoreEntityMapper {

    public static DatastoreEntityResponseDto toDatastoreEntityResponseDto(
        DatastoreEntity entity,
        Map<String, DidEntityResponseDto> files
    ) {
        return new DatastoreEntityResponseDto(
            entity.getId(),
            mapEnum(DatastoreStatusDto.class, entity.getStatus()),
            files
        );
    }
}
