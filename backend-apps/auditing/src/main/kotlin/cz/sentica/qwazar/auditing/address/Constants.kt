package cz.sentica.qwazar.auditing.address

// TODO: add convertors where used in db entities
// - querying by converted properties doesn't seem to work yet, see https://github.com/ebean-orm/ebean/issues/2944

internal const val ADDRESS_WILDCARD = "*"
const val ANY_NAMESPACE_STRING = ADDRESS_WILDCARD
const val ANY_ADDRESS_STRING = ADDRESS_WILDCARD

const val ANY_NAMESPACE_STRING_OLD = "_internal_any_namespace"
const val ANY_ADDRESS_STRING_OLD = "_internal_any_address"

const val NOT_RELEVANT_STRING_OLD = "_internal_not_relevant_parameter"
const val NOT_RELEVANT = ADDRESS_WILDCARD

val ANY_NAMESPACE = Namespace.parse(ANY_NAMESPACE_STRING)

const val RESOURCETYPE_SEPARATOR = ":" // separates resource type from the rest of address
const val NAMESPACE_SEPARATOR = "/" // separates namespace from identifier
const val SUBSEGMENT_SEPARATOR = "." // separates subsegments of namespace or identifier

@Suppress("ClassName")
object ANY_IDENTIFIER : Identifier(NOT_RELEVANT) {
    override val first = NOT_RELEVANT
    override val second = NOT_RELEVANT
    override val third = NOT_RELEVANT
}

val ANY_ADDRESS = Address(
    resourceType = ResourceType.Any,
    namespace = ANY_NAMESPACE,
    identifier = ANY_IDENTIFIER,
)
val NULL_ADDRESS = Address(
    resourceType = ResourceType.None,
    namespace = Namespace(),
    identifier = Identifier(),
)
