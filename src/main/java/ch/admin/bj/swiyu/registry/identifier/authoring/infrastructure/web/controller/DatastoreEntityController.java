/*
 * SPDX-FileCopyrightText: 2025 Swiss Confederation
 *
 * SPDX-License-Identifier: MIT
 */

package ch.admin.bj.swiyu.registry.identifier.authoring.infrastructure.web.controller;

import ch.admin.bj.swiyu.registry.identifier.authoring.api.ApiErrorDto;
import ch.admin.bj.swiyu.registry.identifier.authoring.api.DatastoreEntityRequestDto;
import ch.admin.bj.swiyu.registry.identifier.authoring.api.DatastoreEntityResponseDto;
import ch.admin.bj.swiyu.registry.identifier.authoring.service.DidEntityService;
import io.micrometer.core.annotation.Timed;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/entry")
@AllArgsConstructor
@Tag(name = "Datastore", description = "Manages entries in the Datastore.")
public class DatastoreEntityController {

    private final DidEntityService didEntityService;

    @Timed
    @GetMapping(value = "/{id}")
    @Operation(
            summary = "Get an entry configuration from the Datastore.",
            description = "Get an entry configuration from the Datastore."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Success", useReturnTypeSchema = true),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not Found",
                            content = @Content(schema = @Schema(implementation = ApiErrorDto.class))
                    ),
            }
    )
    public DatastoreEntityResponseDto getDatastoreEntry(@Valid @PathVariable(name = "id") UUID id) {
        return didEntityService.getDatastoreEntity(id);
    }

    @Timed
    @PostMapping(value = "/")
    @Operation(summary = "Create a new entry in the Datastore.", description = "Create a new entry in the Datastore.")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Success", useReturnTypeSchema = true)})
    @ResponseStatus(HttpStatus.CREATED)
    public DatastoreEntityResponseDto createDatastoreEntry() {
        return this.didEntityService.createDatastoreEntity();
    }

    @Timed
    @PatchMapping(value = "/{id}")
    @Operation(
            summary = "Update an entry configuration from the Datastore.",
            description = "Update an entry configuration from the Datastore."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Success", useReturnTypeSchema = true),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not Found",
                            content = @Content(schema = @Schema(implementation = ApiErrorDto.class))
                    ),
            }
    )
    public DatastoreEntityResponseDto updateDatastoreEntry(
            @Valid @PathVariable(name = "id") UUID id,
            @RequestBody DatastoreEntityRequestDto body
    ) {
        return didEntityService.updateDatastoreEntity(id, body);
    }
}
