package org.latesoft.briefmint.model

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Inheritance
import jakarta.persistence.InheritanceType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Entity
@Table(name = "nodes")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
abstract class Node(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne
    @JsonBackReference(value = "note-nodes")
    @JoinColumn(name = "note_id")
    var note: Note? = null,

    @ManyToOne
    @JsonBackReference(value = "node-parent-child")
    @JoinColumn(name = "parent_id")
    var parent: Node? = null,

    @OneToMany(
        mappedBy = "parent",
        cascade = [CascadeType.ALL],
        fetch = FetchType.LAZY,
        orphanRemoval = true,
    )
    @JsonManagedReference(value = "node-parent-child")
    val children: MutableList<Node> = mutableListOf(),
) {
    fun addChild(node: Node) {
        children.add(node)
        node.parent = this
    }

    fun removeChild(node: Node) {
        children.remove(node)
        node.parent = null
    }
}