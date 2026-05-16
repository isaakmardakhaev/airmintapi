package org.latesoft.briefmint.model

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import org.hibernate.annotations.SQLRestriction

@Entity
@Table(name = "folders")
class Folder(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "name")
    @Size(min = 1, max = 128, message = "Name must be between 1 and 128")
    @NotBlank(message = "Name must be not blank")
    var name: String,

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference(value = "user-folders")
    var user: User? = null,

    @OneToMany(
        mappedBy = "folder",
        cascade = [CascadeType.ALL],
        fetch = FetchType.LAZY,
        orphanRemoval = true
    )
    @JsonManagedReference("folder-notes")
    @SQLRestriction("parent_id IS NULL")
    val notes: MutableList<Note> = mutableListOf(),
) {
    fun addNote(note: Note) {
        this.notes.add(note)
        note.folder = this
    }

    fun removeNote(note: Note) {
        this.notes.remove(note)
        note.folder = null
    }
}