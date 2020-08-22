package np.com.naveenniraula.zoomsdk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import us.zoom.sdk.*

class MainActivity : AppCompatActivity(), ZoomSDKInitializeListener, ZoomSDKAuthenticationListener {

    private lateinit var msg: TextView

    private lateinit var startMeeting: Button
    private lateinit var joinMeeting: Button

    private lateinit var meetingId: EditText
    private lateinit var meetingPassword: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        joinMeeting = findViewById(R.id.button5)
        meetingId = findViewById(R.id.editTextTextPersonName)
        meetingPassword = findViewById(R.id.editTextTextPersonName2)

        joinMeeting.setOnClickListener {
            JoinMeetingHelper().joinMeeting(
                this,
                ZoomSDK.getInstance(),
                meetingId.text.toString(),
                meetingPassword.text.toString(),
                "TechEnum"
            )
        }

        // to show visual feedback
        msg = findViewById(R.id.auth_result)
        startMeeting = findViewById(R.id.button4)

        initSdk()

        startMeeting.setOnClickListener {
            MeetingHostHelper(
                this,
                ZoomSDK.getInstance(),
                object : MeetingHostHelper.MeetingStatusListener {
                    override fun onMeetingFailed() {
                        Toast.makeText(
                            this@MainActivity,
                            "Could not host a meeting.",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                    override fun onMeetingRunning() {

                        val idPwd = InMeetingHelper.getIdPassword(ZoomSDK.getInstance())
                        Toast.makeText(
                            this@MainActivity,
                            "ID: ${idPwd.first} PWD: ${idPwd.second}",
                            Toast.LENGTH_LONG
                        ).show()
                        Log.d("MainActivity", "ID: ${idPwd.first} PWD: ${idPwd.second}")

                        meetingId.setText(idPwd.first)
                        meetingPassword.setText(idPwd.second)

                    }

                }).createInstantMeeting()

        }

        InMeetingHelper.getUpdates(
            ZoomSDK.getInstance(),
            object : InMeetingHelper.MeetingUpdateListener {
                override fun onSomeoneJoinedMeeting() {
                }

                override fun onSomeoneLeftMeeting() {
                }

                override fun onMeetingLeaveSuccess() {
                }

                override fun onMeetingEnd() {
                }
            })

    }

    /**
     * Responsible for initializing the SDK
     */
    private fun initSdk() {
        val initParams = ZoomSDKInitParams().apply {
            appKey = Credentials.SDK_KEY
            appSecret = Credentials.SDK_SECRET
            domain = Credentials.SDK_DOMAIN
        }
        ZoomSDK.getInstance().initialize(this, this, initParams)
        ZoomSDK.getInstance().addAuthenticationListener(this)
    }

    private fun doLoginToZoom() {
        val email = "naveenworkingatoffice@gmail.com"
        val password = "morethan3veR"
        ZoomSDK.getInstance().loginWithZoom(email, password)
    }

    override fun onZoomSDKInitializeResult(p0: Int, p1: Int) {
        msg.text = "SDK Initialized!"
        doLoginToZoom()
    }

    override fun onZoomAuthIdentityExpired() {
    }

    override fun onZoomSDKLoginResult(p0: Long) {
        when (p0.toInt()) {
            ZoomAuthenticationError.ZOOM_AUTH_ERROR_SUCCESS -> {
                val message = "Logged in successfully."
                msg.text = message
                Toast.makeText(this, message, Toast.LENGTH_LONG).show()
            }
            ZoomAuthenticationError.ZOOM_AUTH_ERROR_USER_NOT_EXIST,
            ZoomAuthenticationError.ZOOM_AUTH_ERROR_WRONG_PASSWORD -> {
                val message = "Username / Password do not match."
                msg.text = message
                Toast.makeText(this, message, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onZoomIdentityExpired() {
    }

    override fun onZoomSDKLogoutResult(p0: Long) {
    }

}