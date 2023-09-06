SUMMARY = "RDK services MessageControl plugin"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=39fb5e7bc6aded7b6d2a5f5aa553425f"
PR = "r1"
PV = "3.0+git${SRCPV}"
S = "${WORKDIR}/git"

# source code directly taken from Metrological version:
# git@github.com:WebPlatformForEmbedded/ThunderNanoServicesRDK.git R4 branch R4.3 tag revision: a1ceaa9df45735630f555a97afa71d771f979b22
# ... or git@github.com:WebPlatformForEmbedded/ThunderNanoServicesRDK.git R4 branch R4.2 tag revision: 68cb55f538046245ca9046e8b8c7bf31e07fc0ce

SRC_URI += "git://github.com/WebPlatformForEmbedded/ThunderNanoServicesRDK.git;protocol=git;branch=R4;name=${PN};destsuffix=git"
SRCREV_${PN}="${@bb.utils.contains('DISTRO_FEATURES', 'thunder-4.3', 'a1ceaa9df45735630f555a97afa71d771f979b22', '68cb55f538046245ca9046e8b8c7bf31e07fc0ce', d)}"

inherit cmake

DEPENDS += "wpeframework-tools-native wpeframework wpeframework-interfaces"

EXTRA_OECMAKE += "-DCMAKE_SYSROOT=${STAGING_DIR_HOST}"
EXTRA_OECMAKE += "-DTOOLS_SYSROOT=${STAGING_DIR_NATIVE}"

EXTRA_OECMAKE += " \
    -DBUILD_REFERENCE=${SRCREV} \
    -DPLUGIN_MESSAGECONTROL=ON \
    -DPLUGIN_MESSAGECONTROL_AUTOSTART=false \
"

TARGET_CXXFLAGS += "${@bb.utils.contains('MACHINE_FEATURES', 'debug', '-D_TRACE_LEVEL=2 -D__ENABLE_ASSERT__=ON', '', d)}"

FILES_SOLIBSDEV = ""
FILES_${PN} += "${libdir}/wpeframework/plugins/*.so ${libdir}/*.so ${datadir}/WPEFramework/*"

INSANE_SKIP_${PN} += "libdir staticdev dev-so"
INSANE_SKIP_${PN}-dbg += "libdir"

