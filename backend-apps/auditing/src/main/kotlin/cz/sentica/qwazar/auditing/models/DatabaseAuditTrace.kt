package cz.sentica.qwazar.auditing.models

import cz.sentica.qwazar.auditing.address.Address
import cz.sentica.qwazar.auditing.address.NULL_ADDRESS
import io.ebean.annotation.DbName
import java.util.*
import javax.persistence.*

@Converter(autoApply = true)
class AddressConverter : AttributeConverter<Address, String> {
    override fun convertToDatabaseColumn(attribute: Address): String = attribute.serialize()
    override fun convertToEntityAttribute(dbData: String): Address = Address.parse(dbData)
}

// @Converter(autoApply = true)
// class AddressConverter : ScalarTypeConverter<Address, String> {
//     // override fun convertToDatabaseColumn(attribute: Address): String = attribute.serialize()
//     // override fun convertToEntityAttribute(dbData: String): Address = Address.parse(dbData)
//     override fun getNullValue(): Address = NULL_ADDRESS
//     override fun unwrapValue(beanType: Address?) = (beanType ?: nullValue).serialize()
//     override fun wrapValue(scalarType: String?) = scalarType?.let { Address.parse(it) } ?: nullValue
// }

@Entity
@DbName("rest")
data class DatabaseAuditTrace(
    @Id
    var gid: String = UUID.randomUUID().toString(),

    @Convert(converter = AddressConverter::class)
    var resourceAddress: Address = NULL_ADDRESS,
) {
    override fun hashCode(): Int = gid.hashCode()
    override fun equals(other: Any?): Boolean = other is DatabaseAuditTrace && other.gid == this.gid
}
