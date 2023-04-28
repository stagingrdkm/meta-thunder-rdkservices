SUMMARY = "RDK services TraceControl plugin"

# plugin directory in rdkservices repository
PLUGINDIR="TraceControl"

include rdkservices-common/common_plugin.inc

EXTERNALSRC = ""
SRC_URI += "${DEFAULT_NEXT_SRC_URI}"
SRCREV_${PN}="${DEFAULT_NEXT_SRC_URI_REVISION}"

EXTRA_OECMAKE += " \
    -DPLUGIN_TRACECONTROL_AUTOSTART=false \
"

