SUMMARY = "RDK services MessageControl plugin"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=39fb5e7bc6aded7b6d2a5f5aa553425f"
PR = "r1"
PV = "3.0+git${SRCPV}"
S = "${WORKDIR}/git"

# source code directly taken from Metrological version:
# git@github.com:WebPlatformForEmbedded/ThunderNanoServicesRDK.git R4 branch R4.3 tag revision: a1ceaa9df45735630f555a97afa71d771f979b22
# for Thunder 4.2, github.com:WebPlatformForEmbedded/ThunderNanoServicesRDK.git branch R4, tag R4.2.1, version 7fb0b598ab6bd87b49235b99770a3162bb3691e1 - that fixes deinitialization core dump

SRC_URI += "git://github.com/WebPlatformForEmbedded/ThunderNanoServicesRDK.git;protocol=git;branch=R4;name=${BPN};destsuffix=git"
SRCREV_${BPN}="${@bb.utils.contains('DISTRO_FEATURES', 'thunder-4.3', 'a1ceaa9df45735630f555a97afa71d771f979b22', '7fb0b598ab6bd87b49235b99770a3162bb3691e1', d)}"

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

