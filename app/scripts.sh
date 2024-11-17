
# Before setting device owner, log out of all accounts on the device (google)
adb shell
# dumpsys account,  maybe not what is needed
# maybe just users is needed
pm list users
pm remove-user <999>

dpm set-device-owner io.github.coden.dictator/.DictatorAdminReceiver

dpm remove-active-admin io.github.coden.dictator/.DictatorAdminReceiver
pm list packages --user 0 # sometimes is needed when permission denied



Users:
        UserInfo{0:Alina Barynina:4c13} running
        UserInfo{150:Secure Folder:10061030} running



#
User UserInfo{0:Alina Barynina:4c13}:
  Accounts: 30
    Account {name=alina00427@gmail.com, type=com.google}
    Account {name=io13fiot@gmail.com, type=com.google}
    Account {name=barynina.alina@lll.kpi.ua, type=com.google}
    Account {name=a1inabar0707@gmail.com, type=com.google}
    Account {name=den.blackshov@gmail.com, type=com.google}
    Account {name=alina.barynina.dev@gmail.com, type=com.google}
    Account {name=alina00427@gmail.com, type=com.microsoft.skydrive}
    Account {name=Pinterest, type=com.pinterest.dev.accounttransfer.type}
    Account {name=394953631, type=org.telegram.messenger}
    Account {name=alina00427@gmail.com, type=com.adobe.creativesdk.foundation.auth.adobeID.DC}
    Account {name=WhatsApp, type=com.whatsapp}
    Account {name=TikTok, type=com.zhiliaoapp.musically}
    Account {name=Duo, type=com.google.android.apps.tachyon}
    Account {name=Meet, type=com.google.android.apps.tachyon}
    Account {name=a1inabar@yahoo.com, type=com.google.android.gm.legacyimap}
    Account {name=Office, type=com.microsoft.office}
    Account {name=Alina27381511, type=com.twitter.android.auth.login}
    Account {name=alina-barynina, type=com.soundcloud.android.account}
    Account {name=Zenly, type=zenly.account}
    Account {name=Duolingo, type=com.duolingo}
    Account {name=KL Kundenkonto, type=com.kaufland.klapp.Account}
    Account {name=den.blackshov@gmail.com, type=de.check24.check24.account}
    Account {name=alina00427@gmail.com, type=com.microsoft.appmanager}
    Account {name=Skype, type=com.skype.raider}
    Account {name=aljolen#2319, type=com.blizzard.mobile.auth}
    Account {name=Facebook, type=com.facebook.auth.login}
    Account {name=100018774131156, type=com.facebook.auth.login}
    Account {name=Immowelt, type=Immowelt.de}
    Account {name=a1inabar0707@gmail.com, type=de.dm.mobile.customer.account}
    Account {name=5684016505, type=www.instagram.com}

  AccountId, Action_Type, timestamp, UID, TableName, Key
  Accounts History
  26,action_account_add,2023-05-13 06:57:06,10763,accounts,43
  -1,action_called_account_add,2023-06-29 18:46:18,10236,accounts,44
  -1,action_called_account_add,2023-06-29 18:46:30,10236,accounts,45
  27,action_account_add,2023-06-29 18:47:33,10236,accounts,46
  27,action_set_password,2023-06-29 18:47:33,10236,accounts,47
  3,action_clear_password,2023-10-19 15:23:27,10236,accounts,48
  3,action_set_password,2023-10-19 17:29:47,10236,accounts,49
  28,action_account_add,2023-11-29 18:12:45,10427,accounts,50
  29,action_account_add,2024-02-06 23:42:08,10140,accounts,51
  -1,action_called_account_remove,2024-02-28 20:25:47,10686,accounts,52
  -1,action_called_account_remove,2024-02-28 20:25:52,10686,accounts,53
  -1,action_called_account_remove,2024-02-28 20:25:53,10686,accounts,54
  -1,action_called_account_remove,2024-02-28 20:25:54,10686,accounts,55
  -1,action_called_account_remove,2024-02-28 20:27:56,10686,accounts,56
  30,action_account_add,2024-02-28 20:29:35,10686,accounts,57
  31,action_account_add,2024-03-09 23:19:29,10476,accounts,58
  8,action_called_account_remove,2024-05-08 23:39:04,10326,accounts,59
  32,action_account_add,2024-05-08 23:39:04,10326,accounts,60
  8,action_account_remove,2024-05-08 23:39:04,10326,accounts,61
  32,action_called_account_remove,2024-05-09 00:25:30,10326,accounts,62
  32,action_account_remove,2024-05-09 00:25:30,10326,accounts,63
  33,action_account_add,2024-05-24 19:15:38,10298,accounts,0
  34,action_account_add,2024-05-24 19:16:08,10298,accounts,1
  -1,action_called_account_add,2024-06-06 00:14:38,10349,accounts,2
  -1,action_called_account_add,2024-06-18 03:01:26,10349,accounts,3
  -1,action_called_account_add,2024-06-19 01:30:58,10349,accounts,4
  -1,action_called_account_add,2024-06-28 01:45:46,10349,accounts,5
  -1,action_called_account_add,2024-07-03 17:24:29,10250,accounts,6
  -1,action_called_account_add,2024-07-03 17:37:44,10250,accounts,7
  35,action_account_add,2024-07-03 17:38:16,10236,accounts,8
  35,action_set_password,2024-07-03 17:38:16,10236,accounts,9
  36,action_account_add,2024-07-11 13:49:34,10761,accounts,10
  37,action_account_add,2024-08-08 17:45:14,10398,accounts,11
  37,action_called_account_remove,2024-08-08 17:55:27,10398,accounts,12
  37,action_account_remove,2024-08-08 17:55:27,10398,accounts,13
  38,action_account_add,2024-08-08 17:55:27,10398,accounts,14
  38,action_authenticator_remove,2024-08-25 22:00:34,1000,accounts,15
  39,action_account_add,2024-09-24 11:47:34,10444,accounts,16
  40,action_account_add,2024-10-08 13:00:19,10374,accounts,17
  41,action_account_add,2024-10-13 11:44:55,10463,accounts,18
  41,action_called_account_remove,2024-10-14 14:50:46,10463,accounts,19
  41,action_account_remove,2024-10-14 14:50:46,1000,accounts,20
  36,action_called_account_remove,2024-10-24 17:59:44,10761,accounts,21
  36,action_account_remove,2024-10-24 17:59:44,1000,accounts,22
  42,action_account_add,2024-10-24 17:59:44,10761,accounts,23
  42,action_called_account_remove,2024-10-26 03:11:10,10761,accounts,24
  42,action_account_remove,2024-10-26 03:11:10,1000,accounts,25
  43,action_account_add,2024-10-26 03:11:10,10761,accounts,26
  43,action_called_account_remove,2024-11-02 10:08:29,10761,accounts,27
  43,action_account_remove,2024-11-02 10:08:30,1000,accounts,28
  44,action_account_add,2024-11-02 10:08:30,10761,accounts,29
  44,action_called_account_remove,2024-11-09 11:37:49,10761,accounts,30
  44,action_account_remove,2024-11-09 11:37:49,1000,accounts,31
  45,action_account_add,2024-11-09 11:37:49,10761,accounts,32
  40,action_called_account_remove,2024-11-11 15:55:13,10374,accounts,33
  40,action_account_remove,2024-11-11 15:55:13,1000,accounts,34
  46,action_account_add,2024-11-11 15:55:13,10374,accounts,35
  45,action_called_account_remove,2024-11-16 12:05:51,10761,accounts,36
  45,action_account_remove,2024-11-16 12:05:51,1000,accounts,37
  47,action_account_add,2024-11-16 12:05:51,10761,accounts,38
  1,action_called_account_remove,2024-11-17 20:31:25,10100,accounts,39
  1,action_account_remove,2024-11-17 20:31:26,10100,accounts,40
  2,action_called_account_remove,2024-11-17 20:31:40,10103,accounts,41
  2,action_account_remove,2024-11-17 20:31:41,10103,accounts,42

  Active Sessions: 1
    Session: expectLaunch false, connected true, stats (0/0/0), lifetime 183.333, hasFeatures, Account {name=alina00427@gmail.com, type=com.microsoft.appmanager}, android.account.DEVICE_OR_PROFILE_OWNER_DISALLOWED

  RegisteredServicesCache: 36 services
    ServiceInfo: AuthenticatorDescription {type=com.microsoft.appmanager}, ComponentInfo{com.microsoft.appmanager/com.microsoft.appmanager.oem.account.AccountAuthenticatorService}, uid 10140
    ServiceInfo: AuthenticatorDescription {type=com.microsoft.msa.ltw}, ComponentInfo{com.microsoft.appmanager/com.microsoft.identity.broker.accountmanager.AccountManagerServiceForPersonalAccounts}, uid 10140
    ServiceInfo: AuthenticatorDescription {type=com.google.android.apps.tachyon}, ComponentInfo{com.google.android.apps.tachyon/com.google.android.apps.tachyon.contacts.sync.DuoAccountService}, uid 10249
    ServiceInfo: AuthenticatorDescription {type=com.microsoft.workaccount.authapp}, ComponentInfo{com.azure.authenticator/com.microsoft.identity.broker.accountmanager.AccountManagerServiceForWorkAccounts}, uid 10349
    ServiceInfo: AuthenticatorDescription {type=zenly.account}, ComponentInfo{app.zenly.locator/co.znly.core.authenticator.ZenlyCoreAuthenticatorService}, uid 10371
    ServiceInfo: AuthenticatorDescription {type=com.microsoft.ltwpassthroughbackup}, ComponentInfo{com.microsoft.appmanager/com.microsoft.identity.broker.activebrokerselection.backupipc.service.AccountManagerIpcServiceForRequestsTargetingSpecificBrokerApp}, uid 10140
    ServiceInfo: AuthenticatorDescription {type=com.microsoft.office}, ComponentInfo{com.microsoft.office.word/com.microsoft.office.msohttp.keystore.OfficeAuthenticationService}, uid 10282
    ServiceInfo: AuthenticatorDescription {type=com.snapchat.location}, ComponentInfo{com.snapchat.android/com.snap.location.livelocation.syncadapter.AuthenticatorService}, uid 10549
    ServiceInfo: AuthenticatorDescription {type=com.skype.raider}, ComponentInfo{com.skype.raider/com.skype4life.syncadapter.AccountService}, uid 10686
    ServiceInfo: AuthenticatorDescription {type=com.samsung.android.mobileservice}, ComponentInfo{com.samsung.android.mobileservice/com.samsung.android.mobileservice.social.calendar.presentation.service.background.account.AuthenticatorService}, uid 10103
    ServiceInfo: AuthenticatorDescription {type=com.google.android.gm.exchange}, ComponentInfo{com.google.android.gm/com.android.email.service.EasAuthenticatorService}, uid 10256
    ServiceInfo: AuthenticatorDescription {type=com.microsoft.authapppassthroughbackup}, ComponentInfo{com.azure.authenticator/com.microsoft.identity.broker.activebrokerselection.backupipc.service.AccountManagerIpcServiceForRequestsTargetingSpecificBrokerApp}, uid 10349
    ServiceInfo: AuthenticatorDescription {type=com.viber.voip}, ComponentInfo{com.viber.voip/com.viber.service.contacts.authentication.AccountAuthenticatorService}, uid 10326
    ServiceInfo: AuthenticatorDescription {type=com.microsoft.msa.authapp}, ComponentInfo{com.azure.authenticator/com.microsoft.identity.broker.accountmanager.AccountManagerServiceForPersonalAccounts}, uid 10349
    ServiceInfo: AuthenticatorDescription {type=com.myunidays.account}, ComponentInfo{com.myunidays/com.myunidays.account.AuthenticatorService}, uid 10463
    ServiceInfo: AuthenticatorDescription {type=de.mcdonalds.mcdonaldsinfoapp.account}, ComponentInfo{de.mcdonalds.mcdonaldsinfoapp/com.mcdonalds.app.account.AuthenticatorService}, uid 10698
    ServiceInfo: AuthenticatorDescription {type=org.telegram.messenger}, ComponentInfo{org.telegram.messenger/org.telegram.messenger.AuthenticatorService}, uid 10325
    ServiceInfo: AuthenticatorDescription {type=de.dm.mobile.customer.account}, ComponentInfo{de.dm.meindm.android/de.dm.meindm.android.account.AuthenticatorService}, uid 10374
    ServiceInfo: AuthenticatorDescription {type=com.microsoft.workaccount.ltw}, ComponentInfo{com.microsoft.appmanager/com.microsoft.identity.broker.accountmanager.AccountManagerServiceForWorkAccounts}, uid 10140
    ServiceInfo: AuthenticatorDescription {type=com.microsoft.skydrive}, ComponentInfo{com.microsoft.skydrive/com.microsoft.authorization.AuthenticationService}, uid 10094
    ServiceInfo: AuthenticatorDescription {type=com.whatsapp}, ComponentInfo{com.whatsapp/com.whatsapp.accountsync.AccountAuthenticatorService}, uid 10324
    ServiceInfo: AuthenticatorDescription {type=com.google.android.gm.pop3}, ComponentInfo{com.google.android.gm/com.android.email.service.Pop3AuthenticatorService}, uid 10256
    ServiceInfo: AuthenticatorDescription {type=com.google}, ComponentInfo{com.google.android.gms/com.google.android.gms.auth.account.authenticator.GoogleAccountAuthenticatorService}, uid 10236
    ServiceInfo: AuthenticatorDescription {type=com.snapchat.android}, ComponentInfo{com.snapchat.android/com.snap.identity.enhancedcontacts.core.SnapAndroidAccountAuthenticatorService}, uid 10549
    ServiceInfo: AuthenticatorDescription {type=com.zhiliaoapp.account}, ComponentInfo{com.zhiliaoapp.musically/com.ss.android.ugc.trill.account.TiktokAuthService}, uid 10327
    ServiceInfo: AuthenticatorDescription {type=com.microsoft.workaccount}, ComponentInfo{com.azure.authenticator/com.microsoft.workaccount.authenticatorservice.AuthenticatorService}, uid 10349
    ServiceInfo: AuthenticatorDescription {type=app.diia.gov.ua}, ComponentInfo{ua.gov.diia.app/ua.gov.diia.app.service.sync.AuthenticatorS}, uid 10358
    ServiceInfo: AuthenticatorDescription {type=com.kaufland.klapp.Account}, ComponentInfo{com.kaufland.Kaufland/kaufland.com.accountkit.oauth.UserAuthenticatorService}, uid 10763
    ServiceInfo: AuthenticatorDescription {type=com.osp.app.signin}, ComponentInfo{com.osp.app.signin/com.samsung.android.samsungaccount.authentication.service.OspAuthenticationService}, uid 10100
    ServiceInfo: AuthenticatorDescription {type=com.samsung.android.coreapps}, ComponentInfo{com.samsung.android.mobileservice/com.samsung.android.mobileservice.registration.auth.legacy.presentation.coreapps.AuthenticationService}, uid 10103
    ServiceInfo: AuthenticatorDescription {type=www.instagram.com}, ComponentInfo{com.instagram.android/com.instagram.libraries.access.accountmanager.service.InstagramAuthenticationService}, uid 10761
    ServiceInfo: AuthenticatorDescription {type=com.pinterest.accounttransfer.type}, ComponentInfo{com.pinterest/com.pinterest.account.AuthenticatorService}, uid 10377
    ServiceInfo: AuthenticatorDescription {type=com.facebook.lite}, ComponentInfo{com.facebook.lite/com.facebook.lite.access.accountmanager.AccountAuthenticatorService}, uid 10661
    ServiceInfo: AuthenticatorDescription {type=com.facebook.auth.login}, ComponentInfo{com.facebook.katana/com.facebook.katana.platform.FacebookAuthenticationService}, uid 10298
    ServiceInfo: AuthenticatorDescription {type=Immowelt.de}, ComponentInfo{de.immowelt.android.immobiliensuche/de.immowelt.mobile.android.sdk.core.service.DefaultAccountService}, uid 10444
    ServiceInfo: AuthenticatorDescription {type=com.google.android.gm.legacyimap}, ComponentInfo{com.google.android.gm/com.android.email.service.LegacyImapAuthenticatorService}, uid 10256

  Account visibility:
    alina00427@gmail.com
      com.google.android.apps.translate, 2
      com.google.ar.core, 2
      notion.id, 2
      com.gosund.smart, 2
      com.stepstone.borowf01, 2
      com.realtimeboard, 2
      com.britishcouncil.ieltsprep, 2
      com.linkedin.android, 2
      com.chess, 2
      com.soundcloud.android, 2
      com.google.android.apps.restore, 2
      com.google.android.apps.walletnfcrel, 2
      de.stocard.stocard, 2
      com.google.android.apps.docs.editors.docs, 2
      app.foryou, 2
      com.twitter.android, 2
      com.zzkko, 2
      com.indeed.android.jobsearch, 2
      com.google.android.apps.photos, 2
      de.mcdonalds.mcdonaldsinfoapp, 2
      com.adobe.scan.android, 2
      com.google.android.apps.docs, 2
      com.google.android.apps.chromecast.app, 2
      com.google.android.apps.subscriptions.red, 2
      com.iherb, 2
      com.google.android.apps.maps, 2
      com.google.android.apps.messaging, 2
      com.zaful, 2
      com.google.android.apps.classroom, 2
      com.habitrpg.android.habitica, 2
      com.google.android.videos, 2
      com.zhiliaoapp.musically, 2
      shafa.odejda.obuv.aksessuary, 2
      com.duolingo, 2
      com.waze, 2
      period.tracker.calendar.ovulation.women.fertility.cycle, 2
      com.hevy, 2
      com.relevoapp, 2
      com.picsart.studio, 2
      com.wolt.android, 2
      com.google.android.apps.docs.editors.sheets, 2
      de.burgerking.kingfinder, 2
      app.source.getcontact, 2
      com.medvediev.dono, 2
      com.lemon.lvoverseas, 2
      com.google.android.gm, 2
      com.app.tgtg, 2
      com.xing.android, 2
      com.babblerdev.familyshoping, 2
      com.yopeso.lieferando, 2
      com.tier.app, 2
      com.cider, 2
    barynina.alina@lll.kpi.ua
      com.google.android.apps.maps, 2
      com.google.android.apps.translate, 2
      com.google.android.apps.docs.editors.docs, 2
      com.google.ar.core, 2
      com.google.android.apps.messaging, 2
      com.google.android.apps.classroom, 2
      com.google.android.apps.photos, 2
      com.google.android.videos, 2
      com.google.android.apps.docs.editors.sheets, 2
      com.google.android.gm, 2
      com.google.android.apps.docs, 2
      com.google.android.apps.chromecast.app, 2
      com.google.android.apps.restore, 2
      com.google.android.apps.subscriptions.red, 2
      com.google.android.apps.walletnfcrel, 2
    den.blackshov@gmail.com
      com.google.android.apps.maps, 2
      com.google.android.apps.translate, 2
      com.google.android.apps.docs.editors.docs, 2
      com.google.ar.core, 2
      com.google.android.apps.messaging, 2
      com.google.android.apps.classroom, 2
      com.google.android.apps.photos, 2
      com.google.android.videos, 2
      com.booking, 2
      com.google.android.apps.docs.editors.sheets, 2
      com.google.android.gm, 2
      com.google.android.apps.docs, 2
      com.google.android.apps.chromecast.app, 2
      com.google.android.apps.restore, 2
      com.google.android.apps.subscriptions.red, 2
      com.google.android.apps.walletnfcrel, 2
    io13fiot@gmail.com
      com.google.android.videos, 2
      com.google.android.apps.docs.editors.sheets, 2
      com.google.android.apps.chromecast.app, 2
      com.google.android.apps.restore, 2
      com.google.android.apps.subscriptions.red, 2
      com.google.android.apps.classroom, 2
      com.google.android.apps.walletnfcrel, 2
    alina.barynina.dev@gmail.com
      com.google.android.apps.maps, 2
      com.google.android.apps.translate, 2
      com.google.android.apps.docs.editors.docs, 2
      com.google.ar.core, 2
      com.google.android.apps.messaging, 2
      com.google.android.apps.classroom, 2
      com.google.android.apps.photos, 2
      com.google.android.videos, 2
      com.google.android.apps.docs.editors.sheets, 2
      com.google.android.gm, 2
      com.google.android.apps.docs, 2
      com.google.android.apps.chromecast.app, 2
      com.google.android.apps.restore, 2
      com.google.android.apps.subscriptions.red, 2
      com.google.android.apps.walletnfcrel, 2
    a1inabar0707@gmail.com
      com.google.android.videos, 2
      com.google.android.apps.docs.editors.sheets, 2
      at.helloagain.muellerde, 2
      com.yazio.android, 2
      com.google.android.apps.chromecast.app, 2
      com.google.android.apps.restore, 2
      fr.vinted, 2
      com.google.android.apps.subscriptions.red, 2
      com.google.android.apps.classroom, 2
      com.google.android.apps.walletnfcrel, 2

User UserInfo{150:Secure Folder:10061030}:
  Accounts: 0

  AccountId, Action_Type, timestamp, UID, TableName, Key
  Accounts History

  Active Sessions: 1
    Session: expectLaunch false, connected true, stats (0/0/0), lifetime 183.343, hasFeatures, Account {name=alina00427@gmail.com, type=com.microsoft.appmanager}, android.account.DEVICE_OR_PROFILE_OWNER_DISALLOWED

  RegisteredServicesCache: 5 services
    ServiceInfo: AuthenticatorDescription {type=com.google}, ComponentInfo{com.google.android.gms/com.google.android.gms.auth.account.authenticator.GoogleAccountAuthenticatorService}, uid 15010236
    ServiceInfo: AuthenticatorDescription {type=com.osp.app.signin}, ComponentInfo{com.osp.app.signin/com.samsung.android.samsungaccount.authentication.service.OspAuthenticationService}, uid 15010100
    ServiceInfo: AuthenticatorDescription {type=com.samsung.android.coreapps}, ComponentInfo{com.samsung.android.mobileservice/com.samsung.android.mobileservice.registration.auth.legacy.presentation.coreapps.AuthenticationService}, uid 15010103
    ServiceInfo: AuthenticatorDescription {type=com.samsung.android.mobileservice}, ComponentInfo{com.samsung.android.mobileservice/com.samsung.android.mobileservice.social.calendar.presentation.service.background.account.AuthenticatorService}, uid 15010103
    ServiceInfo: AuthenticatorDescription {type=com.google.work}, ComponentInfo{com.google.android.gms/com.google.android.gms.auth.account.authenticator.WorkAccountAuthenticatorService}, uid 15010236

  Account visibility:


#
pm remove-user 150

#
Users:
        UserInfo{0:Alina Barynina:4c13} running
r9q:/ $ dumpsys account
User UserInfo{0:Alina Barynina:4c13}:
  Accounts: 30
    Account {name=alina00427@gmail.com, type=com.google}
    Account {name=io13fiot@gmail.com, type=com.google}
    Account {name=barynina.alina@lll.kpi.ua, type=com.google}
    Account {name=a1inabar0707@gmail.com, type=com.google}
    Account {name=den.blackshov@gmail.com, type=com.google}
    Account {name=alina.barynina.dev@gmail.com, type=com.google}
    Account {name=alina00427@gmail.com, type=com.microsoft.skydrive}
    Account {name=Pinterest, type=com.pinterest.dev.accounttransfer.type}
    Account {name=394953631, type=org.telegram.messenger}
    Account {name=alina00427@gmail.com, type=com.adobe.creativesdk.foundation.auth.adobeID.DC}
    Account {name=WhatsApp, type=com.whatsapp}
    Account {name=TikTok, type=com.zhiliaoapp.musically}
    Account {name=Duo, type=com.google.android.apps.tachyon}
    Account {name=Meet, type=com.google.android.apps.tachyon}
    Account {name=a1inabar@yahoo.com, type=com.google.android.gm.legacyimap}
    Account {name=Office, type=com.microsoft.office}
    Account {name=Alina27381511, type=com.twitter.android.auth.login}
    Account {name=alina-barynina, type=com.soundcloud.android.account}
    Account {name=Zenly, type=zenly.account}
    Account {name=Duolingo, type=com.duolingo}
    Account {name=KL Kundenkonto, type=com.kaufland.klapp.Account}
    Account {name=den.blackshov@gmail.com, type=de.check24.check24.account}
    Account {name=alina00427@gmail.com, type=com.microsoft.appmanager}
    Account {name=Skype, type=com.skype.raider}
    Account {name=aljolen#2319, type=com.blizzard.mobile.auth}
    Account {name=Facebook, type=com.facebook.auth.login}
    Account {name=100018774131156, type=com.facebook.auth.login}
    Account {name=Immowelt, type=Immowelt.de}
    Account {name=a1inabar0707@gmail.com, type=de.dm.mobile.customer.account}
    Account {name=5684016505, type=www.instagram.com}

  AccountId, Action_Type, timestamp, UID, TableName, Key
  Accounts History
  26,action_account_add,2023-05-13 06:57:06,10763,accounts,43
  -1,action_called_account_add,2023-06-29 18:46:18,10236,accounts,44
  -1,action_called_account_add,2023-06-29 18:46:30,10236,accounts,45
  27,action_account_add,2023-06-29 18:47:33,10236,accounts,46
  27,action_set_password,2023-06-29 18:47:33,10236,accounts,47
  3,action_clear_password,2023-10-19 15:23:27,10236,accounts,48
  3,action_set_password,2023-10-19 17:29:47,10236,accounts,49
  28,action_account_add,2023-11-29 18:12:45,10427,accounts,50
  29,action_account_add,2024-02-06 23:42:08,10140,accounts,51
  -1,action_called_account_remove,2024-02-28 20:25:47,10686,accounts,52
  -1,action_called_account_remove,2024-02-28 20:25:52,10686,accounts,53
  -1,action_called_account_remove,2024-02-28 20:25:53,10686,accounts,54
  -1,action_called_account_remove,2024-02-28 20:25:54,10686,accounts,55
  -1,action_called_account_remove,2024-02-28 20:27:56,10686,accounts,56
  30,action_account_add,2024-02-28 20:29:35,10686,accounts,57
  31,action_account_add,2024-03-09 23:19:29,10476,accounts,58
  8,action_called_account_remove,2024-05-08 23:39:04,10326,accounts,59
  32,action_account_add,2024-05-08 23:39:04,10326,accounts,60
  8,action_account_remove,2024-05-08 23:39:04,10326,accounts,61
  32,action_called_account_remove,2024-05-09 00:25:30,10326,accounts,62
  32,action_account_remove,2024-05-09 00:25:30,10326,accounts,63
  33,action_account_add,2024-05-24 19:15:38,10298,accounts,0
  34,action_account_add,2024-05-24 19:16:08,10298,accounts,1
  -1,action_called_account_add,2024-06-06 00:14:38,10349,accounts,2
  -1,action_called_account_add,2024-06-18 03:01:26,10349,accounts,3
  -1,action_called_account_add,2024-06-19 01:30:58,10349,accounts,4
  -1,action_called_account_add,2024-06-28 01:45:46,10349,accounts,5
  -1,action_called_account_add,2024-07-03 17:24:29,10250,accounts,6
  -1,action_called_account_add,2024-07-03 17:37:44,10250,accounts,7
  35,action_account_add,2024-07-03 17:38:16,10236,accounts,8
  35,action_set_password,2024-07-03 17:38:16,10236,accounts,9
  36,action_account_add,2024-07-11 13:49:34,10761,accounts,10
  37,action_account_add,2024-08-08 17:45:14,10398,accounts,11
  37,action_called_account_remove,2024-08-08 17:55:27,10398,accounts,12
  37,action_account_remove,2024-08-08 17:55:27,10398,accounts,13
  38,action_account_add,2024-08-08 17:55:27,10398,accounts,14
  38,action_authenticator_remove,2024-08-25 22:00:34,1000,accounts,15
  39,action_account_add,2024-09-24 11:47:34,10444,accounts,16
  40,action_account_add,2024-10-08 13:00:19,10374,accounts,17
  41,action_account_add,2024-10-13 11:44:55,10463,accounts,18
  41,action_called_account_remove,2024-10-14 14:50:46,10463,accounts,19
  41,action_account_remove,2024-10-14 14:50:46,1000,accounts,20
  36,action_called_account_remove,2024-10-24 17:59:44,10761,accounts,21
  36,action_account_remove,2024-10-24 17:59:44,1000,accounts,22
  42,action_account_add,2024-10-24 17:59:44,10761,accounts,23
  42,action_called_account_remove,2024-10-26 03:11:10,10761,accounts,24
  42,action_account_remove,2024-10-26 03:11:10,1000,accounts,25
  43,action_account_add,2024-10-26 03:11:10,10761,accounts,26
  43,action_called_account_remove,2024-11-02 10:08:29,10761,accounts,27
  43,action_account_remove,2024-11-02 10:08:30,1000,accounts,28
  44,action_account_add,2024-11-02 10:08:30,10761,accounts,29
  44,action_called_account_remove,2024-11-09 11:37:49,10761,accounts,30
  44,action_account_remove,2024-11-09 11:37:49,1000,accounts,31
  45,action_account_add,2024-11-09 11:37:49,10761,accounts,32
  40,action_called_account_remove,2024-11-11 15:55:13,10374,accounts,33
  40,action_account_remove,2024-11-11 15:55:13,1000,accounts,34
  46,action_account_add,2024-11-11 15:55:13,10374,accounts,35
  45,action_called_account_remove,2024-11-16 12:05:51,10761,accounts,36
  45,action_account_remove,2024-11-16 12:05:51,1000,accounts,37
  47,action_account_add,2024-11-16 12:05:51,10761,accounts,38
  1,action_called_account_remove,2024-11-17 20:31:25,10100,accounts,39
  1,action_account_remove,2024-11-17 20:31:26,10100,accounts,40
  2,action_called_account_remove,2024-11-17 20:31:40,10103,accounts,41
  2,action_account_remove,2024-11-17 20:31:41,10103,accounts,42

  Active Sessions: 1
    Session: expectLaunch false, connected true, stats (0/0/0), lifetime 345.551, hasFeatures, Account {name=alina00427@gmail.com, type=com.microsoft.appmanager}, android.account.DEVICE_OR_PROFILE_OWNER_DISALLOWED

  RegisteredServicesCache: 36 services
    ServiceInfo: AuthenticatorDescription {type=com.microsoft.appmanager}, ComponentInfo{com.microsoft.appmanager/com.microsoft.appmanager.oem.account.AccountAuthenticatorService}, uid 10140
    ServiceInfo: AuthenticatorDescription {type=com.microsoft.msa.ltw}, ComponentInfo{com.microsoft.appmanager/com.microsoft.identity.broker.accountmanager.AccountManagerServiceForPersonalAccounts}, uid 10140
    ServiceInfo: AuthenticatorDescription {type=com.google.android.apps.tachyon}, ComponentInfo{com.google.android.apps.tachyon/com.google.android.apps.tachyon.contacts.sync.DuoAccountService}, uid 10249
    ServiceInfo: AuthenticatorDescription {type=com.microsoft.workaccount.authapp}, ComponentInfo{com.azure.authenticator/com.microsoft.identity.broker.accountmanager.AccountManagerServiceForWorkAccounts}, uid 10349
    ServiceInfo: AuthenticatorDescription {type=zenly.account}, ComponentInfo{app.zenly.locator/co.znly.core.authenticator.ZenlyCoreAuthenticatorService}, uid 10371
    ServiceInfo: AuthenticatorDescription {type=com.microsoft.ltwpassthroughbackup}, ComponentInfo{com.microsoft.appmanager/com.microsoft.identity.broker.activebrokerselection.backupipc.service.AccountManagerIpcServiceForRequestsTargetingSpecificBrokerApp}, uid 10140
    ServiceInfo: AuthenticatorDescription {type=com.microsoft.office}, ComponentInfo{com.microsoft.office.word/com.microsoft.office.msohttp.keystore.OfficeAuthenticationService}, uid 10282
    ServiceInfo: AuthenticatorDescription {type=com.snapchat.location}, ComponentInfo{com.snapchat.android/com.snap.location.livelocation.syncadapter.AuthenticatorService}, uid 10549
    ServiceInfo: AuthenticatorDescription {type=com.skype.raider}, ComponentInfo{com.skype.raider/com.skype4life.syncadapter.AccountService}, uid 10686
    ServiceInfo: AuthenticatorDescription {type=com.samsung.android.mobileservice}, ComponentInfo{com.samsung.android.mobileservice/com.samsung.android.mobileservice.social.calendar.presentation.service.background.account.AuthenticatorService}, uid 10103
    ServiceInfo: AuthenticatorDescription {type=com.google.android.gm.exchange}, ComponentInfo{com.google.android.gm/com.android.email.service.EasAuthenticatorService}, uid 10256
    ServiceInfo: AuthenticatorDescription {type=com.microsoft.authapppassthroughbackup}, ComponentInfo{com.azure.authenticator/com.microsoft.identity.broker.activebrokerselection.backupipc.service.AccountManagerIpcServiceForRequestsTargetingSpecificBrokerApp}, uid 10349
    ServiceInfo: AuthenticatorDescription {type=com.viber.voip}, ComponentInfo{com.viber.voip/com.viber.service.contacts.authentication.AccountAuthenticatorService}, uid 10326
    ServiceInfo: AuthenticatorDescription {type=com.microsoft.msa.authapp}, ComponentInfo{com.azure.authenticator/com.microsoft.identity.broker.accountmanager.AccountManagerServiceForPersonalAccounts}, uid 10349
    ServiceInfo: AuthenticatorDescription {type=com.myunidays.account}, ComponentInfo{com.myunidays/com.myunidays.account.AuthenticatorService}, uid 10463
    ServiceInfo: AuthenticatorDescription {type=de.mcdonalds.mcdonaldsinfoapp.account}, ComponentInfo{de.mcdonalds.mcdonaldsinfoapp/com.mcdonalds.app.account.AuthenticatorService}, uid 10698
    ServiceInfo: AuthenticatorDescription {type=org.telegram.messenger}, ComponentInfo{org.telegram.messenger/org.telegram.messenger.AuthenticatorService}, uid 10325
    ServiceInfo: AuthenticatorDescription {type=de.dm.mobile.customer.account}, ComponentInfo{de.dm.meindm.android/de.dm.meindm.android.account.AuthenticatorService}, uid 10374
    ServiceInfo: AuthenticatorDescription {type=com.microsoft.workaccount.ltw}, ComponentInfo{com.microsoft.appmanager/com.microsoft.identity.broker.accountmanager.AccountManagerServiceForWorkAccounts}, uid 10140
    ServiceInfo: AuthenticatorDescription {type=com.microsoft.skydrive}, ComponentInfo{com.microsoft.skydrive/com.microsoft.authorization.AuthenticationService}, uid 10094
    ServiceInfo: AuthenticatorDescription {type=com.whatsapp}, ComponentInfo{com.whatsapp/com.whatsapp.accountsync.AccountAuthenticatorService}, uid 10324
    ServiceInfo: AuthenticatorDescription {type=com.google.android.gm.pop3}, ComponentInfo{com.google.android.gm/com.android.email.service.Pop3AuthenticatorService}, uid 10256
    ServiceInfo: AuthenticatorDescription {type=com.google}, ComponentInfo{com.google.android.gms/com.google.android.gms.auth.account.authenticator.GoogleAccountAuthenticatorService}, uid 10236
    ServiceInfo: AuthenticatorDescription {type=com.snapchat.android}, ComponentInfo{com.snapchat.android/com.snap.identity.enhancedcontacts.core.SnapAndroidAccountAuthenticatorService}, uid 10549
    ServiceInfo: AuthenticatorDescription {type=com.zhiliaoapp.account}, ComponentInfo{com.zhiliaoapp.musically/com.ss.android.ugc.trill.account.TiktokAuthService}, uid 10327
    ServiceInfo: AuthenticatorDescription {type=com.microsoft.workaccount}, ComponentInfo{com.azure.authenticator/com.microsoft.workaccount.authenticatorservice.AuthenticatorService}, uid 10349
    ServiceInfo: AuthenticatorDescription {type=app.diia.gov.ua}, ComponentInfo{ua.gov.diia.app/ua.gov.diia.app.service.sync.AuthenticatorS}, uid 10358
    ServiceInfo: AuthenticatorDescription {type=com.kaufland.klapp.Account}, ComponentInfo{com.kaufland.Kaufland/kaufland.com.accountkit.oauth.UserAuthenticatorService}, uid 10763
    ServiceInfo: AuthenticatorDescription {type=com.osp.app.signin}, ComponentInfo{com.osp.app.signin/com.samsung.android.samsungaccount.authentication.service.OspAuthenticationService}, uid 10100
    ServiceInfo: AuthenticatorDescription {type=com.samsung.android.coreapps}, ComponentInfo{com.samsung.android.mobileservice/com.samsung.android.mobileservice.registration.auth.legacy.presentation.coreapps.AuthenticationService}, uid 10103
    ServiceInfo: AuthenticatorDescription {type=www.instagram.com}, ComponentInfo{com.instagram.android/com.instagram.libraries.access.accountmanager.service.InstagramAuthenticationService}, uid 10761
    ServiceInfo: AuthenticatorDescription {type=com.pinterest.accounttransfer.type}, ComponentInfo{com.pinterest/com.pinterest.account.AuthenticatorService}, uid 10377
    ServiceInfo: AuthenticatorDescription {type=com.facebook.lite}, ComponentInfo{com.facebook.lite/com.facebook.lite.access.accountmanager.AccountAuthenticatorService}, uid 10661
    ServiceInfo: AuthenticatorDescription {type=com.facebook.auth.login}, ComponentInfo{com.facebook.katana/com.facebook.katana.platform.FacebookAuthenticationService}, uid 10298
    ServiceInfo: AuthenticatorDescription {type=Immowelt.de}, ComponentInfo{de.immowelt.android.immobiliensuche/de.immowelt.mobile.android.sdk.core.service.DefaultAccountService}, uid 10444
    ServiceInfo: AuthenticatorDescription {type=com.google.android.gm.legacyimap}, ComponentInfo{com.google.android.gm/com.android.email.service.LegacyImapAuthenticatorService}, uid 10256

  Account visibility:
    alina00427@gmail.com
      com.google.android.apps.translate, 2
      com.google.ar.core, 2
      notion.id, 2
      com.gosund.smart, 2
      com.stepstone.borowf01, 2
      com.realtimeboard, 2
      com.britishcouncil.ieltsprep, 2
      com.linkedin.android, 2
      com.chess, 2
      com.soundcloud.android, 2
      com.google.android.apps.restore, 2
      com.google.android.apps.walletnfcrel, 2
      de.stocard.stocard, 2
      com.google.android.apps.docs.editors.docs, 2
      app.foryou, 2
      com.twitter.android, 2
      com.zzkko, 2
      com.indeed.android.jobsearch, 2
      com.google.android.apps.photos, 2
      de.mcdonalds.mcdonaldsinfoapp, 2
      com.adobe.scan.android, 2
      com.google.android.apps.docs, 2
      com.google.android.apps.chromecast.app, 2
      com.google.android.apps.subscriptions.red, 2
      com.iherb, 2
      com.google.android.apps.maps, 2
      com.google.android.apps.messaging, 2
      com.zaful, 2
      com.google.android.apps.classroom, 2
      com.habitrpg.android.habitica, 2
      com.google.android.videos, 2
      com.zhiliaoapp.musically, 2
      shafa.odejda.obuv.aksessuary, 2
      com.duolingo, 2
      com.waze, 2
      period.tracker.calendar.ovulation.women.fertility.cycle, 2
      com.hevy, 2
      com.relevoapp, 2
      com.picsart.studio, 2
      com.wolt.android, 2
      com.google.android.apps.docs.editors.sheets, 2
      de.burgerking.kingfinder, 2
      app.source.getcontact, 2
      com.medvediev.dono, 2
      com.lemon.lvoverseas, 2
      com.google.android.gm, 2
      com.app.tgtg, 2
      com.xing.android, 2
      com.babblerdev.familyshoping, 2
      com.yopeso.lieferando, 2
      com.tier.app, 2
      com.cider, 2
    barynina.alina@lll.kpi.ua
      com.google.android.apps.maps, 2
      com.google.android.apps.translate, 2
      com.google.android.apps.docs.editors.docs, 2
      com.google.ar.core, 2
      com.google.android.apps.messaging, 2
      com.google.android.apps.classroom, 2
      com.google.android.apps.photos, 2
      com.google.android.videos, 2
      com.google.android.apps.docs.editors.sheets, 2
      com.google.android.gm, 2
      com.google.android.apps.docs, 2
      com.google.android.apps.chromecast.app, 2
      com.google.android.apps.restore, 2
      com.google.android.apps.subscriptions.red, 2
      com.google.android.apps.walletnfcrel, 2
    den.blackshov@gmail.com
      com.google.android.apps.maps, 2
      com.google.android.apps.translate, 2
      com.google.android.apps.docs.editors.docs, 2
      com.google.ar.core, 2
      com.google.android.apps.messaging, 2
      com.google.android.apps.classroom, 2
      com.google.android.apps.photos, 2
      com.google.android.videos, 2
      com.booking, 2
      com.google.android.apps.docs.editors.sheets, 2
      com.google.android.gm, 2
      com.google.android.apps.docs, 2
      com.google.android.apps.chromecast.app, 2
      com.google.android.apps.restore, 2
      com.google.android.apps.subscriptions.red, 2
      com.google.android.apps.walletnfcrel, 2
    io13fiot@gmail.com
      com.google.android.videos, 2
      com.google.android.apps.docs.editors.sheets, 2
      com.google.android.apps.chromecast.app, 2
      com.google.android.apps.restore, 2
      com.google.android.apps.subscriptions.red, 2
      com.google.android.apps.classroom, 2
      com.google.android.apps.walletnfcrel, 2
    alina.barynina.dev@gmail.com
      com.google.android.apps.maps, 2
      com.google.android.apps.translate, 2
      com.google.android.apps.docs.editors.docs, 2
      com.google.ar.core, 2
      com.google.android.apps.messaging, 2
      com.google.android.apps.classroom, 2
      com.google.android.apps.photos, 2
      com.google.android.videos, 2
      com.google.android.apps.docs.editors.sheets, 2
      com.google.android.gm, 2
      com.google.android.apps.docs, 2
      com.google.android.apps.chromecast.app, 2
      com.google.android.apps.restore, 2
      com.google.android.apps.subscriptions.red, 2
      com.google.android.apps.walletnfcrel, 2
    a1inabar0707@gmail.com
      com.google.android.videos, 2
      com.google.android.apps.docs.editors.sheets, 2
      at.helloagain.muellerde, 2
      com.yazio.android, 2
      com.google.android.apps.chromecast.app, 2
      com.google.android.apps.restore, 2
      fr.vinted, 2
      com.google.android.apps.subscriptions.red, 2
      com.google.android.apps.classroom, 2
      com.google.android.apps.walletnfcrel, 2


###
User UserInfo{0:Alina Barynina:4c13}:
  Accounts: 9
    Account {name=Pinterest, type=com.pinterest.dev.accounttransfer.type}
    Account {name=alina00427@gmail.com, type=com.adobe.creativesdk.foundation.auth.adobeID.DC}
    Account {name=TikTok, type=com.zhiliaoapp.musically}
    Account {name=Alina27381511, type=com.twitter.android.auth.login}
    Account {name=alina-barynina, type=com.soundcloud.android.account}
    Account {name=Duolingo, type=com.duolingo}
    Account {name=den.blackshov@gmail.com, type=de.check24.check24.account}
    Account {name=aljolen#2319, type=com.blizzard.mobile.auth}
    Account {name=5684016505, type=www.instagram.com}

  AccountId, Action_Type, timestamp, UID, TableName, Key
  Accounts History
  43,action_account_remove,2024-11-02 10:08:30,1000,accounts,28
  44,action_account_add,2024-11-02 10:08:30,10761,accounts,29
  44,action_called_account_remove,2024-11-09 11:37:49,10761,accounts,30
  44,action_account_remove,2024-11-09 11:37:49,1000,accounts,31
  45,action_account_add,2024-11-09 11:37:49,10761,accounts,32
  40,action_called_account_remove,2024-11-11 15:55:13,10374,accounts,33
  40,action_account_remove,2024-11-11 15:55:13,1000,accounts,34
  46,action_account_add,2024-11-11 15:55:13,10374,accounts,35
  45,action_called_account_remove,2024-11-16 12:05:51,10761,accounts,36
  45,action_account_remove,2024-11-16 12:05:51,1000,accounts,37
  47,action_account_add,2024-11-16 12:05:51,10761,accounts,38
  1,action_called_account_remove,2024-11-17 20:31:25,10100,accounts,39
  1,action_account_remove,2024-11-17 20:31:26,10100,accounts,40
  2,action_called_account_remove,2024-11-17 20:31:40,10103,accounts,41
  2,action_account_remove,2024-11-17 20:31:41,10103,accounts,42
  17,action_called_account_remove,2024-11-17 20:38:18,1000,accounts,43
  17,action_account_remove,2024-11-17 20:38:19,10236,accounts,44
  35,action_called_account_remove,2024-11-17 20:38:22,1000,accounts,45
  35,action_account_remove,2024-11-17 20:38:23,10236,accounts,46
  16,action_called_account_remove,2024-11-17 20:38:59,1000,accounts,47
  16,action_account_remove,2024-11-17 20:38:59,10236,accounts,48
  15,action_called_account_remove,2024-11-17 20:39:09,1000,accounts,49
  15,action_account_remove,2024-11-17 20:39:10,10236,accounts,50
  27,action_called_account_remove,2024-11-17 20:39:13,1000,accounts,51
  27,action_account_remove,2024-11-17 20:39:14,10236,accounts,52
  3,action_called_account_remove,2024-11-17 20:39:22,1000,accounts,53
  3,action_called_account_remove,2024-11-17 20:40:01,1000,accounts,54
  3,action_account_remove,2024-11-17 20:40:28,10236,accounts,55
  24,action_called_account_remove,2024-11-17 20:40:37,10249,accounts,56
  24,action_account_remove,2024-11-17 20:40:37,10249,accounts,57
  34,action_called_account_remove,2024-11-17 20:41:31,1000,accounts,58
  34,action_account_remove,2024-11-17 20:41:32,10298,accounts,59
  33,action_called_account_remove,2024-11-17 20:41:35,1000,accounts,60
  33,action_account_remove,2024-11-17 20:41:35,10298,accounts,61
  26,action_called_account_remove,2024-11-17 20:41:38,1000,accounts,62
  48,action_account_add,2024-11-17 20:41:39,10298,accounts,63
  26,action_account_remove,2024-11-17 20:41:42,10763,accounts,0
  48,action_called_account_remove,2024-11-17 20:41:46,1000,accounts,1
  48,action_account_remove,2024-11-17 20:41:48,10298,accounts,2
  29,action_called_account_remove,2024-11-17 20:41:56,1000,accounts,3
  29,action_account_remove,2024-11-17 20:41:56,10140,accounts,4
  12,action_called_account_remove,2024-11-17 20:41:59,1000,accounts,5
  12,action_account_remove,2024-11-17 20:42:01,10249,accounts,6
  18,action_called_account_remove,2024-11-17 20:42:04,1000,accounts,7
  18,action_account_remove,2024-11-17 20:42:04,10282,accounts,8
  39,action_called_account_remove,2024-11-17 20:42:16,1000,accounts,9
  39,action_account_remove,2024-11-17 20:42:17,10444,accounts,10
  46,action_called_account_remove,2024-11-17 20:42:19,1000,accounts,11
  46,action_account_remove,2024-11-17 20:42:20,10374,accounts,12
  23,action_called_account_remove,2024-11-17 20:42:23,1000,accounts,13
  23,action_account_remove,2024-11-17 20:42:24,10371,accounts,14
  10,action_called_account_remove,2024-11-17 20:42:29,1000,accounts,15
  10,action_account_remove,2024-11-17 20:42:29,10324,accounts,16
  7,action_called_account_remove,2024-11-17 20:42:35,1000,accounts,17
  7,action_account_remove,2024-11-17 20:42:35,10325,accounts,18
  49,action_account_add,2024-11-17 20:42:38,10324,accounts,19
  30,action_called_account_remove,2024-11-17 20:42:38,1000,accounts,20
  30,action_account_remove,2024-11-17 20:42:39,10686,accounts,21
  14,action_called_account_remove,2024-11-17 20:42:43,1000,accounts,22
  14,action_account_remove,2024-11-17 20:42:43,10256,accounts,23
  49,action_called_account_remove,2024-11-17 20:42:46,1000,accounts,24
  49,action_account_remove,2024-11-17 20:42:46,10324,accounts,25
  4,action_called_account_remove,2024-11-17 20:42:48,1000,accounts,26
  4,action_account_remove,2024-11-17 20:42:49,10094,accounts,27

  Active Sessions: 1
    Session: expectLaunch false, connected true, stats (0/0/0), lifetime 706.966, hasFeatures, Account {name=alina00427@gmail.com, type=com.microsoft.appmanager}, android.account.DEVICE_OR_PROFILE_OWNER_DISALLOWED

  RegisteredServicesCache: 36 services
    ServiceInfo: AuthenticatorDescription {type=com.microsoft.appmanager}, ComponentInfo{com.microsoft.appmanager/com.microsoft.appmanager.oem.account.AccountAuthenticatorService}, uid 10140
    ServiceInfo: AuthenticatorDescription {type=com.microsoft.msa.ltw}, ComponentInfo{com.microsoft.appmanager/com.microsoft.identity.broker.accountmanager.AccountManagerServiceForPersonalAccounts}, uid 10140
    ServiceInfo: AuthenticatorDescription {type=com.google.android.apps.tachyon}, ComponentInfo{com.google.android.apps.tachyon/com.google.android.apps.tachyon.contacts.sync.DuoAccountService}, uid 10249
    ServiceInfo: AuthenticatorDescription {type=com.microsoft.workaccount.authapp}, ComponentInfo{com.azure.authenticator/com.microsoft.identity.broker.accountmanager.AccountManagerServiceForWorkAccounts}, uid 10349
    ServiceInfo: AuthenticatorDescription {type=zenly.account}, ComponentInfo{app.zenly.locator/co.znly.core.authenticator.ZenlyCoreAuthenticatorService}, uid 10371
    ServiceInfo: AuthenticatorDescription {type=com.microsoft.ltwpassthroughbackup}, ComponentInfo{com.microsoft.appmanager/com.microsoft.identity.broker.activebrokerselection.backupipc.service.AccountManagerIpcServiceForRequestsTargetingSpecificBrokerApp}, uid 10140
    ServiceInfo: AuthenticatorDescription {type=com.microsoft.office}, ComponentInfo{com.microsoft.office.word/com.microsoft.office.msohttp.keystore.OfficeAuthenticationService}, uid 10282
    ServiceInfo: AuthenticatorDescription {type=com.snapchat.location}, ComponentInfo{com.snapchat.android/com.snap.location.livelocation.syncadapter.AuthenticatorService}, uid 10549
    ServiceInfo: AuthenticatorDescription {type=com.skype.raider}, ComponentInfo{com.skype.raider/com.skype4life.syncadapter.AccountService}, uid 10686
    ServiceInfo: AuthenticatorDescription {type=com.samsung.android.mobileservice}, ComponentInfo{com.samsung.android.mobileservice/com.samsung.android.mobileservice.social.calendar.presentation.service.background.account.AuthenticatorService}, uid 10103
    ServiceInfo: AuthenticatorDescription {type=com.google.android.gm.exchange}, ComponentInfo{com.google.android.gm/com.android.email.service.EasAuthenticatorService}, uid 10256
    ServiceInfo: AuthenticatorDescription {type=com.microsoft.authapppassthroughbackup}, ComponentInfo{com.azure.authenticator/com.microsoft.identity.broker.activebrokerselection.backupipc.service.AccountManagerIpcServiceForRequestsTargetingSpecificBrokerApp}, uid 10349
    ServiceInfo: AuthenticatorDescription {type=com.viber.voip}, ComponentInfo{com.viber.voip/com.viber.service.contacts.authentication.AccountAuthenticatorService}, uid 10326
    ServiceInfo: AuthenticatorDescription {type=com.microsoft.msa.authapp}, ComponentInfo{com.azure.authenticator/com.microsoft.identity.broker.accountmanager.AccountManagerServiceForPersonalAccounts}, uid 10349
    ServiceInfo: AuthenticatorDescription {type=com.myunidays.account}, ComponentInfo{com.myunidays/com.myunidays.account.AuthenticatorService}, uid 10463
    ServiceInfo: AuthenticatorDescription {type=de.mcdonalds.mcdonaldsinfoapp.account}, ComponentInfo{de.mcdonalds.mcdonaldsinfoapp/com.mcdonalds.app.account.AuthenticatorService}, uid 10698
    ServiceInfo: AuthenticatorDescription {type=org.telegram.messenger}, ComponentInfo{org.telegram.messenger/org.telegram.messenger.AuthenticatorService}, uid 10325
    ServiceInfo: AuthenticatorDescription {type=de.dm.mobile.customer.account}, ComponentInfo{de.dm.meindm.android/de.dm.meindm.android.account.AuthenticatorService}, uid 10374
    ServiceInfo: AuthenticatorDescription {type=com.microsoft.workaccount.ltw}, ComponentInfo{com.microsoft.appmanager/com.microsoft.identity.broker.accountmanager.AccountManagerServiceForWorkAccounts}, uid 10140
    ServiceInfo: AuthenticatorDescription {type=com.microsoft.skydrive}, ComponentInfo{com.microsoft.skydrive/com.microsoft.authorization.AuthenticationService}, uid 10094
    ServiceInfo: AuthenticatorDescription {type=com.whatsapp}, ComponentInfo{com.whatsapp/com.whatsapp.accountsync.AccountAuthenticatorService}, uid 10324
    ServiceInfo: AuthenticatorDescription {type=com.google.android.gm.pop3}, ComponentInfo{com.google.android.gm/com.android.email.service.Pop3AuthenticatorService}, uid 10256
    ServiceInfo: AuthenticatorDescription {type=com.google}, ComponentInfo{com.google.android.gms/com.google.android.gms.auth.account.authenticator.GoogleAccountAuthenticatorService}, uid 10236
    ServiceInfo: AuthenticatorDescription {type=com.snapchat.android}, ComponentInfo{com.snapchat.android/com.snap.identity.enhancedcontacts.core.SnapAndroidAccountAuthenticatorService}, uid 10549
    ServiceInfo: AuthenticatorDescription {type=com.zhiliaoapp.account}, ComponentInfo{com.zhiliaoapp.musically/com.ss.android.ugc.trill.account.TiktokAuthService}, uid 10327
    ServiceInfo: AuthenticatorDescription {type=com.microsoft.workaccount}, ComponentInfo{com.azure.authenticator/com.microsoft.workaccount.authenticatorservice.AuthenticatorService}, uid 10349
    ServiceInfo: AuthenticatorDescription {type=app.diia.gov.ua}, ComponentInfo{ua.gov.diia.app/ua.gov.diia.app.service.sync.AuthenticatorS}, uid 10358
    ServiceInfo: AuthenticatorDescription {type=com.kaufland.klapp.Account}, ComponentInfo{com.kaufland.Kaufland/kaufland.com.accountkit.oauth.UserAuthenticatorService}, uid 10763
    ServiceInfo: AuthenticatorDescription {type=com.osp.app.signin}, ComponentInfo{com.osp.app.signin/com.samsung.android.samsungaccount.authentication.service.OspAuthenticationService}, uid 10100
    ServiceInfo: AuthenticatorDescription {type=com.samsung.android.coreapps}, ComponentInfo{com.samsung.android.mobileservice/com.samsung.android.mobileservice.registration.auth.legacy.presentation.coreapps.AuthenticationService}, uid 10103
    ServiceInfo: AuthenticatorDescription {type=www.instagram.com}, ComponentInfo{com.instagram.android/com.instagram.libraries.access.accountmanager.service.InstagramAuthenticationService}, uid 10761
    ServiceInfo: AuthenticatorDescription {type=com.pinterest.accounttransfer.type}, ComponentInfo{com.pinterest/com.pinterest.account.AuthenticatorService}, uid 10377
    ServiceInfo: AuthenticatorDescription {type=com.facebook.lite}, ComponentInfo{com.facebook.lite/com.facebook.lite.access.accountmanager.AccountAuthenticatorService}, uid 10661
    ServiceInfo: AuthenticatorDescription {type=com.facebook.auth.login}, ComponentInfo{com.facebook.katana/com.facebook.katana.platform.FacebookAuthenticationService}, uid 10298
    ServiceInfo: AuthenticatorDescription {type=Immowelt.de}, ComponentInfo{de.immowelt.android.immobiliensuche/de.immowelt.mobile.android.sdk.core.service.DefaultAccountService}, uid 10444
    ServiceInfo: AuthenticatorDescription {type=com.google.android.gm.legacyimap}, ComponentInfo{com.google.android.gm/com.android.email.service.LegacyImapAuthenticatorService}, uid 10256

  Account visibility:

