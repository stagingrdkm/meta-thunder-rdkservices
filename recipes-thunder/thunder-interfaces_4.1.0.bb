SUMMARY = "WPEFramework interfaces"
LICENSE = "Apache-2.0"
PR = "r0"

require include/thunder.inc
LIC_FILES_CHKSUM = "file://LICENSE;md5=2f6c18f99faffa0e5d4ff478705c53f8"
PROVIDES += "wpeframework-interfaces"
RPROVIDES_${PN} += "wpeframework-interfaces"

DEPENDS = "wpeframework wpeframework-tools-native"

SRC_URI = "git://github.com/rdkcentral/ThunderInterfaces.git;protocol=https;branch=R4;name=wpeframework-interfaces \
           file://SplitDeviceCapablities.patch \
           file://0018-notifyclient-event-added.patch \
           file://0020-Adding-the-VoiceCommand-API-for-Netflix-plugin.patch \
           file://Library-version-Matched-With-Release-interfaces.patch \
           "

# R4.1
SRCREV_wpeframework-interfaces = "5011063f484e3222da06f46901f2845187a22653"

# ----------------------------------------------------------------------------

PACKAGECONFIG ?= "release"

# ----------------------------------------------------------------------------

EXTRA_OECMAKE += " \
    -DBUILD_SHARED_LIBS=ON \
    -DBUILD_REFERENCE=${SRCREV} \
    -DPYTHON_EXECUTABLE=${STAGING_BINDIR_NATIVE}/python3-native/python3 \
"

# ----------------------------------------------------------------------------

do_install_append() {
    if ${@bb.utils.contains("DISTRO_FEATURES", "opencdm", "true", "false", d)}
    then
        install -m 0644 ${D}${includedir}/WPEFramework/interfaces/IDRM.h ${D}${includedir}/cdmi.h
    fi
}

# ----------------------------------------------------------------------------

FILES_SOLIBSDEV = ""
FILES_${PN} += "${libdir}/* ${datadir}/WPEFramework/* ${PKG_CONFIG_DIR}/*.pc"
FILES_${PN}-dev += "${libdir}/cmake/*"
FILES_${PN}-dbg += "${libdir}/wpeframework/proxystubs/.debug/"
FILES_${PN} += "${includedir}/cdmi.h"

INSANE_SKIP_${PN} += "dev-so"
INSANE_SKIP_${PN}-dbg += "dev-so"
