SUMMARY = "RDK services OCDM plugin"

# plugin directory in rdkservices repository
PLUGINDIR="OpenCDMi"

include rdkservices-common/common_plugin.inc
include ${RDKORIGINDIR}/include/ocdm.inc

EXTERNALSRC = ""
SRC_URI += "${DEFAULT_NEXT_SRC_URI}"
SRCREV_${PN}="${DEFAULT_NEXT_SRC_URI_REVISION}"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

# patches and files from rdkservice-ocdm
SRC_URI += "file://ocdm@.service"
SRC_URI += "file://ocdm.conf"
SRC_URI += "file://ocdm_store_check.sh"

#overwrite library name
OECMAKE_TARGET_COMPILE = "WPEFrameworkOCDM"

DEPENDS += "wpeframework-clientlibraries devicesettings iarmmgrs libodherr asconnector rdk-logger"
# when cmake scripts was provided by wpeframework-clientlibraries it was fine, now the scripts are not found
DEPENDS += "ocdm-tcl"
# problematic line is the following: find_package(ocdm REQUIRED)
# it means that each app/module that integrates with splited TCL could have similar integration issues
#| CMake Error at OpenCDMi/CMakeLists.txt:35 (find_package):
#|   By not providing "Findocdm.cmake" in CMAKE_MODULE_PATH this project has
#|   asked CMake to find a package configuration file provided by "ocdm", but
#|   CMake did not find one.
#| 
#|   Could not find a package configuration file provided by "ocdm" with any of
#|   the following names:
#| 
#|     ocdmConfig.cmake
#|     ocdm-config.cmake
#| 
#|   Add the installation prefix of "ocdm" to CMAKE_PREFIX_PATH or set
#|   "ocdm_DIR" to a directory containing one of the above files.  If "ocdm"
#|   provides a separate development package or SDK, be sure it has been
#|   installed.

RDEPENDS_${PN} += "devicesettings libodherr"

PACKAGECONFIG ?= " \
    opencdmi \
    ${@bb.utils.contains('DISTRO_FEATURES', 'playready_nexus_svp',  'opencdmi_prnx_svp', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'widevine_nexus_svp',   'opencdmi_wv_svp', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'clearkey',             'opencdmi_ck', '', d)} \
"

PACKAGECONFIG[opencdmi]       = "-DPLUGIN_OPENCDMI=ON \
                                 -DPLUGIN_OPENCDMI_AUTOSTART=false \
                                 -DPLUGIN_OPENCDMI_MODE=Container \
                                ,,"

do_install_append() {
    install -d ${D}${systemd_unitdir}/system
    install -m 0644 ${WORKDIR}/ocdm@.service ${D}/${systemd_unitdir}/system/
    install -d ${D}${sysconfdir}/tmpfiles.d
    install -m 0644 ${WORKDIR}/ocdm.conf ${D}${sysconfdir}/tmpfiles.d
    install -d ${D}${bindir}
    install -m 0500 ${WORKDIR}/ocdm_store_check.sh ${D}${bindir}/
}

FILES_${PN} += "${sysconfdir}/tmpfiles.d/ocdm.conf"

SYSTEMD_SERVICE_${PN} += "ocdm@.service"

