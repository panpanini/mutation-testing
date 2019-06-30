class SessionController(
    private val sessions: MutableList<Session>
) : EpoxyController() {

    fun setSessions(sessions: List<Session>) {
        this.sessions.clear()
        this.sessions.addAll(sessions)
        requestModelBuild()
    }

    @VisibleForTesting
    override fun buildModels() {
        generateModels(sessions)
            .forEach { it.addTo(this) }
    }

    @VisibleForTesting
    fun generateModels(sessions: List<Session>): List<SessionModel> {
        return sessions
            .map { session ->
                SessionModel_()
                    .id(session.id)
                    .title(session.title)
                    .name(session.speaker.fullName)
                    .imageUrl(
                        if (session.speaker.profileImage != "") {
                            session.speaker.profileImage
                        } else {
                            null
                        }
                    )
            }
    }
}
