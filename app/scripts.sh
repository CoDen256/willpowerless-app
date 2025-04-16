# WARNING, USE ONLY IF THERE IS NO EXISTING ACCESSIBLE ADMIN RECEIVER
# IF THERE IS AN EXISTING ADMIN (OWNER), RUN THE APP WITH THE OWNER
# devicePolicyManager.transferOwnership(
#   adminComponent,
#   ComponentName("io.github.coden.guard", "io.github.coden.guard.service.AdminReceiver"),
#   null)
#

# Before setting device owner, log out of all accounts on the device (google)
adb shell
dumpsys account | grep "Account {"#,  maybe not what is needed
# logout from every account specified in dumpsys account, then reboot, then set-device-owner

# maybe just users is needed
pm list users
pm remove-user <999>
# install app, then. CHECK IF ERRORS APPEAR, READ CAREFULLY
dpm set-device-owner io.github.coden256.wpl.guard/.services.AdminReceiver

# if needed to remove existing device owner, use EXISTING DEVICE OWNER TO TRANSFER OWNERSHIP
# dpm remove-active-admin io.github.coden.guard/.GuardAdminReceiver

pm list packages --user 0 # sometimes is needed when permission denied

dpm list-owners