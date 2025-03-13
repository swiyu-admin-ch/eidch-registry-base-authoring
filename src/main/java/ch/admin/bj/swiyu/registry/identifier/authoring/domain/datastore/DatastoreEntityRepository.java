/*
 * SPDX-FileCopyrightText: 2025 Swiss Confederation
 *
 * SPDX-License-Identifier: MIT
 */

package ch.admin.bj.swiyu.registry.identifier.authoring.domain.datastore;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DatastoreEntityRepository extends JpaRepository<DatastoreEntity, UUID> {
}
