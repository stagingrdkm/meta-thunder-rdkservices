# compilation issues around devicesettings dependencies

SUMMARY = "RDK services DisplaySettings plugin"

# plugin directory in rdkservices repository
PLUGINDIR="DisplaySettings"

require rdkservices-common/common_plugin.inc

EXTRA_OECMAKE += " \
    -DPLUGIN_DISPLAYSETTINGS_AUTOSTART=true \
 "

RDEPENDS_${PN} += "devicesettings"
DEPENDS += "devicesettings iarmmgrs"
