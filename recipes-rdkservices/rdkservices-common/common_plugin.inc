# common definitions for rdkservices plugin compile in separate package
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=16cf2209d4e903e4d5dcd75089d7dfe2"
PR = "r1"
PV = "3.0+git${SRCPV}"
S = "${WORKDIR}/git"

inherit onemw_externalsrc cmake pkgconfig systemd onemw_rdkversion
EXTERNALSRC = "${MW_SRC_PATH}/rdkservices/"

DEPENDS += "wpeframework-tools-native wpeframework wpeframework-interfaces"

# rdkservices "next" location, Thunder R4
DEFAULT_NEXT_SRC_URI="git://github.com/LibertyGlobal/rdkservices.git;protocol=git;branch=test_wpe_r4;name=${PN};destsuffix=git"
DEFAULT_NEXT_SRC_URI_REVISION="71c62d9e0f0b776b46a840ff9cd4394cc0ea7602"

TOOLCHAIN = "gcc"
EXTRA_OECMAKE += "-DCMAKE_SYSROOT=${STAGING_DIR_HOST}"

EXTRA_OECMAKE += " \
    -DBUILD_REFERENCE=${SRCREV} \
    -DBUILD_SHARED_LIBS=ON \
    -DSECAPI_LIB=sec_api \
    ${@bb.utils.contains('DISTRO_FEATURES', 'systimemgr',     '-DBUILD_ENABLE_SYSTIMEMGR_SUPPORT=ON', '', d)} \
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

RDKORIGINDIR = "../../../meta-rdk-next/recipes-extended/rdkservices/"
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


