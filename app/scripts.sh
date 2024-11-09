
# Before setting device owner, log out of all accounts on the device (google)
adb shell dpm set-device-owner io.github.coden.dictator/.MyDeviceAdminReceiver

adb shell dpm remove-active-admin io.github.coden.dictator/.MyDeviceAdminReceiver
