# common definitions for rdkservices plugin compile in separate package
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=16cf2209d4e903e4d5dcd75089d7dfe2"
PR = "r1"
PV = "3.0+git${SRCPV}"
S = "${WORKDIR}/git"

inherit onemw_externalsrc cmake pkgconfig systemd onemw_rdkversion
EXTERNALSRC = ""

DEPENDS += "wpeframework-tools-native wpeframework wpeframework-interfaces"

# rdkservices location, Thunder R4
RDKSERVICES_BRANCH = "${@onemw_variable_switch ('ONEMW_THUNDER4_VERSION_MINOR', \
                   '1',    'lgi-thunder4-20230518',   \
                   '2',    'lgi-thunder4.2-20230922', \
                   '3',    'lgi-thunder4.3-20230628', \
                 d)}"

SRC_URI += "git://github.com/LibertyGlobal/rdkservices.git;protocol=git;branch=${RDKSERVICES_BRANCH};name=${BPN};destsuffix=git"

SRCREV_${BPN}= "${@onemw_variable_switch ('ONEMW_THUNDER4_VERSION_MINOR', \
                   '1',    '143c11fa5bc65dfae846f91589f601f68ac40a00', \
                   '2',    '4deee65592d4cf5a2ee30e2d8614a00bc0073804', \
                   '3',    '09af974572c25c21af8f4fa25ee92c68212bd12f', \
                 d)}"

TOOLCHAIN = "gcc"
EXTRA_OECMAKE += "-DCMAKE_SYSROOT=${STAGING_DIR_HOST}"
EXTRA_OECMAKE += "-DTOOLS_SYSROOT=${STAGING_DIR_NATIVE}"

EXTRA_OECMAKE += " \
    -DBUILD_REFERENCE=${SRCREV} \
    -DBUILD_SHARED_LIBS=ON \
    -DSECAPI_LIB=sec_api \
    ${@bb.utils.contains('DISTRO_FEATURES', 'systimemgr',     '-DBUILD_ENABLE_SYSTIMEMGR_SUPPORT=ON', '', d)} \
    -DUSE_THUNDER_R4=1 \
"
EXTRA_OECMAKE += " \
    -DBUILD_BROADCOM=ON \
    -DSVCMGR_PLATFORM_SOC_NAME=broadcom \
    -DSVCMGR_PLATFORM_SOC=common \
    -DSECAPI_LIB= \
 "

TARGET_CXXFLAGS += "${@bb.utils.contains('MACHINE_FEATURES', 'debug', '-D_TRACE_LEVEL=2 -D__ENABLE_ASSERT__=ON', '', d)}"


PACKAGECONFIG[activitymonitor]      = "-DPLUGIN_ACTIVITYMONITOR=ON,-DPLUGIN_ACTIVITYMONITOR=OFF,"
PACKAGECONFIG[continuewatching]     = "-DPLUGIN_CONTINUEWATCHING=ON,-DPLUGIN_CONTINUEWATCHING=OFF,"
PACKAGECONFIG[devicediagnostics]    = "-DPLUGIN_DEVICEDIAGNOSTICS=ON,-DPLUGIN_DEVICEDIAGNOSTICS=OFF,"
PACKAGECONFIG[framerate]            = "-DPLUGIN_FRAMERATE=ON,-DPLUGIN_FRAMERATE=OFF,"
PACKAGECONFIG[stateobserver]        = "-DPLUGIN_STATEOBSERVER=ON,-DPLUGIN_STATEOBSERVER=OFF,"
PACKAGECONFIG[telemetry]            = "-DPLUGIN_TELEMETRY=ON, -DPLUGIN_TELEMETRY=OFF, telemetry, "
PACKAGECONFIG[warehouse]            = "-DPLUGIN_WAREHOUSE=ON,-DPLUGIN_WAREHOUSE=OFF,"

PACKAGECONFIG_remove = "activitymonitor"
PACKAGECONFIG_remove = "continuewatching"
PACKAGECONFIG_remove = "devicediagnostics"
PACKAGECONFIG_remove = "framerate"
PACKAGECONFIG_remove = "stateobserver"
PACKAGECONFIG_remove = "telemetry"
PACKAGECONFIG_remove = "warehouse"

RDKORIGINDIR = "../recipes-thunder/"
FILESEXTRAPATHS_prepend := "${THISDIR}/../${RDKORIGINDIR}/files:"
FILESEXTRAPATHS_prepend := "${THISDIR}:"

PLUGINFLAG="${@d.getVar('PLUGINDIR', True).upper()}"
EXTRA_OECMAKE += " \
    -DPLUGIN_${PLUGINFLAG}=true \
"

OECMAKE_TARGET_COMPILE = "WPEFramework${PLUGINDIR}"
OECMAKE_TARGET_INSTALL = "${PLUGINDIR}/install"

#ONEM-22285: do not create any symlinks in source directory -- to allows sstate cache reusability
EXTERNALSRC_SYMLINKS = ""

SRCREV_FORMAT = "rdkservices"

FILES_SOLIBSDEV = ""
FILES_${PN} += "${libdir}/wpeframework/plugins/*.so ${libdir}/*.so ${datadir}/WPEFramework/*"

INSANE_SKIP_${PN} += "libdir staticdev dev-so"
INSANE_SKIP_${PN}-dbg += "libdir"


