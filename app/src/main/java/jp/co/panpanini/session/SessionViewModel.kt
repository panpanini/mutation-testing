package jp.co.panpanini.session

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import jp.co.panpanini.models.Session

class SessionViewModel : ViewModel() {

    private val _sessions = MutableLiveData<List<Session>>()
    val sessions: LiveData<List<Session>> = _sessions
}