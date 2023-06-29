SUMMARY = "RDK services TraceControl plugin"

# plugin directory in rdkservices repository
PLUGINDIR="TraceControl"

require rdkservices-common/common_plugin.inc

EXTRA_OECMAKE += " \
    -DPLUGIN_TRACECONTROL_AUTOSTART=false \
"

