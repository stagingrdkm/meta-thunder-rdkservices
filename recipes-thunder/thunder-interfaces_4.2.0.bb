SUMMARY = "WPEFramework interfaces"
LICENSE = "Apache-2.0"
PR = "r0"

require include/thunder.inc
LIC_FILES_CHKSUM = "file://LICENSE;md5=2f6c18f99faffa0e5d4ff478705c53f8"
PROVIDES += "wpeframework-interfaces"
RPROVIDES_${PN} += "wpeframework-interfaces"

DEPENDS = "wpeframework wpeframework-tools-native"

SRC_URI = "git://github.com/rdkcentral/ThunderInterfaces.git;protocol=git;branch=R4;name=wpeframework-interfaces \
           file://Library-version-Matched-With-Release-interfaces.patch \
           file://SplitDeviceCapablities.patch \
           file://0018-notifyclient-event-added.patch \
           file://0020-Adding-the-VoiceCommand-API-for-Netflix-plugin.patch \
           file://0001-Add-TextToSpeech-Interface.patch \
           file://0001-RDK-37042-Add-interfaces-for-Airplay-Plugin.patch \
           file://0001-RDK-40478-Run-AirplayDaemon-as-thunder-plugin.patch \
           file://0001-OCDM-Adapter-Implementation.patch \
           file://Inetflix-api-revert.patch \
           file://0101-IWatermark-Interface-In-R4.patch \
           file://0001-add-PersistLoadWatermark.patch \
           file://0007-RDK-IDeviceInfo-Changes.patch \
           file://0110-OMWAPPI-910-Increase-subsamples-limit-for-4k-content_new.patch \
           file://0001-OMWAPPI-1634-align-metrics-API.patch \
           "

# R4.2
SRCREV_wpeframework-interfaces = "d144e041f9c2169d45136e36da246c7f5c9b80a7"

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
