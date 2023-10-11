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

# Currently LISA's repository is in stagingrdkm which is staging area for
# RDK projects therefore we can't use the default way of building rdkservice
# plugin but need to overwrite some of the properties set by common_plugin.inc
# and slightly alter its behavior. When LISA becomes an official plugin
# and will be moved to regular plugins repository the following definitions
# can be removed
SRC_URI_remove = "git://github.com/LibertyGlobal/rdkservices.git;protocol=git;branch=${RDKSERVICES_BRANCH};name=${BPN};destsuffix=git"
SRC_URI_append = " git://github.com/stagingrdkm/LISA.git;branch=main;protocol=https"

# Overwrite SRCREV to fixed stagingrdkm/LISA revision.
SRCREV = "cd5391de3d6381e0bea671a35df9081a2b64be55"
SRCREV_${BPN} = "cd5391de3d6381e0bea671a35df9081a2b64be55"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

TOOLCHAIN = "gcc"
EXTRA_OECMAKE += "-DCMAKE_SYSROOT=${STAGING_DIR_HOST}"
EXTRA_OECMAKE += "-DBUILD_REFERENCE=${SRCREV}"

# the lines below were taken from upstream recipe, but were cancelling subsequent 'EXTRA_OECMAKE' (probably because they use _brcm postfix)
# so now we just use EXTRA_OECMAKE, without any suffixes
#EXTRA_OECMAKE_brcm += "-DBUILD_BROADCOM=ON"
#EXTRA_OECMAKE_rpi += "-DBUILD_RASPBERRYPI=ON"
#EXTRA_OECMAKE_amlrefapp += "-DBUILD_AMLOGIC=ON"
EXTRA_OECMAKE += "-DBUILD_BROADCOM=ON"


SRC_URI += "file://lisa@.service"

SRC_URI += "file://amlogic.cmake;subdir=git"
SRC_URI += "file://raspberrypi.cmake;subdir=git"

EXTRA_OECMAKE += "-DPLUGIN_LISA_APPS_GID=251 -DPLUGIN_LISA_DATA_GID=145"

do_install_append() {
    install -d ${D}${systemd_unitdir}/system
    install -m 0644 ${WORKDIR}/lisa@.service ${D}/${systemd_unitdir}/system/
}

SYSTEMD_SERVICE_${PN} += "lisa@.service"
