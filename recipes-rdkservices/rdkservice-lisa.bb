SUMMARY  = "RDK services LISA plugin"
LICENSE  = "Apache-2.0"

# By default all Thunder plugins recipes just define the plugin name under
# which it is stored in rdk-services repositry at
# https://github.com/rdkcentral/rdkservices and include common_plugin.inc.
# If LISA was a part of that repo and had a directory called LISA then
# the plugin recipe would look like this

# plugin directory in rdkservices repository
PLUGINDIR="LISA"
include rdkservices-common/common_plugin.inc

EXTERNALSRC = ""

DEPENDS += "boost curl libarchive"

# LISA plugin resides in its own repo: https://github.com/rdkcentral/LISA
# therefore we can't use the default way of building rdkservice
# plugin but need to overwrite some of the properties set by common_plugin.inc
# and slightly alter its behavior.
SRC_URI_remove = "git://github.com/LibertyGlobal/rdkservices.git;protocol=git;branch=${RDKSERVICES_BRANCH};name=${BPN};destsuffix=git"
SRC_URI_append = " ${CMF_GITHUB_ROOT}/LISA;protocol=${CMF_GIT_PROTOCOL};branch=main"
SRCREV = "e8b26299909d2f86e25f49e0db994ef4d793acee"
SRCREV_${BPN} = "e8b26299909d2f86e25f49e0db994ef4d793acee"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e3fc50a88d0a364313df4b21ef20c29e"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

TOOLCHAIN = "gcc"
EXTRA_OECMAKE += "-DCMAKE_SYSROOT=${STAGING_DIR_HOST}"
EXTRA_OECMAKE += "-DBUILD_REFERENCE=${SRCREV}"

SRC_URI += "file://lisa@.service"

EXTRA_OECMAKE += "-DPLUGIN_LISA_APPS_GID=251 -DPLUGIN_LISA_DATA_GID=145"

do_install_append() {
    install -d ${D}${systemd_unitdir}/system
    install -m 0644 ${WORKDIR}/lisa@.service ${D}/${systemd_unitdir}/system/
}

SYSTEMD_SERVICE_${PN} += "lisa@.service"

include rdkservice-lisa-dac-config.inc
