/*
 * SPDX-FileCopyrightText: 2025 Swiss Confederation
 *
 * SPDX-License-Identifier: MIT
 */

package ch.admin.bj.swiyu.registry.identifier.authoring.domain;

import ch.admin.bj.swiyu.registry.identifier.authoring.domain.datastore.DatastoreEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED) // JPA
@Table(name = "did_entity")
public class DidEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "base_id", referencedColumnName = "id")
    private DatastoreEntity base;

    @Column(name = "file_type")
    @Enumerated(EnumType.STRING)
    private DidType fileType;

    @Column(name = "content")
    private String content;

    @Column(name = "read_uri")
    private String readUri;

    public DidEntity(DatastoreEntity base, DidType fileType, String content, String readUri) {
        this.base = base;
        this.fileType = fileType;
        this.content = content;
        this.readUri = readUri;
    }

    public void updateContent(String content) {
        this.content = content;
    }
}
