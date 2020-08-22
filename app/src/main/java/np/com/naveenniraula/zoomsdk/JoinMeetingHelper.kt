package np.com.naveenniraula.zoomsdk

import android.content.Context
import us.zoom.sdk.*

class JoinMeetingHelper {

    /**
     * This method will join the meeting with specified ID and Password
     *
     * @param context     the usual context
     * @param instance    the instance of [ZoomSDK]
     * @param id          the id of the meeting
     * @param password    the password for meeting
     * @param displayName the name that will be displayed to others
     */
    fun joinMeeting(
        context: Context,
        instance: ZoomSDK,
        id: String,
        password: String,
        displayName: String
    ): Int {

        // if meeting service is null we cannot do anything
        val meetingService: MeetingService = instance.meetingService ?: return 0

        // this is required ; default options to join the meeting
        val jmo = JoinMeetingOptions()
        // some available options
        // jmo.no_driving_mode = true;
        // jmo.no_invite = true;
        // jmo.no_meeting_end_message = true;
        // jmo.no_titlebar = true;
        // jmo.no_bottom_toolbar = true;
        // jmo.no_dial_in_via_phone = true;
        // jmo.no_dial_out_to_phone = true;
        // jmo.no_disconnect_audio = true;
        // jmo.no_share = true;
        // jmo.invite_options = InviteOptions.INVITE_VIA_EMAIL + InviteOptions.INVITE_VIA_SMS;
        // jmo.no_audio = true;
        // jmo.no_video = true;
        // jmo.meeting_views_options = MeetingViewsOptions.NO_BUTTON_SHARE;
        // jmo.no_meeting_error_message = true;
        // jmo.participant_id = "participant id";

        // here we will define all the required parameters such as name id and pwd
        val jmp = JoinMeetingParams()
        jmp.displayName = displayName
        jmp.meetingNo = id
        jmp.password = password

        // you can avoid returning anything too
        return meetingService.joinMeetingWithParams(context, jmp, jmo)

    }

}