/*
 * SPDX-FileCopyrightText: 2025 Swiss Confederation
 *
 * SPDX-License-Identifier: MIT
 */

package ch.admin.bj.swiyu.registry.identifier.authoring.infrastructure.web.controller;

import ch.admin.bj.swiyu.registry.identifier.authoring.api.ApiErrorDto;
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
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/did")
@AllArgsConstructor
@Tag(name = "DID Controller", description = "Manages DID entries in the datastore.")
public class DidController {

    private final DidEntityService didEntityService;

    @Timed
    @PutMapping(value = "/{id}/did.json")
    @Operation(
            summary = "Update a did:web entry in the datastore.",
            description = "Update a did:web entry in the Datastore."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Success", useReturnTypeSchema = true),
                    @ApiResponse(
                            responseCode = "425",
                            description = "Too Early, Resource cannot be edited.",
                            content = @Content(schema = @Schema(implementation = ApiErrorDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not Found",
                            content = @Content(schema = @Schema(implementation = ApiErrorDto.class))
                    ),
            }
    )
    public DatastoreEntityResponseDto updateDidWebEntry(@Valid @PathVariable UUID id, @RequestBody String body) {
        return didEntityService.updateDidWebEntry(id, body);
    }

    @Timed
    @PutMapping(value = "/{id}/did.jsonl")
    @Operation(
            summary = "Update a did:tdw entry in the datastore.",
            description = "Update a did:tdw entry in the Datastore."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Success", useReturnTypeSchema = true),
                    @ApiResponse(
                            responseCode = "425",
                            description = "Too Early, Resource cannot be edited.",
                            content = @Content(schema = @Schema(implementation = ApiErrorDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not Found",
                            content = @Content(schema = @Schema(implementation = ApiErrorDto.class))
                    ),
            }
    )
    public DatastoreEntityResponseDto updateDidTdwEntry(@Valid @PathVariable UUID id, @RequestBody String body) {
        return didEntityService.updateDidTdwEntry(id, body);
    }
}
