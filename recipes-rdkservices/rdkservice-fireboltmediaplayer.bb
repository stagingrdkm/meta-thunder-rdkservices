SUMMARY = "RDK services Firebolt Media Player plugin"

# plugin directory in rdkservices repository
PLUGINDIR="FireboltMediaPlayer"

require rdkservices-common/common_plugin.inc

EXTERNALSRC = ""
SRC_URI += "${DEFAULT_NEXT_SRC_URI}"
SRCREV_${PN}="${DEFAULT_NEXT_SRC_URI_REVISION}"


PACKAGECONFIG ?= "fireboltmediaplayer"
PACKAGECONFIG[fireboltmediaplayer]   = "-DPLUGIN_DEVICEINFO=ON,-DPLUGIN_DEVICEINFO=OFF,"

EXTRA_OECMAKE += " \
    -DPLUGIN_FIREBOLTMEDIAPLAYER=on \
"
CXXFLAGS += "-UUSE_IARM"

RDEPENDS_${PN} += "rfc"
DEPENDS += "iarmbus wpeframework-clientlibraries curl aamp rfc"
