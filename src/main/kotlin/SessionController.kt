class SessionController(
    private val sessions: MutableList<Session>
) : EpoxyController() {

    fun setSessions(sessions: List<Session>) {}

    override fun buildModels() {}

    fun generateModels(sessions: List<Session>): List<SessionModel> {}
}
