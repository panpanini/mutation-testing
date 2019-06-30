package jp.co.panpanini.session

import androidx.annotation.ColorRes
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.EpoxyModelClass
import jp.co.panpanini.R
import jp.co.panpanini.uicomponents.SessionView

@EpoxyModelClass(layout = R.layout.model_session)
abstract class SessionModel : EpoxyModel<SessionView>() {

    @EpoxyAttribute
    lateinit var title: String

    @EpoxyAttribute
    lateinit var name: String

    @EpoxyAttribute
    var imageUrl: String? = null

    @EpoxyAttribute
    @ColorRes
    var backgroundColor: Int? = null

    override fun bind(view: SessionView) {
        super.bind(view)
        view.run {
            setSpeakerName(name)
            setTitle(title)
            setProfileImage(imageUrl)
            setBackgroundColor(backgroundColor)
        }
    }

}