/*
 * SPDX-FileCopyrightText: 2025 Swiss Confederation
 *
 * SPDX-License-Identifier: MIT
 */

package ch.admin.bj.swiyu.registry.identifier.authoring.common.config;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "did-authoring")
@Getter
@Setter
public class DidAuthoringProperties {

    /**
     * The format template to create a data read response for a did:web entry.
     * Format specifiers: {0} -> The entry ID as UUID
     */
    @NotNull
    @NotEmpty
    private String didWebTemplate;

    /**
     * The format template to create a data read response for a did:tdw entry.
     * Format specifiers: {0} -> The entry ID as UUID
     */
    @NotNull
    @NotEmpty
    private String didTdwRouteTemplate;
}
