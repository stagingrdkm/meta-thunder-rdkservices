## from meta-cmf-video-reference-next
FILESEXTRAPATHS_prepend := "${THISDIR}/rdkservices-common:"

## from broadcom layer
EXTRA_OECMAKE += " \
    -DBUILD_BROADCOM=ON \
    -DSVCMGR_PLATFORM_SOC_NAME=broadcom \
    -DPLUGIN_TRACECONTROL_AUTOSTART=false \
    -DSVCMGR_PLATFORM_SOC=common \
    -DSECAPI_LIB= \
 "


## LGI specific
inherit onemw_externalsrc systemd onemw_rdkversion
EXTERNALSRC = ""
EXTERNALSRC_SYMLINKS = ""

# rdkservices "next" location. Thunder R4
SRC_URI_remove = "git://github.com/rdkcentral/rdkservices.git;protocol=https;branch=sprint/2101"
SRC_URI += "git://github.com/LibertyGlobal/rdkservices.git;protocol=git;branch=test_wpe_r4;name=${PN};destsuffix=git"
SRCREV = "774dc81e818eba279213704c8802464888e245c0"

DEPENDS_remove = "storagemanager"
DEPENDS_remove = "audiocapturemgr"
DEPENDS_remove = "tts"
DEPENDS_remove = "breakpad-wrapper"
DEPENDS_remove = "rfc"

DEPENDS += "wpeframework-tools-native"

# !!! IMPORTANT !!! in case of adding/removing of plugin please check the wpeframework-interfaces/interfaces.inc file
# and update list of automatically generated code for both (COMRPC and JSONRPC) INTERFACES_PATTERNS and JSONRPC_PATTERNS variables

RDEPENDS_${PN} += "${@bb.utils.contains('DISTRO_FEATURES', 'opencdm', 'rdkservice-ocdm', '', d)}"

#RDEPENDS_${PN} += "${@bb.utils.contains('DISTRO_FEATURES', 'onemwfirebolt', 'rdkservice-deviceinfo', '', d)}"
RDEPENDS_${PN} += "${@bb.utils.contains('DISTRO_FEATURES', 'onemwfirebolt', 'rdkservice-displayinfo', '', d)}"
RDEPENDS_${PN} += "${@bb.utils.contains('DISTRO_FEATURES', 'onemwfirebolt', 'rdkservice-playerinfo', '', d)}"

# new plugins to deploy under onemwfirebolt flag
RDEPENDS_${PN} += "${@bb.utils.contains('DISTRO_FEATURES', 'onemwfirebolt', 'rdkservice-hdcpprofile', '', d)}"
RDEPENDS_${PN} += "${@bb.utils.contains('DISTRO_FEATURES', 'onemwfirebolt', 'rdkservice-displaysettings', '', d)}"
RDEPENDS_${PN} += "${@bb.utils.contains('DISTRO_FEATURES', 'onemwfirebolt', 'rdkservice-deviceidentification', '', d)}"
#RDEPENDS_${PN} += "${@bb.utils.contains('DISTRO_FEATURES', 'onemwfirebolt', 'rdkservice-xcast', '', d)}"
RDEPENDS_${PN} += "${@bb.utils.contains('DISTRO_FEATURES', 'onemwfirebolt', 'rdkservice-hdmicec', '', d)}"

# new LGI specific plugins under onemwfirebolt flag
RDEPENDS_${PN} += "${@bb.utils.contains('DISTRO_FEATURES', 'onemwfirebolt', 'rdkservice-lgi-network', '', d)}"
RDEPENDS_${PN} += "${@bb.utils.contains('DISTRO_FEATURES', 'onemwfirebolt', 'rdkservice-lgi-texttospeech', '', d)}"
RDEPENDS_${PN} += "${@bb.utils.contains('DISTRO_FEATURES', 'onemwfirebolt', 'rdkservice-lgi-system', '', d)}"


RDEPENDS_${PN} += "${@bb.utils.contains('DISTRO_FEATURES', 'onemwdac', 'rdkservice-fireboltmediaplayer', '', d)}"
RDEPENDS_${PN} += "${@bb.utils.contains('DISTRO_FEATURES', 'onemwdac', 'rdkservice-lisa', '', d)}"
RDEPENDS_${PN} += "${@bb.utils.contains('DISTRO_FEATURES', 'onemwdac', 'rdkservice-ocicontainer', '', d)}"

RDEPENDS_${PN} += "${@bb.utils.contains('DISTRO_FEATURES', 'system-lgi-rdkservices', 'rdkservice-lgidisplaysettings', '', d)}"
RDEPENDS_${PN} += "${@bb.utils.contains('DISTRO_FEATURES', 'system-lgi-rdkservices', 'rdkservice-lgihdcpprofile', '', d)}"
RDEPENDS_${PN} += "${@bb.utils.contains('DISTRO_FEATURES', 'system-lgi-rdkservices', 'rdkservice-lgi-hdmicec', '', d)}"

PACKAGECONFIG[telemetry]         = "-DPLUGIN_TELEMETRY=ON, -DPLUGIN_TELEMETRY=OFF, telemetry, "

LDFLAGS_append=" -Wl,--no-undefined"
