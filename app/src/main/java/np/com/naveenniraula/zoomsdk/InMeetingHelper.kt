package np.com.naveenniraula.zoomsdk

import us.zoom.sdk.*

object InMeetingHelper {

    /**
     * Get the meeting user and password from the currently running meeting.
     *
     * @param instance
     *
     * @return [Pair] where first is the meeting id and second is password
     *
     */
    fun getIdPassword(instance: ZoomSDK): Pair<String, String> {
        val ims = instance.inMeetingService
        val number = ims.currentMeetingNumber
        val pwd = ims.meetingPassword
        return Pair(number.toString(), pwd)
    }

    /**
     * Meeting helper callbacks when the user is in the meeting. We have simplified much further
     * because we don't want to use all everytime.
     *
     * @param instance
     * @param meetingUpdateListener
     *
     */
    fun getUpdates(instance: ZoomSDK, meetingUpdateListener: MeetingUpdateListener) {
        instance.inMeetingService.addListener(object : InMeetingServiceListener {
            override fun onMeetingActiveVideo(p0: Long) {

            }

            override fun onFreeMeetingReminder(p0: Boolean, p1: Boolean, p2: Boolean) {

            }

            override fun onJoinWebinarNeedUserNameAndEmail(p0: InMeetingEventHandler?) {

            }

            override fun onActiveVideoUserChanged(p0: Long) {

            }

            override fun onActiveSpeakerVideoUserChanged(p0: Long) {

            }

            override fun onChatMessageReceived(p0: InMeetingChatMessage?) {

            }

            override fun onUserNetworkQualityChanged(p0: Long) {

            }

            override fun onMeetingUserJoin(p0: MutableList<Long>?) {
                meetingUpdateListener.onSomeoneJoinedMeeting()
            }

            override fun onMeetingUserLeave(p0: MutableList<Long>?) {
                meetingUpdateListener.onSomeoneLeftMeeting()
            }

            override fun onMeetingFail(p0: Int, p1: Int) {
                meetingUpdateListener.onMeetingEnd()
            }

            override fun onUserAudioTypeChanged(p0: Long) {

            }

            override fun onMyAudioSourceTypeChanged(p0: Int) {

            }

            override fun onSilentModeChanged(p0: Boolean) {

            }

            override fun onMeetingCoHostChanged(p0: Long) {

            }

            override fun onLowOrRaiseHandStatusChanged(p0: Long, p1: Boolean) {

            }

            override fun onSinkAttendeeChatPriviledgeChanged(p0: Int) {

            }

            override fun onMeetingUserUpdated(p0: Long) {

            }

            override fun onMeetingSecureKeyNotification(p0: ByteArray?) {

            }

            override fun onMeetingNeedColseOtherMeeting(p0: InMeetingEventHandler?) {

            }

            override fun onMicrophoneStatusError(p0: InMeetingAudioController.MobileRTCMicrophoneError?) {

            }

            override fun onHostAskStartVideo(p0: Long) {

            }

            override fun onSinkAllowAttendeeChatNotification(p0: Int) {

            }

            override fun onWebinarNeedRegister() {

            }

            override fun onSpotlightVideoChanged(p0: Boolean) {

            }

            override fun onMeetingHostChanged(p0: Long) {

            }

            override fun onMeetingLeaveComplete(p0: Long) {
                meetingUpdateListener.onMeetingLeaveSuccess()
            }

            override fun onHostAskUnMute(p0: Long) {

            }

            override fun onUserAudioStatusChanged(p0: Long) {

            }

            override fun onUserNameChanged(p0: Long, p1: String?) {

            }

            override fun onMeetingNeedPasswordOrDisplayName(
                p0: Boolean,
                p1: Boolean,
                p2: InMeetingEventHandler?
            ) {

            }

            override fun onUserVideoStatusChanged(p0: Long) {

            }

        })
    }

    interface MeetingUpdateListener {
        fun onSomeoneJoinedMeeting()
        fun onSomeoneLeftMeeting()
        fun onMeetingLeaveSuccess()
        fun onMeetingEnd()
    }

}