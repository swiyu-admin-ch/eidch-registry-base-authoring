/*
 * SPDX-FileCopyrightText: 2025 Swiss Confederation
 *
 * SPDX-License-Identifier: MIT
 */

package ch.admin.bj.swiyu.registry.identifier.authoring.api;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "DidEntityResponse")
public record DidEntityResponseDto(
        String readUri,
        Boolean isConfigured
) {

}
