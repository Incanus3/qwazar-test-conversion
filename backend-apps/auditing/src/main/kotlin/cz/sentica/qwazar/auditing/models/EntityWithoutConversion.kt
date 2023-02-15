package cz.sentica.qwazar.auditing.models

import io.ebean.annotation.DbName
import java.util.*
import javax.persistence.*

@Entity
@DbName("rest")
class EntityWithoutConversion(
    @Id
    var gid: String = UUID.randomUUID().toString(),
    var resourceAddress: String = "*",
) {
    override fun hashCode(): Int = gid.hashCode()
    override fun equals(other: Any?): Boolean = other is EntityWithoutConversion && other.gid == this.gid
}
