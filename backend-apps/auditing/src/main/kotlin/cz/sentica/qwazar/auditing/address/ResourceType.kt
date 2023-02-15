package cz.sentica.qwazar.auditing.address

enum class ResourceType(val requiredSegmentCount: Int) {
    Registry(1),
    Artifact(2),
    Slot(3),
    WfTransition(3),
    Any(0),
    None(0);

    class Invalid(name: String) : IllegalArgumentException("$name is not a valid ResourceType name")

    companion object {
        fun from(name: String) = values().find { it.name == name } ?: throw Invalid(name)
    }
}
