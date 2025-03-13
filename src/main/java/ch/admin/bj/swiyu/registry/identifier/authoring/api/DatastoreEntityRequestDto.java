/*
 * SPDX-FileCopyrightText: 2025 Swiss Confederation
 *
 * SPDX-License-Identifier: MIT
 */

package ch.admin.bj.swiyu.registry.identifier.authoring.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Optional;

@Schema(name = "DatastoreEntityRequest")
public record DatastoreEntityRequestDto(DatastoreStatusDto status) {

    @JsonIgnore
    public Optional<DatastoreStatusDto> getStatus() {
        return Optional.ofNullable(status);
    }
}