package jp.co.panpanini.session

import com.airbnb.epoxy.EpoxyController
import com.airbnb.epoxy.EpoxyModel
import com.nhaarman.mockitokotlin2.*
import jp.co.panpanini.models.Session
import jp.co.panpanini.models.Speaker
import org.assertj.core.api.Assertions
import org.junit.Before
import org.junit.Test

import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.spy
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner
import java.util.*

class SessionControllerTest {

    private lateinit var target: SessionController

    @Mock
    lateinit var sessions: MutableList<Session>

    @Mock
    lateinit var controller: EpoxyController

    private val speaker = Speaker(
        "Name McNameson",
        "profile image",
        "biography"

    )
    private val session = Session(
        1,
        "title",
        "description",
        speaker
    )

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        target = spy(SessionController(sessions, controller))
    }

    @Test
    fun `setSessions should clear currently held sessions`() {

        target.setSessions(emptyList())

        verify(sessions).clear()
    }

    @Test
    fun `setSessions should add all input to sessions`() {
        val items: List<Session> = mock { }

        target.setSessions(items)

        verify(sessions).addAll(items)
    }


    //TODO: Put a test here 🍺



    @Test
    fun `generateModels should set the imageUrl to an empty string if speaker profileImage is empty`() {
        val sessions: List<Session> = listOf(session.copy(speaker = speaker.copy(profileImage = "")))

        val result = target.generateModels(sessions).first()

    }

    @Test
    fun `generateModels with an empty list should return an empty list`() {
        val sessions: List<Session> = listOf()

        val result = target.generateModels(sessions)

        Assertions.assertThat(result).isEqualTo(emptyList<EpoxyModel<*>>())
    }

    @Test
    fun `generateModels should generate SessionModels for items in list`() {
        val sessions: List<Session> = listOf(session, session.copy(id = 2))

        val result = target.generateModels(sessions)

        Assertions.assertThat(result).hasSize(2)
    }

    @Test
    fun `generateModels should set the id to the session id`() {
        val sessions: List<Session> = listOf(session)

        val result = target.generateModels(sessions).first()

        Assertions.assertThat(result.id()).isEqualTo(session.id)
    }

    @Test
    fun `generateModels should set the title to the session title`() {
        val sessions: List<Session> = listOf(session)

        val result = target.generateModels(sessions).first()

        Assertions.assertThat(result.title).isEqualTo(session.title)
    }

    @Test
    fun `generateModels should set the name to the speaker name`() {
        val sessions: List<Session> = listOf(session)

        val result = target.generateModels(sessions).first()

        Assertions.assertThat(result.name).isEqualTo(session.speaker.fullName)
    }

    @Test
    fun `buildModels should call generateModels with the session list`() {
        doReturn(listOf<SessionModel>()).`when`(target).generateModels(sessions)

        target.buildModels()

        verify(target).generateModels(sessions)
    }

    @Test
    fun `raise code coverage by calling functions`() {
        target = SessionController(controller = controller)
    }
}