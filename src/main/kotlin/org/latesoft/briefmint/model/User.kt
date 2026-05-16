package org.latesoft.briefmint.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Entity
@Table(name = "users")
class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    @Column(name = "username", nullable = false)
    var username: String,
    @Column(name = "password", nullable = false)
    @JsonIgnore
    var password: String,
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    var role: Role,
    @OneToMany(
        mappedBy = "user",
        cascade = [CascadeType.ALL],
        fetch = FetchType.LAZY,
        orphanRemoval = true
    )
    @JsonManagedReference(value = "user-folders")
    var folders: MutableList<Folder> = mutableListOf(),
) {
    fun addFolder(folder: Folder) {
        folders.add(folder)
        folder.user = this
    }

    fun removeFolder(folder: Folder) {
        folders.remove(folder)
        folder.user = null
    }
}