package org.latesoft.briefmint.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import org.hibernate.validator.constraints.URL

@Entity
class ImageByLink(
    @Column(name = "url")
    @URL(message = "Invalid URL format")
    var url: String = "",
    note: Note? = null,
    parent: Node? = null
) : Node(note = note, parent = parent) {}