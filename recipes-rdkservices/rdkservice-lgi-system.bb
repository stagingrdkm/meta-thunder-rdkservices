SUMMARY = "RDK services LGI System plugin"

# plugin directory in rdkservices repository
PLUGINDIR="LgiSystemServices"

include rdkservices-common/common_plugin.inc

EXTRA_OECMAKE += "-DPLUGIN_LGISYSTEMSERVICE_AUTOSTART=true"

EXTERNALSRC = ""
#EXTERNALSRC = "${MW_SRC_PATH}/rdkservices-next/"
SRC_URI += "git://github.com/LibertyGlobal/rdkservices.git;protocol=git;branch=OMWAPPI-1512-getTimeZoneDST-system-plugin;name=${PN};destsuffix=git"
SRCREV_${PN}="327a7a488fb5cb17f4c92886438bb8778203d40e"
