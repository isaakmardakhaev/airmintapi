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
@Table(name = "notes")
class Note(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(name = "name")
    @Size(min = 1, max = 128, message = "Name must be between 1 and 128")
    @NotBlank(message = "Name must be not blank")
    var name: String,
    @ManyToOne
    @JoinColumn(name = "folder_id")
    @JsonBackReference(value = "folder-notes")
    var folder: Folder? = null,
    @OneToMany(
        mappedBy = "note",
        cascade = [CascadeType.ALL],
        fetch = FetchType.LAZY,
        orphanRemoval = true,
    )
    @JsonManagedReference(value = "note-nodes")
    @SQLRestriction("parent_id IS NULL")
    var nodes: MutableList<Node> = mutableListOf(),
    @ManyToOne
    @JoinColumn(name = "parent_id")
    @JsonBackReference(value = "note-children")
    var parent: Note? = null,
    @OneToMany(
        mappedBy = "parent",
        cascade = [CascadeType.ALL],
        fetch = FetchType.LAZY,
        orphanRemoval = true,
    )
    @JsonManagedReference(value = "note-children")
    var children: MutableList<Note> = mutableListOf(),
){
    fun addNode(node: Node) {
        nodes.add(node)
        node.note = this
    }

    fun removeNode(node: Node) {
        nodes.remove(node)
        node.note = null
    }

    fun addChild(note: Note) {
        children.add(note)
        note.parent = this
    }

    fun removeChild(note: Note) {
        children.remove(note)
        note.parent = null
    }
}