SUMMARY = "RDK services XCast plugin"

# plugin directory in rdkservices repository
PLUGINDIR="XCast"

require rdkservices-common/common_plugin.inc

EXTERNALSRC = ""
SRC_URI += "${DEFAULT_NEXT_SRC_URI}"
SRCREV_${PN}="${DEFAULT_NEXT_SRC_URI_REVISION}"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

DEPENDS += "cjson rfc rtremote wpeframework-clientlibraries rtremote"
RDEPENDS_${PN} += "cjson rfc rtremote"

EXTRA_OECMAKE += " \
    -DPLUGIN_XCAST_AUTOSTART=true \
"

CXXFLAGS += "-DXCAST_ENABLED_BY_DEFAULT -DXCAST_ENABLED_BY_DEFAULT_IN_STANDBY"
