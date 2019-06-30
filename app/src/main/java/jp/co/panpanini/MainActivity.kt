package jp.co.panpanini

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.epoxy.EpoxyController
import jp.co.panpanini.session.SessionController
import jp.co.panpanini.session.SessionViewModel

class MainActivity : AppCompatActivity() {

    private val recyclerView: RecyclerView
            get() = findViewById(R.id.recycler_view)

    private val controller = SessionController(controller = object: EpoxyController() {
        override fun buildModels() {
            this@MainActivity.buildModels()
        }
    })

    private val viewModel: SessionViewModel by lazy {
        TODO()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.sessions.observe(this, Observer { sessions ->
            if (sessions == null) return@Observer
            controller.setSessions(sessions)
        })
    }

    fun buildModels() {
        controller.buildModels()
    }


}