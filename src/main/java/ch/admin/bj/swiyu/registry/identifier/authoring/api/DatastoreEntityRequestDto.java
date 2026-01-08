/*
 * SPDX-FileCopyrightText: 2025 Swiss Confederation
 *
 * SPDX-License-Identifier: MIT
 */

package ch.admin.bj.swiyu.registry.identifier.authoring.api;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "DatastoreEntityRequest")
public class DatastoreEntityRequestDto {

    private DatastoreStatusDto status;

    public Optional<DatastoreStatusDto> getStatus() {
        return Optional.ofNullable(status);
    }
}
