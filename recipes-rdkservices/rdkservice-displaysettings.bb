# compilation issues around devicesettings dependencies

SUMMARY = "RDK services DisplaySettings plugin"

# plugin directory in rdkservices repository
PLUGINDIR="DisplaySettings"

include rdkservices-common/common_plugin.inc

EXTERNALSRC = ""
SRC_URI += "${DEFAULT_NEXT_SRC_URI}"
SRCREV_${PN}="${DEFAULT_NEXT_SRC_URI_REVISION}"

EXTRA_OECMAKE += " \
    -DPLUGIN_DISPLAYSETTINGS_AUTOSTART=true \
 "

RDEPENDS_${PN} += "devicesettings"
DEPENDS += "devicesettings"
