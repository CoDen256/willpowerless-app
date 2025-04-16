
# Before setting device owner, log out of all accounts on the device (google)
adb shell
dumpsys account | grep "Account {"#,  maybe not what is needed
# logout from every account specified in dumpsys account, then reboot, then set-device-owner

# TODO CHECK IF POSSIBLE JUST TO TRANSFER OWNERSHIP TO ANOTHER PACKAGE FROM THE APP
# WITHOUT DOING MANUAL LOGGING OUT

# maybe just users is needed
pm list users
pm remove-user <999>
# install app, then
# FIRST SET, THEN REMOVE
dpm set-device-owner io.github.coden.guard/.services.AdminReceiver

## VERIFY PACKAGE NAME!!!!!!
dpm remove-active-admin io.github.coden.guard/.GuardAdminReceiver
pm list packages --user 0 # sometimes is needed when permission denied

dpm list-owners