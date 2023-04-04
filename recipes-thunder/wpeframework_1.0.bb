SUMMARY = "Thunder Framework"
require include/thunder.inc
LIC_FILES_CHKSUM = "file://LICENSE;md5=85bcfede74b96d9a58c6ea5d4b607e58"

DEPENDS = "zlib wpeframework-tools-native rfc"
DEPENDS_append_libc-musl = " libexecinfo"
DEPENDS += "breakpad-wrapper"

# Need gst-svp-ext which is an abstracting lib for metadata
DEPENDS +=  "${@bb.utils.contains('DISTRO_FEATURES', 'rdk_svp', 'gst-svp-ext', '', d)}"

FILESEXTRAPATHS_append := ":${THISDIR}/${PN}-R4"

#           file://trace_log_flag_enabled.patch - it does not make sense for us

SRC_URI += " \
           file://wpeframework-init \
           file://wpeframework.service.in \
           file://wpeframework.service.xdial.in \
           file://wpeframework.service.no-container.in \
           file://wpeframework.service.no-container.xdial.in \
           file://0003-OCDM-increase-RPC-comm-timeout-R4.patch \
           file://0001-RDK-28534-Security-Agent-Utility-and-Logging.patch \
           file://0004-Enable-BrowserConsoleLog-by-default-R4.patch \
           file://0001-RDKTV-15393-clock_gettime.patch \
           file://LLAMA-2254_fix_netlink_buffer_size_error-R4.patch \
           file://0001-Thunder_Trace_log_enable_error_fix.patch \
           file://to_add_protocols_pkgconfig_sdk.patch \
           file://0001-Thunder_json_quoted_string_parsing_fix.patch \
           file://0001_Reverted_changes_Fix_JSON_Reset_R3.5_for_SystemServicesPlugin.patch \
           file://0001-disable_trace_warnings.patch \
           file://0005-Thunder-R4.1-fixes.patch \
           file://activationevent.patch \
"

inherit systemd update-rc.d python3native

WPEFRAMEWORK_PERSISTENT_PATH = "/opt/persistent/rdkservices"
WPEFRAMEWORK_SYSTEM_PREFIX = "OE"
WPEFRAMEWORK_PORT = "9998"
WPEFRAMEWORK_BINDING = "127.0.0.1"
WPEFRAMEWORK_IDLE_TIME = "0"
WPEFRAMEWORK_THREADPOOL_COUNT ?= "8"
WPEFRAMEWORK_EXIT_REASONS ?= "WatchdogExpired"

PACKAGECONFIG ?= " \
    release \
    virtualinput \
    websocket \
    "

PACKAGECONFIG_append = " ${@bb.utils.contains('DISTRO_FEATURES', 'DOBBY_CONTAINERS','processcontainers processcontainers_dobby', '',d)}"

# Buildtype
# Maybe we need to couple this to a Yocto feature
PACKAGECONFIG[debug]          = "-DBUILD_TYPE=Debug,,"
PACKAGECONFIG[debugoptimized] = "-DBUILD_TYPE=DebugOptimized,,"
PACKAGECONFIG[releasesymbols] = "-DBUILD_TYPE=ReleaseSymbols,,"
PACKAGECONFIG[release]        = "-DBUILD_TYPE=Release,,"
PACKAGECONFIG[production]     = "-DBUILD_TYPE=Production,,"

PACKAGECONFIG[cyclicinspector]  = "-DTEST_CYCLICINSPECTOR=ON,-DTEST_CYCLICINSPECTOR=OFF,"
PACKAGECONFIG[provisionproxy]   = "-DPROVISIONPROXY=ON,-DPROVISIONPROXY=OFF,libprovision"
PACKAGECONFIG[testloader]       = "-DTEST_LOADER=ON,-DTEST_LOADER=OFF,"
PACKAGECONFIG[virtualinput]     = "-DVIRTUALINPUT=ON,-DVIRTUALINPUT=OFF,"
PACKAGECONFIG[bluetooth]        = "-DBLUETOOTH_SUPPORT=ON,-DBLUETOOTH_SUPPORT=OFF,"

PACKAGECONFIG[processcontainers]          = "-DPROCESSCONTAINERS=ON,-DPROCESSCONTAINERS=OFF,"
PACKAGECONFIG[processcontainers_dobby]    = "-DPROCESSCONTAINERS_DOBBY=ON,,dobby"

# FIXME
# The WPEFramework also needs limited Plugin info in order to determine what to put in the "resumes" configuration
# it feels a bit the other way around but lets set at least webserver and webkit
PACKAGECONFIG[websource]       = "-DPLUGIN_WEBSERVER=ON,,"
PACKAGECONFIG[webkitbrowser]   = "-DPLUGIN_WEBKITBROWSER=ON,,"
PACKAGECONFIG[websocket]       = "-DWEBSOCKET=ON,,"

# FIXME, determine this a little smarter
# Provision event is required for libprovision and provision plugin
# Location event is required for locationsync plugin
# Time event is required for timesync plugin
# Identifier event is required for Compositor plugin
# Internet event is provided by the LocationSync plugin
# WebSource event is provided by the WebServer plugin

# Only enable certain events if wpeframework is in distro features
WPEFRAMEWORK_DIST_EVENTS ?= "${@bb.utils.contains('DISTRO_FEATURES', 'wpeframework', 'Network', '', d)}"

WPEFRAMEWORK_EXTERN_EVENTS ?= " \
    Decryption \
    ${@bb.utils.contains('PACKAGECONFIG', 'provisionproxy', 'Provisioning', '', d)} \
    ${@bb.utils.contains('PACKAGECONFIG', 'websource', 'WebSource', '', d)} \
    ${WPEFRAMEWORK_DIST_EVENTS} \
    Location Time Internet \
    ${@bb.utils.contains('DISTRO_FEATURES', 'thunder_security_disable', '', 'Security', d)} \
"

EXTRA_OECMAKE += " \
    -DINSTALL_HEADERS_TO_TARGET=ON \
    -DEXTERN_EVENTS="${WPEFRAMEWORK_EXTERN_EVENTS}" \
    -DEXCEPTIONS_ENABLE=ON \
    -DBUILD_SHARED_LIBS=ON \
    -DRPC=ON \
    -DBUILD_REFERENCE=${SRCREV} \
    -DTREE_REFERENCE=${SRCREV_thunder} \
    -DPORT=${WPEFRAMEWORK_PORT} \
    -DBINDING=${WPEFRAMEWORK_BINDING} \
    -DPERSISTENT_PATH=${WPEFRAMEWORK_PERSISTENT_PATH} \
    -DSYSTEM_PREFIX=${WPEFRAMEWORK_SYSTEM_PREFIX} \
    -DPYTHON_EXECUTABLE=${STAGING_BINDIR_NATIVE}/python3-native/python3 \
    -DIDLE_TIME=${WPEFRAMEWORK_IDLE_TIME} \
    -DTHREADPOOL_COUNT=${WPEFRAMEWORK_THREADPOOL_COUNT} \
    -DEXCEPTION_CATCHING=ON \
    -DWARNING_REPORTING=ON \
    -DEXIT_REASONS=${WPEFRAMEWORK_EXIT_REASONS} \
"

TARGET_CXXFLAGS_remove_class-target_dunfell = "-Werror=return-type"

do_install_append() {
    if ${@bb.utils.contains("DISTRO_FEATURES", "systemd", "true", "false", d)}
    then
        if ${@bb.utils.contains("MACHINE_FEATURES", "platformserver", "true", "false", d)}
        then
           extra_after=""
        elif ${@bb.utils.contains("PREFERRED_PROVIDER_virtual/egl", "broadcom-refsw", "true", bb.utils.contains("PREFERRED_PROVIDER_virtual/egl", "broadcom-ursr", "true", "false", d), d)}
        then
           extra_after="nxserver.service"
        fi

        AMI_SERVICE="${@oe.utils.conditional('WPE_AS_APPMODULE', '1', 'ami.service', '', d)}"

        extra_after="${extra_after} ${WAYLAND_COMPOSITOR} ${AMI_SERVICE}"
        install -d ${D}${systemd_unitdir}/system
        if ${@bb.utils.contains("DISTRO_FEATURES", "onemwthunder-no-container", "true", "false", d)}
        then
            sed -e "s|@EXTRA_AFTER@|${extra_after}|g" < ${WORKDIR}/wpeframework.service.no-container.${@bb.utils.contains("PREFERRED_PROVIDER_dialserver", "xdialserver", "xdial.in", "in", d)} > ${D}${systemd_unitdir}/system/wpeframework.service
        else
            sed -e "s|@EXTRA_AFTER@|${extra_after}|g" < ${WORKDIR}/wpeframework.service.${@bb.utils.contains("PREFERRED_PROVIDER_dialserver", "xdialserver", "xdial.in", "in", d)} > ${D}${systemd_unitdir}/system/wpeframework.service
        fi
    else
        install -d ${D}${sysconfdir}/init.d
        install -m 0755 ${WORKDIR}/wpeframework-init ${D}${sysconfdir}/init.d/wpeframework
    fi
}

SYSTEMD_SERVICE_${PN} = "wpeframework.service"

# ----------------------------------------------------------------------------

PACKAGES =+ "${PN}-initscript"

FILES_${PN}-initscript = "${sysconfdir}/init.d/wpeframework"

FILES_SOLIBSDEV = ""
FILES_${PN} += "${libdir}/*.so ${datadir}/WPEFramework/* ${PKG_CONFIG_DIR}/*.pc"
FILES_${PN} += "${includedir}/cdmi.h"
FILES_${PN}-dev += "${libdir}/cmake/*"
FILES_${PN}-dbg += "${libdir}/wpeframework/proxystubs/.debug/"

# ----------------------------------------------------------------------------

INITSCRIPT_PACKAGES = "${PN}-initscript"
INITSCRIPT_NAME_${PN}-initscript = "wpeframework"

# If WPE Framework is enabled as distro feature, start earlier. Assuming packagegroup-wpe-boot is used and we're in control for the network
WPEFRAMEWORK_START = "${@bb.utils.contains('DISTRO_FEATURES', 'wpeframework', '40', '80', d)}"

INITSCRIPT_PARAMS_${PN}-initscript = "defaults ${WPEFRAMEWORK_START} 24"

RRECOMMENDS_${PN} = "${PN}-initscript"

# ----------------------------------------------------------------------------

INSANE_SKIP_${PN} += "dev-so"
INSANE_SKIP_${PN}-dbg += "dev-so"

# ----------------------------------------------------------------------------

RDEPENDS_${PN}_rpi = "userland"
RDEPENDS_${PN} += "${@bb.utils.contains('DISTRO_FEATURES', 'rdk_svp', 'gst-svp-ext', '', d)}"

# Should be able to remove this when generic rdk_svp flag is added to Xi6 and XG1v4 product-config .inc files
RDEPENDS_${PN} += "${@bb.utils.contains('DISTRO_FEATURES', 'sage_svp', 'gst-svp-ext', '', d)}"

RDEPENDS_${PN}_append_rpi = " ${@bb.utils.contains('DISTRO_FEATURES', 'vc4graphics', '', 'userland', d)}"

