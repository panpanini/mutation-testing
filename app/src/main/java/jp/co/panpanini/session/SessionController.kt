package jp.co.panpanini.session

import androidx.annotation.VisibleForTesting
import com.airbnb.epoxy.EpoxyController
import jp.co.panpanini.R
import jp.co.panpanini.models.Session
import java.util.*

class SessionController(
    private val sessions: MutableList<Session> = mutableListOf(),
    private val controller: EpoxyController
) {

    fun setSessions(sessions: List<Session>) {
        this.sessions.clear()
        this.sessions.addAll(sessions)
        controller.requestModelBuild()
    }

    @VisibleForTesting
    fun buildModels() {
        generateModels(sessions)
            .forEach { it.addTo(controller) }
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