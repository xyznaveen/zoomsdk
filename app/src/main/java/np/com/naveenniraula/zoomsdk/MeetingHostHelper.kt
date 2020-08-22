package np.com.naveenniraula.zoomsdk

import android.content.Context
import us.zoom.sdk.*

class MeetingHostHelper(
    private val context: Context,
    private val zoomSDK: ZoomSDK,
    private val meetingStatusListener: MeetingStatusListener
) {

    private val meetingService = zoomSDK.meetingService

    fun createInstantMeeting(): Int {

        if (meetingService == null) return -1 // should not be null; if null return -1

        val opts = InstantMeetingOptions()
        // opts.no_driving_mode = true;
        // opts.no_invite = true;
        // opts.no_meeting_end_message = true;
        // opts.no_titlebar = true;
        // opts.no_bottom_toolbar = true;
        // opts.no_dial_in_via_phone = true;
        // opts.no_dial_out_to_phone = true;
        // opts.no_disconnect_audio = true;
        // opts.no_share = true;

        meetingService.addListener { meetingStatus, errorCode, _ ->

            if (meetingStatus == MeetingStatus.MEETING_STATUS_INMEETING) {
                meetingStatusListener.onMeetingRunning()
            } else if (meetingStatus == MeetingStatus.MEETING_STATUS_FAILED) {
                meetingStatusListener.onMeetingFailed()
            } else if (meetingStatus == MeetingStatus.MEETING_STATUS_FAILED
                && errorCode == MeetingError.MEETING_ERROR_CLIENT_INCOMPATIBLE
            ) {
                // meeting failed because the SDK version is too low upgrade the sdk
                meetingStatusListener.onMeetingFailed()
            }

        }

        return meetingService.startInstantMeeting(context, opts)

    }

    /**
     * To get the status of meeting when the user clicks create.
     * This is a listener
     */
    interface MeetingStatusListener {
        fun onMeetingFailed()
        fun onMeetingRunning()
    }

}