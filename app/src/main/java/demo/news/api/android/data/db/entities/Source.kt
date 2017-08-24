package demo.news.api.android.data.db.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by Frederick on 24/08/2017.
 */
@Entity(tableName = "sources")
class Source {

    @PrimaryKey
    var id: String
    var name: String
    var description: String
    var category: String
    var url: String

    constructor(id: String, name: String, description: String, category: String, url: String) {
        this.id = id
        this.name = name
        this.description = description
        this.category = category
        this.url = url
    }
}