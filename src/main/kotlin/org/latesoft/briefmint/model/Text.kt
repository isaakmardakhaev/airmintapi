package org.latesoft.briefmint.model

import jakarta.persistence.Column
import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity

@Entity
@DiscriminatorValue("TEXT")
class Text(
    @Column(name = "body", nullable = false)
    var body: String = "",
    note: Note? = null,
    parent: Node? = null
) : Node(note = note, parent = parent) {}