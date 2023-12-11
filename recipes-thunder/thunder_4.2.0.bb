SUMMARY = "Thunder Framework"
require include/thunder.inc
require include/thunder_${PV}.inc

PROVIDES += "wpeframework"
RPROVIDES_${PN} += "wpeframework"
DEPENDS = "zlib wpeframework-tools-native rfc"
DEPENDS_append_libc-musl = " libexecinfo"
DEPENDS += "breakpad-wrapper"

# Need gst-svp-ext which is an abstracting lib for metadata
DEPENDS +=  "${@bb.utils.contains('DISTRO_FEATURES', 'rdk_svp', 'gst-svp-ext', '', d)}"

#default patches for compiling thunder
SRC_URI += "file://wpeframework-init \
           file://wpeframework.service.in \
           file://WPEFramework.env \
           file://Library_version_matched_with_release_tag.patch \
           file://Remove_versioning_for_executables.patch \
           "
# file://r4/0001-RDK-28534-Security-Agent-Utility-and-Logging.patch

#Additional patches specific to Comcast
SRC_URI += "file://0001-Thunder_json_quoted_string_parsing_fix.patch \
            file://0001-DEADLOCK-Deadlock-prevention-by-locking-the-ServiceA.patch \
            file://0001-DEADLOCK-Next-to-closing-the-deadlock-on-the-open-of.patch \
            file://0007-Make-size-type-of-Core-Frame-Text-configurable.patch \
            file://0008-Fix-calculation-of-handled-size.patch \
            file://0001_Reverted_changes_Fix_JSON_Reset_R3.5_for_SystemServicesPlugin.patch \
            file://activationevent.patch \
            file://wpeframework_added_optimization_flag_improvement.patch \
            file://DELIA-56519_wpeframework_fix_R4.patch \
            file://R4.3.2_Wait_for_Open_in_Communication_Channel.patch \
            file://RDKTV-24021.patch \
            file://ONEM-31782_R4.patch \
            file://dsmanagerplugin;subdir=git/Source \
            file://ONEM-33502-Add-DSManagerPlugin.patch \
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
    virtualinput \
    websocket \
    "

PACKAGECONFIG_append = " ${@bb.utils.contains('DISTRO_FEATURES', 'DOBBY_CONTAINERS','processcontainers processcontainers_dobby', '',d)}"

PACKAGECONFIG_append = " ${@bb.utils.contains('DISTRO_FEATURES', 'thunder_startup_services', 'com pluginactivator', '', d)}"
PACKAGECONFIG_append = " ${@bb.utils.contains('DISTRO_FEATURES', 'wpeframework_systemd_notify', 'sdnotify', '', d)}"

# Buildtype
# Maybe we need to couple this to a Yocto feature
PACKAGECONFIG[debug]          = "-DCMAKE_BUILD_TYPE=Debug,,"
PACKAGECONFIG[debugoptimized] = "-DCMAKE_BUILD_TYPE=DebugOptimized,,"
PACKAGECONFIG[releasesymbols] = "-DCMAKE_BUILD_TYPE=ReleaseSymbols,,"
PACKAGECONFIG[release]        = "-DCMAKE_BUILD_TYPE=Release,,"
PACKAGECONFIG[production]     = "-DCMAKE_BUILD_TYPE=MinSizeRel,,"

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

PACKAGECONFIG[com] = "-DCOM=ON,,,"
PACKAGECONFIG[pluginactivator] = "-DBUILD_PLUGIN_ACTIVATOR=ON,,,"
PACKAGECONFIG[sdnotify] = ",,systemd,"

SRC_URI += "${@bb.utils.contains('PACKAGECONFIG', 'sdnotify', 'file://wpeframework-sd_notify.patch ', '', d)}"

# FIXME, determine this a little smarter
# Provision event is required for libprovision and provision plugin
# Location event is required for locationsync plugin
# Time event is required for timesync plugin
# Identifier event is required for Compositor plugin
# Internet event is provided by the LocationSync plugin
# WebSource event is provided by the WebServer plugin

WPEFRAMEWORK_EXTERN_EVENTS ?= " \
    Decryption \
    ${@bb.utils.contains('PACKAGECONFIG', 'provisionproxy', 'Provisioning', '', d)} \
    ${@bb.utils.contains('PACKAGECONFIG', 'websource', 'WebSource', '', d)} \
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
    -DENABLED_TRACING_LEVEL=2 \
    -DPERSISTENT_PATH=${WPEFRAMEWORK_PERSISTENT_PATH} \
    -DSYSTEM_PREFIX=${WPEFRAMEWORK_SYSTEM_PREFIX} \
    -DPYTHON_EXECUTABLE=${STAGING_BINDIR_NATIVE}/python3-native/python3 \
    -DIDLE_TIME=${WPEFRAMEWORK_IDLE_TIME} \
    -DTHREADPOOL_COUNT=${WPEFRAMEWORK_THREADPOOL_COUNT} \
    -DEXCEPTION_CATCHING=ON \
    -DWARNING_REPORTING=ON \
    -DHIDE_NON_EXTERNAL_SYMBOLS=OFF \
    -DEXIT_REASONS=${WPEFRAMEWORK_EXIT_REASONS} \
    -DMESSAGING=ON \
    -DDSMANAGERPLUGIN=ON \
    -DUMASK=001 \
"

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
        extra_after="${extra_after} ${WAYLAND_COMPOSITOR}"
        install -d ${D}${systemd_unitdir}/system
        sed -e "s|@EXTRA_AFTER@|${extra_after}|g" < ${WORKDIR}/wpeframework.service.in > ${D}${systemd_unitdir}/system/wpeframework.service
    else
        install -d ${D}${sysconfdir}/init.d
        install -m 0755 ${WORKDIR}/wpeframework-init ${D}${sysconfdir}/init.d/wpeframework
    fi

    install -d ${D}${sysconfdir}/wpeframework
    install -m 0644 ${WORKDIR}/WPEFramework.env ${D}${sysconfdir}/wpeframework
}

do_install_append() {
    if [ "${@bb.utils.contains('PACKAGECONFIG', 'sdnotify', 'true', 'false', d)}" = "true" ]; then
        sed -i -e 's/\[Service\]/\[Service\]\nType=notify/' ${D}${systemd_system_unitdir}/wpeframework.service
    fi
}

SYSTEMD_SERVICE_${PN} = "wpeframework.service"

# ----------------------------------------------------------------------------

PACKAGES =+ "${PN}-initscript"

FILES_${PN}-initscript = "${sysconfdir}/init.d/wpeframework"

FILES_SOLIBSDEV = ""
FILES_${PN} += "${libdir}/*.so ${datadir}/WPEFramework/* ${PKG_CONFIG_DIR}/*.pc"
FILES_${PN} += "${libdir}/wpeframework/proxystubs/*"
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
