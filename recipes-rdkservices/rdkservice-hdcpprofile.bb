SUMMARY = "RDK services HdcpProfile plugin"

# plugin directory in rdkservices repository
PLUGINDIR="HdcpProfile"

require rdkservices-common/common_plugin.inc

EXTERNALSRC = ""
SRC_URI += "${DEFAULT_NEXT_SRC_URI}"
SRCREV_${PN}="${DEFAULT_NEXT_SRC_URI_REVISION}"

RDEPENDS_${PN} += "devicesettings"

EXTRA_OECMAKE += " \
    -DPLUGIN_HDCPPROFILE_AUTOSTART=true \
 "

