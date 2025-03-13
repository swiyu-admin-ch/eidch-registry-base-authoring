/*
 * SPDX-FileCopyrightText: 2025 Swiss Confederation
 *
 * SPDX-License-Identifier: MIT
 */

package ch.admin.bj.swiyu.registry.identifier.authoring.domain.datastore;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.UUID;

@Entity
@Getter
@Table(name = "datastore_entity")
public class DatastoreEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private DatastoreStatus status;

    public DatastoreEntity() {
        this.status = DatastoreStatus.SETUP;
    }

    public void changeStatus(DatastoreStatus status) {
        this.status = status;
    }
}
