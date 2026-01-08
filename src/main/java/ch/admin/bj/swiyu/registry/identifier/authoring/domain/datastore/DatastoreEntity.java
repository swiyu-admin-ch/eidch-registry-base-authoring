/*
 * SPDX-FileCopyrightText: 2025 Swiss Confederation
 *
 * SPDX-License-Identifier: MIT
 */

package ch.admin.bj.swiyu.registry.identifier.authoring.domain.datastore;

import ch.admin.bj.swiyu.registry.identifier.authoring.common.domain.AuditMetadata;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.Getter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@Table(name = "datastore_entity")
public class DatastoreEntity {

    @Embedded
    @Valid
    private final AuditMetadata auditMetadata = new AuditMetadata();

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
