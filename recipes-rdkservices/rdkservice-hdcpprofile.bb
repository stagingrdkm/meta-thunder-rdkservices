SUMMARY = "RDK services HdcpProfile plugin"

# plugin directory in rdkservices repository
PLUGINDIR="HdcpProfile"

require rdkservices-common/common_plugin.inc

RDEPENDS_${PN} += "devicesettings"

EXTRA_OECMAKE += " \
    -DPLUGIN_HDCPPROFILE_AUTOSTART=true \
 "

