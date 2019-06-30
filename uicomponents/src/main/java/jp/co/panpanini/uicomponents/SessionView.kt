package jp.co.panpanini.uicomponents

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.constraintlayout.widget.ConstraintLayout


class SessionView : ConstraintLayout {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    private val title: TextView
        get() = findViewById(R.id.title)

    private val speakerName: TextView
        get() = findViewById(R.id.speaker_name)

    private val profileImage: ImageView
        get() = findViewById(R.id.profile)

    init {
        LayoutInflater.from(context).inflate(R.layout.view_session, this, true)
    }

    fun setTitle(title: String) {
        this.title.text = title
    }

    fun setSpeakerName(name: String) {
        speakerName.text = name
    }

    fun setProfileImage(imageUrl: String?) {
        TODO()
    }

    fun setBackgroundColor(@ColorRes background: Int?) {
        background?.let(::setBackgroundColor)
    }
}