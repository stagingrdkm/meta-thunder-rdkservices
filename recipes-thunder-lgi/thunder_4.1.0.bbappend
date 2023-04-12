inherit onemw_build_type

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}:"

# R2-v1.12 tag revision
# R4.1 tag version
SRCREV_thunder = "65bff2a6c5599ccbeb01d77a16417ad9d09df918"
RDEPENDS_${PN} += "rfc libodherr"
DEPENDS_remove = "breakpad-wrapper"
DEPENDS_append = " breakpad"
DEPENDS_append = " glib-2.0"
DEPENDS_append = " dbus libodherr lxc systemd"

# SecurityAgent plugin is not integrated, that patch was and is removed
SRC_URI_remove = "file://0001-RDK-28534-Security-Agent-Utility-and-Logging.patch"
# we need only part of that FindRFC.cmake
SRC_URI += "file://0001-RDK-28534-Security-Agent-Utility-and-Logging-OnlyFindRFC.patch"

SRC_URI_remove = "file://trace_log_flag_enabled.patch"

EXTRA_OECMAKE += "${@in_debug_or_release_no_ops(bb, d, '-DBINDING=0.0.0.0')}"
EXTRA_OECMAKE += "-DTRACING_ONLY_DIRECT_OUTPUT=ON"
EXTRA_OECMAKE += "-DTRACE_SETTINGS=logCfg.json"
# this is not setup of umask to 0 value, but leave the value that is originally set
EXTRA_OECMAKE += "-DUMASK=000"
EXTRA_OECMAKE += "-DHIDE_NON_EXTERNAL_SYMBOLS=ON"
EXTRA_OECMAKE += "-DCOMMUNICATOR=/run/wpeframework/communicator"
EXTRA_OECMAKE += "-DINPUT_LOCATOR=/run/wpeframework/keyhandler"
EXTRA_OECMAKE += "-DKEY_OUTPUT_DISABLED=true"
EXTRA_OECMAKE += "-DWARNING_REPORTING=OFF"
EXTRA_OECMAKE += "-DEXCEPTION_CATCHING=ON"
EXTRA_OECMAKR += "-DENABLED_TRACING_LEVEL=3"

TARGET_CXXFLAGS += "${@bb.utils.contains('MACHINE_FEATURES', 'debug', '-D_TRACE_LEVEL=2 -D__ENABLE_ASSERT__=ON', '', d)}"
TARGET_CXXFLAGS += "-D__ERRORRESULT__=errno"

# file://0001-ONEM-22285-do-not-modify-local-files.patch
# file://no_color_in_trace_1_12.patch
# file://0112-ONEM-15869-bugfix-container-stop.patch
# file://0117-ONEM-22611-So-locations-restricted_1_12.patch
# file://0120-ONEM-23505-Setup-configuration-umask-by-CMake.patch
# file://0122-ONEM-23840-Support-for-serving-gzip-compressed-files.patch
# file://0123-ARRISAPOL-2315-Missing-external-symbols-not-exported.patch
# file://0124-ARRISEOS-40990-Missing-logs-from-syslog.patch
# file://0125-ONEM-24034-Setup-group-and-permissions-for-socket.patch
# file://0128-ONEM-24449-Load-fallback-logging-configuration.patch
# file://0132-HUMAXEOSR-995-Block-plugins-activation-when-WPEFrame.patch
# file://0134-HUMAXEOSR-995-WPEProcess-crash-on-termination-with-_.patch
# file://0135-ARRISEOS-41575-extend_timeout_to_25sec.patch
# file://0136-JSON-FloatType-updated-to-support-single-byte-wise-d.patch
# file://0137-JSON-Automatically-escape-UTF8-in-JSON_1_12.patch
# file://0138-JSON-RPC-parsing-fixes-848.patch
# file://0139-Revert-DELIA-54331-Don-t-set-the-receive-buffer.patch
# file://0146-ONEM-22659-Synchronization-in-Release-changed.patch
# file://0147-fix-cobalt-crash.patch
# file://0125-ONEM-24034-Setup-group-and-permissions-for-socket.patch == rights_patch.patch


SRC_URI += "file://0107-ONEM-18296-tracing-use-direct-output.patch \
            file://wpeframework.conf \
            file://core-dumps-0006.conf \
            file://wpeprocess-start.sh \
            file://container_rdk_service_stop.sh \
            file://collect_identification_data.sh \
            file://0110-ONEM-21606-remove-incorrect-ASSERT-R4.patch \
            file://0111-ONEM-15869-awc-proxy-container-R4.patch \
            file://0113-ONEM-21500-added-notifyserviceready-to-awclistener-R4.patch \
            file://0118-ONEM-15903-error-reporting-deinit.patch \
            file://0121-ONEM-23238-Unified-handling-of-logging-configuration.patch \
            file://0126-ONEM-22947_allow_assert_with_trace.patch \
            file://0127-ONEM-22947-Additional-EXTERNAL-R4.patch \
            file://0129-ONEMPERS-285-Limit-thunder-container-access-to-global-tmp.patch \
            file://0131-HUMAXEOSR-995-Termination-thread-introduced.patch \
            file://0133-ONEMPERS-367-Avoid-sending-unknown-method-response.patch \
            file://0142-HUMAXEOS-4773-Fix-wpeframework-crashes-during-reboot.patch \
            file://0143-ARRISAPOL-2718-Add-JSONRPC-helper-prints.patch \
            file://0144-ARRISEOS-42363-Don-t-flush-libraries-in-Dispatch.patch \
            file://0145-ARRISEOS-42502-Fix-random-crashes-under-stress.patch \
            file://0147-ARRISAPP-140-Fix-assert-on-call-to-opencdm_dispose.patch \
            file://0148-FindSlauncher-lost-letter-R4.patch \
            file://0001-COMRPC-Enlarge-the-buffer-in-which-we-hold-the-COMRP.patch \
            file://local.patch \
            file://rights_patch.patch \
"

# 0001-COMRPC-Enlarge-the-buffer-in-which-we-hold-the-COMRP.patch taken from R4 (is on R4.1.1)
# there was a problem: static assert while compilation of wpeframework-interfaces code generated for metrics API
# local.patch
# WARNING_REPORTING feature is problematic for WPEProcesses in contaners (runtime)
# after disable WARNING_REPORTING compilation issues: undefined reference to 'WPEFramework::Core::CallsignTLS::Callsign(char const*)

SRC_URI += "${@bb.utils.contains('PREFERRED_PROVIDER_dialserver', 'xdialserver', ' file://rtrpc.conf ', '', d)}"
SRC_URI += "${@bb.utils.contains('PREFERRED_PROVIDER_dialserver', 'xdialserver', ' file://wpeframework_rtremote.socket ', '', d)}"

WPEFRAMEWORK_PERSISTENT_PATH = "/mnt/wpeframework"

# ONEM-22947: revert back to original configuration.
# debug configuration should be rather considered as devel configuration since it inflates plugins 4x in size and changes timeouts to infinite in many places
# to use it, uncomment 4 lines below:
PACKAGECONFIG_remove = "release"
PACKAGECONFIG += "\
       ${@bb.utils.contains('MACHINE_FEATURES', 'debug', 'debug', 'release', d)} \
"

PACKAGECONFIG += "\
        ${@bb.utils.contains('DISTRO_FEATURES', 'playready_nexus_svp', 'opencdmi_prnx_svp', '', d)} \
"
PACKAGECONFIG[processcontainers_awc]      = "-DPROCESSCONTAINERS_AWC=ON,,slauncher"
PACKAGECONFIG_append = " processcontainers processcontainers_awc"
PACKAGECONFIG_remove = " virtualinput"

do_install_append() {
    install -d ${D}${datadir}/WPEFramework
    install -m 0755 ${WORKDIR}/wpeprocess-start.sh ${D}${datadir}/WPEFramework
    install -d ${D}${systemd_unitdir}/system
    if ${@bb.utils.contains('PREFERRED_PROVIDER_dialserver', 'xdialserver', 'true', 'false', d)}; then
        install -m 0644 ${WORKDIR}/wpeframework_rtremote.socket ${D}/${systemd_unitdir}/system/
        install -m 0440 ${WORKDIR}/rtrpc.conf ${D}${sysconfdir}/WPEFramework/
    fi
    install -d ${D}${sysconfdir}/tmpfiles.d
    install -m 0644 ${WORKDIR}/wpeframework.conf ${D}${sysconfdir}/tmpfiles.d
    install -m 0644 ${WORKDIR}/core-dumps-0006.conf ${D}${sysconfdir}/tmpfiles.d
    ln -sf ${datadir}/logcfg/current/THUNDER/log.json ${D}${sysconfdir}/WPEFramework/logCfg.json
    ln -sf ${datadir}/defaultlogcfg/THUNDER/log.json ${D}${sysconfdir}/WPEFramework/logCfgDefault.json
    install -d ${D}/${bindir}
    install -m 0755 ${WORKDIR}/container_rdk_service_stop.sh ${D}${bindir}/container_rdk_service_stop.sh
    install -m 0755 ${WORKDIR}/collect_identification_data.sh ${D}${bindir}/collect_identification_data.sh
}

SYSTEMD_SERVICE_${PN} += "${@bb.utils.contains('PREFERRED_PROVIDER_dialserver', 'xdialserver', 'wpeframework_rtremote.socket ', '', d)}"
FILES_${PN} += "${sysconfdir}/tmpfiles.d/wpeframework.conf ${sysconfdir}tmpfiles.d/core-dumps-0006.conf"
FILES_${PN} += "${bindir}/container_rdk_service_stop.sh"


